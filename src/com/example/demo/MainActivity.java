package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.demo.TwitterHelper.Tweet;

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
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_settings : Intent settings = new Intent(this, SettingsActivity.class);
									  startActivity(settings);
									  return true;
		    default : return false;
		}
	}

	private void searchTwitter(String query) {
		new TwitterSearch().execute(query);
	}
	
	private class TwitterSearch extends AsyncTask<String, Void, List<Tweet>> {
		private static final String KEY_TWEET = "tweet";
		private static final String KEY_FROM = "from";
		private Exception exception = null;

		@Override
		protected List<Tweet> doInBackground(String... query) {
			String q = query[0];
			try {
				return TwitterHelper.performSearch(q);
			} catch (Exception e) {
				exception = e;
			}
			return null;
		}
		
		@Override 
		protected void onPostExecute(List<Tweet> tweets) {
			if (exception!=null) {
				Toast.makeText(getApplicationContext(), "Searching failed: "+exception.getMessage(), Toast.LENGTH_SHORT).show();
			} else if (tweets!=null) {
				SimpleAdapter adapter = new SimpleAdapter(MainActivity.this, convertTweetsMap(tweets),
						android.R.layout.simple_list_item_2, new String[]{KEY_TWEET, KEY_FROM}, new int[]{android.R.id.text1, android.R.id.text2});
				tweetsList.setAdapter(adapter);
			}
		}
		
		private List<Map<String, String>> convertTweetsMap(List<Tweet> tweets) {
			List<Map<String,String>> result = new ArrayList<Map<String,String>>();
			for (Tweet tweet : tweets) {
				Map<String, String> map = new HashMap<String, String>();
				map.put(KEY_TWEET, tweet.tweet);
				map.put(KEY_FROM, tweet.from);
				result.add(map);
			}
			return result;
		}

	}

}
