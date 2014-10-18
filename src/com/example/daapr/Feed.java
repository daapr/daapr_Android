package com.example.daapr;

import java.io.InputStream;

import java.net.URL;

import java.text.SimpleDateFormat;

import java.util.ArrayList;

import java.util.Calendar;

import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.annotation.SuppressLint;

import android.content.Context;

import android.content.Intent;

import android.content.SharedPreferences;

import android.graphics.drawable.Drawable;

import android.net.Uri;

import android.os.AsyncTask;

import android.os.Bundle;

import android.support.v7.app.ActionBarActivity;

import android.view.Menu;

import android.view.MenuInflater;

import android.view.MenuItem;

import android.view.View;

import android.widget.AbsListView;

import android.widget.AdapterView;

import android.widget.AdapterView.OnItemClickListener;

import android.widget.ListView;

import android.widget.RelativeLayout.LayoutParams;

import android.widget.TextView;

public class Feed extends ActionBarActivity {

	String api_key;

	int current_length;

	String last_time_synchronized;

	static int CARD_WIDTH = 200;

	static int MP = LayoutParams.MATCH_PARENT;

	static int WP = LayoutParams.WRAP_CONTENT;

	Context context;

	int initial_feed_length = 20;

	String url = "https://orangeseven7.com/rest_append_feed?";

	private ListView feed_listview;

	CardAdapter adapter;

	@SuppressLint({ "SimpleDateFormat", "NewApi" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.feed);

		getSupportActionBar().setDisplayHomeAsUpEnabled(false);

		getActionBar().setDisplayShowTitleEnabled(false);

		context = this;

		api_key = getIntent().getStringExtra("API_KEY");

		feed_listview = (ListView) findViewById(R.id.feed_list);

		updateParams(current_length, last_time_synchronized);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu items for use in the action bar

		MenuInflater inflater = getMenuInflater();

		inflater.inflate(R.menu.main, menu);

		return super.onCreateOptionsMenu(menu);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// Handle presses on the action bar items

		switch (item.getItemId()) {

		case R.id.action_signout:

			signOut();

			return true;

		default:

			return super.onOptionsItemSelected(item);

		}

	}

	private void signOut() {

		// Remove the session's api_key

		SharedPreferences sharedPref = getSharedPreferences(

		"com.example.daapr.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE);

		SharedPreferences.Editor editor = sharedPref.edit();

		editor.remove("api_key");

		editor.commit();

		// Navigate back to sign in

		Intent signin = new Intent(this, SignIn.class);

		startActivity(signin);

	}

	@SuppressLint("SimpleDateFormat")
	public void updateParams(int new_length, String last_time_synchronized) {

		List<BasicNameValuePair> urlParams = new ArrayList<BasicNameValuePair>(

		2);

		if (current_length == 0) {

			System.out.println("The current_length is 0");

		}

		// Set last_time_synchronized to current time if it has not been set yet

		SimpleDateFormat dateFormat = new SimpleDateFormat(

		"yyyy/MM/dd HH:mm:ss Z");

		Calendar cal = Calendar.getInstance();

		String time = dateFormat.format(cal.getTime());

		last_time_synchronized = time;

		urlParams.add(new BasicNameValuePair("api_key", api_key));

		urlParams

		.add(new BasicNameValuePair("current_length", "" + new_length));

		urlParams.add(new BasicNameValuePair("last_time_synchronized",

		last_time_synchronized));

		new FeedTask().execute(url, urlParams);

	}

	public void configureAdapter(ArrayList<Card> card_data) {

		// Adapt cards to views to be put in the listview

		if (feed_listview.getAdapter() == null) {

			adapter = new CardAdapter(context, R.layout.listview_card,

			card_data);

			feed_listview.setAdapter(adapter);

		} else {

			adapter.getData().addAll(card_data);

			adapter.notifyDataSetChanged();

		}

		feed_listview.setOnScrollListener(new AbsListView.OnScrollListener() {

			@Override
			public void onScroll(AbsListView view, int firstVisible,

			int visibleCount, int totalCount) {

				boolean loadMore = /* maybe add a padding */

				firstVisible + visibleCount >= totalCount - 5;

				if (loadMore) {

					current_length += adapter.getCount();

					adapter.setCount(adapter.getCount() + visibleCount);

					System.out.println("TOTAL ADAPTER COUNT = "

					+ adapter.getCount());

					((CardAdapter) feed_listview.getAdapter())

					.notifyDataSetChanged();

					updateParams(current_length, last_time_synchronized);

				}

			}

			@Override
			public void onScrollStateChanged(AbsListView v, int s) {

			}

		});

		// Set listview items to have onItemClickListeners. Opens link (no

		// intermediate view.

		// For intermediate view, see

		// http://stackoverflow.com/questions/6867372/add-onclicklistener-to-listview-item.

		// See

		// http://stackoverflow.com/questions/18818279/listview-to-open-hyperlinks-in-android.

		feed_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,

			long id) {

				// RelativeLayout card_options_layout = showCardOptions();

				// feed_listview.addView(card_options_layout);

				// feed_listview.invalidate();

				String url = adapter.getItem(position).getUrl();

				Intent i = new Intent(Intent.ACTION_VIEW);

				i.setData(Uri.parse(url));

				startActivity(i);

			}

		});

	}

	/**
	 * 
	 * Background thread that sends an Http POST request to append new feed
	 * 
	 * items.
	 */

	private class FeedTask extends AsyncTask<Object, Void, Object[]> {

		@SuppressWarnings("unchecked")
		protected Object[] doInBackground(Object... params) {

			return HTTP.append_feed((String) params[0],

			(List<BasicNameValuePair>) params[1]);

		}

		protected void onPostExecute(Object[] result) {

			if ((Boolean) result[0]) {

				Object[] feed_array = (Object[]) result[1];

				for (int i = 0; i < feed_array.length; i++) {

					Card card = new Card(getApplicationContext(), feed_array[i]);

					new LoadImageTask().execute(card);

				}

			} else {

				TextView error = (TextView) findViewById(R.id.feed_error_tv);

				error.setVisibility(0); // set to visible

				error.setText((String) result[1]);

			}

		}

	}

	/** Loads images from image_url into the card */

	class LoadImageTask extends AsyncTask<Card, Void, ArrayList<Object>> {

		protected ArrayList<Object> doInBackground(Card... params) {

			try {

				InputStream is = (InputStream) new URL(params[0].getImageUrl())

				.getContent();

				Drawable d = Drawable.createFromStream(is,

				params[0].getSiteName());

				ArrayList<Object> tuple = new ArrayList<Object>();

				tuple.add(d);

				tuple.add(params[0]);

				return tuple;

			} catch (Exception e) {

				e.printStackTrace();

				return null;

			}

		}

		protected void onPostExecute(ArrayList<Object> tuple) {

			if (tuple != null) {

				System.out.println("got here");

				System.out.println("drawable " + tuple.get(0));

				System.out.println("card " + tuple.get(1));

				Drawable img = (Drawable) tuple.get(0);

				Card card = (Card) tuple.get(1);

				card.setImageDrawable(img);

				ArrayList<Card> cardArray = new ArrayList<Card>();

				cardArray.add(card);

				configureAdapter(cardArray);

			}

		}

	}

}