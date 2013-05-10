package after_navigate;

import java.util.ArrayList;

import com.example.cellularg.MyService;
import com.example.cellularg.R;
import com.example.cellularg.SecondActivity;

import model.FragmentTabTutorialApplication;
import network.ChatIF;
import network.ConnectionControl;
import network.SystemMode;
import network.SystemObject;
import GPSclass.GPSLocation;
import android.R.integer;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebView.FindListener;
import android.widget.Button;

public class Fragment_Head extends Fragment {
	public  Intent launchBrowser;
	private Button nextStationButton;
	private Button prevoiusStationButton;
	private Button backMainMenuButton;
	private Button nextPlaceButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_tab_header_an, container, false);
        nextStationButton = (Button) view.findViewById(R.id.next_station_an);
        prevoiusStationButton= (Button)view.findViewById(R.id.previous_station_an);
        backMainMenuButton = (Button)view.findViewById(R.id.back_main_menu_head_an);
        nextPlaceButton = (Button)view.findViewById(R.id.next_place_head_an);
        if(FragmentTabTutorialApplication.place_or_route==true)
        	nextPlaceButton.setEnabled(false);
        else if((FragmentTabTutorialApplication.currentPlacePosition+1)>=FragmentTabTutorialApplication.places_in_route.size())
        	nextPlaceButton.setEnabled(false);
        if (FragmentTabTutorialApplication.currentStationPosition==0)
        	prevoiusStationButton.setEnabled(false);
        if ((FragmentTabTutorialApplication.currentStationPosition+1)==FragmentTabTutorialApplication.stations_for_place.size())
        	nextStationButton.setEnabled(false);
        backMainMenuButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (Fragment_Body.MP.isPlaying()) {
					Fragment_Body.MP.stop();
				}
				Intent i = new Intent(view.getContext(), SecondActivity.class);
				startActivity(i);
			}
		});
        nextPlaceButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (Fragment_Body.MP.isPlaying()) {
					Fragment_Body.MP.stop();
				}
				FragmentTabTutorialApplication.currentPlacePosition++;
				FragmentTabTutorialApplication.stations_for_place.clear();
				FragmentTabTutorialApplication.currentStationPosition=0;
				getActivity().getApplicationContext().startService(new Intent(getActivity().getApplicationContext(), MyService.class));
		        goToUrl (FragmentTabTutorialApplication.places_in_route.get(FragmentTabTutorialApplication.currentPlacePosition).wazeLink);
			}
		});
        nextStationButton.setOnClickListener(new OnClickListener() {
	
        	@Override
        	public void onClick(View v) {
        		// TODO Auto-generated method stub
				if (Fragment_Body.MP.isPlaying()) {
					//Fragment_Body.seekBar.setProgress(0);
					Fragment_Body.MP.stop();
				}
			//	Fragment_Body.seekBar.setProgress(0);
				nextStationButton.setEnabled(true);
				prevoiusStationButton.setEnabled(true);
        		FragmentTabTutorialApplication.currentStationPosition++;
                if (FragmentTabTutorialApplication.currentStationPosition==0)
                	prevoiusStationButton.setEnabled(false);
                if ((FragmentTabTutorialApplication.currentStationPosition+1)==FragmentTabTutorialApplication.stations_for_place.size())
                	nextStationButton.setEnabled(false);
				FragmentManager fm = getFragmentManager();
				if (fm != null) {
					FragmentTransaction ft = fm.beginTransaction();					
					Fragment_Body listFragment = new Fragment_Body();
					ft.replace(R.id.fragment_content_Body_an, listFragment);
					ft.commit();
				}
        	}
        });
        prevoiusStationButton.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {
        		// TODO Auto-generated method stub
				if (Fragment_Body.MP.isPlaying()) {
					Fragment_Body.MP.stop();
				}
				Fragment_Body.seekBar.setProgress(0);
				nextStationButton.setEnabled(true);
				prevoiusStationButton.setEnabled(true);
        		FragmentTabTutorialApplication.currentStationPosition--;
                if (FragmentTabTutorialApplication.currentStationPosition==0)
                	prevoiusStationButton.setEnabled(false);
                if ((FragmentTabTutorialApplication.currentStationPosition+1)==FragmentTabTutorialApplication.stations_for_place.size())
                	nextStationButton.setEnabled(false);
				FragmentManager fm = getFragmentManager();
				if (fm != null) {
					FragmentTransaction ft = fm.beginTransaction();					
					Fragment_Body listFragment = new Fragment_Body();
					ft.replace(R.id.fragment_content_Body_an, listFragment);
					ft.commit();
				}
        	}
        });
        return view;
    }
	private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }
}