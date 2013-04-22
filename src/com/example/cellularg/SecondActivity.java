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
    }
	@Override
	public void display(Object msg) {
		// TODO Auto-generated method stub
		SystemObject msgSrv = (SystemObject)msg;
		switch (msgSrv.getMode()){
			case GPSPOINTS:
				Log.i("999999*************", "Doneeeeeee");
				break;
		}
	}
}