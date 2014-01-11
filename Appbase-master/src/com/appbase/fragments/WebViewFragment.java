package com.appbase.fragments;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.appbase.R;
import com.appbase.httphandler.HttpConstants;

public class WebViewFragment extends BaseFragment{

	JSONObject cardJsonObject;
	LinearLayout webViewFragment;
	boolean loadFromUrl	=	false;
	String url;
	
	public void loadUrl(boolean loadFromUrl,String url){
		this.loadFromUrl=loadFromUrl;
		this.url	=	url;
	}
	
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

		
		 final ProgressDialog pd = ProgressDialog.show(getActivity(), "", "Loading...",true);
		 pd.setCancelable(true);
		// Inflate the layout for this fragment
	
		webViewFragment = (LinearLayout) inflater.inflate(
				R.layout.webview_fragment, container, false);
		
		 WebView webview = (WebView)webViewFragment.findViewById(R.id.webview);
		 webview.getSettings().setJavaScriptEnabled(true);
		
		 if(!loadFromUrl){
		 this.cardJsonObject = MenuFragment.cardObject;
		 	webview.loadDataWithBaseURL("", cardJsonObject.optString(HttpConstants.DESCRIPTION_JKEY), "text/html", "UTF-8", "");
		 	pd.dismiss();
		 }else{
			 webview.setWebViewClient(new WebViewClient() {
		            @Override
		            public void onPageFinished(WebView view, String url) {
		                if(pd.isShowing()&&pd!=null)
		                {
		                    pd.dismiss();
		                }
		            }
		        });
			 webview.loadUrl(url);
		 }
		return webViewFragment;
	}
}
