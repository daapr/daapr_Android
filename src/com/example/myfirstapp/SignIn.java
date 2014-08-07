package com.example.myfirstapp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignIn extends Activity {
	
	int daapr_gray = Color.parseColor("#B3B3B3");
	int daapr_blue = Color.parseColor("#53B6B4");
	String api_key;
	
	
	// Returns if a network connection is available.
	public boolean availableConnection() {
	    ConnectivityManager connMgr = (ConnectivityManager) 
	        getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
	    if (networkInfo != null && networkInfo.isConnected()) {
	        // fetch data
	    	return true;
	    } else {
	        // display error
	    	return false;
	    }
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signin);
		
		Button btn = (Button) findViewById(R.id.signin);
		btn.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
			    try {
			    	// Actual user input values.
//					EditText email = (EditText) findViewById(R.id.email);
//			    	EditText password = (EditText) findViewById(R.id.password);
					
					String url = "https://stage4lungcancer.herokuapp.com/rest_sign_in?";
					String email = "dummy1@example.com";
					String password = "dummyone";
					List<BasicNameValuePair> urlParams = new ArrayList<BasicNameValuePair>(2);
			        urlParams.add(new BasicNameValuePair("email", email));
			    	urlParams.add(new BasicNameValuePair("password", password));
			    	if (availableConnection()) {
			    		System.out.println("There is an available network connection!");
			    	}
			    	else {
			    		System.out.println("No network connection!!");
			    	}
			    	new SignInTask().execute(url, urlParams);
//			    	api_key = HTTP.postData(url, urlParams);
					
//					HTTP.sendPost(url, "email=aym27@cornell.edu&password=default");
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
		startActivity(feed);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}
	
	/** Background thread that sends an Http POST request. */
	private class SignInTask extends AsyncTask<Object, Void, String> {
	    @SuppressWarnings("unchecked")
		protected String doInBackground(Object... params) {
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
	        if (api_key != null) { signIn(); }
	    }
	}
}