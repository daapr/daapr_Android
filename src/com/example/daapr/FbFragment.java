package com.example.daapr;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

public class FbFragment extends Fragment implements OnEditorActionListener {
	private String fb_id, first_name, last_name, fb_email, fb_image; // attributes needed for FB login
	private UiLifecycleHelper uiHelper; // helps track fragment state changes
	private String api_key; // attribute needed for sign in (regular and FB)
	private SharedPreferences sharedPref; // tracks app's session state
	private Button btn;
	private String root_url = "https://www.orangeseven7.com";
	
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    // Get a handle to shared preferences
	    sharedPref = getActivity().getSharedPreferences("com.example.daapr.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE);
		// Read from shared preferences
		String session_api_key = sharedPref.getString("api_key", null);
		if (session_api_key == null) {
			// Render sign in page
			 View view = inflater.inflate(R.layout.signin, container, false);
		    // Actual user input values
	    	final EditText email_et = (EditText) view.findViewById(R.id.username_et);
	    	final EditText password_et = (EditText) view.findViewById(R.id.password_et);
			btn = (Button) view.findViewById(R.id.signin);
			btn.setOnClickListener(new View.OnClickListener() {
			    @Override
			    public void onClick(View v) {
				    try {
				    	String url = root_url + "/rest_sign_in?";
				    	String email = email_et.getText().toString();
				    	String password = password_et.getText().toString();
				 
				    	if (email.equals("")) {
				    		Toast toast = Toast.makeText(getActivity(), "You must enter a valid email.", Toast.LENGTH_SHORT);
				    		toast.show();
				    	} else if (password.equals("")) {
				    		Toast toast = Toast.makeText(getActivity(), "You must enter a valid password.", Toast.LENGTH_SHORT);
				    		toast.show();
				    	} else {
							List<BasicNameValuePair> urlParams = new ArrayList<BasicNameValuePair>(2);
					        urlParams.add(new BasicNameValuePair("email", email));
					    	urlParams.add(new BasicNameValuePair("password", password));
					    	new SignInTask().execute(url, urlParams);
				    	}
					} catch (Exception e) {
						e.printStackTrace();
					}
			    }
			});
			
			password_et.setOnEditorActionListener(this);
			
			// Set up on click for creating a new account
			TextView newAccountLink = (TextView) view.findViewById(R.id.create_account_link);
			newAccountLink.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String url = root_url;
					Intent i = new Intent(Intent.ACTION_VIEW);
					i.setData(Uri.parse(url));
					startActivity(i);
				}
			});
		    LoginButton authButton = (LoginButton) view.findViewById(R.id.authButton);
		    // Allow fragment to receive onActivityResult() call
		    authButton.setFragment(this);
		    authButton.setReadPermissions(Arrays.asList("email", "public_profile"));
		    return view;
		} else {
			// go to feed
			api_key = session_api_key;
			signIn();
			return null;
		}
	    
	}
	
	/** Currently unused. Should return the user's Facebook profile picture as a bitmap given
	 *  his or her userID. 
	 * @throws IOException  */
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
		                String url = root_url + "/rest_facebook_login?";
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
	    	Toast.makeText(getActivity(), "Oops. Unable to sign in to daapr using your Facebook.", Toast.LENGTH_SHORT).show();
	    }
		
	}
	
	private void buildUserInfo(GraphUser user) {
        fb_id = user.getId();
        first_name = user.getFirstName();
        last_name = user.getLastName();
        fb_email = (String) user.getProperty("email");
        fb_image = "https://graph.facebook.com/" + fb_id + "/picture";
	}
	
	/** Starts the feed activity after passing api_key to the feed. */
	private void signIn() {
		if (isAdded()) {
			getActivity().finish();
			Intent feed = new Intent(getActivity(), Feed.class);
			feed.putExtra("API_KEY", api_key);
			startActivity(feed);
			
		}
	}
	
	private class SignInTask extends AsyncTask<Object, Void, Object[]> {
	    @SuppressWarnings("unchecked")
		protected Object[] doInBackground(Object... params) {
	        return HTTP.sign_in((String) params[0],(List<BasicNameValuePair>) params[1]);
	    }

	    protected void onPostExecute(Object[] result) {
	    	//result[0] == true means the login was successful
	        if ((Boolean) result[0]) {
	        	JSONObject user = (JSONObject) result[1];
	        	try {
					api_key = (String) user.get("api_key");
				} catch (JSONException e) {
					e.printStackTrace();
				}
	        	// Write the api key to shared preferences
				SharedPreferences.Editor editor = sharedPref.edit();
				editor.putString("api_key", api_key);
				editor.putString("fb_id", fb_id);
				editor.commit();
	        	signIn();
	        } else {
	        	//login was unsuccessful for some reason
	        	Toast.makeText(getActivity(), "Invalid email or password!", Toast.LENGTH_SHORT).show();
	        }
	    }
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
			//automatically presses the sign in button when "done" or "return" is clicked on the keyboard.
			btn.performClick();
        }    
		return false;
	}
}
