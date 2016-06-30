package com.example.louise.test;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CoinActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    private Button searchcoin;
    Double la, lo;

    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    SupportMapFragment mFragment;
    GoogleMap mGoogleMap;

    Marker currLocationMarker;
    List<Double> latitude = new ArrayList<>();
    List<Double> longitude = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin);
        getWindow().setBackgroundDrawableResource(R.drawable.bg);

//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.coinmap);
        searchcoin = (Button) findViewById(R.id.searchcoin);
        searchcoin.setTextColor(0xffffffff);

        mFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.coinmap);

        mFragment.getMapAsync(this);

        searchcoin.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                        mGoogleApiClient);
                if (mLastLocation != null) {
                    onLocationChanged(mLastLocation);
                }
            }
        });




//        GroundOverlay go = mGoogleMap.addGroundOverlay(new GroundOverlayOptions()
//                .image(BitmapDescriptorFactory.fromResource(R.drawable.searchrange))
//                .position(new LatLng(24.987155, 121.576507), 500f)
//                .transparency(0.5f));
//        Fragment m = (Fragment) findViewById(R.id.coinmap);

    }

    @Override
    public void onMapReady(GoogleMap gMap) {
        mGoogleMap = gMap;
        mGoogleMap.setMyLocationEnabled(true);
        buildGoogleApiClient();
        mGoogleApiClient.connect();
        mGoogleMap.getUiSettings().setZoomGesturesEnabled(false); // 縮放手勢
        mGoogleMap.getUiSettings().setScrollGesturesEnabled(false); // 捲動 (平移)手勢
        mGoogleMap.getUiSettings().setRotateGesturesEnabled(false); // 兩指放在地圖上，然後開始旋轉劃圈
        mGoogleMap.getUiSettings().setCompassEnabled(false); // 左上指南針
        mGoogleMap.getUiSettings().setZoomControlsEnabled(false); // 縮放控制項
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false); // 右下角我的位置
        mGoogleMap.getUiSettings().setMapToolbarEnabled(false); // 地圖工具列
        mGoogleMap.getUiSettings().setTiltGesturesEnabled(false); // 停用傾斜手勢

        //maker infowindows click event
        mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Toast.makeText(CoinActivity.this, "Onclick ", Toast.LENGTH_SHORT);
            }
        });

        la = 26.22588;
        lo = 120.2834;
        if (la>26.22588 || la <23.58 || lo > 124.349 || lo <120.2834) {
            Toast.makeText(CoinActivity.this, "目前所在位置已超過領土範圍囉 ><", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            LatLng whole = new LatLng(la, lo);
            BitmapDescriptor image = BitmapDescriptorFactory.fromResource(R.drawable.cbg);
            GroundOverlay groundOverlay = mGoogleMap.addGroundOverlay(new GroundOverlayOptions().image(image).anchor(0, 0).position(whole, 350000f, 500000f).transparency(0f));
        }

    }

    protected synchronized void buildGoogleApiClient() {
//        Toast.makeText(this,"API 建立完成",Toast.LENGTH_SHORT).show();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(Bundle bundle) {
//        Toast.makeText(this,"連線中...",Toast.LENGTH_SHORT).show();
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            //place marker at current position
            //mGoogleMap.clear();
//            latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
//            markerOptions.position(latLng);
//            markerOptions.title("我在這裡");


            Double maxlat = mLastLocation.getLatitude() + 0.005;
            Double minlat = mLastLocation.getLatitude() - 0.005;
            Double maxlon = mLastLocation.getLongitude() + 0.006;
            Double minlon = mLastLocation.getLongitude() - 0.006;
            Double resultlat = 0.0;
            Double resultlon = 0.0;
            Random random = new Random();

            for (int ii = 0; ii < 10; ii++) {
                resultlat = minlat + random.nextDouble() * (maxlat - minlat);
                resultlat = ((int) (resultlat * 1000000)) / 1000000.0;
                latitude.add(resultlat);

                resultlon = minlon + random.nextDouble() * (maxlon - minlon);
                resultlon = ((int) (resultlon * 1000000)) / 1000000.0;
                longitude.add(resultlon);
            }

//            latitude.add(mLastLocation.getLatitude());
//            longitude.add(mLastLocation.getLongitude());


//            Toast.makeText(getApplicationContext(), resultlat.toString() + ", " + resultlon.toString(), Toast.LENGTH_SHORT).show();
            int ss = latitude.size();
            for (int l = 0; l < ss; l++) {
                final LatLng po = new LatLng(latitude.get(l), longitude.get(l));
                markerOptions.position(po);
                if (latitude.get(l) < mLastLocation.getLatitude()+0.0003 && longitude.get(l) < mLastLocation.getLongitude()+0.0003) {
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.coin));
                } else {
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.coin2));
                }
                currLocationMarker = mGoogleMap.addMarker(markerOptions);
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(po, 15));
            }
        }


        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000); //5 seconds
        mLocationRequest.setFastestInterval(3000); //3 seconds
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        //mLocationRequest.setSmallestDisplacement(0.1F); //1/10 meter

//        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);



    }
    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this,"onConnectionSuspended",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this,"onConnectionFailed",Toast.LENGTH_SHORT).show();
    }

    public void onLocationChanged(Location location) {
        Toast.makeText(this, "回到目前範圍", Toast.LENGTH_SHORT).show();
        //place marker at current position
        //mGoogleMap.clear();
        if (currLocationMarker != null) {
            currLocationMarker.remove();
        }
//        latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(latLng);
//        markerOptions.title("我在這裡");
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);

        int ss = latitude.size();
        for (int l = 0; l < ss; l++) {
            final LatLng po = new LatLng(latitude.get(l), longitude.get(l));
            markerOptions.position(po);
            if (latitude.get(l) < mLastLocation.getLatitude() + 0.0003 && longitude.get(l) < mLastLocation.getLongitude() + 0.0003) {
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.coin));
            } else {
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.coin2));
            }

            currLocationMarker = mGoogleMap.addMarker(markerOptions);
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(po, 15));


//    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
//        currLocationMarker = mGoogleMap.addMarker(markerOptions);



            //zoom to current position:
//            CameraPosition cameraPosition = new CameraPosition.Builder()
//                    .target(po).zoom(15).build();
//
//            mGoogleMap.animateCamera(CameraUpdateFactory
//                    .newCameraPosition(cameraPosition));
        }
        //If you only need one location, unregister the listener
        //LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);

    }


}