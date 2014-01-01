package com.appbase.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.appbase.R;
import com.appbase.fragments.LiveOrderFragment;


public class LiveOrderActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.launcher);
		loadLiveOrderFragment();

	}

	/**
	 * Load the LiveOrder fragment
	 * 
	 */

	private void loadLiveOrderFragment() {

		LiveOrderFragment mOrderListFragment = new LiveOrderFragment();
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();

		// fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit,
		// R.anim.pop_enter, R.anim.pop_exit);
		// Replace whatever is in the fragment_container view with this
		// fragment,
		// and add the transaction to the back stack
		fragmentTransaction.add(R.id.fragment_holder, mOrderListFragment);

		// Commit the transaction
		fragmentTransaction.commit();

	}

}
