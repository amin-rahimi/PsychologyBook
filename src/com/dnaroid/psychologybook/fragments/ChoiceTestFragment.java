package com.dnaroid.psychologybook.fragments;

import com.dnaroid.psychologybook.R;
import com.dnaroid.psychologybook.TestActivity;
import com.dnaroid.psychologybook.TestResultsAvtivity;
import com.dnaroid.psychologybook.database.TextDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ChoiceTestFragment extends Fragment {
	public final static String TEST_ID = "TEST_ID";
	public final static String TITLE = "TITLE";
	private int mTestId;
	private String mTitle;
	public ChoiceTestFragment(){
		
	}
	
	public static ChoiceTestFragment newInstance(int testId, String title) {
		ChoiceTestFragment fragment = new ChoiceTestFragment();
		Bundle bundle = new Bundle();
		bundle.putInt(TEST_ID, testId);
		bundle.putString(TITLE, title);
		fragment.setArguments(bundle);
		return fragment;

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mTestId = getArguments().getInt(TEST_ID);
		mTitle = getArguments().getString(TITLE);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_choice_test, container, false);
		
		Button beginTestButton = (Button)v.findViewById(R.id.beginTestBt);
		Button showSavedTestButton = (Button)v.findViewById(R.id.showResultsBt);
		
		TextView title = (TextView)v.findViewById(R.id.title_text_choice);
		TextView description = (TextView)v.findViewById(R.id.description_choice);
		
		title.setText(mTitle);
		String desc = TextDatabase.loadFullText("tests/test_" + String.valueOf(mTestId) + "/description.txt", getActivity());
		description.setText(desc);
		
		((ActionBarActivity)getActivity()).getSupportActionBar().setTitle("تست های روانشناسی (گزینه ای)");
		((ActionBarActivity)getActivity()).getSupportActionBar().setLogo(R.drawable.logo_use);
		
		showSavedTestButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getActivity(), TestResultsAvtivity.class);
				i.putExtra(TestActivity.TEST_ID, mTestId);
				i.putExtra(TestActivity.TEST_TITLE, mTitle);
				startActivity(i);
				
			}
		});
		
		beginTestButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getActivity(), TestActivity.class);
				i.putExtra(TestActivity.TEST_ID, mTestId);
				i.putExtra(TestActivity.TEST_TITLE, mTitle);
				startActivity(i);
			}
		});
		return v;
	}
	
	public String readDescription(int id){
		
		return "";
	}
	
	

}
