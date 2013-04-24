package com.example.cellularg;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Vector;

import model.FragmentTabTutorialApplication;
import model.LocationModel;
import model.RouteModel;
import network.ChatIF;
import network.ConnectionControl;
import network.SystemMode;
import network.SystemObject;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements ChatIF{

	private ConnectionControl connC;
	public boolean IsConnected;
	public Button loginButton;
	public Button registerButton;
	public EditText IP;
	public EditText Port;
	private EditText password;
	private EditText userEmail;
	SystemObject msgToServ;
	public String mIP;
	public int mPort;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		loginButton = (Button) findViewById(R.id.login);
		registerButton = (Button) findViewById(R.id.register);
        IP = (EditText) findViewById(R.id.IP);
        Port = (EditText) findViewById(R.id.PORT);
        password = (EditText) findViewById(R.id.password);
        userEmail = (EditText) findViewById(R.id.email);
        mIP=  "192.168.1.7";
        //mIP=  "172.20.10.5";
        //mIP=  "87.69.244.100";
        mPort=8080;
        Thread toRun = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				connC = new ConnectionControl(mIP, mPort);
				connC.setConniction();
			}
		});
		if(connC.client==null){		      
			toRun.start();
		}
        loginButton.setOnClickListener(new OnClickListener() {
        	
			public void onClick(View v) {
				ConnectionControl.client.setClientUI(MainActivity.this);
				ArrayList<String> msgValue = new ArrayList<String>();
				msgValue.add(userEmail.getText().toString());
				msgValue.add(password.getText().toString());
				msgToServ = new SystemObject(msgValue,SystemMode.LOGIN);
				ConnectionControl.sendToServer(msgToServ);
			}
		});
        registerButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
		        startActivity(i);
			}
		});
		//Intent i = new Intent(getApplicationContext(), SecondActivity.class);
        //startActivity(i);
        
	}

	@Override
	public void display(Object msg) {
		// TODO Auto-generated method stub
		SystemObject msgSrv = (SystemObject)msg;
		switch (msgSrv.getMode()){
			case LOGIN:
				final ArrayList<String> msgValue11 = (ArrayList<String>) msgSrv.getObj();
				if(msgValue11.get(0).equalsIgnoreCase("false"))
				{
					MainActivity.this.runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							Toast.makeText(getApplicationContext(), msgValue11.get(1).toString(),Toast.LENGTH_SHORT).show();
						}
					});
				}else{
					ArrayList<String> msgValue2 = new ArrayList<String>();
					msgToServ = new SystemObject(msgValue2,SystemMode.GETPLACESFROMSRVR);
					ConnectionControl.sendToServer(msgToServ);
					//Intent i = new Intent(getApplicationContext(), SecondActivity.class);
			        //startActivity(i);
				}
				break;
			case GETPLACESFROMSRVR:
			{
				Vector<Vector> allLocations = (Vector<Vector>) msgSrv.getObj();
				for(int i=0;i<allLocations.size();i++)
				{
					ArrayList<String> msgValue = new ArrayList<String>(allLocations.get(i));
					LocationModel location = new LocationModel(Integer.valueOf(msgValue.get(0)), msgValue.get(1), Integer.valueOf(msgValue.get(2)), msgValue.get(3), msgValue.get(4), msgValue.get(5), Double.valueOf(msgValue.get(6)), Double.valueOf(msgValue.get(7)), msgValue.get(8) );
					FragmentTabTutorialApplication.addToLocationModel(location);
				}
				ArrayList<String> msgValue2 = new ArrayList<String>();
				msgToServ = new SystemObject(msgValue2,SystemMode.GETROUTESFROMSRVR);
				ConnectionControl.sendToServer(msgToServ);
			}
				break;
			case GETROUTESFROMSRVR:
			{
				Vector<Vector> allroutes = (Vector<Vector>) msgSrv.getObj();
				for(int i=0;i<allroutes.size();i++)
				{
					ArrayList<String> msgValue = new ArrayList<String>(allroutes.get(i));
					RouteModel route = new RouteModel(Integer.valueOf(msgValue.get(0)), msgValue.get(1));
					FragmentTabTutorialApplication.addToRouteModel(route);
				}
				ArrayList<String> msgValue2 = new ArrayList<String>();
				msgToServ = new SystemObject(msgValue2,SystemMode.GETPATHXPLACEFROMSRVR);
				ConnectionControl.sendToServer(msgToServ);
			}
				break;
			case GETPATHXPLACEFROMSRVR:
			{
				Vector<Vector> allpathxroute = (Vector<Vector>) msgSrv.getObj();
				for(int i=0;i<allpathxroute.size();i++)
				{
					ArrayList<String> msgValue = new ArrayList<String>(allpathxroute.get(i));
					ArrayList<Integer> newList = new ArrayList<Integer>(msgValue.size()) ;
		            for (String myInt : msgValue) 
		            { 
		              newList.add(Integer.valueOf(myInt)); 
		            }
					FragmentTabTutorialApplication.addToPathPlace(newList);
				}
			}
			
			
				Intent i = new Intent(getApplicationContext(), SecondActivity.class);
		        startActivity(i);
				break;
		}
	}
	

}
