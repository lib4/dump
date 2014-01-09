package com.appbase.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.appbase.R;
import com.appbase.activities.LauncherActivity;
import com.appbase.activities.LiveOrderActivity;
import com.appbase.activities.MenuActivity;
import com.appbase.datastorage.DBManager;
import com.appbase.httphandler.HTTPResponseListener;
import com.appbase.httphandler.HttpHandler;
import com.appbase.utils.Utils;

public class SettingsFragment extends BaseFragment implements HTTPResponseListener{

	LinearLayout settingsLayout,menuManagementLayout;
	Button back_Btn, logout_Btn;

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
		settingsLayout = (LinearLayout) inflater.inflate(
				R.layout.settings_fragment, container, false);
		init();
		return settingsLayout;
	}

	/**
	 * Method to initialize all the UI element and start any network operation
	 * if required.
	 */
	private void init() {

		back_Btn = (Button) settingsLayout.findViewById(R.id.left_arrow_btn);
		logout_Btn = (Button) settingsLayout.findViewById(R.id.logout_btn);

		back_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getActivity().finish();
			}
		});

		logout_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				signOutAlert();
			}
		});

		
		menuManagementLayout	=	(LinearLayout) settingsLayout.findViewById(R.id.menu_management);
		menuManagementLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),
						MenuActivity.class);
				
				startActivity(intent);
				
			}
		});
	}
	
	private void trgrSignOutService(){
		
		new HttpHandler().signOut(getActivity());
	}
	
	private void signOutAlert(){
		
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					getActivity());

			// set title
			// alertDialogBuilder.setTitle("Your Title");

			// set dialog message
			alertDialogBuilder
					.setMessage(
							getActivity().getString(R.string.logut_alert))
					.setCancelable(false)
					.setPositiveButton("No", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							// if this button is clicked, close
							// current activity

						}
					})

					.setNegativeButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									// if this button is clicked, close
									// current activity
									
									Intent intent = new Intent(getActivity(),
											LiveOrderActivity.class);
									intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
									intent.putExtra("EXIT", true);

									trgrSignOutService();
									
									DBManager mDbManager	=	new DBManager(getActivity());
									mDbManager.clearDB();
									startActivity(intent);
									getActivity().finish();
									
								}
							});

			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();
		
	}

	@Override
	public void onSuccess() {
		// TODO Auto-generated method stub
		Utils.TOKEN	=	null;
	}

	@Override
	public void onFailure(int failureCode) {
		// TODO Auto-generated method stub
		Utils.TOKEN	=	null;
		
	}
}
