package com.example.daapr;

import java.net.InetAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.widget.LoginButton;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class SignIn extends Activity {
	
//	int daapr_gray = Color.parseColor("#B3B3B3");
//	int daapr_blue = Color.parseColor("#53B6B4");
//	int daapr_red = Color.parseColor("#FF6666");
	String api_key;
	FbFragment fbfragment;
	TextView userInfoTextView;
	
	
	// Returns if a network connection is available.
	public boolean availableConnection() {
	    ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
	    return (networkInfo != null && networkInfo.isConnected()); // fetch data if true; else display error
	}
	
	// Returns if device is connected to Internet.
	public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com"); //You can replace it with your name
            if (ipAddr.equals("")) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
        	System.out.println("SOMETHING IS WRONG WITH INTERNET CONNECTION");
            return false;
        }
    }
	
//	@Override
//	public void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//		Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
//	}

	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
	    if (state.isOpened()) {
	        userInfoTextView.setVisibility(View.VISIBLE);
	    } else if (state.isClosed()) {
	        userInfoTextView.setVisibility(View.INVISIBLE);
	    }
	}
	
	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signin);
		
		LoginButton authButton = (LoginButton) findViewById(R.id.authButton);
		userInfoTextView = (TextView) findViewById(R.id.userInfoTextView);
		
		
//		authButton.setReadPermissions(Arrays.asList("email", "public_profile"));
//		if (savedInstanceState == null) {
//	        // Add the fragment on initial activity setup
//			System.out.println("---Saved instance is NULL");
//	        fbfragment = new FbFragment();
//	        getSupportFragmentManager()
//	        .beginTransaction()
//	        .add(android.R.id.content, fbfragment)
//	        .commit();
//	    } else {
//	        // Or set the fragment from restored state info
//	    	System.out.println("~~~Saved instance is NOT null");
//	        fbfragment = (FbFragment) getSupportFragmentManager()
//	        .findFragmentById(android.R.id.content);
//	    }
		
		final int h_margin = (int) getResources().getDimension(R.dimen.activity_horizontal_margin);
		final int v_margin = (int) getResources().getDimension(R.dimen.activity_vertical_margin);
//		LinearLayout signin_layout = (LinearLayout) findViewById(R.layout.signin);
//		signin_layout.setPadding(h_margin, v_margin, h_margin, v_margin);
		
		// start Facebook Login
//	    Session.openActiveSession(this, true, new Session.StatusCallback() {
//		    // callback when session changes state
//		    @Override
//		    public void call(Session session, SessionState state, Exception exception) {
//		    	System.out.println("IN SESSION CALL");
//		    	if (session.isOpened()) {
//		            // make request to the /me API
//		            Request.newMeRequest(session, new Request.GraphUserCallback() {
//		            	// callback after Graph API response with user object
//		            	@Override
//		            	public void onCompleted(GraphUser user, Response response) {
//		            		if (user != null) {
////		            			TextView welcome = (TextView) findViewById(R.id.welcome);
////		            			welcome.setText("Hello " + user.getName() + "!");
//		            		}
//		            	}
//		            }).executeAsync();
//		        }
//	    	}
//	    });
		
		// Actual user input values.
    	EditText email_unused = (EditText) findViewById(R.id.username_et);
    	EditText password_unused = (EditText) findViewById(R.id.password_et);
    	if (email_unused == null) { System.out.println("SOMETHING IS WRONG...why is it null?"); }
    	email_unused.setBackgroundResource(android.R.drawable.editbox_background_normal);
    	password_unused.setBackgroundResource(android.R.drawable.editbox_background_normal);
		
		Button btn = (Button) findViewById(R.id.signin);
		btn.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
			    try {
			    	String url = "https://daapr.com/rest_sign_in?";
					String email = "test@example.com";
					String password = "testing123";
					List<BasicNameValuePair> urlParams = new ArrayList<BasicNameValuePair>(2);
			        urlParams.add(new BasicNameValuePair("email", email));
			    	urlParams.add(new BasicNameValuePair("password", password));
			    	if (availableConnection()) { System.out.println("There is an available network connection!"); }
			    	else { System.out.println("No network connection!!"); }
//			    	if (isInternetAvailable()) { System.out.println("Connected to the internet!"); }
//			    	else { System.out.println("Something went wrong with the internet connection"); }
			    	new SignInTask().execute(url, urlParams);
				} catch (Exception e) {
					System.out.println("Sign in failed!");
					e.printStackTrace();
				}
		    }
		});
	}

	/** Called when the user clicks the Sign In button */
	public void signIn() {
		Intent feed = new Intent(this, Feed.class);
		if (api_key != null) { System.out.println("api_key is not null before sending to Feed.java"); }
		feed.putExtra("API_KEY", api_key);
		startActivity(feed);
	}
	
	/** Background thread that sends an Http POST request. */
	private class SignInTask extends AsyncTask<Object, Void, String> {
	    @SuppressWarnings("unchecked")
		protected String doInBackground(Object... params) {
//	    	return "";
	        return HTTP.postData((String) params[0],(List<BasicNameValuePair>) params[1]);
	    }

	    protected void onPostExecute(String result) {
	    	// tv1 appears after the signin button is released; for testing purposes
			TextView tv1 = (TextView) findViewById(R.id.testing);
	        tv1.setTextColor(getResources().getColor(R.color.daapr_blue));
	        Spannable title = new SpannableString(tv1.getText());
	        title.setSpan(new RelativeSizeSpan(1f), 0, title.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
	        tv1.setText(result);
	        
	        api_key = result;
	        if (api_key != null && api_key.indexOf("error") == -1) { signIn(); }
//	        signIn();
	    }
	}
	
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
	}
}