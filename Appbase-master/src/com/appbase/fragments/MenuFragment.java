package com.appbase.fragments;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.appbase.R;
import com.appbase.activities.BaseActivity;
import com.appbase.activities.DealDetailsActivity;
import com.appbase.adapters.MenuAdapter;
import com.appbase.datastorage.DBManager;
import com.appbase.httphandler.HTTPResponseListener;
import com.appbase.httphandler.HttpConstants;
import com.appbase.httphandler.HttpHandler;
import com.appbase.utils.Utils;

public class MenuFragment extends BaseFragment implements HTTPResponseListener {

	LinearLayout menuLayout;
	ListView mListView;
	static ProgressDialog mDialog;
	MenuFragment menuFragment;

	boolean isFetchFromServer = false;
	public static JSONObject cardObject;
	MenuAdapter menuAdapter;
	JSONArray cards_Array = new JSONArray();
	TextView noItemTextView;

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
		
		
		
		mDialog = null;
		// Inflate the layout for this fragment
		menuLayout = (LinearLayout) inflater.inflate(R.layout.menu_fragment,
				container, false);
		if (Utils.BUSINESS_NAME.length() == 0) {
			getActivity().getActionBar().setTitle(Utils.CATALOGUE_TEXT);
		} else {
			getActivity().getActionBar().setTitle(Utils.BUSINESS_NAME);
		}

		init();
		if (new DBManager(getActivity()).isCatalogsAvailable()
				&& !Utils.REFRESH_CATALOGE) {
			isFetchFromServer = false;
		}
		
		
		
		if (isFetchFromServer) {
			trgrGetMenusService();
		} else {
			onSuccess();
		}
		menuFragment = this;

