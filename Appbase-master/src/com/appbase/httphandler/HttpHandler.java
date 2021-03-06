package com.appbase.httphandler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.appbase.activities.BaseActivity;
import com.appbase.datahandler.GetBusinessParser;
import com.appbase.datahandler.GetMenusParser;
import com.appbase.datahandler.LiveOrderParser;
import com.appbase.datahandler.SignInParser;
import com.appbase.gcm.GCM_Constants;
import com.appbase.network.Connection;
import com.appbase.utils.Utils;
import com.estimote.sdk.Beacon;

public class HttpHandler extends Thread {

	public static String BASE_URL = "https://airoffers.herokuapp.com";
	public static String SIGN_IN_URL = BASE_URL + "/account/signin";
	public static String LIVE_ORDER_URL = BASE_URL + "/live/orders";
	public static String LIVE_ORDER_ACKNOWLEDGE_URL = "acknowledge";
	public static String LIVE_ORDER_CONFIRM_URL = "confirm";
	public static String LIVE_ORDER_CANCEL_URL = "cancel";

	public static String GET_MENUS = BASE_URL + "/catalogs";
	public static String LOGUT_URL = BASE_URL + "/account/signout";
	public static String BUSINESS_URL = BASE_URL + "/settings/business";
	public static String SENSORS_URL = BASE_URL + "/sensors/active";
	public static String ARCHIVE_SENSORS_URL = BASE_URL + "/sensor/";
	public static String DEVICE_URL = BASE_URL + "/account/device";

	public static final String HTTP_POST = "POST";
	public static final String HTTP_GET = "GET";
	public static final String HTTP_DELETE = "DELETE";
	private int REQUEST_API_CODE = 0;
	final int SIGNIN_API_CODE = 1;
	final int LIVE_ORDER_API_CODE = SIGNIN_API_CODE + 1;
	final int GET_MENUS_API_CODE = LIVE_ORDER_API_CODE + 1;
	final int GET_LIVE_ORDER_ACTION_API_CODE = GET_MENUS_API_CODE + 1;
	final int LOGOUT_API_CODE = GET_LIVE_ORDER_ACTION_API_CODE + 1;
	final int BUSINESS_API_CODE = LOGOUT_API_CODE + 1;
	final int GET_SENSORS_API_CODE = BUSINESS_API_CODE + 1;
	final int DELETE_SENSORS_API_CODE = GET_SENSORS_API_CODE + 1;
	final int DEVICE_TOKEN_API_CODE = DELETE_SENSORS_API_CODE + 1;
	public static final int NO_NETWORK_CODE = 999;
	public static final int DEFAULT_CODE = 1;
	String URL;
	String requestBody;
	Context context;
	String requestType;
	HTTPResponseListener mHttpResponseListener;

	/**
	 * 
	 * 
	 * Function calls the ServerConnection gateway once the response is received
	 * Response will sent to appropriate response handled method. which in turn
	 * stores the data in to RecordStore.
	 */
	public void getLiveOrders(Context context,
			HTTPResponseListener mHttpResponseListener) {

		URL = LIVE_ORDER_URL;
		this.context = context;
		this.mHttpResponseListener = mHttpResponseListener;
		REQUEST_API_CODE = LIVE_ORDER_API_CODE;
		requestType = HTTP_GET;
		start();
	}

