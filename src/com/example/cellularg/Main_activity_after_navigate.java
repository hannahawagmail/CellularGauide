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
import model.RouteModel;
import model.StationModel;
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
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

public class Main_activity_after_navigate extends FragmentActivity implements ChatIF{
	private static final int AUDIO = 0;
	private static final int IMAGE = 1;
	private static final int DOC = 2;
	public TcpClient client;
	public LocationManager locationManager;
	private SystemObject msgToServ;
	boolean reciveMSG = false;
	private int stationNumber;
	private int bringStation = 0;
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
		Integer i=Integer.valueOf(FragmentTabTutorialApplication.currentPlace);
		msgToServ = new SystemObject(i,SystemMode.GETSTATIONFORPLACE);
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
			case GETSTATIONFORPLACE:
				Vector<Vector> allstations = (Vector<Vector>) msgSrv.getObj();
				for(int i=0;i<allstations.size();i++)
				{
					ArrayList<String> msgValue = new ArrayList<String>(allstations.get(i));
					StationModel station = new StationModel(Integer.valueOf(msgValue.get(0)), msgValue.get(1), msgValue.get(2), msgValue.get(3), msgValue.get(4));
					FragmentTabTutorialApplication.addToStationArray(station);
				}
				ask_for_files_from_server(bringStation);
				break;
			case GETFILEFROMSERVER:
				ArrayList<Object> objectFiles = (ArrayList<Object>) msgSrv.getObj();
				SaveFileAs(objectFiles.get(0),AUDIO);
				SaveFileAs(objectFiles.get(0),IMAGE);
				SaveFileAs(objectFiles.get(0),DOC);
				this.bringStation++;
				if(bringStation < FragmentTabTutorialApplication.stations_for_place.size())
					ask_for_files_from_server(bringStation);
				else
				{
					FragmentTabTutorialApplication.currentStation = 0;
					FragmentManager fm = getSupportFragmentManager();
					if (fm != null) {
						Fragment_Head tabFragment = new Fragment_Head();
						FragmentTransaction ft = fm.beginTransaction();
						ft.replace(R.id.fragment_content_header_an, tabFragment);
						
						Fragment_Body listFragment = new Fragment_Body();
						ft.replace(R.id.fragment_content_Body_an, listFragment);
						ft.commit();
					}
		        }
				break;
		}
	}
	
	private void ask_for_files_from_server(int i) {
		// TODO Auto-generated method stub
		ArrayList<String> listFiles = new ArrayList<String>();
		listFiles.add(FragmentTabTutorialApplication.stations_for_place.get(i).PathToAudio);
		listFiles.add(FragmentTabTutorialApplication.stations_for_place.get(i).PathToImage);
		listFiles.add(FragmentTabTutorialApplication.stations_for_place.get(i).PathToDoc);
		msgToServ = new SystemObject(listFiles,SystemMode.GETFILEFROMSERVER);
		ConnectionControl.sendToServer(msgToServ);
		stationNumber = FragmentTabTutorialApplication.stations_for_place.get(i).idStation;
	}
	public void SaveFileAs(Object obj, int mode) {
			
		byte[] bytes = (byte[])obj;
		OutputStream st;
		String type = null;
		switch(mode)
		{
		case AUDIO:
			type = ".mp3";
			break;
		case IMAGE:
			type= ".jpg";
			break;
		case DOC:
			type= ".txt";
			break;
		}
		try {
			File path = Environment.getExternalStorageDirectory(); 
			String pathS = path.getPath();
			boolean success;
			success = (new File(pathS+"/CellularGuide")).mkdirs();
			if (!success) {
				//TODO: add function to exit if there any error
			}
			success = (new File(pathS+"/CellularGuide/"+FragmentTabTutorialApplication.currentPlace)).mkdirs();
			st = new FileOutputStream(path+"/CellularGuide/" +FragmentTabTutorialApplication.currentPlace+"/"+stationNumber+ type);
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