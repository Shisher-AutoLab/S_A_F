package com.qa.FW.Utility;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.Select;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.qa.FW.TestBase.Base;

public class Helper extends Base {

	WebDriver driver;

	public Helper(WebDriver driver) {
		this.driver = driver;
	}

	// public class logstatus {}

	/**
	 * This method will take the screenshot and attach in extent report
	 * 
	 * @author NPGMO87 - Shisher
	 * @param strMessage
	 * @return
	 */
	public ExtentTest logfail(String strMessage) {
		try {
			return log.fail(strMessage,
					MediaEntityBuilder.createScreenCaptureFromPath(helper.captureScreenshot(driver)).build());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * This method will not take the screenshot, if user want to take the screen
	 * shot change the value "ScreenshotCaptureForPass" to 1 in config file
	 * 
	 * @param strMessage
	 * @return
	 */
	public ExtentTest logpass(String strMessage) {
		if (config.getvalue("ScreenshotCaptureForPass").equals("1")) {
			try {
				return log.pass(strMessage,
						MediaEntityBuilder.createScreenCaptureFromPath(helper.captureScreenshot(driver)).build());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (config.getvalue("ScreenshotCaptureForPass").equals("0")) {
			return log.pass(strMessage);
		} else {
			return log.info("No value appear in config file to take the screen shot");
		}
		return null;

	}

	/**
	 * This will capture the screen shott and return the path of the destination
	 * 
	 * @param driver
	 * @return
	 */

	public String captureScreenshot(WebDriver driver) {
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String screenshotpath = System.getProperty("user.dir") + "/Reports/Screenshots/CP_" + getcurrentDateTime()
				+ ".png";
		try {
			FileHandler.copy(src, new File(screenshotpath));
		} catch (IOException e) {
			System.out.println("Unable to capturenscreen shot>>" + e.getMessage());
		}
		return screenshotpath;
	}

	/**
	 * Accept the alert of the page
	 * 
	 * @return
	 */
	public boolean checkAlert_Accept() {
		try {
			waits.waitTillAlertPresent(20);
			Alert a = driver.switchTo().alert();
			String str = a.getText();
			System.out.println(str);
			a.accept();
			return true;
		} catch (Exception e) {
			System.out.println("No alert found");
			return false;

		}
	}

	/**
	 * Dismiss the alert of the page
	 * 
	 * @return
	 */
	public boolean checkAlert_Dismiss() {
		try {
			waits.waitTillAlertPresent(20);
			Alert a = driver.switchTo().alert();
			String str = a.getText();
			System.out.println(str);
			a.dismiss();
			return true;
		} catch (Exception e) {
			System.out.println("No alert found ");
			return false;

		}
	}

	/**
	 * This method will check the check box
	 * 
	 * @param checkbox
	 */
	public void checkbox_Checking(WebElement checkbox) {
		boolean checkstatus;
		waits.waitTillElementFound(checkbox, 20);
		helper.highlightelement(checkbox);
		checkstatus = checkbox.isSelected();
		if (checkstatus == true) {
			System.out.println("Checkbox is already checked");
		} else {
			checkbox.click();
			System.out.println("Checked the checkbox");
		}
	}

	/**
	 * This method will uncheck the check box
	 * 
	 * @param checkbox
	 */
	public void checkbox_Unchecking(WebElement checkbox) {
		boolean checkstatus;
		waits.waitTillElementFound(checkbox, 20);
		helper.highlightelement(checkbox);
		checkstatus = checkbox.isSelected();
		if (checkstatus == true) {
			checkbox.click();
			System.out.println("Checkbox is unchecked");
		} else {
			System.out.println("Checkbox is already unchecked");
		}
	}

	/**
	 * This method will clear the text field
	 * 
	 * @param element
	 */
	public void clearTextField(WebElement element) {
		try {
			waits.waitTillElementFound(element, 20);
			helper.highlightelement(element);
			element.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method will select the element from the list
	 * 
	 * @param xpathOfElement
	 * @param valueToSelect
	 */
	public void clickCheckboxFromList(String xpathOfElement, String valueToSelect) {
		List<WebElement> lst = driver.findElements(By.xpath(xpathOfElement));
		for (int i = 0; i < lst.size(); i++) {
			List<WebElement> dr = lst.get(i).findElements(By.tagName("label"));
			for (WebElement f : dr) {
				System.out.println("value in the list : " + f.getText());
				if (valueToSelect.equals(f.getText())) {
					f.click();
					break;
				}
			}
		}
	}

	/*
	 * This method will click on webelement // Author - Shisher public void
	 * clickWebelement(WebElement element) { try { boolean elementIsClickable =
	 * element.isEnabled(); while (elementIsClickable) { element.click(); }
	 * 
	 * } catch (Exception e) { System.out.println("Element is not enabled" +
	 * e.getMessage()); } }
	 */
	// this method will perform the drag and drop of the element (way 1)
	public void dragAndDrop(WebElement fromWebElement, WebElement toWebElement) {
		try {
			waits.waitTillElementFound(fromWebElement, 30);
			Actions builder = new Actions(driver);
			builder.dragAndDrop(fromWebElement, toWebElement);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method will perform the drag and drop of the element (way 2)
	 * 
	 * @param fromWebElement
	 * @param toWebElement
	 */
	public void dragAndDrop_Method2(WebElement fromWebElement, WebElement toWebElement) {
		Actions builder = new Actions(driver);
		Action dragAndDrop = builder.clickAndHold(fromWebElement).moveToElement(toWebElement).release(toWebElement)
				.build();
		dragAndDrop.perform();
	}

	/**
	 * this method will perform the drag and drop of the element (way 3)
	 * 
	 * @param fromWebElement
	 *            Enter the from Element
	 * @param toWebElement
	 *            Enter the to element
	 * @throws InterruptedException
	 */
	public void dragAndDrop_Method3(WebElement fromWebElement, WebElement toWebElement) throws InterruptedException {
		Actions builder = new Actions(driver);
		builder.clickAndHold(fromWebElement).moveToElement(toWebElement).perform();
		Thread.sleep(2000);
		builder.release(toWebElement).build().perform();
	}

	/**
	 * This will return the current date in format
	 * 
	 * @return date in format MM_dd_yyyy_HH_mm_ss
	 */
	// Author - Shisher
	public static String getcurrentDateTime() {
		DateFormat format = new SimpleDateFormat("MM_dd_yyyy_HH_mm_ss");
		Date currentdate = new Date();
		return format.format(currentdate);
	}

	/**
	 * This method will highlight webelement
	 * 
	 * @param element-
	 *            Enter WebElement
	 */
	public void highlightelement(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
				"color: solid red; border: 2px solid red;");
		js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
				"color: solid red; border: 2px solid green;");
	}

	/**
	 * This method will click on the element using JS executor.
	 * 
	 * @param element
	 *            - - Enter WebElement
	 */
	public void javaexecutorclick(WebElement element) {
		try {
			highlightelement(element);
			// WebElement element = driver.findElement(By.id("app-select-no"));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method will send the value to the drop down using JS executor.
	 * 
	 * @param element
	 *            - Enter WebElement
	 * @param value
	 *            - Enter the value
	 */
	public void javaexecutorsendkeys(WebElement element, String value) {
		try {
			highlightelement(element);
			// WebElement element = driver.findElement(By.id("app-select-no"));
			((JavascriptExecutor) driver).executeScript("arguments[0].value='" + value + "';", element);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method will move to new tab
	 * 
	 * @param element-
	 *            Enter WebElement
	 */
	// Author - Shisher
	public void moveToTab(WebElement element) {
		highlightelement(element);
		element.sendKeys(Keys.chord(Keys.CONTROL, Keys.TAB));
	}

	/**
	 * This method will perform back navigation of browser
	 */
	public void navigate_back() {
		driver.navigate().back();
	}

	/**
	 * This method will perform forward navigation of browser
	 */
	public void navigate_forward() {
		driver.navigate().forward();
	}

	/**
	 * return page title
	 * 
	 * @return page title
	 */
	public String pagetitle() {
		return driver.getTitle();
	}

	/**
	 * This will generate the dynamic xpath with contains vale.
	 * 
	 * @param xpathValue
	 * @param tag
	 * @return
	 */
	// Author - Shisher
	public WebElement prepareWebElementWithDynamicXpath(String xpathValue, String tag) {
		return driver.findElement(By.xpath("//" + tag + "[contains(text(),'" + xpathValue + "')]"));
	}

	/**
	 * this method will press down key of the keyboard
	 * 
	 * @param element-
	 *            Enter WebElement
	 */

	public void pressKeyDown(WebElement element) {
		highlightelement(element);
		element.sendKeys(Keys.DOWN);
	}

	/**
	 * This method will press the key tab
	 * 
	 * @param element-
	 *            Enter WebElement
	 */

	public void pressKeytab(WebElement element) {
		highlightelement(element);
		element.sendKeys(Keys.TAB);
	}

	/**
	 * This method will press UP key of the keyboard
	 * 
	 * @param element-
	 *            Enter WebElement
	 */
	public void pressKeyUp(WebElement element) {
		highlightelement(element);
		element.sendKeys(Keys.UP);
	}

	/**
	 * This method will deselect the radio button
	 * 
	 * @param Radio
	 *            - Enter WebElement
	 */
	public void radioButton_Deselect(WebElement Radio) {
		waits.waitTillElementFound(Radio, 30);
		highlightelement(Radio);
		boolean checkstatus;
		checkstatus = Radio.isSelected();
		if (checkstatus == true) {
			Radio.click();
			System.out.println("Radio Button is deselected");
		} else {
			System.out.println("Radio Button was already Deselected");
		}
	}

	/**
	 * This method will check the radio button
	 * 
	 * @param Radio
	 *            - - Enter WebElement
	 */
	public void radiobutton_Select(WebElement Radio) {
		boolean checkstatus;
		waits.waitTillElementFound(Radio, 30);
		highlightelement(Radio);
		checkstatus = Radio.isSelected();
		if (checkstatus == true) {
			System.out.println("RadioButton is already checked");
		} else {
			Radio.click();
			System.out.println("Selected the Radiobutton");
		}
	}

	/**
	 * This method will refresh the page
	 */

	public void refresh() {
		driver.navigate().refresh();
	}

	/**
	 * THis method will scroll the page to the element present
	 * 
	 * @param element
	 *            - Enter WebElement
	 */
	public void scrolltoElement(WebElement element) {
		highlightelement(element);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
	}

	/**
	 * This function will upload the file using robot class
	 * 
	 * @param strdocumentpath
	 *            - Enter the path to get the document
	 */
	public void selectdocument(String strdocumentpath) {
		try {
			Robot robot = new Robot();
			StringSelection stringsel = new StringSelection(strdocumentpath);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringsel, null);
			robot.setAutoDelay(2000);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.setAutoDelay(2000);
			robot.keyPress(KeyEvent.VK_V);
			robot.setAutoDelay(2000);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_V);
			robot.setAutoDelay(2000);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		} catch (Exception e) {

		}
	}

	/**
	 * Select the value from the drop down based on index
	 * 
	 * @param element
	 *            - Enter WebElement
	 * @param index
	 *            - Enter index value which user wants to select from drop down
	 */
	public void selectElementByIndexMethod(WebElement element, int index) {
		try {
			waits.waitTillElementFound(element, 30);
			highlightelement(element);
			Select selectitem = new Select(element);
			selectitem.selectByIndex(index);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Select the value from the drop down based on name
	 * 
	 * @param element
	 *            - Enter WebElement
	 * @param Name
	 *            - Enter name which user wants to select from drop down
	 */
	public void selectElementByNameMethod(WebElement element, String Name) {
		try {
			waits.waitTillElementFound(element, 30);
			highlightelement(element);
			Select selectitem = new Select(element);
			selectitem.selectByVisibleText(Name);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Select the value from the drop down based on value
	 * 
	 * @param element
	 *            - Enter WebElement
	 * @param value
	 *            - Enter value which user wants to select from drop down
	 */
	public void selectElementByValueMethod(WebElement element, String value) {
		try {
			highlightelement(element);
			Select selectitem = new Select(element);
			selectitem.selectByValue(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * This function will click on the element
	 */
	public void clickElement(WebElement element) {
		waits.waitTillElementFound(element, 30);
		highlightelement(element);
		element.click();

	}

	/*
	 * This function will send the values to the text box
	 */
	public void sendkeys(WebElement element, String values) {
		try {
			waits.waitTillElementFound(element, 30);
			highlightelement(element);
			element.sendKeys(values);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * switch to next window
	 */
	public void switchNewWindow(String parent_child) {
		try {
			Set<String> windows = driver.getWindowHandles();
			Iterator<String> itr = windows.iterator();
			String patName = itr.next();
			if (parent_child.equalsIgnoreCase("child")) {
				String chldName = itr.next();
				Thread.sleep(1000);
				driver.switchTo().window(chldName);
				System.out.println("Switch to child window");
			}
			if (parent_child.equalsIgnoreCase("parent")) {
				Thread.sleep(1000);
				driver.switchTo().window(patName);
				System.out.println("Switch to parent window");
				// Helper.logstatus.logpass("Switch to Parent window" + driver.getTitle());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * switch to previous window
	 */
	public void switchOld() {
		try {
			String win = driver.getWindowHandle();
			driver.switchTo().window(win);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// this method will press enter key of the keyboard
	// Author - Shisher
	public void pressKeyEnter(WebElement element) {
		try {
			waits.waitTillElementFound(element, 30);
			highlightelement(element);
			element.sendKeys(Keys.ENTER);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method will get the text and return it as a string.
	 */
	public String getText(WebElement element) {
		try {
			waits.waitTillElementFound(element, 30);
			highlightelement(element);
			return element.getText();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public boolean isDisplayed(WebElement ele) {
		try {
			if (ele.isDisplayed()) {
				helper.highlightelement(ele);
				// return true;
			}
			return true;
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			return false;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
}
