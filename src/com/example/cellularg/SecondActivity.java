package com.example.cellularg;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Vector;

import model.FragmentTabTutorialApplication;
import model.LocationModel;
import network.ChatIF;
import network.ConnectionControl;
import network.SystemMode;
import network.SystemObject;
import network.TcpClient;
import GPSclass.GPSLocation;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

public class SecondActivity extends FragmentActivity implements ChatIF{
	public TcpClient client;
	public LocationManager locationManager;
	private SystemObject msgToServ;
	boolean reciveMSG = false;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_tab);
		ConnectionControl.client.setClientUI(SecondActivity.this);
        FragmentManager fm = getSupportFragmentManager();
        if (fm != null) {
        	TabFragment tabFragment = new TabFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment_content_sec, tabFragment);
            
            LocationListFragment listFragment = new LocationListFragment();
            ft.replace(R.id.fragment_content, listFragment);
            ft.commit();
            }
        locationManager =(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 
        		0, 
        		0,
        		new GPSLocation()
        		);
    }
	@Override
	public void display(Object msg) {
		// TODO Auto-generated method stub
		SystemObject msgSrv = (SystemObject)msg;
		switch (msgSrv.getMode()){
			case GPSPOINTS:
				Log.i("999999*************", "Doneeeeeee");
				break;
			case GETFILEFROMSERVER:
				 SaveFileAs(msgSrv.getObj());
				 break;
		}
	}
	
	public void SaveFileAs(Object obj) {
			
		byte[] bytes = (byte[])obj;
		    OutputStream st;
		    try {
		    	String aa = "/storage/sdcard0/";
				boolean success;
				success = (new File(aa+"CellularGuide")).mkdirs();
				if (!success) {
				  
				}
		    	st = new FileOutputStream("/storage/sdcard0/CellularGuide/hhhhhhh" + ".mp3");
		    	st.write(bytes);			
				st.flush();		    	
				st.close();
		    }
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}