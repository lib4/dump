package com.appbase.httphandler;

public class HttpConstants {

	// General/Common Request Header Json key

	/*
	 * ############################## SIGN IN REQUEST PARAMS/JSON KEYS
	 */
	public static final String EMAIL_JKEY = "email";
	public static final String PASSWORD_JKEY = "password";
	public static final String TYPE_JKEY = "type";

	/*
	 * ############################## SIGN IN RESPONSE JSON KEYS
	 */
	public static final String ACCESS_TOKEN_JKEY = "accessToken";
	public static final String V_JKEY = "__v";
	public static final String _ID_JKEY = "_id";
	public static final String BUSINESS_JKEY = "business";
	public static final String CREATED_JKEY = "created";
	public static final String lNAME_JKEY = "lname";
	public static final String FNAME_JKEY = "fname";
	public static final String LASTUPDATED_JKEY = "lastUpdated";
	public static final String PHONE_JKEY = "phone";
	public static final String BACKGROUND_SCAN_JKEY = "backgroundScan";
	public static final String GUEST_JKEY = "guest";
	public static final String FULLNAME_JKEY = "fullname";
	public static final String ID_JKEY = "id";

	/*
	 * ########################### LIVEORDER RESPONSE JSON KEYS
	 */

	public static final String AMOUNT_JKEY = "amount";
	public static final String TAX_JKEY = "tax";
	public static final String CONSUMER_JKEY = "consumer";
	public static final String DEVICE_JKEY = "device";
	public static final String RATED_JKEY = "rated";
	public static final String FEE_COLLECTED_JKEY = "feeCollected";
	public static final String STATUS_JKEY = "status";
	public static final String ITEMS_JKEY = "items";
	public static final String PRICE_JKEY = "price";
	public static final String QTY_JKEY = "1";
	public static final String IMAGE_JKEY = "image";
	public static final String THUMBNAIL_JKEY = "thumbnail";
	public static final String NAME_JKEY = "name";

	public static final String CHOICES_JKEY = "choices";

	/*
	 * ################################# GET MENU PARSER
	 */
	public static final String CARDS_JKEY = "cards";
	public static final String CHOICE_GROUPS_JKEY = "choiceGroups";
	public static final String PAYMENTS_ENABLED_JKEY = "paymentsEnabled";
	public static final String SUBGROUPS_JKEY = "subgroups";
	public static final String GROUPS_JKEY = "groups";
	public static final String SENSOR_ADV_ID_JKEY = "sensorAdvID";
	public static final String DESCRIPTION_JKEY = "description";
	public static final String PRICE_STRING_JKEY = "priceString";
	public static final String TEXT_JKEY = "text";
	
	
	/*
	 *################################### BUSINESS
	 */
	
	public static final String BUSINESS_ID_JKEY	=	"businessID";
	public static final String CITY_JKEY	=	"city";
	public static final String COUNTRY_JKEY	=	"country";
	public static final String LAST_UPDATED_JKEY	=	"lastUpdated";
	public static final String LOGO_JKEY	=	"logo";
	public static final String POSTAL_CODE_JKEY	=	"postalCode";
	public static final String PROXIMITY_ID_JKEY	=	"proximityID";
	public static final String SENSOR_BATCH_JKEY	=	"sensorBatch";
	public static final String STATE_JKEY	=	"state";
	public static final String STREET_ADDRESS_JKEY	=	"streetAddress";
	public static final String STRIPE_RECEIPIENT_ID_JKEY	=	"stripeRecipientID";
	public static final String TAX_PERCENT_JEKEY	=	"taxPercent";
	public static final String SENSORS_JKEY	=	"sensors";
	
	public static final String BALANCE_JKEY	=	"balance";
	public static final String VERIFIED_JKEY	=	"verified";
	public static final String RATING_SUM_JKEY	=	"ratingsSum";
	public static final String RATING_COUNT_JKEY	=	"ratingsCount";
	public static final String GEO_JKEY	=	"geo";
	
	public static final String SENSOR_ID_JKEY	=	"sensorID";
	
	
	public static final String ADVERTISING_INTERVAL_JKEY	=	"advertisingInterval";
	public static final String TX_POWER_JKEY	=	"txPower";
	
	public static final String	MAJOR_JKEY	=	"major";
	public static final String	MINOR_JKEY	=	"minor";
	
	public static final String	DISPLAY_SENSOR_TYPE_JKEY	=	"displaySensorType";
	
	
	public static final String	ESTIMOTE	=	"ESTIMOTE";
	public static final String	AIRSENSOR	=	"AIRSENSOR";
	public static final String	VALIDATE_STATUS	=	"validation_status";
	public static final String	FAILURE	=	"failure";
	public static final String	SUCCESS	=	"success";
	public static final String	VALIDATE_TIME	=	"validation_status_time";
}
