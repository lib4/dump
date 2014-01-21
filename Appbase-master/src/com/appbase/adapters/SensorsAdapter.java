package com.appbase.adapters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
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
		Log.e("GET COUNT ", "COUNT " + sensorsArray.length());
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
			viewHolder.validationStatusLayout = (LinearLayout) rowView
					.findViewById(R.id.validationStatusLayout);
			viewHolder.sensortemLayout = (RelativeLayout) rowView
					.findViewById(R.id.sensor_item);

			rowView.setTag(viewHolder);
		}

		ViewHolder holder = (ViewHolder) rowView.getTag();

		holder.sensorType.setVisibility(View.GONE);
		holder.validationStatusLayout.setVisibility(View.GONE);
		holder.sensortemLayout.setTag(arg0);
		holder.sensortemLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				mContext.sensorItemTapped((Integer)v.getTag());
			}
		});
		JSONObject mJsonObject = null;
		try {
			mJsonObject = sensorsArray.getJSONObject(arg0);
			holder.sensorName.setText(mJsonObject
					.getString(HttpConstants.NAME_JKEY));
			holder.sensorAdvId.setText("Advertising ID : "
					+ mJsonObject.getInt(HttpConstants.SENSOR_ID_JKEY));
			String type = mJsonObject
					.optString(HttpConstants.DISPLAY_SENSOR_TYPE_JKEY);

			if (!type.isEmpty()) {
				holder.sensorType.setVisibility(View.VISIBLE);
				holder.sensorType.setText(type);

			}

			String sensorType = mJsonObject.optString(HttpConstants.TYPE_JKEY);

			if (sensorType.compareToIgnoreCase(HttpConstants.ESTIMOTE) == 0) {
				holder.sensorStatImage.setVisibility(View.VISIBLE);
			} else {
				holder.sensorStatImage.setVisibility(View.GONE);
			}

			// Check for validation layout display
			String validation_status = mJsonObject
					.optString(HttpConstants.VALIDATE_STATUS);
			if (!validation_status.isEmpty()) {
				holder.sensorStatImage.setVisibility(View.GONE);
				holder.validationStatusLayout.setVisibility(View.VISIBLE);
				ImageView mImageView = (ImageView) holder.validationStatusLayout
						.findViewById(R.id.validation_status_image);
				TextView mTextView = (TextView) holder.validationStatusLayout
						.findViewById(R.id.validation_status_text);
				if (validation_status
						.compareToIgnoreCase(HttpConstants.FAILURE) == 0) {
					mTextView.setText("Validation failed on "
							+ mJsonObject
									.optString(HttpConstants.VALIDATE_TIME));
					mImageView.setImageResource(R.drawable.ic_cross);

				} else if (validation_status
						.compareToIgnoreCase(HttpConstants.SUCCESS) == 0) {
					holder.sensorStatImage.setVisibility(View.GONE);
					mTextView.setText("Validated on "
							+ mJsonObject
									.optString(HttpConstants.VALIDATE_TIME));
					mImageView.setImageResource(R.drawable.ic_tick_mark);
				}

			} else {
				holder.validationStatusLayout.setVisibility(View.GONE);
			}

		} catch (JSONException e) {

			e.printStackTrace();
		}

		return rowView;
	}

	static class ViewHolder {
		public TextView sensorType;
		public TextView sensorName;
		public TextView sensorAdvId;
		public ImageView sensorStatImage;
		public LinearLayout validationStatusLayout;
		public RelativeLayout sensortemLayout;

	}

}
