package com.feuji.test;

import com.aventstack.extentreports.Status;
import com.feuji.page.SauceCheckoutPage;
import com.feuji.page.SauceHomePage;
import org.json.JSONArray;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.feuji.base.Base;
import com.feuji.factory.PageInstanceFactory;
import com.feuji.page.SauceLoginPage;

/**
 * This class contains test methods to validate various functionalities of the Sauce application.
 * It utilizes TestNG annotations for test execution and parameterization.
 */
public class SauceTest extends Base {

	/**
	 * Test method to validate the login functionality with valid user credentials.
	 * @param browser The browser on which the test should run.
	 */
	@Test(testName = "Login Test", description = "User should login with valid user credentials", groups = { "chrome",
			"edge" })
	@Parameters("browser")
	public void sauceLoginTest(String browser) {
		SauceLoginPage loginPage = PageInstanceFactory.getInstance(SauceLoginPage.class);
		assert loginPage != null;
		String actualHeading = loginPage.getLoginHeading();
		String expectedHeading = uiData.getJSONObject("assertionData").getString("loginHeading");
		String userName = uiData.getJSONObject("loginData").getString("user");
		String password = uiData.getJSONObject("loginData").getString("password");
		String expectedUrl = uiData.getJSONObject("assertionData").getString("homeURL");
		extentTest.get().info("Validate the Login Page heading");
		softAssert.assertEquals(actualHeading, expectedHeading);
		loginPage.logIn(userName, password);
		System.err.println(Thread.currentThread()+"  "+ softAssert);
		extentTest.get().info("Validate the Home Page URL");
		softAssert.assertEquals(loginPage.getUrl(), expectedUrl);
		softAssert.assertAll();
	}

	/**
	 * Test method to validate the login functionality with locked user credentials.
	 * @param browser The browser on which the test should run.
	 */
	@Test(testName = "Login Test for locked user", description = "User should login with Locked user credentials", groups = {
			"edge", "chrome" })
	@Parameters("browser")
	public void sauceLoginTestNegative(String browser) {
		SauceLoginPage loginPage = PageInstanceFactory.getInstance(SauceLoginPage.class);
		assert loginPage != null;
		String actualHeading = loginPage.getLoginHeading();
		String expectedHeading = uiData.getJSONObject("assertionData").getString("loginHeading");
		String userName = uiData.getJSONObject("loginData").getString("lockeduser");
		String password = uiData.getJSONObject("loginData").getString("password");
		extentTest.get().info("Validate the Login Page heading");
		softAssert.assertEquals(actualHeading, expectedHeading);
		loginPage.logIn(userName, password);
		String loginError = uiData.getJSONObject("assertionData").getString("loginError");
		String errMsg = loginPage.getErrMsg();
		extentTest.get().info("Validate the login error message ");
		softAssert.assertTrue(errMsg.contains(loginError));
		softAssert.assertAll();
	}

	/**
	 * Test method to validate the filter functionality on the Sauce home page.
	 * @param browser The browser on which the test should run.
	 */
	@Test(testName = "Filter check", description = "checking whether filter is working", groups = { "chrome", "edge" })
	@Parameters("browser")
	public void sauceFilterTest(String browser) {
		SauceHomePage sauceHomePage = PageInstanceFactory.getInstance(SauceHomePage.class);
		assert sauceHomePage != null;
		extentTest.get().log(Status.INFO, "Login with valid credentials");
		String userName = uiData.getJSONObject("loginData").getString("user");
		String password = uiData.getJSONObject("loginData").getString("password");
		sauceHomePage.logIn(userName, password);
		extentTest.get().log(Status.INFO, "user successfully logged in");
		sauceHomePage.filterProducts();
		softAssert.assertTrue(sauceHomePage.getPriceInt1() < sauceHomePage.getPriceInt2());
		sauceHomePage.logOut();
		softAssert.assertAll();
	}

