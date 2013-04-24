package com.example.cellularg;

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
		//TODO: create new fragmentactivity that contain the information about the place that we reach
		Log.e(TAG, "onStart");
		try {
			Log.i("fdsfsdf","works in service");
			//TODO: create function to check the location
			Thread.sleep(10000);
			Intent i = new Intent();
			i.setAction(Intent.ACTION_MAIN);
			i.addCategory(Intent.CATEGORY_LAUNCHER);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			ComponentName cn = new ComponentName(this, Main_activity_after_navigate.class);
			i.setComponent(cn);
			startActivity(i);
			stopSelf();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
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
