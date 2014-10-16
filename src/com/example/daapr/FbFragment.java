package com.example.daapr;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

public class FbFragment extends Fragment {
	TextView userInfoTextView;
	String fb_id, first_name, last_name, fb_email, fb_image;
	private UiLifecycleHelper uiHelper;
	String api_key;
	
//	// Returns if a network connection is available.
//	public boolean availableConnection() {
//	    ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
//	    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//	    return (networkInfo != null && networkInfo.isConnected()); // fetch data if true; else display error
//	}

	private Session.StatusCallback fbStatusCallback = new Session.StatusCallback() {
	    @Override
	    public void call(Session session, SessionState state, Exception exception) {
	        onSessionStateChange(session, state, exception);
	    }
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    uiHelper = new UiLifecycleHelper(getActivity(), fbStatusCallback);
	    uiHelper.onCreate(savedInstanceState);
	}
	
	@Override
	public void onResume() {
	    super.onResume();
	    // For scenarios where the main activity is launched and user
	    // session is not null, the session state change notification
	    // may not be triggered. Trigger it if it's open/closed.
	    Session session = Session.getActiveSession();
	    if (session != null &&
	           (session.isOpened() || session.isClosed()) ) {
	        onSessionStateChange(session, session.getState(), null);
	    }
	    uiHelper.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onPause() {
	    super.onPause();
	    uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
	    super.onDestroy();
	    uiHelper.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    uiHelper.onSaveInstanceState(outState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, 
	        ViewGroup container, 
	        Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.signin, container, false);
	    
//	    // Actual user input values.
//    	EditText email_unused = (EditText) getActivity().findViewById(R.id.username_et);
//    	EditText password_unused = (EditText) getActivity().findViewById(R.id.password_et);
//    	email_unused.setBackgroundResource(android.R.drawable.editbox_background_normal);
//    	password_unused.setBackgroundResource(android.R.drawable.editbox_background_normal);
//		
//		Button btn = (Button) getActivity().findViewById(R.id.signin);
//		btn.setOnClickListener(new View.OnClickListener() {
//		    @Override
//		    public void onClick(View v) {
//		    	System.out.println("IN ON CLICK FOR SIGN IN");
//			    try {
//			    	String url = "https://daapr.com/rest_sign_in?";
//					String email = "test@example.com";
//					String password = "testing123";
//					List<BasicNameValuePair> urlParams = new ArrayList<BasicNameValuePair>(2);
//			        urlParams.add(new BasicNameValuePair("email", email));
//			    	urlParams.add(new BasicNameValuePair("password", password));
//			    	if (availableConnection()) { System.out.println("There is an available network connection!"); }
//			    	else { System.out.println("No network connection!!"); }
//			    	new SignInTask().execute(url, urlParams);
//				} catch (Exception e) {
//					System.out.println("Sign in failed!");
//					e.printStackTrace();
//				}
//		    }
//		});
//	    
		userInfoTextView = (TextView) view.findViewById(R.id.userInfoTextView);
	    LoginButton authButton = (LoginButton) view.findViewById(R.id.authButton);
	    authButton.setFragment(this);
	    authButton.setReadPermissions(Arrays.asList("email", "public_profile"));

	    return view;
	}
	
	/** Currently unused. Should return the user's Facebook profile picture as a bitmap given
	 *  his or her userID. 
	 * @throws IOException  */
	public static Bitmap getFacebookProfilePicture(String userID) throws IOException {
	    URL imageURL = new URL("https://graph.facebook.com/" + userID + "/picture?type=large");
	    Bitmap bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());

	    return bitmap;
	}
//	Bitmap bitmap = getFacebookProfilePicture(userId);
	
	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
		if (state.isOpened()) {
	        userInfoTextView.setVisibility(View.VISIBLE);
	        Request.newMeRequest(session, new Request.GraphUserCallback() {
				@Override
				public void onCompleted(GraphUser user, Response response) {
					
					if (user != null) {
		                // Display the parsed user info
		                userInfoTextView.setText(buildUserInfoDisplay(user));
		                // All necessary user info has been set
		                // Call rest_facebook_login
		                String url = "https://orangeseven7.com/rest_facebook_login?";
						List<BasicNameValuePair> urlParams = new ArrayList<BasicNameValuePair>(2);
				        urlParams.add(new BasicNameValuePair("email", fb_email));
				        urlParams.add(new BasicNameValuePair("first_name", first_name));
				    	urlParams.add(new BasicNameValuePair("last_name", last_name));
				    	urlParams.add(new BasicNameValuePair("image", fb_image));
				    	urlParams.add(new BasicNameValuePair("facebook_id", fb_id));
				    	new SignInTask().execute(url, urlParams);
		            }
				}
            }).executeAsync();
	    } else if (state.isClosed()) {
	        userInfoTextView.setVisibility(View.INVISIBLE);
	    }
	}
	
	private String buildUserInfoDisplay(GraphUser user) {
	    StringBuilder userInfo = new StringBuilder("");

    	System.out.println("GETTING USER INFO!!");
        fb_id = user.getId();
        userInfo.append(String.format("Id: %s\n\n", fb_id));
        first_name = user.getFirstName();
        userInfo.append(String.format("First name: %s\n\n", first_name));
        last_name = user.getLastName();
        userInfo.append(String.format("Last name: %s\n\n", last_name));
        fb_email = (String) user.getProperty("email");
        userInfo.append(String.format("Email: %s\n\n", fb_email));
        fb_image = "https://graph.facebook.com/" + fb_id + "/picture";
        userInfo.append(String.format("Image URL: %s\n\n", fb_image));
        System.out.println("USER INFO! ---> " + fb_id + "; " + first_name + "; " +
            last_name + "; " + fb_email + "; ");
	    return userInfo.toString();
	}
	
	/** Called when the user clicks the Facebook Login button */
	public void signIn() {
		Intent feed = new Intent(getActivity(), Feed.class);
		feed.putExtra("API_KEY", api_key);
		startActivity(feed);
	}
	
	private class SignInTask extends AsyncTask<Object, Void, String> {
	    @SuppressWarnings("unchecked")
		protected String doInBackground(Object... params) {
	        return HTTP.sign_in((String) params[0],(List<BasicNameValuePair>) params[1]);
	    }

	    protected void onPostExecute(String result) {
	    	// tv1 appears after the signin button is released; for testing purposes
			TextView tv1 = (TextView) getActivity().findViewById(R.id.testing);
	        tv1.setTextColor(getResources().getColor(R.color.daapr_blue));
	        Spannable title = new SpannableString(tv1.getText());
	        title.setSpan(new RelativeSizeSpan(1f), 0, title.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
	        tv1.setText("api_key: " + result);
	        
	        api_key = result;
	        if (api_key != null && api_key.indexOf("error") == -1) { signIn(); }
	    }
	}
	
}
