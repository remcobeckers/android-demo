package com.example.demo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONObject;

public class TwitterHelper {
	private static final String SEARCH_URL = "http://search.twitter.com/search.json?q=";

	public static JSONArray performSearch(String q) throws Exception {
		URLConnection connection = makeQueryURL(q).openConnection();
		InputStream inputStream = connection.getInputStream();
		JSONObject responseObject = new JSONObject(convertStreamToString(inputStream));
		return responseObject.getJSONArray("results");
	}

	public static URL makeQueryURL(String query) throws UnsupportedEncodingException, MalformedURLException {
		String encodedQuery = URLEncoder.encode(query, "UTF-8");
		return new URL(SEARCH_URL+encodedQuery);
	}
	
	public static String convertStreamToString(InputStream is) throws Exception {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();
	    String line = null;
	    while ((line = reader.readLine()) != null) {
	        sb.append(line);
	    }
	    is.close();

	    return sb.toString();
	}
}
