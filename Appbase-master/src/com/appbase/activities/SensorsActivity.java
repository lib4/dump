package com.appbase.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.appbase.R;
import com.appbase.fragments.MenuFragment;
import com.appbase.fragments.SensorsFragment;

public class SensorsActivity extends BaseActivity {

	
	SensorsFragment mSensorsFragment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.launcher);
		loadSensorsFragment();
		
	}
	
	
	private void loadSensorsFragment(){
		
		mSensorsFragment = new SensorsFragment();
	
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();

		// fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit,
		// R.anim.pop_enter, R.anim.pop_exit);
		// Replace whatever is in the fragment_container view with this
		// fragment,
		// and add the transaction to the back stack
		fragmentTransaction.replace(R.id.fragment_holder, mSensorsFragment);

		// Commit the transaction
		fragmentTransaction.commit();
	}
	
}
