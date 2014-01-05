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
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

public class GetMenusParser {

	String response;
	Context context;

	public GetMenusParser(InputStream inputStream, Context context)
			throws JSONException {
		this.context = context;
		parse(inputStream);
	}

	/**
	 * Function used to parse and store the data on local db.
	 * 
	 * @throws JSONException
	 */
	private void parse(InputStream response) throws JSONException {

		DBManager mDbManager = new DBManager(context);
		mDbManager.startTransaction();
		/*
		 * Parse the response here
		 */
		try {

			System.out.println("Parsing... : ");

			JsonFactory jsonfactory = new JsonFactory();
			JsonParser jsonParser = jsonfactory.createJsonParser(response);

			ContentValues values = null;
			while (jsonParser.nextToken() != JsonToken.END_ARRAY) {

				// Begin the parsing procedure
				while (jsonParser.nextToken() != JsonToken.END_OBJECT) {

					String token = jsonParser.getCurrentName();

					if (jsonParser.getCurrentToken() == JsonToken.START_OBJECT) {
						values = new ContentValues();
					}
					if (HttpConstants.NAME_JKEY.equals(token)) {

						// get the next token which will be the value...
						jsonParser.nextToken();
						values.put(AppSqliteHelper.COLUMN_NAME,
								jsonParser.getText());

					}

					if (HttpConstants.TYPE_JKEY.equals(token)) {

						jsonParser.nextToken();
						values.put(AppSqliteHelper.COLUMN_TYPE,
								jsonParser.getText());
						if (jsonParser.getText().equals("offer")) {
							values.put(AppSqliteHelper.COLUMN_NAME, "Deal");
						} else if (jsonParser.getText().equals("info")) {
							values.put(AppSqliteHelper.COLUMN_NAME, "Info");
						}
					}
					if (HttpConstants._ID_JKEY.equals(token)) {

						jsonParser.nextToken();
						values.put(AppSqliteHelper.COLUMN__ID,
								jsonParser.getText());

					}
					if (HttpConstants.STATUS_JKEY.equals(token)) {

						jsonParser.nextToken();
						values.put(AppSqliteHelper.COLUMN_STATUS,
								jsonParser.getText());

					}

					// GROUPS
					if (HttpConstants.GROUPS_JKEY.equals(token)) {
						jsonParser.nextToken();

						JSONArray groupsArray = new JSONArray();

						while (jsonParser.nextToken() != JsonToken.END_ARRAY) {

							JSONObject groupObject = new JSONObject();
							while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
								token = jsonParser.getCurrentName();

								if (HttpConstants.NAME_JKEY.equals(token)) {
									jsonParser.nextToken();
									groupObject
											.put(token, jsonParser.getText());
								}

								if (HttpConstants._ID_JKEY.equals(token)) {
									jsonParser.nextToken();
									groupObject
											.put(token, jsonParser.getText());
								}

								// SUBGROUPS

								if (HttpConstants.SUBGROUPS_JKEY.equals(token)) {
									jsonParser.nextToken();

									JSONArray subGroupArray = new JSONArray();

									while (jsonParser.nextToken() != JsonToken.END_ARRAY) {

										JSONObject subGroupObject = new JSONObject();
										while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
											token = jsonParser.getCurrentName();

											if (HttpConstants.NAME_JKEY
													.equals(token)) {
												jsonParser.nextToken();
												subGroupObject.put(token,
														jsonParser.getText());
											}

											if (HttpConstants._ID_JKEY
													.equals(token)) {
												jsonParser.nextToken();
												subGroupObject.put(token,
														jsonParser.getText());
											}

											if (HttpConstants.CARDS_JKEY
													.equals(token)) {
												jsonParser.nextToken();
												subGroupObject
														.put(token,
																cardsArrayParser(jsonParser));

											}

										}

										if (jsonParser.getCurrentToken() == JsonToken.END_OBJECT) {
											subGroupArray.put(subGroupObject);
										}

									}
									if (jsonParser.getCurrentToken() == JsonToken.END_ARRAY) {
										groupObject.put(
												HttpConstants.SUBGROUPS_JKEY,
												subGroupArray);
									}

								}

							}

							if (jsonParser.getCurrentToken() == JsonToken.END_OBJECT) {
								groupsArray.put(groupObject);
							}

						}

						// System.out.println("GROUPS ARRAY"+groupsArray.toString());

						values.put(AppSqliteHelper.COLUMN_GROUPS,
								groupsArray.toString());
					}

				}

				if (jsonParser.getCurrentToken() == JsonToken.END_OBJECT)

					mDbManager.insertCataloges(values);
			}
			jsonParser.close();

		} catch (JsonGenerationException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

		finally {
			mDbManager.endTransaction();
		}

	}

	private JSONArray cardsArrayParser(JsonParser jsonParser) {

		String token;
		JSONArray cardsArray = new JSONArray();
		// CARDS
		try {

			while (jsonParser.nextToken() != JsonToken.END_ARRAY) {

				JSONObject cardObject = new JSONObject();
				while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
					token = jsonParser.getCurrentName();

					if (HttpConstants.TYPE_JKEY.equals(token)) {
						jsonParser.nextToken();
						cardObject.put(token, jsonParser.getText());
					}

					if (HttpConstants.NAME_JKEY.equals(token)) {
						jsonParser.nextToken();
						cardObject.put(token, jsonParser.getText());
					}

					if (HttpConstants.DESCRIPTION_JKEY.equals(token)) {
						jsonParser.nextToken();
						cardObject.put(token, jsonParser.getText());
					}

					if (HttpConstants.PRICE_JKEY.equals(token)) {
						jsonParser.nextToken();
						cardObject.put(token, jsonParser.getDoubleValue());
					}
					if (HttpConstants.PRICE_STRING_JKEY.equals(token)) {
						jsonParser.nextToken();
						cardObject.put(token, jsonParser.getText());
					}
					if (HttpConstants._ID_JKEY.equals(token)) {
						jsonParser.nextToken();
						cardObject.put(token, jsonParser.getText());
					}

					// CHOICE GROUPS

					if (HttpConstants.CHOICE_GROUPS_JKEY.equals(token)) {
						jsonParser.nextToken();
						cardObject.put(token, choiceGroupsParser(jsonParser));

					}

					if (HttpConstants.PAYMENTS_ENABLED_JKEY.equals(token)) {
						jsonParser.nextToken();
						cardObject.put(token, jsonParser.getBooleanValue());
					}

				}

				if (jsonParser.getCurrentToken() == JsonToken.END_OBJECT) {
					cardsArray.put(cardObject);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return cardsArray;
	}

	private JSONArray choiceGroupsParser(JsonParser jsonParser) {

		String token;
		JSONArray choiceGroupArray = new JSONArray();

		try {

			while (jsonParser.nextToken() != JsonToken.END_ARRAY) {

				JSONObject choiceGroupObject = new JSONObject();
				while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
					token = jsonParser.getCurrentName();

					if (HttpConstants.TEXT_JKEY.equals(token)) {
						jsonParser.nextToken();
						choiceGroupObject.put(token, jsonParser.getText());
					}

					if (HttpConstants._ID_JKEY.equals(token)) {
						jsonParser.nextToken();
						choiceGroupObject.put(token, jsonParser.getText());
					}

					// Choice array parser
					if (HttpConstants.CHOICES_JKEY.equals(token)) {
						jsonParser.nextToken();
						choiceGroupObject.put(token, choicesParser(jsonParser));

					}

				}

				if (jsonParser.getCurrentToken() == JsonToken.END_OBJECT) {
					choiceGroupArray.put(choiceGroupObject);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return choiceGroupArray;
	}

	private JSONArray choicesParser(JsonParser jsonParser) {

		String token;
		JSONArray choicesArray = new JSONArray();
		// CARDS
		try {

			while (jsonParser.nextToken() != JsonToken.END_ARRAY) {

				JSONObject choiceObject = new JSONObject();
				while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
					token = jsonParser.getCurrentName();

					if (HttpConstants.NAME_JKEY.equals(token)) {
						jsonParser.nextToken();
						choiceObject.put(token, jsonParser.getText());
					}

					if (HttpConstants.PRICE_JKEY.equals(token)) {
						jsonParser.nextToken();
						choiceObject.put(token, jsonParser.getDoubleValue());
					}

					if (HttpConstants.PRICE_STRING_JKEY.equals(token)) {
						jsonParser.nextToken();
						choiceObject.put(token, jsonParser.getText());
					}

					if (HttpConstants._ID_JKEY.equals(token)) {
						jsonParser.nextToken();
						choiceObject.put(token, jsonParser.getText());
					}

				}

				if (jsonParser.getCurrentToken() == JsonToken.END_OBJECT) {
					choicesArray.put(choiceObject);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return choicesArray;
	}

}
