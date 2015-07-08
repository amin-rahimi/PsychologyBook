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

public class CategoryTopicsFragment extends Fragment {

	public final static String CATEGORY_ID = "CATEGORY_ID";
	public final static String SCROLL_POSITION = "SCROLL_POSITION";

	private int mCategoryId;
	private int mScrollPosition;
	private Cursor mPsyItemsCursor;
	private DatabaseManager mDbManager;
	ArrayList<PsyItem> mPsyItems = new ArrayList<PsyItem>();
	private ListView itemsListView;
	SharedPrefrencesManager shpm;
	private Boolean isPurchased;

	public CategoryTopicsFragment() {
	}

	public static CategoryTopicsFragment newInstance(int categoryId,
			int scrollPosition) {
		CategoryTopicsFragment fragment = new CategoryTopicsFragment();
		Bundle bundle = new Bundle();
		bundle.putInt(CATEGORY_ID, categoryId);
		bundle.putInt(SCROLL_POSITION, scrollPosition);
		fragment.setArguments(bundle);
		return fragment;

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRetainInstance(true);

		((PsyApplication) getActivity().getApplication())
				.setHistory(new History("category_items", -1));

		shpm = new SharedPrefrencesManager(getActivity());
		isPurchased = shpm.isPurchased();

		mCategoryId = getArguments().getInt(CATEGORY_ID);
		mScrollPosition = getArguments().getInt(SCROLL_POSITION);

		mDbManager = new DatabaseManager(getActivity());

		//int freeCount = mDbManager.getFreeTopicsCount();

		// -----------------------------------
		//if (!isPurchased && freeCount > 220) {
			//getActivity().finish();
		//}

		if (mCategoryId == 16) {
			mPsyItems.add(new PsyItem("تست ازدواج ویژه دختر و پسران مجرد", 12,
					16, 1, 1));
			mPsyItems.add(new PsyItem("استاندارد ترین تست شخصیت شناسی", 1, 16,
					1, 1));
			mPsyItems.add(new PsyItem("امتحان سازگاری", 4, 16, 1, 1));
			mPsyItems.add(new PsyItem(
					"نمره خوشبختی شما چند است (دکتر تئو لنتز)؟", 5, 16, 1, 1));
			mPsyItems.add(new PsyItem("تست مسئولیت پذیری", 6, 16, 1, 1));
			mPsyItems.add(new PsyItem("درونگرا یا برونگرا؟", 11, 16, 1, 1));
			mPsyItems.add(new PsyItem("چقدر در خطر ابتلا به افسردگی هستید", 7,
					16, 1, 1));
			mPsyItems.add(new PsyItem("همسرتان را چقدر شناخته اید؟", 8, 16, 1,
					1));
			mPsyItems.add(new PsyItem("تاچه حد اهل خطر کردن هستید ؟", 20, 16,
					1, 1));
			mPsyItems.add(new PsyItem("نمره اعصاب شما چند است؟", 21, 16, 1, 1));
			mPsyItems.add(new PsyItem("تست ضریب هوشی", 22, 16, 1, 0));
			mPsyItems.add(new PsyItem("تست میزان شجاعت", 23, 16, 1, 0));
			mPsyItems.add(new PsyItem("برخورد شما با همسرتان چگونه است؟", 24,
					16, 1, 0));
			mPsyItems.add(new PsyItem("تست روانشناسی و میزان اضطراب", 25, 16,
					1, 0));
			mPsyItems.add(new PsyItem("میزان محبوبیت شما", 26, 16, 1, 0));
			mPsyItems.add(new PsyItem("پرحرفيد يا کم حرف؟", 27, 16, 1, 0));
			mPsyItems
					.add(new PsyItem("تست روانشناسی تیپ شخصیتی", 13, 16, 1, 0));
			mPsyItems
					.add(new PsyItem("ازمون افسردگى دکتر آرون بک", 2, 16, 1, 0));
			mPsyItems.add(new PsyItem("پرسشنامه ی وضعیت زناشویی گلومبوک", 9,
					16, 1, 0));
			mPsyItems.add(new PsyItem("اخرین تست روانشناسی بین الملی در دنیا",
					3, 16, 1, 0));
			mPsyItems.add(new PsyItem("تست شادی : میزان شادی خود را بسنجید",
					10, 16, 1, 0));

			mPsyItems.add(new PsyItem("آیا غرغرو هستید؟", 14, 16, 1, 0));
			mPsyItems.add(new PsyItem("آزمون سلامت عاطفی", 15, 16, 1, 0));
			mPsyItems.add(new PsyItem("رویاهایتان به شما چه می گویند؟", 16, 16,
					1, 0));
			mPsyItems.add(new PsyItem("تست عشق و عاشقی", 17, 16, 1, 0));
			mPsyItems.add(new PsyItem("تست سلامت روحی و روانی", 18, 16, 1, 0));
			mPsyItems.add(new PsyItem("تست روانشناسی فضولی", 19, 16, 1, 0));

		} else {

			mPsyItemsCursor = mDbManager.getCategoryTopics(mCategoryId);
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

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		if (mCategoryId == 16) {
			((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(
					"تست های روانشناسی (گزینه ای)");
			((ActionBarActivity) getActivity()).getSupportActionBar().setLogo(
					R.drawable.logo_use);
		} else {
			String title = mDbManager.getCategoryName(mCategoryId);
			((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(
					title);
			((ActionBarActivity) getActivity()).getSupportActionBar().setLogo(
					R.drawable.logo_use);
			;
		}
		View v = inflater.inflate(R.layout.fragment_category_items, container,
				false);
		itemsListView = (ListView) v.findViewById(R.id.category_items_listview);

		CategoryItemsAdapter adapter = new CategoryItemsAdapter(getActivity(),
				mPsyItems);

		itemsListView.setAdapter(adapter);

		itemsListView.setSelection(mScrollPosition);

		itemsListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						// TODO Auto-generated method stub
						PsyItem psyItem = (PsyItem) itemsListView
								.getItemAtPosition(position);

						if (!isPurchased && psyItem.getIsFree() != 1) {
							Toast.makeText(
									getActivity(),
									"برای ارتقا به نسخه حرفه ای از منو کنار استفاده کنید. با تشکر DNAroid",
									Toast.LENGTH_LONG).show();
						} else {
							if (psyItem.getCategoryId() == 16) {
								ChoiceTestFragment fragment = ChoiceTestFragment
										.newInstance(psyItem.getId(),
												psyItem.getTitle());
								FragmentManager fragmentManager = getActivity()
										.getSupportFragmentManager();
								fragmentManager.beginTransaction()
										.replace(R.id.content_frame, fragment)
										.commit();
								((PsyApplication) getActivity()
										.getApplication()).setHistory(new History(
										"topic", psyItem.getCategoryId(),
										itemsListView.getFirstVisiblePosition()));
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
										"topic", psyItem.getCategoryId(),
										itemsListView.getFirstVisiblePosition()));
							}
						}

					}
				});

		return v;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (mCategoryId != 16) {
			mPsyItemsCursor.close();
			mDbManager.close();
		}
	}

}
