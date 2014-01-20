/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appbase.ble;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.ParcelUuid;
import android.renderscript.Byte2;
import android.util.Log;

/**
 * Service for managing connection and data communication with a GATT server
 * hosted on a given Bluetooth LE device.
 */
public class BluetoothLeService {
	private final static String TAG = BluetoothLeService.class.getSimpleName();

	private BluetoothManager mBluetoothManager;
	private BluetoothAdapter mBluetoothAdapter;
	private String mBluetoothDeviceAddress;
	private BluetoothGatt mBluetoothGatt;
	private int mConnectionState = STATE_DISCONNECTED;

	private static final int STATE_DISCONNECTED = 0;
	private static final int STATE_CONNECTING = 1;
	private static final int STATE_CONNECTED = 2;

	public final static String ACTION_GATT_CONNECTED = "com.appbase.bluetooth.le.ACTION_GATT_CONNECTED";
	public final static String ACTION_GATT_DISCONNECTED = "com.appbase.bluetooth.le.ACTION_GATT_DISCONNECTED";
	public final static String ACTION_GATT_SERVICES_DISCOVERED = "com.appbase.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED";
	public final static String ACTION_DATA_AVAILABLE = "com.appbase.bluetooth.le.ACTION_DATA_AVAILABLE";
	public final static String EXTRA_DATA = "com.appbase.bluetooth.le.EXTRA_DATA";

	public final static UUID UUID_HEART_RATE_MEASUREMENT = UUID
			.fromString(SampleGattAttributes.HEART_RATE_MEASUREMENT);

	public final static UUID minoruid_characteristics = UUID
			.fromString("B9403002-F5F8-466E-AFF9-25556B57FE6D");
	public final static UUID majoruid_characteristics = UUID
			.fromString("B9403001-F5F8-466E-AFF9-25556B57FE6D");
	
	
	public final static UUID SERVICE_ID_NAME = UUID
			.fromString("00002a00-0000-1000-8000-00805f9b34fb");
	public final static UUID SERVICE_ID_MAJOR_MINOR	=UUID
			.fromString("B9403000-F5F8-466E-AFF9-25556B57FE6D");
	
	
	

	private Context mContext;

	HashMap<UUID, String> mHashMap = new HashMap<UUID, String>();
	
	
	BluetoothGattService MAJOR_MINOR_SERVICE;