	/**
	 * Test method to validate adding products to the cart and checking the cart count.
	 * @param browser The browser on which the test should run.
	 */
	@Test(testName = "Add to Cart check", description = "checking whether products are added to cart", groups = {
			"chrome", "edge" })
	@Parameters("browser")
	public void sauceAddToCartTest(String browser) {
		SauceHomePage sauceHomePage = PageInstanceFactory.getInstance(SauceHomePage.class);
		assert sauceHomePage != null;
		extentTest.get().log(Status.INFO, "Login with valid credentials");
		String userName = uiData.getJSONObject("loginData").getString("user");
		String password = uiData.getJSONObject("loginData").getString("password");
		sauceHomePage.logIn(userName, password);
		extentTest.get().log(Status.INFO, "user successfully logged in");
		sauceHomePage.addToCart();
		softAssert.assertEquals(sauceHomePage.getCountInt(), 2);
		sauceHomePage.logOut();
		softAssert.assertAll();
	}

	/**
	 * Test method to validate the removal of products from the cart.
	 * @param browser The browser on which the test should run.
	 */
	@Test(testName = "Product Removing", description = "User can remove the product from cart", groups = { "chrome",
			"edge" })
	@Parameters("browser")
	public void removeProductsFromCart(String browser) {
		SauceCheckoutPage sauceCheckoutPage = PageInstanceFactory.getInstance(SauceCheckoutPage.class);
		assert sauceCheckoutPage != null;
		String userName = uiData.getJSONObject("loginData").getString("user");
		String password = uiData.getJSONObject("loginData").getString("password");
		sauceCheckoutPage.logIn(userName, password);
		extentTest.get().log(Status.INFO, "Login with valid credentials");
		sauceCheckoutPage.removeProductFromCart();
		extentTest.get().log(Status.INFO, "Product is removed from the Cart");
		softAssert.assertNotEquals(sauceCheckoutPage.getInitialCount(), sauceCheckoutPage.getFinalCount(),
				"Initial count should not be equal to final count");
		extentTest.get().log(Status.INFO, "Checking that the initial count and the final count of Cart products are not the same.");
		sauceCheckoutPage.logOut();
		softAssert.assertAll();
	}

	/**
	 * Test method to validate the checkout process for products in the cart.
	 * @param browser The browser on which the test should run.
	 */
	@Test(testName = "Checkout Products Test", description = "Checkout the products which are added to the cart", groups = {
			"chrome", "edge" })
	@Parameters("browser")
	public void sauceCheckoutProductsTest(String browser) {
		SauceCheckoutPage checkoutPage = PageInstanceFactory.getInstance(SauceCheckoutPage.class);
		extentTest.get().log(Status.INFO, "Login with valid credentials");
		assert checkoutPage != null;
		String userName = uiData.getJSONObject("loginData").getString("user");
		String password = uiData.getJSONObject("loginData").getString("password");
		checkoutPage.logIn(userName, password);
		String firstname = uiData.getString("firstname");
		String lastname = uiData.getString("lastname");
		String pincode = uiData.getString("pincode");
		checkoutPage.checkoutProduct(firstname, lastname, pincode);
		boolean isTextVisible = checkoutPage.isHeaderTextVisible();
		extentTest.get().log(isTextVisible ? Status.PASS : Status.FAIL,
				isTextVisible ? "Header text is visible" : "Header Text is not visible");
		softAssert.assertTrue(isTextVisible);
		extentTest.get().log(Status.INFO, "Checking the text is equal or not");
		String actualText = checkoutPage.getHeaderText();
		String expectedText = uiData.getJSONObject("assertionData").getString("expectedText");
		softAssert.assertEquals(actualText, expectedText);
		checkoutPage.clickOnBackToHomeBtn();
		checkoutPage.logOut();
		softAssert.assertAll();
	}

