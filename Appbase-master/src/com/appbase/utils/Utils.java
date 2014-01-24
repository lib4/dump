package com.appbase.utils;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.util.Log;


public class Utils {

	public static String USERNAME	=	null;
	public static String PASSWORD	=	null;
	public static String TOKEN		=	null;
	public static String ABOUT_URL	=	"http://airoffers.desk.com";
	public static boolean REFRESH_CATALOGE	=	false;
	public static int REFRESH_INTERVAL	=	1000*30;
	public static int REQUEST_ENABLE_BT	=	100;
	public static int DELETE_SENSOR	=	99;
	public static JSONObject SELECTED_OBJECT;
	public static String BUSINESS_NAME	=	"";
	public static String SENSORS_TEXT	=	"Sensors";
	
	public static String SENSOR_DETAILS_TEXT	=	"Sensor Details";
	public static String CATALOGUE_TEXT	=	"Catalogue";
	public static int ARCHIVE_INDEX			=	-1;
 
	
	
	
	public static boolean isTabletDevice(Context activityContext) {
        boolean device_large = ((activityContext.getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) ==
                Configuration.SCREENLAYOUT_SIZE_LARGE);

        if (device_large) {
            DisplayMetrics metrics = new DisplayMetrics();
            Activity activity = (Activity) activityContext;
            activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

            if (metrics.densityDpi == DisplayMetrics.DENSITY_DEFAULT
                    || metrics.densityDpi == DisplayMetrics.DENSITY_HIGH
                    || metrics.densityDpi == DisplayMetrics.DENSITY_MEDIUM
                    || metrics.densityDpi == DisplayMetrics.DENSITY_TV
                    || metrics.densityDpi == DisplayMetrics.DENSITY_XHIGH) {
                Log.e("DeviceHelper","IsTabletDevice-True");
                return true;
            }
        }
        Log.e("DeviceHelper","IsTabletDevice-False");
        return false;
    }
}
