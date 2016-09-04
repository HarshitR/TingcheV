package com.iot.tingchev.mapscreen;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Harshit on 04/09/2016.
 */
public class FixedLatLngListener implements GoogleMap.OnCameraChangeListener {

    private LatLng center;
    private GroundOverlay overlay;
    private GoogleMap map;

    public void setCenter(LatLng center){
        this.center = center;
    }

    public void setOverlay(GroundOverlay overlay){
        this.overlay = overlay;
    }

    public void setMap(GoogleMap map){
        this.map = map;
    }

    public LatLng getCenter(){
        return center;
    }

    public GroundOverlay getOverlay(){
        return overlay;
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        boolean move = false;
        if (cameraPosition.zoom<10)
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(center, 15.0f));

        if (cameraPosition.target.latitude > overlay.getBounds().northeast.latitude) {
            move = true;
        }
        if (cameraPosition.target.longitude < overlay.getBounds().southwest.longitude) {
            move = true;
        }
        if (cameraPosition.target.longitude > overlay.getBounds().northeast.longitude) {
            move = true;
        }
        if (cameraPosition.target.latitude < overlay.getBounds().southwest.latitude) {
            move = true;
        }
        if (move)
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(center, 15.0f));
        Log.e("zoom level", "" + map.getCameraPosition().zoom);
    }

    public BitmapDescriptor getBmpBounds(){
        Bitmap bmpBounds = Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmpBounds);
        Paint paint = new Paint(Paint.DITHER_FLAG);
        paint.setARGB(255, 0, 0, 0);
        canvas.drawLine( 0, 50, 0, 200, paint);
        canvas.drawLine( 50, 0, 200, 0, paint);
        canvas.drawLine( 255, 50, 255, 200, paint);
        canvas.drawLine( 50, 255, 200, 255, paint);
        return BitmapDescriptorFactory.fromBitmap(bmpBounds);
    }
}
