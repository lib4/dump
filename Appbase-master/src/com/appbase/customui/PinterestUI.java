package com.appbase.customui;

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
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appbase.R;
import com.appbase.androidquery.AQuery;
import com.appbase.androidquery.callback.AjaxStatus;
import com.appbase.androidquery.callback.BitmapAjaxCallback;
import com.appbase.httphandler.HTTPResponseListener;
import com.appbase.httphandler.HttpHandler;

public class PinterestUI extends LinearLayout implements HTTPResponseListener{
	private int NUM_COLUMN = 2;
	Context context;
	int SCREEN_WIDTH = 0;
	int SCREEN_HEIGHT = 0;
	int[] HeightArray;
	LinearLayout NextLayout;
	int TOTAL_NUM_ITEMS = 0;
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
		
	
	}

	public void createLayout(Cursor mCursor) {
		
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
		
		
		
		liveOrderCursor	=	mCursor;
		
		TOTAL_NUM_ITEMS	=	liveOrderCursor.getCount();

		
		if(SCREEN_WIDTH>1200){
			NUM_COLUMN	=	SCREEN_WIDTH/400;
		}
		for (int i = 0; i < NUM_COLUMN; i++) {

			LinearLayout mLinearLayout = new LinearLayout(context);
			mLinearLayout.setLayoutParams(new LayoutParams(SCREEN_WIDTH
					/ NUM_COLUMN, LayoutParams.WRAP_CONTENT));
			
			mLinearLayout.setOrientation(LinearLayout.VERTICAL);
			setOrientation(LinearLayout.HORIZONTAL);
			mLinearLayout.setPadding(10, 20, 10, 20);
			addView(mLinearLayout);

			System.out.println("IM HERE>>>>>>>>>>> Layout Creation" + i);
		}

		
		draw(0);
		

	}

	private void draw(int itemIndex) {
		
		try{
		if(liveOrderCursor!=null){
		liveOrderCursor.moveToPosition(itemIndex);
		if (NextLayout == null) {
			NextLayout = (LinearLayout) getChildAt(0);
		}
		LayoutInflater mLayoutInflater	=	LayoutInflater.from(context);
		FrameLayout mLinearLayout	=	(FrameLayout) mLayoutInflater.inflate(R.layout.tiles,null);
		mLinearLayout.setTag(liveOrderCursor.getString(2));//SettingId
		mLinearLayout.startAnimation(AnimationUtils.loadAnimation(context, R.anim.scale_alpha));
		if(liveOrderCursor.getString(3).compareToIgnoreCase("Pending")==0)
			mLinearLayout.setOnClickListener(TileClickLister);
		else{
			mLinearLayout.setOnClickListener(TileClickLister);
			
			((ImageView) mLinearLayout.findViewById(R.id.tick_image)).setVisibility(View.VISIBLE);
		}
		
		TextView customerName	=	(TextView) mLinearLayout.findViewById(R.id.customer_name);
		customerName.setText(liveOrderCursor.getString(0));
		TextView amount	=	(TextView) mLinearLayout.findViewById(R.id.amount);
		String price 	=	""+liveOrderCursor.getDouble(1);
		if(!price.contains("$")){
			price	=	"$"+price;
		}
		
		amount.setText(""+price);
		LinearLayout itemLinearLayout	=	(LinearLayout) mLinearLayout.findViewById(R.id.itemsLayout);
	
		ImageView mImageView 	=	(ImageView) mLinearLayout.findViewById(R.id.profile_pic);
		try {
			JSONArray mJsonArray	=	new JSONArray(liveOrderCursor.getString(4));
			int itemsSize	=	mJsonArray.length();
			for(int i=0;i<itemsSize;i++){
			JSONObject mJsonObject	=	mJsonArray.getJSONObject(i);
			
			LayoutInflater itemLayoutInflater	=	LayoutInflater.from(context);
			LinearLayout mItemLinearLayout	=	(LinearLayout) itemLayoutInflater.inflate(R.layout.item_tile,null);
			TextView itemName	=	(TextView) mItemLinearLayout.findViewById(R.id.item_name);
			TextView itemPrice	=	(TextView) mItemLinearLayout.findViewById(R.id.item_price);
			itemLinearLayout.addView(mItemLinearLayout);
			itemName.setText(mJsonObject.getString("name"));
			price 	=	""+mJsonObject.getDouble("price");
			if(!price.contains("$")){
				price	=	"$"+price;
			}
			
			itemPrice.setText(price);
			
			AQuery aq = new AQuery(mLinearLayout);
			aq.id(mImageView)
					//.image("http://design.jboss.org/arquillian/logo/final/arquillian_icon_256px.png",
			.image(liveOrderCursor.getString(5),
					
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

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		NextLayout.addView(mLinearLayout);
		}
		}catch(Exception e){
			//Rare scenario. 
			e.printStackTrace();
		}
	}

	
	public OnClickListener TileClickLister		=	new OnClickListener() {
		
		@Override
		public void onClick(final View v) {
			// TODO Auto-generated method stub
			LayoutInflater itemLayoutInflater	=	LayoutInflater.from(context);
			final LinearLayout mActionpanelLinearLayout	=	(LinearLayout) itemLayoutInflater.inflate(R.layout.action_item_panel,null);
			mActionpanelLinearLayout.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slideup_action_item));
			((FrameLayout)v).addView(mActionpanelLinearLayout);
			Button confirmBtn		=	(Button)	mActionpanelLinearLayout.findViewById(R.id.accept_btn);
			Button cancelBtn		=	(Button)	mActionpanelLinearLayout.findViewById(R.id.reject_btn);
			Button acknowledgeBtn	=	(Button)	mActionpanelLinearLayout.findViewById(R.id.acknowledge_btn);
			ImageView mImageView	=	(ImageView) v.findViewById(R.id.tick_image);
			
			if(mImageView.getVisibility()==View.VISIBLE){
				acknowledgeBtn.setVisibility(View.GONE);
				cancelBtn.setVisibility(View.GONE);
			}
			
			confirmBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					new HttpHandler().liveOrderAction(context, PinterestUI.this, (String)v.getTag(), "confirm");
					v.setVisibility(View.GONE);
					
				}
			});
			
			cancelBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					new HttpHandler().liveOrderAction(context, PinterestUI.this, (String)v.getTag(), "cancel");
					v.setVisibility(View.GONE);
					
				}
			});
			
			acknowledgeBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					new HttpHandler().liveOrderAction(context, PinterestUI.this, (String)v.getTag(), "acknowledge");
					((ImageView) v.findViewById(R.id.tick_image)).setVisibility(View.VISIBLE);
					mActionpanelLinearLayout.setVisibility(View.GONE);
					
				}
			});
			
			Button closeBtn	=(Button)	mActionpanelLinearLayout.findViewById(R.id.close_btn);
			closeBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					
					mActionpanelLinearLayout.setVisibility(View.GONE);
				}
			});
		
		
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

}
