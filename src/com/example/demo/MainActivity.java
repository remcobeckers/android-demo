package com.example.demo;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, convertTweets(tweets));
				tweetsList.setAdapter(adapter);
			}
		}
		
		private String[] convertTweets(List<Tweet> tweets) {
			String[] result = new String[tweets.size()];
			int n = 0;
			for (Tweet tweet : tweets) {
				result[n] = tweet.tweet;
				n++;
			}
			return result;
		}

	}

}
