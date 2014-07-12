package com.example.myfirstapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SignIn extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signin);
	}

	/** Called when the user clicks the Sign In button */
	public void signIn(View v) {
		Intent feed = new Intent(this, Feed.class);
		startActivity(feed);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
}
