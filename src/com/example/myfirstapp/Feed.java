package com.example.myfirstapp;

import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.annotation.SuppressLint;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;



public class Feed extends ActionBarActivity {

	// Global Variables
	int numTextViews = 2;
//	int daapr_gray = Color.parseColor("#B3B3B3");
//	int daapr_blue = Color.parseColor("#53B6B4");
//	int source_gray = Color.parseColor("#777777");
//	int card_color = Color.parseColor("#F0F0F0");
	String api_key;
	int current_length;
	String last_time_synchronized;
	
	public static Drawable LoadImageFromWebOperations(String url) {
	    try {
	        InputStream is = (InputStream) new URL(url).getContent();
	        Drawable d = Drawable.createFromStream(is, "src name");
	        return d;
	    } catch (Exception e) {
	        return null;
	    }
	}
	
    @SuppressLint("SimpleDateFormat")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed);
        
        api_key = getIntent().getStringExtra("API_KEY");
        if (current_length == 0) { System.out.println("The current_length is 0"); }
        // Set last_time_synchronized to current time if it has not been set yet
        if (last_time_synchronized == null) {
        	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss Z");
        	Calendar cal = Calendar.getInstance();
        	String time = dateFormat.format(cal.getTime());
        	System.out.println("time " + time);
        	last_time_synchronized = time;
        	System.out.println("Last time is " + last_time_synchronized);
        }
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
        
        String url = "https://stage4lungcancer.herokuapp.com/rest_append_feed?";
        List<BasicNameValuePair> urlParams = new ArrayList<BasicNameValuePair>(2);
        urlParams.add(new BasicNameValuePair("api_key", api_key));
    	urlParams.add(new BasicNameValuePair("current_length", "" + current_length));
    	urlParams.add(new BasicNameValuePair("last_time_synchronized", last_time_synchronized));
    	new FeedTask().execute(url, urlParams);
        
        // background color: #f4f4f4
        // border: 1px solid #dedede;      
        // blue: #53B6B4
        
        
//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.container, new PlaceholderFragment())
//                    .commit();
//        }

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
    
    /** Background thread that sends an Http POST request. */
	private class FeedTask extends AsyncTask<Object, Void, Object[]> {
	    @SuppressWarnings("unchecked")
		protected Object[] doInBackground(Object... params) {
	    	// FIX. postData() needs to be able to return an array!
//	        return HTTP.postData((String) params[0],(List<BasicNameValuePair>) params[1]);
	    	
	    	// Dummy Object array. Delete. (Replace with actual postData() call)
	    	Object[] result = new Object[5];
	    	return result;
	    }

	    protected void onPostExecute(Object[] result) {	    	
	        //#0-micropost_id, 1-url, 2-title,3-image_url,4-video_url,5-site_name,6-reshare_user_id, 7-micropost_user_name,8-reshare_user_name, 9-reshare_created_at
	        //#10-reshare_id, 11-micropost_user_id, 12-like_num, 13-current_user_liked, 14-reshare_num, 15-current_user_reshared, 16-caption, 17-comment_num, 18-?, 19-?, 20-micropost_description
            LinearLayout feed_layout = (LinearLayout) findViewById(R.id.container);
	        // replace 10 with result.length
	        for (int i = 0; i < 10; i++) {
//	        	String title = result[i][2];
//	        	String site_name = result[i][5];
//	        	String micropost_user_name = result[i][7];
//	        	String image_url = result[i][3];
	        	
	        	
		        // Background Color
	        	RelativeLayout card_layout = new RelativeLayout(getApplicationContext());
	        	LayoutParams lp = (LayoutParams) new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
	        			LayoutParams.WRAP_CONTENT);
	        	card_layout.setLayoutParams(lp);
	        	int h_margin = (int) getResources().getDimension(R.dimen.activity_horizontal_margin);
	        	int v_margin = (int) getResources().getDimension(R.dimen.activity_vertical_margin);
	        	card_layout.setPadding(h_margin, v_margin, h_margin, v_margin);
	        	card_layout.setBackgroundColor(getResources().getColor(R.color.card_color));	        	

		        // Image that goes on card
	        	ImageView card_image = new ImageView(getApplicationContext());
	        	// Replace i with actual Id!
	        	card_image.setId(i + 1);
		        card_image.setImageResource(R.drawable.wieber);
		        card_image.setScaleType(ImageView.ScaleType.CENTER_CROP);
		        LayoutParams lp_card_image = (LayoutParams) new RelativeLayout.LayoutParams(200, 200);
	        	card_image.setLayoutParams(lp_card_image);
	        	lp_card_image.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
	        	lp_card_image.addRule(RelativeLayout.ALIGN_PARENT_TOP);
	        	card_image.setContentDescription("Jordyn Wieber wins gold!");
		        
	        	
		        // Title text
	        	TextView tv1 = new TextView(getApplicationContext());
	        	LayoutParams lp_tv1 = (LayoutParams) new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
	        			LayoutParams.WRAP_CONTENT);
	        	tv1.setLayoutParams(lp_tv1);
		        tv1.setTextColor(getResources().getColor(R.color.daapr_blue));
		        Spannable title = new SpannableString("Sample Title: World Champion Wieber");
		        title.setSpan(new RelativeSizeSpan(1f), 0, title.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		        tv1.setText(title);
                lp_tv1.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                lp_tv1.addRule(RelativeLayout.RIGHT_OF, card_image.getId());
	        
		        //card width = 280
		        
//		        // Source text
//		        TextView tv2 = (TextView) findViewById(R.id.source);
//		        Spannable source = new SpannableString(tv2.getText());
//		        source.setSpan(new RelativeSizeSpan(0.8f), 0, source.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//		        tv2.setTextColor(getResources().getColor(R.color.source_gray));
//		        tv2.setText(source);
//		        
//		        // User text
//		        TextView tv3 = (TextView) findViewById(R.id.user);
//		        Spannable user = new SpannableString(tv3.getText());
//		        user.setSpan(new RelativeSizeSpan(0.85f), 0, user.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//		        tv3.setTextColor(getResources().getColor(R.color.daapr_blue));
//		        tv3.setText(user);
//		        
//		        // Time stamp text
//		        TextView tv4 = (TextView) findViewById(R.id.time);
//		        Spannable time = new SpannableString(tv4.getText());
//		        time.setSpan(new RelativeSizeSpan(0.85f), 0, time.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//		        tv4.setTextColor(getResources().getColor(R.color.daapr_gray));
//		        tv4.setText(time);
		        
		        card_layout.addView(card_image);
		        card_layout.addView(tv1);
		        feed_layout.addView(card_layout);
	        }
	    }
	}
	
	/** Generic array class. Currently unused. */
	public class GenSet<E> {

	    private Object[] a;

	    public GenSet(int s) {
	        a = new Object[s];
	    }

	    E get(int i) {
	        @SuppressWarnings("unchecked")
	        final E e = (E) a[i];
	        return e;
	    }
	}

//	/**
//     * A placeholder fragment containing a simple view.
//     */
//    public static class PlaceholderFragment extends Fragment {
//
//        public PlaceholderFragment() {
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                Bundle savedInstanceState) {
//            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
//            return rootView;
//        }
//    }
    
//    @Override
//	protected void onPause() {
//		super.onPause();
//	}

}
