package com.appbase.fragments;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appbase.R;
import com.appbase.httphandler.HTTPResponseListener;
import com.appbase.httphandler.HttpConstants;
import com.appbase.httphandler.HttpHandler;
import com.appbase.utils.Utils;

public class SensorDetailsFragment extends BaseFragment implements HTTPResponseListener{

	
	LinearLayout sensorDetailsLayout;
	
	static ProgressDialog mDialog;
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
		mDialog = new ProgressDialog(getActivity());
		mDialog.setMessage(getActivity().getString(R.string.wait_archiving));
		mDialog.setCancelable(false);
		getActivity().getActionBar().setTitle(Utils.SENSOR_DETAILS_TEXT);
		sensorDetailsLayout	=	 (LinearLayout) inflater.inflate(R.layout.sensor_details_fragment, container,
				false);	
		init();
		return sensorDetailsLayout;
	}
	

	
	private void init(){
		
		try{
		TextView SensorName	=	(TextView) sensorDetailsLayout.findViewById(R.id.sensorNameText);
		SensorName.setText(Utils.SELECTED_OBJECT.getString(HttpConstants.NAME_JKEY));
		
		TextView 	AdvIntervalText =	(TextView) sensorDetailsLayout.findViewById(R.id.AdvIntervalText);
		TextView advIntervalValue	=	(TextView) sensorDetailsLayout.findViewById(R.id.advIntervalValue);
		advIntervalValue.setText(Utils.SELECTED_OBJECT.getString(HttpConstants.ADVERTISING_INTERVAL_JKEY));
		
		
		TextView powerText	=	(TextView) sensorDetailsLayout.findViewById(R.id.powerText);
		TextView powerValue	=	(TextView) sensorDetailsLayout.findViewById(R.id.powerValue);
		powerValue.setText(Utils.SELECTED_OBJECT.getString(HttpConstants.TX_POWER_JKEY)+"dBm");
		Button archiveButon 	=	(Button) sensorDetailsLayout.findViewById(R.id.archive_btn);
		archiveButon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mDialog!=null)
					mDialog.show();
				
				trgrArchiveSensor();
		
			}
		});
		
		}catch(Exception e){
			
		}
		
	}
	private void dismissProgressDialog() {
		if (mDialog != null && mDialog.isShowing())
			mDialog.dismiss();
	}
	public void trgrArchiveSensor() {
		mDialog.setMessage(getActivity().getText(R.string.wait_archiving));
		mDialog.show();
		
		String sensorId = null;
		try {
			
			sensorId = Utils.SELECTED_OBJECT.getString(HttpConstants._ID_JKEY);
			Utils.ARCHIVE_INDEX	=	Utils.SELECTED_OBJECT.getInt("index");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		new HttpHandler().archiveSensor(sensorId, getActivity(), this);

	}



	@Override
	public void onSuccess() {
		dismissProgressDialog();
		getActivity().finish();
		
	}



	@Override
	public void onFailure(int failureCode) {
		dismissProgressDialog();
		getActivity().finish();
	}
	
}