	/**
	 * @param userName
	 *            // Username entered by the user
	 * @param password
	 *            //password entered by the user
	 * 
	 *            Function calls the ServerConnection gateway once the response
	 *            is received Response will sent to appropriate response handled
	 *            method. which in turn stores the data in to RecordStore.
	 */
	public void doSignIn(String emailAddress, String password, Context context,
			HTTPResponseListener mHttpResponseListener) {

		URL = SIGN_IN_URL;

		/**
		 * Forming the SignIn Json Request.
		 */
		try {

			JSONObject signInReqObject = new JSONObject();
			signInReqObject.put(HttpConstants.EMAIL_JKEY, emailAddress);
			signInReqObject.put(HttpConstants.PASSWORD_JKEY, password);
			Utils.USERNAME = emailAddress;
			Utils.PASSWORD = password;
			signInReqObject.put(HttpConstants.TYPE_JKEY, "merchant");
			requestBody = signInReqObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		this.context = context;
		this.mHttpResponseListener = mHttpResponseListener;
		REQUEST_API_CODE = SIGNIN_API_CODE;
		requestType = HTTP_POST;

		start();
	}

	/**
	 * 
	 * 
	 * Function calls the ServerConnection gateway once the response is received
	 * Response will sent to appropriate response handled method. which in turn
	 * stores the data in to RecordStore.
	 */
	public void getMenus(Context context,
			HTTPResponseListener mHttpResponseListener) {

		URL = GET_MENUS;
		this.context = context;
		this.mHttpResponseListener = mHttpResponseListener;
		REQUEST_API_CODE = GET_MENUS_API_CODE;
		requestType = HTTP_GET;
		start();
	}

	/**
	 * 
	 * 
	 * Function calls the ServerConnection gateway once the response is received
	 * Response will sent to appropriate response handled method. which in turn
	 * stores the data in to RecordStore.
	 */
	public void liveOrderAction(Context context,
			HTTPResponseListener mHttpResponseListener, String id, String action) {

		URL = LIVE_ORDER_URL + "/" + id + "/" + action;
		this.context = context;
		this.mHttpResponseListener = mHttpResponseListener;
		REQUEST_API_CODE = GET_LIVE_ORDER_ACTION_API_CODE;
		requestType = HTTP_GET;
		start();
	}

	/**
	 * 
	 * BUSINESS Function calls the ServerConnection gateway once the response is
	 * received Response will sent to appropriate response handled method. which
	 * in turn stores the data in to RecordStore.
	 */
	public void getBusiness(Context context,
			HTTPResponseListener mHttpResponseListener) {

		URL = BUSINESS_URL;
		this.context = context;
		this.mHttpResponseListener = mHttpResponseListener;
		REQUEST_API_CODE = BUSINESS_API_CODE;
		requestType = HTTP_GET;
		start();
	}

	/**
	 * 
	 * SENSORS Function calls the ServerConnection gateway once the response is
	 * received Response will sent to appropriate response handled method. which
	 * in turn stores the data in to RecordStore.
	 */
	public void getSensors(Context context,
			HTTPResponseListener mHttpResponseListener, List<Beacon> mBeacons) {

		URL = SENSORS_URL;
		this.context = context;
		this.mHttpResponseListener = mHttpResponseListener;
		Iterator mIterator = mBeacons.iterator();
		JSONArray sensJsonArray = new JSONArray();
		while (mIterator.hasNext()) {

			Beacon mBeacon = (Beacon) mIterator.next();
			JSONObject sensorObject = new JSONObject();
			try {
				sensorObject.put(HttpConstants.PROXIMITY_ID_JKEY,
						mBeacon.getProximityUUID());
				sensorObject.put("major", mBeacon.getMajor());
				sensorObject.put("minor", mBeacon.getMinor());
				sensJsonArray.put(sensorObject);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		JSONObject requestObject = new JSONObject();
		try {

			requestObject.put("sensors", sensJsonArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
		requestBody = requestObject.toString();

		REQUEST_API_CODE = GET_SENSORS_API_CODE;
		requestType = HTTP_POST;
		start();
	}

	/**
	 * 
	 * Archive Sensor Function calls the ServerConnection gateway once the
	 * response is received Response will sent to appropriate response handled
	 * method. which in turn stores the data in to RecordStore.
	 */
	public void archiveSensor(String sensorId, Context context,
			HTTPResponseListener mHttpResponseListener) {

		URL = ARCHIVE_SENSORS_URL + sensorId;
		this.context = context;
		this.mHttpResponseListener = mHttpResponseListener;
		REQUEST_API_CODE = DELETE_SENSORS_API_CODE;
		requestType = HTTP_DELETE;
		start();
	}

	/**
	 * 
	 * SIGNOUT Function calls the ServerConnection gateway once the response is
	 * received Response will sent to appropriate response handled method. which
	 * in turn stores the data in to RecordStore.
	 */
	public void sendDeviceToken(String deviceToken, Context context,
			HTTPResponseListener mHttpResponseListener) {

		URL = DEVICE_URL;
		this.context = context;

		try {
			JSONObject signInReqObject = new JSONObject();
			signInReqObject.put(HttpConstants.DEVICE_TOKEN, deviceToken);
			signInReqObject.put(HttpConstants.TYPE, "android");
			requestBody = signInReqObject.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.mHttpResponseListener = mHttpResponseListener;
		REQUEST_API_CODE = DEVICE_TOKEN_API_CODE;
		requestType = HTTP_POST;
		Log.e("Start on Archive ", "Start on archive");
		start();
	}

	/**
	 * 
	 * SIGNOUT Function calls the ServerConnection gateway once the response is
	 * received Response will sent to appropriate response handled method. which
	 * in turn stores the data in to RecordStore.
	 */
	public void signOut(Context context) {

		URL = LOGUT_URL;
		this.context = context;
		
		try {
			
			final SharedPreferences prefs = context.getSharedPreferences(BaseActivity.class.getSimpleName(),
					Context.MODE_PRIVATE);
			
			
			
			String registrationId = prefs.getString(GCM_Constants.PROPERTY_REG_ID,
					"");
			JSONObject signInReqObject = new JSONObject();
			signInReqObject.put(HttpConstants.DEVICE_TOKEN, registrationId);
			signInReqObject.put(HttpConstants.TYPE, "android");
			requestBody = signInReqObject.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.mHttpResponseListener = mHttpResponseListener;
		REQUEST_API_CODE = LOGOUT_API_CODE;
		requestType = HTTP_POST;
		start();
	}

	/**
	 * Method Responsible for the following:- 1 Initiating Server Connection 2
	 * Initiating the Response Handling ie passing the response to appropriate
	 * parser class. 3.Trigger the UI events
	 */
	public void run() {

		if (!isNetworkOnline()) {
			mHttpResponseListener.onFailure(NO_NETWORK_CODE);
			return;
		}
		try {

			Connection mConnection = new Connection();
			try {
				mConnection.connect(URL, requestBody, requestType);
			} catch (Exception e) {
				// Exception will be handled based on response code obtained.
				mHttpResponseListener.onFailure(DEFAULT_CODE);
				return;
			}
			switch (REQUEST_API_CODE) {

			case SIGNIN_API_CODE:
				switch (mConnection.responseCode) {
				case 200:
					SignInParser mSignInParser = new SignInParser(
							mConnection.responseStream, context);
					mHttpResponseListener.onSuccess();

					break;

				default:

					// mHttpResponseListener.onSuccess();
					mHttpResponseListener.onFailure(DEFAULT_CODE);
					break;
				}
				break;

			case LIVE_ORDER_API_CODE:
				switch (mConnection.responseCode) {
				case 200:

					LiveOrderParser mLiveOrderParser = new LiveOrderParser(
							mConnection.responseStream, context);
					mHttpResponseListener.onSuccess();

					break;

				default:
					mHttpResponseListener.onFailure(DEFAULT_CODE);
					break;
				}
				break;

			case GET_MENUS_API_CODE:
				switch (mConnection.responseCode) {
				case 200:

					GetMenusParser mGetMenusParser = new GetMenusParser(
							mConnection.responseStream, context);

					mHttpResponseListener.onSuccess();

					break;

				default:
					mHttpResponseListener.onFailure(DEFAULT_CODE);
					break;
				}
				break;

			case BUSINESS_API_CODE:
				switch (mConnection.responseCode) {
				case 200:

					GetBusinessParser mGetBusinessParser = new GetBusinessParser(
							mConnection.responseStream, context);

					if(mHttpResponseListener!=null)
						mHttpResponseListener.onSuccess();

					break;

				default:
					if(mHttpResponseListener!=null)
						mHttpResponseListener.onFailure(DEFAULT_CODE);
					break;
				}
				break;

			case GET_LIVE_ORDER_ACTION_API_CODE:
			case GET_SENSORS_API_CODE:
				switch (mConnection.responseCode) {
				default:
					InputStreamReader is = new InputStreamReader(
							mConnection.responseStream);
					StringBuilder sb = new StringBuilder();
					BufferedReader br = new BufferedReader(is);
					String read = br.readLine();

					while (read != null) {
						// System.out.println(read);
						sb.append(read);
						read = br.readLine();

					}

					System.out.println(sb.toString());
					mHttpResponseListener.onSuccess();
					break;
				}
				break;

			case DELETE_SENSORS_API_CODE:
				switch (mConnection.responseCode) {
				case 200:
					mHttpResponseListener.onFailure(Utils.DELETE_SENSOR);
					break;

				default:
					mHttpResponseListener.onFailure(DEFAULT_CODE);
					break;
				}
				break;

			case DEVICE_TOKEN_API_CODE:
				switch (mConnection.responseCode) {
				case 200:
					// mHttpResponseListener.onFailure(Utils.DELETE_SENSOR);

					InputStreamReader is = new InputStreamReader(
							mConnection.responseStream);
					StringBuilder sb = new StringBuilder();
					BufferedReader br = new BufferedReader(is);
					String read = br.readLine();

					while (read != null) {

						sb.append(read);
						read = br.readLine();

					}

					System.out.println(sb.toString());

					break;

				default:
					// mHttpResponseListener.onFailure(DEFAULT_CODE);
					break;
				}
				break;

			default:
				mHttpResponseListener.onFailure(DEFAULT_CODE);
				break;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// responseResolver(mServerConnection);
	}

	public boolean isNetworkOnline() {
		boolean status = true;
		try {
			ConnectivityManager cm = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netInfo = cm.getNetworkInfo(0);
			if (netInfo != null
					&& netInfo.getState() == NetworkInfo.State.CONNECTED) {
				status = true;
			} else {
				netInfo = cm.getNetworkInfo(1);
				if (netInfo != null
						&& netInfo.getState() == NetworkInfo.State.CONNECTED)
					status = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
		return status;

	}
}
