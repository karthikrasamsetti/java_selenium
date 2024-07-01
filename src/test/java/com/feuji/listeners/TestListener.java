
package com.feuji.listeners;

import java.util.Arrays;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.feuji.base.Base;
import com.feuji.context.Constants;
import com.feuji.context.WebDriverContext;
import com.feuji.utils.ExtentManager;
import com.feuji.utils.GetProperties;
import com.feuji.utils.LoggerUtil;

/**
 * TestListener class implements ITestListener interface and provides methods to
 * listen to test events such as test start, success, failure, skip, and finish.
 * It also integrates with ExtentReports for test reporting and captures
 * screenshots on test failure.
 */
public class TestListener extends Base implements ITestListener {

	ExtentReports extent = ExtentManager.getInstance();
	ExtentTest test;

	/**
	 * Method to get the name of the test from the ITestResult object.
	 *
	 * @param result The ITestResult object.
	 * @return The name of the test.
	 */

	public String getTestName(ITestResult result) {
		return result.getTestName() != null ? result.getTestName()
				: result.getMethod().getConstructorOrMethod().getName();
	}

	@Override
	public void onTestStart(ITestResult result) {
		LoggerUtil.log(getTestName(result) + ": Test started");
		String browser = null;
		Object[] parameters = result.getParameters();
		for (Object parameter : parameters) {
			if (parameter.toString().equals("edge")) {
				browser = parameter.toString();
				break;
			} else if (parameter.toString().equals("chrome")) {
				browser = parameter.toString();
				break;
			}
		}
		if (browser != null) {
			test = extent.createTest(result.getMethod().getMethodName()).assignCategory(browser);
		} else {
			test = extent.createTest(result.getMethod().getMethodName());
			List<String> list = Arrays.asList(result.getMethod().getGroups());
			test.assignCategory(list.get(0));
		}
		extentTest.set(test);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		LoggerUtil.log(getTestName(result) + " : Test Passed");
		extentTest.get().log(Status.PASS, "Test Case Passed Sucessfully");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		Throwable throwable = result.getThrowable();
		String cause = "";
		if (throwable != null)
			cause = throwable.getMessage();
		LoggerUtil.getLogger().fatal(getTestName(result) + " : Test Failed : " + cause);
		extentTest.get().log(Status.FAIL, throwable);
		if (!Arrays.asList(result.getMethod().getGroups()).contains("APIAutomation")) {
			extentTest.get()
					.addScreenCaptureFromBase64String(captureScreenshot(result.getMethod().getMethodName(),
							Constants.WORKING_DIRECTORY + GetProperties.getProperty("imagesPath"),
							WebDriverContext.getDriver()), result.getMethod().getMethodName());
		}
		extentTest.get().log(Status.FAIL, "Test Case Failed");
		extentTest.get().log(Status.FAIL,
				MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		LoggerUtil.log(getTestName(result) + " : Test Skipped");
		extentTest.get().log(Status.SKIP, result.getThrowable());
	}

	@Override
	public void onFinish(ITestContext context) {
		LoggerUtil.log("Test Finished");
		extent.flush();
	}

	@Override
	public void onStart(ITestContext context) {
		GetProperties.loadAllProperties();
	}

}
