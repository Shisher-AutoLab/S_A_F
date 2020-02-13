package com.qa.FW.Utility;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.qa.FW.TestBase.Base;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Browserfactory extends Base {

	public static WebDriver startapplication(WebDriver driver, String browser, String URL) {

		if (browser.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			//System.setProperty("webdriver.chrome.driver", "./Drivers//chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("FF")) {
			WebDriverManager.firefoxdriver().setup();
			//System.setProperty("webdriver.gecko.driver", "./Drivers//geckodriver.exe");
			driver = new FirefoxDriver();
		} else if (browser.equalsIgnoreCase("IE")) {
			WebDriverManager.iedriver().setup();
			/*System.setProperty("webdriver.ie.driver", "./Drivers//IEDriverServer.exe");
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			capabilities.setCapability("requireWindowFocus", true);*/
			driver = new InternetExplorerDriver();
		}
		e_event = new EventFiringWebDriver(driver);
		e_Listener = new WebEventListner();
		e_event.register(e_Listener);
		driver = e_event;

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(Waits.page_load_timeout, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(Waits.implicit_timeout, TimeUnit.SECONDS);

		driver.get(URL);
		return driver;

	}

	public static void quitbrowser(WebDriver driver) {
		driver.quit();
	}

}
