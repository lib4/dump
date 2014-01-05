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

	public MenuAdapter(Context mContext, Cursor mCursor,
			MenuFragment menuFragment) {

		this.iContext = (Activity) mContext;
		this.iCursor = mCursor;
		this.menuFragment = menuFragment;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (iCursor != null && iCursor.getCount() > 0)
			return iCursor.getCount();
		else
			return 0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
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

		ViewHolder viewHolder = new ViewHolder();
		View rowView = convertView;
		// if (rowView == null) {
		LayoutInflater inflater = iContext.getLayoutInflater();
		rowView = inflater.inflate(R.layout.deals_holder, null);

		viewHolder.calaloge_name = (TextView) rowView
				.findViewById(R.id.cataloge_name);

		rowView.setTag(viewHolder);
		// }

		viewHolder.cardHolder = (LinearLayout) rowView
				.findViewById(R.id.card_holder_layout);
		ViewHolder holder = (ViewHolder) rowView.getTag();

		iCursor.moveToPosition(arg0);

		holder.calaloge_name.setText(iCursor.getString(0));
		// holder.txt_description.setText("ID:WXN000"+iCursor.getString(0)+"\n Comment: "+iCursor.getString(1));

		System.out.println("***************");
		System.out.println("iCursor.getString(4)\n" + iCursor.getString(4));
		System.out.println("***************");
		try {
			getGroupDetails(new JSONArray(iCursor.getString(4)), holder, arg0);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rowView;
	}

	private void getGroupDetails(JSONArray mJsonArray, ViewHolder holder,
			int index) {

		try {

			int size = mJsonArray.length();
			for (int k = 0; k < size; k++) {
				JSONObject group = (JSONObject) mJsonArray.get(k);

				LayoutInflater inflater = iContext.getLayoutInflater();
				LinearLayout cardsHolder = (LinearLayout) inflater.inflate(
						R.layout.cards_holder, null);
				TextView groupNameTextView = (TextView) cardsHolder
						.findViewById(R.id.group_name);
				try {
					groupNameTextView.setText(group
							.getString(HttpConstants.NAME_JKEY));

				} catch (Exception e) {

				}

				JSONArray subGroups = group
						.getJSONArray(HttpConstants.SUBGROUPS_JKEY);

				int length = subGroups.length();
				for (int i = 0; i < length; i++) {
					try {

						JSONObject cards = (JSONObject) subGroups.get(i);

						JSONArray cardsArray = cards
								.getJSONArray(HttpConstants.CARDS_JKEY);

						int cardsSize = cardsArray.length();

						LinearLayout cardsLayout = (LinearLayout) cardsHolder
								.findViewById(R.id.cards_holder);
						for (int j = 0; j < cardsSize; j++) {

							LayoutInflater infltr = iContext.getLayoutInflater();
							RelativeLayout cardView = (RelativeLayout) infltr
									.inflate(R.layout.card, null);
							JSONObject card = (JSONObject) cardsArray.get(j);

							TextView card_titleTextView = (TextView) cardView
									.findViewById(R.id.card_title);
							TextView card_descriptionTextView = (TextView) cardView
									.findViewById(R.id.card_description);

							TextView priceTextView = (TextView) cardView
									.findViewById(R.id.price);

							card.put("groupName", groupNameTextView.getText());
							cardView.setTag(card);

							try {
								card_titleTextView.setText(card
										.getString(HttpConstants.NAME_JKEY));
							} catch (Exception e) {

							}
							try {
								card_descriptionTextView
										.setText(card
												.getString(HttpConstants.DESCRIPTION_JKEY));
							} catch (Exception e) {

							}

							try {
								priceTextView
										.setText(card
												.getString(HttpConstants.PRICE_STRING_JKEY));
							} catch (Exception e) {

							}

							ImageView image = (ImageView) cardView
									.findViewById(R.id.image);
							AQuery aq = new AQuery(cardView);
							aq.id(image)
									.image("http://design.jboss.org/arquillian/logo/final/arquillian_icon_256px.png",
											true, true, 0, 0,
											new BitmapAjaxCallback() {
												@Override
												public void callback(
														String url,
														ImageView iv,
														Bitmap bm,
														AjaxStatus status) {
													iv.setImageBitmap(bm);
													// do something to the
													// bitmap
													// iv.setColorFilter(tint,
													// PorterDuff.Mode.SRC_ATOP);
												}
											});

							cardView.setOnClickListener(menuFragment);
							cardsLayout.addView(cardView);

						}

					
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					
				}

				holder.cardHolder.addView(cardsHolder);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static class ViewHolder {

		public TextView calaloge_name;
		public LinearLayout cardHolder;
		// public TextView group_name;

	}

}
