package model;


import java.util.ArrayList;

import com.example.cellularg.R;
import com.example.cellularg.R.id;
import com.example.cellularg.R.layout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LocationModelListAdapter extends BaseAdapter {

    private class ViewHolder {
        public TextView textView;
    }
    
    private ArrayList<LocationModel> mLocations;
    private LayoutInflater  mInflater;
    
    public LocationModelListAdapter(Context context, ArrayList<LocationModel> locations) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLocations = locations;
    }
    
    @Override
    public int getCount() {
        if (mLocations != null) {
            return mLocations.size();
        }
        
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mLocations != null && position >= 0 && position < getCount()) {
            return mLocations.get(position);
        }
        
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (mLocations != null && position >= 0 && position < getCount()) {
            return mLocations.get(position).idPlace;
        }
        
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        
        View       view = convertView;
        ViewHolder viewHolder;
        
        if (view == null) {
            view = mInflater.inflate(R.layout.item_location_list, parent, false);
            
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) view.findViewById(R.id.list_label);
            
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }
        
        LocationModel locationModel = mLocations.get(position);
        
        viewHolder.textView.setText(locationModel.placeName);
        
        return view;
    }

}
