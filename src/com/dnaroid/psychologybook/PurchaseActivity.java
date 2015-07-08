package com.dnaroid.psychologybook;

import com.dnaroid.psychologybook.R;
import com.dnaroid.psychologybook.preferences.SharedPrefrencesManager;

import util.IabHelper;
import util.IabResult;
import util.Inventory;
import util.Purchase;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class PurchaseActivity extends ActionBarActivity {

	// Debug tag, for logging
	static final String TAG = "Purchase";

	// SKUs for our products: the premium upgrade (non-consumable)
	static final String SKU_PREMIUM = "premium";

	// Does the user have the premium upgrade?
	boolean mIsPremium = false;

	// (arbitrary) request code for the purchase flow
	static final int RC_REQUEST = 10001;

	// The helper object
	IabHelper mHelper;

	SharedPrefrencesManager sharedManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_purchase);

		//String base64EncodedPublicKey = "MIHNMA0GCSqGSIb3DQEBAQUAA4G7ADCBtwKBrwDI74FD/OkGaWZtyXsYIdjBrSQ/e4gRlx/htfbsdHgMor1W6B/gjJfZ6dXKJkdW8qURKFCjWiQZi8DJKLY9m9gTZBz3bN6ghjg9x6dOEd1p6bKD3LSLBJkXh5TNYTzMnrqbDJFeoSsfk790+lw06zGLp/X8mKYRL8mR77ahmfGjZQhkSzqfq8ZJhYzQ4X6DGkvlkyy3QTpZT6TnhGJmg45gk9aKqF6u2Gk4OGLLX0cCAwEAAQ==";
		String base64EncodedPublicKey = "MIGeMA0GCSqGSIb3DQEBAQUAA4GMADCBiAKBgJ2Fbz/6Guu+WTfBHJ08+/IeldLWiBnaF1B7LoPpTJjJiC66ZlcgtIUe8Qv83K+pAfhpt6GihJimpqTHU3gRkD4kyIpmZY0enOiXW+DxkwD9Rt3ZTaj3WoJYxz9z2PZOOYTaq6OxvCxCDXA7K6eRu3C/CZUuYCt1sCSjjU5dh5/VAgMBAAE=";
		getSupportActionBar().setTitle("");
		getSupportActionBar().setLogo(R.drawable.logo);

		mHelper = new IabHelper(this, base64EncodedPublicKey);
		sharedManager = new SharedPrefrencesManager(this);

		Log.d(TAG, "Starting setup.");
		mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
			public void onIabSetupFinished(IabResult result) {
				Log.d(TAG, "Setup finished.");

				if (!result.isSuccess()) {
					// Oh noes, there was a problem.
					Log.d(TAG, "Problem setting up In-app Billing: " + result);
				}
				// Hooray, IAB is fully set up!
				mHelper.queryInventoryAsync(mGotInventoryListener);
			}
		});

		Button purchaseButton = (Button) findViewById(R.id.button_purchase);
		purchaseButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (mHelper != null) {
					mHelper.flagEndAsync();
				}
				mHelper.launchPurchaseFlow(PurchaseActivity.this, SKU_PREMIUM,
						RC_REQUEST, mPurchaseFinishedListener, "payload-string");
				// sharedManager.setPurchased();
				// finish();

			}
		});

	}

	IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
		public void onQueryInventoryFinished(IabResult result,
				Inventory inventory) {
			Log.d(TAG, "Query inventory finished.");
			if (result.isFailure()) {
				Log.d(TAG, "Failed to query inventory: " + result);
				return;
			} else {
				Log.d(TAG, "Query inventory was successful.");
				// does the user have the premium upgrade?
				mIsPremium = inventory.hasPurchase(SKU_PREMIUM);

				// update UI accordingly
				if (mIsPremium) {
					sharedManager.setPurchased();
					finish();
				}
				Log.d(TAG, "User is "
						+ (mIsPremium ? "PREMIUM" : "NOT PREMIUM"));
			}

			Log.d(TAG, "Initial inventory query finished; enabling main UI.");
		}
	};

	IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
		public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
			if (result.isFailure()) {
				Log.d(TAG, "Error purchasing: " + result);
				return;
			} else if (purchase.getSku().equals(SKU_PREMIUM)) {
				// give user access to premium content and update the UI
				sharedManager.setPurchased();
				finish();
			}
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + ","
				+ data);

		// Pass on the activity result to the helper for handling
		if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
			super.onActivityResult(requestCode, resultCode, data);
		} else {
			Log.d(TAG, "onActivityResult handled by IABUtil.");
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mHelper != null)
			mHelper.dispose();
		mHelper = null;
	}

}
