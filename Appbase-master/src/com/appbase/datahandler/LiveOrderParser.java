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

public class LiveOrderParser {



			String response;
			Context context;
			public LiveOrderParser(InputStream inputStream,Context context) {
				this.context	=	context;
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
					
					System.out.println("Parsing... : ");

					JsonFactory jsonfactory = new JsonFactory();
					JsonParser jsonParser = jsonfactory.createJsonParser(response);
					ContentValues values	=	new ContentValues();
					// Begin the parsing procedure
					while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
					while (jsonParser.nextToken() != JsonToken.END_OBJECT) {

						String token = jsonParser.getCurrentName();

						if (HttpConstants.LASTUPDATED_JKEY.equals(token)) {

							// get the next token which will be the value...
							jsonParser.nextToken();
							values.put(AppSqliteHelper.COLUMN_LASTUPDATED, jsonParser.getText());
							
						}

						if (HttpConstants.CREATED_JKEY.equals(token)) {

							jsonParser.nextToken();
							System.out.println("members : " + jsonParser.getText());
							values.put(AppSqliteHelper.COLUMN_CREATED, jsonParser.getText());

						}
						if (HttpConstants.AMOUNT_JKEY.equals(token)) {

							jsonParser.nextToken();
							System.out.println("members : " + jsonParser.getFloatValue());
							values.put(AppSqliteHelper.COLUMN_AMOUNT, jsonParser.getText());

						}
						if (HttpConstants.TAX_JKEY.equals(token)) {

							jsonParser.nextToken();
							System.out.println("members : " + jsonParser.getFloatValue());
							values.put(AppSqliteHelper.COLUMN_TAX, jsonParser.getText());

						}
						
						//Consumer object parsing
						if (HttpConstants.CONSUMER_JKEY.equals(token)) {

							while (jsonParser.nextToken() != JsonToken.END_OBJECT){
								token = jsonParser.getCurrentName();
								if (HttpConstants.LASTUPDATED_JKEY.equals(token)) {

									jsonParser.nextToken();
									System.out.println("CONSUMER_JKEY : " + jsonParser.getText());
									
								}
								if (HttpConstants.CREATED_JKEY.equals(token)) {

									jsonParser.nextToken();
									System.out.println("CONSUMER_JKEY : " + jsonParser.getText());
								}
								if (HttpConstants.EMAIL_JKEY.equals(token)) {

									jsonParser.nextToken();
									System.out.println("CONSUMER_JKEY : " + jsonParser.getText());
									values.put(AppSqliteHelper.COLUMN_CONSUMEREMAIL, jsonParser.getText());
								}
								if (HttpConstants.DEVICE_JKEY.equals(token)) {
									
									while (jsonParser.nextToken() != JsonToken.END_OBJECT){
										token = jsonParser.getCurrentName();
										if (HttpConstants.ID_JKEY.equals(token)) {
											jsonParser.nextToken();
											System.out.println("*********** DEVICE ID : " + jsonParser.getText());
											values.put(AppSqliteHelper.COLUMN_DEVICE_ID, jsonParser.getText());
										}
									}
									
									jsonParser.nextToken();
									token = jsonParser.getCurrentName();
								}
								if (HttpConstants.TYPE_JKEY.equals(token)) {

									jsonParser.nextToken();
									System.out.println("CONSUMER_JKEY : " + jsonParser.getText());
									values.put(AppSqliteHelper.COLUMN_TYPE, jsonParser.getText());
								}
								if (HttpConstants._ID_JKEY.equals(token)) {

									jsonParser.nextToken();
									System.out.println("CONSUMER_JKEY : " + jsonParser.getText());
									values.put(AppSqliteHelper.COLUMN_CONSUMER__ID, jsonParser.getText());
								}
								
								if (HttpConstants.V_JKEY.equals(token)) {

									jsonParser.nextToken();
									System.out.println("CONSUMER_JKEY : " + jsonParser.getIntValue());
									values.put(AppSqliteHelper.COLUMN_CONSUMER_V, jsonParser.getIntValue());
								}
								
								if (HttpConstants.BACKGROUND_SCAN_JKEY.equals(token)) {

									jsonParser.nextToken();
									System.out.println("CONSUMER_JKEY : " + jsonParser.getBooleanValue());
									
									values.put(AppSqliteHelper.COLUMN_BACKGROUNDSCAN, jsonParser.getBooleanValue());
								}
								
								if (HttpConstants.GUEST_JKEY.equals(token)) {

									jsonParser.nextToken();
									System.out.println("CONSUMER_JKEY : " + jsonParser.getBooleanValue());
									values.put(AppSqliteHelper.COLUMN_GUEST, jsonParser.getBooleanValue());
								}
								
								if (HttpConstants.ID_JKEY.equals(token)) {

									jsonParser.nextToken();
									System.out.println("CONSUMER_JKEY : " + jsonParser.getText());
									values.put(AppSqliteHelper.COLUMN_ID, jsonParser.getText());
								}
								
							}
							

						}
						
						if (HttpConstants.BUSINESS_JKEY.equals(token)) {

							jsonParser.nextToken();
							System.out.println("BUSINESS_JKEY : " + jsonParser.getText());

							values.put(AppSqliteHelper.COLUMN_BUSINESS, jsonParser.getText());
						}

						if (HttpConstants._ID_JKEY.equals(token)) {

							jsonParser.nextToken();
							System.out.println("members : " + jsonParser.getText());

							values.put(AppSqliteHelper.COLUMN__ID, jsonParser.getText());
						}
						
						
						if (HttpConstants.V_JKEY.equals(token)) {

							jsonParser.nextToken();
							System.out.println("members : " + jsonParser.getIntValue());

							values.put(AppSqliteHelper.COLUMN_V, jsonParser.getIntValue());
						}
						
						
						if (HttpConstants.RATED_JKEY.equals(token)) {

							jsonParser.nextToken();
							System.out.println("members : " + jsonParser.getBooleanValue());

							values.put(AppSqliteHelper.COLUMN_RATED, jsonParser.getBooleanValue());
						}
						
						
						if (HttpConstants.FEE_COLLECTED_JKEY.equals(token)) {

							jsonParser.nextToken();
							System.out.println("members : " + jsonParser.getBooleanValue());

							values.put(AppSqliteHelper.COLUMN_FEECOLLECTED, jsonParser.getBooleanValue());
						}
						
						if (HttpConstants.STATUS_JKEY.equals(token)) {

							jsonParser.nextToken();
							System.out.println("STATUS_JKEY : " + jsonParser.getText());

							values.put(AppSqliteHelper.COLUMN_STATUS, jsonParser.getText());
						}
						
						
						if (HttpConstants.ITEMS_JKEY.equals(token)) {
							jsonParser.nextToken();
							JSONArray itemArray	=	new JSONArray();
							while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
								
								
								JSONObject mJsonObject	=	new JSONObject();
								while (jsonParser.nextToken() != JsonToken.END_OBJECT){
									
									token = jsonParser.getCurrentName();
									
									try{
									if (HttpConstants.NAME_JKEY.equals(token)) {
										jsonParser.nextToken();
										mJsonObject.put(token, jsonParser.getText());
										System.out.println("ITEMS_JKEY  : " + jsonParser.getText());
									}
									
									if (HttpConstants.PRICE_JKEY.equals(token)) {
										jsonParser.nextToken();
										mJsonObject.put(token, jsonParser.getDoubleValue());
										System.out.println("ITEMS_JKEY PRICE_JKEY : " + jsonParser.getDoubleValue());
									}
									
									if (HttpConstants.QTY_JKEY.equals(token)) {
										jsonParser.nextToken();
										mJsonObject.put(token, jsonParser.getIntValue());
										System.out.println("ITEMS_JKEY : " + jsonParser.getIntValue());
									}
									if (HttpConstants.IMAGE_JKEY.equals(token)) {
										jsonParser.nextToken();
										mJsonObject.put(token, jsonParser.getText());
										System.out.println("ITEMS_JKEY: " + jsonParser.getText());
									}
									if (HttpConstants._ID_JKEY.equals(token)) {
										jsonParser.nextToken();
										mJsonObject.put(token, jsonParser.getText());
										System.out.println("ITEMS_JKEY : " + jsonParser.getText());
									}
									
									
									if (HttpConstants.CHOICES_JKEY.equals(token)) {
										String choicetoken	=	token;
										jsonParser.nextToken();
										JSONArray choicesArray	=	new JSONArray();
										while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
											
											
											JSONObject choiceObject	=	new JSONObject();
											while (jsonParser.nextToken() != JsonToken.END_OBJECT){
												token = jsonParser.getCurrentName();
												
												if (HttpConstants.NAME_JKEY.equals(token)) {
													jsonParser.nextToken();
													mJsonObject.put(token, jsonParser.getText());
													choiceObject.put(token, jsonParser.getText());
												}
												
												if (HttpConstants.PRICE_JKEY.equals(token)) {
													jsonParser.nextToken();
													mJsonObject.put(token, jsonParser.getFloatValue());
													choiceObject.put(token, jsonParser.getFloatValue());
													
												}
												
												if (HttpConstants.TYPE_JKEY.equals(token)) {
													jsonParser.nextToken();
													mJsonObject.put(token, jsonParser.getText());
													choiceObject.put(token, jsonParser.getText());
													
												}
												
												
											}
											
											if(jsonParser.getCurrentToken()==JsonToken.END_OBJECT){
												choicesArray.put(choiceObject);
											}
										}
										
										
										
										mJsonObject.put(choicetoken, choicesArray);
										
									}
									}catch(JSONException e){
										e.printStackTrace();
									}
								}
								
								if(jsonParser.getCurrentToken()==JsonToken.END_OBJECT){
									itemArray.put(mJsonObject);
								}
								
							}
							
							
							System.out.println("TEST  mJsonObject: " + itemArray.toString());
							
							values.put(AppSqliteHelper.COLUMN_ITEMS, itemArray.toString());
						}
						

					}
					
					if(jsonParser.getCurrentToken()==JsonToken.END_OBJECT){
						new DBManager(context).insertLiveOrders(values);
					}
					}
					
					jsonParser.close();

					
				} catch (JsonGenerationException e) {

					e.printStackTrace();

				} catch (IOException e) {

					e.printStackTrace();

				}

			}
			

		}

