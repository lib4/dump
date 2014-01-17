package com.appbase.fragments;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.appbase.R;
import com.appbase.adapters.SensorsAdapter;
import com.appbase.datastorage.DBManager;
import com.appbase.httphandler.HTTPResponseListener;
import com.appbase.httphandler.HttpConstants;
import com.appbase.httphandler.HttpHandler;
import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

public class SensorsFragment extends BaseFragment implements
		HTTPResponseListener, OnItemClickListener {

	LinearLayout sensorsLayout;
	ListView sensorsList;
	SensorsAdapter mSensorsAdapter;
	JSONArray sensorArray = new JSONArray();
	BeaconManager beaconManager;
	Region ALL_ESTIMOTE_BEACONS_REGION;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {

			return null;
		}

		// Inflate the layout for this fragment
		sensorsLayout = (LinearLayout) inflater.inflate(
				R.layout.sensors_fragment, container, false);
		sensorsList = (ListView) sensorsLayout.findViewById(R.id.sensors_list);

		new HttpHandler().getBusiness(getActivity(), this);
		validateSensor();
		return sensorsLayout;
	}

	@Override
	public void onSuccess() {
		// TODO Auto-generated method stub

		mHandler.sendMessage(new Message());

	}

	@Override
	public void onFailure(int failureCode) {
		// TODO Auto-generated method stub
		Message message = new Message();
		message.arg1 = failureCode;

		mHandler.sendMessage(message);

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

				setSensorsArray(new DBManager(getActivity())
						.fetchBusinessSensors());

				if (sensorArray != null && sensorArray.length() > 0) {
					TextView no_sensors = (TextView) sensorsLayout
							.findViewById(R.id.no_active_sensors);
					no_sensors.setVisibility(View.GONE);
					mSensorsAdapter = new SensorsAdapter(SensorsFragment.this,
							sensorArray);
					sensorsList.setOnItemClickListener(SensorsFragment.this);
					sensorsList.setAdapter(mSensorsAdapter);
					mSensorsAdapter.notifyDataSetChanged();
				} else {

					TextView no_sensors = (TextView) sensorsLayout
							.findViewById(R.id.no_active_sensors);
					no_sensors.setVisibility(View.VISIBLE);
				}

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

	private JSONArray setSensorsArray(Cursor iCursor) {
		boolean displaySensorType_Estimote = true;
		boolean displaySensorType_AirSensor = true;
		sensorArray = new JSONArray();
		JSONArray airsensorArray = new JSONArray();

		if (iCursor == null) {
			return null;
		}

		try {
			if (!iCursor.isClosed() && iCursor.moveToNext() != false) {

				JSONObject mJsonObject = new JSONObject(iCursor.getString(3));
				JSONArray sensorArray = mJsonObject
						.getJSONArray(HttpConstants.SENSORS_JKEY);
				int size = sensorArray.length();
				for (int k = 0; k < size; k++) {
					JSONObject sensJsonObject = sensorArray.getJSONObject(k);

					sensJsonObject.put(HttpConstants.PROXIMITY_ID_JKEY,
							iCursor.getString(2));
					sensJsonObject.put("major", iCursor.getInt(1));
					sensJsonObject
							.put("minor", sensJsonObject
									.getInt(HttpConstants.SENSOR_ID_JKEY));
					if (sensJsonObject.getString(HttpConstants.STATUS_JKEY)
							.compareToIgnoreCase("Archived") != 0) {
						String sensorType = sensJsonObject
								.optString(HttpConstants.TYPE_JKEY);
						if (sensorType.compareToIgnoreCase("ESTIMOTE") == 0) {
							if (displaySensorType_Estimote) {
								sensJsonObject.put("displaySensorType",
										"ESTIMOTE");
								displaySensorType_Estimote = false;
							}
							this.sensorArray.put(sensJsonObject);
						} else {
							if (displaySensorType_AirSensor) {
								sensJsonObject.put("displaySensorType",
										"AIRSENSOR");
								displaySensorType_AirSensor = false;
							}
							airsensorArray.put(sensJsonObject);
						}

					}

				}

			}

			concatArray(airsensorArray);
			System.out.println("ARRAY " + sensorArray);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return sensorArray;

	}

	/*
	 * private JSONArray concatArray(JSONArray... arrs) throws JSONException {
	 * JSONArray result = new JSONArray(); for (JSONArray arr : arrs) { for (int
	 * i = 0; i < arr.length(); i++) { result.put(arr.get(i)); } } return
	 * result; }
	 */

	private void concatArray(JSONArray arr) throws JSONException {

		for (int i = 0; i < arr.length(); i++) {

			sensorArray.put(sensorArray.length(), arr.get(i));
		}

	}

	public void validateSensor() {
		// final String EXTRAS_BEACON = "extrasBeacon";

	}

	@Override
	public void onStart() {
		super.onStart();
		// Check if device supports Bluetooth Low Energy.
		// if (!beaconManager.hasBluetooth()) {
		// Toast.makeText(getActivity(),
		// "Device does not have Bluetooth Low Energy",
		// Toast.LENGTH_LONG).show();
		// return;
		// }

		// If Bluetooth is not enabled, let user enable it.
		// if (!beaconManager.isBluetoothEnabled()) {
		// Intent enableBtIntent = new Intent(
		// BluetoothAdapter.ACTION_REQUEST_ENABLE);
		// startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		// } else {
		// connectToService();
		// }
	}

	@Override
	public void onStop() {
		super.onStop();
		try {
			if (beaconManager != null)
				beaconManager.stopRanging(ALL_ESTIMOTE_BEACONS_REGION);
		} catch (RemoteException e) {
			Log.d("TAG", "Error while stopping ranging", e);
		}

	}

	@Override
	public void onDestroy() {
		if (beaconManager != null)
			beaconManager.disconnect();
		super.onDestroy();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

		JSONObject mJsonObject = (JSONObject) arg1.getTag();
		try {
			findThisBeacon(
					mJsonObject.getString(HttpConstants.PROXIMITY_ID_JKEY),
					mJsonObject.getInt("major"), mJsonObject.getInt("minor"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void findThisBeacon(String proximityId, int major, int minor) {

		String ESTIMOTE_PROXIMITY_UUID = "B9407F30-F5F8-466E-AFF9-25556B57FE6D";
		ALL_ESTIMOTE_BEACONS_REGION = new Region("rid",
				ESTIMOTE_PROXIMITY_UUID, null, null);

		// Configure BeaconManager.
		beaconManager = new BeaconManager(getActivity());

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
					Log.e("TAG", "Cannot start ranging", e);
				}
			}
		});

		beaconManager.setRangingListener(new BeaconManager.RangingListener() {
			@Override
			public void onBeaconsDiscovered(Region region,
					final List<Beacon> beacons) {

				if (beacons.size() > 0) {
					Beacon mBeacon = beacons.get(0);
					Toast.makeText(
							getActivity(),
							"found Beacons" + beacons.size() + " Proximity  ID"
									+ mBeacon.getProximityUUID() + " "
									+ mBeacon.getName(), 1000).show();
				}
		
			}
		});

	}

}
