package com.dnaroid.psychologybook;

import com.dnaroid.psychologybook.database.DatabaseManager;
import com.dnaroid.psychologybook.database.UserData;
import com.dnaroid.psychologybook.preferences.SharedPrefrencesManager;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class FirstStartActivity extends ActionBarActivity {

	private SharedPrefrencesManager shpm;
	private int versionCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first_start);
		getSupportActionBar().hide();

		shpm = new SharedPrefrencesManager(this);

		final TextView statusTextView = (TextView) findViewById(R.id.textViewStatus);
		final ProgressBar progress = (ProgressBar) findViewById(R.id.progressBarLoad);
		final Button startButton = (Button) findViewById(R.id.btnComplete);
		
		/*
		final ImageView rating = (ImageView)findViewById(R.id.ratingImageFirst);
		
		rating.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Intent.ACTION_EDIT);
				i.setData(Uri.parse("bazaar://details?id=com.dnaroid.psychologybook"));
				startActivity(i);
			}
		});
		
		*/

		startButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(FirstStartActivity.this,
						PsychologyActivity.class);
				startActivity(i);
				//finish();
			}
		});

		Object[] object = new Object[3];
		object[0] = progress;
		object[1] = statusTextView;
		object[2] = startButton;

		try {
			PackageInfo pInfo = this.getPackageManager().getPackageInfo(
					this.getPackageName(), 0);
			versionCode = pInfo.versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (shpm.getVersionCode() < versionCode) {
			new dbUpdator().execute(object);
			shpm.setVersionCode();

		} else {
			Intent i = new Intent(FirstStartActivity.this,
					PsychologyActivity.class);
			startActivity(i);
			finish();

		}

	}

	private class dbUpdator extends AsyncTask<Object, Void, Void> {

		private ProgressBar progressBar;
		private TextView statusTextView;
		private Button startButton;
		private UserData userData;

		@Override
		protected Void doInBackground(Object... args) {
			// TODO Auto-generated method stub
			userData = new UserData(FirstStartActivity.this);
			progressBar = (ProgressBar) args[0];
			statusTextView = (TextView) args[1];
			startButton = (Button) args[2];
			DatabaseManager dbManager = new DatabaseManager(
					FirstStartActivity.this);
			dbManager.copyDatabase();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub

			if (!shpm.isFirstRun()) {
				userData.loadUserData();
			} else {
				shpm.setParentalControlAccess(false);
				shpm.setParentalPassword("122333");
				shpm.setFirstRun();
			}

			userData.createCountersCache();

			progressBar.setVisibility(View.INVISIBLE);
			statusTextView.setTextColor(Color.parseColor("#4f7c1a"));
			statusTextView.setText("بارگذاری با موفقیت انجام شد");
			startButton.setBackgroundColor(Color.parseColor("#4f7c1a"));
			startButton.setEnabled(true);

		}

	}

}
