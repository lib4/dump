package com.appbase.adapters;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.appbase.androidquery.AQuery;
import com.appbase.androidquery.callback.AjaxStatus;
import com.appbase.androidquery.callback.BitmapAjaxCallback;
import com.appbase.datastorage.DBManager;
import com.appbase.R;

/**
 * 
 * @author aabuback Dummy base adapter
 */
public class DummyAdapter extends BaseAdapter {

	private final Activity iContext; // Context to access the UI resources
	private Cursor iCursor;

	public DummyAdapter(Context mContext,Cursor mCursor) {

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
			rowView = inflater.inflate(R.layout.list_viewholder, null);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.image = (ImageView) rowView.findViewById(R.id.row_image);
			viewHolder.txt_comment	=	(TextView) rowView.findViewById(R.id.txt_comment);
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
		holder.txt_comment.setText("ID:WXN000"+iCursor.getString(0)+"\n Comment: "+iCursor.getString(1));
		return rowView;
	}

	static class ViewHolder {
		public ImageView image;
		public TextView txt_comment;
	}

}
