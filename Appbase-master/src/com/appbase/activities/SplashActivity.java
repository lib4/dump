package com.appbase.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.appbase.R;
import com.appbase.datastorage.DBManager;
import com.appbase.utils.Utils;



public class SplashActivity extends BaseActivity{


	private Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String profileToken	=	new DBManager(this).getProfileToken();
		
		System.out.println("profileToken"+profileToken);
		if(profileToken!=null){
			Utils.TOKEN	=	profileToken;
			swapToLiveViewActivity();
		}else{
			setContentView(R.layout.splash);
			swapToSignInActivity();
		}
	}

	/**
	 * Load the SignIn fragment
	 * 
	 */

	private void swapToSignInActivity() {

		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				// Calling the next Activity.
				Intent intent = new Intent(SplashActivity.this, LauncherActivity.class);
				startActivity(intent);
				finish();

			}

		}, 4000);
		

	}
	
	
	/**
	 * Load the Live fragment
	 * 
	 */

	private void swapToLiveViewActivity() {

		
				// Calling the next Activity.
				Intent intent = new Intent(SplashActivity.this, LiveOrderActivity.class);
				startActivity(intent);
				finish();

		
		

	}


}
