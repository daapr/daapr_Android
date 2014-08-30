package com.example.myfirstapp;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


public class HTTP {
	
	/** Sends an Http GET request given the source path and urlParams. See example at
	 *  http://stackoverflow.com/questions/20321799/android-http-get. Uses Apache. */
	public static String getData(String path, List<BasicNameValuePair> urlParams) throws URISyntaxException {
		HttpResponse response = null;
	    HttpClient httpclient = new DefaultHttpClient();
	    HttpGet request = null;
	    request = new HttpGet();
	    try {
	        // Add the data
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	        for (BasicNameValuePair data : urlParams) {
	        	nameValuePairs.add(data);
	        }
	    	URI website = new URI(path+ "email=happymealsadarteries%40gmail.com&password=password123");
	    	request.setURI(website);

	    	// Print the HTTP method to make sure it's get
	    	System.out.println("Http method: " + request.getMethod());
	        // Execute HTTP Get Request
	    	response = httpclient.execute(request);
	        
	        String responseString = new BasicResponseHandler().handleResponse(response);
	        System.out.println(responseString);
	        return responseString;
	        
	    } catch (ClientProtocolException e) {
	    	e.printStackTrace();
	    	System.out.println("http code is " + response.getStatusLine().toString());
	    	return "Client error";
	    } catch (IOException e) {
	    	e.printStackTrace();
	    	return "IO error";
	    }
	}
	
	/** Sends an Http POST request given the source path and urlParams. See example at
	 *  http://stackoverflow.com/questions/2938502/sending-post-data-in-android. Uses Apache. */
	public static String postData(String path, List<BasicNameValuePair> urlParams) {
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
	    	System.out.println("url is: " + path);
	    	HttpEntity entity = new UrlEncodedFormEntity(nameValuePairs, "utf-8");
	        httppost.setEntity(entity);
	    	String content = EntityUtils.toString(entity);
	    	System.out.println("content is: " + content);

	    	// Print HTTP method to make sure it's POST
	    	System.out.println("Http method: " + httppost.getMethod());
	        // Execute HTTP Post Request
	    	response = httpclient.execute(httppost);
	        
	        // Convert response to string
	        String responseString = new BasicResponseHandler().handleResponse(response);
	        System.out.println("The API key is " + responseString);
	        return responseString;
	        
	    } catch (ClientProtocolException e) {
	    	e.printStackTrace();
	    	System.out.println("http code is " + response.getStatusLine().toString());
	    	return "Client error";
	    } catch (IOException e) {
	    	e.printStackTrace();
	    	return "IO error";
	    }
	}

	
}
