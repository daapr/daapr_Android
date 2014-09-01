package com.example.myfirstapp;

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
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;



public class Feed extends ActionBarActivity {

	// Global Variables
	int numTextViews = 2;
	String api_key;
	int current_length;
	String last_time_synchronized;
	int CARD_WIDTH = 200;
	int MP = LayoutParams.MATCH_PARENT;
	int WP = LayoutParams.WRAP_CONTENT;
	
	public static Drawable loadImageFromWebOperations(String url) {
	    try {
	        InputStream is = (InputStream) new URL(url).getContent();
	        Drawable d = Drawable.createFromStream(is, "src name");
	        return d;
	    } catch (Exception e) {
	        return null;
	    }
	}
	
    @SuppressLint({ "SimpleDateFormat", "NewApi" })
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayShowTitleEnabled(false);
        
        api_key = getIntent().getStringExtra("API_KEY");
        if (current_length == 0) { System.out.println("The current_length is 0"); }
        // Set last_time_synchronized to current time if it has not been set yet
        if (last_time_synchronized == null) {
        	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss Z");
        	Calendar cal = Calendar.getInstance();
        	String time = dateFormat.format(cal.getTime());
        	System.out.println("time " + time);
        	last_time_synchronized = time;
        }
        
        String url = "https://stage4lungcancer.herokuapp.com/rest_append_feed?";
        List<BasicNameValuePair> urlParams = new ArrayList<BasicNameValuePair>(2);
        urlParams.add(new BasicNameValuePair("api_key", api_key));
    	urlParams.add(new BasicNameValuePair("current_length", "" + current_length));
    	urlParams.add(new BasicNameValuePair("last_time_synchronized", last_time_synchronized));
    	new FeedTask().execute(url, urlParams);

//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.container, new PlaceholderFragment())
//                    .commit();
//        }

    }

    // Uncomment when top bar needed.
    /*
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
		    case R.id.action_profile:
		        openProfile();
		        return true;
		    case R.id.action_notifications:
		        openNotifications();
		        return true;
		    case R.id.action_add_post:
		    	openAddPost();
		    	return true;
		    case R.id.action_discover_users:
		    	openUsers();
		    	return true;
		    case R.id.action_settings:
		    	openSettings();
		    	return true;
		    default:
		        return super.onOptionsItemSelected(item);
    	}
    }
    */
    	 
	/** Called when the user clicks the Settings action item */
    private void openSettings() {
		
	}

    /** Called when the user clicks the Discover Users action item */
	private void openUsers() {
		
	}


	/** Called when the user clicks the Notifications action item */
    private void openNotifications() {
		Intent notes = new Intent(this, Notifications.class);
		startActivity(notes);	
	}
    
    /** Called when the user clicks the "Add a Post" action item */
	private void openAddPost() {
		
	}

	/** Called when the user clicks the Profile action item */
    private void openProfile() {
    	Intent profile = new Intent(this, Profile.class);
		startActivity(profile);
	}
    
    /** Background thread that sends an Http POST request to append new feed items. */
	private class FeedTask extends AsyncTask<Object, Void, Object[]> {
	    @SuppressWarnings("unchecked")
		protected Object[] doInBackground(Object... params) {
	    	// FIX. postData() needs to be able to return an array!
//	        return HTTP.append_feed((String) params[0],(List<BasicNameValuePair>) params[1]);
	    	
	    	// Dummy Object array. Delete. (Replace with actual postData() call)
	    	Object[] result = new Object[5];
	    	return result;
	    }

	    protected void onPostExecute(Object[] result) {	    	
	        //#0-micropost_id, 1-url, 2-title,3-image_url,4-video_url,5-site_name,6-reshare_user_id, 7-micropost_user_name,8-reshare_user_name, 9-reshare_created_at
	        //#10-reshare_id, 11-micropost_user_id, 12-like_num, 13-current_user_liked, 14-reshare_num, 15-current_user_reshared, 16-caption, 17-comment_num, 18-?, 19-?, 20-micropost_description
            final LinearLayout feed_layout = (LinearLayout) findViewById(R.id.feed);
	        // replace 10 with result.length
	        for (int i = 0; i < 10; i++) {
//	        	String title = result[i][2];
//	        	String site_name = result[i][5];
//	        	String micropost_user_name = result[i][7];
//	        	String image_url = result[i][3];
	        	
//		        card width = 280
	        	
	        	// Container that holds the cards
	        	final RelativeLayout container_layout = new RelativeLayout(getApplicationContext());
	        	LayoutParams lp = (LayoutParams) new RelativeLayout.LayoutParams(MP, WP);
	        	container_layout.setLayoutParams(lp);
	        	int h_margin = (int) getResources().getDimension(R.dimen.activity_horizontal_margin);
	        	int v_margin = (int) getResources().getDimension(R.dimen.activity_vertical_margin);
	        	// FIX. Change i to be length of result array.
	        	if (i != 9) { container_layout.setPadding(h_margin, v_margin, h_margin, 0); }
	        	else { container_layout.setPadding(h_margin, v_margin, h_margin, v_margin); }
	        	container_layout.setBackgroundColor(getResources().getColor(R.color.feed_color));
	        	
		        // Background color and margins
	        	final RelativeLayout card_layout = new RelativeLayout(getApplicationContext());
	        	LayoutParams lp_card = (LayoutParams) new RelativeLayout.LayoutParams(WP,
	        			WP);
	        	card_layout.setLayoutParams(lp_card);
	        	card_layout.setBackgroundColor(getResources().getColor(R.color.white));

		        // Image that goes on card
	        	ImageView card_image = new ImageView(getApplicationContext());
	        	// Replace i with actual Id!
	        	card_image.setId(i + 1);
		        card_image.setImageResource(R.drawable.wieber);
		        card_image.setScaleType(ImageView.ScaleType.CENTER_CROP);
		        LayoutParams lp_card_image = (LayoutParams) new RelativeLayout.LayoutParams(CARD_WIDTH, CARD_WIDTH);
	        	card_image.setLayoutParams(lp_card_image);
	        	lp_card_image.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
	        	lp_card_image.addRule(RelativeLayout.ALIGN_PARENT_TOP);
	        	card_image.setContentDescription("Jordyn Wieber wins gold!");
	        	
		        // Title text
	        	TextView title_tv = new TextView(getApplicationContext());
	        	title_tv.setId((i + 1) * 10);
	        	LayoutParams lp_title = (LayoutParams) new RelativeLayout.LayoutParams(WP,
	        			WP);
	        	title_tv.setLayoutParams(lp_title);
		        title_tv.setTextColor(getResources().getColor(R.color.daapr_blue));
		        Spannable title_text = new SpannableString("Sample Title: World Champion Wieber");
		        title_text.setSpan(new RelativeSizeSpan(1f), 0, title_text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		        title_tv.setText(title_text);
                lp_title.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                lp_title.addRule(RelativeLayout.RIGHT_OF, card_image.getId());
		        
		        // Source text
		        TextView source_tv = new TextView(getApplicationContext());
		        source_tv.setId((i + 1) * 100);
		        LayoutParams lp_source = (LayoutParams) new RelativeLayout.LayoutParams(WP,
	        			WP);
	        	source_tv.setLayoutParams(lp_source);
	        	Spannable source_text = new SpannableString("Sample Source: USA Gymnastics");
		        source_text.setSpan(new RelativeSizeSpan(0.8f), 0, source_text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		        source_tv.setTextColor(getResources().getColor(R.color.source_gray));
		        source_tv.setText(source_text);
		        lp_source.addRule(RelativeLayout.BELOW, title_tv.getId());
		        lp_source.addRule(RelativeLayout.ALIGN_LEFT, title_tv.getId());
		        
		        // User text
		        TextView user_tv = new TextView(getApplicationContext());
		        user_tv.setId((i + 1) * 1000);
		        LayoutParams lp_user = (LayoutParams) new RelativeLayout.LayoutParams(WP,
	        			WP);
	        	user_tv.setLayoutParams(lp_user);
	        	Spannable user_text = new SpannableString("Mckayla Maroney");
		        user_text.setSpan(new RelativeSizeSpan(0.8f), 0, user_text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		        user_tv.setTextColor(getResources().getColor(R.color.daapr_blue));
		        user_tv.setText(user_text);
		        lp_user.setMargins(0, 20, 0, 0);
		        lp_user.addRule(RelativeLayout.ALIGN_LEFT, source_tv.getId());
		        lp_user.addRule(RelativeLayout.BELOW, source_tv.getId());
		        
		        // Time stamp text
		        TextView time_tv = new TextView(getApplicationContext());
		        time_tv.setId((i + 1) * 10000);
		        LayoutParams lp_time = (LayoutParams) new RelativeLayout.LayoutParams(WP,
	        			WP);
	        	time_tv.setLayoutParams(lp_time);
	        	Spannable time_text = new SpannableString("4 months ago");
		        time_text.setSpan(new RelativeSizeSpan(0.8f), 0, time_text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		        time_tv.setTextColor(getResources().getColor(R.color.daapr_gray));
		        time_tv.setText(time_text);
		        lp_time.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		        lp_time.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		        
		        card_layout.addView(card_image);
		        card_layout.addView(title_tv);
		        card_layout.addView(source_tv);
		        card_layout.addView(user_tv);
		        card_layout.addView(time_tv);
		        container_layout.addView(card_layout);
		        feed_layout.addView(container_layout);
		        
		        // Handles card reaction on click
//		        OnClickListener card_click = new OnClickListener() {
//		        	@Override
//		            public void onClick(View v) {
//		        		// Box that covers entire card
//		        		RelativeLayout card_options_layout = new RelativeLayout(getApplicationContext());
//		        		card_options_layout.setLayoutParams((LayoutParams) new RelativeLayout.LayoutParams(
//		        				MP, CARD_WIDTH));
//		        		card_options_layout.setBackgroundColor(getResources().getColor(R.color.oil));
//		        		
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
//		        		
//		        		TextView view_tv = new TextView(getApplicationContext());	
//		            	view_tv.setText("View this");
//		                LayoutParams lp_view_tv = (LayoutParams) new RelativeLayout.LayoutParams(MP,
//			  	        	(CARD_WIDTH / 3));
//			  	        view_tv.setLayoutParams(lp_view_tv);
//			  	        lp_view_tv.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//			  	        view_tv.setBackgroundColor(getResources().getColor(R.color.daapr_blue));
//			  	        
//			  	        card_options_layout.addView(like_iv);
//			  	        card_options_layout.addView(reshare_iv);
//			  	        card_options_layout.addView(comment_iv);
//			  	        card_options_layout.addView(view_tv);
//			  	        card_layout.addView(card_options_layout);
//			  	        container_layout.invalidate();
//			  	        feed_layout.invalidate();
//		        	}
//		        };
//	        	card_layout.setOnClickListener(card_click);
	        }
	    }
	}
}
