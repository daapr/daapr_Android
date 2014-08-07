package com.example.myfirstapp;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.HttpResponseException;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.Uri.Builder;
import android.view.View;


public class HTTP {

	private final static String USER_AGENT = "Mozilla/5.0";
	 
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
		builder.authority(path);
//		builder.authority("0.0.0.0:3000");
//		builder.appendPath("turtles").appendPath("types");
//		for (param : params) {
//			
//		}
//		builder.appendQueryParameter("type", "1").appendQueryParameter("sort", "relevance");
//		builder.buildUpon().appendPath("turtles").appendPath("types").appendQueryParameter("type", "1").appendQueryParameter("sort", "relevance");
		String myUrl = builder.build().toString();
		System.out.println(myUrl);
		return myUrl;
	}

	// Not currently in use. See example at http://www.mkyong.com/java/how-to-send-http-request-getpost-in-java/.
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
	
	// See example at http://www.mkyong.com/java/how-to-send-http-request-getpost-in-java/.
	public static void sendPost(String url, String urlParams) throws Exception {
		
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
	
	// Sends an Http POST request given the source path and urlParams. See example at
	// http://stackoverflow.com/questions/2938502/sending-post-data-in-android. Uses Apache.
	public static String postData(String path, List<BasicNameValuePair> urlParams) {
	    // Create a new HttpClient and Post Header
		
		//additional path
//		String extend = path + "email=dummy1@example.com&password=dummyone";
//		String extend2 = path + "email=" + encodeURI("dummy1@example.com") + "&password=dummyone";
		
		HttpResponse response = null;
	    HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = null;
	    httppost = new HttpPost(path);
//		try {
//			String extend3 = path + "email=" + URLEncoder.encode("dummy1@example.com", "UTF-8")
//					+ "&password=dummyone";
//			httppost = new HttpPost(extend3);
//		} catch (UnsupportedEncodingException e1) {
//			System.out.println("url encoding didn't work. stack trace:\n");
//			e1.printStackTrace();
//		}
	    

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

	        // Execute HTTP Post Request
	        response = httpclient.execute(httppost);
	        
	        // Convert response to string
//	        HttpEntity entity_response = response.getEntity();
//	        String responseString = EntityUtils.toString(entity_response, "UTF-8");
//	        System.out.println(responseString);
	        
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

	// Sends POST request using HttpURLConnection. If setDoOutput(true) is not called,
	// the request will be GET. See example at
	// http://developer.android.com/reference/java/net/HttpURLConnection.html.
	public static void post(URL url) throws IOException {
		int len = 500;
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		try {
			urlConnection.setDoOutput(true);
			urlConnection.setChunkedStreamingMode(0);
				
			OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
			writeStream(out);
				
			InputStream in = new BufferedInputStream(urlConnection.getInputStream());
			readStream(in, len);
		} finally {
			urlConnection.disconnect();
		}
	}
	
	// This function was needed for post() but I'm not sure what it should be.
	private static void writeStream(OutputStream out) {
//		writeBytes(out);
//		out.flush();
//		out.close();
		return;
	}

	// Reads an InputStream and converts it to a String.
	private static String readStream(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
	    Reader reader = null;
	    reader = new InputStreamReader(stream, "UTF-8");        
	    char[] buffer = new char[len];
	    reader.read(buffer);
	    return new String(buffer);
	}
	
	
}
