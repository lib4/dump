package com.appbase.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import com.appbase.R;

/**
 * 
 * @author aabuback
 * 
 *         This class acts a base class for all the activity which is being
 *         created in the app. All the Activities should extend this class. The
 *         most commonly used function Example: Saving the state, memory
 *         clearing etc needs to e handled only in this class.
 * 
 */
public class BaseActivity extends Activity {

	
	/**
	 * Called when the activity is starting.
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.overridePendingTransition(R.anim.enter, R.anim.exit);
		getActionBar().setHomeButtonEnabled(true);
		
	}

	/**
	 * Called after onCreate(Bundle) — or after onRestart() when the activity
	 * had been stopped, but is now again being displayed to the user.
	 */
	protected void onStart() {
		super.onStart();

	}

	/**
	 * Called after onStop() when the current activity is being re-displayed to
	 * the user (the user has navigated back to it).
	 */
	protected void onRestart() {
		super.onRestart();

	}

	/**
	 * Called after onRestoreInstanceState(Bundle), onRestart(), or onPause(),
	 * for your activity to start interacting with the user.
	 */
	protected void onResume() {
		super.onResume();

	}

	/**
	 * Called as part of the activity lifecycle when an activity is going into
	 * the background, but has not (yet) been killed.
	 */
	protected void onPause() {
		super.onPause();
		this.overridePendingTransition(R.anim.pop_enter, R.anim.pop_exit);

	}

	/**
	 * Called when you are no longer visible to the user.
	 */
	protected void onStop() {
		super.onStop();

	}

	/**
	 * Perform any final cleanup before an activity is destroyed.
	 */
	protected void onDestroy() {
		super.onDestroy();

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {

		super.onSaveInstanceState(outState);

	}
	
	/**
     * On selecting action bar icons
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	super.onOptionsItemSelected(item);
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
        case android.R.id.home:
        		finish();
        	  break; 
       
        }
        
        return false;
    }
 

}
