package com.appbase.fragments;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
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
import com.appbase.activities.SensorDetailsActivity;
import com.appbase.adapters.SensorsAdapter;
import com.appbase.datastorage.DBManager;
import com.appbase.httphandler.HTTPResponseListener;
import com.appbase.httphandler.HttpConstants;
import com.appbase.httphandler.HttpHandler;
import com.appbase.utils.Utils;
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
	static ProgressDialog mDialog;
	public String ProximityId;
	public int major, minor, itemindex;
	private SensorsFragment mSensorsFragment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {

			return null;
		}
		getActivity().getActionBar().setTitle(Utils.SENSORS_TEXT);
		mDialog = null;
		// Inflate the layout for this fragment
		sensorsLayout = (LinearLayout) inflater.inflate(
				R.layout.sensors_fragment, container, false);
		sensorsList = (ListView) sensorsLayout.findViewById(R.id.sensors_list);
		mDialog = new ProgressDialog(getActivity());
		mDialog.setMessage(getActivity().getString(R.string.loading));
		mDialog.setCancelable(false);
		trgrGetBusiness();
		mSensorsFragment	=	this;
		return sensorsLayout;
	}

	private void trgrGetBusiness() {

		mDialog.show();

		new DBManager(getActivity()).clearBusiness();
		new HttpHandler().getBusiness(getActivity(), this);

	}

	@Override
	public void onSuccess() {
		dismissProgressDialog();
		mHandler.sendMessage(new Message());

	}

	@Override
	public void onFailure(int failureCode) {

		dismissProgressDialog();

		Message message = new Message();
		message.arg1 = failureCode;
		mHandler.sendMessage(message);

	}

	final Handler mHandler = new Handler(Looper.getMainLooper()) {

		public void handleMessage(Message msg) {
			Log.e("Sensor", "Sensr not nulll" + msg.arg1);
			if (msg.arg1 == HttpHandler.NO_NETWORK_CODE) {

				showNoNetworkAlertDialog();
			} else if (msg.arg1 == HttpHandler.DEFAULT_CODE) {// Empty Message

				TextView no_sensors = (TextView) sensorsLayout
						.findViewById(R.id.no_active_sensors);
				no_sensors.setVisibility(View.VISIBLE);
			} else if (msg.arg1 == Utils.DELETE_SENSOR) {

				try {
					if (sensorArray != null) {

						Log.e("Sensor", "Sensr not nulll");
						removeJson(itemindex);
						mSensorsAdapter		=	new SensorsAdapter(mSensorsFragment, sensorArray);
						sensorsList.setAdapter(mSensorsAdapter);
						mSensorsAdapter.notifyDataSetChanged();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
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
					sensJsonObject.put(HttpConstants.MAJOR_JKEY,
							iCursor.getInt(1));
					sensJsonObject
							.put(HttpConstants.MINOR_JKEY, sensJsonObject
									.getInt(HttpConstants.SENSOR_ID_JKEY));

					if (sensJsonObject.getString(HttpConstants.STATUS_JKEY)
							.compareToIgnoreCase("Archived") != 0) {
						String sensorType = sensJsonObject
								.optString(HttpConstants.TYPE_JKEY);
						if (sensorType
								.compareToIgnoreCase(HttpConstants.ESTIMOTE) == 0) {
							if (displaySensorType_Estimote) {
								sensJsonObject.put(
										HttpConstants.DISPLAY_SENSOR_TYPE_JKEY,
										HttpConstants.ESTIMOTE);
								displaySensorType_Estimote = false;
							}
							this.sensorArray.put(sensJsonObject);
						} else {
							if (displaySensorType_AirSensor) {
								sensJsonObject.put(
										HttpConstants.DISPLAY_SENSOR_TYPE_JKEY,
										HttpConstants.AIRSENSOR);
								displaySensorType_AirSensor = false;
							}
							airsensorArray.put(sensJsonObject);
						}

					}

				}

			}

			concatArray(airsensorArray);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return sensorArray;

	}

	private void concatArray(JSONArray arr) throws JSONException {

		for (int i = 0; i < arr.length(); i++) {

			sensorArray.put(sensorArray.length(), arr.get(i));
		}

	}

	@Override
	public void onStart() {
		super.onStart();
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

		try {
			JSONObject mJsonObject = (JSONObject) sensorArray.get(arg2);

			System.out.println("JSON " + mJsonObject.toString());
			String sensorType = mJsonObject.optString(HttpConstants.TYPE_JKEY);
			String validated = mJsonObject
					.optString(HttpConstants.VALIDATE_STATUS);
			if ((sensorType.compareToIgnoreCase(HttpConstants.ESTIMOTE) == 0
					&& (!validated.isEmpty() && validated
							.compareToIgnoreCase(HttpConstants.FAILURE) == 0) || (sensorType
					.compareToIgnoreCase(HttpConstants.ESTIMOTE) == 0 && validated
					.isEmpty()))) {
				showValidateAlert(
						mJsonObject.getString(HttpConstants.PROXIMITY_ID_JKEY),
						mJsonObject.getInt(HttpConstants.MAJOR_JKEY),
						mJsonObject.getInt(HttpConstants.MINOR_JKEY), arg2);
			} else if (sensorType.compareToIgnoreCase(HttpConstants.ESTIMOTE) == 0
					&& (!validated.isEmpty() && validated
							.compareToIgnoreCase(HttpConstants.SUCCESS) == 0)) {
				loadSensorDetailsFragment();
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private int SCANN_CYCLE	=	0;
	public void findThisBeacon(String proximityId, int major, int minor,
			final int itemindex) {

		this.ProximityId = proximityId;
		this.major = major;
		this.minor = minor;
		this.itemindex = itemindex;

		String ESTIMOTE_PROXIMITY_UUID = "B9407F30-F5F8-466E-AFF9-25556B57FE6D";
		ALL_ESTIMOTE_BEACONS_REGION = new Region("rid",
				ESTIMOTE_PROXIMITY_UUID, major, minor);
		
		Log.e("major minor ",""+major + " "+minor);

		// Configure BeaconManager.
		beaconManager = new BeaconManager(getActivity());
		beaconManager.setBackgroundScanPeriod(3000, 1000);
		beaconManager.setForegroundScanPeriod(3000, 1000);

		beaconManager.setRangingListener(new BeaconManager.RangingListener() {
			@Override
			public void onBeaconsDiscovered(Region region,
					final List<Beacon> beacons) {
				
				if(SCANN_CYCLE<4){
					SCANN_CYCLE++;
					return;
				}
				SCANN_CYCLE	=	0;
				if (beacons.size() > 0) {
					try {
						dismissProgressDialog();
						beaconManager.stopRanging(ALL_ESTIMOTE_BEACONS_REGION);
						beaconManager.disconnect();
						updateValidationStatus(itemindex, HttpConstants.SUCCESS);
					} catch (RemoteException e) {
						e.printStackTrace();
					}

				} else {
					
					
					try {
						dismissProgressDialog();
						beaconManager.stopRanging(ALL_ESTIMOTE_BEACONS_REGION);
						beaconManager.disconnect();
						updateValidationStatus(itemindex, HttpConstants.FAILURE);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
					noSensorAlert(itemindex);
				}

			}

		});

		beaconManager.setErrorListener(new BeaconManager.ErrorListener() {

			@Override
			public void onError(Integer arg0) {

				try {

					dismissProgressDialog();
					beaconManager.stopRanging(ALL_ESTIMOTE_BEACONS_REGION);
					beaconManager.disconnect();
					updateValidationStatus(itemindex, HttpConstants.FAILURE);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				noSensorAlert(itemindex);

				Log.e("error", "error");
			}
		});

		// Check if device supports Bluetooth Low Energy.
		if (!beaconManager.hasBluetooth()) {
			dismissProgressDialog();
			Toast.makeText(getActivity(),
					"Device does not have Bluetooth Low Energy",
					Toast.LENGTH_LONG).show();
			return;
		}

		// If Bluetooth is not enabled, let user enable it.
		if (!beaconManager.isBluetoothEnabled()) {
			dismissProgressDialog();
			Intent enableBtIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent, Utils.REQUEST_ENABLE_BT);
		} else {
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

					}
				}
			});
		}

	}

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

	private void showValidateAlert(final String proximityId, final int major,
			final int minor, final int itemindex) {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				getActivity());
		alertDialogBuilder
				.setMessage(
						getActivity().getString(R.string.alert_validate_sensor))
				.setCancelable(false)
				.setNegativeButton(
						getActivity().getString(R.string.cancel_btn),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// if this button is clicked, close
								// current activity

							}
						})

				.setPositiveButton(
						getActivity().getString(R.string.validate_btn),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// if this button is clicked, close
								// current activity
								mDialog.setMessage(getActivity().getText(
										R.string.please_wait_validating));
								mDialog.show();
								findThisBeacon(proximityId, major, minor,
										itemindex);
							}
						});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}

	private void noSensorAlert(final int itemIndex) {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				getActivity());
		alertDialogBuilder
				.setMessage(
						getActivity().getString(R.string.could_not_find_sensor))
				.setCancelable(false)
				.setNegativeButton(
						getActivity().getString(R.string.cancel_btn),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// if this button is clicked, close
								// current activity

							}
						})

				.setPositiveButton(getActivity().getString(R.string.archive),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// if this button is clicked, close
								// current activity
								trgrArchiveSensor(itemIndex);

							}
						});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}

	private void dismissProgressDialog() {
		if (mDialog != null && mDialog.isShowing())
			mDialog.dismiss();
	}

	private void updateValidationStatus(int itemIndex, String status) {

		try {

			String pattern = "MMM dd, hh:mm aa";
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			JSONObject mJsonObject = (JSONObject) sensorArray.get(itemIndex);
			mJsonObject.put(HttpConstants.VALIDATE_STATUS, status);
			mJsonObject.put(HttpConstants.VALIDATE_TIME,
					"" + format.format(Calendar.getInstance().getTime()));
			mSensorsAdapter.notifyDataSetChanged();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void trgrArchiveSensor(int itemIndex) {

		Log.e("Tapped on Archive ", "tapped on archive");

		mDialog.setTitle(getActivity().getText(R.string.wait_archiving));
		mDialog.show();
		String sensorId = null;
		try {
			JSONObject mJsonObject = sensorArray.getJSONObject(itemIndex);
			sensorId = mJsonObject.getString(HttpConstants._ID_JKEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		new HttpHandler().archiveSensor(sensorId, getActivity(), this);

	}

	/**
	 * Load the SensorDetailsFragment
	 * 
	 */

	private void loadSensorDetailsFragment() {
		try{
		Utils.SELECTED_OBJECT	=	sensorArray.getJSONObject(itemindex);
		Intent intent = new Intent(getActivity(), SensorDetailsActivity.class);
		startActivity(intent);
		}catch(Exception e){
			
		}

	}

	private void removeJson(int itemIndex) {


		try {
			JSONArray list = new JSONArray();

			int len = sensorArray.length();
			if (sensorArray != null) {
				for (int i = 0; i < len; i++) {
					// Excluding the item at position
					if (i != itemIndex) {
						list.put(sensorArray.get(i));
						
					
						
					}
				}
			}
			sensorArray = list;
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
