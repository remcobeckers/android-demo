android-demo
============

Presentation I gave for Ciber colleagues on building a basic Android app. The presentation consisted of creating the app from scratch. This repository is the complete application including some steps not discussed in the presentation. 
The app is a Twitter search app that uses the Twitter search API. It demonstrates some of the following concepts:
* Activity creation
* Layout creation
* Using UI components in the Activity
* Basic usage of ListView with ArrayAdapter and SimpleAdapter
* Starting activities via Intents
* AsyncTask usage
* Settings activity
* Permissions
* Toasts
* Creating and using a menu item

Each step in the presentation is available in its own branch with the final result in the master branch. For using within 
the Eclipse ADT checkout the repository and import an existing android project.

The steps for creating the app were:

1. Create application and create layout for main activity
2. Add some action under the search button
3. Implement the Twitter search and fill the ListView with the search result (using ArrayAdapter)
4. Add a settings activity and start it when clicking a menu item
5. Use the SimpleAdapter instead of the ArrayAdapter to fill the list so that it can display both the tweet text and the tweet user.
