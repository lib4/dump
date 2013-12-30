package com.appbase.network;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import android.util.Base64;

import com.appbase.httphandler.HttpHandler;
import com.appbase.utils.Utils;



public class Connection {

	public InputStream responseStream;
	public int responseCode;
	public void connect(String url,String requestBody,String requestType) throws IOException{
	
		
		URL obj = new URL(url);
		//Trusting all the Https server and certificate. This is insecure should be avoided.
		trustAllHosts();
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post body : " + requestBody);
		
		con.setRequestMethod(requestType);
		//con.setRequestProperty("User-Agent", USER_AGENT);
		//con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
 
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Accept","application/json");
		if(Utils.TOKEN != null && Utils.PASSWORD != null){
			System.out.println(" base64EncodedCredentials : " + Utils.TOKEN);
			con.setRequestProperty("Authorization", "Bearer " + Utils.TOKEN);
		}

		
		if(requestType.equals(HttpHandler.HTTP_POST)){
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		//Based on request type. Request body will get attached to the http request.
		
		wr.writeBytes(requestBody);
	
		wr.flush();
		wr.close();
		}
 
		responseCode = con.getResponseCode();
	
		System.out.println("Response Code : " + responseCode);
		/*
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		//print result
		System.out.println(response.toString());

		 */
		
		responseStream 	=	con.getInputStream();
	}
	
	
	public static void trustAllHosts() {
	
		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return new java.security.cert.X509Certificate[] {};
			}

			public void checkClientTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			public void checkServerTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}
		} };

		// Install the all-trusting trust manager
		try {
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection
			.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
