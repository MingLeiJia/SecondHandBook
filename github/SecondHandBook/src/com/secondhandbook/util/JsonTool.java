package com.secondhandbook.util;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonTool {
	
	public static final String JSON_REQUEST_PARAMS = "json_request_params";
	public static final String JSON_RESULT_CODE = "json_result_code";
	public static final String JSON_BOOK_ARRAY = "json_book_array";

	public static final String STATUS = "status";
	public static final String REASON = "reason";

	public static String createJsonString(String key,Object value){
		
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put(key, value);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject.toString();
	}
}
