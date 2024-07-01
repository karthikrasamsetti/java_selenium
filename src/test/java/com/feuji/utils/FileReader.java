package com.feuji.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

public class FileReader {

	public static JSONObject getData(String filePath) {
		JSONObject jsonObject = null;
		try {
			System.out.println(filePath);
			String data = FileUtils.readFileToString(new File(filePath), "UTF-8");
			jsonObject = new JSONObject(data);
		} catch (IOException e) {
		}
		return jsonObject;
	}
}
