package com.example.cellularg;

import network.ConnectionControl;
import network.SystemMode;
import network.SystemObject;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TabFragment extends Fragment {
	public Button list_view_tab;
	public Button list_view_route;
	public Button list_view_place;

    private static final int ROUTE_STATE = 0x1;
    private static final int PLACE_STATE = 0x2;
    private int mTabState;
    
	SystemObject msgToServ;

    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
        list_view_route = (Button) view.findViewById(R.id.list_view_route);
        list_view_route.setOnClickListener(new OnClickListener() {
        	
			public void onClick(View v) {
				if (mTabState != ROUTE_STATE) {
		            mTabState = ROUTE_STATE;
		            FragmentManager fm = getFragmentManager();
		            
		            if (fm != null) {
		                FragmentTransaction ft = fm.beginTransaction();
		                ft.replace(R.id.fragment_content, new RouteListFragment());
		                ft.commit();
		            }
				}
			}
        });
        list_view_place = (Button) view.findViewById(R.id.list_view_place);
        list_view_place.setOnClickListener(new OnClickListener() {
        	
        	public void onClick(View v) {
				if (mTabState != PLACE_STATE) {
		            mTabState = PLACE_STATE;
		            FragmentManager fm = getFragmentManager();
		            
		            if (fm != null) {
		                FragmentTransaction ft = fm.beginTransaction();
		                ft.replace(R.id.fragment_content, new LocationListFragment());
		                ft.commit();
		            }
				}
			}
		});
        list_view_tab = (Button) view.findViewById(R.id.list_view_tab);
        list_view_tab.setOnClickListener(new OnClickListener() {
        	
			public void onClick(View v) {
		   /*	double lattitude,longtitude;
				lattitude=GPSLocation.lattitude;
				longtitude=GPSLocation.atitude;
				//ConnectionControl.client.setClientUI((ChatIF)getActivity());
				ArrayList<String> msgValue = new ArrayList<String>();
				msgValue.add(Double.toString(longtitude));
				msgValue.add(Double.toString(lattitude));
				float d=distinationInMeters(lattitude,longtitude,32.9134697284472,35.287984217295545 );
				if(lattitude!=0 && d<15)
				{
					msgValue.add("IN");
					
				}
				else
					msgValue.add("OUT");
				msgValue.add(Float.toString(d));
				msgToServ = new SystemObject(msgValue,SystemMode.GPSPOINTS);
				ConnectionControl.sendToServer(msgToServ);
				*/			
			}
		});
        return view;
    }
    
    
public static float distinationInMeters(double lat1, double lng1, double lat2, double lng2) {
		
	    double earthRadius = 3958.75;
	    double dLat = Math.toRadians(lat2-lat1);
	    double dLng = Math.toRadians(lng2-lng1);
	    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
	               Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
	               Math.sin(dLng/2) * Math.sin(dLng/2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    double dist = earthRadius * c;

	    int meterConversion = 1609;

	    return new Float(dist * meterConversion).floatValue();
}
}