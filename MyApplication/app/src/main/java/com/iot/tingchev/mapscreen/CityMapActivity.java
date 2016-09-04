package com.iot.tingchev.mapscreen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.iot.tingchev.CarParkedActivity;
import com.iot.tingchev.R;
import com.iot.tingchev.parkinglist.ParkListActivity;

public class CityMapActivity extends FragmentActivity implements
        OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener{

    private Activity mActivity;
    private GoogleMap mMap;
    private ImageButton grab;
    private Marker spot;
    private boolean showOverlay= true;
    private GroundOverlayOptions overlay;
    private GroundOverlay park = null;
    private EditText searchBar;
    private RelativeLayout rl;
    private Button btn;
    private final double latitude = 28.5288561;
    private final double longitude = 76.8011377;
    private Animation display;
    private String entry = "";
    private LatLng site;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_map_activity);
        mActivity = this;
        grab = (ImageButton)findViewById(R.id.grab);
        searchBar = (EditText)findViewById(R.id.search_bar);
        rl = (RelativeLayout)findViewById(R.id.overlay);
        btn = (Button)findViewById(R.id.push);
        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    Toast.makeText(mActivity, v.getText().toString(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(mActivity, ParkListActivity.class));
                }
                return false;
            }
        });
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl.setVisibility(View.GONE);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(searchBar, 0);
            }
        });
        display = AnimationUtils.loadAnimation(this, R.anim.display_overlay);
        entry = getIntent().getStringExtra("entry_point");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        entry = intent.getStringExtra("entry_point");
        setCameraListener(intent);
        super.onNewIntent(intent);
    }

    @Override
    public void onBackPressed() {
        switch(entry){
            case "region" :
                startActivity(new Intent(this, ParkListActivity.class));
                break;
            case "car_parked" :
                startActivity(new Intent(this, CarParkedActivity.class));
                break;
            default:
                super.onBackPressed();
        }
    }

    protected void setCameraListener(Intent cIntent) {
//        Intent cIntent = getIntent();
        String entry = cIntent.getStringExtra("entry_point");
        GoogleMap.OnCameraChangeListener camlistener = null;

        if (entry != null){
            switch(entry){
                case "car_parked" :
                    final LatLng car = new LatLng(cIntent.getDoubleExtra("lat",0.0), cIntent.getDoubleExtra("lng",0.0));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(car, 10.5f));
                    camlistener = new GoogleMap.OnCameraChangeListener() {
                        @Override
                        public void onCameraChange(CameraPosition cameraPosition) {
                            boolean move = false;
                            if (cameraPosition.zoom<10)
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(car, 10f));

                            if (cameraPosition.target.latitude > park.getBounds().northeast.latitude) {
                                move = true;
                            }
                            if (cameraPosition.target.longitude < park.getBounds().southwest.longitude) {
                                move = true;
                            }
                            if (cameraPosition.target.longitude > park.getBounds().northeast.longitude) {
                                move = true;
                            }
                            if (cameraPosition.target.latitude < park.getBounds().southwest.latitude) {
                                move = true;
                            }
                            if (move)
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(car, 10f));
                            Log.e("zoom level", ""+mMap.getCameraPosition().zoom);
                        }
                    };
                    break;
                case "region" :
                    final LatLng center = new LatLng(cIntent.getDoubleExtra("lat",0.0), cIntent.getDoubleExtra("lng",0.0));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(center, 10.5f));
                    camlistener = new GoogleMap.OnCameraChangeListener() {
                        @Override
                        public void onCameraChange(CameraPosition cameraPosition) {
                            boolean move = false;
                            if (cameraPosition.zoom<10)
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(center, 10f));

                            if (cameraPosition.target.latitude > park.getBounds().northeast.latitude) {
                                move = true;
                            }
                            if (cameraPosition.target.longitude < park.getBounds().southwest.longitude) {
                                move = true;
                            }
                            if (cameraPosition.target.longitude > park.getBounds().northeast.longitude) {
                                move = true;
                            }
                            if (cameraPosition.target.latitude < park.getBounds().southwest.latitude) {
                                move = true;
                            }
                            if (move)
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(center, 10f));
                            Log.e("zoom level", ""+mMap.getCameraPosition().zoom);
                        }
                    };
                    break;
                case "normal" :
                default:
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(site, 10.5f));
                    camlistener = new GoogleMap.OnCameraChangeListener() {
                        @Override
                        public void onCameraChange(CameraPosition cameraPosition) {}
                    };
                    break;
            }
        }
        mMap.setOnCameraChangeListener(camlistener);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        site = new LatLng(28.613, 77.210);
        final LatLng circleOp = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(site).title("Parking SITE"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(28.213, 77.212)).title("Lonavala").draggable(true));
        overlay = new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher))
                .position(circleOp, 60000f, 60000f);
        park = mMap.addGroundOverlay(overlay);
        /*mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                boolean move = movecam;
                if (cameraPosition.zoom<10)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(circleOp, 10f));

                if (cameraPosition.target.latitude > park.getBounds().northeast.latitude) {
                    move = true;
                }
                if (cameraPosition.target.longitude < park.getBounds().southwest.longitude) {
                    move = true;
                }
                if (cameraPosition.target.longitude > park.getBounds().northeast.longitude) {
                    move = true;
                }
                if (cameraPosition.target.latitude < park.getBounds().southwest.latitude) {
                    move = true;
                }
                if (move)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(circleOp, 10f));
                Log.e("zoom level", ""+mMap.getCameraPosition().zoom);
            }
        });*/

        spot = mMap.addMarker(new MarkerOptions().position(circleOp).title("Circle OP"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(circleOp, 10.0f));
        setCameraListener(getIntent());

        grab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng pin = mMap.getCameraPosition().target;
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(circleOp, 12f));
                Log.e("Lat " + pin.latitude, "Long " + pin.longitude);
            }
        });
        mMap.setOnMarkerClickListener(this);
    }

    private Marker[] markers = new Marker[10];
    private GroundOverlay siteOverlay;

    @Override
    public boolean onMarkerClick(Marker marker) {
        int i=0;
        if (marker.getTitle().equals(spot.getTitle())){
            Log.e("Circle OP", "marker");
            if (showOverlay){
                showOverlay = false;
                try {
                    for (Marker option : markers) {
                        option.remove();
                    }
                }catch(NullPointerException npe){
                    npe.printStackTrace();
                    Log.e("Something went", "wrong");
                }
                park.remove();
            }else{
                showOverlay = true;
                park = mMap.addGroundOverlay(overlay);
                for(i=0; i<10; i++){
                    markers[i] = mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(latitude+(0.0008*i), longitude+(0.0008*i)))
                            .title(String.valueOf(i)));
                }
            }
        }
        else{
            FixedLatLngListener fixedListener = new FixedLatLngListener();
            if (marker.getAlpha() == 1f) {
                fixedListener.setMap(mMap);
                fixedListener.setCenter(marker.getPosition());
                siteOverlay = mMap.addGroundOverlay(new GroundOverlayOptions()
                        .image(fixedListener.getBmpBounds())
                        .position(marker.getPosition(), 1000f, 1000f));
                fixedListener.setOverlay(siteOverlay);
                mMap.setOnCameraChangeListener(fixedListener);
                marker.setAlpha(0.6f);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 15.0f));
            }else{
                marker.setAlpha(1.0f);
                if(siteOverlay != null)
                    siteOverlay.remove();
                mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
                    @Override
                    public void onCameraChange(CameraPosition cameraPosition) {

                    }
                });
                mMap.animateCamera(CameraUpdateFactory.zoomBy(12.0f));
            }
        }
        return false;
    }
}
