package com.appbase.ble;

import android.os.Parcel;
import android.os.Parcelable;

public class BeaconParcel implements Parcelable {

	private String proximityId = "B9407F30-F5F8-466E-AFF9-25556B57FE61";
	private String name = "anas";
	private String macAddress = "F9:D5:E7:71:AA:13";
	private int major = 100;
	private int minor = 20;
	private int measuredPower = 100;
	private int rssi = 100;

	public BeaconParcel() {

	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		// TODO Auto-generated method stub
		arg0.writeString(proximityId);
		arg0.writeString(name);

		arg0.writeString(macAddress);

		arg0.writeInt(major);

		arg0.writeInt(minor);

		arg0.writeInt(rssi);

	}

	/** Static field used to regenerate object, individually or as arrays */

	public static final Parcelable.Creator<BeaconParcel> CREATOR = new Parcelable.Creator<BeaconParcel>() {

		public BeaconParcel createFromParcel(Parcel pc) {

			return new BeaconParcel(pc);

		}

		public BeaconParcel[] newArray(int size) {

			return new BeaconParcel[size];

		}

	};

	public BeaconParcel(Parcel pc) {

		proximityId = pc.readString();
		name = pc.readString();
		macAddress = pc.readString();
		major = pc.readInt();
		minor = pc.readInt();
		
		measuredPower = pc.readInt();
		
		rssi = pc.readInt();
		

	}

}
