package com.appbase.adapters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appbase.R;
import com.appbase.activities.MenuActivity;
import com.appbase.androidquery.AQuery;
import com.appbase.androidquery.callback.AjaxStatus;
import com.appbase.androidquery.callback.BitmapAjaxCallback;
import com.appbase.fragments.MenuFragment;
import com.appbase.httphandler.HttpConstants;

/**
 * 
 * @author aabuback Dummy base adapter
 */
public class MenuAdapter extends BaseAdapter {

	private final Activity iContext; // Context to access the UI resources
	private Cursor iCursor;
	private MenuFragment menuFragment;
	public JSONObject cardObject;

	JSONArray cards_Array = new JSONArray();

	public MenuAdapter(Context mContext, Cursor mCursor,
			MenuFragment menuFragment) {

		this.iContext = (Activity) mContext;
		this.iCursor = mCursor;
		this.menuFragment = menuFragment;
		setCardArray();

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (cards_Array != null && cards_Array.length() > 0)
			return cards_Array.length();
		else
			return 0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		try {
			return cards_Array.get(arg0);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub

		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = iContext.getLayoutInflater();
			rowView = inflater.inflate(R.layout.deals_holder, null);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.calaloge_name = (TextView) rowView
					.findViewById(R.id.cataloge_name);
			viewHolder.cardHolder = (LinearLayout) rowView
					.findViewById(R.id.card_holder_layout);

			viewHolder.group_name = (TextView) rowView
					.findViewById(R.id.group_name);

			viewHolder.card_titleTextView = (TextView) rowView
					.findViewById(R.id.card_title);
			viewHolder.card_descriptionTextView = (TextView) rowView
					.findViewById(R.id.card_description);

			viewHolder.priceTextView = (TextView) rowView
					.findViewById(R.id.price);

			viewHolder.cardImage = (ImageView) rowView.findViewById(R.id.image);

			rowView.setTag(viewHolder);
		}
		
		
		
	

		ViewHolder holder = (ViewHolder) rowView.getTag();
		
		

		holder.group_name.setText("");

		holder.card_titleTextView .setText("");
		holder.card_descriptionTextView .setText("");

		holder.priceTextView .setText("");

		try {
			setGroupDetails((JSONObject) cards_Array.get(arg0), holder, rowView);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rowView;
	}

	private void setGroupDetails(JSONObject card, ViewHolder holder,
			View rowView) {

		try {

			// System.out.println("*****" +
			// mJsonObject.optString("catalogeName"));

			if (card.optBoolean("showCatalogeName")) {
				holder.calaloge_name.setVisibility(View.VISIBLE);
				holder.calaloge_name.setText(card.optString("catalogeName"));

			} else {

				holder.calaloge_name.setVisibility(View.GONE);
			}

			if (card.optBoolean("showGroupName")) {
				holder.group_name.setVisibility(View.VISIBLE);
				holder.group_name.setText(card.getString("groupName"));

			} else {

				holder.group_name.setVisibility(View.GONE);
			}

			try {
				holder.card_titleTextView.setText(card
						.getString(HttpConstants.NAME_JKEY));
			} catch (Exception e) {

			}
			try {
				holder.card_descriptionTextView.setText(card
						.getString(HttpConstants.DESCRIPTION_JKEY));
			} catch (Exception e) {

			}

			try {
				
				String price 	=	card
						.getString(HttpConstants.PRICE_STRING_JKEY);
				if(!price.contains("$")){
					price="$"+price;
				}
				holder.priceTextView.setText(price);
			} catch (Exception e) {

			}

			AQuery aq = new AQuery(rowView);
			aq.id(holder.cardImage)
					.image("http://design.jboss.org/arquillian/logo/final/arquillian_icon_256px.png",
							true, true, 0, 0, new BitmapAjaxCallback() {
								@Override
								public void callback(String url, ImageView iv,
										Bitmap bm, AjaxStatus status) {
									iv.setImageBitmap(bm);
									// do something to the
									// bitmap
									// iv.setColorFilter(tint,
									// PorterDuff.Mode.SRC_ATOP);
								}
							});

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static class ViewHolder {

		public TextView calaloge_name;
		public LinearLayout cardHolder;
		public TextView group_name;
		public TextView card_titleTextView;
		public TextView card_descriptionTextView;
		public TextView priceTextView;;
		public ImageView cardImage;

	}

	private void setCardArray() {

		boolean catalogNameAdded = false;
		boolean groupNameAdded = false;
		if(iCursor==null){
			return;
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
	}
}
