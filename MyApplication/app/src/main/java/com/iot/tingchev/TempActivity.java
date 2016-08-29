package com.iot.tingchev;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.iot.tingchev.parkinglist.ParkListActivity;

public class TempActivity extends AppCompatActivity {

    private LinearLayout carCard;
    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
        mActivity = this;
        carCard = (LinearLayout) findViewById(R.id.parkedCar);
        carCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mActivity, "Will open the location on map", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(mActivity, ParkListActivity.class));
            }
        });
    }
}
