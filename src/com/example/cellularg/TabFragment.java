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
        return view;
    }
}