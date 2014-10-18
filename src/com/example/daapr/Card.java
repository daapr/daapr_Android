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
	// #0-micropost_id, 1-url,
	// 2-title,3-image_url,4-video_url,5-site_name,6-reshare_user_id,
	// 7-micropost_user_name,8-reshare_user_name, 9-reshare_created_at
	// #10-reshare_id, 11-micropost_user_id, 12-like_num, 13-current_user_liked,
	// 14-reshare_num,
	// 15-current_user_reshared, 16-caption, 17-comment_num, 18-?, 19-?,
	// 20-micropost_description
	private int micropostId;
	private String url;
	private String title;
	private String imageUrl;
	private String videoUrl;
	private String siteName;
	private int reshareUserId;
	private String micropostUserName;
	private String reshareUserName;
	private String reshareCreatedAt;
	private int reshareId;
	private int micropostUserId;
	private int likeNum;
	private String[] currentUserLiked;
	private int reshareNum;
	private String currentUserReshared;
	private String caption;
	private int commentNum;
	private String micropostDescription;
	private ImageView cardImage;
	private Drawable imageDrawable;

	public Card(Context c, Object result) {

		Object[] card_array = (Object[]) result;

		micropostId = (Integer) card_array[0];
		imageUrl = (String) card_array[3];
		siteName = (String) card_array[5];
		url = (String) card_array[1];
		title = (String) card_array[2];
		reshareCreatedAt = (String) card_array[9];
		micropostUserName = (String) card_array[7];
	}

	public int getMicropostId() {
		return micropostId;
	}

	public void setMicropostId(int micropostId) {
		this.micropostId = micropostId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCardTitle() {
		return title;
	}

	public void setCardTitle(String title) {
		this.title = title;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public int getReshareUserId() {
		return reshareUserId;
	}

	public void setReshareUserId(int reshareUserId) {
		this.reshareUserId = reshareUserId;
	}

	public String getMicropostUserName() {
		return micropostUserName;
	}

	public void setMicropostUserName(String micropostUserName) {
		this.micropostUserName = micropostUserName;
	}

	public String getReshareUserName() {
		return reshareUserName;
	}

	public void setReshareUserName(String reshareUserName) {
		this.reshareUserName = reshareUserName;
	}

	public String getReshareCreatedAt() {
		return reshareCreatedAt;
	}

	public void setReshareCreatedAt(String reshareCreatedAt) {
		this.reshareCreatedAt = reshareCreatedAt;
	}

	public int getReshareId() {
		return reshareId;
	}

	public void setReshareId(int reshareId) {
		this.reshareId = reshareId;
	}

	public int getMicropostUserId() {
		return micropostUserId;
	}

	public void setMicropostUserId(int micropostUserId) {
		this.micropostUserId = micropostUserId;
	}

	public int getLikeNum() {
		return likeNum;
	}

	public void setLikeNum(int likeNum) {
		this.likeNum = likeNum;
	}

	public String[] getCurrentUserLiked() {
		return currentUserLiked;
	}

	public void setCurrentUserLiked(String[] currentUserLiked) {
		this.currentUserLiked = currentUserLiked;
	}

	public int getReshareNum() {
		return reshareNum;
	}

	public void setReshareNum(int reshareNum) {
		this.reshareNum = reshareNum;
	}

	public String getCurrentUserReshared() {
		return currentUserReshared;
	}

	public void setCurrentUserReshared(String currentUserReshared) {
		this.currentUserReshared = currentUserReshared;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public int getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(int commentNum) {
		this.commentNum = commentNum;
	}

	public String getMicropostDescription() {
		return micropostDescription;
	}

	public void setMicropostDescription(String micropostDescription) {
		this.micropostDescription = micropostDescription;
	}

	public ImageView getCardImage() {
		return cardImage;
	}

	public void setCardImage(ImageView cardImage) {
		this.cardImage = cardImage;
	}

	public Drawable getImageDrawable() {
		return imageDrawable;
	}

	public void setImageDrawable(Drawable imageDrawable) {
		this.imageDrawable = imageDrawable;
	}
}