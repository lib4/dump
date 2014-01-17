package com.appbase.adapters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import com.appbase.httphandler.HttpConstants;

public class SensorsAdapter extends BaseAdapter {

	SensorsFragment mContext;
	JSONArray sensorsArray;

	public SensorsAdapter(SensorsFragment mContext, JSONArray sensorArray) {

		this.mContext = mContext;
		this.sensorsArray = sensorArray;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return sensorsArray.length();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		try {
			return sensorsArray.get(arg0);
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
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		View rowView = arg1;
		if (rowView == null) {
			LayoutInflater inflater = mContext.getActivity()
					.getLayoutInflater();
			rowView = inflater.inflate(R.layout.sensors_list_item, null);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.sensorName = (TextView) rowView
					.findViewById(R.id.sensor_title);
			viewHolder.sensorType = (TextView) rowView
					.findViewById(R.id.sensor_tyoe);
			viewHolder.sensorAdvId = (TextView) rowView
					.findViewById(R.id.sensor_adv_id);

			viewHolder.sensorStatImage = (ImageView) rowView
					.findViewById(R.id.sensor_image);

			rowView.setTag(viewHolder);
		}

		ViewHolder holder = (ViewHolder) rowView.getTag();

		holder.sensorType.setVisibility(View.GONE);
		JSONObject mJsonObject = null;
		try {
			mJsonObject = sensorsArray.getJSONObject(arg0);
			holder.sensorName.setText(mJsonObject
					.getString(HttpConstants.NAME_JKEY));
			holder.sensorAdvId.setText("Advertising ID : "
					+ mJsonObject.getInt(HttpConstants.SENSOR_ID_JKEY));
			String type = mJsonObject.optString("displaySensorType");
			System.out.println("TYPE=== " + type);
			if (!type.isEmpty()) {
				holder.sensorType.setVisibility(View.VISIBLE);
				holder.sensorType.setText(type);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rowView.setTag(mJsonObject);
		return rowView;
	}

	static class ViewHolder {
		public TextView sensorType;
		public TextView sensorName;
		public TextView sensorAdvId;
		public ImageView sensorStatImage;

	}

}
