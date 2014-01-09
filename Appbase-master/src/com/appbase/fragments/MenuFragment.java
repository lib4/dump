package com.appbase.fragments;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.menu;
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
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SearchView;

import com.appbase.R;
import com.appbase.activities.DealDetailsActivity;
import com.appbase.adapters.MenuAdapter;
import com.appbase.datastorage.DBManager;
import com.appbase.httphandler.HTTPResponseListener;
import com.appbase.httphandler.HttpConstants;
import com.appbase.httphandler.HttpHandler;

public class MenuFragment extends BaseFragment implements HTTPResponseListener,
		OnClickListener, SearchView.OnQueryTextListener,         SearchView.OnCloseListener {

	LinearLayout menuLayout;
	ListView mListView;
	static ProgressDialog mDialog;
	MenuFragment menuFragment;

	boolean isFetchFromServer = false;
	public static JSONObject cardObject;
	MenuAdapter menuAdapter;
	JSONArray cards_Array = new JSONArray();
	SearchView seachView;
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
		init();
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
		mListView.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				cardObject	=	 (JSONObject) menuAdapter.getItem(arg2);
				loadDealDetailsFragment();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				cardObject	=	 (JSONObject) menuAdapter.getItem(arg2);
				loadDealDetailsFragment();
				
			}
		});
		
		
		//prepare the SearchView  
		SearchView searchView = (SearchView) menuLayout.findViewById(R.id.search);
		//Sets the default or resting state of the search field. If true, a single search icon is shown by default and 
		// expands to show the text field and other buttons when pressed. Also, if the default state is iconified, then it
		// collapses to that state when the close button is pressed. Changes to this property will take effect immediately.
		//The default value is true.
		searchView.setIconifiedByDefault(false);
		searchView.setOnQueryTextListener(this);
		searchView.setOnCloseListener(this); 

		
	}

	/**
	 * Load the Deals Details fragment
	 * 
	 */

	public void loadDealDetailsFragment() {

		View mView = getActivity().getWindow().getDecorView()
				.findViewById(android.R.id.content);

		if (mView.findViewById(R.id.slide_list) != null) {
			System.out.println("NOT NULLE "
					+ getActivity().getClass().getCanonicalName());

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
				cards_Array	=	setCardArray( new DBManager(
						getActivity()).fetchCatalogs());
				menuAdapter = new MenuAdapter(getActivity(),cards_Array, menuFragment);
				mListView.setAdapter(menuAdapter);
				mListView.setSmoothScrollbarEnabled(true);
				mListView.setOverScrollMode(ScrollView.OVER_SCROLL_ALWAYS);

				resolveWidth();
			}
		}

	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		cardObject = (JSONObject) v.getTag();
		System.out.println("ID>>> " + cardObject);
		loadDealDetailsFragment();

	}

	private void resolveWidth() {

		try {
			WindowManager wm = (WindowManager) getActivity().getSystemService(
					Context.WINDOW_SERVICE);
			DisplayMetrics metrics = new DisplayMetrics();
			wm.getDefaultDisplay().getMetrics(metrics);

			if (metrics.widthPixels > 1000) {
				loadDealDetailsFragment();

			}
		} catch (Exception e) {

		}

	}

	@Override
	public boolean onClose() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onQueryTextChange(String arg0) {
		// TODO Auto-generated method stub
		System.out.println("SEARCH STRING"+arg0);
		menuAdapter.cards_Array	= searchCardArray(arg0);
		menuAdapter.notifyDataSetChanged();
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void trgrSearch(String searchStr){
		
		menuAdapter.cards_Array	= searchCardArray(searchStr);
		menuAdapter.notifyDataSetChanged();
	}
	
	/**
	 * Converting Cursor To JsonArray
	 * @param iCursor
	 * @return cardsArray
	 */
	private JSONArray  setCardArray(Cursor iCursor) {
		JSONArray cards_Array = new JSONArray();
		boolean catalogNameAdded = false;
		boolean groupNameAdded = false;
		if(iCursor==null){
			return null;
		}
		while (iCursor.moveToNext() != false) {
			try {
				
				System.out.println("--------------------------------------");
				catalogNameAdded = false;
				String cataloge_name = iCursor.getString(0);
				
				
				JSONArray mJsonArray = new JSONArray(iCursor.getString(4));

				int size = mJsonArray.length();
				for (int k = 0; k < size; k++) {
					JSONObject group = (JSONObject) mJsonArray.get(k);
					String groupName	=	"";
					try{
						groupName = group.getString(HttpConstants.NAME_JKEY);
					}catch(Exception e){
						
					}
					groupNameAdded = false;
					JSONArray subGroups = group
							.getJSONArray(HttpConstants.SUBGROUPS_JKEY);

					int length = subGroups.length();
					for (int i = 0; i < length; i++) {
						try {

							JSONObject cards = (JSONObject) subGroups.get(i);

							JSONArray cardsArray = cards
									.getJSONArray(HttpConstants.CARDS_JKEY);

							int cardsSize = cardsArray.length();
							for (int j = 0; j < cardsSize; j++) {

								JSONObject card = (JSONObject) cardsArray
										.get(j);
								card.put("groupName", groupName);
								if (!groupNameAdded) {
									
									card.put("showGroupName", true);
									groupNameAdded = true;
								}
								card.put("catalogeName", cataloge_name);
								if (!catalogNameAdded) {
									card.put("showCatalogeName", true);
									catalogNameAdded = true;
								}
								System.out.println("CARD>>>" + card);
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
			
			System.out.println("--------------------------------------");

		}
		
		return cards_Array;
	}
	
	
	
	/**
	 * Search JsonArray
	 * @return search Result cardsArray
	 */
	private JSONArray  searchCardArray(String searchString) {
		
		JSONArray searchResult	=	new JSONArray();
		int sourceArrayLength	=	cards_Array.length();
		int i =0;
		while(i<sourceArrayLength){
			
				try {
					JSONObject card	=	cards_Array.getJSONObject(i);
					
					String name	=	card.optString(HttpConstants.NAME_JKEY);
					String description	=	card.optString(HttpConstants.DESCRIPTION_JKEY);
					if(name.toLowerCase().contains(searchString.toLowerCase())||
							description.toLowerCase().contains(searchString.toLowerCase())){
							searchResult.put(card);
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				i++;
		}
		
		
		return searchResult;
	}

}
