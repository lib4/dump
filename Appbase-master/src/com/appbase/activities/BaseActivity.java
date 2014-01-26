package com.appbase.activities;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.Toast;

import com.appbase.R;
import com.appbase.fragments.MenuFragment;
import com.appbase.fragments.SettingsFragment;
import com.appbase.gcm.GCM_Constants;
import com.appbase.httphandler.HttpHandler;
import com.appbase.utils.Utils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

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
public class BaseActivity extends Activity implements
		SearchView.OnQueryTextListener, SearchView.OnCloseListener {

	public DrawerLayout mDrawerLayout;
	public ActionBarDrawerToggle mDrawerToggle;
	public static float SCREEN_WIDTH_DP;
	GoogleCloudMessaging gcm;
	AtomicInteger msgId = new AtomicInteger();
	Context context;

	String regid;
	SearchView searchView;
	private String TAG = "BASE ACTIVITY";
	Menu searchMenuItem;

	/**
	 * Called when the activity is starting.
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Utils.IS_TABLET = Utils.isTabletDevice(this);
		Toast.makeText(this, "" + Utils.IS_TABLET, 1000).show();

		if (!Utils.IS_TABLET) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		} else {

			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}

		this.overridePendingTransition(R.anim.enter, R.anim.exit);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		Utils.WIDTH_IN_DP = Utils.findDisplayWidthDensity(this);
		Utils.WIDTH_IN_PX = Utils.findDisplayWidthPixels(this);

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.e("MENU>>>>>>>>>>", "MENU>>>>>>>>>>>>>>>>>>>>>>>>>");
		searchMenuItem = menu;
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.cataloge_list, menu);
		searchView = (SearchView) menu.findItem(R.id.action_search)
				.getActionView();
		searchView.setOnQueryTextListener(this);
		searchView.setOnCloseListener(this);
		menu.findItem(R.id.action_search).setVisible(false);

		return super.onCreateOptionsMenu(menu);
	}

	public void showSearchActionItem() {
		if (searchMenuItem != null) {
			Log.e("text", "test" + searchMenuItem.findItem(R.id.action_search));
			searchMenuItem.findItem(R.id.action_search).setVisible(true);
		}
	}

	public void hideSearchActionItem() {
		if (searchMenuItem != null) {
			Log.e("text", "test" + searchMenuItem.findItem(R.id.action_search));
			searchMenuItem.findItem(R.id.action_search).setVisible(false);
		}
	}

	/**
	 * On selecting action bar icons
	 * */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		Log.e("OnOption Selected", "On option");

		if(!Utils.IS_TABLET){
				if (mDrawerToggle.onOptionsItemSelected(item)) {
					return true;
				}
		}
		// Take appropriate action for each action item click
		switch (item.getItemId()) {
		case android.R.id.home:
			if(!Utils.IS_TABLET)
			finish();
			else
				super.onBackPressed();
			break;
		default:
			return super.onOptionsItemSelected(item);

		}

		return true;
	}

	public void initializeDrawer() {

		if (!Utils.IS_TABLET) {
			mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

			// set a custom shadow that overlays the main content when the
			// drawer
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

				}

				public void onDrawerOpened(View drawerView) {

				}
			};
			mDrawerLayout.setDrawerListener(mDrawerToggle);
			mDrawerLayout.closeDrawers();

		}

	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		if (!Utils.IS_TABLET) {
			// Sync the toggle state after onRestoreInstanceState has occurred.
			if (savedInstanceState != null
					&& savedInstanceState.get("FromDealDetails") != null
					|| savedInstanceState != null
					&& savedInstanceState.get("FromSensorsList") != null
					|| savedInstanceState != null
					&& savedInstanceState.get("FromSensorDetails") != null) {

			} else {
				mDrawerToggle.syncState();
			}
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (!Utils.IS_TABLET)
			// Pass any configuration change to the drawer toggls
			mDrawerToggle.onConfigurationChanged(newConfig);
	}

	/**
	 * Check the device to make sure it has the Google Play Services APK. If it
	 * doesn't, display a dialog that allows users to download the APK from the
	 * Google Play Store or enable it in the device's system settings.
	 */
	private boolean checkPlayServices() {
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode, this,
						GCM_Constants.PLAY_SERVICES_RESOLUTION_REQUEST).show();
			} else {

				finish();
			}
			return false;
		}
		return true;
	}

	public void initiateGCMRegistration() {
		if (checkPlayServices()) {
			gcm = GoogleCloudMessaging.getInstance(this);
			regid = getRegistrationId(context);

			if (regid.isEmpty()) {
				registerInBackground();
			}
		}
	}

	/**
	 * Gets the current registration ID for application on GCM service, if there
	 * is one.
	 * <p>
	 * If result is empty, the app needs to register.
	 * 
	 * @return registration ID, or empty string if there is no existing
	 *         registration ID.
	 */
	private String getRegistrationId(Context context) {
		final SharedPreferences prefs = getGcmPreferences(context);
		String registrationId = prefs.getString(GCM_Constants.PROPERTY_REG_ID,
				"");
		if (registrationId.isEmpty()) {
			Log.i(TAG, "Registration not found.");
			return "";
		}
		// Check if app was updated; if so, it must clear the registration ID
		// since the existing regID is not guaranteed to work with the new
		// app version.
		int registeredVersion = prefs.getInt(
				GCM_Constants.PROPERTY_APP_VERSION, Integer.MIN_VALUE);
		int currentVersion = getAppVersion(context);
		if (registeredVersion != currentVersion) {
			Log.i(TAG, "App version changed.");
			return "";
		}
		return registrationId;
	}

	/**
	 * @return Application's {@code SharedPreferences}.
	 */
	private SharedPreferences getGcmPreferences(Context context) {
		// This sample app persists the registration ID in shared preferences,
		// but
		// how you store the regID in your app is up to you.
		return getSharedPreferences(BaseActivity.class.getSimpleName(),
				Context.MODE_PRIVATE);
	}

	/**
	 * @return Application's version code from the {@code PackageManager}.
	 */
	private static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			// should never happen
			throw new RuntimeException("Could not get package name: " + e);
		}
	}

	/**
	 * Registers the application with GCM servers asynchronously.
	 * <p>
	 * Stores the registration ID and the app versionCode in the application's
	 * shared preferences.
	 */
	private void registerInBackground() {
		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				String msg = "";
				try {
					if (gcm == null) {
						gcm = GoogleCloudMessaging.getInstance(context);
					}
					regid = gcm.register(GCM_Constants.SENDER_ID);
					msg = "Device registered, registration ID=" + regid;

					// You should send the registration ID to your server over
					// HTTP, so it
					// can use GCM/HTTP or CCS to send messages to your app.
					sendRegistrationIdToBackend(regid);

					// For this demo: we don't need to send it because the
					// device will send
					// upstream messages to a server that echo back the message
					// using the
					// 'from' address in the message.

					// Persist the regID - no need to register again.
					storeRegistrationId(context, regid);
				} catch (IOException ex) {
					msg = "Error :" + ex.getMessage();
					// If there is an error, don't just keep trying to register.
					// Require the user to click a button again, or perform
					// exponential back-off.
				}
				return msg;
			}

			@Override
			protected void onPostExecute(String msg) {
				// mDisplay.append(msg + "\n");
				Log.e("MESSAGE ", "" + msg);
			}
		}.execute(null, null, null);
	}

	/**
	 * Stores the registration ID and the app versionCode in the application's
	 * {@code SharedPreferences}.
	 * 
	 * @param context
	 *            application's context.
	 * @param regId
	 *            registration ID
	 */
	private void storeRegistrationId(Context context, String regId) {
		final SharedPreferences prefs = getGcmPreferences(context);
		int appVersion = getAppVersion(context);
		Log.i(TAG, "Saving regId on app version " + appVersion);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(GCM_Constants.PROPERTY_REG_ID, regId);
		editor.putInt(GCM_Constants.PROPERTY_APP_VERSION, appVersion);
		editor.commit();
	}

	private void sendRegistrationIdToBackend(String deviceToken) {

		new HttpHandler().sendDeviceToken(deviceToken, context, null);
	}

	public void closeDrawayer() {

		if (!Utils.IS_TABLET) {
			mDrawerLayout.closeDrawers();
		}
	}

	@Override
	public boolean onClose() {
		// TODO Auto-generated method stub

		return false;
	}

	@Override
	public boolean onQueryTextChange(String arg0) {
		// TODO Auto-generated method stub

		MenuFragment menuFragment = (MenuFragment) getFragmentManager()
				.findFragmentByTag(MenuFragment.class.getName());
		menuFragment.trgrSearch(arg0);
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		// TODO Auto-generated method stub

		hideKeyboard();
		return false;
	}

	/**
	 * Method to hide the keyboard
	 */

	private void hideKeyboard() {
		InputMethodManager imm = (InputMethodManager) this
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
	}
}
