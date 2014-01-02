package com.appbase.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.appbase.R;
import com.appbase.activities.DealDetailsActivity;
import com.appbase.activities.LiveOrderActivity;
import com.appbase.adapters.MenuAdapter;
import com.appbase.datastorage.DBManager;
import com.appbase.httphandler.HTTPResponseListener;
import com.appbase.httphandler.HttpHandler;

public class MenuFragment extends BaseFragment implements HTTPResponseListener{

	LinearLayout menuLayout;
	Button back_Btn;
	ListView mListView;

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
		menuLayout = (LinearLayout) inflater.inflate(
				R.layout.menu_fragment, container, false);
		init();
		new HttpHandler().getMenus(getActivity(), this);
		return menuLayout;
	}

	/**
	 * Method to initialize all the UI element and start any network operation
	 * if required.
	 */
	private void init() {

		back_Btn = (Button) menuLayout.findViewById(R.id.back_btn);

		back_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getActivity().finish();
			}
		});
		
		mListView	=	(ListView) menuLayout.findViewById(R.id.menu_list);
		MenuAdapter menuAdapter	=	new MenuAdapter(getActivity(), new DBManager(getActivity()).fetchLiveOrders());
		mListView.setAdapter(menuAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				loadDealDetailsFragment();
			}
		});

	}
	
	
	/**
	 * Load the SignIn fragment
	 * 
	 */

	public void loadDealDetailsFragment() {

		Intent intent = new Intent(getActivity(), DealDetailsActivity.class);
		startActivity(intent);

	}

	@Override
	public void onSuccess() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFailure(int failureCode) {
		// TODO Auto-generated method stub
		
	}

}
