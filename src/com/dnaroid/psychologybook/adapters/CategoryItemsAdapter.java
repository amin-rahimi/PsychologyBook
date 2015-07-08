package com.dnaroid.psychologybook.adapters;

import java.util.ArrayList;

import com.dnaroid.psychologybook.model.PsyItem;
import com.dnaroid.psychologybook.preferences.SharedPrefrencesManager;
import com.dnaroid.psychologybook.R;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

public class CategoryItemsAdapter extends ArrayAdapter<PsyItem> {

	// private Context mContext;
	private LayoutInflater mLayoutInflater;
	private Context mContext;
	SharedPrefrencesManager shpm;
	private Boolean isPurchased;

	public CategoryItemsAdapter(Context context, ArrayList<PsyItem> psyItems) {
		super(context, 0, psyItems);
		this.mContext = context;
		mLayoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		shpm = new SharedPrefrencesManager(mContext);
		isPurchased = shpm.isPurchased();

		// TODO Auto-generated constructor stub
	}

	static class ViewHolder {
		TextView categoryItemTextView;
		RelativeLayout categoryItemRelativeLayout;
		ToggleButton likeTogglebutton;
		TextView newTextView;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		final PsyItem psyItem = getItem(position);
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.category_items_item,
					null);

			viewHolder = new ViewHolder();
			viewHolder.categoryItemTextView = (TextView) convertView
					.findViewById(R.id.category_item_items_textview);
			viewHolder.categoryItemRelativeLayout = (RelativeLayout) convertView
					.findViewById(R.id.category_item_items_relativeLayout);
			viewHolder.likeTogglebutton = (ToggleButton) convertView
					.findViewById(R.id.toggle);
			viewHolder.newTextView = (TextView) convertView
					.findViewById(R.id.new_textview);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		
		viewHolder.likeTogglebutton.setClickable(false);

		if (psyItem.getIsFavorite() == 1) {
			viewHolder.likeTogglebutton.setChecked(true);
		} else {
			viewHolder.likeTogglebutton.setChecked(false);
		}

		viewHolder.newTextView.setBackgroundResource(R.drawable.counter);
		setTextViewColor(viewHolder.newTextView, psyItem.getCategoryId(),
				psyItem.getIsFree());

		if (!isPurchased) {
			if (psyItem.getIsFree() == 1) {
				viewHolder.categoryItemRelativeLayout
						.setBackgroundResource(R.drawable.category_list_view_button);
				viewHolder.categoryItemTextView.setTextColor(Color
						.parseColor("#000000"));
			} else {

				viewHolder.categoryItemRelativeLayout.setBackgroundColor(Color
						.parseColor("#ffffff"));
				viewHolder.categoryItemTextView.setTextColor(Color
						.parseColor("#999999"));
			}
		} else {

			viewHolder.categoryItemRelativeLayout
					.setBackgroundResource(R.drawable.category_list_view_button);
			viewHolder.categoryItemTextView.setTextColor(Color
					.parseColor("#000000"));
		}

		viewHolder.categoryItemTextView.setText(psyItem.getTitle());
		return convertView;
	}

	private void setTextViewColor(TextView tv, int id, int isFree) {

		GradientDrawable drawable = (GradientDrawable) tv.getBackground();
		if (isFree != 1 && !isPurchased) {
			drawable.setColor(mContext.getResources()
					.getColor(R.color.deactive));
			return;
		}
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
			drawable.setColor(mContext.getResources().getColor(R.color.test_green));
			break;

		default:
			break;
		}
	}

}
