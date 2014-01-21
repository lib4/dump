package com.appbase.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.appbase.R;
import com.appbase.activities.BaseActivity;
import com.appbase.activities.HelpCenterActivity;
import com.appbase.activities.LiveOrderActivity;
import com.appbase.activities.MenuActivity;
import com.appbase.activities.PrivacyPolicyActivity;
import com.appbase.activities.SecurityActivity;
import com.appbase.activities.SensorsActivity;
import com.appbase.activities.TermsOfServiceActivity;
import com.appbase.datastorage.DBManager;
import com.appbase.httphandler.HttpHandler;

public class SettingsFragment extends BaseFragment {

	LinearLayout security, help_center, privacy_policy, terms_of_service,
			liveOrdersLayout, menuManagementLayout, sensor_management,
			logoutLayout;
	ScrollView settingsLayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		settingsLayout = (ScrollView) inflater.inflate(
				R.layout.settings_fragment, container, false);
		init();
		return settingsLayout;
	}

	/**
	 * Method to initialize all the UI element and start any network operation
	 * if required.
	 */
	private void init() {

		/**
		 * Preview Cards
		 */

		liveOrdersLayout = (LinearLayout) settingsLayout
				.findViewById(R.id.live_orders);
		liveOrdersLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				disableHighlight(v);
				Intent intent = new Intent(getActivity(),
						LiveOrderActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);

			}
		});

		menuManagementLayout = (LinearLayout) settingsLayout
				.findViewById(R.id.menu_management);
		menuManagementLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				disableHighlight(v);

				Intent intent = new Intent(getActivity(), MenuActivity.class);
				startActivity(intent);

			}
		});

		sensor_management = (LinearLayout) settingsLayout
				.findViewById(R.id.sensor_management);
		sensor_management.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Toast.makeText(getActivity(),
				// "Functinality yet to be implemented.", 1000).show();
				disableHighlight(v);
				Intent intent = new Intent(getActivity(), SensorsActivity.class);

				startActivity(intent);

			}
		});

		logoutLayout = (LinearLayout) settingsLayout.findViewById(R.id.logout);
		logoutLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				signOutAlert();

			}
		});

		/**
		 * Load Web Url. While Tapping on Security Help Center Privacy Policy
		 * Terms Of Service
		 * 
		 */
		security = (LinearLayout) settingsLayout.findViewById(R.id.security);
		security.setOnClickListener(loadWebViewListener);
		help_center = (LinearLayout) settingsLayout
				.findViewById(R.id.help_center);
		help_center.setOnClickListener(loadWebViewListener);
		privacy_policy = (LinearLayout) settingsLayout
				.findViewById(R.id.privacy_policy);
		privacy_policy.setOnClickListener(loadWebViewListener);
		terms_of_service = (LinearLayout) settingsLayout
				.findViewById(R.id.terms_of_service);
		terms_of_service.setOnClickListener(loadWebViewListener);

	}

	OnClickListener loadWebViewListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			disableHighlight(v);
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.security:
				Intent security_intent = new Intent(getActivity(),
						SecurityActivity.class);
				startActivity(security_intent);
				break;
			case R.id.help_center:
				Intent help_center_intent = new Intent(getActivity(),
						HelpCenterActivity.class);
				startActivity(help_center_intent);
				break;
			case R.id.privacy_policy:
				Intent privacy_policy_intent = new Intent(getActivity(),
						PrivacyPolicyActivity.class);
				startActivity(privacy_policy_intent);
				break;
			case R.id.terms_of_service:
				Intent terms_of_service_intent = new Intent(getActivity(),
						TermsOfServiceActivity.class);
				startActivity(terms_of_service_intent);
				break;

			}

		}
	};

	@Override
	public void onResume() {
		super.onResume();

	}

	@Override
	public void onStart() {
		super.onStart();
		resolveHighlight();
	}

	@Override
	public void onStop() {
		((BaseActivity) getActivity()).mDrawerLayout.closeDrawers();
		super.onStop();
	}

	private void resolveHighlight() {
		disableHighlight(null);
		String callingActyivityName = getActivity().getComponentName()
				.getClassName();
		String packageName = "com.appbase.Activities.";
		
		if (callingActyivityName.equalsIgnoreCase(packageName
				+ "LiveOrderActivity")) {
			liveOrdersLayout.setPressed(true);
		} else if (callingActyivityName.equalsIgnoreCase(packageName
				+ "MenuActivity")) {
			menuManagementLayout.setPressed(true);
		} else if (callingActyivityName.equalsIgnoreCase(packageName
				+ "SensorsActivity")) {
			sensor_management.setPressed(true);

		} else if (callingActyivityName.equalsIgnoreCase(packageName
				+ "SecurityActivity")) {
			security.setPressed(true);
		} else if (callingActyivityName.equalsIgnoreCase(packageName
				+ "PrivacyPolicyActivity")) {
			privacy_policy.setPressed(true);
		} else if (callingActyivityName.equalsIgnoreCase(packageName
				+ "TermsOfServiceActivity")) {
			terms_of_service.setPressed(true);
		} else if (callingActyivityName.equalsIgnoreCase(packageName
				+ "HelpCenterActivity")) {
			help_center.setPressed(true);
		}
	}

	private void disableHighlight(View v) {

		liveOrdersLayout.setPressed(false);
		menuManagementLayout.setPressed(false);
		sensor_management.setPressed(false);
		security.setPressed(false);
		logoutLayout.setPressed(false);
		privacy_policy.setPressed(false);
		help_center.setPressed(false);
		terms_of_service.setPressed(false);
		if(v!=null){
			v.setPressed(true);
			
		}
		
	}

	private void signOutAlert() {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				getActivity());

		// set dialog message
		alertDialogBuilder
				.setMessage(this.getString(R.string.logut_alert))
				.setCancelable(true)
				.setTitle(this.getString(R.string.app_name))
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// if this button is clicked, close
								// current activity

							}
						})

				.setPositiveButton("Logout",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// if this button is clicked, close
								// current activity

								Intent intent = new Intent(getActivity(),
										LiveOrderActivity.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								intent.putExtra("EXIT", true);

								trgrSignOutService();

								DBManager mDbManager = new DBManager(
										getActivity());
								mDbManager.clearDB(getActivity());
								startActivity(intent);
								getActivity().finish();

							}
						});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();

	}

	private void trgrSignOutService() {

		new HttpHandler().signOut(getActivity());
	}

}
