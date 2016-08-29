package com.iot.tingchev.parkinglist;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.iot.tingchev.R;

import java.util.List;

/**
 * Created by Harshit on 27/08/2016.
 */
public class SitesAdapter extends ArrayAdapter {

    private List<ParkingSiteObject> mDataset;
    private Activity activity;
    private TextView siteName;
    private TextView location;
    private TextView status;


    public SitesAdapter(Activity activity, int resource, List<ParkingSiteObject> objects) {
        super(activity, resource, objects);
        mDataset = objects;
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listRootView;
        if (convertView == null)
            listRootView = activity.getLayoutInflater().inflate(R.layout.parklist_layout, null);
        else {
            listRootView = convertView;
        }
        siteName = (TextView)listRootView.findViewById(R.id.site_name);
        location = (TextView)listRootView.findViewById(R.id.site_location);
        status = (TextView)listRootView.findViewById(R.id.parking_status);

        siteName.setText(mDataset.get(position).getSiteName());
        location.setText(mDataset.get(position).getLocation());
        status.setText(mDataset.get(position).getOccupied() + " / " + mDataset.get(position).getCapacity());

        return listRootView;
    }


}
