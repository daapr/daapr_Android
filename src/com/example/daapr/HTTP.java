package com.example.daapr;
import android.annotation.SuppressLint;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;


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
	        
	        //For testing purposes only
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
	
	/** Returns an array of the feed items by sending an Http POST request. See example at
	 *  http://stackoverflow.com/questions/18651641/how-to-put-http-response-into-array-in-android. */
	@SuppressWarnings("null")
	@SuppressLint("NewApi")
	public static Object[] append_feed(String path, List<BasicNameValuePair> urlParams) {
		System.out.println("In append_feed()");
		HttpResponse response = null;
	    HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = null;
	    httppost = new HttpPost(path);

	    // Delete this. Dummy array.
	    Object[] dummy = new Object[1];
	    try {
	    	// Add the data
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	        for (BasicNameValuePair data : urlParams) {
	        	nameValuePairs.add(data);
	        }
	    	HttpEntity entity = new UrlEncodedFormEntity(nameValuePairs, "utf-8");
	        httppost.setEntity(entity);
	    	String content = EntityUtils.toString(entity);
	    	System.out.println("content is: " + path + content);

	        // Execute HTTP Post Request
	    	response = httpclient.execute(httppost);
	    	
	    	System.out.println("About to convert Http response into outer array.");
	    	// Code from http://stackoverflow.com/questions/2845599/how-do-i-parse-json-from-a-java-httpresponse
	    	// Convert HTTP response into JSONArray
	    	BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
	    	StringBuilder builder = new StringBuilder();
	    	for (String line = null; (line = reader.readLine()) != null;) {
	    	    builder.append(line).append("\n");
	    	}
	    	JSONTokener tokener = new JSONTokener(builder.toString());
//	    	System.out.println(tokener);
	    	JSONArray outerArray = new JSONArray(tokener);
	    	
	    	System.out.println("About to create inner array");
	    	// Convert JSONArray into array
	    	Object[][] result = new Object[outerArray.length()][];
	    	for (int i = 0; i < outerArray.length(); i++){
	    		JSONArray innerArray = outerArray.getJSONArray(i);
		        Object[] innerResult = new Object[innerArray.length()];
		        for(int j = 0; j < innerArray.length(); j++){               
		            innerResult[j] = innerArray.get(j);               
		        }
		        result[i] = innerResult;
	    	}
	    	return result;
	        
	    } catch (ClientProtocolException e) {
	    	e.printStackTrace();
	    	System.out.println("http code is " + response.getStatusLine().toString());
	    	return dummy;
	    } catch (IOException e) {
	    	System.out.println("IN IOEXCEPTION");
	    	e.printStackTrace();
	    	return dummy;
	    } 
	    catch (JSONException e) {
	    	System.out.println("IN JSONEXCEPTION");
			e.printStackTrace();
			return dummy;
		}
	}

	
}
