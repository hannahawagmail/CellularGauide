package before_nivegate;

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

public class Fragment_main_for_place extends Fragment {
	public   Intent launchBrowser;
	private Button backButton;
	private Button startButton;
	private TextView mainTextView;
	private TextView subTextView;
	private ImageView imageMainView;
	public Intent intentMain;
	Integer position;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_before_navigate_place, container, false);
        backButton = (Button) view.findViewById(R.id.backButton_in_main_bn);
        startButton = (Button)view.findViewById(R.id.startNivigate_in_main_bn);
        mainTextView = (TextView)view.findViewById(R.id.mainTextView_in_main_bn);
        subTextView = (TextView)view.findViewById(R.id.subTextView_in_main_bn);
        imageMainView = (ImageView)view.findViewById(R.id.mainImageView_in_main_bn);
        
        position = FragmentTabTutorialApplication.position;
        mainTextView.setText(FragmentTabTutorialApplication.sLocations.get(position).placeName);
        subTextView.setText(FragmentTabTutorialApplication.sLocations.get(position).discribtion);
        
        backButton.setOnClickListener(new OnClickListener() {
        	
        	public void onClick(View v) {
        		FragmentManager fm = getFragmentManager();
                if (fm != null) {
                	TabFragment tabFragment = new TabFragment();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.fragment_content_sec, tabFragment);
                    
                    LocationListFragment listFragment = new LocationListFragment();
                    ft.replace(R.id.fragment_content, listFragment);
                    ft.commit();
                    }
			}
		});
        startButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getActivity().getApplicationContext().startService(new Intent(getActivity().getApplicationContext(), MyService.class));
		        goToUrl (FragmentTabTutorialApplication.sLocations.get(position).wazeLink);
			}
		});
        return view;
    }
	
	@Override
	public void onPause()
    {
    	super.onPause();
    	Log.i("fdsfsdf","in activity !!!!!!!!!");
    }
	private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }
}