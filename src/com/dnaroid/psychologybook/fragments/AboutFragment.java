package com.dnaroid.psychologybook.fragments;

import com.dnaroid.psychologybook.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AboutFragment extends Fragment {
	
	public AboutFragment() {
	}
	
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		((ActionBarActivity)getActivity()).getSupportActionBar().setTitle("");
		((ActionBarActivity)getActivity()).getSupportActionBar().setLogo(R.drawable.logo);
		View v = inflater.inflate(R.layout.fragment_about, container, false);
		return v;
	}
	
	

}
