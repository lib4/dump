package com.appbase.adapters;

import android.content.Context;
import android.provider.ContactsContract.CommonDataKinds.Im;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appbase.R;
import com.appbase.adapters.MenuAdapter.ViewHolder;
import com.appbase.fragments.SensorsFragment;

public class SensorsAdapter extends BaseAdapter{

	
	SensorsFragment mContext;
	public SensorsAdapter(SensorsFragment mContext){
		
		this.mContext	=	mContext;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 10;
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
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		View rowView = arg1;
		if (rowView == null) {
			LayoutInflater inflater =  mContext.getActivity().getLayoutInflater();
			rowView = inflater.inflate(R.layout.sensors_list_item, null);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.sensorName = (TextView) rowView
					.findViewById(R.id.sensor_title);
			viewHolder.sensorAdvId = (TextView) rowView
					.findViewById(R.id.sensor_adv_id);

			viewHolder.sensorStatImage = (ImageView) rowView
					.findViewById(R.id.sensor_image);

			rowView.setTag(viewHolder);
		}
		
		
		ViewHolder holder = (ViewHolder) rowView.getTag();
		
		return rowView;
	}
	
	
	static class ViewHolder {

		public TextView sensorName;
		public TextView sensorAdvId;
		public ImageView sensorStatImage;
		

	}

}
