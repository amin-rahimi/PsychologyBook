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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class SearchResultFragment extends Fragment {

	public static final String QUERY = "QUERY";
	private String mQuery;
	private Cursor mPsyItemsCursor;
	private DatabaseManager mDbManager;
	ArrayList<PsyItem> mPsyItems = new ArrayList<PsyItem>();
	private ListView itemsListView;
	private Boolean mParentalAccess = false;
	SharedPrefrencesManager shpm;
	private Boolean isPurchased;

	public SearchResultFragment() {
	}

	public static SearchResultFragment newInstance(String query) {
		SearchResultFragment fragment = new SearchResultFragment();
		Bundle bundle = new Bundle();
		bundle.putString(QUERY, query);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		mQuery = getArguments().getString(QUERY);
		mDbManager = new DatabaseManager(getActivity());
		mPsyItemsCursor = mDbManager.searchTopics(mQuery);

		shpm = new SharedPrefrencesManager(getActivity());
		mParentalAccess = shpm.getParentalControlAcceess();
		isPurchased = shpm.isPurchased();

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
		View v = inflater.inflate(R.layout.fragment_category_items, container,
				false);
		itemsListView = (ListView) v.findViewById(R.id.category_items_listview);

		CategoryItemsAdapter adapter = new CategoryItemsAdapter(getActivity(),
				mPsyItems);

		itemsListView.setAdapter(adapter);

		itemsListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						// TODO Auto-generated method stub
						PsyItem psyItem = (PsyItem) itemsListView
								.getItemAtPosition(position);
						//if (psyItem.getCategoryId() == 15) {
							//if (!mParentalAccess) {
							//	Toast.makeText(
									//	getActivity(),
									//	"دسترسی این قسمت در حال حاضر بسته میباشد. برای اطلاعات بیشتر به تنظیمات نرم افزار مراجعه بفرمایید",
									//	Toast.LENGTH_LONG).show();
							//	return;
							//}
						//}
						if (!isPurchased && psyItem.getIsFree() != 1) {
							Toast.makeText(
									getActivity(),
									"برای ارتقا به نسخه حرفه ای از منو کنار استفاده کنید. با تشکر DNAroid",
									Toast.LENGTH_LONG).show();
						} else {
							TopicFragment fragment = TopicFragment
									.newInstance(psyItem.getId());
							FragmentManager fragmentManager = getActivity()
									.getSupportFragmentManager();
							fragmentManager.beginTransaction()
									.replace(R.id.content_frame, fragment)
									.commit();
							((PsyApplication) getActivity().getApplication())
									.setHistory(new History("topic", psyItem
											.getCategoryId(), itemsListView
											.getFirstVisiblePosition()));
						}

					}
				});

		return v;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mPsyItemsCursor.close();
		mDbManager.close();
	}

}
