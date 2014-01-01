package com.appbase.fragments;

import com.appbase.R;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class CustomDialogeFragment extends DialogFragment {
	static CustomDialogeFragment newInstance() {
		return new CustomDialogeFragment();
	}

	Button leftBtn, rightBtn;
	TextView alertMessageTxtView;
	boolean enableSingleButton;
	String alertMessage,leftBtnText,rightBtnText;
	static OnClickListener leftBtnClickListener, rightBtnClickListener;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.alert, container, false);
		alertMessageTxtView = (TextView) v.findViewById(R.id.alert_message);
		
		
		
		
		leftBtn = (Button) v.findViewById(R.id.leftButton);
		rightBtn = (Button) v.findViewById(R.id.rightButton);

		if (savedInstanceState != null) {
            // Restore last state for checked position.
		
			alertMessage = savedInstanceState.getString("alertMessageTxtView", "");
			leftBtnText = savedInstanceState.getString("leftBtnText", "");
			rightBtnText = savedInstanceState.getString("rightBtnText", "");
			enableSingleButton = savedInstanceState.getBoolean("enableSingleButton");
        }
		
		
		alertMessageTxtView.setText(alertMessage);
		if (enableSingleButton) {
			rightBtn.setVisibility(View.GONE);
		}
		leftBtn.setOnClickListener(leftBtnClickListener);
		if (!enableSingleButton) {
			rightBtn.setOnClickListener(rightBtnClickListener);
		}
		
		leftBtn.setText(leftBtnText);
		rightBtn.setText(rightBtnText);
		
		return v;
	}

	public void setData(String alertMessage, boolean enableSingleButton,
			String leftBtnText, String rightBtnText) {
		this.alertMessage = alertMessage;
		this.enableSingleButton = enableSingleButton;
		this.leftBtnText	=	leftBtnText;
		this.rightBtnText	=	rightBtnText;

	}

	public void setClickListener(OnClickListener leftButtonClickLister,
			OnClickListener rightButtonClickListener) {

		this.leftBtnClickListener = leftButtonClickLister;
		this.rightBtnClickListener = rightButtonClickListener;

	}
	
	 	@Override
	    public void onSaveInstanceState(Bundle outState) {
	        super.onSaveInstanceState(outState);
	        outState.putString("alertMessageTxtView", alertMessage);
	        outState.putString("leftBtnText", leftBtnText);
	        outState.putString("rightBtnText", rightBtnText);
	        outState.putBoolean("enableSingleButton", enableSingleButton);
	        
	    }

}