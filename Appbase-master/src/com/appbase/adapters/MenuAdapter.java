package com.appbase.adapters;

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
import android.widget.TextView;

import com.appbase.R;
import com.appbase.androidquery.AQuery;
import com.appbase.androidquery.callback.AjaxStatus;
import com.appbase.androidquery.callback.BitmapAjaxCallback;
import com.appbase.datastorage.DBManager;

/**
 * 
 * @author aabuback Dummy base adapter
 */
public class MenuAdapter extends BaseAdapter {

	private final Activity iContext; // Context to access the UI resources
	private Cursor iCursor;

	public MenuAdapter(Context mContext,Cursor mCursor) {

		this.iContext = (Activity) mContext;
		this.iCursor	=	mCursor;
		if(this.iCursor==null){
			DBManager mDbManager	=	new DBManager(mContext);
			mDbManager.open();
			iCursor	=	mDbManager.fetchProfile();
			
			
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(iCursor!=null&&iCursor.getCount()>0)
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

		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = iContext.getLayoutInflater();
			rowView = inflater.inflate(R.layout.deals_holder, null);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.image = (ImageView) rowView.findViewById(R.id.image);
			viewHolder.txt_title	=	(TextView) rowView.findViewById(R.id.title);
			viewHolder.txt_description	=	(TextView) rowView.findViewById(R.id.description);
			rowView.setTag(viewHolder);
		}

		ViewHolder holder = (ViewHolder) rowView.getTag();
		AQuery aq = new AQuery(convertView);
		aq.id(holder.image)
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

		iCursor.moveToPosition(arg0);
		//holder.txt_title.setText("ID:WXN000"+iCursor.getString(0)+"\n Comment: "+iCursor.getString(1));
		
		//holder.txt_description.setText("ID:WXN000"+iCursor.getString(0)+"\n Comment: "+iCursor.getString(1));

		return rowView;
	}

	static class ViewHolder {
		public ImageView image;
		public TextView txt_title;
		public TextView txt_description;
	}

}
