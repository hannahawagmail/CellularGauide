package com.example.cellularg;

import java.util.ArrayList;

import network.ConnectionControl;
import network.SystemMode;
import network.SystemObject;
import model.FragmentTabTutorialApplication;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service
{
private static final String TAG = "BOOMBOOMTESTGPS";
private LocationManager mLocationManager = null;
private static final int LOCATION_INTERVAL = 1000;
private static final float LOCATION_DISTANCE = 10f;

private class LocationListener implements android.location.LocationListener{
    Location mLastLocation;
	public double atitude=0;
	public double lattitude=0;
	private int in_range_count;
    public LocationListener(String provider)
    {
        Log.e(TAG, "LocationListener " + provider);
        mLastLocation = new Location(provider);
    }
    @Override
    public void onLocationChanged(Location location)
    {
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
        Log.e(TAG, "onLocationChanged: " + location);
        mLastLocation.set(location);
        atitude=Double.valueOf(location.getLongitude());
		lattitude=Double.valueOf(location.getLatitude());
		float d=distinationInMeters(lattitude,atitude,placeLatt,placedAtt );
		ArrayList<Double> send_to_server = new ArrayList<Double>();
		send_to_server.add(atitude);
		send_to_server.add(lattitude);
		send_to_server.add(placedAtt);
		send_to_server.add(placeLatt);
		Object msgToServ = new SystemObject(send_to_server,SystemMode.PRINT_DISTANCE);
		ConnectionControl.sendToServer(msgToServ);
		if(lattitude!=0 && d<80)
		{
			in_range_count++;
			if(in_range_count==3)
				stopSelf();
		}
		else in_range_count =0;
    }
    @Override
    public void onProviderDisabled(String provider)
    {
        Log.e(TAG, "onProviderDisabled: " + provider);            
    }
    @Override
    public void onProviderEnabled(String provider)
    {
        Log.e(TAG, "onProviderEnabled: " + provider);
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {
        Log.e(TAG, "onStatusChanged: " + provider);
    }
	public float distinationInMeters(double lat1, double lng1, double lat2, double lng2) {
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
LocationListener[] mLocationListeners = new LocationListener[] {
        new LocationListener(LocationManager.GPS_PROVIDER),
        new LocationListener(LocationManager.NETWORK_PROVIDER)
};
@Override
public IBinder onBind(Intent arg0)
{
    return null;
}
@Override
public int onStartCommand(Intent intent, int flags, int startId)
{
    Log.e(TAG, "onStartCommand");
    super.onStartCommand(intent, flags, startId);       
    return START_STICKY;
}
@Override
public void onCreate()
{
    Log.e(TAG, "onCreate");
    if(MainActivity.debugMode)
    {
    	try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	stopSelf();
    }
    initializeLocationManager();
    try {
        mLocationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                mLocationListeners[1]);
    } catch (java.lang.SecurityException ex) {
        Log.i(TAG, "fail to request location update, ignore", ex);
    } catch (IllegalArgumentException ex) {
        Log.d(TAG, "network provider does not exist, " + ex.getMessage());
    }
    try {
        mLocationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                mLocationListeners[0]);
    } catch (java.lang.SecurityException ex) {
        Log.i(TAG, "fail to request location update, ignore", ex);
    } catch (IllegalArgumentException ex) {
        Log.d(TAG, "gps provider does not exist " + ex.getMessage());
    }
}
@Override
public void onDestroy()
{
    Log.e(TAG, "onDestroy");
    super.onDestroy();
    if (mLocationManager != null) {
        for (int i = 0; i < mLocationListeners.length; i++) {
            try {
                mLocationManager.removeUpdates(mLocationListeners[i]);
            } catch (Exception ex) {
                Log.i(TAG, "fail to remove location listners, ignore", ex);
            }
        }
    }
    Intent i = new Intent();
	i.setAction(Intent.ACTION_MAIN);
	i.addCategory(Intent.CATEGORY_LAUNCHER);
	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	ComponentName cn = new ComponentName(this, Main_activity_after_navigate.class);
	i.setComponent(cn);
	startActivity(i);
	//stopSelf();
} 
private void initializeLocationManager() {
    Log.e(TAG, "initializeLocationManager");
    if (mLocationManager == null) {
        mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
    }
}

}
/*package com.example.cellularg;

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
	private GPSLocation gps_location;
	double lattitude=0,longtitude=0;
	private boolean placeReached=false;
	@Override
	public IBinder onBind(Intent intent) {
		
		return null;
	}
	
	@Override
	public void onCreate() {
		Toast.makeText(this, "My Service Created", Toast.LENGTH_LONG).show();
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
		gps_location = new GPSLocation();
		locationManager =(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 
        		0, 
        		0,
        		gps_location
        		);
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
					while (lattitude==0 || longtitude==0)
					{
						lattitude=gps_location.lattitude;
						longtitude=gps_location.atitude;
					}
					float d=distinationInMeters(lattitude,longtitude,placeLatt,placedAtt );
					Log.e(TAG, "=====> "+d);
					if(lattitude!=0 && d<150)
					{
						placeReached=true;
					}
				}
				locationManager.removeUpdates(gps_location);
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
*/