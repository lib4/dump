package com.appbase.datastorage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AppSqliteHelper extends SQLiteOpenHelper{

	 public static final String TABLE_PROFILE = "profile";
	 private static final String DATABASE_NAME = "airoffers_merchant.db";
	 private static final int DATABASE_VERSION = 1;
	 
	 public static final String COLUMN_ID	=	"id";
	 public static final String COLUMN_V	=	"v";
	 public static final String COLUMN_BUSINESS	=	"business";
	 public static final String COLUMN_CREATED	=	"created";
	 public static final String COLUMN_EMAIL	=	"email";
	 public static final String COLUMN_FNAME	=	"fname";
	 public static final String COLUMN_LASTUPDATED	=	"lastUpdated";
	 public static final String COLUMN_LNAME	=	"lname";
	 public static final String COLUMN_PHONE	=	"phone";
	 public static final String COLUMN_TYPE	=	"type";
	 public static final String COLUMN_BACKGROUNDSCAN	=	"backgroundScan";
	 public static final String COLUMN_GUEST	=	"guest";
	 public static final String COLUMN_FULLNAME	=	"fullname";
	 public static final String COLUMN__ID	=	"_id";
	 public static final String COLUMN_ACCESSTOKEN	=	"accessToken";

	  // Database creation sql statement
	  private static final String DATABASE_CREATE = "create table profile " +
	  		"(auto_id integer primary key autoincrement,"+COLUMN_ID+" text not null,"+COLUMN_V+" integer," +
	  		""+COLUMN_ACCESSTOKEN+" text not null," +
	  		""+COLUMN_BUSINESS+" text not null,"+COLUMN_CREATED+" text not null,"+COLUMN_EMAIL+" text not null," +
	  				""+COLUMN_FNAME+" text not null," +
	  		""+COLUMN_LASTUPDATED+" text not null,"+COLUMN_LNAME+" text not null,"+COLUMN_PHONE+" text not null,"+COLUMN_TYPE+" text not null," +
	  		""+COLUMN_BACKGROUNDSCAN+" boolean not null,"+COLUMN_GUEST+" boolean not null,"+COLUMN_FULLNAME+" text not null,"+COLUMN__ID+" text not null);";
	      

	  
	  
	  
	  public AppSqliteHelper(Context context) {
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	  }

	  @Override
	  public void onCreate(SQLiteDatabase database) {
	    database.execSQL(DATABASE_CREATE);
	    
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
