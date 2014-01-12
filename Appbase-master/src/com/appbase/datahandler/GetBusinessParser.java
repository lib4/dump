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
import com.appbase.utils.Utils;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

public class GetBusinessParser {

	String response;
	Context context;

	public GetBusinessParser(InputStream inputStream, Context context) {
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

			JsonFactory jsonfactory = new JsonFactory();
			JsonParser jsonParser = jsonfactory.createJsonParser(response);
			ContentValues values = new ContentValues();
			;
			// Begin the parsing procedure
			while (jsonParser.nextToken() != JsonToken.END_OBJECT) {

				String token = jsonParser.getCurrentName();

				if (HttpConstants.V_JKEY.equals(token)) {

					// get the next token which will be the value...
					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_ACCESSTOKEN,
							jsonParser.getText());
					Utils.TOKEN = jsonParser.getText();
				}

				if (HttpConstants._ID_JKEY.equals(token)) {

					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN__ID, jsonParser.getText());

				}
				if (HttpConstants.BUSINESS_ID_JKEY.equals(token)) {

					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_BACKGROUNDSCAN,
							jsonParser.getText());

				}
				if (HttpConstants.CITY_JKEY.equals(token)) {

					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_BUSINESS,
							jsonParser.getText());

				}
				if (HttpConstants.COUNTRY_JKEY.equals(token)) {

					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_CREATED,
							jsonParser.getText());
				}
				if (HttpConstants.CREATED_JKEY.equals(token)) {

					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_EMAIL,
							jsonParser.getText());
				}

				if (HttpConstants.LAST_UPDATED_JKEY.equals(token)) {

					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_FNAME,
							jsonParser.getText());
				}

				if (HttpConstants.LOGO_JKEY.equals(token)) {

					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_FULLNAME,
							jsonParser.getText());
				}

				if (HttpConstants.NAME_JKEY.equals(token)) {

					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_GUEST,
							jsonParser.getText());
				}

				if (HttpConstants.POSTAL_CODE_JKEY.equals(token)) {

					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_ID, jsonParser.getText());
				}

				if (HttpConstants.PROXIMITY_ID_JKEY.equals(token)) {

					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_LASTUPDATED,
							jsonParser.getText());
				}

				if (HttpConstants.SENSOR_BATCH_JKEY.equals(token)) {

					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_LNAME,
							jsonParser.getText());
				}

				if (HttpConstants.STATE_JKEY.equals(token)) {

					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_PHONE,
							jsonParser.getText());
				}

				if (HttpConstants.STREET_ADDRESS_JKEY.equals(token)) {

					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_V, jsonParser.getText());
				}

				if (HttpConstants.STRIPE_RECEIPIENT_ID_JKEY.equals(token)) {

					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_TYPE,
							jsonParser.getText());
				}

				if (HttpConstants.TAX_PERCENT_JEKEY.equals(token)) {

					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_TYPE,
							jsonParser.getText());
				}

				if (HttpConstants.SENSORS_JKEY.equals(token)) {// ARRAY

					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_TYPE,
							jsonParser.getText());
				}

				if (HttpConstants.BALANCE_JKEY.equals(token)) {

					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_TYPE,
							jsonParser.getText());
				}
				if (HttpConstants.VERIFIED_JKEY.equals(token)) {

					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_TYPE,
							jsonParser.getText());
				}
				if (HttpConstants.RATING_SUM_JKEY.equals(token)) {

					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_TYPE,
							jsonParser.getText());
				}
				if (HttpConstants.RATING_COUNT_JKEY.equals(token)) {

					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_TYPE,
							jsonParser.getText());
				}

				if (HttpConstants.GEO_JKEY.equals(token)) {

					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_TYPE,
							jsonParser.getText());
				}

			}

			jsonParser.close();

			new DBManager(context).insertProfile(values);
		} catch (JsonGenerationException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	private void parseSensors(JsonParser jsonParser, String token) throws JsonParseException, IOException {

		
		
		if (HttpConstants.SENSORS_JKEY.equals(token)) {
			jsonParser.nextToken();

			JSONArray sensorArray = new JSONArray();
			
			
			try{

			while (jsonParser.nextToken() != JsonToken.END_ARRAY) {

				JSONObject snesorObject = new JSONObject();
				while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
					token = jsonParser.getCurrentName();

					if (HttpConstants.NAME_JKEY.equals(token)) {
						jsonParser.nextToken();
						snesorObject.put(token, jsonParser.getText());
					}

					if (HttpConstants.ADVERTISING_INTERVAL_JKEY.equals(token)) {
						jsonParser.nextToken();
						snesorObject.put(token, jsonParser.getText());
					}

					if (HttpConstants.NAME_JKEY.equals(token)) {
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

			}
			
			}catch(Exception e){
				e.printStackTrace();
			}

		}
	}

}
