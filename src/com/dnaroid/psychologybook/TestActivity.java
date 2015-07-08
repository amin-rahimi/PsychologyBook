package com.dnaroid.psychologybook;

import java.util.ArrayList;












import com.dnaroid.psychologybook.database.TextDatabase;
import com.dnaroid.psychologybook.fragments.SaveTestDialogFragment;
import com.dnaroid.psychologybook.model.AnswerModel;
import com.dnaroid.psychologybook.model.TestItem;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class TestActivity extends ActionBarActivity implements OnClickListener {

	public static String TEST_ID = "TEST_ID";
	public static String TEST_TITLE = "TEST_TITLE";

	private int mTestId;
	private String mTitle;
	private String mAnswer;
	private ArrayList<TestItem> mQuestions;
	private ArrayList<ArrayList<Integer>> pointsArray;
	private ArrayList<AnswerModel> answersArray;
	private int mFinalPoints;
	private LinearLayout ll;
	private LayoutParams lp;
	private TextView allQuestionCount;
	private TextView currentQuestionCount;
	private TextView question;
	private int allCount;
	private int currentCount;
	
	

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		Bundle b = getIntent().getExtras();
		mTestId = b.getInt(TEST_ID);
		mTitle = b.getString(TEST_TITLE);
		getSupportActionBar().hide();

		ll = (LinearLayout) findViewById(R.id.testsLayout);
		lp = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		lp.setMargins(0, 5, 0, 5);

		allQuestionCount = (TextView) findViewById(R.id.allQuestion);
		currentQuestionCount = (TextView) findViewById(R.id.currentQuestion);
		question = (TextView) findViewById(R.id.textViewQuestion);

		
		
		// initialization
		if (savedInstanceState == null) {
			mQuestions = TextDatabase.getQuestions(mTestId, this);
			pointsArray = TextDatabase.getPoints(mTestId, this);
			answersArray = TextDatabase.getAnswers(mTestId, this);
			
			allQuestionCount.setText(String.valueOf(mQuestions.size()));
			currentQuestionCount.setText("1");
			allCount = mQuestions.size();
			currentCount = 1;
			mFinalPoints = 0;

			question.setText(mQuestions.get(0).getQuestion());
			int i = 0;
			for (String choice : mQuestions.get(0).getChoices()) {
				Button button = new Button(this);
				button.setText(choice);
				button.setLayoutParams(lp);
				button.setTag(pointsArray.get(0).get(i));
				button.setBackgroundResource(R.drawable.test_bt);
				button.setOnClickListener(this);
				button.setTextSize(14);
				ll.addView(button);
				i++;
			}

			currentCount += 1;

		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (currentCount < (allCount + 1)) {
			int point = (int) v.getTag();
			mFinalPoints += point;
			ll.removeAllViews();
			currentQuestionCount.setText(String.valueOf(currentCount));
			question.setText(mQuestions.get(currentCount - 1).getQuestion());
			int i = 0;
			for (String choice : mQuestions.get(currentCount - 1).getChoices()) {
				Button button = new Button(this);
				button.setText(choice);
				button.setLayoutParams(lp);
				button.setTag(pointsArray.get(currentCount - 1).get(i));
				button.setBackgroundResource(R.drawable.test_bt);
				button.setOnClickListener(this);
				button.setTextSize(14);
				ll.addView(button);
				i++;
			}

			currentCount += 1;

		} else {
			if (currentCount == (allCount + 1)) {
				int point = (int) v.getTag();
				mFinalPoints += point;
				ll.removeAllViews();
				question.setLines(1);
				question.setText("امتیاز شما");
				question.setTextSize(20);
				question.setGravity(Gravity.CENTER);
				question.setBackgroundColor(Color.parseColor("#f4f4f4"));
				question.setTextColor(Color.parseColor("#0a99db"));
				TextView t = new TextView(this);
				t.setTextColor(Color.parseColor("#0a99db"));
				t.setTextSize(35);
				t.setText(String.valueOf(mFinalPoints));
				t.setLayoutParams(lp);
				t.setGravity(Gravity.CENTER);
				ll.addView(t);
				
				LinearLayout buttonsLayout = new LinearLayout(this);
				@SuppressWarnings("deprecation")
				LayoutParams lparams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
				buttonsLayout.setLayoutParams(lparams);
				buttonsLayout.setGravity(Gravity.CENTER);
				buttonsLayout.setOrientation(LinearLayout.HORIZONTAL);
				buttonsLayout.setBackgroundColor(Color.parseColor("#f4f4f4"));
				
				//addbutton
				
				Button addButton = new Button(this);
				addButton.setBackgroundColor(Color.parseColor("#769824"));
				LayoutParams btparams = new LayoutParams(LayoutParams.WRAP_CONTENT, 80);
				btparams.setMargins(1, 0, 0, 0);
				addButton.setLayoutParams(btparams);
				addButton.setTextColor(Color.parseColor("#ffffff"));
				addButton.setText("ذخیره تست");
				addButton.setPadding(50, 0, 50, 0);
				addButton.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						FragmentManager fm = getSupportFragmentManager();
						SaveTestDialogFragment fragment = SaveTestDialogFragment.newInstance(mFinalPoints, mAnswer, mTestId);
						fragment.show(fm, "fragment_save_test");
					}
				});
				
				
				
				//share button
				ImageView shareButton = new ImageView(this);
				shareButton.setImageResource(R.drawable.share_blue);
				LayoutParams imgl = new LayoutParams(80,
						80);
				//imgl.gravity = Gravity.CENTER;
				shareButton.setLayoutParams(imgl);
				shareButton.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
						sharingIntent.setType("text/plain");
					    String shareBody = mAnswer + "\n" + "روانشناس همراه" + "\n" + "xxx";
					    
					    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, mTitle);
					    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
					    startActivity(Intent.createChooser(sharingIntent, "اشتراک گذاری از طریق"));
						
					}
				});
				
				buttonsLayout.addView(shareButton);
				buttonsLayout.addView(addButton);
				
				//endsharebutton
				
				LayoutParams lpp = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
				TextView di = new TextView(this);
				di.setTextColor(Color.parseColor("#333333"));
				di.setTextSize(16);
				lpp.gravity = Gravity.CENTER;
				lpp.setMargins(0, 15, 0, 0);
				di.setLayoutParams(lpp);
				di.setGravity(Gravity.CENTER);
				di.setTextColor(Color.parseColor("#ff5a00"));
				di.setText("ما را با امتیاز خود یاری کنید.ممنونیم.");
				
				
				ImageView rateButton = new ImageView(this);
				rateButton.setImageResource(R.drawable.rating);
				LayoutParams rimgl = new LayoutParams(150,
						LayoutParams.WRAP_CONTENT);
				
				rimgl.gravity = Gravity.CENTER;
				rateButton.setLayoutParams(rimgl);
				
				/*
				rateButton.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Intent i = new Intent(Intent.ACTION_EDIT);
						i.setData(Uri.parse("bazaar://details?id=com.dnaroid.psychologybook"));
						startActivity(i);
					}
				});
				*/
				/////////
				TextView dt = new TextView(this);
				dt.setTextColor(Color.parseColor("#333333"));
				dt.setTextSize(16);
				dt.setLayoutParams(lp);
				dt.setGravity(Gravity.RIGHT);
				dt.setBackgroundResource(R.drawable.rounded);
				dt.setLineSpacing(7, 1);
				int i = 0;
				while(i < answersArray.size()){
					AnswerModel am = answersArray.get(i);
					if(mFinalPoints >= am.getLow() && mFinalPoints <= am.getHigh()){
						dt.setText(am.getDescription());
						mAnswer = am.getDescription();
						ll.addView(dt);
						ll.addView(buttonsLayout);
						ll.addView(di);
						ll.addView(rateButton);
						break;
					}
					i++;
					
				}
				currentCount += 1;

			}
		}

	}

}
