package com.example.myfirstapp;

import android.app.Activity;
import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

public class Card extends Activity {
	//#0-micropost_id, 1-url, 2-title,3-image_url,4-video_url,5-site_name,6-reshare_user_id,
	// 7-micropost_user_name,8-reshare_user_name, 9-reshare_created_at
    //#10-reshare_id, 11-micropost_user_id, 12-like_num, 13-current_user_liked, 14-reshare_num,
	// 15-current_user_reshared, 16-caption, 17-comment_num, 18-?, 19-?, 20-micropost_description
	private int micropost_id;
	private String url;
	private String title;
	private String image_url;
	private String video_url;
	private String site_name;
	private int reshare_user_id;
	private String micropost_user_name;
	private String reshare_user_name;
	private String reshare_created_at;
	private int reshare_id;
	private int micropost_user_id;
	private int like_num;
	private String[] current_user_liked;
	private int reshare_num;
	private String current_user_reshared;
	private String caption;
	private int comment_num;
	private String micropost_description;
	final RelativeLayout layout;
	
	public Card(Context c, int id, int title_id, int source_id, int user_id, int time) {
		// Background color and margins
    	this.layout = new RelativeLayout(c);
    	LayoutParams lp_card = (LayoutParams) new RelativeLayout.LayoutParams(Feed.WP,
    			Feed.WP);
    	layout.setLayoutParams(lp_card);
    	layout.setBackgroundColor(c.getResources().getColor(R.color.white));

        // Image that goes  on card
    	ImageView card_image = new ImageView(c);
    	// Replace i with actual Id!
    	card_image.setId(id);
        card_image.setImageResource(R.drawable.wieber);
        card_image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        LayoutParams lp_card_image = (LayoutParams) new RelativeLayout.LayoutParams(Feed.CARD_WIDTH, Feed.CARD_WIDTH);
    	card_image.setLayoutParams(lp_card_image);
    	lp_card_image.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
    	lp_card_image.addRule(RelativeLayout.ALIGN_PARENT_TOP);
    	card_image.setContentDescription("Jordyn Wieber wins gold!");
    	
        // Title text
    	TextView title_tv = new TextView(c);
    	title_tv.setId(title_id);
    	LayoutParams lp_title = (LayoutParams) new RelativeLayout.LayoutParams(Feed.WP, Feed.WP);
    	title_tv.setLayoutParams(lp_title);
        title_tv.setTextColor(c.getResources().getColor(R.color.daapr_blue));
        Spannable title_text = new SpannableString("Sample Title: World Champion Wieber");
        title_text.setSpan(new RelativeSizeSpan(1f), 0, title_text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        title_tv.setText(title_text);
        lp_title.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        lp_title.addRule(RelativeLayout.RIGHT_OF, card_image.getId());
        
        // Source text
        TextView source_tv = new TextView(c);
        source_tv.setId(source_id);
        LayoutParams lp_source = (LayoutParams) new RelativeLayout.LayoutParams(Feed.WP, Feed.WP);
    	source_tv.setLayoutParams(lp_source);
    	Spannable source_text = new SpannableString("Sample Source: USA Gymnastics");
        source_text.setSpan(new RelativeSizeSpan(0.8f), 0, source_text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        source_tv.setTextColor(c.getResources().getColor(R.color.source_gray));
        source_tv.setText(source_text);
        lp_source.addRule(RelativeLayout.BELOW, title_tv.getId());
        lp_source.addRule(RelativeLayout.ALIGN_LEFT, title_tv.getId());
        
        // User text
        TextView user_tv = new TextView(c);
        user_tv.setId(user_id);
        LayoutParams lp_user = (LayoutParams) new RelativeLayout.LayoutParams(Feed.WP, Feed.WP);
    	user_tv.setLayoutParams(lp_user);
    	Spannable user_text = new SpannableString("Mckayla Maroney");
        user_text.setSpan(new RelativeSizeSpan(0.8f), 0, user_text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        user_tv.setTextColor(c.getResources().getColor(R.color.daapr_blue));
        user_tv.setText(user_text);
        lp_user.setMargins(0, 20, 0, 0);
        lp_user.addRule(RelativeLayout.ALIGN_LEFT, source_tv.getId());
        lp_user.addRule(RelativeLayout.BELOW, source_tv.getId());
        
        // Time stamp text
        TextView time_tv = new TextView(c);
        time_tv.setId(time);
        LayoutParams lp_time = (LayoutParams) new RelativeLayout.LayoutParams(Feed.WP, Feed.WP);
    	time_tv.setLayoutParams(lp_time);
    	Spannable time_text = new SpannableString("4 months ago");
        time_text.setSpan(new RelativeSizeSpan(0.8f), 0, time_text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        time_tv.setTextColor(c.getResources().getColor(R.color.daapr_gray));
        time_tv.setText(time_text);
        lp_time.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lp_time.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        
        layout.addView(card_image);
        layout.addView(title_tv);
        layout.addView(source_tv);
        layout.addView(user_tv);
        layout.addView(time_tv);
	}
	
	public class CardAdapter extends ArrayAdapter {
		public CardAdapter(Context context, int resource, int textViewResourceId) {
			super(context, resource, textViewResourceId);
		}

		int count = 20; /* starting amount */

	    public int getCount() { return count; }
	    public Object getItem(int pos) { return pos; }
	    public long getItemId(int pos) { return pos; }

	    public View getView(int pos, View v, ViewGroup p) {
	            Card card = new Card(getApplicationContext(), pos, pos, pos, pos, pos);
	            return card.layout;
	    }
	}
}
