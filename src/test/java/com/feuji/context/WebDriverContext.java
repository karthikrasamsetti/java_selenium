package com.feuji.context;

import org.openqa.selenium.WebDriver;

public class WebDriverContext {

	/** The driverInstance. */
	private static InheritableThreadLocal<WebDriver> driverInstance = new InheritableThreadLocal<>();

	/**
	 * Gets the driver.
	 *
	 * @return the driver
	 */
	public static WebDriver getDriver() {
		if (driverInstance.get() == null)
			throw new IllegalStateException(
					"WebDriver has not been set, Please set WebDriver instance by WebDriverContext.setDriver...");
		else
			return driverInstance.get();
	}

	/**
	 * Sets the driver.
	 *
	 * @param driver the new driver
	 */
	public static void setDriver(WebDriver driver) {
		driverInstance.set(driver);
	}

	/**
	 * Removes the driver.
	 */
	public static void removeDriver() {
		driverInstance.remove();
	}
}
