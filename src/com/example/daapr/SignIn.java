package com.example.daapr;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;


public class SignIn extends FragmentActivity {
	FbFragment fbfragment; // contains all sign in logic
	Bundle savedInstanceState;
	
	
	/** Returns if a network connection is available. Used for debugging. */
	public boolean availableConnection() {
	    ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
	    return (networkInfo != null && networkInfo.isConnected());
	}

	private void loadSignIn() {
		if (!availableConnection()) {
			this.setContentView(R.layout.no_internet);
		} else {
			// Initialize FbFragment or restore it
			if (savedInstanceState == null) {
		        // Add the fragment on initial activity setup
		        fbfragment = new FbFragment();
		        getSupportFragmentManager()
		        .beginTransaction()
		        .add(android.R.id.content, fbfragment)
		        .commit();
		    } else {
		        // set the fragment from restored state info
		        fbfragment = (FbFragment) getSupportFragmentManager()
		        .findFragmentById(android.R.id.content);
		    }
		}
	}
	
	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.savedInstanceState = savedInstanceState;
		loadSignIn();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		loadSignIn();
	}

	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
	}
}