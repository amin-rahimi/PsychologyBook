package com.dnaroid.psychologybook;

import java.util.ArrayList;

import com.dnaroid.psychologybook.adapters.MenuItemAdapter;
import com.dnaroid.psychologybook.fragments.AboutFragment;
import com.dnaroid.psychologybook.fragments.CategoryFragment;
import com.dnaroid.psychologybook.fragments.CategoryTopicsFragment;
import com.dnaroid.psychologybook.fragments.FavorateFragment;
import com.dnaroid.psychologybook.fragments.SearchResultFragment;
import com.dnaroid.psychologybook.model.History;
import com.dnaroid.psychologybook.model.MenuItemEntry;
import com.dnaroid.psychologybook.model.MenuItemInterface;
import com.dnaroid.psychologybook.model.MenuItemSection;
import com.dnaroid.psychologybook.preferences.PsyApplication;
import com.dnaroid.psychologybook.preferences.SharedPrefrencesManager;
import com.dnaroid.psychologybook.R;

import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v4.view.MenuItemCompat;

public class PsychologyActivity extends ActionBarActivity {

	private ActionBarDrawerToggle mDrawerToggle;
	private DrawerLayout mDrawerLayout;
	private ArrayList<MenuItemInterface> mMenuItems = new ArrayList<MenuItemInterface>();
	ListView mListView = null;
	SharedPrefrencesManager shpm;
	private Boolean mParentalAccess = false;

