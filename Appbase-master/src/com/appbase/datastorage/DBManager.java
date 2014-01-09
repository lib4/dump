package com.appbase.datastorage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class DBManager {

	private AppSqliteHelper sqLiteOpenHelper; // SQLITE Helper instance

	private SQLiteDatabase appSqLiteDatabase; // Database instance

	/**
	 * Constructor initializes the Sqlitehelper
	 * 
	 * @param mContext
	 */
	public DBManager(Context mContext) {
		// TODO Auto-generated constructor stub
		sqLiteOpenHelper = new AppSqliteHelper(mContext);

	}

	/**
	 * Methods opens the database
	 * 
	 * @throws SQLException
	 */
	public void open() throws SQLException {
		try {
			appSqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
			Uri mUril = Uri.parse(appSqLiteDatabase.getPath());
			Log.e("URI ", "" + mUril.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method closes the database.
	 */
	public void close() {
		sqLiteOpenHelper.close();
	}

	// /**
	// * Dummy insertion of values to comments table
	// */
	//
	// public void insertComments(ContentValues values) {
	// //for(int i=0;i<100;i++){
	// ContentValues values = new ContentValues();
	// values.put(AppSqliteHelper.COLUMN_COMMENT, "aabuback: Comment ID ");
	// long insertId = appSqLiteDatabase.insert(
	// sqLiteOpenHelper.TABLE_COMMENTS, null, values);
	// //}
	// }
	//
	//
	/**
	 * Inserting on to Profile Table. After succeful signup
	 */
	public void insertProfile(ContentValues values) {
		open();
		long insertId = appSqLiteDatabase.delete(
				AppSqliteHelper.TABLE_PROFILE, null, null);
		insertId = appSqLiteDatabase.insert(
				sqLiteOpenHelper.TABLE_PROFILE, null, values);
		fetchProfile();
		close();

	}

	/**
	 * Inserting on to Profile Table. After succeful signup
	 */
	public void insertLiveOrders(ContentValues values) {

		System.out.println("Size Values " + values.size());
		// open();
		long insertId = appSqLiteDatabase.insert(
				sqLiteOpenHelper.TABLE_LIVE_ORDERS, null, values);
		// fetchLiveOrders();
		// close();

	}

	/**
	 * Flush the data in Live Order Table
	 */
	public void clearLiveOrders() {

		open();
		long insertId = appSqLiteDatabase.delete(
				AppSqliteHelper.TABLE_LIVE_ORDERS, null, null);
		close();

	}
	
	
	/**
	 * Flush the data in Menus  Table
	 */
	public void clearMenus() {

		open();
		long insertId = appSqLiteDatabase.delete(
				AppSqliteHelper.TABLE_CATALOGS, null, null);
		close();

	}
	
	
	/**
	 * Flush the data in Profile Table
	 */
	public void clearProfile() {

		open();
		long insertId = appSqLiteDatabase.delete(
				AppSqliteHelper.TABLE_PROFILE, null, null);
		close();

	}
	
	
	/**
	 * Flush the data in Menus  Table
	 */
	public void clearDB() {

		open();
		long insertId = appSqLiteDatabase.delete(
				AppSqliteHelper.TABLE_PROFILE, null, null);
		 insertId = appSqLiteDatabase.delete(
				AppSqliteHelper.TABLE_CATALOGS, null, null);
		 insertId = appSqLiteDatabase.delete(
				AppSqliteHelper.TABLE_LIVE_ORDERS, null, null);
		close();

	}

	/**
	 * Inserting on to Profile Table. After succeful signup
	 */
	public void insertCataloges(ContentValues values) {

		System.out.println("Size Values " + values.size());
		long insertId = appSqLiteDatabase.insert(
				sqLiteOpenHelper.TABLE_CATALOGS, null, values);
		
	}

	//
	//
	//
	// /**
	// * Fetching all the comments from Comment table.
	// */
	//
	public Cursor fetchProfile() {

		String[] allColumns = { sqLiteOpenHelper.COLUMN_ID,
				sqLiteOpenHelper.COLUMN_ACCESSTOKEN };
		Cursor cursor = appSqLiteDatabase.query(AppSqliteHelper.TABLE_PROFILE,
				allColumns, null, null, null, null, null);
		Log.e("Curson Size ", "== " + cursor.getCount());
		return cursor;
	}

	
	public String getProfileToken() {

		try{
			open();
		String[] allColumns = { 
				sqLiteOpenHelper.COLUMN_ACCESSTOKEN };
		Cursor cursor = appSqLiteDatabase.query(AppSqliteHelper.TABLE_PROFILE,
				allColumns, null, null, null, null, null);
		Log.e("Curson Size ", "== " + cursor.getCount());
		
		if(cursor!=null&&cursor.getCount()>0){
			cursor.moveToNext();
			return cursor.getString(0);
		}else
			return null;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
		finally{
			close();
		}

		
	}
	public Cursor fetchLiveOrders() {
		Cursor cursor;
		try {
			open();
			String[] allColumns = { sqLiteOpenHelper.COLUMN_FULLNAME,
					sqLiteOpenHelper.COLUMN_AMOUNT,
					sqLiteOpenHelper.COLUMN__ID,
					sqLiteOpenHelper.COLUMN_STATUS,
					sqLiteOpenHelper.COLUMN_ITEMS,
					sqLiteOpenHelper.COLUMN_THUMB };
			cursor = appSqLiteDatabase.query(AppSqliteHelper.TABLE_LIVE_ORDERS,
					allColumns, null, null, null, null, null);
			Log.e("Curson Size ", "== " + cursor.getCount());
			close();
		} catch (Exception e) {
			return null;
		}
		return cursor;
	}

	public Cursor fetchCatalogs() {
		Cursor cursor;
		try {
			open();
			String[] allColumns = { sqLiteOpenHelper.COLUMN_NAME,
					sqLiteOpenHelper.COLUMN_TYPE, sqLiteOpenHelper.COLUMN__ID,
					sqLiteOpenHelper.COLUMN_STATUS,
					sqLiteOpenHelper.COLUMN_GROUPS };
			cursor = appSqLiteDatabase.query(AppSqliteHelper.TABLE_CATALOGS,
					allColumns, null, null, null, null, null);
			System.out.println("CURSOR>>>>>>>> " + cursor.getCount());

			close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return cursor;
	}
	
	
	
	public Cursor fetchCatalogDetails(String id) {
		Cursor cursor;
		try {
			open();
			String[] allColumns = { sqLiteOpenHelper.COLUMN_NAME,
					sqLiteOpenHelper.COLUMN_TYPE, sqLiteOpenHelper.COLUMN__ID,
					sqLiteOpenHelper.COLUMN_STATUS,
					sqLiteOpenHelper.COLUMN_GROUPS };
			cursor = appSqLiteDatabase.query(AppSqliteHelper.TABLE_CATALOGS,
					allColumns, sqLiteOpenHelper.COLUMN__ID +"= '"+id +"'", null, null, null, null);
			System.out.println("CURSOR>>>>>>>> " + cursor.getCount());

			close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return cursor;
	}

	public void startTransaction() {
		open();
		
		appSqLiteDatabase.beginTransaction();
	}

	public void endTransaction() {
		appSqLiteDatabase.setTransactionSuccessful();

		appSqLiteDatabase.endTransaction();
		close();

	}
}
