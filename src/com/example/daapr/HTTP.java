package com.example.daapr;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import android.annotation.SuppressLint;


public class HTTP {
	
	/** Sends an Http POST request given the source path and urlParams. See example at
	 *  http://stackoverflow.com/questions/2938502/sending-post-data-in-android. Uses Apache. */
	public static Object[] sign_in(String path, List<BasicNameValuePair> urlParams) {
		HttpResponse response = null;
	    HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = null;
	    httppost = new HttpPost(path);
	    try {
	        // Add the values
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	        for (BasicNameValuePair data : urlParams) {
	        	nameValuePairs.add(data);
	        }
	    	HttpEntity entity = new UrlEncodedFormEntity(nameValuePairs, "utf-8");
	        httppost.setEntity(entity);
	        
	    	// Execute HTTP Post Request
	    	response = httpclient.execute(httppost);
	    	// Convert HTTP response into JSONArray
	    	BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
	    	StringBuilder builder = new StringBuilder();
	    	for (String line = null; (line = reader.readLine()) != null;) {
	    	    builder.append(line).append("\n");
	    	}
	    	JSONTokener tokener = new JSONTokener(builder.toString());
			JSONArray jsonArray = new JSONArray(tokener);
			// Convert JSONArray into array result
	    	Object[] result = new Object[jsonArray.length()];
	    	for (int i = 0; i < jsonArray.length(); i++) {
	    		result[i] = jsonArray.get(i);
	    	}
	    	return result;
	    } catch (ClientProtocolException e) {
	    	e.printStackTrace();
	    	return null;
	    } catch (IOException e) {
	    	e.printStackTrace();
	    	return null;
	    } catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/** Returns an array of the feed items by sending an Http POST request. See example at
	 *  http://stackoverflow.com/questions/18651641/how-to-put-http-response-into-array-in-android. */
	@SuppressLint("NewApi")
	public static Object[] append_feed(String path, List<BasicNameValuePair> urlParams) {
		HttpResponse response = null;
	    HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = null;
	    httppost = new HttpPost(path);
	    try {
	    	// Add the data
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	        for (BasicNameValuePair data : urlParams) {
	        	nameValuePairs.add(data);
	        }
	    	HttpEntity entity = new UrlEncodedFormEntity(nameValuePairs, "utf-8");
	        httppost.setEntity(entity);

	        String content = EntityUtils.toString(entity);
	    	System.out.println("URL is: " + path + content);
	        
	        // Execute HTTP Post Request
	    	response = httpclient.execute(httppost);
	    	// Code from http://stackoverflow.com/questions/2845599/how-do-i-parse-json-from-a-java-httpresponse
	    	// Convert HTTP response into JSONArray
	    	BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
	    	StringBuilder builder = new StringBuilder();
	    	for (String line = null; (line = reader.readLine()) != null;) {
	    	    builder.append(line).append("\n");
	    	}
	    	JSONTokener tokener = new JSONTokener(builder.toString());
	    	JSONArray outerArray = new JSONArray(tokener);
	    	// Convert JSONArray into array result
	    	Object[] result = new Object[outerArray.length()];
	    	// i = 0 --> Boolean; i = 1 --> feed array
	    	for (int i = 0; i < outerArray.length(); i++){
	    		if (i == 0) {
	    			result[i] = outerArray.getBoolean(i);
	    		} else {
	    			// construct feed array (array of arrays)
		    		JSONArray feedArray = outerArray.getJSONArray(i);
			        Object[][] feedResult = new Object[feedArray.length()][];
			        // j represents the current card's position in the feed
			        for (int j = 0; j < feedArray.length(); j++){
			        	JSONArray cardArray = feedArray.getJSONArray(j);
			        	Object[] cardResult = new Object[cardArray.length()];
			        	// k represents the position of the current card element in the card
			        	for (int k = 0; k < cardArray.length(); k++) {
			        		cardResult[k] = cardArray.get(k);
			        	}
			        	feedResult[j] = cardResult;
			        }
			        // i should be 1
			        result[i] = feedResult;
	    		}
	    	}
	    	return result;
	        
	    } catch (ClientProtocolException e) {
	    	e.printStackTrace();
	    	return null;
	    } catch (IOException e) {
	    	e.printStackTrace();
	    	return null;
	    } catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
}