	private boolean doubleBackToExitPressedOnce;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_psychology);
		// setDefaultUncaughtExceptionHandler();
		shpm = new SharedPrefrencesManager(this);

		/*
		 * if (shpm.isFirstRun()) { shpm.setParentalControlAccess(false);
		 * shpm.setParentalPassword("122333"); shpm.setFirstRun();
		 * 
		 * }
		 */

		// mParentalAccess = shpm.getParentalControlAcceess();

		getSupportActionBar().setBackgroundDrawable(
				getResources().getDrawable(R.drawable.actionbar_background));
		getSupportActionBar().setTitle("");

		getSupportActionBar().setLogo(R.drawable.logo);

		if (savedInstanceState == null) {
			CategoryFragment fragment = new CategoryFragment();
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment).commit();

			((PsyApplication) getApplication()).setHistory(new History(
					"category", -1));
		}

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				supportInvalidateOptionsMenu(); // creates call to
												// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				supportInvalidateOptionsMenu(); // creates call to
												// onPrepareOptionsMenu()
			}

		};

		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		// mDrawerLayout.setScrimColor(getResources().getColor(
		// R.color.drawer_layout_shadow));
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		mMenuItems.clear();
		mListView = (ListView) findViewById(R.id.left_drawer_list);

		if (!shpm.isPurchased()) {
			mMenuItems.add(new MenuItemEntry(
					"نسخه رایگان - ارتقا به نسخه کامل", -3));
		}

		mMenuItems.add(new MenuItemSection("دسته بندی ها"));
		mMenuItems.add(new MenuItemEntry("تست های روانشناسی (گزینه ای)", 16));
		mMenuItems.add(new MenuItemEntry("تست های روانشناسی", 13));
		mMenuItems.add(new MenuItemEntry("مشاور تغذیه", 14));
		// mMenuItems.add(new MenuItemEntry("روانشناسی زناشویی", 1));
		// mMenuItems.add(new MenuItemEntry("روانشناسی ازدواج", 8));
		mMenuItems.add(new MenuItemEntry("روانشناسی رنگها", 12));
		mMenuItems.add(new MenuItemEntry("روانشناسی موفقیت", 2));
		mMenuItems.add(new MenuItemEntry("روانشناسی خانواده", 3));
		mMenuItems.add(new MenuItemEntry("فرزندان و امتحانات", 4));
		mMenuItems.add(new MenuItemEntry("والدین موفق", 5));
		mMenuItems.add(new MenuItemEntry("روانشناسی کودک و نوجوان", 6));
		mMenuItems.add(new MenuItemEntry("روانشناسی روابط", 7));

		mMenuItems.add(new MenuItemEntry("روانشناسی تبلیغات", 9));
		mMenuItems.add(new MenuItemEntry("روانشناسی مدیریت", 10));
		mMenuItems.add(new MenuItemEntry("روانشناسی ثروت افرینی", 11));
		// mMenuItems.add(new MenuItemEntry("مشاور جنسی (برای متاهلین)", 15));

		mMenuItems.add(new MenuItemSection("دیگر"));
		mMenuItems.add(new MenuItemEntry("حمایت از ما با درج امتیاز", -5));
		mMenuItems.add(new MenuItemEntry("آنچه من میخواهم", -1));
		// mMenuItems.add(new MenuItemEntry("تنظیمات", -4));
		mMenuItems.add(new MenuItemEntry("درباره ما", -2));

		MenuItemAdapter adapter = new MenuItemAdapter(this, mMenuItems);
		mListView.setAdapter(adapter);

		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				MenuItemEntry menuItemEntry = (MenuItemEntry) mListView
						.getItemAtPosition(position);

				if (menuItemEntry.getId() > 0) {

					/*
					 * if (menuItemEntry.getId() == 15) { if (!mParentalAccess)
					 * { Toast.makeText( PsychologyActivity.this,
					 * "دسترسی این قسمت در حال حاضر بسته میباشد. برای اطلاعات بیشتر به تنظیمات نرم افزار مراجعه بفرمایید"
					 * , Toast.LENGTH_LONG).show(); return; } }
					 */

					CategoryTopicsFragment fragment = CategoryTopicsFragment
							.newInstance(menuItemEntry.getId(), 0);
					FragmentManager fragmentManager = getSupportFragmentManager();
					fragmentManager.beginTransaction()
							.replace(R.id.content_frame, fragment).commit();

					((PsyApplication) getApplication()).setHistory(new History(
							"category_items", -1));
				} else if (menuItemEntry.getId() == -1) {
					FavorateFragment fragment = new FavorateFragment();
					FragmentManager fragmentManager = getSupportFragmentManager();
					fragmentManager.beginTransaction()
							.replace(R.id.content_frame, fragment).commit();

					((PsyApplication) getApplication()).setHistory(new History(
							"category_items", -1));

				} else if (menuItemEntry.getId() == -2) {
					AboutFragment fragment = new AboutFragment();
					FragmentManager fragmentManager = getSupportFragmentManager();
					fragmentManager.beginTransaction()
							.replace(R.id.content_frame, fragment).commit();

					((PsyApplication) getApplication()).setHistory(new History(
							"category_items", -1));
				} else if (menuItemEntry.getId() == -3) {
					Intent i = new Intent(PsychologyActivity.this,
							PurchaseActivity.class);
					startActivityForResult(i, 10);
				} else if (menuItemEntry.getId() == -4) {
					Intent i = new Intent(PsychologyActivity.this,
							SettingsActivity.class);
					startActivity(i);
				} else if (menuItemEntry.getId() == -5) {
					/*
					Intent i = new Intent(Intent.ACTION_EDIT);
					i.setData(Uri
							.parse("xxx"));
					startActivity(i);
					*/
				}
				mDrawerLayout.closeDrawers();

			}

		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.psychology, menu);
		// Get the SearchView and set the searchable configuration
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

		MenuItem searchItem = menu.findItem(R.id.action_search);

		SearchView searchView = (SearchView) MenuItemCompat
				.getActionView(searchItem);

		searchView.setSearchableInfo(searchManager
				.getSearchableInfo(getComponentName()));
		MenuItemCompat.setOnActionExpandListener(searchItem,
				new MenuItemCompat.OnActionExpandListener() {

					@Override
					public boolean onMenuItemActionExpand(MenuItem arg0) {
						// TODO Auto-generated method stub
						return true;
					}

					@Override
					public boolean onMenuItemActionCollapse(MenuItem arg0) {
						// TODO Auto-generated method stub
						History history = ((PsyApplication) getApplication())
								.getHistory();
						if (history.getCurrentPage().endsWith("topic")) {
							return true;
						}
						CategoryFragment fragment = new CategoryFragment();
						FragmentManager fragmentManager = getSupportFragmentManager();
						fragmentManager.beginTransaction()
								.replace(R.id.content_frame, fragment).commit();
						((PsyApplication) getApplication())
								.setHistory(new History("category", -1));
						return true;
					}
				});

		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String query) {
				// TODO Auto-generated method stub
				// Toast.makeText(getBaseContext(), query, Toast.LENGTH_SHORT)
				// .show();
				SearchResultFragment fragment = SearchResultFragment
						.newInstance(query);
				FragmentManager fragmentManager = getSupportFragmentManager();
				fragmentManager.beginTransaction()
						.replace(R.id.content_frame, fragment).commit();
				((PsyApplication) getApplication()).setHistory(new History(
						"search", -1));
				return true;
			}

			@Override
			public boolean onQueryTextChange(String arg0) {
				// TODO Auto-generated method stub
				return false;
			}

		});

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		int id = item.getItemId();
		if (id == R.id.action_search) {
			return true;
		}
		if (id == R.id.action_settings) {
			Intent i = new Intent(PsychologyActivity.this,
					SettingsActivity.class);
			startActivity(i);
			return true;
		}
		if (id == R.id.action_favorate) {
			FavorateFragment fragment = new FavorateFragment();
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment).commit();

			((PsyApplication) getApplication()).setHistory(new History(
					"category_items", -1));
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		History history = ((PsyApplication) getApplication()).getHistory();

		
		
		if(history == null){
			CategoryFragment fragment = new CategoryFragment();
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment).commit();

			((PsyApplication) getApplication()).setHistory(new History(
					"category", -1));
			return;
		}

		if (history.getCurrentPage().equals("category")) {
			if (doubleBackToExitPressedOnce) {
				super.onBackPressed();
				return;
			}
			this.doubleBackToExitPressedOnce = true;
			Toast.makeText(this, "برای خروج یکبار دیگر دکمه را فشار دهید",
					Toast.LENGTH_SHORT).show();

			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					doubleBackToExitPressedOnce = false;
				}
			}, 2000);

		}

		if (history.getCurrentPage().equals("category_items")) {
			CategoryFragment fragment = new CategoryFragment();
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment).commit();

			((PsyApplication) getApplication()).setHistory(new History(
					"category", -1));
		}

		if (history.getCurrentPage().equals("search")) {
			CategoryFragment fragment = new CategoryFragment();
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment).commit();

			((PsyApplication) getApplication()).setHistory(new History(
					"category", -1));
		}

		if (history.getCurrentPage().equals("topic")) {
			
			
			
			try{
			CategoryTopicsFragment fragment = CategoryTopicsFragment
					.newInstance(history.getCategoryId(),
							history.getScrollPosition());
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment).commit();

			((PsyApplication) getApplication()).setHistory(new History(
					"category_items", -1));
			}catch(Exception e){
				CategoryFragment fragment = new CategoryFragment();
				FragmentManager fragmentManager = getSupportFragmentManager();
				fragmentManager.beginTransaction()
						.replace(R.id.content_frame, fragment).commit();

				((PsyApplication) getApplication()).setHistory(new History(
						"category", -1));
			}
		}

		if (history.getCurrentPage().equals("topicFav")) {
			FavorateFragment fragment = new FavorateFragment();
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment).commit();

			((PsyApplication) getApplication()).setHistory(new History(
					"category_items", -1));
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == 10) {
			Intent intent = new Intent(this, PsychologyActivity.class);
			this.startActivity(intent);
			finish();
		}
	}

	private void setDefaultUncaughtExceptionHandler() {
		try {
			Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

				@Override
				public void uncaughtException(Thread t, Throwable e) {
					Log.d("Uncaught Exception detected in thread {}",
							t.toString(), e);
				}
			});
		} catch (SecurityException e) {
			Intent i = new Intent(PsychologyActivity.this,
					PsychologyActivity.class);
		}
	}

}
