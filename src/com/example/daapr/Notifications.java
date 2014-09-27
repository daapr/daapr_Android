package com.example.daapr;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class Notifications extends ActionBarActivity {
  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.notifications);
		
		LinearLayout linearLayout= new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        linearLayout.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
		
		//ImageView Setup
        ImageView imageView = new ImageView(this);
        //setting image resource
        imageView.setImageResource(R.drawable.notification);
        //setting image position
        imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
        		LayoutParams.WRAP_CONTENT));

        //adding view to layout
//        LinearLayout not_layout = (LinearLayout) findViewById(R.layout.notifications);
        linearLayout.addView(imageView);
        //make visible to program
        setContentView(linearLayout);
        
        ActionBar actionBar = getSupportActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	}
	
//	@Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//    	// Inflate the menu items for use in the action bar
//        return f.onCreateOptionsMenu(menu);
//    }
//	
//	@Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle presses on the action bar items
//		return f.onOptionsItemSelected(item);
//	}

}
