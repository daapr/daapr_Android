package com.example.myfirstapp;

import java.util.ArrayList;

import junit.framework.Test;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;


public class CardAdapter extends ArrayAdapter<Card> {
	static int CARD_WIDTH = 200;
	static int MP = LayoutParams.MATCH_PARENT;
	static int WP = LayoutParams.WRAP_CONTENT;
	
	Context context;
    int layoutResourceId;
    ArrayList<Card> data = null;
	public CardAdapter(Context app_context, int layoutResourceId, ArrayList<Card> data) {
		super(app_context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = app_context;
		this.data = data;
	}
    
	int count = 20; // starting amount

    public int getCount() { return count; }
    public long getItemId(int pos) { return pos; }
    public void updateData(ArrayList<Card> new_data) {
    	data.addAll(new_data);
//    	notifyDataSetChanged(); 
	}

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
//    	Card card = new Card(c, id, title_id, source_id, user_id, time);
//        return card.layout;
        View row = convertView;
        CardHolder holder = null;
        Card card = data.get(position);
        System.out.println("CURRENT POSITION IS " + position);
        System.out.println("NEW title SHOULD be " + card.title);
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new CardHolder();
            holder.iv = (ImageView)row.findViewById(R.id.card_image);
//            System.out.println("Id of image: " + card.card_image.getId());
            if (card.image_drawable == null) {
            	System.out.println("Image " + position + " is null!! Replacing with Wieber");
            	holder.iv.setImageResource(R.drawable.wieber);
            } else {
            	holder.iv.setImageDrawable(card.image_drawable);
            }
            holder.tv_title = (TextView)row.findViewById(R.id.title);
            holder.tv_title.setTextColor(context.getResources().getColor(R.color.daapr_blue));
            holder.tv_title.setText(card.title);
            holder.tv_source = (TextView)row.findViewById(R.id.source);
            holder.tv_source.setTextColor(context.getResources().getColor(R.color.source_gray));
            holder.tv_source.setText(card.site_name);
            holder.tv_user = (TextView)row.findViewById(R.id.user);
            holder.tv_user.setTextColor(context.getResources().getColor(R.color.daapr_blue));
            holder.tv_user.setText(card.micropost_user_name);
            holder.tv_time = (TextView)row.findViewById(R.id.time);
            holder.tv_time.setText(card.reshare_created_at);
            row.setTag(holder);
//            row.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					// Box that covers entire card
////					RelativeLayout card_options_layout = new RelativeLayout(context);
//					CardHolder card_options_layout = new CardHolder();
////					card_options_layout.setLayoutParams((LayoutParams) new RelativeLayout.LayoutParams(
////							MP, CARD_WIDTH));
////					card_options_layout.setBackgroundColor(context.getResources().getColor(R.color.trans_blue));
//
//					card_options_layout.view_tv = new TextView(context);
//					TextView view_tv = card_options_layout.view_tv;
//			    	view_tv.setText("View this");
//			        LayoutParams lp_view_tv = (LayoutParams) new RelativeLayout.LayoutParams(MP,
//				        	CARD_WIDTH);
//			        view_tv.setLayoutParams(lp_view_tv);
//			        lp_view_tv.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//			        view_tv.setBackgroundColor(context.getResources().getColor(R.color.daapr_blue));
//			        row.setTag(card_options_layout);
//			        card_options_layout.addView(view_tv);
//			        parent.addView(card_options_layout);
//				}
//            });
        }
        else
        {
            holder = (CardHolder)row.getTag();
            if (card.image_drawable == null) {
            	System.out.println("Image " + position + " is null!! Replacing with Wieber");
            	holder.iv.setImageResource(R.drawable.wieber);
            } else {
            	holder.iv.setImageDrawable(card.image_drawable);
            }
            holder.tv_title.setTextColor(context.getResources().getColor(R.color.daapr_blue));
            holder.tv_title.setText(card.title);
            holder.tv_source.setTextColor(context.getResources().getColor(R.color.source_gray));
            holder.tv_source.setText(card.site_name);
            holder.tv_user.setTextColor(context.getResources().getColor(R.color.daapr_blue));
            holder.tv_user.setText(card.micropost_user_name);
            holder.tv_time.setText(card.reshare_created_at);
            row.setTag(holder);
        }
        return row;
    }
    
    static class CardHolder
    {
        ImageView iv;
        TextView tv_title;
        TextView tv_user;
        TextView tv_source;
        TextView tv_time;
        
        TextView view_tv;
    }
}