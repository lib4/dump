package com.appbase.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.appbase.R;
import com.appbase.fragments.SensorsFragment;
import com.appbase.utils.Utils;

public class SensorsActivity extends BaseActivity {

	
	SensorsFragment mSensorsFragment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.launcher);
		loadSensorsFragment();
		initializeDrawer();
		
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
	
	
	/**
	 * On selecting action bar icons
	 * */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// Take appropriate action for each action item click

		switch (item.getItemId()) {
		case android.R.id.home:
		     if (mDrawerToggle.onOptionsItemSelected(item)) {
		            return true;
		    }
			
			return true;

		}
		return super.onOptionsItemSelected(item);

	}


	 @Override
	  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		 
		 Log.e("On Activity Result","on  Activity result.");
	    if (requestCode == Utils.REQUEST_ENABLE_BT) {
	      if (resultCode == Activity.RESULT_OK) {
	    	  if(mSensorsFragment!=null){
	    		  mSensorsFragment.findThisBeacon(mSensorsFragment.ProximityId, 
	    				  mSensorsFragment.major, mSensorsFragment.minor, mSensorsFragment.itemindex);
	    	  }
	      } else {
	        Toast.makeText(this, "Bluetooth not enabled", Toast.LENGTH_LONG).show();
	        getActionBar().setSubtitle("Bluetooth not enabled");
	      }
	    }
	    super.onActivityResult(requestCode, resultCode, data);
	  }

	
	
}
