package com.appbase.fragments;

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

public class DealsDetailsFragment extends BaseFragment {

	LinearLayout dealsDetailsLayout;
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
		dealsDetailsLayout = (LinearLayout) inflater.inflate(
				R.layout.deal_details_fragment, container, false);
		init();
		return dealsDetailsLayout;
	}

	/**
	 * Method to initialize all the UI element and start any network operation
	 * if required.
	 */
	private void init() {

		back_Btn = (Button) dealsDetailsLayout
				.findViewById(R.id.back_btn);

		back_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getActivity().finish();
			}
		});

	

	}
}
