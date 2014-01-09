package com.appbase.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.appbase.R;
import com.appbase.activities.LiveOrderActivity;
import com.appbase.httphandler.HTTPResponseListener;
import com.appbase.httphandler.HttpHandler;

public class SignInFragment extends BaseFragment implements
		HTTPResponseListener {

	EditText emailAddress_edtTxt;// Email Address input field
	EditText password_edtTxt;// Password input field
	static ProgressDialog mDialog;
	Button signIn_Btn;
	View signInLayout;
	String emailAddress_str, password_str;

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
		signInLayout = inflater.inflate(R.layout.signin_fragment, container,
				false);
		init();
		if (savedInstanceState != null) {
			System.out.println("Save instance oncreate not null");
			emailAddress_str = savedInstanceState
					.getString("emailAddress_edtTxt");
			password_str = savedInstanceState.getString("password_edtTxt");

		}
		System.out.println("Save instance oncreate outside");

		System.out.println("emailAddrss "
				+ emailAddress_edtTxt.getText().toString());

		if (emailAddress_str != null)
			emailAddress_edtTxt.setText(emailAddress_str);
		if (password_str != null)
			password_edtTxt.setText(password_str);
		return signInLayout;
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

		emailAddress_edtTxt = (EditText) signInLayout
				.findViewById(R.id.email_edtTxt);
		password_edtTxt = (EditText) signInLayout
				.findViewById(R.id.password_edtTxt);

		// Hardcodes the emaild and password Need to remove
		emailAddress_edtTxt.setText("merch27@teamonapp.com");
		password_edtTxt.setText("123123");

		password_edtTxt.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					trgrSignInWebService();
				}
				return false;
			}
		});

		signIn_Btn = (Button) signInLayout.findViewById(R.id.signin_btn);
		signIn_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				trgrSignInWebService();
			}
		});

	}

	@Override
	public void onSuccess() {
		if (mDialog != null && mDialog.isShowing())
			mDialog.dismiss();

		loadLiveOrderFragment();
		getActivity().finish();
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

	/**
	 * TRIGGER SIGN IN WEB SERVICE CALL
	 */

	private void trgrSignInWebService() {
		hideKeyboard();
		mDialog = new ProgressDialog(getActivity());
		mDialog.setMessage(getActivity().getString(R.string.signing));
		mDialog.setCancelable(false);
		mDialog.show();
		new HttpHandler().doSignIn(emailAddress_edtTxt.getText().toString(),
				password_edtTxt.getText().toString(), getActivity(), this);
	}

	/**
	 * Load the SignIn fragment
	 * 
	 */

	private void loadLiveOrderFragment() {

		Intent intent = new Intent(getActivity(), LiveOrderActivity.class);
		startActivity(intent);

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("emailAddress_edtTxt", emailAddress_edtTxt.getText()
				.toString());
		outState.putString("password_edtTxt", password_edtTxt.getText()
				.toString());
		System.out.println("Save instance");

	}

	private void showAlertDialog() {

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
				});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();

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

	/**
	 * Method to hide the keyboard
	 */
	
	private void hideKeyboard(){
		InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(
			      Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(password_edtTxt.getWindowToken(), 0);
	}
	final Handler mHandler = new Handler(Looper.getMainLooper()) {

		public void handleMessage(Message msg) {
			if (msg.arg1 == HttpHandler.NO_NETWORK_CODE) {

				showNoNetworkAlertDialog();
			} else {
				showAlertDialog();

			}
		}
	};

}
