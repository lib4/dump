package com.appbase.fragments;

import org.json.JSONArray;
import org.json.JSONObject;

import android.net.NetworkInfo.DetailedState;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appbase.R;
import com.appbase.httphandler.HttpConstants;

public class DealsDetailsFragment extends BaseFragment {

	LinearLayout dealsDetailsLayout;
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

					String price = cardJsonObject
							.getString(HttpConstants.PRICE_STRING_JKEY);
					if (!price.contains("$")) {
						price = "$" + price;
					}

					priceText.setText(price);

				} catch (Exception e) {

				}

			} catch (Exception e) {

			}

			TextView cardDescriptionText = (TextView) dealsDetailsLayout
					.findViewById(R.id.cardDescription_text);
			cardDescriptionText.setText("");

			try {
				cardDescriptionText.setText(cardJsonObject
						.getString(HttpConstants.DESCRIPTION_JKEY));
			} catch (Exception e) {

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	
		//Adding choices and additions
		
		int i =0;
		try{
			
		JSONArray choices_addtion_array	=	cardJsonObject.getJSONArray(HttpConstants.CHOICE_GROUPS_JKEY);
		int choiceGroupSize	=	choices_addtion_array.length();
		
		LayoutInflater mLayoutInflater = LayoutInflater.from(getActivity());
		
		LinearLayout mLinearLayout	=	(LinearLayout) dealsDetailsLayout.findViewById(R.id.choices_addtion_layout);
		
		//Check 
		
		while(i<choiceGroupSize){//For Choice adn Addition
			
		
			
			LinearLayout choiceAddtionItem_container = (LinearLayout) mLayoutInflater
					.inflate(R.layout.choice_add_item_container, null);
			mLinearLayout.addView(choiceAddtionItem_container);
			
				JSONObject mJsonObject		=	(JSONObject) choices_addtion_array.get(i);
				
				System.out.println("XXXX  "+mJsonObject.toString());
				TextView typeText 	=	(TextView) choiceAddtionItem_container.findViewById(R.id.item_type);
				if(mJsonObject.getString(HttpConstants.TYPE_JKEY).compareToIgnoreCase("Select")==0){
					typeText.setText("Choices");
				}else{
					
					typeText.setText("Additions");
				}
				
				
				
						JSONArray choiceItems 	=	mJsonObject.getJSONArray(HttpConstants.CHOICES_JKEY);
						int choicesSize	=	choiceItems.length();
				
				int k	=	0;
				while(k<choicesSize){
				
					
					LinearLayout item = (LinearLayout) mLayoutInflater
							.inflate(R.layout.choice_addition_item, null);
					choiceAddtionItem_container.addView(item);
					JSONObject choiceObject 	=	choiceItems.getJSONObject(k);
					TextView choiceName 	=	(TextView) item.findViewById(R.id.choice_name);
					TextView choicePrice 	=	(TextView) item.findViewById(R.id.choice_price);
					choiceName.setText(choiceObject.optString(HttpConstants.NAME_JKEY));
					
					String priceString	=	choiceObject.optString(HttpConstants.PRICE_STRING_JKEY);
					if(typeText.getText().toString().compareToIgnoreCase("Choices")==0){
						
						if(priceString.isEmpty()){
							choicePrice.setText("$"+choiceObject.optString(HttpConstants.PRICE_JKEY));
							
						}else if(priceString.contains("$")){
							choicePrice.setText("+$"+choiceObject.optString(HttpConstants.PRICE_JKEY));
						}
						else 
							choicePrice.setText("+$"+choiceObject.optString(HttpConstants.PRICE_JKEY));
						
					}else {
						choicePrice.setText("+$"+choiceObject.optString(HttpConstants.PRICE_JKEY));
					}
					
				
					
					k++;
					
				}
				
				
			i++;
		}
		}catch(Exception e){
			e.printStackTrace(); 
			
		}
	}
}