	/**
	 * Test method to validate the presence of elements in the sidebar of the Sauce application.
	 * @param browser The browser on which the test should run.
	 * @throws Exception If any error occurs during the test execution.
	 */
	@Test(testName = "Side Bar Validation", description = "Check whether the elements are present in side bar or not", groups = {
			"chrome", "edge" })
	@Parameters("browser")
	public void sauceSideBarValidationTest(String browser) throws Exception {
		SauceHomePage sauceHomePage = PageInstanceFactory.getInstance(SauceHomePage.class);
		extentTest.get().log(Status.INFO, "Login with valid credentials");
		assert sauceHomePage != null;
		String userName = uiData.getJSONObject("loginData").getString("user");
		String password = uiData.getJSONObject("loginData").getString("password");
		sauceHomePage.logIn(userName, password);
		JSONArray webElements = uiData.getJSONArray("sidebar");
		sauceHomePage.clickOnSideBarBtn();
		Thread.sleep(3000);
		for (Object webElement : webElements) {
			softAssert.assertTrue(sauceHomePage.sideBarValidation(webElement.toString()));
		}
		sauceHomePage.clickOnCloseBtn();
		sauceHomePage.logOut();
		softAssert.assertAll();
	}

	/**
	 * Test method to validate the footer elements of the Sauce application.
	 * @param browser The browser on which the test should run.
	 */
	@Test(testName = "Footer Test", description = "Validating elements in footer", groups = { "chrome", "edge" })
	@Parameters("browser")
	public void testFooterValidation(String browser) {
		SauceHomePage homePage = PageInstanceFactory.getInstance(SauceHomePage.class);
		assert homePage != null;
		String userName = uiData.getJSONObject("loginData").getString("user");
		String password = uiData.getJSONObject("loginData").getString("password");
		homePage.logIn(userName, password);
		homePage.getTwitterIcon().isDisplayed();
		extentTest.get().info("validate footer icon");
		String actualFooterText = homePage.getFooterText();
		String expectedFooterText = uiData.getJSONObject("assertionData").getString("footerText");
		softAssert.assertEquals(actualFooterText, expectedFooterText);
		extentTest.get().info("validate footer text");
		homePage.logOut();
		softAssert.assertAll();
	}

	/**
	 * Test method to validate the LinkedIn tab in the footer of the Sauce application.
	 * @param browser The browser on which the test should run.
	 */
	@Test(testName = "Footer LinkedIn tab Test", description = "Validating LinkedIn tab in footer", groups = { "chrome",
			"edge" })
	@Parameters("browser")
	public void testValidateLinkedInTab(String browser) {
		SauceHomePage homePage = PageInstanceFactory.getInstance(SauceHomePage.class);
		assert homePage != null;
		String userName = uiData.getJSONObject("loginData").getString("user");
		String password = uiData.getJSONObject("loginData").getString("password");
		homePage.logIn(userName, password);
		homePage.clickLinkedIn();
		extentTest.get().info("validate LinkedIn navigation in new tab");
		String actualUrl = driver.getCurrentUrl();
		System.out.println(actualUrl);
		String expectedUrl = uiData.getJSONObject("assertionData").getString("linkedInUrl");
		softAssert.assertEquals(actualUrl, expectedUrl);
		homePage.logOut();
		softAssert.assertAll();
	}

	/**
	 * Test method to validate the logout functionality of the Sauce application.
	 * @param browser The browser on which the test should run.
	 */
	@Test(testName = "Logout Test", description = "User can logout from the application", groups = { "chrome", "edge" })
	@Parameters("browser")
	public void testLogout(String browser) {
		SauceHomePage sauceHomePage = PageInstanceFactory.getInstance(SauceHomePage.class);
		assert sauceHomePage != null;
		String userName = uiData.getJSONObject("loginData").getString("user");
		String password = uiData.getJSONObject("loginData").getString("password");
		sauceHomePage.logIn(userName, password);
		extentTest.get().info("User can login with valid credentials");
		sauceHomePage.logOut();
		extentTest.get().info("Logingout from the application");
		softAssert.assertAll();
	}
}