package com.appbase.activities;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;

import com.appbase.R;

/**
 * 
 * @author aabuback
 * 
 *         This class acts a base class for all the activity which is being
 *         created in the app. All the Activities should extend this class. The
 *         most commonly used function Example: Saving the state, memory
 *         clearing etc needs to e handled only in this class.
 * 
 */
public class BaseActivity extends Activity {

	public  DrawerLayout mDrawerLayout;
	public  ActionBarDrawerToggle mDrawerToggle;
	public static  float SCREEN_WIDTH_DP;

	/**
	 * Called when the activity is starting.
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.launcher);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		this.overridePendingTransition(R.anim.enter, R.anim.exit);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		

	}

	/**
	 * Called after onCreate(Bundle) — or after onRestart() when the activity
	 * had been stopped, but is now again being displayed to the user.
	 */
	protected void onStart() {
		super.onStart();
		
		

	}

	/**
	 * Called after onStop() when the current activity is being re-displayed to
	 * the user (the user has navigated back to it).
	 */
	protected void onRestart() {
		super.onRestart();
		

	}

	/**
	 * Called after onRestoreInstanceState(Bundle), onRestart(), or onPause(),
	 * for your activity to start interacting with the user.
	 */
	protected void onResume() {
		super.onResume();
		
	}

	/**
	 * Called as part of the activity lifecycle when an activity is going into
	 * the background, but has not (yet) been killed.
	 */
	protected void onPause() {
		super.onPause();
		this.overridePendingTransition(R.anim.pop_enter, R.anim.pop_exit);

	}

	/**
	 * Called when you are no longer visible to the user.
	 */
	protected void onStop() {
		super.onStop();

	}

	/**
	 * Perform any final cleanup before an activity is destroyed.
	 */
	protected void onDestroy() {
		
		super.onDestroy();

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
	
		super.onSaveInstanceState(outState);

	}
	
	


	/**
	 * On selecting action bar icons
	 * */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.e("OnOption Selected","On option");
		
	      if (mDrawerToggle.onOptionsItemSelected(item)) {
	            return true;
	    }
		// Take appropriate action for each action item click
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		default:
			return super.onOptionsItemSelected(item);

		}

		return true;
	}
	
	public void initializeDrawer(){
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		// set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description for accessibility */
		R.string.drawer_close /* "close drawer" description for accessibility */
		) {
			public void onDrawerClosed(View view) {
				// getActionBar().setTitle("Closed");
				//invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				// getActionBar().setTitle("Opened");
				//invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		mDrawerLayout.closeDrawers();
		
		
		
	}
	
	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		Log.e("OnPostCreetae","Create");
		// Sync the toggle state after onRestoreInstanceState has occurred.
	 if(savedInstanceState!=null&&savedInstanceState.get("FromDealDetails")!=null||
			 savedInstanceState!=null&&savedInstanceState.get("FromSensorsList")!=null){
		 
	 }else{
		mDrawerToggle.syncState();
	 }
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		Log.e("OnPostCreetae","onConfigurationChanged");
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	private void findDensity(){
		Display display = getWindowManager().getDefaultDisplay();
	    DisplayMetrics outMetrics = new DisplayMetrics ();
	    display.getMetrics(outMetrics);

	    float density  = getResources().getDisplayMetrics().density;
	    float dpHeight = outMetrics.heightPixels / density;
	     SCREEN_WIDTH_DP  = outMetrics.widthPixels / density;
	}
}
