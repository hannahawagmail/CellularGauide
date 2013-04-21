package before_nivegate;

import java.util.ArrayList;

import com.example.cellularg.MyService;
import com.example.cellularg.LocationListFragment;
import com.example.cellularg.R;
import com.example.cellularg.SecondActivity;
import com.example.cellularg.TabFragment;

import model.FragmentTabTutorialApplication;
import model.LocationModel;
import model.LocationModelListAdapter;
import network.ChatIF;
import network.ConnectionControl;
import network.SystemMode;
import network.SystemObject;
import GPSclass.GPSLocation;
import android.R.integer;
import android.app.Activity;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment_main_for_route extends Fragment {
	public   Intent launchBrowser;
	private Button backButton;
	private Button startButton;
	private TextView mainTextView;
	public Intent intentMain;
	Integer position;
	ListView lv;
	private ListAdapter simpleAdpt;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_before_navigate_route, container, false);
        backButton = (Button) view.findViewById(R.id.backButton_in_main_bn_route);
        startButton = (Button)view.findViewById(R.id.startNivigate_in_main_bn_route);
        mainTextView = (TextView)view.findViewById(R.id.mainTextView_in_main_bn_route);
        
        position = FragmentTabTutorialApplication.currentPlace;
        mainTextView.setText(FragmentTabTutorialApplication.sRoutes.get(position).routeName);
        
        FragmentTabTutorialApplication.initList(FragmentTabTutorialApplication.sRoutes.get(position).idRoute);
        lv = (ListView)view.findViewById(R.id.listView1);
        Activity activity = getActivity();
        if (activity != null) {
        	simpleAdpt = new LocationModelListAdapter(activity, FragmentTabTutorialApplication.places_in_route);
        }
        lv.setAdapter(simpleAdpt);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	 
        	public void onItemClick(AdapterView<?> parentAdapter, View view, int position, long id) {
        		Activity activity = getActivity();
        		if (activity != null) {
        			ListAdapter listAdapter = lv.getAdapter();
        			LocationModel locationModel = (LocationModel) listAdapter.getItem(position);
                    Toast.makeText(activity, locationModel.discribtion, Toast.LENGTH_SHORT).show();
                    }
        	}
        });
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
				//TODO: call the waze with the first link
				getActivity().getApplicationContext().startService(new Intent(getActivity().getApplicationContext(), MyService.class));
		        goToUrl (FragmentTabTutorialApplication.sLocations.get(position).wazeLink);
			}
		});
        return view;
    }
	public void onPause()
    {
    	super.onPause();
    	Log.i("********************","in fragment main for route !!!!!!!!!");
    }
	private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

}