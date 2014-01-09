package com.appbase.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.SearchView;

import com.appbase.R;
import com.appbase.fragments.DealsDetailsFragment;
import com.appbase.fragments.MenuFragment;

public class MenuActivity extends BaseActivity implements SearchView.OnQueryTextListener,         SearchView.OnCloseListener {

	MenuFragment mMenuFragment;

	boolean fetchFromServer = true;
	SearchView searchView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null) {
			fetchFromServer = false;
		}
		resolveWidth();
	
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
		fragmentTransaction = fragmentManager
				.beginTransaction();
		
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

		// FragmentTransaction ft = getFragmentManager().beginTransaction();
		// ft.detach(mSignInFragment);
		// ft.commit();

	}
	
	
	private void  resolveWidth(){
		
		WindowManager wm = (WindowManager) this
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics metrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(metrics);

		if(metrics.widthPixels>1000){
			setContentView(R.layout.two_pane_layout);
			loadMenuAndDealsDetailFragment();
			
		}else{
			setContentView(R.layout.launcher);
			loadMenuFragment();
			
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
	    	super.onOptionsItemSelected(item);
	        // Take appropriate action for each action item click
	    	
	    	
	        switch (item.getItemId()) {
	        case R.id.action_search:
	        	SearchView searchView = (SearchView) item.getActionView();
               

	        	
	            return true;
	       
	        }
	        
	        return true;
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
		return false;
	}



}
