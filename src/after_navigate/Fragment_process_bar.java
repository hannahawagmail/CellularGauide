package after_navigate;

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
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Fragment_process_bar extends Fragment {
	public   Intent launchBrowser;
	public Intent intentMain;
	Integer position;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.progress_bar, container, false);
		return view;
       
    }

}