		return menuLayout;
	}

	/**
	 * Method to initialize all the UI element and start any network operation
	 * if required.
	 */
	private void init() {

		mListView = (ListView) menuLayout.findViewById(R.id.menu_list);
		noItemTextView = (TextView) menuLayout.findViewById(R.id.no_item_found);

	}

	/**
	 * Load the Deals Details fragment
	 * 
	 */

	public void loadDealDetailsFragment(int itemIndex) {
		cardObject = (JSONObject) menuAdapter.getItem(itemIndex);

		if (!Utils.IS_TABLET) {
			Intent intent = new Intent(getActivity(), DealDetailsActivity.class);
			startActivity(intent);
		} else {
			
			((BaseActivity)getActivity()).hideSearchActionItem();

			DealsDetailsFragment mDealsDetailsFragment = new DealsDetailsFragment();
			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager
					.beginTransaction();

			// fragmentTransaction.setCustomAnimations(R.anim.enter,
			// R.anim.exit,
			// R.anim.pop_enter, R.anim.pop_exit);
			// Replace whatever is in the fragment_container view with this
			// fragment,
			// and add the transaction to the back stack
			fragmentTransaction.replace(R.id.fragment_holder,
					mDealsDetailsFragment);
			fragmentTransaction.addToBackStack(null);
			// Commit the transaction
			fragmentTransaction.commit();

		}

	}
	
	@Override
	public void onResume(){
		super.onResume();
		((BaseActivity)getActivity()).showSearchActionItem();
		
	}

	@Override
	public void onSuccess() {

		if (mDialog != null && mDialog.isShowing())
			mDialog.dismiss();
		mHandler.sendMessage(new Message());

		Utils.REFRESH_CATALOGE = false;

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
				cards_Array = setCardArray(new DBManager(getActivity())
						.fetchCatalogs());

				if (cards_Array != null && cards_Array.length() > 0) {
					menuAdapter = new MenuAdapter(getActivity(), cards_Array,
							menuFragment);
					mListView.setAdapter(menuAdapter);
					mListView.setSmoothScrollbarEnabled(true);
					mListView.setOverScrollMode(ScrollView.OVER_SCROLL_ALWAYS);
				} else {
					mListView.setVisibility(View.GONE);
					noItemTextView.setVisibility(View.VISIBLE);

				}

				resolveWidth();
			}
		}

	};

	private void resolveWidth() {

		try {
			WindowManager wm = (WindowManager) getActivity().getSystemService(
					Context.WINDOW_SERVICE);
			DisplayMetrics metrics = new DisplayMetrics();
			wm.getDefaultDisplay().getMetrics(metrics);

		} catch (Exception e) {

		}

	}

	@Override
	public void onStop(){
		
		((BaseActivity)getActivity()).hideSearchActionItem();
		super.onStop();
	}
	public void trgrSearch(String searchStr) {

		try {
			menuAdapter.cards_Array = searchCardArray(searchStr);
			if (menuAdapter.cards_Array != null
					&& menuAdapter.cards_Array.length() > 0) {
				mListView.setVisibility(View.VISIBLE);
				menuAdapter.notifyDataSetChanged();
				noItemTextView.setVisibility(View.GONE);
			} else {
				mListView.setVisibility(View.GONE);
				noItemTextView.setVisibility(View.VISIBLE);

			}
		} catch (Exception e) {

		}
	}

	/**
	 * Converting Cursor To JsonArray
	 * 
	 * @param iCursor
	 * @return cardsArray
	 */
	private JSONArray setCardArray(Cursor iCursor) {
		JSONArray cards_Array = new JSONArray();
		boolean catalogNameAdded = false;
		boolean groupNameAdded = false;
		if (iCursor == null) {
			return null;
		}
		while (!iCursor.isClosed() && iCursor.moveToNext() != false) {
			try {

				catalogNameAdded = false;
				String cataloge_name = iCursor.getString(0);
				String cataloge_type = iCursor.getString(1);

				JSONArray mJsonArray = new JSONArray(iCursor.getString(4));

				int size = mJsonArray.length();
				for (int k = 0; k < size; k++) {
					JSONObject group = (JSONObject) mJsonArray.get(k);
					String groupName = "";
					try {
						groupName = group.getString(HttpConstants.NAME_JKEY);
					} catch (Exception e) {

					}

					groupNameAdded = false;
					JSONArray subGroups = group
							.getJSONArray(HttpConstants.SUBGROUPS_JKEY);

					int length = subGroups.length();
					for (int i = 0; i < length; i++) {
						try {

							JSONObject cards = (JSONObject) subGroups.get(i);
							String subGroupName = cards
									.optString(HttpConstants.NAME_JKEY);
							JSONArray cardsArray = cards
									.getJSONArray(HttpConstants.CARDS_JKEY);

							int cardsSize = cardsArray.length();
							for (int j = 0; j < cardsSize; j++) {

								JSONObject card = (JSONObject) cardsArray
										.get(j);
								card.put("groupName", groupName);
								card.put("subGroupName", subGroupName);
								if (!groupNameAdded) {

									card.put("showGroupName", true);
									groupNameAdded = true;
								}
								card.put("catalogeName", cataloge_name);
								if (!catalogNameAdded) {
									card.put("showCatalogeName", true);
									catalogNameAdded = true;
								}
								card.put("cataloge_type", cataloge_type);
								cards_Array.put(card);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return cards_Array;
	}

	/**
	 * Search JsonArray
	 * 
	 * @return search Result cardsArray
	 */
	private JSONArray searchCardArray(String searchString) {

		JSONArray searchResult = new JSONArray();
		int sourceArrayLength = cards_Array.length();
		int i = 0;
		boolean foundGroupHeader = false;

		while (i < sourceArrayLength) {

			try {
				JSONObject card = cards_Array.getJSONObject(i);

				String name = card.optString(HttpConstants.NAME_JKEY);
				String description = card
						.optString(HttpConstants.DESCRIPTION_JKEY);
				boolean showGroupName = card.optBoolean("showGroupName");
				boolean showCatalogeName = card.optBoolean("showCatalogeName");

				if (showGroupName || showCatalogeName) {
					foundGroupHeader = true;
				}

				if (name.toLowerCase().contains(searchString.toLowerCase())
						|| description.toLowerCase().contains(
								searchString.toLowerCase())) {
					if (foundGroupHeader) {
						foundGroupHeader = false;
						card.put("showCatalogeName", true);
						card.put("showGroupName", true);
					}
					searchResult.put(card);
				}

			} catch (JSONException e) {

				e.printStackTrace();
			}
			i++;
		}

		return searchResult;
	}

}
