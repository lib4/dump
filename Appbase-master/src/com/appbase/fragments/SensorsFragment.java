package com.appbase.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.appbase.R;
import com.appbase.adapters.SensorsAdapter;

public class SensorsFragment extends BaseFragment{

	LinearLayout sensorsLayout;
	ListView sensorsList;
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
		sensorsLayout = (LinearLayout) inflater.inflate(
				R.layout.sensors_fragment, container, false);
		sensorsList	=	(ListView) sensorsLayout.findViewById(R.id.sensors_list);
		init();
		return sensorsLayout;
	}
	
	private void init(){
		
		SensorsAdapter mSensorsAdapter	=	new SensorsAdapter(this);
		sensorsList.setAdapter(mSensorsAdapter);
	}
}
