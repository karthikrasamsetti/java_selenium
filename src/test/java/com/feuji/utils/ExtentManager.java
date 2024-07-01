package com.feuji.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

/**
 * The ExtentManager class is responsible for managing the ExtentReports instance for generating test reports.
 * It follows a singleton pattern to ensure there is only one instance of ExtentReports throughout the application.
 */
public class ExtentManager {
    private static ExtentReports extent;

    /**
     * Returns the singleton instance of ExtentReports.
     * If the instance does not exist, it creates a new one and configures it with necessary settings.
     * @return The singleton instance of ExtentReports
     */
    public static ExtentReports getInstance() {
        if (extent == null) {
            extent = new ExtentReports();
            ExtentSparkReporter spark = new ExtentSparkReporter("./reports/Extentreport.html");
            extent.attachReporter(spark);
            spark.config().setTheme(Theme.DARK);
            spark.config().setDocumentTitle("MyReport");
            spark.config().setReportName("Test Report");
            spark.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
        }
        return extent;
    }
}