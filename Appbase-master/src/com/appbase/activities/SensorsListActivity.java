package com.appbase.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.appbase.R;
import com.appbase.fragments.SensorsListFragment;

public class SensorsListActivity extends BaseActivity {

	SensorsListFragment sensorsListFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.launcher);
		loadSensorsFragment();
		initializeDrawer();

	}

	private void loadSensorsFragment() {

		sensorsListFragment = new SensorsListFragment();

		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		fragmentTransaction.replace(R.id.fragment_holder, sensorsListFragment);

		// Commit the transaction
		fragmentTransaction.commit();
	}
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.sensors_list, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * On selecting action bar icons
	 * */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// Take appropriate action for each action item click

		switch (item.getItemId()) {
		case android.R.id.home:

			finish();
			return true;
			
		case R.id.action_refresh:
			sensorsListFragment.trbrGetActiveSensors();
			
			return true;

		}

		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		// Sync the toggle state after onRestoreInstanceState has occurred.
		savedInstanceState	=	new Bundle();
		savedInstanceState.putBoolean("FromSensorsList", true);
		super.onPostCreate(savedInstanceState);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}


}
