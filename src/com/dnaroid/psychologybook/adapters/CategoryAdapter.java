package com.dnaroid.psychologybook.adapters;

import java.util.ArrayList;

import com.dnaroid.psychologybook.database.UserData;
import com.dnaroid.psychologybook.model.CounterCache;
import com.dnaroid.psychologybook.model.MenuItemEntry;
import com.dnaroid.psychologybook.preferences.SharedPrefrencesManager;
import com.dnaroid.psychologybook.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CategoryAdapter extends ArrayAdapter<MenuItemEntry> {

	private Context mContext;
	private LayoutInflater mLayoutInflater;
	SharedPrefrencesManager shpm;
	private Boolean isPurchased;
	private ArrayList<CounterCache> mCounterCacheArrey;

	public CategoryAdapter(Context context, ArrayList<MenuItemEntry> categories) {
		super(context, 0, categories);
		this.mContext = context;
		mLayoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// cacheCounter = ((PsyApplication) (((Activity) mContext)
		// .getApplication())).categoryCountCache;
		shpm = new SharedPrefrencesManager(mContext);
		isPurchased = shpm.isPurchased();
		UserData userData = new UserData(mContext);
		mCounterCacheArrey = userData.getCounterCaches();

		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.category_list_item,
					null);
		}

		MenuItemEntry entry = getItem(position);

		TextView activeTopicCounter = (TextView) convertView
				.findViewById(R.id.category_counter_text);

		TextView categoryTitleTV = (TextView) convertView
				.findViewById(R.id.category_title_text);
		categoryTitleTV.setText(entry.getTitle());
		ImageView categoryIMV = (ImageView) convertView
				.findViewById(R.id.category_list_image);
		setSlideMenuIcons(categoryIMV, entry.getId());
		LinearLayout lny = (LinearLayout) convertView
				.findViewById(R.id.image_container);
		setTextViewColor(lny, entry.getId());

		// ///////////////////////////////////////////////////counter
		int allCounter = 27;
		int freeCounter = 10;
		
		
		
		if (entry.getId() != 16) {
			if (mCounterCacheArrey == null || mCounterCacheArrey.size() < 3) {
				allCounter = 100;
				freeCounter = 20;
			} else {
				allCounter = mCounterCacheArrey.get(entry.getId() - 1)
						.getAllCount();
				freeCounter = mCounterCacheArrey.get(entry.getId() - 1)
						.getFreeCount();
			}

		}
		if (isPurchased) {
			if (mCounterCacheArrey == null) {
				activeTopicCounter.setText("100+" + " / "
						+ "-");
			} else {
				activeTopicCounter.setText(String.valueOf(allCounter) + " / "
						+ String.valueOf(allCounter));
			}
		} else {
			if (mCounterCacheArrey == null) {
				activeTopicCounter.setText("100+" + " / " + "-");
			} else {
				activeTopicCounter.setText(String.valueOf(allCounter) + " / "
						+ String.valueOf(freeCounter));
			}
		}

		return convertView;
	}

	public void setSlideMenuIcons(ImageView imv, int id) {
		switch (id) {
		case 1:
			imv.setImageResource(R.drawable.slidingmenu_ic_wife_hasband_white);
			break;

		case 2:
			imv.setImageResource(R.drawable.slidingmenu_ic_better_life_white);
			break;

		case 3:
			imv.setImageResource(R.drawable.slidingmenu_ic_family_white);
			break;

		case 4:
			imv.setImageResource(R.drawable.slidingmenu_ic_education_white);
			break;

		case 5:
			imv.setImageResource(R.drawable.slidingmenu_ic_family_high_white);
			break;

		case 6:
			imv.setImageResource(R.drawable.slidingmenu_ic_son_white);
			break;

		case 7:
			imv.setImageResource(R.drawable.slidingmenu_ic_connect_white);
			break;

		case 8:
			imv.setImageResource(R.drawable.slidingmenu_ic_man_female_white);
			break;

		case 9:
			imv.setImageResource(R.drawable.slidingmenu_ic_adv_white);
			break;

		case 10:
			imv.setImageResource(R.drawable.slidingmenu_ic_management_white);
			break;

		case 11:
			imv.setImageResource(R.drawable.slidingmenu_ic_money_white);
			break;

		case 12:
			imv.setImageResource(R.drawable.slidingmenu_ic_color_white);
			break;

		case 13:
			imv.setImageResource(R.drawable.slidingmenu_ic_test_white);
			break;

		case 14:
			imv.setImageResource(R.drawable.slidingmenu_ic_food_white);
			break;

		case 15:
			imv.setImageResource(R.drawable.slidingmenu_ic_advisor_white);
			break;

		case 16:
			imv.setImageResource(R.drawable.slidingmenu_ic_test_white);
			break;

		default:
			imv.setImageResource(R.drawable.transparent);
			break;
		}
	}

	private void setTextViewColor(LinearLayout tv, int id) {

		int mod = (id % 17);
		switch (mod) {
		case 11:

			tv.setBackgroundColor(mContext.getResources().getColor(
					R.color.black_blue));
			break;

		case 2:
			tv.setBackgroundColor(mContext.getResources().getColor(
					R.color.black_purple));
			break;
		case 3:
			tv.setBackgroundColor(mContext.getResources().getColor(
					R.color.dark_purple));
			break;

		case 4:
			tv.setBackgroundColor(mContext.getResources().getColor(
					R.color.dark_red));
			break;
		case 5:
			tv.setBackgroundColor(mContext.getResources().getColor(
					R.color.dark_blue));
			break;

		case 6:
			tv.setBackgroundColor(mContext.getResources().getColor(
					R.color.light_blue));
			break;
		case 7:
			tv.setBackgroundColor(mContext.getResources().getColor(
					R.color.green));
			break;

		case 8:
			tv.setBackgroundColor(mContext.getResources().getColor(
					R.color.light_green));
			break;
		case 9:
			tv.setBackgroundColor(mContext.getResources().getColor(
					R.color.dark_yellow));
			break;

		case 10:
			tv.setBackgroundColor(mContext.getResources().getColor(
					R.color.orange));
			break;
		case 1:
			tv.setBackgroundColor(mContext.getResources().getColor(
					R.color.dark_orange));
			break;

		case 12:
			tv.setBackgroundColor(mContext.getResources().getColor(
					R.color.color));
			break;

		case 13:
			tv.setBackgroundColor(mContext.getResources()
					.getColor(R.color.test));
			break;
		case 14:
			tv.setBackgroundColor(mContext.getResources()
					.getColor(R.color.food));
			break;

		case 15:
			tv.setBackgroundColor(mContext.getResources().getColor(
					R.color.dark_pink));
			break;

		case 16:
			tv.setBackgroundColor(mContext.getResources().getColor(
					R.color.test_green));
			break;

		default:
			break;
		}
	}
	
	

}
