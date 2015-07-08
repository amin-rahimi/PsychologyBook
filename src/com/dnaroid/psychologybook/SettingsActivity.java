package com.dnaroid.psychologybook;

import com.dnaroid.psychologybook.preferences.SharedPrefrencesManager;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

public class SettingsActivity extends ActionBarActivity {
	SharedPrefrencesManager shpm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		shpm = new SharedPrefrencesManager(this);

		getSupportActionBar().setTitle("تنظیمات");
		getSupportActionBar().setLogo(R.drawable.logo_use);

		final CheckBox accessCheckbox = (CheckBox) findViewById(R.id.accesschbox);

		final EditText passwordEdt = (EditText) findViewById(R.id.passwordedt);

		final EditText oldPasswordEdt = (EditText) findViewById(R.id.currentpass);

		final EditText newPasswordEdt = (EditText) findViewById(R.id.newpass);

		final EditText repeatePasswordEdt = (EditText) findViewById(R.id.newpassrepeat);

		final Button passLoginBt = (Button) findViewById(R.id.openaccessbt);

		final Button changePassBt = (Button) findViewById(R.id.changepassbtn);

		if (shpm.getParentalControlAcceess()) {
			accessCheckbox.setChecked(true);
		} else {
			accessCheckbox.setChecked(false);
		}

		passLoginBt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (passwordEdt.getText().toString().equals("")) {
					Toast.makeText(SettingsActivity.this,
							"رمز عبور را وارد کنید", Toast.LENGTH_SHORT).show();

					return;
				}

				String password = shpm.getParentalPassword();
				if (!passwordEdt.getText().toString().equals(password)) {
					Toast.makeText(SettingsActivity.this,
							"رمز عبور اشتباه می باشد", Toast.LENGTH_SHORT)
							.show();

				} else {
					accessCheckbox.setEnabled(true);
					Toast.makeText(SettingsActivity.this,
							"اکنون میتوانید تنظیمات را تغییر دهید",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		changePassBt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (oldPasswordEdt.getText().toString().equals("")
						|| newPasswordEdt.getText().toString().equals("")
						|| repeatePasswordEdt.getText().toString().equals("")) {
					Toast.makeText(SettingsActivity.this,
							"یک یا چند فیلد خالی است", Toast.LENGTH_SHORT)
							.show();
					return;

				}
				String password = shpm.getParentalPassword();
				if (!oldPasswordEdt.getText().toString().equals(password)) {
					Toast.makeText(SettingsActivity.this,
							"رمز عبور اشتباه می باشد", Toast.LENGTH_SHORT)
							.show();
					return;
				}

				if (oldPasswordEdt.getText().toString().equals(password)) {
					if (newPasswordEdt.getText().toString()
							.equals(repeatePasswordEdt.getText().toString())) {
						shpm.setParentalPassword(newPasswordEdt.getText()
								.toString());
						Toast.makeText(SettingsActivity.this,
								"رمز عبور با موفقیت تغییر کرد",
								Toast.LENGTH_SHORT).show();
						oldPasswordEdt.setText("");
						newPasswordEdt.setText("");
						repeatePasswordEdt.setText("");
						return;
					} else {
						Toast.makeText(SettingsActivity.this,
								"رمز جدید و تکرار ان مشابه نیستند",
								Toast.LENGTH_SHORT).show();
						return;
					}
				}
			}
		});

		accessCheckbox
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton check,
							boolean arg1) {
						// TODO Auto-generated method stub
						if (check.isChecked()) {
							shpm.setParentalControlAccess(true);
						} else {
							shpm.setParentalControlAccess(false);
						}

						Toast.makeText(SettingsActivity.this,
								"تنظیمات ذخیره شد", Toast.LENGTH_SHORT).show();
					}
				});

	}
}
