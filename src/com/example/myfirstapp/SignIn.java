package com.example.myfirstapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignIn extends Activity {
	
	int daapr_gray = Color.parseColor("#B3B3B3");
	int daapr_blue = Color.parseColor("#53B6B4");
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signin);
		
		Button btn = (Button) findViewById(R.id.signin);

		btn.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
//		    	HTTP http = new HTTP();
//		    	String url = "http://0.0.0.0:3000/microposts/rest_sign_in/?" +
//		    			"api_key=90485e931f687a9b9c2a66bf58a3861a&" + 
//		    			"current_user_id=101&" + "user_name=alice.meng@yahoo.com" + "password=August123";
//		    	String urlParams = "";
			    try {
//					http.sendPost(url, urlParams);
					signIn(v);
				} catch (Exception e) {
					e.printStackTrace();
				}
		    }
		});
	}

	/** Called when the user clicks the Sign In button */
	public void signIn(View v) {
		Intent feed = new Intent(this, Feed.class);
		startActivity(feed);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}
}
