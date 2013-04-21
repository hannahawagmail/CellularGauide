package GPSclass;
import android.location.Location;
import android.location.LocationListener;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;


public class GPSLocation implements LocationListener{
	public static double atitude=0,lattitude=0;
	
	@Override
	public void onLocationChanged(Location location) {
		atitude=Double.valueOf(location.getLongitude());
		lattitude=Double.valueOf(location.getLatitude());
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
}
