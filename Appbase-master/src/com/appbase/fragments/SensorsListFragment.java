package com.appbase.fragments;

import java.util.Collections;
import java.util.List;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.appbase.R;
import com.appbase.activities.SensorsListActivity;
import com.appbase.adapters.LeDeviceListAdapter;
import com.appbase.datastorage.DBManager;
import com.appbase.httphandler.HTTPResponseListener;
import com.appbase.utils.Utils;
import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

public class SensorsListFragment extends BaseFragment implements
		HTTPResponseListener {

	private static final String TAG = SensorsListActivity.class.getSimpleName();

	public static final String EXTRAS_TARGET_ACTIVITY = "extrasTargetActivity";
	public static final String EXTRAS_BEACON = "extrasBeacon";

	private static final int REQUEST_ENABLE_BT = 1234;
	private static final String ESTIMOTE_PROXIMITY_UUID = "B9407F30-F5F8-466E-AFF9-25556B57FE6D";
	private static final Region ALL_ESTIMOTE_BEACONS_REGION = new Region("rid",
			ESTIMOTE_PROXIMITY_UUID, null, null);

	private BeaconManager beaconManager;
	private LeDeviceListAdapter adapter;

	FrameLayout sensorListLayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			// We have different layouts, and in one of them this
			// fragment's containing frame doesn't exist. The fragment
			// may still be created from its saved state, but there is
			// no reason to try to create its view hierarchy because it
			// won't be displayed. Note this is not needed -- we could
			// just run the code below, where we would create and return
			// the view hierarchy; it would just never be used.
			return null;
		}

		// Inflate the layout for this fragment
		sensorListLayout = (FrameLayout) inflater.inflate(
				R.layout.sensors_list_fragment, container, false);
		init();
		return sensorListLayout;
	}

	private void init() {

		// Configure device list.
		adapter = new LeDeviceListAdapter(getActivity());
		ListView list = (ListView) sensorListLayout
				.findViewById(R.id.device_list);
		list.setAdapter(adapter);
		//list.setOnItemClickListener(createOnItemClickListener());

		// Configure verbose debug logging.

		// Configure BeaconManager.
		beaconManager = new BeaconManager(getActivity());
		beaconManager.setRangingListener(new BeaconManager.RangingListener() {
			@Override
			public void onBeaconsDiscovered(Region region,
					final List<Beacon> beacons) {
				// Note that results are not delivered on UI thread.
				/*
				 * runOnUiThread(new Runnable() {
				 * 
				 * @Override public void run() {
				 * getActionBar().setSubtitle("Found beacons: " +
				 * beacons.size()); adapter.replaceWith(beacons); } });
				 */
			}
		});
	}

	@Override
	public void onDestroy() {
		beaconManager.disconnect();

		super.onDestroy();
	}

	@Override
	public void onStart() {
		super.onStart();

		// Check if device supports Bluetooth Low Energy.
		if (!beaconManager.hasBluetooth()) {
			Toast.makeText(getActivity(),
					"Device does not have Bluetooth Low Energy",
					Toast.LENGTH_LONG).show();
			return;
		}

		// If Bluetooth is not enabled, let user enable it.
		if (!beaconManager.isBluetoothEnabled()) {
			Intent enableBtIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		} else {
			connectToService();
		}
	}

	@Override
	public void onStop() {
		try {
			beaconManager.stopRanging(ALL_ESTIMOTE_BEACONS_REGION);
		} catch (RemoteException e) {
			Log.d(TAG, "Error while stopping ranging", e);
		}

		super.onStop();
	}

	private void connectToService() {
		//getActionBar().setSubtitle("Scanning...");
		adapter.replaceWith(Collections.<Beacon> emptyList());
		beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
			@Override
			public void onServiceReady() {
				try {
					beaconManager.startRanging(ALL_ESTIMOTE_BEACONS_REGION);
				} catch (RemoteException e) {
					Toast.makeText(
							getActivity(),
							"Cannot start ranging, something terrible happened",
							Toast.LENGTH_LONG).show();
					Log.e(TAG, "Cannot start ranging", e);
				}
			}
		});
	}

	@Override
	public void onSuccess() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFailure(int failureCode) {
		// TODO Auto-generated method stub

	}

}
