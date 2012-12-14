package com.example.demo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TwitterHelper {
	private static final String SEARCH_URL = "http://search.twitter.com/search.json?q=";

	public static class Tweet {
		public String tweet;
		public String from;
		public Tweet(String tweet, String from) {
			this.tweet = tweet;
			this.from = from;
		}
	}
	
	public static List<Tweet> performSearch(String q) throws Exception {
		URLConnection connection = makeQueryURL(q).openConnection();
		InputStream inputStream = connection.getInputStream();
		JSONObject responseObject = new JSONObject(convertStreamToString(inputStream));
		JSONArray searchResult = responseObject.getJSONArray("results");
		if (searchResult!=null) {
			return createTweetsList(searchResult);
		} else {
			return null;
		}

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
	
	private static List<Tweet> createTweetsList(JSONArray searchResult) throws JSONException {
		List<Tweet> tweets = new ArrayList<Tweet>();
		for (int i =0; i<searchResult.length(); i++) {
			JSONObject tweet = searchResult.getJSONObject(i);
			Tweet tweetObject = new Tweet(tweet.getString("text"), tweet.getString("from_user"));
			tweets.add(tweetObject);
		}
		return tweets;
	}

}
