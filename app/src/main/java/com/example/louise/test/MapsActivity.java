package com.example.louise.test;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v4.widget.DrawerLayout;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.List;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MarkerOptions options = new MarkerOptions();
    private ArrayList<LatLng> latlngs = new ArrayList<>();
    private JSONArray placelist = null;
    private String url = "";
    public static String[] list;
    public static List<Integer> plistid = new ArrayList<Integer>();
    public static List<String> plistname = new ArrayList<String>();
    private String userid;
    private int check = 0;
    private Location currentLocation;
    private Marker currentMarker, itemMarker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


//        Toast toast = Toast.makeText(MapsActivity.this, IndexActivity.userid, Toast.LENGTH_SHORT);
//        toast.show();


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);

        String placeurl = " http://140.119.163.40:8080/Spring08/app/place ";
        String json2 = "";
        try {
            json2 = Httpconnect.httpget(placeurl);


        } catch (ProtocolException e) {
            e.printStackTrace();
        }

        int size = 0;
        try {
            placelist = new JSONArray(json2);
            size = placelist.length();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < size; i++) {

            try {

                if (placelist.getJSONObject(i).getInt("mainid") == 0) {
                    Double latitudea = placelist.getJSONObject(i).getDouble("latitude");
                    Double longitude = placelist.getJSONObject(i).getDouble("longitude");
                    LatLng po = new LatLng(latitudea, longitude);
                    options.position(po);
                    options.title(placelist.getJSONObject(i).getString("name"));
//                    修改水滴的圖示
//                    BitmapDescriptor icon =
//                            BitmapDescriptorFactory.fromResource(R.drawable.newbie);
//                    options.icon(icon);

                    mMap.addMarker(options);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(po, 17));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        //maker infowindows click event
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                int placeid = 0;
                String placename = "";
                int num = 0;


                String ss = "";
                //find maker's id
                for (int i = 0; i < placelist.length(); i++) {

                    try {
                        if (placelist.getJSONObject(i).getString("name").equals(marker.getTitle())) {
                            placeid = placelist.getJSONObject(i).getInt("id");
                            placename = placelist.getJSONObject(i).getString("name");
                            break;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                //find subplace
                plistid.clear();
                plistname.clear();
                plistid.add(placeid);
                plistname.add(placename);

                for (int i = 0; i < placelist.length(); i++) {

                    try {
                        if (placelist.getJSONObject(i).getInt("mainid") == placeid) {
                            plistid.add(placelist.getJSONObject(i).getInt("id"));
                            plistname.add(placelist.getJSONObject(i).getString("name"));

                            break;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
//
//                for (int i = 0; i < placelist.length(); i++) {
//                    try {
//                        if (plistid.size() >1) {
//                            Intent intent = new Intent();
//                            intent.setClass(MapsActivity.this, PlacelistActivity.class);
//                            startActivity(intent);
//                            break;
//                        }
//                        else {
//                            Intent intent = new Intent();
//                            Bundle bundle = new Bundle();
//                            bundle.putInt("placeid", placeid);
//                            //將Bundle物件assign給intent
//                            intent.putExtras(bundle);
//                            intent.setClass(MapsActivity.this, PlaceActivity.class);
//                            startActivity(intent);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
                Intent intent = new Intent();
                intent.setClass(MapsActivity.this, PlacelistActivity.class);
                startActivity(intent);
            }
        });
    }
}