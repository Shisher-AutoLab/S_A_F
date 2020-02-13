package com.qa.FW.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.FW.TestBase.Base;

public class Eclerx_Comply_Login extends Base{

	WebDriver driver;

	public Eclerx_Comply_Login(WebDriver driver) {
		this.driver =driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id= "txtClientId")
	private WebElement input_username;
	
	@FindBy(id= "txtClientCode")
	private WebElement input_password;
	
	@FindBy(id= "btnlgn")
	private WebElement btn_login;
	
	public boolean login(String username, String pwd) {
		try {
			helper.sendkeys(input_username, username);
			helper.sendkeys(input_password, pwd);
			helper.clickElement(btn_login);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
