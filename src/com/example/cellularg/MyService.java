package com.example.cellularg;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {
	private static final String TAG = "MyService";
	
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
			ComponentName cn = new ComponentName(this, SecondActivity.class);
			i.setComponent(cn);
			startActivity(i);
			stopSelf();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
