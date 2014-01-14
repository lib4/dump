/**
 * 
 * 
 */

package com.appbase.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.appbase.R;
import com.appbase.fragments.SignInFragment;

/**
 * 
 * @author aabuback Launcher class to simulate the Server request and image
 *         downloading part. Activity making use of open source volley network
 *         framework http request and response and Aquery framework for image
 *         downloading.
 * 
 */

public class LauncherActivity extends Activity {

	SignInFragment mSignInFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		setContentView(R.layout.launcher_no_drawer);
		loadSignInFragment();
	}

	/**
	 * Load the SignIn fragment
	 * 
	 */

	private void loadSignInFragment() {

		mSignInFragment = new SignInFragment();
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();

		// fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit,
		// R.anim.pop_enter, R.anim.pop_exit);
		// Replace whatever is in the fragment_container view with this
		// fragment,
		// and add the transaction to the back stack
		fragmentTransaction.add(R.id.fragment_holder, mSignInFragment);

		// Commit the transaction
		fragmentTransaction.commit();
		
	

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
	
			super.onSaveInstanceState(outState);

//			FragmentTransaction ft = getFragmentManager().beginTransaction();
//			ft.detach(mSignInFragment);
//			ft.commit();
		
	}

}
