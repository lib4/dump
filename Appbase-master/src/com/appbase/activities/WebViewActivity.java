package com.appbase.activities;

import com.appbase.R;
import com.appbase.fragments.WebViewFragment;
import com.appbase.utils.Utils;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class WebViewActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.launcher);
		loadWebViewFragment();
		
	}
	
	
	
	/**
	 * Load the SignIn fragment
	 * 
	 */

	private void loadWebViewFragment() {

		WebViewFragment mWebViewFragment = new WebViewFragment();
		mWebViewFragment.loadUrl(true, Utils.ABOUT_URL);
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
	
}
