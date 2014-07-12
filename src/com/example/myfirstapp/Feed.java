package com.example.myfirstapp;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;



public class Feed extends ActionBarActivity {

	// Global Variables
	int numTextViews = 2;
//	int daapr_gray = Color.parseColor("#B3B3B3");
//	int daapr_blue = Color.parseColor("#53B6B4");
//	int source_gray = Color.parseColor("#777777");
//	int card_color = Color.parseColor("#F0F0F0");
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed);
        
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        
        
        // Background Color
        RelativeLayout card_layout = (RelativeLayout) findViewById(R.id.card);
        card_layout.setBackgroundColor(getResources().getColor(R.color.card_color));
        //card_layout.getResources().getColor(R.color.card_color);
        
        //card width = 280
        
        /*<FrameLayout
        android:id="@+id/card"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignBottom="@+id/user"
        android:layout_alignRight="@+id/card_image" 
        android:layout_alignLeft="@+id/card_image"
        android:layout_alignTop="@+id/card_image"
        android:background="@color/card_color"> 
        </FrameLayout>*/
        
        // Title text
        TextView tv1 = (TextView) findViewById(R.id.title);
        tv1.setTextColor(getResources().getColor(R.color.daapr_blue));
        Spannable title = new SpannableString(tv1.getText());
        title.setSpan(new RelativeSizeSpan(1f), 0, title.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        tv1.setText(title);
        
        // Source text 
        TextView tv2 = (TextView) findViewById(R.id.source);
        Spannable source = new SpannableString(tv2.getText());
        source.setSpan(new RelativeSizeSpan(0.8f), 0, source.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        tv2.setTextColor(getResources().getColor(R.color.source_gray));
        tv2.setText(source);
        
        // User text
        TextView tv3 = (TextView) findViewById(R.id.user);
        Spannable user = new SpannableString(tv3.getText());
        user.setSpan(new RelativeSizeSpan(0.85f), 0, user.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        tv3.setTextColor(getResources().getColor(R.color.daapr_blue));
        tv3.setText(user);
        
        // Time stamp text
        TextView tv4 = (TextView) findViewById(R.id.time);
        Spannable time = new SpannableString(tv4.getText());
        time.setSpan(new RelativeSizeSpan(0.85f), 0, time.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        tv4.setTextColor(getResources().getColor(R.color.daapr_gray));
        tv4.setText(time);
        
        
        // background color: #f4f4f4
        // border: 1px solid #dedede;
        
        /*
        // Test the sizing of text 
        TextView test = (TextView) findViewById(R.id.testText);
        int test_length = test.length();
        Spannable span = new SpannableString(test.getText());
        span.setSpan(new RelativeSizeSpan(0.8f), 0, test_length, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        test.setText(span);
        <TextView 
        android:id="@+id/testText"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/test_text"/>*/
        
        // Image that goes on card 
        ImageView iv1 = (ImageView) findViewById(R.id.card_image);
        iv1.setImageResource(R.drawable.wieber); 
        
        /*
        // GOOD EXAMPLE but xml interferes
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.container);
        RelativeLayout.LayoutParams relativeLayoutParams;       
        TextView[] textView = new TextView[numTextViews];

        // 1st TextView
        textView[0] = new TextView(this);//(TextView) findViewById(R.id.title);

        relativeLayoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        textView[0].setId(1); // changed id from 0 to 1
        textView[0].setText("Title: Jordyn Wieber becomes World Champion");   

        relativeLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);

        relativeLayout.addView(textView[0], relativeLayoutParams);

        // 2nd TextView
        textView[1] = new TextView(this);//(TextView) findViewById(R.id.source);

        relativeLayoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);      

        textView[1].setText("Source: Universal Sports");

        relativeLayoutParams.addRule(RelativeLayout.RIGHT_OF,
                textView[0].getId());
        relativeLayoutParams.addRule(RelativeLayout.ALIGN_TOP,
                textView[0].getId()); // added top alignment rule

        relativeLayout.addView(textView[1], relativeLayoutParams); */
        
        /*
        // BAD EXAMPLE
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //RelativeLayout.LayoutParams relativeLayoutParams; 
        RelativeLayout relativeLayout= (RelativeLayout) findViewById(R.id.container);
        TextView[] textView = new TextView[numTextViews];
        
        textView[0] = new TextView(this);//creates first textview
        textView[0].setId(1);
        textView[0].setText("1");
        //textView[0].setBackgroundResource(R.drawable.shape);//parses an image from shape.xml

        RelativeLayout.LayoutParams relativeLayoutParams =
                new RelativeLayout.LayoutParams((RelativeLayout.LayoutParams.WRAP_CONTENT),
                		(RelativeLayout.LayoutParams.WRAP_CONTENT));//create params for new textview
        relativeLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        textView[0].setLayoutParams(relativeLayoutParams);

        relativeLayout.addView(textView[0], relativeLayoutParams);//creates another textview

        textView[1] = new TextView(this);
        

       // textView[1].setBackgroundResource(R.drawable.shape);

        relativeLayoutParams.addRule(RelativeLayout.RIGHT_OF, textView[0].getId());//to align the textview side by side
        textView[1].setText("2");

        relativeLayout.addView(textView[1], relativeLayoutParams); */
        //end bad example
        
        //blue: #53B6B4
        
        
//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.container, new PlaceholderFragment())
//                    .commit();
//        }

    }


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
    	System.out.println("In onOptionsItemSelected");
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
		// TODO Auto-generated method stub
		
	}

    /** Called when the user clicks the Discover Users action item */
	private void openUsers() {
		// TODO Auto-generated method stub
		
	}


	/** Called when the user clicks the Notifications action item */
    private void openNotifications() {
		// TODO Auto-generated method stub
		
	}


	/** Called when the user clicks the Profile action item */
    private void openProfile() {
		// TODO Auto-generated method stub
    	System.out.println("In openProfile()");
    	Intent profile = new Intent(this, Profile.class);
		startActivity(profile);
	}

	/**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}
