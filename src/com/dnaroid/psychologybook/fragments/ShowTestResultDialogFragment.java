package com.dnaroid.psychologybook.fragments;

import com.dnaroid.psychologybook.R;
import com.dnaroid.psychologybook.model.SavedTest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.TextView;

public class ShowTestResultDialogFragment extends DialogFragment {

	public final static String USERNAME = "USERNAME";
	public final static String POINT = "POINT";
	public final static String ANSWER = "ANSWER";

	private String mUser;
	private String mPoint;
	private String mAnswer;

	public ShowTestResultDialogFragment() {

	}

	public static ShowTestResultDialogFragment newInstance(SavedTest savedTest) {
		ShowTestResultDialogFragment fragment = new ShowTestResultDialogFragment();
		Bundle bundle = new Bundle();
		bundle.putString(USERNAME, savedTest.getUsername());
		bundle.putString(POINT, savedTest.getPoint());
		bundle.putString(ANSWER, savedTest.getAnswer());
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = getActivity().getLayoutInflater().inflate(
				R.layout.dialog_fragment_test_result, null);
		
		mUser = getArguments().getString(USERNAME);
		mPoint = getArguments().getString(POINT);
		mAnswer = getArguments().getString(ANSWER);
		
		TextView usernameTextView = (TextView)v.findViewById(R.id.textView1Username);
		TextView pointTextView = (TextView)v.findViewById(R.id.textView2Point);
		TextView answerTextView = (TextView)v.findViewById(R.id.textView3Answer);
		
		usernameTextView.setText(mUser);
		pointTextView.setText(mPoint);
		answerTextView.setText(mAnswer);
		
		Dialog d = new AlertDialog.Builder(getActivity()).setView(v).create();
		return d;

	}

}
