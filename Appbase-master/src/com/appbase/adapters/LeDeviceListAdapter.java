package com.appbase.adapters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.appbase.R;
import com.estimote.sdk.Beacon;
import com.estimote.sdk.Utils;




/**
 * Displays basic information about beacon. List is sorted by distance between beacon and device.
 * @see Utils#computeAccuracy(Beacon)
 *
 * @author wiktor@estimote.com (Wiktor Gworek)
 */
public class LeDeviceListAdapter extends BaseAdapter {


  private ArrayList<Beacon> beacons;
  private LayoutInflater inflater;


  public LeDeviceListAdapter(Context context) {
    this.inflater = LayoutInflater.from(context);
    this.beacons = new ArrayList<Beacon>();
  }


  public void replaceWith(Collection<Beacon> newBeacons) {
    this.beacons.clear();
    this.beacons.addAll(newBeacons);
    Collections.sort(beacons, new Comparator<Beacon>() {
      @Override
      public int compare(Beacon lhs, Beacon rhs) {
        return (int) Math.signum(Utils.computeAccuracy(lhs) - Utils.computeAccuracy(rhs));
      }
    });
    notifyDataSetChanged();
  }


  @Override
  public int getCount() {
    return beacons.size();
  }


  @Override
  public Beacon getItem(int position) {
    return beacons.get(position);
  }


  @Override
  public long getItemId(int position) {
    return position;
  }


  @Override
  public View getView(int position, View view, ViewGroup parent) {
    view = inflateIfRequired(view, position, parent);
    bind(getItem(position), view);
    return view;
  }


  private void bind(Beacon beacon, View view) {
    ViewHolder holder = (ViewHolder) view.getTag();
    holder.macTextView.setText(String.format("MAC: %s (%.2fm)", beacon.getMacAddress(), Utils.computeAccuracy(beacon)));
    
    //holder.rssiTextView.setText("NAME " + beacon.getRssi());
    //holder.measuredPowerTextView.setText("Name: " + beacon.getName());
    holder.majorTextView.setText("Major: " + beacon.getMajor());
    holder.minorTextView.setText("Minor: " + beacon.getMinor());
    holder.measuredPowerTextView.setText("MPower: " + beacon.getMeasuredPower());
    holder.rssiTextView.setText("RSSI: " + beacon.getRssi());
    
    
    Log.e("NAME ",""+beacon.getName() +" Major "+beacon.getMajor());
  }


  private View inflateIfRequired(View view, int position, ViewGroup parent) {
    if (view == null) {
      view = inflater.inflate(R.layout.device_item, null);
      view.setTag(new ViewHolder(view));
    }
    return view;
  }


  static class ViewHolder {
    final TextView macTextView;
    final TextView majorTextView;
    final TextView minorTextView;
    final TextView measuredPowerTextView;
    final TextView rssiTextView;


    ViewHolder(View view) {
      macTextView = (TextView) view.findViewWithTag("mac");
      majorTextView = (TextView) view.findViewWithTag("major");
      minorTextView = (TextView) view.findViewWithTag("minor");
      measuredPowerTextView = (TextView) view.findViewWithTag("mpower");
      rssiTextView = (TextView) view.findViewWithTag("rssi");
    }
  }
}
