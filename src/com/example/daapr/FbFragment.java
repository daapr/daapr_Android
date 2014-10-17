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
	String fb_id, first_name, last_name, fb_email, fb_image; // attributes needed for FB login
	private UiLifecycleHelper uiHelper; // helps track session state changes
	String api_key; // attribute needed for regular sign in
	
	// Returns if a network connection is available. Used for debugging.
	public boolean availableConnection() {
	    ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
	    return (networkInfo != null && networkInfo.isConnected()); // fetch data if true; else display error
	}

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
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
	        						Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.signin, container, false);
	    // Actual user input values.
    	final EditText email_et = (EditText) view.findViewById(R.id.username_et);
    	final EditText password_et = (EditText) view.findViewById(R.id.password_et);
		Button btn = (Button) view.findViewById(R.id.signin);
		btn.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    	System.out.println("IN ON CLICK FOR SIGN IN in fragment onCreateView");
			    try {
			    	String url = "https://daapr.com/rest_sign_in?";
					String email = "test@example.com";
					String password = "testing123";
			    	// Replace with below code when using the actual user's inputs
//			    	String email = email_et.getText().toString();
//			    	String password = password_et.getText().toString();
					List<BasicNameValuePair> urlParams = new ArrayList<BasicNameValuePair>(2);
			        urlParams.add(new BasicNameValuePair("email", email));
			    	urlParams.add(new BasicNameValuePair("password", password));
			    	new SignInTask().execute(url, urlParams);
				} catch (Exception e) {
					e.printStackTrace();
				}
		    }
		});
	    LoginButton authButton = (LoginButton) view.findViewById(R.id.authButton);
	    // Allow fragment to receive onActivityResult() call
	    authButton.setFragment(this);
	    authButton.setReadPermissions(Arrays.asList("email", "public_profile"));
	    return view;
	}
	
	/** Currently unused. Should return the user's Facebook profile picture as a bitmap given
	 *  his or her userID. 
	 * @throws IOException  */
//	Bitmap bitmap = getFacebookProfilePicture(userId);
	public static Bitmap getFacebookProfilePicture(String userID) throws IOException {
	    URL imageURL = new URL("https://graph.facebook.com/" + userID + "/picture?type=large");
	    Bitmap bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
	    return bitmap;
	}
	
	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
		if (state.isOpened()) {
	        Request.newMeRequest(session, new Request.GraphUserCallback() {
				@Override
				public void onCompleted(GraphUser user, Response response) {
					if (user != null) {
		                // Set necessary FB user info
						buildUserInfo(user);
						
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
	    } else {
	    	System.out.println("---State is closed---");
	    }
	}
	
	private void buildUserInfo(GraphUser user) {
        fb_id = user.getId();
        first_name = user.getFirstName();
        last_name = user.getLastName();
        fb_email = (String) user.getProperty("email");
        fb_image = "https://graph.facebook.com/" + fb_id + "/picture";
        // Remove when finished testing.
        System.out.println("USER INFO! ---> " + fb_id + "; " + first_name + "; " +
            last_name + "; " + fb_email + "; ");
	}
	
	/** Called when the user clicks the Sign In button or the Facebook Login button */
	public void signIn() {
		Intent feed = new Intent(getActivity(), Feed.class);
		feed.putExtra("API_KEY", api_key);
		startActivity(feed);
	}
	
	private class SignInTask extends AsyncTask<Object, Void, Object[]> {
	    @SuppressWarnings("unchecked")
		protected Object[] doInBackground(Object... params) {
	        return HTTP.sign_in((String) params[0],(List<BasicNameValuePair>) params[1]);
	    }

	    protected void onPostExecute(Object[] result) {
	    	// tv1 appears after the signin button is released; for testing purposes
			TextView tv1 = (TextView) getActivity().findViewById(R.id.testing);
//	        Spannable title = new SpannableString(tv1.getText());
//	        title.setSpan(new RelativeSizeSpan(1f), 0, title.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
	        tv1.setText("api_key: " + result[1]);
	        
	        if ((Boolean) result[0]) {
	        	api_key = (String) result[1];
	        	if (api_key != null) { signIn(); }
	        } else {
	        	TextView error = (TextView) getActivity().findViewById(R.id.signin_error_tv);
	        	error.setVisibility(0);
	        	error.setText((String) result[1]);
	        }
	    }
	}
}