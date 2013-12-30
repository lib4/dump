/**
 * 
 * 
 */

package com.appbase.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;

import com.appbase.R;
import com.appbase.datastorage.DBManager;
import com.appbase.fragments.SignInFragment;

/**
 * 
 * @author aabuback Launcher class to simulate the Server request and image
 *         downloading part. Activity making use of open source volley network
 *         framework http request and response and Aquery framework for image
 *         downloading.
 * 
 */

public class LauncherActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.launcher);
		loadSignInFragment();
		//dumpdataandget();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.launcher, menu);
		return true;
	}

	/**
	 * Load the SignIn fragment
	 * 
	 */

	private void loadSignInFragment() {

		SignInFragment mSignInFragment = new SignInFragment();
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		
		fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit,
				R.anim.pop_enter, R.anim.pop_exit);
		// Replace whatever is in the fragment_container view with this
		// fragment,
		// and add the transaction to the back stack
		fragmentTransaction.replace(R.id.fragment_holder, mSignInFragment);
		// Commit the transaction
		fragmentTransaction.commit();

	}
	
	
	/**
	 * Insert and fetch the Comments from local data storage
	 */
	
	private void dumpdataandget(){
//		DBManager appDbManager	=	new DBManager(this);
//		appDbManager.open();
//		appDbManager.insertComments();
//		appDbManager.fetchComments().close();
//		appDbManager.close();
	}
}
