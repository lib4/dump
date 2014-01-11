package com.appbase.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.appbase.R;
import com.appbase.activities.MenuActivity;
import com.appbase.activities.WebViewActivity;

public class SettingsFragment extends BaseFragment {

	LinearLayout settingsLayout;
	

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

	/**
	 * Preview Cards
	 */
		
		LinearLayout menuManagementLayout	=	(LinearLayout) settingsLayout.findViewById(R.id.menu_management);
		menuManagementLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),
						MenuActivity.class);
				
				startActivity(intent);
				
			}
		});
		
		
		LinearLayout sensor_management	=	(LinearLayout) settingsLayout.findViewById(R.id.sensor_management);
		
		sensor_management.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "Functinality yet to be implemented.", 1000).show();
				
			}
		});
		
		/**
		 * Load Web Url. While Tapping on
		 * Security
		 * Help Center
		 * Privacy Policy
		 * Terms Of Service
		 * 
		 */
		LinearLayout security	=	(LinearLayout) settingsLayout.findViewById(R.id.security);
		security.setOnClickListener(loadWebViewListener);
		LinearLayout help_center	=	(LinearLayout) settingsLayout.findViewById(R.id.help_center);
		help_center.setOnClickListener(loadWebViewListener);
		LinearLayout privacy_policy	=	(LinearLayout) settingsLayout.findViewById(R.id.privacy_policy);
		privacy_policy.setOnClickListener(loadWebViewListener);
		LinearLayout terms_of_service	=	(LinearLayout) settingsLayout.findViewById(R.id.terms_of_service);
		terms_of_service.setOnClickListener(loadWebViewListener);
		
		
	}
	
	OnClickListener loadWebViewListener	=	new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(getActivity(),
					WebViewActivity.class);
			
			startActivity(intent);
		}
	};
	


}
