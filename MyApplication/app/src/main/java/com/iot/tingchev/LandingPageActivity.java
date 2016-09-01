package com.iot.tingchev;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.iot.tingchev.mapscreen.CityMapActivity;

public class LandingPageActivity extends AppCompatActivity {

    private static Button loginBtn;
    private static Button parked;
    private static Button search;
    private static Intent intent;
    private static Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page_activity);
        mActivity = this;
        loginBtn = (Button)findViewById(R.id.loginBtn);
        parked = (Button)findViewById(R.id.parked);
        search = (Button)findViewById(R.id.search);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (new AlertDialog.Builder(mActivity))
                        .setCancelable(true)
                        .setNegativeButton("Okay", null)
                        .setMessage("You've been logged in!")
                        .create().show();
            }
        });

        //TODO replace buttons by checking shared preferences

        parked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, CarParkedActivity.class));
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, CityMapActivity.class).putExtra("entry_point", "normal"));
            }
        });
    }
}
