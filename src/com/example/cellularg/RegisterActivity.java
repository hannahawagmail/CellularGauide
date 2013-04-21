package com.example.cellularg;

import java.util.ArrayList;

import network.ChatIF;
import network.ConnectionControl;
import network.SystemMode;
import network.SystemObject;
import network.TcpClient;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity implements ChatIF{
	public TcpClient client;
	public Button cancelButton;
	public Button registerButton;
	private EditText regPassword;
	private EditText regUserEmail;
	private EditText regFName;
	private EditText regLName;
	SystemObject msgToServ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.register_user);
	        ConnectionControl.client.setClientUI(RegisterActivity.this);
	        regUserEmail = (EditText) findViewById(R.id.reg_email);
	        regPassword = (EditText) findViewById(R.id.reg_password);
	        regFName = (EditText) findViewById(R.id.reg_fname);
	        regLName = (EditText) findViewById(R.id.reg_lname);
	        registerButton = (Button) findViewById(R.id.register_btn);
	        cancelButton = (Button) findViewById(R.id.cancel_btn);
	        
	        registerButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ArrayList<String> msgValue = new ArrayList<String>();
					msgValue.add(regUserEmail.getText().toString());
					msgValue.add(regPassword.getText().toString());
					msgValue.add(regFName.getText().toString());
					msgValue.add(regLName.getText().toString());
					msgToServ = new SystemObject(msgValue,SystemMode.REGISTER);
					ConnectionControl.sendToServer(msgToServ);
				}
			});
	        cancelButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i = new Intent(getApplicationContext(), MainActivity.class);
			        startActivity(i);
				}
			});
	}
	@Override
	public void display(Object msg) {
		// TODO Auto-generated method stub
		SystemObject msgSrv = (SystemObject)msg;
		switch (msgSrv.getMode()){
			case REGISTER:
				final ArrayList<String> msgValue = (ArrayList<String>) msgSrv.getObj();
				if(msgValue.get(0).equalsIgnoreCase("false"))
				{
					RegisterActivity.this.runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							Toast.makeText(getApplicationContext(), msgValue.get(1).toString(),Toast.LENGTH_SHORT).show();
						}
					});
				}else{
					Intent i = new Intent(getApplicationContext(), MainActivity.class);
			        startActivity(i);
				}
				break;
		}
	}
}