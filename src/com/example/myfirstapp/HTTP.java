package com.example.myfirstapp;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
 
import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.net.Uri;
import android.net.Uri.Builder;
import android.widget.TextView;

public class HTTP {

	private final String USER_AGENT = "Mozilla/5.0";
	 
//	public static void main(String[] args) throws Exception {
// 
//		HTTP http = new HTTP();
//		String url = "http://www.google.com/search?q=mkyong";
//		String url2 = "https://selfsolve.apple.com/wcResults.do";
//		String postParams = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
//		
//		System.out.println("Testing 1 - Send Http GET request");
//		http.sendGet(url);
// 
//		System.out.println("\nTesting 2 - Send Http POST request");
//		http.sendPost(url2, postParams);
// 
//		build_uri();
//	}
	
	// Returns (the string of) the URI given the source path and the params.
	public static String build_uri(String path, String[] params) {
		Uri.Builder builder;
		builder = new Builder();
		builder.scheme("http");
		builder.authority("0.0.0.0:3000");
		builder.appendPath("turtles").appendPath("types");
		builder.appendQueryParameter("type", "1").appendQueryParameter("sort", "relevance");
		//builder.buildUpon().appendPath("turtles").appendPath("types").appendQueryParameter("type", "1").appendQueryParameter("sort", "relevance");
		String myUrl = builder.build().toString();
		System.out.println(myUrl);
		return myUrl;
	}

	public void sendGet(String url) throws Exception {
		
		 
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		// optional default is GET
		con.setRequestMethod("GET");
 
		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		//print result
		System.out.println(response.toString());		
	}
	
	public void sendPost(String url, String urlParams) throws Exception {
		
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
 
		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
 
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParams);
		wr.flush();
		wr.close();
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParams);
		System.out.println("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		//print result
		System.out.println(response.toString());
	}
	
	// Sends an Http POST request given the source path and urlParams
	public static void postData(String path, List<BasicNameValuePair> urlParams) {
	    // Create a new HttpClient and Post Header
	    HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost(path);

	    try {
	        // Add your data
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	        for (BasicNameValuePair data : urlParams) {
	        	nameValuePairs.add(data);
	        }
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	        // Execute HTTP Post Request
	        httpclient.execute(httppost);

	    } catch (ClientProtocolException e) {
	    	return;
	    } catch (IOException e) {
	    	return;
	    }
	} 

}
