package com.appbase.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.appbase.R;
import com.appbase.fragments.LiveOrderFragment;

public class LiveOrderActivity extends BaseActivity {
	LiveOrderFragment mOrderListFragment;
	private boolean fetchFromServer	=	true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(savedInstanceState!=null){
			fetchFromServer	=	false;
		}
		
		if (getIntent().getBooleanExtra("EXIT", false)) {
			System.out.println("From Logut button press");
			Intent intent = new Intent(this,
					LauncherActivity.class);
			startActivity(intent);
		    finish();
		    
		}else{
		setContentView(R.layout.launcher);
		loadLiveOrderFragment();
		}
		
	}

	/**
	 * Load the LiveOrder fragment
	 * 
	 */

	private void loadLiveOrderFragment() {

		mOrderListFragment = new LiveOrderFragment();
		mOrderListFragment.FetchFromServerNeeded(fetchFromServer);
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();

		// fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit,
		// R.anim.pop_enter, R.anim.pop_exit);
		// Replace whatever is in the fragment_container view with this
		// fragment,
		// and add the transaction to the back stack
		fragmentTransaction.replace(R.id.fragment_holder, mOrderListFragment);
		
		// Commit the transaction
		fragmentTransaction.commit();

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
//		FragmentManager fragmentManager = getFragmentManager();
//
//		FragmentTransaction fragmentTransaction = fragmentManager
//				.beginTransaction();
//
//		// fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit,
//		// R.anim.pop_enter, R.anim.pop_exit);
//		// Replace whatever is in the fragment_container view with this
//		// fragment,
//		// and add the transaction to the back stack
//		fragmentTransaction.detach(mOrderListFragment);
//
//		// Commit the transaction
//		fragmentTransaction.commit();
		super.onSaveInstanceState(outState);

	}
	
	
	  @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        MenuInflater inflater = getMenuInflater();
	        inflater.inflate(R.menu.liveorder, menu);
	 
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
	        case R.id.action_settings:
	            
	        	loadSettingsFragment();
	            return true;
	       
	        }
	        
	        return true;
	    }
	 

		/**
		 * Load the Settings fragment
		 * 
		 */

		private void loadSettingsFragment() {

			Intent intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);

		}
}
