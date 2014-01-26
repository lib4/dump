package com.appbase.datahandler;

import java.io.IOException;
import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

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
				
				DBManager mDbManager	=	new DBManager(context);
		
					mDbManager.startTransaction();
				/*
				 * Parse the response here
				 */
				try {
					
					
					JsonFactory jsonfactory = new JsonFactory();
					JsonParser jsonParser = jsonfactory.createJsonParser(response);
					ContentValues values	=	new ContentValues();

					
					jsonParser.nextToken();
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
											values.put(AppSqliteHelper.COLUMN_CREATED, jsonParser.getText());
				
										}
										if (HttpConstants.AMOUNT_JKEY.equals(token)) {
				
											jsonParser.nextToken();
											values.put(AppSqliteHelper.COLUMN_AMOUNT, jsonParser.getDoubleValue());
				
										}
										if (HttpConstants.TAX_JKEY.equals(token)) {
				
											jsonParser.nextToken();
											values.put(AppSqliteHelper.COLUMN_TAX, jsonParser.getDoubleValue());
				
										}
										
										//Consumer object parsing
										if (HttpConstants.CONSUMER_JKEY.equals(token)) {
				
											if(jsonParser.nextToken()==JsonToken.START_OBJECT){
											while (jsonParser.nextToken() != JsonToken.END_OBJECT){
												token = jsonParser.getCurrentName();
												
												Log.e("TOKEN ","TOKEN  "+token);
												if (HttpConstants.LASTUPDATED_JKEY.equals(token)) {
				
													jsonParser.nextToken();
													
												}
												if (HttpConstants.CREATED_JKEY.equals(token)) {
				
													jsonParser.nextToken();
												}
												if (HttpConstants.EMAIL_JKEY.equals(token)) {
				
													jsonParser.nextToken();
													values.put(AppSqliteHelper.COLUMN_CONSUMEREMAIL, jsonParser.getText());
												}
												
												if (HttpConstants.FNAME_JKEY.equals(token)) {
				
													jsonParser.nextToken();
													values.put(AppSqliteHelper.COLUMN_FNAME, jsonParser.getText());
												}
												
												if (HttpConstants.lNAME_JKEY.equals(token)) {
				
													jsonParser.nextToken();
													values.put(AppSqliteHelper.COLUMN_LNAME, jsonParser.getText());
												}
												
												if (HttpConstants.FULLNAME_JKEY.equals(token)) {
				
													jsonParser.nextToken();
													values.put(AppSqliteHelper.COLUMN_FULLNAME, jsonParser.getText());
												}
												if (HttpConstants.IMAGE_JKEY.equals(token)) {
				
													jsonParser.nextToken();
													values.put(AppSqliteHelper.COLUMN_IMAGE, jsonParser.getText());
												}
												
												if (HttpConstants.THUMBNAIL_JKEY.equals(token)) {
				
													jsonParser.nextToken();
													values.put(AppSqliteHelper.COLUMN_THUMB, jsonParser.getText());
												}
												
												if (HttpConstants.DEVICE_JKEY.equals(token)) {
													
													while (jsonParser.nextToken() != JsonToken.END_OBJECT){
														token = jsonParser.getCurrentName();
														if (HttpConstants.ID_JKEY.equals(token)) {
															jsonParser.nextToken();
															values.put(AppSqliteHelper.COLUMN_DEVICE_ID, jsonParser.getText());
														}
													}
													
													jsonParser.nextToken();
													token = jsonParser.getCurrentName();
												}
												if (HttpConstants.TYPE_JKEY.equals(token)) {
				
													jsonParser.nextToken();
													values.put(AppSqliteHelper.COLUMN_TYPE, jsonParser.getText());
												}
												if (HttpConstants._ID_JKEY.equals(token)) {
				
													jsonParser.nextToken();
													values.put(AppSqliteHelper.COLUMN_CONSUMER__ID, jsonParser.getText());
												}
												
												if (HttpConstants.V_JKEY.equals(token)) {
				
													jsonParser.nextToken();
													values.put(AppSqliteHelper.COLUMN_CONSUMER_V, jsonParser.getIntValue());
												}
												
												if (HttpConstants.BACKGROUND_SCAN_JKEY.equals(token)) {
				
													jsonParser.nextToken();
													values.put(AppSqliteHelper.COLUMN_BACKGROUNDSCAN, jsonParser.getBooleanValue());
												}
												
												if (HttpConstants.GUEST_JKEY.equals(token)) {
				
													jsonParser.nextToken();
													values.put(AppSqliteHelper.COLUMN_GUEST, jsonParser.getBooleanValue());
												}
												
												if (HttpConstants.ID_JKEY.equals(token)) {
				
													jsonParser.nextToken();
													values.put(AppSqliteHelper.COLUMN_ID, jsonParser.getText());
												}
												
											}
											}//If Consumer Close
				
										}
										
										if (HttpConstants.BUSINESS_JKEY.equals(token)) {
				
											jsonParser.nextToken();
											values.put(AppSqliteHelper.COLUMN_BUSINESS, jsonParser.getText());
										}
				
										if (HttpConstants._ID_JKEY.equals(token)) {
				
											jsonParser.nextToken();
											values.put(AppSqliteHelper.COLUMN__ID, jsonParser.getText());
										}
										
										
										if (HttpConstants.V_JKEY.equals(token)) {
				
											jsonParser.nextToken();
											values.put(AppSqliteHelper.COLUMN_V, jsonParser.getIntValue());
										}
										
										
										if (HttpConstants.RATED_JKEY.equals(token)) {
				
											jsonParser.nextToken();
											values.put(AppSqliteHelper.COLUMN_RATED, jsonParser.getBooleanValue());
										}
										
										
										if (HttpConstants.FEE_COLLECTED_JKEY.equals(token)) {
				
											jsonParser.nextToken();
											values.put(AppSqliteHelper.COLUMN_FEECOLLECTED, jsonParser.getBooleanValue());
										}
										
										if (HttpConstants.STATUS_JKEY.equals(token)) {
				
											jsonParser.nextToken();
											values.put(AppSqliteHelper.COLUMN_STATUS, jsonParser.getText());
										}
										
										
										if (HttpConstants.ITEMS_JKEY.equals(token)) {
											Log.e("Items ","Items "+token);
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
													}
													
													if (HttpConstants.PRICE_JKEY.equals(token)) {
														jsonParser.nextToken();
														mJsonObject.put(token, jsonParser.getDoubleValue());
													}
													
													if (HttpConstants.QTY_JKEY.equals(token)) {
														jsonParser.nextToken();
														mJsonObject.put(token, jsonParser.getIntValue());
													}
													if (HttpConstants.IMAGE_JKEY.equals(token)) {
														jsonParser.nextToken();
														mJsonObject.put(token, jsonParser.getText());
													}
													if (HttpConstants._ID_JKEY.equals(token)) {
														jsonParser.nextToken();
														mJsonObject.put(token, jsonParser.getText());
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
																
																	mJsonObject.put(token, Float.valueOf(jsonParser.getText()));
																	choiceObject.put(token, Float.valueOf(jsonParser.getText()));
																	
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
											
											
											values.put(AppSqliteHelper.COLUMN_ITEMS, itemArray.toString());
										}
										
				
									}
									
									if(jsonParser.getCurrentToken()==JsonToken.END_OBJECT){
										
										mDbManager.insertLiveOrders(values);
									}
					
					//}
					
					
					}
					
					jsonParser.close();

					
				} catch (JsonGenerationException e) {

					e.printStackTrace();

				} catch (IOException e) {

					e.printStackTrace();

				}
				
				finally{
		
						mDbManager.endTransaction();
					
				
				}

			}
			

		}

