package com.appbase.fragments;

import org.json.JSONObject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.appbase.R;
import com.appbase.httphandler.HttpConstants;

public class WebViewFragment extends BaseFragment{

	JSONObject cardJsonObject;
	LinearLayout webViewFragment;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			// We have different layouts, and in one of them this
			// fragment's containing frame doesn't exist. The fragment
			// may still be created from its saved state, but there is
			// no reason to try to create its view hierarchy because it
			// won't be displayed. Note this is not needed -- we could
			// just run the code below, where we would create and return
			// the view hierarchy; it would just never be used.
			return null;
		}

		// Inflate the layout for this fragment
	
		this.cardJsonObject = MenuFragment.cardObject;
		webViewFragment = (LinearLayout) inflater.inflate(
				R.layout.webview_fragment, container, false);
		
		 WebView webview = (WebView)webViewFragment.findViewById(R.id.webview);
		 webview.getSettings().setJavaScriptEnabled(true);
		 
		 
		 webview.loadDataWithBaseURL("", cardJsonObject.optString(HttpConstants.DESCRIPTION_JKEY), "text/html", "UTF-8", "");
		
		return webViewFragment;
	}
}
