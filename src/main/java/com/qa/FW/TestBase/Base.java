package com.qa.FW.TestBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.asserts.SoftAssert;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.qa.FW.Pages.Eclerx_Comply_Login;
import com.qa.FW.Pages.Eclerx_Comply_W8BENE;
import com.qa.FW.Utility.Browserfactory;
import com.qa.FW.Utility.ConfigReader;
import com.qa.FW.Utility.Helper;
import com.qa.FW.Utility.Waits;
import com.qa.FW.Utility.WebEventListner;

public class Base {	

	// Generic object creation
	public WebDriver driver;
	public static ConfigReader config;
	public static ExtentReports report;
	public ExtentHtmlReporter extent;
	public static ExtentTest log;
	public static SoftAssert softAssert;
	public static EventFiringWebDriver e_event;
	public static WebEventListner e_Listener;
	// public static ReadExcelDatadriven readexldd;
	public static Waits waits;
	public static Helper helper;
	public static Map<Object, Object> map;
	private XSSFSheet sheet;
	private static XSSFRow row;
	public String sheetname;
	public static Eclerx_Comply_W8BENE BENE;
	public static Eclerx_Comply_Login login;

	// object creation for page

	// GoogleSearchPage search = new GoogleSearchPage();

	@BeforeMethod
	public void setup(Method method) throws Exception {
		if (config.getvalue("Safari").equalsIgnoreCase("Yes") || config.getvalue("Edge").contentEquals("Yes")) {
			/*
			 * BrowserStackBase base = new BrowserStackBase(); driver =
			 * base.setUp(config_file, environment, driver, config.getSitURL());
			 * System.out.println(driver);
			 */
		} else {
			driver = null;
			driver = Browserfactory.startapplication(driver, config.getbrowser(), config.getvalue("url"));
		}
		log = report.createTest(method.getName());
		softAssert = new SoftAssert();
		// readexldd = new ReadExcelDatadriven();
		waits = new Waits(driver);
		helper = new Helper(driver);
		map = new HashMap<>();
		BENE = new Eclerx_Comply_W8BENE(driver);
		login = new Eclerx_Comply_Login(driver);
	}

	@BeforeSuite
	public void setupsuit() {

		config = new ConfigReader();
		String nameOS = "os.name";
		String versionOS = "os.version";
		String javaversion = "java.version";
		String username = "user.name";
		nameOS = System.getProperty(nameOS);
		versionOS = System.getProperty(versionOS);
		extent = new ExtentHtmlReporter("./Reports/CP_Automation_" + Helper.getcurrentDateTime() + ".html");
		report = new ExtentReports();
		extent.config().setChartVisibilityOnOpen(true);
		extent.loadXMLConfig(new File(System.getProperty("user.dir") + "./Reports/Report.xml"));
		report.setSystemInfo("Application Name", config.getvalue("ApplicationName"));
		report.setSystemInfo("Application Name", config.getvalue("Environment"));
		report.setSystemInfo("OS Name", nameOS);
		report.setSystemInfo("OS Version", versionOS);
		report.setSystemInfo("Device", config.getvalue("Device"));
		report.setSystemInfo("Browser", config.getbrowser());
		report.setSystemInfo("Java Version", System.getProperty(javaversion));
		report.setSystemInfo("User Name", System.getProperty(username));
		report.attachReporter(extent);
	}

	@AfterMethod
	public void teardoenMethod(ITestResult result) {
		try {
			if (result.getStatus() == ITestResult.FAILURE) {
				helper.captureScreenshot(driver);
				log.fail("Test Fail",
						MediaEntityBuilder.createScreenCaptureFromPath(helper.captureScreenshot(driver)).build());
			} else if (result.getStatus() == ITestResult.SUCCESS) {
				helper.captureScreenshot(driver);
				log.pass("Test Pass",
						MediaEntityBuilder.createScreenCaptureFromPath(helper.captureScreenshot(driver)).build());
			}

		} catch (IOException e) {
			System.out.println("Exception is>>" + e.getMessage());
		}

		Browserfactory.quitbrowser(driver);
		report.flush();

	}

	// ******************************************************************************

	// ********************************************************************************
	public String getSheetname() {
		String sheet = config.getvalue("TestDataSheetName");
		return sheet;
	}

	public void setSheetname(String sheetname) {
		this.sheetname = sheetname;
	}

	@DataProvider(name = "data")
	public Object[][] dataSupplier() throws Exception {
		if (sheet == null) {
			loadfile();
		}
		int lastRowNum = sheet.getLastRowNum();
		int lastCellNum = sheet.getRow(0).getLastCellNum();
		Object[][] obj = new Object[lastRowNum][1];

		for (int i = 0; i < lastRowNum; i++) {
			Map<Object, Object> datamap = new HashMap<>();
			for (int j = 0; j < lastCellNum; j++) {
				datamap.put(sheet.getRow(0).getCell(j).toString(), sheet.getRow(i + 1).getCell(j).toString());
			}
			obj[i][0] = datamap;
		}
		return obj;
	}

	// ***************************************************************
	public void loadfile() throws Exception {
		try {
			File file = new File(config.getvalue("TestDataSheetPath"));
			FileInputStream fis = new FileInputStream(file);
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			sheet = wb.getSheet(config.getvalue("TestDataSheetName"));
			wb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
