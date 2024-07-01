package com.feuji.context;

public class Constants {
	
	/** The Constant WORKING_DIRECTORY. */
	public static final String WORKING_DIRECTORY = System.getProperty("user.dir");
	
	/** The Constant REPORT_DIRECTORY. */
	public final static String REPORT_DIRECTORY = WORKING_DIRECTORY + "/reports/ExtentReport.html";
	
	/** The Constant PROPERTY_FILE_PATH. */
	public final static String BASE_PROPERTY_PATH = WORKING_DIRECTORY + "/src/test/resources/config/base.properties";

	public static final String JSON_SCHEMA = WORKING_DIRECTORY + "/data/expectedJSONSchema.json";

	public static final String TOKEN_REQUEST_BODY = WORKING_DIRECTORY + "/data/tokenRequestBody.json";

	public static final String UI_JSON_DATA = WORKING_DIRECTORY + "/data/data.json";
}
