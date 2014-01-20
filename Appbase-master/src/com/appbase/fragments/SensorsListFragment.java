package com.appbase.fragments;

import java.util.Collections;
import java.util.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.appbase.R;
import com.appbase.activities.SensorsListActivity;
import com.appbase.adapters.LeDeviceListAdapter;
import com.appbase.ble.BeaconParcel;
import com.appbase.ble.DeviceControlActivity;
import com.appbase.httphandler.HTTPResponseListener;
import com.appbase.httphandler.HttpHandler;
import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.ReadCharacteristics.BeaconCharacteristics;
import com.estimote.sdk.Region;
import com.estimote.sdk.Utils;
import com.estimote.sdk.service.BeaconService;

public class SensorsListFragment extends BaseFragment implements
		HTTPResponseListener {

	private static final String TAG = SensorsListActivity.class.getSimpleName();
	static ProgressDialog mDialog;

	// public static final String EXTRAS_TARGET_ACTIVITY =
	// "extrasTargetActivity";
	// public static final String EXTRAS_BEACON = "extrasBeacon";

	private static final int REQUEST_ENABLE_BT = 1234;
	private static final String ESTIMOTE_PROXIMITY_UUID = "B9407F30-F5F8-466E-AFF9-25556B57FE6D";
	private static final Region ALL_ESTIMOTE_BEACONS_REGION = new Region("rid",
			ESTIMOTE_PROXIMITY_UUID, null, null);

	private BeaconManager beaconManager;
	private LeDeviceListAdapter adapter;

	FrameLayout sensorListLayout;
	SensorsListFragment sensorsListFragment;
	List<Beacon> local_beacons;

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
		mDialog = null;
		// Inflate the layout for this fragment
		sensorListLayout = (FrameLayout) inflater.inflate(
				R.layout.sensors_list_fragment, container, false);
		init();
		sensorsListFragment = this;
		trbrGetActiveSensors();
		return sensorListLayout;
	}

	private void init() {

		// Configure device list.
		adapter = new LeDeviceListAdapter(getActivity());
		ListView list = (ListView) sensorListLayout
				.findViewById(R.id.device_list);
		list.setAdapter(adapter);
		// list.setOnItemClickListener(createOnItemClickListener());

	}

	@Override
	public void onDestroy() {

		beaconManager.disconnect();

		super.onDestroy();
	}

	@Override
	public void onStart() {
		super.onStart();

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
		// getActionBar().setSubtitle("Scanning...");
		adapter.replaceWith(Collections.<Beacon> emptyList());
		beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
			@Override
			public void onServiceReady() {
				try {
					
					
					
					
					beaconManager.startRanging(ALL_ESTIMOTE_BEACONS_REGION);
					Log.e("connectToService ", "connectToService Ranging");
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

		if (mDialog != null && mDialog.isShowing())
			mDialog.dismiss();
		mHandler.sendMessage(new Message());
	}

	@Override
	public void onFailure(int failureCode) {
		// TODO Auto-generated method stub

	}

	public void trbrGetActiveSensors() {
		mDialog = new ProgressDialog(getActivity());
		mDialog.setMessage(getActivity().getString(R.string.loading));
		mDialog.setCancelable(false);
		mDialog.show();

		// Configure BeaconManager.
		beaconManager = new BeaconManager(getActivity());
		
		beaconManager.setRangingListener(new BeaconManager.RangingListener() {
			@Override
			public void onBeaconsDiscovered(Region region,
					final List<Beacon> beacons) {

				if (beacons != null && beacons.size() > 0) {
					Log.e("connectToService beacons found ",
							"connectToService Ranging" + beacons.size());
					local_beacons = beacons;
					
					
					
					
					Beacon mBeacon	=	beacons.get(0);
					
				
					
					Parcel mBeaconParcel 	=	Parcel.obtain();
					//mBeaconParcel.writeByteArray(mBeacon.getProximityUUID().getBytes());
					//mBeaconParcel.writeByteArray(mBeacon.getName().getBytes());
					//mBeaconParcel.writeByteArray(mBeacon.getMacAddress().getBytes());
					
					//mBeaconParcel.writeByteArray("1".getBytes());
					mBeaconParcel.writeByteArray("1".getBytes());
					//mBeaconParcel.writeInt(mBeacon.getMeasuredPower());
					//mBeaconParcel.writeInt(mBeacon.getRssi());
					//mBeacon.writeToParcel(mBeaconParcel, 0);
					
					//mBeaconParcel.recycle();
					//region.writeToParcel(mBeaconParcel, 0);
					DeviceControlActivity mConnectToEstimote	=	new DeviceControlActivity(getActivity(),beacons.get(0).getMacAddress());
					Log.e("DONE beacons found ",
							"DONE " );
					
					new HttpHandler().getSensors(getActivity(),
							sensorsListFragment, beacons);
					try {
						beaconManager.stopRanging(ALL_ESTIMOTE_BEACONS_REGION);
					} catch (RemoteException e) {
						Log.d(TAG, "Error while stopping ranging", e);
					}

				}

			}
		});

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

	final Handler mHandler = new Handler(Looper.getMainLooper()) {

		public void handleMessage(Message msg) {

			Log.e("HANDLER ", "HANDLER ");

			if (msg.arg1 == HttpHandler.NO_NETWORK_CODE) {

				showNoNetworkAlertDialog();
			} else if (msg.arg1 == HttpHandler.DEFAULT_CODE) {// Empty Message
																// layout.

			}

			else {

				// Note that results are not delivered on UI thread.

				adapter.replaceWith(local_beacons);

			}
		}
	};

	private void showNoNetworkAlertDialog() {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				getActivity());

		// set title
		// alertDialogBuilder.setTitle("Your Title");

		// set dialog message
		alertDialogBuilder
				.setMessage(
						getActivity().getString(R.string.uname_not_matching))
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// if this button is clicked, close
						// current activity

					}
				})

				.setNegativeButton("Settings",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// if this button is clicked, close
								// current activity
								startActivity(new Intent(
										android.provider.Settings.ACTION_SETTINGS));
							}
						});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}

}
