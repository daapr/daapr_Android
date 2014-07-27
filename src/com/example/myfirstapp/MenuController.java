package com.example.myfirstapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MenuController extends ActionBarActivity {
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


	/** Called when the user clicks the Profile action item */
    private void openProfile() {
    	Intent profile = new Intent(this, Profile.class);
		startActivity(profile);
	}
    
}
