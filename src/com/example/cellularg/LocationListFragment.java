package com.example.cellularg;

import before_nivegate.Fragment_main_for_place;
import before_nivegate.Fragment_tab_for_place;
import model.FragmentTabTutorialApplication;
import model.LocationModelListAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

public class LocationListFragment extends ListFragment {
    
   
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Activity activity = getActivity();
        
        if (activity != null) {
            ListAdapter listAdapter = new LocationModelListAdapter(activity, FragmentTabTutorialApplication.sLocations);
            setListAdapter(listAdapter);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Activity activity = getActivity();
        
        if (activity != null) {               
            FragmentManager fm = getFragmentManager();            
            if (fm != null) {
            	Integer selected_id = Integer.valueOf(position);
            	Fragment_main_for_place main_fargment = new Fragment_main_for_place();
                FragmentTabTutorialApplication.place_or_route = true;
                FragmentTabTutorialApplication.setCurrentID(selected_id);
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_content_sec, new Fragment_tab_for_place());
                ft.replace(R.id.fragment_content, main_fargment);
                ft.commit();
            }
        }
    }
       
}
