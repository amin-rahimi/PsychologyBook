package com.dnaroid.psychologybook.adapters;

import java.util.ArrayList;







import com.dnaroid.psychologybook.R;
import com.dnaroid.psychologybook.model.SavedTest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SavedTestsAdapter extends ArrayAdapter<SavedTest> {
	
	private LayoutInflater mLayoutInflater;
	private ArrayList<SavedTest> mTests;
	
	public SavedTestsAdapter(Context context, ArrayList<SavedTest> tests) {
		super(context, 0, tests);
		// TODO Auto-generated constructor stub
		mLayoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		mTests = tests;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		// TODO Auto-generated method stub
		SavedTest t = getItem(position);
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(
					R.layout.saved_tests_gridview_item, null);
		}
		
		TextView pointTextView = (TextView)convertView.findViewById(R.id.pointTextView);
		TextView usernameTextView = (TextView)convertView.findViewById(R.id.userNameTextView);
		
		pointTextView.setText(t.getPoint());
		usernameTextView.setText(t.getUsername());
		
		return convertView;
	}
	
	


}
