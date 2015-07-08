package com.dnaroid.psychologybook.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

public class SharedPrefrencesManager {

	Context mContext;
	SharedPreferences settings;
	SharedPreferences.Editor editor;
	Encryption encryption;
	private static final String KEY = "a1r2!Fo.";
	Utils utils;

	public SharedPrefrencesManager(Context context) {
		encryption = new Encryption();
		utils = new Utils(context);
		this.mContext = context;
		settings = mContext.getSharedPreferences("psy_shared_prefs", 0);
		editor = settings.edit();
	}

	public void setVersionCode() {
		try {
			PackageInfo pInfo = mContext.getPackageManager().getPackageInfo(
					mContext.getPackageName(), 0);
			int versionCode = pInfo.versionCode;
			editor.putInt("currentVersion", versionCode);
			editor.commit();
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getVersionCode(){
		int versionCode = settings.getInt("currentVersion", 0);
		return versionCode;
	}

	public String getPurchased() {
		String secureData = settings.getString("purchased", null);
		return encryption.decrypt(KEY, secureData);
	}

	public void setPurchased() {
		String imei = utils.getIMEI();
		String secureData = encryption.encrypt(KEY, imei);
		editor.putString("purchased", secureData);
		editor.commit();

	}

	public void setFirstRun() {
		editor.putString("first_run", "false");
		editor.commit();
	}

	public Boolean isFirstRun() {
		String firstRun = settings.getString("first_run", null);
		if (firstRun == null) {
			return true;
		} else {
			return false;
		}
	}

	public void setParentalControlAccess(Boolean access) {
		editor.putBoolean("parental_control_access", access);
		editor.commit();
	}

	public Boolean getParentalControlAcceess() {
		return settings.getBoolean("parental_control_access", false);
	}

	public void setParentalPassword(String password) {
		editor.putString("parental_password", password);
		editor.commit();
	}

	public String getParentalPassword() {
		return settings.getString("parental_password", null);
	}

	public Boolean isPurchased() {
		String imei = getPurchased();

		if (imei == null) {
			return false;

		} else if (imei.equals(utils.getIMEI())) {
			return true;
		}
		return false;

	}

}
