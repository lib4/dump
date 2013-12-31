package com.appbase.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.appbase.R;
import com.appbase.customui.PinterestUI;
import com.appbase.datastorage.DBManager;
import com.appbase.httphandler.HTTPResponseListener;
import com.appbase.httphandler.HttpHandler;

public class OrderListFragment extends BaseFragment implements HTTPResponseListener{

	LinearLayout view;
	PinterestUI mPinterestUI;
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
		view	=	 (LinearLayout) inflater.inflate(R.layout.orderlist_fragment, container, false);
		return view;
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
		mPinterestUI	=	new PinterestUI(getActivity());
		ScrollView mScrollView	=new ScrollView(getActivity());
		mScrollView.addView(mPinterestUI);
		view.addView(mScrollView);
	
		mPinterestUI.createLayout(new DBManager(getActivity()).fetchLiveOrders());
		
	}
	@Override
	public void onStart(){
		super.onStart();
	
		
	}

	@Override
	public void onSuccess() {
		// TODO Auto-generated method stub
		//Cursor mCursor 	=	new DBManager(getActivity()).fetchLiveOrders();
		//mPinterestUI.createLayout(mCursor);
		
	}


	@Override
	public void onFailure() {
		// TODO Auto-generated method stub
		
	}
}
