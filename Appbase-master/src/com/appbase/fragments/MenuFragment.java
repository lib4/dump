package com.appbase.fragments;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import com.appbase.R;
import com.appbase.activities.DealDetailsActivity;
import com.appbase.adapters.MenuAdapter;
import com.appbase.datastorage.DBManager;
import com.appbase.httphandler.HTTPResponseListener;
import com.appbase.httphandler.HttpHandler;

public class MenuFragment extends BaseFragment implements HTTPResponseListener,OnClickListener {

	LinearLayout menuLayout;
	Button back_Btn;
	ListView mListView;
	ProgressDialog mDialog;
	MenuFragment menuFragment;

	boolean isFetchFromServer = false;
	public static JSONObject cardObject;

	public void FetchFromServerNeeded(boolean isFetchFromServer) {
		// TODO Auto-generated constructor stub
		this.isFetchFromServer = isFetchFromServer;
	}

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
		menuLayout = (LinearLayout) inflater.inflate(R.layout.menu_fragment,
				container, false);
		init();
		if (isFetchFromServer) {
			trgrGetMenusService();
		} else {
			onSuccess();
		}
		menuFragment	=	this;
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

		mListView = (ListView) menuLayout.findViewById(R.id.menu_list);

//		mListView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				// TODO Auto-generated method stub
//				loadDealDetailsFragment();
//			}
//		});

	}

	/**
	 * Load the Deals Details fragment
	 * 
	 */

	public void loadDealDetailsFragment() {
		
		
		

		
		View mView	=	getActivity().getWindow().getDecorView().findViewById(android.R.id.content);

	
		if (mView.findViewById(R.id.slide_list)!= null) {
			System.out.println("NOT NULLE "+getActivity().getClass().getCanonicalName());
			
			DealsDetailsFragment mDealsDetailsFragment = new DealsDetailsFragment();
			mDealsDetailsFragment.hideHeaderBar(true);
			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager
					.beginTransaction();

			// fragmentTransaction.setCustomAnimations(R.anim.enter,
			// R.anim.exit,
			// R.anim.pop_enter, R.anim.pop_exit);
			// Replace whatever is in the fragment_container view with this
			// fragment,
			// and add the transaction to the back stack
			fragmentTransaction.replace(R.id.content_pane,
					mDealsDetailsFragment);

			// Commit the transaction
			fragmentTransaction.commit();
			


		} else {
			Intent intent = new Intent(getActivity(), DealDetailsActivity.class);
		
		
			startActivity(intent);

		}

	}

	@Override
	public void onSuccess() {

		if (mDialog != null && mDialog.isShowing())
			mDialog.dismiss();
		mHandler.sendMessage(new Message());
		
		
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

	private void trgrGetMenusService() {
		new DBManager(getActivity()).clearMenus();
		mDialog = new ProgressDialog(getActivity());
		mDialog.setMessage(getActivity().getString(R.string.loading));
		mDialog.setCancelable(false);
		mDialog.show();
		new HttpHandler().getMenus(getActivity(), this);
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

	final Handler mHandler = new Handler(Looper.getMainLooper()) {

		public void handleMessage(Message msg) {

			if (msg.arg1 == HttpHandler.NO_NETWORK_CODE) {

				showNoNetworkAlertDialog();
			} else if (msg.arg1 == HttpHandler.DEFAULT_CODE) {// Empty Message
																// layout.

			}

			else {
				MenuAdapter menuAdapter = new MenuAdapter(getActivity(),
						new DBManager(getActivity()).fetchCatalogs(),menuFragment);
				mListView.setAdapter(menuAdapter);
				mListView.setOverScrollMode(ScrollView.OVER_SCROLL_ALWAYS);
			}
		}

	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		cardObject	=		(JSONObject)v.getTag();
		System.out.println("ID>>> "+cardObject);
		loadDealDetailsFragment();
		
	
	}
	
	

}
