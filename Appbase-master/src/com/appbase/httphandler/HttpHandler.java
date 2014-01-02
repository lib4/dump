package com.appbase.httphandler;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.appbase.datahandler.GetMenusParser;
import com.appbase.datahandler.LiveOrderParser;
import com.appbase.datahandler.SignInParser;
import com.appbase.network.Connection;
import com.appbase.utils.Utils;

public class HttpHandler extends Thread {

	public static String BASE_URL = "https://airoffers.herokuapp.com";
	public static String SIGN_IN_URL = BASE_URL + "/account/signin";
	public static String LIVE_ORDER_URL = BASE_URL + "/live/orders";
	public static String GET_MENUS = BASE_URL + "/catalogs";

	public static final String HTTP_POST = "POST";
	public static final String HTTP_GET = "GET";
	private int REQUEST_API_CODE = 0;
	final int SIGNIN_API_CODE = 1;
	final int LIVE_ORDER_API_CODE = SIGNIN_API_CODE+1;
	final int GET_MENUS_API_CODE = LIVE_ORDER_API_CODE+1;

	public static final int NO_NETWORK_CODE	=	999;
	public static final int DEFAULT_CODE	=	0;
	String URL;
	String requestBody;
	Context context;
	String requestType;
	HTTPResponseListener mHttpResponseListener;

	/**
	 * 
	 * 
	 *            Function calls the ServerConnection gateway once the response
	 *            is received Response will sent to appropriate response handled
	 *            method. which in turn stores the data in to RecordStore.
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
	 *            Function calls the ServerConnection gateway once the response
	 *            is received Response will sent to appropriate response handled
	 *            method. which in turn stores the data in to RecordStore.
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
	 * Method Responsible for the following:- 1 Initiating Server Connection 2
	 * Initiating the Response Handling ie passing the response to appropriate
	 * parser class. 3.Trigger the UI events
	 */
	public void run() {
		if(!isNetworkOnline()){
			mHttpResponseListener.onFailure(NO_NETWORK_CODE);
			return;
		}
		try {

			Connection mConnection = new Connection();
			try{
			mConnection.connect(URL, requestBody, requestType);
			}catch(Exception e){
				//Exception will be handled based on response code obtained.
			}
			switch (REQUEST_API_CODE) {

			case SIGNIN_API_CODE:
				switch (mConnection.responseCode) {
				case 200:
					SignInParser mSignInParser = new SignInParser(
							mConnection.responseStream, context);
					System.out.println("Success");
					mHttpResponseListener.onSuccess();	

					break;

				default:
					
					mHttpResponseListener.onSuccess();
					//mHttpResponseListener.onFailure(DEFAULT_CODE);
					break;
				}
				break;

			case LIVE_ORDER_API_CODE:
				switch (mConnection.responseCode) {
				case 200:

					LiveOrderParser mLiveOrderParser = new LiveOrderParser(
							mConnection.responseStream, context);
					System.out.println("Success");
					mHttpResponseListener.onSuccess();

					break;

				default:

					break;
				}
				break;
				
				
			case GET_MENUS_API_CODE:
				switch (mConnection.responseCode) {
				case 200:

					GetMenusParser mLiveOrderParser = new GetMenusParser(
							mConnection.responseStream, context);
					System.out.println("Success");
					mHttpResponseListener.onSuccess();

					break;

				default:

					break;
				}
				break;

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// responseResolver(mServerConnection);
	}
	
	public boolean isNetworkOnline() {
	    boolean status=true;
	    try{
	        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	        NetworkInfo netInfo = cm.getNetworkInfo(0);
	        if (netInfo != null && netInfo.getState()==NetworkInfo.State.CONNECTED) {
	            status= true;
	        }else {
	            netInfo = cm.getNetworkInfo(1);
	            if(netInfo!=null && netInfo.getState()==NetworkInfo.State.CONNECTED)
	                status= true;
	        }
	    }catch(Exception e){
	        e.printStackTrace();  
	        return true;
	    }
	    return status;

	    }  
}
