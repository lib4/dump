package com.appbase.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.appbase.R;
import com.appbase.httphandler.HTTPResponseListener;
import com.appbase.httphandler.HttpHandler;

public class SignInFragment  extends BaseFragment implements HTTPResponseListener{

	EditText emailAddress_edtTxt;
	EditText password_edtTxt;
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
		return inflater.inflate(R.layout.signin_fragment, container, false);
	}

	@Override
	public void onResume() {
		super.onResume();
		init();
	}

	/**
	 * Method to initialize all the UI element and start any network operation
	 * if required.
	 */
	private void init() {

		View signInLayout		=	getView();
		emailAddress_edtTxt		=	(EditText) signInLayout.findViewById(R.id.email_edtTxt);
		password_edtTxt			=	(EditText) signInLayout.findViewById(R.id.password_edtTxt);
		
		//Hardcodes the emaild and password Need to remove
		emailAddress_edtTxt.setText("merch25@teamonapp.com");
		password_edtTxt.setText("123123");
	
		new HttpHandler().doSignIn(emailAddress_edtTxt.getText().toString(), 
				password_edtTxt.getText().toString(),getActivity(),this);
	}

	@Override
	public void onSuccess() {
		// TODO Auto-generated method stub
		loadOrderListFragment();
	}

	@Override
	public void onFailure() {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Load the SignIn fragment
	 * 
	 */

	private void loadOrderListFragment() {

		OrderListFragment mOrderListFragment = new OrderListFragment();
		FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		
		fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit,
				R.anim.pop_enter, R.anim.pop_exit);
		// Replace whatever is in the fragment_container view with this
		// fragment,
		// and add the transaction to the back stack
		fragmentTransaction.replace(R.id.fragment_holder, mOrderListFragment);
		// Commit the transaction
		fragmentTransaction.commit();

	}
	
}
