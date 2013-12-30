package com.appbase.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appbase.R;
import com.appbase.httphandler.HTTPResponseListener;
import com.appbase.httphandler.HttpHandler;

public class OrderListFragment extends BaseFragment implements HTTPResponseListener{

	@Override 
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		if (container == null) {
			// We have different layouts, and in one of them this 
			// fragment's containing frame doesn't exist.  The fragment
			// may still be created from its saved state, but there is
			// no reason to try to create its view hierarchy because it
			// won't be displayed.  Note this is not needed -- we could
			// just run the code below, where we would create and return
			// the view hierarchy; it would just never be used.
			return null;        
		}
		
		   // Inflate the layout for this fragment
		return inflater.inflate(R.layout.orderlist_fragment, container, false);
	}
	
	
	@Override
	public void onResume(){
		super.onResume();
		init();
	}
	
	
	/**
	 * Method to initialise all the UI element and start
	 *  any network operation if required.
	 */
	private void init(){
		
		new HttpHandler().getLiveOrders(getActivity(), this);
		
	}


	@Override
	public void onSuccess() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onFailure() {
		// TODO Auto-generated method stub
		
	}
}
