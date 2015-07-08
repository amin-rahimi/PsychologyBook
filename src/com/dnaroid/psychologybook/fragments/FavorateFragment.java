package com.dnaroid.psychologybook.fragments;

import java.util.ArrayList;

import com.dnaroid.psychologybook.adapters.CategoryItemsAdapter;
import com.dnaroid.psychologybook.database.DatabaseManager;
import com.dnaroid.psychologybook.model.History;
import com.dnaroid.psychologybook.model.PsyItem;
import com.dnaroid.psychologybook.preferences.PsyApplication;
import com.dnaroid.psychologybook.preferences.SharedPrefrencesManager;
import com.dnaroid.psychologybook.R;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class FavorateFragment extends Fragment {

	private Cursor mPsyItemsCursor;
	private DatabaseManager mDbManager;
	ArrayList<PsyItem> mPsyItems = new ArrayList<PsyItem>();
	private ListView itemsListView;
	SharedPrefrencesManager shpm;
	private Boolean isPurchased;
	private Boolean mParentalAccess = false;

	public FavorateFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRetainInstance(true);

		mDbManager = new DatabaseManager(getActivity());

		mPsyItemsCursor = mDbManager.getFavorates();

		shpm = new SharedPrefrencesManager(getActivity());
		isPurchased = shpm.isPurchased();
		mParentalAccess = shpm.getParentalControlAcceess();

		while (!mPsyItemsCursor.isAfterLast()) {
			PsyItem item = new PsyItem();

			item.setId(mPsyItemsCursor.getInt(mPsyItemsCursor
					.getColumnIndexOrThrow("_id")));

			item.setCategoryId(mPsyItemsCursor.getInt(mPsyItemsCursor
					.getColumnIndexOrThrow("category_id")));

			item.setTitle(mPsyItemsCursor.getString(mPsyItemsCursor
					.getColumnIndexOrThrow("title")));

			item.setIsFavorite(mPsyItemsCursor.getInt(mPsyItemsCursor
					.getColumnIndexOrThrow("favorate")));

			item.setIsNew(mPsyItemsCursor.getInt(mPsyItemsCursor
					.getColumnIndexOrThrow("new")));

			item.setIsFree(mPsyItemsCursor.getInt(mPsyItemsCursor
					.getColumnIndexOrThrow("free")));

			mPsyItems.add(item);
			mPsyItemsCursor.moveToNext();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(
				"انچه من میخواهم");
		((ActionBarActivity) getActivity()).getSupportActionBar().setLogo(
				R.drawable.logo_use);

		View v;
		if (mPsyItemsCursor.getCount() == 0) {
			v = inflater.inflate(R.layout.empty_layout, container, false);
		} else {
			v = inflater.inflate(R.layout.fragment_category_items, container,
					false);
			itemsListView = (ListView) v
					.findViewById(R.id.category_items_listview);

			CategoryItemsAdapter adapter = new CategoryItemsAdapter(
					getActivity(), mPsyItems);

			itemsListView.setAdapter(adapter);

			itemsListView
					.setOnItemClickListener(new AdapterView.OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int position, long arg3) {
							// TODO Auto-generated method stub
							PsyItem psyItem = (PsyItem) itemsListView
									.getItemAtPosition(position);
							if (psyItem.getCategoryId() == 15) {
								if (!mParentalAccess) {
									Toast.makeText(
											getActivity(),
											"دسترسی این قسمت در حال حاضر بسته میباشد. برای اطلاعات بیشتر به تنظیمات نرم افزار مراجعه بفرمایید",
											Toast.LENGTH_LONG).show();
									return;
								}
							}

							if (!isPurchased && psyItem.getIsFree() != 1) {
								// toast ertegha
							} else {

								TopicFragment fragment = TopicFragment
										.newInstance(psyItem.getId());
								FragmentManager fragmentManager = getActivity()
										.getSupportFragmentManager();
								fragmentManager.beginTransaction()
										.replace(R.id.content_frame, fragment)
										.commit();
								((PsyApplication) getActivity()
										.getApplication()).setHistory(new History(
										"topicFav", psyItem.getCategoryId(),
										itemsListView.getFirstVisiblePosition()));
							}

						}
					});

		}

		return v;
	}

}
