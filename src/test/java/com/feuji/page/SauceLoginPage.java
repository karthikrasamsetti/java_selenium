package com.feuji.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SauceLoginPage extends BasePage{

	@FindBy(id = "user-name")
	private WebElement userNameEle;

	@FindBy(id = "password")
	private WebElement passwordEle;
	
	@FindBy(id = "login-button")
	private WebElement loginBtnEle;
	
	@FindBy(xpath = "//div[@class='login_logo']")
	private WebElement loginHeadingEle;

	@FindBy(xpath = "//input[@id='login-button']//preceding-sibling::div[@class='error-message-container error']/h3")
	private WebElement errMsgEle;
	
	public SauceLoginPage(WebDriver driver) {
		super(driver);
	}
	
	public String getLoginHeading() {
		String heading = loginHeadingEle.getText();
		return heading;
	}
	
	public String getErrMsg() {
		String err = errMsgEle.getText();
		return err;
	}

	public void logIn(String userName, String password) {
		userNameEle.sendKeys(userName);
		passwordEle.sendKeys(password);
		loginBtnEle.click();
	}
	
	public String  getUrl() {
		return driver.getCurrentUrl();
	}
}
