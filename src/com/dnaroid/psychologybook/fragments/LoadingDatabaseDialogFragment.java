package com.dnaroid.psychologybook.fragments;

import com.dnaroid.psychologybook.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;

public class LoadingDatabaseDialogFragment extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = getActivity().getLayoutInflater().inflate(
				R.layout.loading_dialog, null);
		
		
		return new AlertDialog.Builder(getActivity()).setView(v).create();
	}




}
