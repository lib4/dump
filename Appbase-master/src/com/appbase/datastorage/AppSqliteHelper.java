package com.appbase.datastorage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AppSqliteHelper extends SQLiteOpenHelper {

	public static final String TABLE_PROFILE = "profile";
	public static final String TABLE_LIVE_ORDERS = "liveorders";
	public static final String TABLE_CATALOGS = "catalogs";
	public static final String TABLE_BUSINESS = "business";
	
	private static final String DATABASE_NAME = "airoffers_merchant.db";
	
	
	private static final int DATABASE_VERSION = 1;

	public static final String COLUMN_ID = "id";
	public static final String COLUMN_V = "v";
	public static final String COLUMN_BUSINESS = "business";
	public static final String COLUMN_CREATED = "created";
	public static final String COLUMN_EMAIL = "email";
	public static final String COLUMN_FNAME = "fname";
	public static final String COLUMN_LASTUPDATED = "lastUpdated";
	public static final String COLUMN_LNAME = "lname";
	public static final String COLUMN_PHONE = "phone";
	public static final String COLUMN_TYPE = "type";
	public static final String COLUMN_BACKGROUNDSCAN = "backgroundScan";
	public static final String COLUMN_GUEST = "guest";
	public static final String COLUMN_FULLNAME = "fullname";
	public static final String COLUMN__ID = "_id";
	public static final String COLUMN_ACCESSTOKEN = "accessToken";

	public static final String COLUMN_AMOUNT = "amount";
	public static final String COLUMN_TAX = "tax";
	public static final String COLUMN_CONSUMEREMAIL = "consumer_email";
	public static final String COLUMN_DEVICE_ID = "consumer_device_id";
	public static final String COLUMN_RATED = "rated";
	public static final String COLUMN_FEECOLLECTED = "feeCollected";

	public static final String COLUMN_STATUS = "status";
	public static final String COLUMN_ITEMS = "items";
	public static final String COLUMN_CONSUMER_BUSINESS = "consumer_business";
	public static final String COLUMN_CONSUMER__ID = "consumer__id";
	public static final String COLUMN_CONSUMER_V = "consumer_v";
	public static final String COLUMN_IMAGE = "image";
	public static final String COLUMN_THUMB = "thumbnail";
	
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_GROUPS = "groups";
	
	
	
	
	public static final String COLUMN_BUSINESS_ID	=	"businessID";
	public static final String COLUMN_CITY	=	"city";
	public static final String COLUMN_COUNTRY=	"country";
	public static final String COLUMN_LAST_UPDATED	=	"lastUpdated";
	public static final String COLUMN_LOGO	=	"logo";
	public static final String COLUMN_POSTAL_CODE	=	"postalCode";
	public static final String COLUMN_PROXIMITY_ID	=	"proximityID";
	public static final String COLUMN_SENSOR_BATCH	=	"sensorBatch";
	public static final String COLUMN_STATE	=	"state";
	public static final String COLUMN_STREET_ADDRESS=	"streetAddress";
	public static final String COLUMN_STRIPE_RECEIPIENT_ID	=	"stripeRecipientID";
	public static final String COLUMN_TAX_PERCENT	=	"taxPercent";
	public static final String COLUMN_SENSORS	=	"sensors";
	
	public static final String COLUMN_BALANCE	=	"balance";
	public static final String COLUMN_VERIFIED	=	"verified";
	public static final String COLUMN_RATING_SUM	=	"ratingsSum";
	public static final String COLUMN_RATING_COUNT	=	"ratingsCount";
	public static final String COLUMN_GEO	=	"geo";
	
	public static final String COLUMN_SENSOR_ID	=	"sensorID";
	
	
	public static final String COLUMN_ADVERTISING_INTERVAL	=	"advertisingInterval";
	public static final String COLUMN_TX_POWER	=	"txPower";
	
	

	// Database creation sql statement
	private static final String CREATE_PROFILE_TABLE = "create table "
			+ TABLE_PROFILE + " "
			+ "(auto_id integer primary key autoincrement," + COLUMN_ID
			+ " text not null," + COLUMN_V + " integer," + ""
			+ COLUMN_ACCESSTOKEN + " text not null," + "" + COLUMN_BUSINESS
			+ " text not null," + COLUMN_CREATED + " text not null,"
			+ COLUMN_EMAIL + " text not null," + "" + COLUMN_FNAME
			+ " text not null," + "" + COLUMN_LASTUPDATED + " text not null,"
			+ COLUMN_LNAME + " text not null," + COLUMN_PHONE
			+ " text," + COLUMN_TYPE + " text not null," + ""
			+ COLUMN_BACKGROUNDSCAN + " boolean not null," + COLUMN_GUEST
			+ " boolean not null," + COLUMN_FULLNAME + " text not null,"
			+ COLUMN__ID + " text not null);";

	private static final String CREATE_LIVEORDERS_TABLE = "create table "
			+ TABLE_LIVE_ORDERS
			+ " (auto_id integer primary key autoincrement,"
			+ COLUMN_LASTUPDATED + " text," + COLUMN_CREATED
			+ " text," + "" + COLUMN_AMOUNT + " FLOAT not null,"
			+ COLUMN_TAX + " FLOAT not null, " + COLUMN_CONSUMEREMAIL
			+ " text , " + COLUMN_DEVICE_ID + " text default null," + ""
			+ COLUMN_TYPE + " text , " + COLUMN_CONSUMER__ID
			+ " text , " + COLUMN_CONSUMER_V + " integer , "
			+COLUMN_FNAME+" text, "+COLUMN_LNAME +" text, "+COLUMN_FULLNAME+" text, "
			+COLUMN_IMAGE+" text, "+COLUMN_THUMB	+" text, "	
			+ COLUMN_BACKGROUNDSCAN + " boolean not null," + "" + COLUMN_GUEST
			+ " boolean not null, " + COLUMN_ID + " text not null,"
			+ COLUMN_BUSINESS + " text not null," + COLUMN__ID
			+ " text not null, " + COLUMN_V + " integer not null, "
			+ COLUMN_RATED + " boolean not null, " + "" + COLUMN_FEECOLLECTED
			+ " boolean not null," + COLUMN_STATUS + " text not null, "
			+ COLUMN_ITEMS + " text not null);";
	

	private static final String CREATE_CATALOG_TABLE = "create table "
			+ TABLE_CATALOGS
			+ " (auto_id integer primary key autoincrement,"
			+ COLUMN_NAME + " text not null," + COLUMN_TYPE
			+ " text not null," + "" + COLUMN__ID + " text not null,"
			+ COLUMN_STATUS + " TEXT not null, " + COLUMN_GROUPS
			+ " text not null);";

	
	
	private static final String CREATE_BUSINESS_TABLE = "create table "
			+ TABLE_BUSINESS
			+ " (auto_id integer primary key autoincrement,"
			+ COLUMN_V + " int not null," + COLUMN__ID
			+ " text not null," + "" + COLUMN_BUSINESS_ID + " text not null,"
			+ COLUMN_COUNTRY + " TEXT not null, " + COLUMN_CREATED
			+ " text not null,"
			+ COLUMN_LAST_UPDATED + " TEXT not null, " 
			+ COLUMN_LOGO + " TEXT not null, " 
			+ COLUMN_NAME + " TEXT not null, "
			+ COLUMN_POSTAL_CODE + " TEXT not null,"
				+ COLUMN_PROXIMITY_ID + " TEXT not null,"
				+ COLUMN_SENSOR_BATCH + " TEXT not null, "
					+ COLUMN_STATE + " TEXT not null, "
						+ COLUMN_STATE + " TEXT not null, "
							+ COLUMN_STREET_ADDRESS + " TEXT not null, "
								+ COLUMN_STRIPE_RECEIPIENT_ID + " TEXT not null, "
									+ COLUMN_TAX_PERCENT + " int not null, "
										+ COLUMN_SENSORS + " TEXT not null, "
											+ COLUMN_BALANCE + " flot not null, "
												+ COLUMN_VERIFIED + " boolean not null, "
													+ COLUMN_RATING_SUM + " TEXT not null, "
														+ COLUMN_RATING_COUNT + " TEXT not null, "
															+ COLUMN_GEO + " TEXT not null "
															+ COLUMN_SENSOR_ID + " TEXT not null "
															+ COLUMN_ADVERTISING_INTERVAL + " TEXT not null "
															+ COLUMN_TX_POWER + " TEXT not null );";
				
					
					
			
			
		
	
//	COLUMN_CONSUMER_V
//	
//	
//	
//	
//		 status=pending
//		 consumer_email=guest-E6015209-0E76-41C5-A340-216F0B7D5EC0@airoffers.co 
//		 lastUpdated=2013-12-17T09:29:38.430Z  
//		 type=consumer
//		 business=52ac9a8a55b534020000000c
//		 consumer__id=52ac7ed44240b7020000019f
//		 id=52ac7ed44240b7020000019f
//		 amount=11.95
//		 v=0 
//		 _id=52b01982de5091020000001a
//		 tax=1.195
//		 items=[{"image":"http:\/\/i.imgur.com\/vTOtpTG.jpg","price":11.949999809265137,"_id":"52b01982de5091020000001b","choices":[],"name":"Mahi-Mahi Sandwich"}] 
//				 created=2013-12-17T09:29:38.430Z
//				 guest=true
//				 backgroundScan=false
//				 rated=false 
//				 feeCollected=false
//				 consumer_device_id=E6015209-0E76-41C5-A340-216F0B7D5EC0

	
	public AppSqliteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(CREATE_PROFILE_TABLE);
		database.execSQL(CREATE_LIVEORDERS_TABLE);
		database.execSQL(CREATE_CATALOG_TABLE);
		//database.execSQL(CREATE_BUSINESS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(AppSqliteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILE);
		onCreate(db);
	}
}
