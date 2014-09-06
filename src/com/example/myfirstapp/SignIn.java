package com.example.myfirstapp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
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
	
//	int daapr_gray = Color.parseColor("#B3B3B3");
//	int daapr_blue = Color.parseColor("#53B6B4");
//	int daapr_red = Color.parseColor("#FF6666");
	String api_key;
	
	
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signin);
		
		int h_margin = (int) getResources().getDimension(R.dimen.activity_horizontal_margin);
    	int v_margin = (int) getResources().getDimension(R.dimen.activity_vertical_margin);
		
		Button btn = (Button) findViewById(R.id.signin);
		btn.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
			    try {
			    	// Actual user input values.
//					EditText email = (EditText) findViewById(R.id.email);
//			    	EditText password = (EditText) findViewById(R.id.password);
					
					String url = "https://staging.daapr.com/rest_sign_in?";
					String email = "happiness@gmail.com";
					String password = "happiness1";
					List<BasicNameValuePair> urlParams = new ArrayList<BasicNameValuePair>(2);
			        urlParams.add(new BasicNameValuePair("email", email));
			    	urlParams.add(new BasicNameValuePair("password", password));
			    	if (availableConnection()) { System.out.println("There is an available network connection!"); }
			    	else { System.out.println("No network connection!!"); }
			    	if (isInternetAvailable()) { System.out.println("Connected to the internet!"); }
			    	else { System.out.println("Something went wrong with the internet connection"); }
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
//	        if (api_key != null && api_key.indexOf("error") == -1) { signIn(); }
	        signIn();
	    }
	}
	
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
	}
}