package com.appbase.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.appbase.R;
import com.appbase.fragments.SettingsFragment;
import com.appbase.fragments.SignInFragment;

public class SettingsActivity extends BaseActivity {

	SettingsFragment mSettingsFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.launcher);
		loadSettingsFragment();
	}

	/**
	 * Load the SignIn fragment
	 * 
	 */

	private void loadSettingsFragment() {

		mSettingsFragment = new SettingsFragment();
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();

		// fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit,
		// R.anim.pop_enter, R.anim.pop_exit);
		// Replace whatever is in the fragment_container view with this
		// fragment,
		// and add the transaction to the back stack
		fragmentTransaction.add(R.id.fragment_holder, mSettingsFragment);

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
