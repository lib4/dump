package com.appbase.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.appbase.adapters.DummyAdapter;
import com.appbase.R;

/**
 * 
 * @author aabuback First Sample fragment. Class defines simple list view and
 *    loading of images using Aquery
 */
public class FirstFragment extends BaseFragment {

	private ListView dummyListview;

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
		return inflater.inflate(R.layout.first_fragment, container, false);
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

		dummyListview = (ListView) this.getActivity().findViewById(
				R.id.listview);
		dummyListview.setCacheColorHint(Color.WHITE);
		DummyAdapter mDummyAdapter = new DummyAdapter(getActivity(), null);
		dummyListview.setAdapter(mDummyAdapter);

		dummyListview.setOnItemClickListener(mItemClickListener);

	}

	/**
	 * Click listener for the dummyListview
	 */

	public OnItemClickListener mItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub

			loadSecondFragment();
		}
	};

	/**
	 * Load second fragment
	 */

	private void loadSecondFragment() {

		SecondFragment mFirstFragment = new SecondFragment();
		FragmentManager fragmentManager = getActivity()
				.getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit,
				R.anim.pop_enter, R.anim.pop_exit);
		// Replace whatever is in the fragment_container view with this
		// fragment,
		// and add the transaction to the back stack
		fragmentTransaction.replace(R.id.fragment_holder, mFirstFragment);
		fragmentTransaction.addToBackStack(null);
		// Commit the transaction
		fragmentTransaction.commit();

	}

}
