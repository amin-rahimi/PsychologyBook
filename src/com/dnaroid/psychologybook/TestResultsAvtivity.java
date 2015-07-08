package com.dnaroid.psychologybook;

import java.util.ArrayList;

import com.dnaroid.psychologybook.adapters.SavedTestsAdapter;
import com.dnaroid.psychologybook.database.UserData;
import com.dnaroid.psychologybook.fragments.SaveTestDialogFragment;
import com.dnaroid.psychologybook.fragments.ShowTestResultDialogFragment;
import com.dnaroid.psychologybook.model.SavedTest;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;

public class TestResultsAvtivity extends ActionBarActivity {

	public final static String TEST_ID = "TEST_ID";
	public final static String TEST_TITLE = "TEST_TITLE";

	private int mTestId;
	private String mTitle;
	private UserData userData;
	private ArrayList<SavedTest> savedTestsArray;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_test_results);
		Bundle b = getIntent().getExtras();
		mTestId = b.getInt(TEST_ID);
		mTitle = b.getString(TEST_TITLE);

		getSupportActionBar().setTitle(mTitle);
		userData = new UserData(this);

		savedTestsArray = userData.getSavedTests(mTestId);

		SavedTestsAdapter adapter = new SavedTestsAdapter(this, savedTestsArray);

		GridView gridview = (GridView) findViewById(R.id.testResultsGridView);

		gridview.setAdapter(adapter);
		
		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				SavedTest st = savedTestsArray.get(position);
				FragmentManager fm = getSupportFragmentManager();
				ShowTestResultDialogFragment fragment = ShowTestResultDialogFragment.newInstance(st);
				fragment.show(fm, "fragment_show_test_result");
			}
		});

	}

}
