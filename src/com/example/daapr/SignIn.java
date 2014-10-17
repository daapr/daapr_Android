package com.example.daapr;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;


public class SignIn extends FragmentActivity {
	FbFragment fbfragment; // contains all sign in logic
	
	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// Initialize FbFragment or restore it
		if (savedInstanceState == null) {
	        // Add the fragment on initial activity setup
	        fbfragment = new FbFragment();
	        getSupportFragmentManager()
	        .beginTransaction()
	        .add(android.R.id.content, fbfragment)
	        .commit();
	    } else {
	        // Or set the fragment from restored state info
	        fbfragment = (FbFragment) getSupportFragmentManager()
	        .findFragmentById(android.R.id.content);
	    }
	}
	
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
	}
}