package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText searchField;
	private Button searchButton;
	private ListView tweetsList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		searchField = (EditText) findViewById(R.id.search_text);
		searchButton = (Button) findViewById(R.id.search_button);
		tweetsList = (ListView) findViewById(R.id.tweets_list);
		
		searchButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String query = searchField.getText().toString();
				if (!query.isEmpty()) {
					searchTwitter(query);
				}
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	private void searchTwitter(String query) {
		new TwitterSearch().execute(query);
	}
	
	private class TwitterSearch extends AsyncTask<String, Void, List<String>> {
		private Exception exception = null;

		@Override
		protected List<String> doInBackground(String... query) {
			String q = query[0];
			try {
				JSONArray searchResult = TwitterHelper.performSearch(q);
				if (searchResult!=null) {
					return createTweetsList(searchResult);
				}
			} catch (Exception e) {
				exception = e;
			}
			return null;
		}
		
		@Override 
		protected void onPostExecute(List<String> tweets) {
			if (exception!=null) {
				Toast.makeText(getApplicationContext(), "Searching failed: "+exception.getMessage(), Toast.LENGTH_SHORT).show();
			} else if (tweets!=null) {
				String[] arrayTweets = tweets.toArray(new String[1]);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, arrayTweets);
				tweetsList.setAdapter(adapter);
			}
		}

		private List<String> createTweetsList(JSONArray searchResult) throws JSONException {
			List<String> tweets = new ArrayList<String>();
			for (int i =0; i<searchResult.length(); i++) {
				JSONObject tweet = searchResult.getJSONObject(i);
				tweets.add(tweet.getString("text"));
			}
			return tweets;
		}

	}

}
