package com.appbase.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import com.appbase.R;
import com.appbase.fragments.DealsDetailsFragment;
import com.appbase.fragments.MenuFragment;
import com.appbase.utils.Utils;

public class MenuActivity extends BaseActivity implements
		SearchView.OnQueryTextListener, SearchView.OnCloseListener {

	MenuFragment mMenuFragment;

	boolean fetchFromServer = true;
	SearchView searchView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setTitle(Utils.BUSINESS_NAME);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		if (savedInstanceState != null) {
			fetchFromServer = false;
		}
		resolveWidth();
		initializeDrawer();

	}

	/**
	 * Load the SignIn fragment
	 * 
	 */

	private void loadMenuFragment() {

		mMenuFragment = new MenuFragment();
		mMenuFragment.FetchFromServerNeeded(fetchFromServer);
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();

		// fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit,
		// R.anim.pop_enter, R.anim.pop_exit);
		// Replace whatever is in the fragment_container view with this
		// fragment,
		// and add the transaction to the back stack
		fragmentTransaction.replace(R.id.fragment_holder, mMenuFragment);

		// Commit the transaction
		fragmentTransaction.commit();

	}

	/**
	 * Load the SignIn fragment
	 * 
	 */

	private void loadMenuAndDealsDetailFragment() {

		mMenuFragment = new MenuFragment();
		mMenuFragment.FetchFromServerNeeded(fetchFromServer);
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();

		// fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit,
		// R.anim.pop_enter, R.anim.pop_exit);
		// Replace whatever is in the fragment_container view with this
		// fragment,
		// and add the transaction to the back stack
		fragmentTransaction.replace(R.id.slide_list, mMenuFragment);

		// Commit the transaction
		fragmentTransaction.commit();

		DealsDetailsFragment mDealsDetailsFragment = new DealsDetailsFragment();
		mDealsDetailsFragment.hideHeaderBar(true);
		fragmentManager = getFragmentManager();
		fragmentTransaction = fragmentManager.beginTransaction();

		// fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit,
		// R.anim.pop_enter, R.anim.pop_exit);
		// Replace whatever is in the fragment_container view with this
		// fragment,
		// and add the transaction to the back stack
		fragmentTransaction.replace(R.id.content_pane, mDealsDetailsFragment);

		// Commit the transaction
		fragmentTransaction.commit();

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {

		super.onSaveInstanceState(outState);

	}

	private void resolveWidth() {

		// WindowManager wm = (WindowManager) this
		// .getSystemService(Context.WINDOW_SERVICE);
		// DisplayMetrics metrics = new DisplayMetrics();
		// wm.getDefaultDisplay().getMetrics(metrics);
		//
		// if(metrics.widthPixels>1000){
		// setContentView(R.layout.two_pane_layout);
		// loadMenuAndDealsDetailFragment();
		//
		// }else{
		// setContentView(R.layout.launcher);
		// loadMenuFragment();
		//
		// }
		//

		final int rotation = ((WindowManager) getSystemService(Context.WINDOW_SERVICE))
				.getDefaultDisplay().getOrientation();
		switch (rotation) {
		case Surface.ROTATION_0:
		case Surface.ROTATION_180:
			setContentView(R.layout.launcher);
			loadMenuFragment();
			break;
		case Surface.ROTATION_90:
		default:
			setContentView(R.layout.two_pane_layout);
			loadMenuAndDealsDetailFragment();
			break;

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.cataloge_list, menu);
		searchView = (SearchView) menu.findItem(R.id.action_search)
				.getActionView();
		searchView.setOnQueryTextListener(this);
		searchView.setOnCloseListener(this);

		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * On selecting action bar icons
	 * */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case android.R.id.home:
			if (mDrawerToggle.onOptionsItemSelected(item)) {
				return true;
			}

			return true;

		}
		return super.onOptionsItemSelected(item);

	}

	@Override
	public boolean onClose() {
		// TODO Auto-generated method stub

		return false;
	}

	@Override
	public boolean onQueryTextChange(String arg0) {
		// TODO Auto-generated method stub
		mMenuFragment.trgrSearch(arg0);
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
