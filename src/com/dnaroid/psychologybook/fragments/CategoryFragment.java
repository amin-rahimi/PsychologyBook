package com.dnaroid.psychologybook.fragments;

import java.util.ArrayList;

import com.dnaroid.psychologybook.adapters.CategoryAdapter;
import com.dnaroid.psychologybook.model.History;
import com.dnaroid.psychologybook.model.MenuItemEntry;
import com.dnaroid.psychologybook.preferences.PsyApplication;
import com.dnaroid.psychologybook.preferences.SharedPrefrencesManager;
import com.dnaroid.psychologybook.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


public class CategoryFragment extends Fragment {
	ListView mListView = null;
	private ArrayList<MenuItemEntry> mCategoryItems = new ArrayList<MenuItemEntry>();
	SharedPrefrencesManager shpm;
	

	public CategoryFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		shpm = new SharedPrefrencesManager(getActivity());
		((PsyApplication) getActivity().getApplication()).setHistory(new History(
				"category", -1));

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		((ActionBarActivity) getActivity()).getSupportActionBar().setTitle("");
		((ActionBarActivity) getActivity()).getSupportActionBar().setLogo(
				R.drawable.logo);

		View v = inflater.inflate(R.layout.fragment_category, container, false);

		mListView = (ListView) v.findViewById(R.id.category_list_view);
		mCategoryItems.clear();
		mCategoryItems
				.add(new MenuItemEntry("تست های روانشناسی (گزینه ای)", 16));
		mCategoryItems.add(new MenuItemEntry("تست های روانشناسی", 13));
		mCategoryItems.add(new MenuItemEntry("مشاور تغذیه", 14));

		// mCategoryItems.add(new MenuItemEntry("روانشناسی زناشویی", 1));
		// mCategoryItems.add(new MenuItemEntry("روانشناسی ازدواج", 8));
		mCategoryItems.add(new MenuItemEntry("روانشناسی رنگها", 12));
		mCategoryItems.add(new MenuItemEntry("روانشناسی موفقیت", 2));
		mCategoryItems.add(new MenuItemEntry("روانشناسی خانواده", 3));
		mCategoryItems.add(new MenuItemEntry("فرزندان و امتحانات", 4));
		mCategoryItems.add(new MenuItemEntry("والدین موفق", 5));
		mCategoryItems.add(new MenuItemEntry("روانشناسی کودک و نوجوان", 6));
		mCategoryItems.add(new MenuItemEntry("روانشناسی روابط", 7));

		mCategoryItems.add(new MenuItemEntry("روانشناسی تبلیغات", 9));
		mCategoryItems.add(new MenuItemEntry("روانشناسی مدیریت", 10));
		mCategoryItems.add(new MenuItemEntry("روانشناسی ثروت افرینی", 11));

		// mCategoryItems.add(new MenuItemEntry("مشاور جنسی (برای متاهلین)",
		// 15));

		CategoryAdapter adapter = new CategoryAdapter(getActivity(),
				mCategoryItems);
		mListView.setAdapter(adapter);

		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				MenuItemEntry menuItemEntry = (MenuItemEntry) mListView
						.getItemAtPosition(position);
				/*
				 * if(menuItemEntry.getId() == 15){ if(!mParentalAccess){
				 * Toast.makeText(getActivity(),
				 * "دسترسی این قسمت در حال حاضر بسته میباشد. برای اطلاعات بیشتر به تنظیمات نرم افزار مراجعه بفرمایید"
				 * , Toast.LENGTH_LONG).show(); return; } }
				 */

				CategoryTopicsFragment fragment = CategoryTopicsFragment
						.newInstance(menuItemEntry.getId(), 0);
				FragmentManager fragmentManager = getActivity()
						.getSupportFragmentManager();
				fragmentManager.beginTransaction()
						.replace(R.id.content_frame, fragment).commit();

				((PsyApplication) getActivity().getApplication())
						.setHistory(new History("category_items", -1));

			}
		});
		return v;

	}

}
