package com.example.daapr;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.OpenRequest;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionDefaultAudience;
import com.facebook.SessionLoginBehavior;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;


public class SignIn extends FragmentActivity {
	
	String api_key;
	FbFragment fbfragment;
	
	// Returns if a network connection is available.
	public boolean availableConnection() {
	    ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
	    return (networkInfo != null && networkInfo.isConnected()); // fetch data if true; else display error
	}
	
	// Returns if device is connected to Internet. Not sure if this is actually useful.
	public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("daapr.com");
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
	
	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signin);
		
		if (savedInstanceState == null) {
	        // Add the fragment on initial activity setup
	        fbfragment = new FbFragment();
	        getSupportFragmentManager()
	        .beginTransaction()
	        .add(android.R.id.content, fbfragment)
	        .commit();
//	        System.out.println("USER INFO IN SIGNIN! ---> " + fbfragment.fb_id + "; " + fbfragment.first_name + "; " +
//            fbfragment.last_name + "; " + fbfragment.fb_email + "; ");
	    } else {
	        // Or set the fragment from restored state info
	        fbfragment = (FbFragment) getSupportFragmentManager()
	        .findFragmentById(android.R.id.content);
//	        System.out.println("USER INFO IN SIGNIN but savedinstancestate was not null! ---> " + fbfragment.fb_id +
//	        		"; " + fbfragment.first_name + "; " +
//	                fbfragment.last_name + "; " + fbfragment.fb_email + "; ");
	    }
		
//		final int h_margin = (int) getResources().getDimension(R.dimen.activity_horizontal_margin);
//		final int v_margin = (int) getResources().getDimension(R.dimen.activity_vertical_margin);
//		LinearLayout signin_layout = (LinearLayout) findViewById(R.layout.signin);
//		signin_layout.setPadding(h_margin, v_margin, h_margin, v_margin);
		
		// Actual user input values.
    	EditText email_unused = (EditText) findViewById(R.id.username_et);
    	EditText password_unused = (EditText) findViewById(R.id.password_et);
    	email_unused.setBackgroundResource(android.R.drawable.editbox_background_normal);
    	password_unused.setBackgroundResource(android.R.drawable.editbox_background_normal);
		
		Button btn = (Button) findViewById(R.id.signin);
		btn.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    	System.out.println("IN ON CLICK FOR SIGN IN");
			    try {
			    	String url = "https://daapr.com/rest_sign_in?";
					String email = "test@example.com";
					String password = "testing123";
					List<BasicNameValuePair> urlParams = new ArrayList<BasicNameValuePair>(2);
			        urlParams.add(new BasicNameValuePair("email", email));
			    	urlParams.add(new BasicNameValuePair("password", password));
			    	if (availableConnection()) { System.out.println("There is an available network connection!"); }
			    	else { System.out.println("No network connection!!"); }
			    	new SignInTask().execute(url, urlParams);
				} catch (Exception e) {
					System.out.println("Sign in failed!");
					e.printStackTrace();
				}
		    }
		});
	}
	
//	@Override
//	public void onActivityResult(int requestCode, int resultCode, Intent data) {
//	    super.onActivityResult(requestCode, resultCode, data);
////	    uiHelper.onActivityResult(requestCode, resultCode, data);
//	}

	/** Called when the user clicks the Sign In button */
	public void signIn() {
		Intent feed = new Intent(this, Feed.class);
		feed.putExtra("API_KEY", api_key);
		startActivity(feed);
	}
	
	/** Background thread that sends an Http POST request. */
	private class SignInTask extends AsyncTask<Object, Void, String> {
	    @SuppressWarnings("unchecked")
		protected String doInBackground(Object... params) {
	        return HTTP.sign_in((String) params[0],(List<BasicNameValuePair>) params[1]);
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
	    }
	}
	
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
	}
}