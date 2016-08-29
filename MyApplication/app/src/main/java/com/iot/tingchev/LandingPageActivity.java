package com.iot.tingchev;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LandingPageActivity extends AppCompatActivity {

    private static Button loginBtn;
    private static Intent intent;
    private static Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page_activity);
        mActivity = this;
        loginBtn = (Button)findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mActivity, "Clicked it", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(mActivity, TempActivity.class));
            }
        });
    }
}
