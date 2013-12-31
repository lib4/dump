package com.appbase.customui;

import java.net.ContentHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appbase.R;
import com.appbase.androidquery.AQuery;
import com.appbase.androidquery.callback.AjaxStatus;
import com.appbase.androidquery.callback.BitmapAjaxCallback;

public class PinterestUI extends LinearLayout {
	private int NUM_COLUMN = 2;
	Context context;
	int SCREEN_WIDTH = 0;
	int SCREEN_HEIGHT = 0;
	int[] HeightArray;
	LinearLayout NextLayout;
	int TOTAL_NUM_ITEMS = 10;
	int ITEM_DRAWN_INDEX = 0;
	Cursor liveOrderCursor;
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
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics metrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(metrics);

		SCREEN_WIDTH = metrics.widthPixels;
		HeightArray = new int[NUM_COLUMN];
		
		ViewTreeObserver vto = getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@SuppressLint("NewApi")
			@Override
			public void onGlobalLayout() {
				ITEM_DRAWN_INDEX++;
				NextLayout = (LinearLayout) getChildAt(getLayoutIndexToAdd());

				if (ITEM_DRAWN_INDEX >= TOTAL_NUM_ITEMS) {

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
	}

	public void createLayout(Cursor mCursor) {
		liveOrderCursor	=	mCursor;
		TOTAL_NUM_ITEMS	=	liveOrderCursor.getCount();
		System.out.println("IM HERE>>>>>>>>>>>" + SCREEN_WIDTH);
		for (int i = 0; i < NUM_COLUMN; i++) {

			LinearLayout mLinearLayout = new LinearLayout(context);
			mLinearLayout.setLayoutParams(new LayoutParams(SCREEN_WIDTH
					/ NUM_COLUMN, LayoutParams.WRAP_CONTENT));
			
			mLinearLayout.setOrientation(LinearLayout.VERTICAL);
			setOrientation(LinearLayout.HORIZONTAL);
			mLinearLayout.setPadding(20, 20, 20, 20);
			addView(mLinearLayout);

			System.out.println("IM HERE>>>>>>>>>>> Layout Creation" + i);
		}

		draw(0);
		

	}

	private void draw(int itemIndex) {
		liveOrderCursor.moveToPosition(itemIndex);
		if (NextLayout == null) {
			NextLayout = (LinearLayout) getChildAt(0);
		}
		LayoutInflater mLayoutInflater	=	LayoutInflater.from(context);
		LinearLayout mLinearLayout	=	(LinearLayout) mLayoutInflater.inflate(R.layout.tiles,null);
		mLinearLayout.startAnimation(AnimationUtils.loadAnimation(context, R.anim.scale_alpha));
		TextView customerName	=	(TextView) mLinearLayout.findViewById(R.id.customer_name);
		customerName.setText(liveOrderCursor.getString(0));
		TextView amount	=	(TextView) mLinearLayout.findViewById(R.id.amount);
		amount.setText(liveOrderCursor.getString(1));
		
		TextView itemName	=	(TextView) mLinearLayout.findViewById(R.id.item_name);
		TextView itemPrice	=	(TextView) mLinearLayout.findViewById(R.id.item_price);
		ImageView mImageView 	=	(ImageView) mLinearLayout.findViewById(R.id.profile_pic);
		try {
			JSONArray mJsonArray	=	new JSONArray(liveOrderCursor.getString(4));
			JSONObject mJsonObject	=	mJsonArray.getJSONObject(0);
			itemName.setText(mJsonObject.getString("name"));
			itemPrice.setText(""+mJsonObject.getDouble("price"));
			
			
			AQuery aq = new AQuery(mLinearLayout);
			aq.id(mImageView)
					.image("http://design.jboss.org/arquillian/logo/final/arquillian_icon_256px.png",
							true, true, 0, 0, new BitmapAjaxCallback() {
								@Override
								public void callback(String url, ImageView iv,
										Bitmap bm, AjaxStatus status) {
									iv.setImageBitmap(bm);
									// do something to the bitmap
									// iv.setColorFilter(tint,
									// PorterDuff.Mode.SRC_ATOP);
								}
							});

			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		NextLayout.addView(mLinearLayout);
	}

	
	private int getLayoutIndexToAdd() {
		int ChildCount = getChildCount();
		int i = 0;
		int fewer = 0;
		int layoutIndex = 0;
		while (i < ChildCount) {
			System.out.println("IM HERE>>>>>>>>>>> Layout getChildAt(i) " + i
					+ "        " + getChildAt(i).getHeight());
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

		System.out.println("IM HERE>>>>>>>>>>> Layout layoutIndex"
				+ layoutIndex);
		return layoutIndex;
	}

}
