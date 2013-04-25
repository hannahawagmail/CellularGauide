package com.example.cellularg;

import model.FragmentTabTutorialApplication;
import GPSclass.GPSLocation;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {
	private static final String TAG = "MyService";
	public LocationManager locationManager;
	double lattitude=0,longtitude=0;
	private boolean placeReached=false;
	@Override
	public IBinder onBind(Intent intent) {
		
		return null;
	}
	
	@Override
	public void onCreate() {
		Toast.makeText(this, "My Service Created", Toast.LENGTH_LONG).show();
        locationManager =(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 
        		0, 
        		0,
        		new GPSLocation()
        		);
		Log.e(TAG, "onCreate");
	}

	@Override
	public void onDestroy() {
		Toast.makeText(this, "My Service Stopped", Toast.LENGTH_LONG).show();
		Log.e(TAG, "onDestroy");
	}
	
	@Override
	public void onStart(Intent intent, int startid) {
		Log.e(TAG, "onStart");
		double placedAtt;
		double placeLatt;
		if(FragmentTabTutorialApplication.place_or_route==true)
		{
			placedAtt = FragmentTabTutorialApplication.sLocations.get(FragmentTabTutorialApplication.currentPlacePosition).attitude;
			placeLatt = FragmentTabTutorialApplication.sLocations.get(FragmentTabTutorialApplication.currentPlacePosition).latitude;
		}
		else{
			placedAtt = FragmentTabTutorialApplication.places_in_route.get(FragmentTabTutorialApplication.currentPlacePosition).attitude;
			placeLatt = FragmentTabTutorialApplication.places_in_route.get(FragmentTabTutorialApplication.currentPlacePosition).latitude;
		}
		while(placeReached == false)
		{
			try {
				//TODO: create function to check the location
				Thread.sleep(10000);
				if(placedAtt==0 || placeLatt==0)
					placeReached=true;
				else {
					lattitude=GPSLocation.lattitude;
					longtitude=GPSLocation.atitude;
					float d=distinationInMeters(lattitude,longtitude,placeLatt,placedAtt );
					if(lattitude!=0 && d<15)
					{
						placeReached=true;
					}
				}
			} catch (InterruptedException e) {
				//TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Intent i = new Intent();
		i.setAction(Intent.ACTION_MAIN);
		i.addCategory(Intent.CATEGORY_LAUNCHER);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		ComponentName cn = new ComponentName(this, Main_activity_after_navigate.class);
		i.setComponent(cn);
		startActivity(i);
		stopSelf();
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
