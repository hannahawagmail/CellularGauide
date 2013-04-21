package before_nivegate;

import java.util.ArrayList;

import com.example.cellularg.R;

import network.ChatIF;
import network.ConnectionControl;
import network.SystemMode;
import network.SystemObject;
import GPSclass.GPSLocation;
import android.R.integer;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Fragment_tab_for_place extends Fragment {
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_before_navigate, container, false);
        return view;
    }
}