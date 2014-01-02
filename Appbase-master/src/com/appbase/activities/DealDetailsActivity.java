package com.appbase.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.appbase.R;
import com.appbase.fragments.DealsDetailsFragment;

public class DealDetailsActivity extends BaseActivity {

	DealsDetailsFragment mDealsDetailsFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.launcher);
		loadDealDetailsFragment();
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

	@Override
	public void onSaveInstanceState(Bundle outState) {

		super.onSaveInstanceState(outState);

		// FragmentTransaction ft = getFragmentManager().beginTransaction();
		// ft.detach(mSignInFragment);
		// ft.commit();

	}

}
