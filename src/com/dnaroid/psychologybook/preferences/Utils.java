package com.dnaroid.psychologybook.preferences;

import java.lang.reflect.Method;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import android.util.Log;

public class Utils {

	private Context mContext;

	public Utils(Context context) {
		this.mContext = context;
	}

	public String getIMEI() {

		try {

			if (!hasTelephony(mContext)) {
				return Settings.Secure.ANDROID_ID;
			}

			TelephonyManager telMgr = (TelephonyManager) mContext
					.getSystemService(Context.TELEPHONY_SERVICE);

			if (isSIMAvailable(mContext, telMgr)) {
				String imei = telMgr.getDeviceId();
				if (imei == null || imei.equals("")) {
					imei = "0000-0000-1111-1111";
				}
				Log.i("IMEI", imei);
				return telMgr.getDeviceId();
			} else {
				Log.i("ANDROID_ID", Settings.Secure.ANDROID_ID);
				return Settings.Secure.ANDROID_ID;
			}
		} catch (Exception e) {
			return "0000-0000-1111-1111";
		}

	}

	public static boolean isSIMAvailable(Context mContext,
			TelephonyManager telMgr) {

		int simState = telMgr.getSimState();

		switch (simState) {
		case TelephonyManager.SIM_STATE_ABSENT:
			return false;
		case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
			return false;
		case TelephonyManager.SIM_STATE_PIN_REQUIRED:
			return false;
		case TelephonyManager.SIM_STATE_PUK_REQUIRED:
			return false;
		case TelephonyManager.SIM_STATE_READY:
			return true;
		case TelephonyManager.SIM_STATE_UNKNOWN:
			return false;
		default:
			return false;
		}
	}

	static public boolean hasTelephony(Context mContext) {
		TelephonyManager tm = (TelephonyManager) mContext
				.getSystemService(Context.TELEPHONY_SERVICE);
		if (tm == null)
			return false;

		// devices below are phones only
		if (Build.VERSION.SDK_INT < 5)
			return true;

		PackageManager pm = mContext.getPackageManager();

		if (pm == null)
			return false;

		boolean retval = false;
		try {
			Class<?>[] parameters = new Class[1];
			parameters[0] = String.class;
			Method method = pm.getClass().getMethod("hasSystemFeature",
					parameters);
			Object[] parm = new Object[1];
			parm[0] = "android.hardware.telephony";
			Object retValue = method.invoke(pm, parm);
			if (retValue instanceof Boolean)
				retval = ((Boolean) retValue).booleanValue();
			else
				retval = false;
		} catch (Exception e) {
			retval = false;
		}

		return retval;
	}

}
