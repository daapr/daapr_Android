package com.example.daapr;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

	// Global Variables
	String api_key;
	int current_length;
	String last_time_synchronized;
	static int CARD_WIDTH = 200;
	static int MP = LayoutParams.MATCH_PARENT;
	static int WP = LayoutParams.WRAP_CONTENT;
	Context context;
	int initial_feed_length = 20;
	String url = "https://orangeseven7.com/rest_append_feed?";
    ListView feed_listview;
    CardAdapter adapter;

	
    @SuppressLint({ "SimpleDateFormat", "NewApi" })
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        context = this;
        
        api_key = getIntent().getStringExtra("API_KEY");
        feed_listview = (ListView)findViewById(R.id.feed_list);
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
        List<BasicNameValuePair> urlParams = new ArrayList<BasicNameValuePair>(2);
        if (current_length == 0) { System.out.println("The current_length is 0"); }
    	// Set last_time_synchronized to current time if it has not been set yet
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss Z");
    	Calendar cal = Calendar.getInstance();
    	String time = dateFormat.format(cal.getTime());
    	System.out.println("time " + time);
    	last_time_synchronized = time;
        urlParams.add(new BasicNameValuePair("api_key", api_key));
    	urlParams.add(new BasicNameValuePair("current_length", "" + new_length));
    	urlParams.add(new BasicNameValuePair("last_time_synchronized", last_time_synchronized));
    	new FeedTask().execute(url, urlParams);
    }

    /** Background thread that sends an Http POST request to append new feed items. */
	private class FeedTask extends AsyncTask<Object, Void, Object[]> {

	    @SuppressWarnings("unchecked")
		protected Object[] doInBackground(Object... params) {
	        return HTTP.append_feed((String) params[0],(List<BasicNameValuePair>) params[1]);
	    }

	    protected void onPostExecute(Object[] result) {
	    	if ((Boolean) result[0]) {
		    	ArrayList<Card> card_data = new ArrayList<Card>();
	        	// Loop through result array to initialize all Cards
		    	Object[] feed_array = (Object[]) result[1];
	        	for (int i = 0; i < feed_array.length; i++) {
	        		Card card = new Card(getApplicationContext(), feed_array[i]);
	        		card_data.add(card);
	        	}
	        	// Adapt cards to views to be put in the listview
		        if (feed_listview.getAdapter() == null) {
		            adapter = new CardAdapter(context, R.layout.listview_card, card_data);
			        feed_listview.setAdapter(adapter);
		        } else {
		        	adapter.updateData(card_data);
			        adapter.notifyDataSetChanged(); //USELESS?? :(
		        }
		        feed_listview.setOnScrollListener(new AbsListView.OnScrollListener() {
		        	@Override
		            public void onScroll(AbsListView view, int firstVisible, int visibleCount, int totalCount) {
		                boolean loadMore = /* maybe add a padding */
		                    firstVisible + visibleCount >= totalCount - 5;
		                if(loadMore) {
		                	current_length += adapter.count;
		                    adapter.count += visibleCount;
		                    System.out.println("TOTAL ADAPTER COUNT = " + adapter.getCount());
		                    ((CardAdapter) feed_listview.getAdapter()).notifyDataSetChanged();
		                    updateParams(current_length, last_time_synchronized);
		                }
		            }
		        	@Override
		            public void onScrollStateChanged(AbsListView v, int s) { }
		        });
		        
		        // Set listview items to have onItemClickListeners. Opens link (no intermediate view.
		        // For intermediate view, see http://stackoverflow.com/questions/6867372/add-onclicklistener-to-listview-item.
		        // See http://stackoverflow.com/questions/18818279/listview-to-open-hyperlinks-in-android.
		        feed_listview.setOnItemClickListener(new OnItemClickListener() {
	
					@Override
					public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
	//					RelativeLayout card_options_layout = showCardOptions();
	//					feed_listview.addView(card_options_layout);
	//					feed_listview.invalidate();
						String url = adapter.getItem(position).url;
				        Intent i = new Intent(Intent.ACTION_VIEW);
				        i.setData(Uri.parse(url));
				        startActivity(i);
					}
		        });	        
		        
	//	        feed_listview.invalidate();
		    	
		    	/*//ORIGINAL CODE BEGIN
		        //#0-micropost_id, 1-url, 2-title,3-image_url,4-video_url,5-site_name,6-reshare_user_id, 7-micropost_user_name,8-reshare_user_name, 9-reshare_created_at
		        //#10-reshare_id, 11-micropost_user_id, 12-like_num, 13-current_user_liked, 14-reshare_num, 15-current_user_reshared, 16-caption, 17-comment_num, 18-?, 19-?, 20-micropost_description
	            final LinearLayout feed_layout = (LinearLayout) findViewById(R.id.feed);
		        // replace 10 with result.length
	            int length = 1000;
	            int load = 20;
	            if (length > 20) {
	            	length -= load;
	            }
		        for (int i = 0; i < load; i++) {
	//	        	String title = result[i][2];
	//	        	String site_name = result[i][5];
	//	        	String micropost_user_name = result[i][7];
	//	        	String image_url = result[i][3];
		        	
		        	// Container that holds the cards
		        	final RelativeLayout container_layout = new RelativeLayout(getApplicationContext());
		        	LayoutParams lp = (LayoutParams) new RelativeLayout.LayoutParams(MP, WP);
		        	container_layout.setLayoutParams(lp);
		        	int h_margin = (int) getResources().getDimension(R.dimen.activity_horizontal_margin);
		        	int v_margin = (int) getResources().getDimension(R.dimen.activity_vertical_margin);
		        	// FIX. Change i to be length of result array.
		        	if (i != load - 1) { container_layout.setPadding(h_margin, v_margin, h_margin, 0); }
		        	else { container_layout.setPadding(h_margin, v_margin, h_margin, v_margin); }
		        	container_layout.setBackgroundColor(getResources().getColor(R.color.feed_color));
		        	
		        	Card card = new Card(getApplicationContext(), i + 1, (i + 1) * 10,
		        			(i + 1) * 100, (i + 1) * 1000, (i + 1) * 10000);
			        
			        container_layout.addView(card.layout);
			        feed_layout.addView(container_layout);
			        */ //ORIGINAL CODE END
		        
			        
			        // Handles card reaction on click
			        /*OnClickListener card_click = new OnClickListener() {
			        	@Override
			            public void onClick(View v) {
			        		// Box that covers entire card
			        		RelativeLayout card_options_layout = new RelativeLayout(getApplicationContext());
			        		card_options_layout.setLayoutParams((LayoutParams) new RelativeLayout.LayoutParams(
			        				MP, CARD_WIDTH));
	//		        		card_options_layout.setBackgroundColor(getResources().getColor(R.color.oil));
			        		card_options_layout.setBackgroundColor(getResources().getColor(R.color.trans_blue));
	
	//		        		ImageView like_iv = new ImageView(getApplicationContext());
	//		        		// GIVE IT A REAL ID
	//		        		like_iv.setId(2000);
	//		        		like_iv.setImageResource(R.drawable.reshare_small);
	//		        		like_iv.setContentDescription("Like");
	//		        		LayoutParams like_lp = (LayoutParams) new RelativeLayout.LayoutParams(CARD_WIDTH / 3,
	//		        				CARD_WIDTH / 3);
	//		        		like_iv.setLayoutParams(like_lp);
	//		        		like_lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
	//		        		
	//		        		ImageView reshare_iv = new ImageView(getApplicationContext());
	//		        		reshare_iv.setImageResource(R.drawable.reshare_small);
	//		        		reshare_iv.setContentDescription("Reshare");
	//		        		LayoutParams reshare_lp = (LayoutParams) new RelativeLayout.LayoutParams(CARD_WIDTH / 3,
	//		        				CARD_WIDTH / 3);
	//		        		reshare_iv.setLayoutParams(reshare_lp);
	//		        		reshare_lp.addRule(RelativeLayout.LEFT_OF, like_iv.getId());
	//
	//		        		ImageView comment_iv = new ImageView(getApplicationContext());
	//		        		comment_iv.setImageResource(R.drawable.reshare_small);
	//		        		comment_iv.setContentDescription("Comment");
	//		        		LayoutParams comment_lp = (LayoutParams) new RelativeLayout.LayoutParams(CARD_WIDTH / 3,
	//		        				CARD_WIDTH / 3);
	//		        		comment_iv.setLayoutParams(comment_lp);
	//		        		comment_lp.addRule(RelativeLayout.RIGHT_OF, like_iv.getId());
			        		
			        		TextView view_tv = new TextView(getApplicationContext());	
			            	view_tv.setText("View this");
			                LayoutParams lp_view_tv = (LayoutParams) new RelativeLayout.LayoutParams(MP,
				  	        	(CARD_WIDTH / 3));
				  	        view_tv.setLayoutParams(lp_view_tv);
				  	        lp_view_tv.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
				  	        view_tv.setBackgroundColor(getResources().getColor(R.color.daapr_blue));
				  	        
	//			  	        card_options_layout.addView(like_iv);
	//			  	        card_options_layout.addView(reshare_iv);
	//			  	        card_options_layout.addView(comment_iv);
				  	        card_options_layout.addView(view_tv);
	//			  	        card_layout.addView(card_options_layout);
	//			  	        container_layout.invalidate();
	//			  	        feed_layout.invalidate();
				  	        feed_listview.invalidate();
			        	}
			        };
	//	        	card_layout.setOnClickListener(card_click);
	//		        layout.setOnClickListener(card_click); */
		        
		        
		        //} //FOR LOOP END
		    } else {
		    	TextView error = (TextView) findViewById(R.id.feed_error_tv);
	        	error.setVisibility(0); // set to visible
	        	error.setText((String) result[1]);
		    }
	    }
	}
}
