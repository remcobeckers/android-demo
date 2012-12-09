package com.example.demo;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
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
		Toast.makeText(this, "Searching for "+query, Toast.LENGTH_SHORT).show();
	}

}
