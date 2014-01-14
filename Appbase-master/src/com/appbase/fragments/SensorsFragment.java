package com.appbase.fragments;

import java.util.Collections;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.appbase.R;
import com.appbase.adapters.SensorsAdapter;
import com.appbase.datastorage.DBManager;
import com.appbase.httphandler.HTTPResponseListener;
import com.appbase.httphandler.HttpConstants;
import com.appbase.httphandler.HttpHandler;

public class SensorsFragment extends BaseFragment implements
		HTTPResponseListener {

	LinearLayout sensorsLayout;
	ListView sensorsList;
	SensorsAdapter mSensorsAdapter;
	JSONArray sensorArray = new JSONArray();

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
		sensorsLayout = (LinearLayout) inflater.inflate(
				R.layout.sensors_fragment, container, false);
		sensorsList = (ListView) sensorsLayout.findViewById(R.id.sensors_list);

		new HttpHandler().getBusiness(getActivity(), this);
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
				System.out.println("Array Length" + sensorArray.length() + " "
						+ sensorArray);
				mSensorsAdapter = new SensorsAdapter(SensorsFragment.this,
						sensorArray);
				sensorsList.setAdapter(mSensorsAdapter);
				mSensorsAdapter.notifyDataSetChanged();

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
		boolean displaySensorType_Estimote	=	true;
		boolean displaySensorType_AirSensor	=	true;
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
					if (sensJsonObject.getString(HttpConstants.STATUS_JKEY)
							.compareToIgnoreCase("Archived") != 0) {
						String sensorType = sensJsonObject
								.optString(HttpConstants.TYPE_JKEY);
						if (sensorType.compareToIgnoreCase("ESTIMOTE") == 0) {
							if(displaySensorType_Estimote){
								sensJsonObject.put("displaySensorType", "ESTIMOTE");
								displaySensorType_Estimote	=	false;
							}
							this.sensorArray.put(sensJsonObject);
						} else {
							if(displaySensorType_AirSensor){
								sensJsonObject.put("displaySensorType", "AIRSENSOR");
								displaySensorType_AirSensor	=	false;
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
}
