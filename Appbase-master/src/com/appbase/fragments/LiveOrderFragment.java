package com.appbase.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.appbase.R;
import com.appbase.activities.LiveOrderActivity;
import com.appbase.activities.SettingsActivity;
import com.appbase.customui.PinterestUI;
import com.appbase.datastorage.DBManager;
import com.appbase.httphandler.HTTPResponseListener;
import com.appbase.httphandler.HttpHandler;

public class LiveOrderFragment extends BaseFragment implements
		HTTPResponseListener {

	public LinearLayout view;
	PinterestUI mPinterestUI;
	Button setting_Btn;
	static ProgressDialog mDialog;
	String CHEK = "Default";
	boolean isFetchFromServer = false;
	

	public void FetchFromServerNeeded(boolean isFetchFromServer) {
		// TODO Auto-generated constructor stub
		this.isFetchFromServer	=	isFetchFromServer;
	}
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
		mDialog	=	null;

		// Inflate the layout for this fragment
		view = (LinearLayout) inflater.inflate(R.layout.orderlist_fragment,
				container, false);
		init();
		if(isFetchFromServer){
			trgrLiveOrderWebService();
		}else{
			onSuccess();
		}
		
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();

	}

	/**
	 * Method to initialize all the UI element and start any network operation
	 * if required.
	 */
	private void init() {

		mPinterestUI = new PinterestUI(getActivity());
		ScrollView mScrollView = new ScrollView(getActivity());
		mScrollView.setOverScrollMode(ScrollView.OVER_SCROLL_ALWAYS);

		mScrollView.addView(mPinterestUI);

		view.addView(mScrollView);
		setting_Btn = (Button) view.findViewById(R.id.settings_btn);
		setting_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				loadSettingsFragment();
			}
		});
	}

	@Override
	public void onStart() {
		super.onStart();

	}

	@Override
	public void onSuccess() {
		if (mDialog != null && mDialog.isShowing())
		mDialog.dismiss();
		mHandler.sendMessage(new Message());

	}

	@Override
	public void onFailure(int failureCode) {
		// TODO Auto-generated method stub

		if (mDialog != null && mDialog.isShowing())
			mDialog.dismiss();

		// Showing alert dialog

		Message message = new Message();
		message.arg1 = failureCode;
	
		mHandler.sendMessage(message);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putBoolean("isSaved", true);
		super.onSaveInstanceState(outState);
	}

	/**
	 * TRIGGER LIVE ORDER WEB SERVICE CALL
	 */

	private void trgrLiveOrderWebService() {

		new DBManager(getActivity()).clearLiveOrders();
		mDialog = new ProgressDialog(getActivity());
		mDialog.setMessage(getActivity().getString(R.string.loading));
		mDialog.setCancelable(false);
		mDialog.show();
		new HttpHandler().getLiveOrders(getActivity(), this);
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

	final Handler mHandler = new Handler(Looper.getMainLooper()) {

		public void handleMessage(Message msg) {

			if (msg.arg1 == HttpHandler.NO_NETWORK_CODE) {

				showNoNetworkAlertDialog();
			} else if (msg.arg1 == HttpHandler.DEFAULT_CODE) {// Empty Message
																// layout.

			}

			else {
				Cursor liveOrders = new DBManager(getActivity())
						.fetchLiveOrders();
				if (liveOrders != null)
					mPinterestUI.createLayout(liveOrders);
			}
		}
	};
	/**
	 * Load the Settings fragment
	 * 
	 */

	private void loadSettingsFragment() {

		Intent intent = new Intent(getActivity(), SettingsActivity.class);
		startActivity(intent);

	}
	
}
