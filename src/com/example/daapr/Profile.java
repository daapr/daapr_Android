package com.example.daapr;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Profile extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);
		
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    
	    Button btn_followers = (Button) findViewById(R.id.followers);
		btn_followers.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
			    try {
					showFollowers(v);
				} catch (Exception e) {
					e.printStackTrace();
				}
		    }
		});
		Button btn_following = (Button) findViewById(R.id.following);
		btn_following.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
			    try {
					showFollowing(v);
				} catch (Exception e) {
					e.printStackTrace();
				}
		    }
		});
	}
	
	public void showFollowers(View v) {
		Intent followers = new Intent(this, Followers.class);
		startActivity(followers);
	}
	
	public void showFollowing(View v) {
		Intent following = new Intent(this, Following.class);
		startActivity(following);
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() { }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
}