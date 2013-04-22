package com.example.cellularg;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Vector;

import com.example.cellularg.R;

import model.FragmentTabTutorialApplication;
import model.LocationModel;
import network.ChatIF;
import network.ConnectionControl;
import network.SystemMode;
import network.SystemObject;
import network.TcpClient;
import GPSclass.GPSLocation;
import after_navigate.Fragment_Body;
import after_navigate.Fragment_Head;
import after_navigate.Fragment_empty;
import after_navigate.Fragment_process_bar;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

public class Main_activity_after_navigate extends FragmentActivity implements ChatIF{
	public TcpClient client;
	public LocationManager locationManager;
	private SystemObject msgToServ;
	boolean reciveMSG = false;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main_after_navigate);
		ConnectionControl.client.setClientUI(Main_activity_after_navigate.this);
        FragmentManager fm = getSupportFragmentManager();
        if (fm != null) {
        	Fragment_empty tabFragment = new Fragment_empty();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment_content_header_an, tabFragment);
            
            Fragment_process_bar listFragment = new Fragment_process_bar();
            ft.replace(R.id.fragment_content_Body_an, listFragment);
            ft.commit();      
            }
		Integer i=Integer.valueOf(1);
		msgToServ = new SystemObject(i,SystemMode.GETFILEFROMSERVER);
		ConnectionControl.sendToServer(msgToServ);
    }
	@Override
	public void display(Object msg) {
		// TODO Progress
		SystemObject msgSrv = (SystemObject)msg;
		switch (msgSrv.getMode()){
			case GPSPOINTS:
				Log.i("999999*************", "Doneeeeeee");
				break;
			case GETFILEFROMSERVER:
				SaveFileAs(msgSrv.getObj());
		        FragmentManager fm = getSupportFragmentManager();
		        if (fm != null) {
		        	Fragment_Head tabFragment = new Fragment_Head();
		            FragmentTransaction ft = fm.beginTransaction();
		            ft.replace(R.id.fragment_content_header_an, tabFragment);
		            
		            Fragment_Body listFragment = new Fragment_Body();
		            ft.replace(R.id.fragment_content_Body_an, listFragment);
		            ft.commit();      
		            }
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