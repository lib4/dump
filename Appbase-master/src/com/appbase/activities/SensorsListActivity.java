package com.appbase.activities;

import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.appbase.R;
import com.appbase.adapters.LeDeviceListAdapter;
import com.appbase.fragments.SensorsFragment;
import com.appbase.fragments.SensorsListFragment;
import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

public class SensorsListActivity extends BaseActivity {







	  private BeaconManager beaconManager;
	  private LeDeviceListAdapter adapter;
	  SensorsListFragment sensorsListFragment;

	  @Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.launcher);
			loadSensorsFragment();
			initializeDrawer();
			
		}
		
		
		private void loadSensorsFragment(){
			
			sensorsListFragment = new SensorsListFragment();
		
			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager
					.beginTransaction();

			// fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit,
			// R.anim.pop_enter, R.anim.pop_exit);
			// Replace whatever is in the fragment_container view with this
			// fragment,
			// and add the transaction to the back stack
			fragmentTransaction.replace(R.id.fragment_holder, sensorsListFragment);

			// Commit the transaction
			fragmentTransaction.commit();
		}
		
		
		





	}
