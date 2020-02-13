package com.qa.FW.Utility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.FW.TestBase.Base;

public class Waits extends Base {

	public WebDriver driver;
	public static long page_load_timeout = 90;
	public static long implicit_timeout = 90;
	WebDriverWait wait;

	public Waits(WebDriver driver) {
		this.driver = driver;
	}

	// public static WebDriver driver;

	public void waitForElementClickable(WebElement element, int seconds) {
		wait = new WebDriverWait(driver, seconds);
		wait.until(ExpectedConditions.elementToBeClickable(element));

	}

	public Boolean waitTillElementFound(WebElement ElementTobeFound, int seconds) {
		wait = new WebDriverWait(driver, seconds);
		wait.until(ExpectedConditions.visibilityOf(ElementTobeFound));
		return true;
	}

	public Boolean waitTillWindowPresent(int win, int seconds) {
		wait = new WebDriverWait(driver, seconds);
		wait.until(ExpectedConditions.numberOfWindowsToBe(win));
		return true;
	}

	public Boolean waitTillAlertPresent(int seconds) {
		wait = new WebDriverWait(driver, seconds);
		wait.until(ExpectedConditions.alertIsPresent());
		return true;
	}

}
