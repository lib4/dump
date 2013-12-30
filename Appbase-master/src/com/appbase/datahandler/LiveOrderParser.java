package com.appbase.datahandler;

import java.io.IOException;
import java.io.InputStream;

import android.content.ContentValues;
import android.content.Context;

import com.appbase.datastorage.AppSqliteHelper;
import com.appbase.datastorage.DBManager;
import com.appbase.httphandler.HttpConstants;
import com.appbase.utils.Utils;
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
					ContentValues values	=	new ContentValues();;
					// Begin the parsing procedure
					while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
					while (jsonParser.nextToken() != JsonToken.END_OBJECT) {

						String token = jsonParser.getCurrentName();

						if (HttpConstants.LASTUPDATED_JKEY.equals(token)) {

							// get the next token which will be the value...
							jsonParser.nextToken();
							values.put(AppSqliteHelper.COLUMN_ACCESSTOKEN, jsonParser.getText());
							//Utils.TOKEN	=	jsonParser.getText();
						}

						if (HttpConstants.CREATED_JKEY.equals(token)) {

							jsonParser.nextToken();
							System.out.println("members : " + jsonParser.getText());
							values.put(AppSqliteHelper.COLUMN__ID, jsonParser.getText());

						}
						if (HttpConstants.AMOUNT_JKEY.equals(token)) {

							jsonParser.nextToken();
							System.out.println("members : " + jsonParser.getFloatValue());
							values.put(AppSqliteHelper.COLUMN_BACKGROUNDSCAN, jsonParser.getText());

						}
						if (HttpConstants.TAX_JKEY.equals(token)) {

							jsonParser.nextToken();
							System.out.println("members : " + jsonParser.getFloatValue());
							values.put(AppSqliteHelper.COLUMN_BUSINESS, jsonParser.getText());

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
								}
								if (HttpConstants.DEVICE_JKEY.equals(token)) {

									while (jsonParser.nextToken() != JsonToken.END_OBJECT){
										if (HttpConstants.ID_JKEY.equals(token)) {
											jsonParser.nextToken();
											System.out.println("CONSUMER_JKEY DEVICE ID : " + jsonParser.getText());
										}
									}
									
									
								}
								if (HttpConstants.TYPE_JKEY.equals(token)) {

									jsonParser.nextToken();
									System.out.println("CONSUMER_JKEY : " + jsonParser.getText());
								}
								if (HttpConstants._ID_JKEY.equals(token)) {

									jsonParser.nextToken();
									System.out.println("CONSUMER_JKEY : " + jsonParser.getText());
								}
								
								if (HttpConstants.V_JKEY.equals(token)) {

									jsonParser.nextToken();
									System.out.println("CONSUMER_JKEY : " + jsonParser.getIntValue());
								}
								
								if (HttpConstants.BACKGROUND_SCAN_JKEY.equals(token)) {

									jsonParser.nextToken();
									System.out.println("CONSUMER_JKEY : " + jsonParser.getBooleanValue());
								}
								
								if (HttpConstants.GUEST_JKEY.equals(token)) {

									jsonParser.nextToken();
									System.out.println("CONSUMER_JKEY : " + jsonParser.getBooleanValue());
								}
								
								if (HttpConstants.ID_JKEY.equals(token)) {

									jsonParser.nextToken();
									System.out.println("CONSUMER_JKEY : " + jsonParser.getText());
								}
								
							}
							

						}
						
						if (HttpConstants.BUSINESS_JKEY.equals(token)) {

							jsonParser.nextToken();
							System.out.println("members : " + jsonParser.getText());

							values.put(AppSqliteHelper.COLUMN_LNAME, jsonParser.getText());
						}

						if (HttpConstants._ID_JKEY.equals(token)) {

							jsonParser.nextToken();
							System.out.println("members : " + jsonParser.getText());

							values.put(AppSqliteHelper.COLUMN_ID, jsonParser.getText());
						}
						
						
						if (HttpConstants.V_JKEY.equals(token)) {

							jsonParser.nextToken();
							System.out.println("members : " + jsonParser.getIntValue());

							values.put(AppSqliteHelper.COLUMN_EMAIL, jsonParser.getIntValue());
						}
						
						
						if (HttpConstants.RATED_JKEY.equals(token)) {

							jsonParser.nextToken();
							System.out.println("members : " + jsonParser.getBooleanValue());

							values.put(AppSqliteHelper.COLUMN_FNAME, jsonParser.getBooleanValue());
						}
						
						
						if (HttpConstants.FEE_COLLECTED_JKEY.equals(token)) {

							jsonParser.nextToken();
							System.out.println("members : " + jsonParser.getBooleanValue());

							values.put(AppSqliteHelper.COLUMN_FULLNAME, jsonParser.getBooleanValue());
						}
						
						if (HttpConstants.STATUS_JKEY.equals(token)) {

							jsonParser.nextToken();
							System.out.println("members : " + jsonParser.getText());

							values.put(AppSqliteHelper.COLUMN_GUEST, jsonParser.getText());
						}
						
						
						if (HttpConstants.ITEMS_JKEY.equals(token)) {

							jsonParser.nextToken();
							
							while (jsonParser.nextToken() != JsonToken.END_ARRAY) {

								while (jsonParser.nextToken() != JsonToken.END_OBJECT){
									token = jsonParser.getCurrentName();
									if (HttpConstants.NAME_JKEY.equals(token)) {
										jsonParser.nextToken();
										System.out.println("CONSUMER_JKEY DEVICE ID : " + jsonParser.getText());
									}
									
									if (HttpConstants.PRICE_JKEY.equals(token)) {
										jsonParser.nextToken();
										System.out.println("CONSUMER_JKEY DEVICE ID : " + jsonParser.getFloatValue());
									}
									
									if (HttpConstants.QTY_JKEY.equals(token)) {
										jsonParser.nextToken();
										System.out.println("CONSUMER_JKEY DEVICE ID : " + jsonParser.getIntValue());
									}
									if (HttpConstants.IMAGE_JKEY.equals(token)) {
										jsonParser.nextToken();
										System.out.println("CONSUMER_JKEY DEVICE ID : " + jsonParser.getText());
									}
									if (HttpConstants._ID_JKEY.equals(token)) {
										jsonParser.nextToken();
										System.out.println("CONSUMER_JKEY DEVICE ID : " + jsonParser.getText());
									}
								}
								
							
								
							}
							

							//values.put(AppSqliteHelper.COLUMN_TYPE, jsonParser.getText());
						}
						/*if (HttpConstants.BUSINESS_JKEY.equals(token)) {

							System.out.println("names :");

							// the next token will be '[' that means that we have an
							// array
							jsonParser.nextToken();

							// parse tokens until you find ']'
							while (jsonParser.nextToken() != JsonToken.END_ARRAY) {

								System.out.println(jsonParser.getText());
							}

						}*/

					}
					}
					
					jsonParser.close();

					//new DBManager(context).insertProfile(values);
				} catch (JsonGenerationException e) {

					e.printStackTrace();

				} catch (IOException e) {

					e.printStackTrace();

				}

			}
			

		}

