package com.appbase.fragments;

import org.json.JSONObject;

import android.net.NetworkInfo.DetailedState;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appbase.R;
import com.appbase.activities.MenuActivity;
import com.appbase.httphandler.HttpConstants;

public class DealsDetailsFragment extends BaseFragment {

	LinearLayout dealsDetailsLayout;
	Button back_Btn, logout_Btn;
	boolean hideHeaderBar = false;

	JSONObject cardJsonObject;

	public void hideHeaderBar(boolean hideHeaderBar) {

		this.hideHeaderBar = hideHeaderBar;
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
		dealsDetailsLayout = (LinearLayout) inflater.inflate(
				R.layout.deal_details_fragment, container, false);
		this.cardJsonObject = MenuFragment.cardObject;
		init();
		if (hideHeaderBar) {
			back_Btn.setVisibility(View.GONE);
		}

		return dealsDetailsLayout;
	}

	/**
	 * Method to initialize all the UI element and start any network operation
	 * if required.
	 */
	private void init() {

		try {

			System.out.println("____________");
			System.out.println("" + cardJsonObject);

			System.out.println("____________");

			TextView tileText = (TextView) dealsDetailsLayout
					.findViewById(R.id.title_txt);
			try {
				tileText.setText(cardJsonObject.getString("groupName"));
			} catch (Exception e) {

			}

			TextView cardName = (TextView) dealsDetailsLayout
					.findViewById(R.id.card_name);

			try {
				cardName.setText(cardJsonObject
						.getString(HttpConstants.NAME_JKEY));
			} catch (Exception e) {

			}

			TextView priceText = (TextView) dealsDetailsLayout
					.findViewById(R.id.price_text);
			try {
				
				try {
					
					String price 	=	cardJsonObject
							.getString(HttpConstants.PRICE_STRING_JKEY);
					if(!price.contains("$")){
						price="$"+price;
					}
					
					priceText.setText(price);
					
				} catch (Exception e) {

				}
				
				
			} catch (Exception e) {

			}

			TextView cardDescriptionText = (TextView) dealsDetailsLayout
					.findViewById(R.id.cardDescription_text);
			
			try{
			cardDescriptionText.setText(cardJsonObject
					.getString(HttpConstants.DESCRIPTION_JKEY));
			}catch(Exception e){
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		back_Btn = (Button) dealsDetailsLayout.findViewById(R.id.back_btn);

		back_Btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getActivity().finish();
			}
		});

	}
}
