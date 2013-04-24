package after_navigate;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

import com.example.cellularg.MyService;
import com.example.cellularg.LocationListFragment;
import com.example.cellularg.R;
import com.example.cellularg.SecondActivity;
import com.example.cellularg.TabFragment;

import model.FragmentTabTutorialApplication;
import network.ChatIF;
import network.ConnectionControl;
import network.SystemMode;
import network.SystemObject;
import GPSclass.GPSLocation;
import android.R.integer;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class Fragment_Body extends Fragment {
	
	private Button buttonPlayStop;
	private SeekBar seekBar;
	private MediaPlayer MP;
	private  Handler handler;
	private ImageView imageView;
	private int currentStation;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_body_an, container, false);
        handler = new Handler();
        currentStation = FragmentTabTutorialApplication.stations_for_place.get(FragmentTabTutorialApplication.currentStation).idStation;
		File path = Environment.getExternalStorageDirectory(); 
		String pathS = path.getPath();
        MP = MediaPlayer.create(view.getContext(), Uri.parse(pathS+"/CellularGuide/" +FragmentTabTutorialApplication.currentPlace+"/"+currentStation+ ".mp3"));
//        File imgFile = new  File(pathS+"/CellularGuide/" +FragmentTabTutorialApplication.currentPlace+"/"+currentStation+ ".jpg");
//        BitmapFactory.Options bmo = new BitmapFactory.Options();
//        bmo.inPreferredConfig = Config.ARGB_8888;
//        if(imgFile.exists()){
//            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath(), bmo);
//
//            imageView = (ImageView) view.findViewById(R.id.imageView1);
//            imageView.setImageBitmap(myBitmap);
//
//        }
        //TODO: fix the image!! 
        imageView = (ImageView) view.findViewById(R.id.imageView1);
        imageView.setImageURI(Uri.parse(pathS+"/CellularGuide/" +FragmentTabTutorialApplication.currentPlace+"/"+currentStation+ ".jpg"));
        buttonPlayStop = (Button) view.findViewById(R.id.ButtonPlayStop);
        seekBar = (SeekBar) view.findViewById(R.id.SeekBar01);
    	seekBar.setMax(MP.getDuration());
        buttonPlayStop.setOnClickListener(new OnClickListener() {@Override public void onClick(View v) {buttonClick();}});
		seekBar.setOnTouchListener(new OnTouchListener() {@Override public boolean onTouch(View v, MotionEvent event) {
		seekChange(v);
		return false; }
		});
        return view;
    }
    public void startPlayProgressUpdater() {
    	seekBar.setProgress(MP.getCurrentPosition());
    	
		if (MP.isPlaying()) {
			Runnable notification = new Runnable() {
		        public void run() {
		        	startPlayProgressUpdater();
				}
		    };
		    handler.postDelayed(notification,1000);
    	}else{
    		MP.pause();
    		buttonPlayStop.setText("play");
    		seekBar.setProgress(0);
    	}
    } 
 
    // This is event handler thumb moving event
    private void seekChange(View v){
    	if(MP.isPlaying()){
	    	SeekBar sb = (SeekBar)v;
			MP.seekTo(sb.getProgress());
		}
    }
 
    // This is event handler for buttonClick event
    private void buttonClick(){
        if (buttonPlayStop.getText().equals("play")) {
            buttonPlayStop.setText("pause");
            try{
            	MP.start();
                startPlayProgressUpdater(); 
            }catch (IllegalStateException e) {
            	MP.pause();
            }
        }else {
            buttonPlayStop.setText("play");
            MP.pause();
        }
    }
	
}