package com.dnaroid.psychologybook.fragments;

import com.dnaroid.psychologybook.R;
import com.dnaroid.psychologybook.database.UserData;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SaveTestDialogFragment extends DialogFragment {

	public final static String SCORE = "SCORE";
	public final static String DESCRIPTION = "DESCRIPTION";
	public final static String TEST_ID = "TEST_ID";
	private int mScore;
	private String mDescription;
	private int mTestId;
	private UserData userData;

	public SaveTestDialogFragment() {
	}

	public static SaveTestDialogFragment newInstance(int score,
			String description, int testId) {
		SaveTestDialogFragment fragment = new SaveTestDialogFragment();
		Bundle bundle = new Bundle();
		bundle.putInt(SCORE, score);
		bundle.putString(DESCRIPTION, description);
		bundle.putInt(TEST_ID, testId);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = getActivity().getLayoutInflater().inflate(
				R.layout.dialog_fragment_save_test, null);

		mScore = getArguments().getInt(SCORE);
		mDescription = getArguments().getString(DESCRIPTION);
		mTestId = getArguments().getInt(TEST_ID);
		userData = new UserData(getActivity());
		Button saveButton = (Button) v.findViewById(R.id.DialogSavebt);
		Button cancelButton = (Button) v.findViewById(R.id.DialogCancelBt);
		final EditText userEditText = (EditText)v.findViewById(R.id.userNameTest);
		
		saveButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String name = userEditText.getText().toString();
				if(name.equals("") || name.equals(" ")){
					Toast.makeText(getActivity(), "نام وارد نشده است", Toast.LENGTH_SHORT).show();
					return;
				}
				userData.saveUserTest(mScore, mDescription, mTestId, name);
				Toast.makeText(getActivity(), "با موفقیت ذخیره شد", Toast.LENGTH_LONG).show();
				dismiss();
			}
		});

		cancelButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});

		Dialog d = new AlertDialog.Builder(getActivity()).setView(v).create();
		return d;
	}

}
