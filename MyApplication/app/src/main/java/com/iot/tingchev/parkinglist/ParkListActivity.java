package com.iot.tingchev.parkinglist;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.iot.tingchev.R;
import com.iot.tingchev.mapscreen.CityMapActivity;

import java.util.ArrayList;
import java.util.List;

public class ParkListActivity extends ListActivity{

    /**
     * Updates the screen state (current list and other views) when the
     * content changes.
     */
    @Override
    public void onContentChanged() {
        super.onContentChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.park_list_acivity);

        List<ParkingSiteObject> parkingSites = new ArrayList<ParkingSiteObject>();

        for(int i=0; i<10; i++) {
            parkingSites.add(new ParkingSiteObject()
                    .setSiteName("Site " + ((char) (65 + i)))
                    .setLocation("location " + (1 + i))
                    .setOccupied(0).setCapacity(5 * (i + 1)));
        }
        setListAdapter(new SitesAdapter(this, R.layout.parklist_layout, parkingSites));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent openSite = new Intent(this, CityMapActivity.class);
        startActivity(openSite);
    }
}
