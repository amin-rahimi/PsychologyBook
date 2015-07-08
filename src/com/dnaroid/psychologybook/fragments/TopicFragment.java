package com.dnaroid.psychologybook.fragments;

import com.dnaroid.psychologybook.database.DatabaseManager;
import com.dnaroid.psychologybook.database.UserData;
import com.dnaroid.psychologybook.model.History;
import com.dnaroid.psychologybook.model.PsyItemDetails;
import com.dnaroid.psychologybook.preferences.PsyApplication;
import com.dnaroid.psychologybook.R;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class TopicFragment extends Fragment {
	public final static String TOPIC_ID = "TOPIC_ID";
	public final static String SCROLL_POSITION = "SCROLL_POSITION";
	private int mTopicId;
	private Cursor mPsyItemDetailsCursor;
	private DatabaseManager mDbManager;
	ScrollView mScrollView;
	private PsyItemDetails topic = new PsyItemDetails();
	private UserData mUserData;
	
	public TopicFragment() {
	}

	public static TopicFragment newInstance(int topicId) {
		TopicFragment fragment = new TopicFragment();
		Bundle bundle = new Bundle();
		bundle.putInt(TOPIC_ID, topicId);
		fragment.setArguments(bundle);
		return fragment;

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRetainInstance(true);

		
		mTopicId = getArguments().getInt(TOPIC_ID);

		mDbManager = new DatabaseManager(getActivity());
		mPsyItemDetailsCursor = mDbManager.getTopic(mTopicId);
		
		mUserData = new UserData(getActivity());

		topic.setId(mPsyItemDetailsCursor.getInt(mPsyItemDetailsCursor
				.getColumnIndexOrThrow("_id")));
		
		topic.setTitle(mPsyItemDetailsCursor.getString(mPsyItemDetailsCursor
				.getColumnIndexOrThrow("title")));
		
		topic.setIsFavorite(mPsyItemDetailsCursor.getInt(mPsyItemDetailsCursor
				.getColumnIndexOrThrow("favorate")));
		
		topic.setIsNew(mPsyItemDetailsCursor.getInt(mPsyItemDetailsCursor
				.getColumnIndexOrThrow("new")));
		
		topic.setText(mPsyItemDetailsCursor.getString(mPsyItemDetailsCursor
				.getColumnIndexOrThrow("text")));
		
		topic.setCategoryId(mPsyItemDetailsCursor.getInt(mPsyItemDetailsCursor
				.getColumnIndexOrThrow("category_id")));
		
	

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		
		String title = mDbManager.getCategoryName(topic.getCategoryId());
		((ActionBarActivity)getActivity()).getSupportActionBar().setTitle(title);
		((ActionBarActivity)getActivity()).getSupportActionBar().setLogo(R.drawable.logo_use);
		
		View v = inflater.inflate(R.layout.fragment_topic, container, false);
		ImageView shareBUtton = (ImageView)v.findViewById(R.id.shareButton);
		final TextView titleTextView = (TextView) v
				.findViewById(R.id.topic_title_textView);
		ToggleButton toggleLike = (ToggleButton) v
				.findViewById(R.id.toggleTopic);
		LinearLayout rl = (LinearLayout) v.findViewById(R.id.linear_topic);
		setTextViewColor(rl, topic.getCategoryId());
		titleTextView.setText(topic.getTitle());
		mScrollView = (ScrollView) v.findViewById(R.id.topic_scrollview);
		final TextView topicTextView = (TextView) v.findViewById(R.id.topic_textView);
		topicTextView.setText(topic.getText());

		if(topic.isIsFavorite() == 1){
			toggleLike.setChecked(true);
		}else{
			toggleLike.setChecked(false);
		}
		toggleLike.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean checked) {
				// TODO Auto-generated method stub
				if (checked) {
					Toast.makeText(getActivity(),
							"به لیست علاقه مندی ها افزوده شد",
							Toast.LENGTH_SHORT).show();
					mDbManager.updateTopic(mTopicId, 1);
					mUserData.saveUserData(mTopicId, "insert");

				} else {
					Toast.makeText(getActivity(),
							"از لیست علاقه مندی ها حذف شد", Toast.LENGTH_SHORT)
							.show();
					mDbManager.updateTopic(mTopicId, 0);
					mUserData.saveUserData(mTopicId, "delete");
				}

			}
		});
		
		shareBUtton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
				sharingIntent.setType("text/plain");
			    String shareBody = topicTextView.getText().toString() + "\n" + "روانشناس همراه" + "\n" + "http://cafebazaar.ir/app/com.dnaroid.psychologybook/?l=fa";
			    
			    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, titleTextView.getText().toString());
			    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
			    startActivity(Intent.createChooser(sharingIntent, "اشتراک گذاری از طریق"));
			}
		});
		return v;
	}

	private void setTextViewColor(LinearLayout tv, int id) {

		int mod = (id % 16);
		switch (mod) {
		case 11:

			tv.setBackgroundColor(getActivity().getResources().getColor(
					R.color.black_blue));
			break;

		case 2:
			tv.setBackgroundColor(getActivity().getResources().getColor(
					R.color.black_purple));
			break;
		case 3:
			tv.setBackgroundColor(getActivity().getResources().getColor(
					R.color.dark_purple));
			break;

		case 4:
			tv.setBackgroundColor(getActivity().getResources().getColor(
					R.color.dark_red));
			break;
		case 5:
			tv.setBackgroundColor(getActivity().getResources().getColor(
					R.color.dark_blue));
			break;

		case 6:
			tv.setBackgroundColor(getActivity().getResources().getColor(
					R.color.light_blue));
			break;
		case 7:
			tv.setBackgroundColor(getActivity().getResources().getColor(
					R.color.green));
			break;

		case 8:
			tv.setBackgroundColor(getActivity().getResources().getColor(
					R.color.light_green));
			break;
		case 9:
			tv.setBackgroundColor(getActivity().getResources().getColor(
					R.color.dark_yellow));
			break;

		case 10:
			tv.setBackgroundColor(getActivity().getResources().getColor(
					R.color.orange));
			break;
		case 1:
			tv.setBackgroundColor(getActivity().getResources().getColor(
					R.color.dark_orange));
			break;
			
		case 12:
			tv.setBackgroundColor(getActivity().getResources().getColor(
					R.color.color));
			break;

		case 13:
			tv.setBackgroundColor(getActivity().getResources().getColor(
					R.color.test));
			break;
		case 14:
			tv.setBackgroundColor(getActivity().getResources().getColor(
					R.color.food));
			break;
			
		case 15:
			tv.setBackgroundColor(getActivity().getResources().getColor(
					R.color.dark_pink));
			break;

		default:
			break;
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mPsyItemDetailsCursor.close();
		mDbManager.close();
	}
	
	

}
