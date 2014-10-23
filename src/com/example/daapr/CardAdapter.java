package com.example.daapr;

import java.util.ArrayList;

import junit.framework.Test;

import android.app.Activity;

import android.content.Context;

import android.graphics.Color;

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

	private CardHolder holder;
	private ArrayList<Card> data;
	private Context context;
	private int layoutResourceId;
	private int count;
	
	public CardAdapter(Context app_context, int layoutResourceId,
					ArrayList<Card> data) {
		super(app_context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = app_context;
		this.data = data;
		count = 20; // starting amount
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public long getItemId(int pos) {
		return pos;
	}

	public ArrayList<Card> getData() {
		return data;
	}

	@Override
	public View getView(int position, View convertView, final ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			// inflate the layout
			LayoutInflater inflater = ((Activity) context)
					.getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			// set the card holder
			holder = new CardHolder();
			holder.tv_title = (TextView) row.findViewById(R.id.title);
			holder.tv_source = (TextView) row.findViewById(R.id.source);
			holder.tv_user = (TextView) row.findViewById(R.id.user);
			holder.tv_time = (TextView) row.findViewById(R.id.time);
			holder.iv = (ImageView) row.findViewById(R.id.card_image);
			// store the holder with the view
			row.setTag(holder);
		} else {
			holder = (CardHolder) row.getTag();
		}

		if (position < data.size()) {
			Card card = data.get(position);
			// assign values if the object is not null
			if (card != null) {
				holder.tv_title.setText(card.getCardTitle());
				holder.tv_source.setText(card.getSiteName());
				holder.tv_user.setText(card.getMicropostUserName());
				holder.tv_time.setText(card.getReshareCreatedAt());
				holder.iv.setImageDrawable(card.getImageDrawable());
			}
		}
		return row;
	}

	static class CardHolder {
		ImageView iv;
		TextView tv_title;
		TextView tv_user;
		TextView tv_source;
		TextView tv_time;
		TextView view_tv;
	}

}