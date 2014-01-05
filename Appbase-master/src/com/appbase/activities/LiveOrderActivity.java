package com.appbase.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

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
		setContentView(R.layout.launcher);
		loadLiveOrderFragment();
		
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
	
	



}
