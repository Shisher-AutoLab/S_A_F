package com.qa.FW.test.Pages;

import org.testng.annotations.Test;

import com.qa.FW.TestBase.Base;

public class BENE extends Base {

	public BENE() {
		super();
	}
//
	@Test
	public void validateLogin() {
		login.login("Test", "Welcome");
	}
}
