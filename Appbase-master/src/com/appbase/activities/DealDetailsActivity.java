package com.appbase.activities;

import org.json.JSONException;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;

import com.appbase.R;
import com.appbase.fragments.DealsDetailsFragment;
import com.appbase.fragments.MenuFragment;
import com.appbase.fragments.WebViewFragment;

public class DealDetailsActivity extends BaseActivity {

	DealsDetailsFragment mDealsDetailsFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.launcher);
		try {
			if (MenuFragment.cardObject.getString("cataloge_type")
					.compareToIgnoreCase("info") == 0) {

				loadWebViewFragment();

			} else {
				loadDealDetailsFragment();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initializeDrawer();
	}

	/**
	 * Load the SignIn fragment
	 * 
	 */

	private void loadDealDetailsFragment() {

		mDealsDetailsFragment = new DealsDetailsFragment();
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();

		// fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit,
		// R.anim.pop_enter, R.anim.pop_exit);
		// Replace whatever is in the fragment_container view with this
		// fragment,
		// and add the transaction to the back stack
		fragmentTransaction.add(R.id.fragment_holder, mDealsDetailsFragment);

		// Commit the transaction
		fragmentTransaction.commit();

	}

	/**
	 * Load the SignIn fragment
	 * 
	 */

	private void loadWebViewFragment() {

		WebViewFragment mWebViewFragment = new WebViewFragment();
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();

		// fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit,
		// R.anim.pop_enter, R.anim.pop_exit);
		// Replace whatever is in the fragment_container view with this
		// fragment,
		// and add the transaction to the back stack
		fragmentTransaction.add(R.id.fragment_holder, mWebViewFragment);

		// Commit the transaction
		fragmentTransaction.commit();

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

		// Take appropriate action for each action item click

		switch (item.getItemId()) {
		case android.R.id.home:

			finish();
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
		savedInstanceState.putBoolean("FromDealDetails", true);
		super.onPostCreate(savedInstanceState);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

}