	// Implements callback methods for GATT events that the app cares about. For
	// example,
	// connection change and services discovered.
	private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
		@Override
		public void onConnectionStateChange(BluetoothGatt gatt, int status,
				int newState) {
			String intentAction;
			if (newState == BluetoothProfile.STATE_CONNECTED) {
				intentAction = ACTION_GATT_CONNECTED;
				mConnectionState = STATE_CONNECTED;
				// broadcastUpdate(intentAction);
				Log.i(TAG, "Connected to GATT server.");
				// Attempts to discover services after successful connection.
				Log.i(TAG, "Attempting to start service discovery:"
						+ mBluetoothGatt.discoverServices());

			} else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
				intentAction = ACTION_GATT_DISCONNECTED;
				mConnectionState = STATE_DISCONNECTED;
				Log.i(TAG, "Disconnected from GATT server.");
				//mBluetoothGatt.connect();
				// broadcastUpdate(intentAction);
			}
		}

		@Override
		public void onServicesDiscovered(final BluetoothGatt gatt, int status) {
			if (status == BluetoothGatt.GATT_SUCCESS) {
				mBluetoothGatt = gatt;

				final List<BluetoothGattService> mBluetoothGattServices = gatt
						.getServices();

				Thread mThread = new Thread() {
					public void run() {
						for (BluetoothGattService mBluetoothGattService : mBluetoothGattServices) {
							
					/*		
							
						if(mBluetoothGattService.getUuid().equals(SERVICE_ID_MAJOR_MINOR)){
							MAJOR_MINOR_SERVICE	=	mBluetoothGattService;
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							BluetoothGattCharacteristic major	=	mBluetoothGattService.getCharacteristic(majoruid_characteristics);
							gatt.readCharacteristic(major);
							//Thread.sleep(500);
							//byte[] test	=	minor.getValue();
							
							
							//write(test, major);
							
							Log.e("Done ","Done");
							
						}else{
							
							
							if(mBluetoothGattService.getCharacteristic(SERVICE_ID_NAME)!=null){
								
								BluetoothGattCharacteristic name	=	mBluetoothGattService.getCharacteristic(SERVICE_ID_NAME);
								gatt.readCharacteristic(name);
								
							}
						
							*/
							
					
							List<BluetoothGattCharacteristic> mBluetoothGattCharacteristics = mBluetoothGattService
							.getCharacteristics();
							
							for (BluetoothGattCharacteristic mBluetoothGattCharacteristic : mBluetoothGattCharacteristics) {
						
								try{
									
									Thread.sleep(300);
								}catch(Exception e){
									
								}

								
								Log.e("UUID ", "" +mBluetoothGattCharacteristic.getUuid() + " "+mBluetoothGattCharacteristic.getProperties() + " "+mBluetoothGattCharacteristic.getPermissions());

								gatt.readCharacteristic(mBluetoothGattCharacteristic);

							}

						
					
							
					
						
		
						}
					
					}
				};

				mThread.start();

			} else {
				Log.w(TAG, "onServicesDiscovered received: " + status);
			}
		}

		@Override
		public void onCharacteristicRead(BluetoothGatt gatt,
				BluetoothGattCharacteristic characteristic, int status) {
			if (status == BluetoothGatt.GATT_SUCCESS) {
				// broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);

				try {
					// For all other profiles, writes the data formatted in HEX.
					final byte[] data = characteristic.getValue();
					
					
					//BluetoothGattCharacteristic minor	=	MAJOR_MINOR_SERVICE.getCharacteristic(minoruid_characteristics);
					
					//write(data,minor);

					if (data != null && data.length > 0) {

						Log.e("new data", "" + new String(data) +" "+characteristic.getUuid() + " "+characteristic.getProperties() + " "+characteristic.getPermissions());

						 // if you want
																// little-endian
//						int result = 0;
//						try{
//							ByteBuffer buffer = ByteBuffer.wrap(data);
//							//buffer.order(ByteOrder.LITTLE_ENDIAN);
//						result 	=	buffer.getShort();
//						Log.e("result ", " " + result);
//
//						}catch(Exception e){
//							e.printStackTrace();
//						}
						
						
					
						final StringBuilder stringBuilder = new StringBuilder(
								data.length);
						String number = "";
						for (byte byteChar : data)
							number	=String.format("%02X",
									byteChar)+number;
							//stringBuilder.append(String.format("%02X",
									//byteChar));

						
						int value = Integer.parseInt(number, 16); 
						Log.e("Data ", " " + number+" value" +value );
						
						
				

					}

				} catch (Exception e) {
					e.printStackTrace();

				}

			}
		}

		@Override
		public void onCharacteristicChanged(BluetoothGatt gatt,
				BluetoothGattCharacteristic characteristic) {
			//broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
		}

		@Override
		public void onCharacteristicWrite(BluetoothGatt gatt,
				BluetoothGattCharacteristic characteristic, int status) {

			Log.e("Write ", "Write" + status);

		}
	};

	private void write(byte[] data,
			BluetoothGattCharacteristic mBluetoothGattCharacteristic) {

		
		mBluetoothGattCharacteristic.setValue(30,BluetoothGattCharacteristic.FORMAT_UINT16,0);
		
		//mBluetoothGattCharacteristic.setValue(data);		
		mBluetoothGatt.writeCharacteristic(mBluetoothGattCharacteristic);
		
		Log.e("Writing has been done ","Done");

	}

	private void broadcastUpdate(final String action,
			final BluetoothGattCharacteristic characteristic) {

		try {
			// For all other profiles, writes the data formatted in HEX.
			final byte[] data = characteristic.getValue();

			// write(data);

			if (data != null && data.length > 0) {

				ByteBuffer buffer = ByteBuffer.wrap(data);
				buffer.order(ByteOrder.LITTLE_ENDIAN); // if you want
														// little-endian
				int result = buffer.getShort();

				Log.e("result ", " " + result);

				final StringBuilder stringBuilder = new StringBuilder(
						data.length);
				for (byte byteChar : data)
					stringBuilder.append(String.format("%02X ", byteChar));

				Log.e("Date ", " " + stringBuilder.toString());

			}

		} catch (Exception e) {
			e.printStackTrace();

		}

		// sendBroadcast(intent);
	}

	/**
	 * Initializes a reference to the local Bluetooth adapter.
	 * 
	 * @return Return true if the initialization is successful.
	 */
	public boolean initialize(Context mContext) {

		this.mContext = mContext;
		// For API level 18 and above, get a reference to BluetoothAdapter
		// through
		// BluetoothManager.
		if (mBluetoothManager == null) {
			mBluetoothManager = (BluetoothManager) mContext
					.getSystemService(Context.BLUETOOTH_SERVICE);
			if (mBluetoothManager == null) {
				Log.e(TAG, "Unable to initialize BluetoothManager.");
				return false;
			}
		}

		mBluetoothAdapter = mBluetoothManager.getAdapter();
		if (mBluetoothAdapter == null) {
			Log.e(TAG, "Unable to obtain a BluetoothAdapter.");
			return false;
		}

		return true;
	}

	/**
	 * Connects to the GATT server hosted on the Bluetooth LE device.
	 * 
	 * @param address
	 *            The device address of the destination device.
	 * 
	 * @return Return true if the connection is initiated successfully. The
	 *         connection result is reported asynchronously through the
	 *         {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
	 *         callback.
	 */
	public boolean connect(Context mContext, final String address) {

		mHashMap.put(majoruid_characteristics, "B9403001-F5F8-466E-AFF9-25556B57FE6D");// Major
		mHashMap.put(minoruid_characteristics, "B9403002-F5F8-466E-AFF9-25556B57FE6D");// Minor
		mHashMap.put(SERVICE_ID_NAME, "B9403002-F5F8-466E-AFF9-25556B57FE6D");
		mHashMap.put(SERVICE_ID_MAJOR_MINOR, "B9403000-F5F8-466E-AFF9-25556B57FE6D");
		initialize(mContext);

		if (mBluetoothAdapter == null || address == null) {
			Log.w(TAG,
					"BluetoothAdapter not initialized or unspecified address.");
			return false;
		}

		// Previously connected device. Try to reconnect.
		if (mBluetoothDeviceAddress != null
				&& address.equals(mBluetoothDeviceAddress)
				&& mBluetoothGatt != null) {
			Log.d(TAG,
					"Trying to use an existing mBluetoothGatt for connection.");
			if (mBluetoothGatt.connect()) {
				mConnectionState = STATE_CONNECTING;
				return true;
			} else {
				return false;
			}
		}

		final BluetoothDevice device = mBluetoothAdapter
				.getRemoteDevice(address);
		if (device == null) {
			Log.w(TAG, "Device not found.  Unable to connect.");
			return false;
		} else {

			Log.e("Device Found", "Device Found");
		}
		// We want to directly connect to the device, so we are setting the
		// autoConnect
		// parameter to false.
		mBluetoothGatt = device.connectGatt(mContext, false, mGattCallback);
		Log.d(TAG, "Trying to create a new connection.");
		mBluetoothDeviceAddress = address;
		mConnectionState = STATE_CONNECTING;
		return true;
	}

	/**
	 * Disconnects an existing connection or cancel a pending connection. The
	 * disconnection result is reported asynchronously through the
	 * {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
	 * callback.
	 */
	public void disconnect() {
		if (mBluetoothAdapter == null || mBluetoothGatt == null) {
			Log.w(TAG, "BluetoothAdapter not initialized");
			return;
		}
		mBluetoothGatt.disconnect();
	}

	/**
	 * After using a given BLE device, the app must call this method to ensure
	 * resources are released properly.
	 */
	public void close() {
		if (mBluetoothGatt == null) {
			return;
		}
		mBluetoothGatt.close();
		mBluetoothGatt = null;
	}

	/**
	 * Request a read on a given {@code BluetoothGattCharacteristic}. The read
	 * result is reported asynchronously through the
	 * {@code BluetoothGattCallback#onCharacteristicRead(android.bluetooth.BluetoothGatt, android.bluetooth.BluetoothGattCharacteristic, int)}
	 * callback.
	 * 
	 * @param characteristic
	 *            The characteristic to read from.
	 */
	public void readCharacteristic(BluetoothGattCharacteristic characteristic) {
		if (mBluetoothAdapter == null || mBluetoothGatt == null) {
			Log.w(TAG, "BluetoothAdapter not initialized");
			return;
		}
		mBluetoothGatt.readCharacteristic(characteristic);
	}

	/**
	 * Enables or disables notification on a give characteristic.
	 * 
	 * @param characteristic
	 *            Characteristic to act on.
	 * @param enabled
	 *            If true, enable notification. False otherwise.
	 */
	public void setCharacteristicNotification(
			BluetoothGattCharacteristic characteristic, boolean enabled) {
		if (mBluetoothAdapter == null || mBluetoothGatt == null) {
			Log.w(TAG, "BluetoothAdapter not initialized");
			return;
		}
		mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);

		// This is specific to Heart Rate Measurement.
		if (UUID_HEART_RATE_MEASUREMENT.equals(characteristic.getUuid())) {
			BluetoothGattDescriptor descriptor = characteristic
					.getDescriptor(UUID
							.fromString(SampleGattAttributes.CLIENT_CHARACTERISTIC_CONFIG));
			descriptor
					.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
			mBluetoothGatt.writeDescriptor(descriptor);
		}
	}

	/**
	 * Retrieves a list of supported GATT services on the connected device. This
	 * should be invoked only after {@code BluetoothGatt#discoverServices()}
	 * completes successfully.
	 * 
	 * @return A {@code List} of supported services.
	 */
	public List<BluetoothGattService> getSupportedGattServices() {
		if (mBluetoothGatt == null)
			return null;

		return mBluetoothGatt.getServices();
	}
}
