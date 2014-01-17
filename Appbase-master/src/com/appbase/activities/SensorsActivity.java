package com.appbase.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

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
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.sensors, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * On selecting action bar icons
	 * */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case android.R.id.home:
		     if (mDrawerToggle.onOptionsItemSelected(item)) {
		            return true;
		    }
			
			return true;
	
		case R.id.action_add:
			Log.e("Tapped","tapped");
			
			Intent intent = new Intent(SensorsActivity.this, SensorsListActivity.class);
			startActivity(intent);
		break;

		}
		return super.onOptionsItemSelected(item);

	}
	
	
}
