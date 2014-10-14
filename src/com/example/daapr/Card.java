package com.example.daapr;

import java.io.InputStream;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;

public class Card extends Activity {
	//#0-micropost_id, 1-url, 2-title,3-image_url,4-video_url,5-site_name,6-reshare_user_id,
	// 7-micropost_user_name,8-reshare_user_name, 9-reshare_created_at
    //#10-reshare_id, 11-micropost_user_id, 12-like_num, 13-current_user_liked, 14-reshare_num,
	// 15-current_user_reshared, 16-caption, 17-comment_num, 18-?, 19-?, 20-micropost_description
	public int micropost_id;
	public String url;
	public String title;
	private String image_url;
	private String video_url;
	public String site_name;
	private int reshare_user_id;
	public String micropost_user_name;
	private String reshare_user_name;
	public String reshare_created_at;
	private int reshare_id;
	private int micropost_user_id;
	private int like_num;
	private String[] current_user_liked;
	private int reshare_num;
	private String current_user_reshared;
	private String caption;
	private int comment_num;
	private String micropost_description;
	
	public ImageView card_image;
	public Drawable image_drawable;
	
	static int CARD_WIDTH = 200;
	static int MP = LayoutParams.MATCH_PARENT;
	static int WP = LayoutParams.WRAP_CONTENT;
	
	public Card(Context c, Object result, int id) {
//		System.out.println("CARD ID IS " + id);
		Object[] card_array = (Object[]) result;
		
		micropost_id = (Integer) card_array[0];
    	image_url = (String) card_array[3];
        site_name = (String) card_array[5];
        url = (String) card_array[1];
    	title = (String) card_array[2];
        reshare_created_at = (String) card_array[9];
        micropost_user_name = (String) card_array[7];
        new LoadImageTask().execute();
	}
	
	/** Load an image from url and set the source name of the url to be srcName. */
	public static Drawable loadImage(String url, String srcName) {
	    try {
	        InputStream is = (InputStream) new URL(url).getContent();
	        Drawable d = Drawable.createFromStream(is, srcName);
	        return d;
	    } catch (Exception e) {
	    	System.out.println("Error: In loadImage");
	    	e.printStackTrace();
	        return null;
	    }
	}
	
	/** Asynchronous task that loads images. */
	class LoadImageTask extends AsyncTask<Void, Void, Drawable> {

	    protected Drawable doInBackground(Void... params) {
	        try {
//	        	System.out.println("Image url is: " + image_url);
	        	return loadImage(image_url, site_name);
	        } catch (Exception e) {
	        	System.out.println("Error: In LoadImageTask");
	            e.printStackTrace();
	            return null;
	        }
	    }

	    protected void onPostExecute(Drawable image) {
	    	image_drawable = image;
	    }
	}
}
