package com.appbase.customui;

import java.text.DecimalFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.dimen;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.appbase.R;
import com.appbase.activities.BaseActivity;
import com.appbase.androidquery.AQuery;
import com.appbase.androidquery.callback.AjaxStatus;
import com.appbase.androidquery.callback.BitmapAjaxCallback;
import com.appbase.fragments.BaseFragment;
import com.appbase.fragments.LiveOrderFragment;
import com.appbase.httphandler.HTTPResponseListener;
import com.appbase.httphandler.HttpHandler;
import com.appbase.utils.Utils;

public class PinterestUI extends LinearLayout implements HTTPResponseListener {
	private int NUM_COLUMN = 2;
	Context context;
	int SCREEN_WIDTH = 0;
	int SCREEN_HEIGHT = 0;
	int[] HeightArray;
	LinearLayout NextLayout;
	int TOTAL_NUM_ITEMS = 0;
	int ITEM_DRAWN_INDEX = 0;
	Cursor liveOrderCursor;
	Typeface tf;
	PopupMenu popup;
	private boolean isAnimationNeeded = true;
	DecimalFormat df = new DecimalFormat("0.00");

	int blankSpace = 0;
	int sidePanelWidthInPix;

	public PinterestUI(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;

	}

	public PinterestUI(Context context) {
		super(context);
		this.context = context;
		setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));

		if (!Utils.IS_TABLET)
			SCREEN_WIDTH = Utils.WIDTH_IN_PX;
		else {

			float width = context.getResources().getDimension(
					R.dimen.drawer_width);

			sidePanelWidthInPix = (Utils.convertDensityToPixel(
					(Activity) context, (int) width));
			
			
			
			SCREEN_WIDTH = (int) (Utils.WIDTH_IN_PX - sidePanelWidthInPix);
			blankSpace = Utils.WIDTH_IN_PX - (sidePanelWidthInPix + (2 * 400));

		}

		NUM_COLUMN = SCREEN_WIDTH / 400;

		HeightArray = new int[NUM_COLUMN];

	}

	public void createLayout(Cursor mCursor, final LiveOrderFragment mFragment) {

		ITEM_DRAWN_INDEX = 0;
		if (popup != null)
			popup.dismiss();
		removeAllViews();
		if (NextLayout != null) {
			isAnimationNeeded = false;
		}
		NextLayout = null;
		ViewTreeObserver vto = getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@SuppressLint("NewApi")
			@Override
			public void onGlobalLayout() {
				ITEM_DRAWN_INDEX++;

				NextLayout = (LinearLayout) getChildAt(getLayoutIndexToAdd());

				if (ITEM_DRAWN_INDEX >= TOTAL_NUM_ITEMS) {

					if (mFragment != null) {
						mFragment.updateScrollPosition();
					}
					ViewTreeObserver obs = getViewTreeObserver();

					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
						obs.removeOnGlobalLayoutListener(this);
					} else {
						obs.removeGlobalOnLayoutListener(this);
					}
				} else {
					draw(ITEM_DRAWN_INDEX);
				}
			}

		});

		liveOrderCursor = mCursor;

		TOTAL_NUM_ITEMS = liveOrderCursor.getCount();

		for (int i = 0; i < NUM_COLUMN; i++) {

			LinearLayout mLinearLayout = new LinearLayout(context);
			mLinearLayout.setLayoutParams(new LayoutParams(SCREEN_WIDTH
					/ NUM_COLUMN, LayoutParams.WRAP_CONTENT));

			mLinearLayout.setOrientation(LinearLayout.VERTICAL);
			setOrientation(LinearLayout.HORIZONTAL);
			setGravity(Gravity.CENTER_HORIZONTAL);
			mLinearLayout.setPadding(2, 5, 2, 5);
			addView(mLinearLayout);

		}

		draw(0);

	}

	private void draw(int itemIndex) {

		try {
			System.out.println("Drawing " + itemIndex);
			if (liveOrderCursor != null) {
				liveOrderCursor.moveToPosition(itemIndex);
				if (NextLayout == null) {
					NextLayout = (LinearLayout) getChildAt(0);
				}
				LayoutInflater mLayoutInflater = LayoutInflater.from(context);
				FrameLayout mLinearLayout = (FrameLayout) mLayoutInflater
						.inflate(R.layout.tiles, null);

				if (isAnimationNeeded)
					mLinearLayout.startAnimation(AnimationUtils.loadAnimation(
							context, R.anim.scale_alpha));

				if (liveOrderCursor.getString(3) != null
						&& liveOrderCursor.getString(3).compareToIgnoreCase(
								"Pending") == 0) {

					ImageView mImageView = ((ImageView) mLinearLayout
							.findViewById(R.id.tick_image));
					// popMenuIcon.setTag(mLinearLayout);
					mImageView.setTag(liveOrderCursor.getString(2));
					mLinearLayout.setOnClickListener(TileClickLister);
				} else {

					ImageView mImageView = ((ImageView) mLinearLayout
							.findViewById(R.id.tick_image));
					mImageView.setTag(liveOrderCursor.getString(2));
					mImageView.setVisibility(View.VISIBLE);
					// popMenuIcon.setTag(mLinearLayout);
					mLinearLayout.setOnClickListener(TileClickLister);
				}

				TextView customerName = (TextView) mLinearLayout
						.findViewById(R.id.customer_name);

				customerName.setText(liveOrderCursor.getString(0));
				TextView amount = (TextView) mLinearLayout
						.findViewById(R.id.amount);

				String priceFormated = df.format(liveOrderCursor.getDouble(1));
				priceFormated = "$" + priceFormated;
				amount.setText("" + priceFormated);
				LinearLayout itemLinearLayout = (LinearLayout) mLinearLayout
						.findViewById(R.id.itemsLayout);

				ImageView mImageView = (ImageView) mLinearLayout
						.findViewById(R.id.profile_pic);
				try {
					JSONArray mJsonArray = new JSONArray(
							liveOrderCursor.getString(4));
					int itemsSize = mJsonArray.length();
					for (int i = 0; i < itemsSize; i++) {
						JSONObject mJsonObject = mJsonArray.getJSONObject(i);

						LayoutInflater itemLayoutInflater = LayoutInflater
								.from(context);
						LinearLayout mItemLinearLayout = (LinearLayout) itemLayoutInflater
								.inflate(R.layout.item_tile, null);
						TextView itemName = (TextView) mItemLinearLayout
								.findViewById(R.id.item_name);
						TextView itemPrice = (TextView) mItemLinearLayout
								.findViewById(R.id.item_price);
						itemLinearLayout.addView(mItemLinearLayout);
						itemName.setText(mJsonObject.getString("name"));
						priceFormated = df.format(mJsonObject
								.getDouble("price"));

						priceFormated = "$" + priceFormated;

						itemPrice.setText(priceFormated);

						AQuery aq = new AQuery(mLinearLayout);
						aq.id(mImageView)

						// .image("http://res.cloudinary.com/demo/image/upload/sample.jpg",
								.image(liveOrderCursor.getString(5),

								true, true, 0, 0, new BitmapAjaxCallback() {
									@Override
									public void callback(String url,
											ImageView iv, Bitmap bm,
											AjaxStatus status) {
										iv.setImageBitmap(bm);
										// do something to the bitmap
										// iv.setColorFilter(tint,
										// PorterDuff.Mode.SRC_ATOP);
									}
								});

					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				NextLayout.addView(mLinearLayout);
			}
		} catch (Exception e) {
			// Rare scenario.
			e.printStackTrace();
		}
	}

	public OnClickListener TileClickLister = new OnClickListener() {

		@Override
		public void onClick(final View v) {

			TextView popMenuIcon = (TextView) v
					.findViewById(R.id.customer_name);

			// Creating the instance of PopupMenu
			popup = new PopupMenu(context, popMenuIcon);

			// final FrameLayout mLinearLayout = (FrameLayout) v.getTag();

			final ImageView mTickImage = (ImageView) v
					.findViewById(R.id.tick_image);
			if (mTickImage.getVisibility() == View.VISIBLE) {
				popup.getMenuInflater().inflate(
						R.menu.order_options_confirm_only, popup.getMenu());

			} else {
				// Inflating the Popup using xml file
				popup.getMenuInflater().inflate(R.menu.order_options,
						popup.getMenu());
			}
			// registering popup with OnMenuItemClickListener
			popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
				public boolean onMenuItemClick(MenuItem item) {

					switch (item.getItemId()) {

					case R.id.acknowledge_order:
						new HttpHandler().liveOrderAction(context,
								PinterestUI.this, (String) mTickImage.getTag(),
								"acknowledge");

						mTickImage.setVisibility(View.VISIBLE);

						break;
					case R.id.confirm_order:
						new HttpHandler().liveOrderAction(context,
								PinterestUI.this, (String) mTickImage.getTag(),
								"confirm");
						v.setVisibility(View.GONE);
						break;
					case R.id.reject_order:
						new HttpHandler().liveOrderAction(context,
								PinterestUI.this, (String) mTickImage.getTag(),
								"cancel");
						v.setVisibility(View.GONE);
						break;

					}
					return true;
				}
			});

			popup.show();// showing popup menu

		}
	};

	private int getLayoutIndexToAdd() {
		int ChildCount = getChildCount();
		int i = 0;
		int fewer = 0;
		int layoutIndex = 0;
		while (i < ChildCount) {

			if (i == 0) {
				fewer = getChildAt(i).getHeight();
				layoutIndex = i;
			} else {

				if (getChildAt(i).getHeight() < fewer) {
					fewer = getChildAt(i).getHeight();
					layoutIndex = i;
				}
			}
			i++;
		}

		return layoutIndex;
	}

	@Override
	public void onSuccess() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFailure(int failureCode) {
		// TODO Auto-generated method stub

	}

	public void adjustSidePaneWidth() {
		/*
		Log.e("sidePanelWidthInPix + blankSpace",""+(sidePanelWidthInPix + blankSpace) + " BLANK "+blankSpace);
		BaseFragment v = (BaseFragment) ((BaseActivity) context)
				.getFragmentManager().findFragmentByTag("SettingsFragment");
		(v.getView()).setLayoutParams(new LinearLayout.LayoutParams(
				sidePanelWidthInPix,
				LinearLayout.LayoutParams.MATCH_PARENT));
				*/

	}

}
