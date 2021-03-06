package com.appbase.fragments;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import com.appbase.utils.Utils;

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
				if (!Utils.IS_TABLET) {
					Intent intent = new Intent(getActivity(),
							LiveOrderActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				} else {
					((BaseActivity) getActivity()).hideSearchActionItem();
					LiveOrderFragment mLiveOrderFragment = new LiveOrderFragment();
					loadFragment(mLiveOrderFragment,
							LiveOrderFragment.class.getName());

				}

			}
		});

		menuManagementLayout = (LinearLayout) settingsLayout
				.findViewById(R.id.menu_management);
		menuManagementLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				disableHighlight(v);
				if (!Utils.IS_TABLET) {
					Intent intent = new Intent(getActivity(),
							MenuActivity.class);
					startActivity(intent);
				} else {
					((BaseActivity) getActivity()).hideSearchActionItem();

					MenuFragment mMenuFragment = new MenuFragment();
					mMenuFragment.FetchFromServerNeeded(true);
					loadFragment(mMenuFragment, MenuFragment.class.getName());

				}

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
				if (!Utils.IS_TABLET) {
					Intent intent = new Intent(getActivity(),
							SensorsActivity.class);

					startActivity(intent);
				} else {
					((BaseActivity) getActivity()).hideSearchActionItem();
					SensorsFragment mSensorsFragment = new SensorsFragment();
					loadFragment(mSensorsFragment,
							SensorsFragment.class.getName());
				}

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
				if (!Utils.IS_TABLET) {
					Intent security_intent = new Intent(getActivity(),
							SecurityActivity.class);
					startActivity(security_intent);
				} else {

					loadWebViewFragment();
				}
				break;
			case R.id.help_center:
				if (!Utils.IS_TABLET) {
					Intent help_center_intent = new Intent(getActivity(),
							HelpCenterActivity.class);
					startActivity(help_center_intent);
				} else {
					loadWebViewFragment();
				}
				break;
			case R.id.privacy_policy:
				if (!Utils.IS_TABLET) {
					Intent privacy_policy_intent = new Intent(getActivity(),
							PrivacyPolicyActivity.class);
					startActivity(privacy_policy_intent);
				} else {
					loadWebViewFragment();
				}
				break;
			case R.id.terms_of_service:
				if (!Utils.IS_TABLET) {
					Intent terms_of_service_intent = new Intent(getActivity(),
							TermsOfServiceActivity.class);
					startActivity(terms_of_service_intent);
				} else {
					loadWebViewFragment();
				}
				break;

			}

		}
	};

	private void loadWebViewFragment() {

		((BaseActivity) getActivity()).hideSearchActionItem();
		WebViewFragment mWebViewFragment = new WebViewFragment();
		mWebViewFragment.loadUrl(true, Utils.ABOUT_URL);
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();

		// fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit,
		// R.anim.pop_enter, R.anim.pop_exit);
		// Replace whatever is in the fragment_container view with this
		// fragment,
		// and add the transaction to the back stack
		fragmentTransaction.add(R.id.fragment_holder, mWebViewFragment);
		fragmentTransaction.addToBackStack(null);
		// Commit the transaction
		fragmentTransaction.commit();

	}

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
		((BaseActivity) getActivity()).closeDrawayer();
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
			liveOrdersLayout.setBackgroundColor(getResources().getColor(
					R.color.grey_selector));
		} else if (callingActyivityName.equalsIgnoreCase(packageName
				+ "MenuActivity")) {
			menuManagementLayout.setPressed(true);
			menuManagementLayout.setBackgroundColor(getResources().getColor(
					R.color.grey_selector));
		} else if (callingActyivityName.equalsIgnoreCase(packageName
				+ "SensorsActivity")) {
			sensor_management.setPressed(true);
			sensor_management.setBackgroundColor(getResources().getColor(
					R.color.grey_selector));

		} else if (callingActyivityName.equalsIgnoreCase(packageName
				+ "SecurityActivity")) {
			security.setPressed(true);
			security.setBackgroundColor(getResources().getColor(
					R.color.grey_selector));
		} else if (callingActyivityName.equalsIgnoreCase(packageName
				+ "PrivacyPolicyActivity")) {
			privacy_policy.setPressed(true);
			privacy_policy.setBackgroundColor(getResources().getColor(
					R.color.grey_selector));
		} else if (callingActyivityName.equalsIgnoreCase(packageName
				+ "TermsOfServiceActivity")) {
			terms_of_service.setPressed(true);
			terms_of_service.setBackgroundColor(getResources().getColor(
					R.color.grey_selector));
		} else if (callingActyivityName.equalsIgnoreCase(packageName
				+ "HelpCenterActivity")) {
			help_center.setPressed(true);
			help_center.setBackgroundColor(getResources().getColor(
					R.color.grey_selector));
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

		liveOrdersLayout.setBackgroundColor(getResources().getColor(
				R.color.white_tile_background_color));
		menuManagementLayout.setBackgroundColor(getResources().getColor(
				R.color.white_tile_background_color));
		sensor_management.setBackgroundColor(getResources().getColor(
				R.color.white_tile_background_color));
		security.setBackgroundColor(getResources().getColor(
				R.color.white_tile_background_color));
		logoutLayout.setBackgroundColor(getResources().getColor(
				R.color.white_tile_background_color));
		privacy_policy.setBackgroundColor(getResources().getColor(
				R.color.white_tile_background_color));
		help_center.setBackgroundColor(getResources().getColor(
				R.color.white_tile_background_color));
		terms_of_service.setBackgroundColor(getResources().getColor(
				R.color.white_tile_background_color));
		if (v != null) {
			v.setPressed(true);
			v.setBackgroundColor(getResources().getColor(R.color.grey_selector));

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

	/**
	 * Load the LiveOrder fragment
	 * 
	 */

	private void loadFragment(BaseFragment mBaseFragment, String fragmentTag) {

		FragmentManager fragmentManager = getFragmentManager();

		BaseFragment mFragment = (BaseFragment) fragmentManager
				.findFragmentByTag(fragmentTag);

		if (mFragment != null) {
			mBaseFragment = mFragment;
			if (fragmentTag.equals(MenuFragment.class.getName())) {
				((BaseActivity) getActivity()).showSearchActionItem();
			}
		}
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();

		// fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit,
		// R.anim.pop_enter, R.anim.pop_exit);
		// Replace whatever is in the fragment_container view with this
		// fragment,
		// and add the transaction to the back stack
		fragmentTransaction.replace(R.id.fragment_holder, mBaseFragment,
				fragmentTag);
		fragmentTransaction.addToBackStack(null);
		// Commit the transaction
		fragmentTransaction.commit();

	}

}
