package com.dnaroid.psychologybook.adapters;

import java.util.ArrayList;

import com.dnaroid.psychologybook.database.UserData;
import com.dnaroid.psychologybook.model.CounterCache;
import com.dnaroid.psychologybook.model.MenuItemEntry;
import com.dnaroid.psychologybook.model.MenuItemInterface;
import com.dnaroid.psychologybook.model.MenuItemSection;

import com.dnaroid.psychologybook.R;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MenuItemAdapter extends ArrayAdapter<MenuItemInterface> {
	private Context mContext;
	private LayoutInflater mLayoutInflater;
	private ArrayList<CounterCache> mCounterCacheArrey;

	public MenuItemAdapter(Context context,
			ArrayList<MenuItemInterface> menuItems) {
		super(context, 0, menuItems);
		this.mContext = context;
		UserData userData = new UserData(mContext);
		mCounterCacheArrey = userData.getCounterCaches();

		mLayoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = convertView;
		MenuItemInterface item = getItem(position);
		if (item != null) {
			if (item.isSection()) {
				MenuItemSection section = (MenuItemSection) item;
				v = mLayoutInflater.inflate(R.layout.menu_section_item, null);
				v.setOnClickListener(null);
				v.setOnLongClickListener(null);
				v.setLongClickable(false);

				TextView sectionTextView = (TextView) v
						.findViewById(R.id.menu_section_item_text);
				sectionTextView.setText(section.getTitle());
			} else {
				MenuItemEntry entry = (MenuItemEntry) item;
				v = mLayoutInflater.inflate(R.layout.menu_item_entry, null);
				TextView entryTextView = (TextView) v
						.findViewById(R.id.menu_item_text);
				entryTextView.setText(entry.getTitle());
				TextView menuCounterTextView = (TextView) v
						.findViewById(R.id.menu_counter_text);
				RelativeLayout rl = (RelativeLayout) v
						.findViewById(R.id.menuRelative);

				if (entry.getId() < 0) {
					menuCounterTextView.setVisibility(View.GONE);
					if (entry.getId() == -3) {
						rl.setBackgroundColor(Color.parseColor("#888888"));
						entryTextView.setTextColor(Color.parseColor("#ffffff"));
						entryTextView.setTypeface(null, Typeface.BOLD);

					}

					if (entry.getId() == -5) {
						rl.setBackgroundColor(Color.parseColor("#8cae4c"));
						entryTextView.setTextColor(Color.parseColor("#ffffff"));
						entryTextView.setTypeface(null, Typeface.BOLD);
					}

				} else {
					// ////////////////////////////////////////////////////////////////////////////////////
					int allCounter = 27;

					if (entry.getId() != 16) {
						if (mCounterCacheArrey == null || mCounterCacheArrey.size() < 3) {
							allCounter = 100;

						} else {
							allCounter = mCounterCacheArrey.get(
									entry.getId() - 1).getAllCount();
						}
					}

					if (mCounterCacheArrey == null || mCounterCacheArrey.size() < 3) {
						menuCounterTextView.setText("100+");
					} else {
						menuCounterTextView.setText(String.valueOf(allCounter));
					}

					menuCounterTextView.setVisibility(View.VISIBLE);
					menuCounterTextView
							.setBackgroundResource(R.drawable.counter);
					setTextViewColor(menuCounterTextView, entry.getId());

					if (entry.getId() == 13) {
						rl.setBackgroundColor(Color.parseColor("#d9eef5"));

					}

					if (entry.getId() == 14) {
						rl.setBackgroundColor(Color.parseColor("#e5f8f2"));

					}

					if (entry.getId() == 16) {
						rl.setBackgroundColor(Color.parseColor("#bfe0eb"));

					}

				}

				ImageView entryImageView = (ImageView) v
						.findViewById(R.id.menu_item_image);

				setSlideMenuIcons(entryImageView, entry.getId());

			}
		}
		return v;
	}

	private void setTextViewColor(TextView tv, int id) {

		GradientDrawable drawable = (GradientDrawable) tv.getBackground();
		int mod = (id % 17);
		switch (mod) {
		case 11:

			drawable.setColor(mContext.getResources().getColor(
					R.color.black_blue));
			// tv.setBackgroundColor(mContext.getResources().getColor(R.color.black_blue));
			break;

		case 2:
			drawable.setColor(mContext.getResources().getColor(
					R.color.black_purple));
			break;
		case 3:
			drawable.setColor(mContext.getResources().getColor(
					R.color.dark_purple));
			break;

		case 4:
			drawable.setColor(mContext.getResources()
					.getColor(R.color.dark_red));
			break;
		case 5:
			drawable.setColor(mContext.getResources().getColor(
					R.color.dark_blue));
			break;

		case 6:
			drawable.setColor(mContext.getResources().getColor(
					R.color.light_blue));
			break;
		case 7:
			drawable.setColor(mContext.getResources().getColor(R.color.green));
			break;

		case 8:
			drawable.setColor(mContext.getResources().getColor(
					R.color.light_green));
			break;
		case 9:
			drawable.setColor(mContext.getResources().getColor(
					R.color.dark_yellow));
			break;

		case 10:
			drawable.setColor(mContext.getResources().getColor(R.color.orange));
			break;
		case 1:
			drawable.setColor(mContext.getResources().getColor(
					R.color.dark_orange));
			break;

		case 12:
			drawable.setColor(mContext.getResources().getColor(R.color.color));
			break;

		case 13:
			drawable.setColor(mContext.getResources().getColor(R.color.test));
			break;

		case 14:
			drawable.setColor(mContext.getResources().getColor(R.color.food));
			break;

		case 15:
			drawable.setColor(mContext.getResources().getColor(
					R.color.dark_pink));
			break;

		case 16:
			drawable.setColor(mContext.getResources().getColor(
					R.color.test_green));
			break;

		default:
			break;
		}
	}

	public void setSlideMenuIcons(ImageView imv, int id) {
		switch (id) {
		case -1:
			imv.setImageResource(R.drawable.slidingmenu_ic_fave);
			break;

		case -2:
			imv.setImageResource(R.drawable.slidingmenu_ic_about);
			break;

		case -4:
			imv.setImageResource(R.drawable.slidingmenu_ic_setting);
			break;

		case 1:
			imv.setImageResource(R.drawable.slidingmenu_ic_wife_hasband);
			break;

		case 2:
			imv.setImageResource(R.drawable.slidingmenu_ic_better_life);
			break;

		case 3:
			imv.setImageResource(R.drawable.slidingmenu_ic_family);
			break;

		case 4:
			imv.setImageResource(R.drawable.slidingmenu_ic_education);
			break;

		case 5:
			imv.setImageResource(R.drawable.slidingmenu_ic_family_high);
			break;

		case 6:
			imv.setImageResource(R.drawable.slidingmenu_ic_son);
			break;

		case 7:
			imv.setImageResource(R.drawable.slidingmenu_ic_connect);
			break;

		case 8:
			imv.setImageResource(R.drawable.slidingmenu_ic_man_female);
			break;

		case 9:
			imv.setImageResource(R.drawable.slidingmenu_ic_adv);
			break;

		case 10:
			imv.setImageResource(R.drawable.slidingmenu_ic_management);
			break;

		case 11:
			imv.setImageResource(R.drawable.slidingmenu_ic_money);
			break;

		case 12:
			imv.setImageResource(R.drawable.slidingmenu_ic_color);
			break;

		case 13:
			imv.setImageResource(R.drawable.slidingmenu_ic_test);
			break;

		case 14:
			imv.setImageResource(R.drawable.slidingmenu_ic_food);
			break;

		case 15:
			imv.setImageResource(R.drawable.slidingmenu_ic_advisor);
			break;
		case 16:
			imv.setImageResource(R.drawable.slidingmenu_ic_test);
			break;

		default:
			imv.setVisibility(View.GONE);
			break;
		}
	}

}
