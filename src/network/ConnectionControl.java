package network;

import java.io.IOException;

import android.util.Log;

public class ConnectionControl{
	public static TcpClient client;
	public boolean IsConnected;
	private String ipString;
	int portString;
	public ConnectionControl(String ipString, int mPort) {
		// TODO Auto-generated constructor stub
			   this.ipString=ipString;
			   this.portString=mPort;
	}
	public void setConniction(){
		client = new TcpClient(ipString, portString);
		 try {
		    	Log.i( "***********8888", "before connection" );
				client.openConnection();
				Log.i( "***********8888", "afger connection" );
				IsConnected = true;
				} catch (Throwable e) {
					IsConnected = false;
					Log.e( getClass().getSimpleName(), e.getMessage());
				}
		        if(IsConnected == true)
		        {
		        	//Go to the next activity
		        }
		        else
		        {
		        }
	}
	public static void sendToServer(Object toServer)
	{
		try {
			if (client.isConnected()==false)
			{
				client.openConnection();
			}
			client.sendToServer(toServer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
