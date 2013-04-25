package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.cellularg.R;
import com.example.cellularg.R.drawable;

import android.app.Application;

public class FragmentTabTutorialApplication extends Application {
	//this variable set if the user goes by place or by value.
	//true if the user choose to go to specific place or false if he choose to go by route
    public static boolean place_or_route = true;
    //save the position of the corrent place array in slocation if place_or_route true. and position of place in places_in_route if place_or_route false
    public static int currentPlacePosition = 0;
    //save the array of the station position in stations_for_place
    public static int currentStationPosition = 0;

    public static ArrayList<LocationModel> sLocations = new ArrayList<LocationModel>();
    
    public static ArrayList<LocationModel> places_in_route = new ArrayList<LocationModel>();
    
    public static ArrayList<RouteModel> sRoutes = new ArrayList<RouteModel>();
    
    public static ArrayList<ArrayList<Integer>> array = new ArrayList<ArrayList<Integer>>();
    
    public static ArrayList<StationModel> stations_for_place = new ArrayList<StationModel>();
    
    public static void addToLocationModel(LocationModel location){
    	sLocations.add(location);
    }

	public static void addToRouteModel(RouteModel route) {
		// TODO Auto-generated method stub
		sRoutes.add(route);
	}

	public static void addToPathPlace(ArrayList<Integer> pathplace) {
		// TODO Auto-generated method stub
		array.add(pathplace);
	}
	
	public static void addToStationArray(StationModel station) {
		// TODO Auto-generated method stub
		stations_for_place.add(station);
	}
	
	public static void initList(int id_area) {
		places_in_route.clear();
		for(int j=0; j<array.size();j++)
			for(int k=0;k<sRoutes.size();k++)
				for(int i=0; i < sLocations.size();i++)
					if(array.get(j).get(0)==sLocations.get(i).idPlace && array.get(j).get(1)==sRoutes.get(k).idRoute&& sRoutes.get(k).idRoute==id_area)
					{
						places_in_route.add(sLocations.get(i));
					}
	}
}
