package com.appbase.datahandler;

import java.io.IOException;
import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;

import com.appbase.datastorage.AppSqliteHelper;
import com.appbase.datastorage.DBManager;
import com.appbase.httphandler.HttpConstants;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

public class GetSensorsParser {
	String response;
	Context context;

	public GetSensorsParser(InputStream inputStream, Context context) {
		this.context = context;
		parse(inputStream);
	}

	/**
	 * Function used to parse and store the data on local db.
	 */
	private void parse(InputStream response) {
		/*
		 * Parse the response here
		 */
		try {
			System.out.println("Parsing....");
			
			JsonFactory jsonfactory = new JsonFactory();
			JsonParser jsonParser = jsonfactory.createJsonParser(response);
			ContentValues values = new ContentValues();
			//jsonParser.nextToken();
			// Begin the parsing procedure
			while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
				
				String token = jsonParser.getCurrentName();
				//System.out.println("Parsing...."+token);
				
				if (HttpConstants.V_JKEY.equals(token)) {

					// get the next token which will be the value...
					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_V,
							jsonParser.getText());
					
				}

				if (HttpConstants._ID_JKEY.equals(token)) {

					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN__ID, jsonParser.getText());

				}
				if (HttpConstants.BUSINESS_ID_JKEY.equals(token)) {

					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_BUSINESS_ID,
							jsonParser.getText());

				}
				if (HttpConstants.CITY_JKEY.equals(token)) {

					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_CITY,
							jsonParser.getText());

				}
				if (HttpConstants.COUNTRY_JKEY.equals(token)) {

					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_COUNTRY,
							jsonParser.getText());
				}
				if (HttpConstants.CREATED_JKEY.equals(token)) {

					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_CREATED,
							jsonParser.getText());
				}

				if (HttpConstants.LAST_UPDATED_JKEY.equals(token)) {

					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_LAST_UPDATED,
							jsonParser.getText());
				}

				if (HttpConstants.LOGO_JKEY.equals(token)) {

					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_LOGO,
							jsonParser.getText());
				}

				if (HttpConstants.NAME_JKEY.equals(token)) {

					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_NAME,
							jsonParser.getText());
				}

				if (HttpConstants.POSTAL_CODE_JKEY.equals(token)) {

					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_POSTAL_CODE, jsonParser.getText());
				}

				if (HttpConstants.PROXIMITY_ID_JKEY.equals(token)) {

					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_PROXIMITY_ID,
							jsonParser.getText());
				}

				if (HttpConstants.SENSOR_BATCH_JKEY.equals(token)) {

					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_SENSOR_BATCH,
							jsonParser.getText());
				}

				if (HttpConstants.STATE_JKEY.equals(token)) {

					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_STATE,
							jsonParser.getText());
				}

				if (HttpConstants.STREET_ADDRESS_JKEY.equals(token)) {

					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_STREET_ADDRESS, jsonParser.getText());
				}

				if (HttpConstants.STRIPE_RECEIPIENT_ID_JKEY.equals(token)) {

					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_STRIPE_RECEIPIENT_ID,
							jsonParser.getText());
				}

				if (HttpConstants.TAX_PERCENT_JEKEY.equals(token)) {

					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_TAX_PERCENT,
							jsonParser.getText());
				}

				if (HttpConstants.SENSORS_JKEY.equals(token)) {// ARRAY

					jsonParser.nextToken();
					JSONObject mSensorObject 	=	new JSONObject(); 
					try {
						mSensorObject.put(HttpConstants.SENSORS_JKEY, parseSensors(jsonParser));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					values.put(AppSqliteHelper.COLUMN_SENSORS,
							mSensorObject.toString());
				}

				if (HttpConstants.BALANCE_JKEY.equals(token)) {

					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_BALANCE,
							jsonParser.getText());
				}
				if (HttpConstants.VERIFIED_JKEY.equals(token)) {

					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_VERIFIED,
							jsonParser.getText());
				}
				if (HttpConstants.RATING_SUM_JKEY.equals(token)) {

					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_RATING_SUM,
							jsonParser.getText());
				}
				if (HttpConstants.RATING_COUNT_JKEY.equals(token)) {

					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_RATING_COUNT,
							jsonParser.getText());
				}

				if (HttpConstants.GEO_JKEY.equals(token)) {//GEO is not come from servee in proper format.
					jsonParser.nextToken();
					System.out.println("GEO "+jsonParser.getText());
					parseGeo(jsonParser);
					values.put(AppSqliteHelper.COLUMN_GEO,
							jsonParser.getText());
				}

			}

			jsonParser.close();
			System.out.println("Values "+values.size() +values.toString());
			new DBManager(context).insertBusiness(values);
		} catch (JsonGenerationException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	private JSONArray parseSensors( JsonParser jsonParser) throws JsonParseException, IOException {

		
	
		JSONArray sensorArray = new JSONArray();
			
		
		
			while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
				try{
				JSONObject snesorObject = new JSONObject();
				while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
					String token = jsonParser.getCurrentName();
					System.out.println("token"+token);
					if (HttpConstants.NAME_JKEY.equals(token)) {
						jsonParser.nextToken();
						snesorObject.put(token, jsonParser.getText());
					}

					if (HttpConstants.ADVERTISING_INTERVAL_JKEY.equals(token)) {
						jsonParser.nextToken();
						snesorObject.put(token, jsonParser.getText());
					}

			
					if (HttpConstants.SENSOR_ID_JKEY.equals(token)) {
						jsonParser.nextToken();
						snesorObject.put(token, jsonParser.getText());

					}

					if (HttpConstants.TX_POWER_JKEY.equals(token)) {
						jsonParser.nextToken();
						snesorObject.put(token, jsonParser.getText());

					}

					if (HttpConstants.TYPE_JKEY.equals(token)) {
						jsonParser.nextToken();
						snesorObject.put(token, jsonParser.getText());

					}

					if (HttpConstants.STATUS_JKEY.equals(token)) {
						jsonParser.nextToken();
						snesorObject.put(token, jsonParser.getText());

					}

				}

				if (jsonParser.getCurrentToken() == JsonToken.END_OBJECT) {
					sensorArray.put(snesorObject);
				}
				
				}catch(Exception e){
					e.printStackTrace();
				}

			}
			
			

			return sensorArray;
	}
	
	
	private JSONArray parseGeo( JsonParser jsonParser) throws JsonParseException, IOException {

		
		
		JSONArray geoArray = new JSONArray();
			
		
		
			while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
				try{
					String token = jsonParser.getCurrentName();
					System.out.println("token"+token +" "+jsonParser.getText());
				}catch(Exception e){
					e.printStackTrace();
				}
				
			}
			
			
			return geoArray;
	}

}
