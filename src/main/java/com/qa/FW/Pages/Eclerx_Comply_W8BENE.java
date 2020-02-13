package com.qa.FW.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.FW.TestBase.Base;


public class Eclerx_Comply_W8BENE extends Base{
	
	WebDriver driver;
	public Eclerx_Comply_W8BENE(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	//***********Account holder page element
	
	@FindBy(id = "liEntity")
	private WebElement btn_Entity;
	
	@FindBy(xpath = "(//button[@class = 'btn btn-link collapsed'])[1]")
	private WebElement link_BasicDetails;
	
	@FindBy(xpath = "(//label[contains(text(),'')])[3]")
	private WebElement radio_NonUS;
	
	@FindBy(id = "txtBusiness_Name")
	private WebElement inpt_busnisName;
	
	@FindBy(xpath = "(//button[@class = 'btn btn-link collapsed'])[4]")
	private WebElement link_PRA;
	
	@FindBy(id = "ctl00_ContentPlaceHolder1_txtPermanentAddress_Line1")
	private WebElement inpt_PRALine1;
	
	@FindBy(id = "ctl00_ContentPlaceHolder1_txtPermanentAddress_City_Town")
	private WebElement inpt_PRACity;
	
	@FindBy(id = "ctl00_ContentPlaceHolder1_txtPermanentAddress_Zip_PostalCode")
	private WebElement inpt_PRAZIP;
	
	@FindBy(id = "ctl00_ContentPlaceHolder1_lstPermanentAddress_Country")
	private WebElement inpt_PRACountry;
	
	@FindBy(xpath = "(//button[@class = 'btn btn-link collapsed'])[5]")
	private WebElement link_ContactDetails;
	
	@FindBy(id = "ctl00_ContentPlaceHolder1_txtProxy_FirstName")
	private WebElement inpt_CDFirstName;
	
	@FindBy(id = "ctl00_ContentPlaceHolder1_txtProxy_LastName")
	private WebElement inpt_CDLastName;
	
	@FindBy(id = "ctl00_ContentPlaceHolder1_txtEmail")
	private WebElement inpt_CDEmail;
	
	@FindBy(xpath = "//label[@for = 'chkOnboardingAgree']")
	private WebElement chbx_Iagree;
	
	@FindBy(id = "ctl00_ContentPlaceHolder1_btncontinue")
	private WebElement btn_Continue;
	
	
	
	
	

}
