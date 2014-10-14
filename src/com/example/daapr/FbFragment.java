package com.example.daapr;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FbFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, 
	        ViewGroup container, 
	        Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.signin, container, false);
	    if (view == null) { System.out.println("the view is null...GAHHH"); }
	    else { System.out.println("the view is not null..."); }
	    return view;
	}
}
