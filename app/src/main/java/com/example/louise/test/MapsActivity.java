package com.example.louise.test;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.PermissionChecker;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import org.json.JSONArray;
import org.json.JSONException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

        // Obtain the SupportMapFragment and get notified when the map is ready to be used

//        if (Build.VERSION.SDK_INT >= 23) { // platform version android 6
//            // Marshmallow+
//            Toast toast = Toast.makeText(MapsActivity.this, "API 23", Toast.LENGTH_SHORT);
//            toast.show();
//            runStreamWrapper();
//
//        } else {
//            // Pre-Marshmallow
//            int version = Build.VERSION.SDK_INT;
//            Toast toast = Toast.makeText(MapsActivity.this, "API " + version, Toast.LENGTH_SHORT);
//            toast.show();
//            runStream();
//        }

        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

//private static final String TAG = MainActivity.class.getSimpleName();
//
//    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 0;
//
//    @TargetApi(Build.VERSION_CODES.M)
//    private void runStreamWrapper () {
//
//        List<String> permissionsNeeded = new ArrayList<String>();
//
//        final List<String> permissionsList = new ArrayList<String>();
//        if (!addPermission(permissionsList, Manifest.permission.ACCESS_FINE_LOCATION))
//            permissionsNeeded.add("GPS");
//        if (!addPermission(permissionsList, Manifest.permission.RECORD_AUDIO))
//            permissionsNeeded.add("microphone");
//        if (!addPermission(permissionsList, Manifest.permission.CAMERA))
//            permissionsNeeded.add("camera");
//
//        if (permissionsList.size() > 0) {
//            if (permissionsNeeded.size() > 0) {
//                String message = "You need to grant access to the " + permissionsNeeded.get(0);
//                Toast toast1 = Toast.makeText(MapsActivity.this, "test1: " , Toast.LENGTH_SHORT);
//                toast1.show();
////                for (int i = 1; i < permissionsNeeded.size(); i++)
////                    message = message + ", " + permissionsNeeded.get(i);
////                showMessageOKCancel(message, new DialogInterface.OnClickListener() {
////                    @TargetApi(Build.VERSION_CODES.M)
////                    @Override
////                    public void onClick(DialogInterface dialog, int which) {
////                        requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
////                                REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
////                    }
////                });
////                return;
//            }
//            Toast toast2 = Toast.makeText(MapsActivity.this, "test2: ", Toast.LENGTH_SHORT);
//            toast2.show();
//            requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
//                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
//        }
//
//        runStream();
//    }
//
//    @TargetApi(Build.VERSION_CODES.M)
//    private boolean addPermission (List<String> permissionsList, String permission) {
//        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
//            permissionsList.add(permission);
//            if(!shouldShowRequestPermissionRationale(permission))
//                return false;
//        }
//        return true;
//    }
//
//    private void showMessageOKCancel (String message, DialogInterface.OnClickListener okListener) {
//        new AlertDialog.Builder(this)
//                .setMessage(message)
//                .setPositiveButton("OK",okListener)
//                .setNegativeButton("Cancel", null)
//                .create()
//                .show();
//    }
//
//    private void runStream() {
//        Toast.makeText(this, "Stream is running", Toast.LENGTH_LONG).show();
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        switch (requestCode) {
//            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS:
//            {
//                Map<String, Integer> perms = new HashMap<String, Integer>();
//                perms.put(Manifest.permission.RECORD_AUDIO, PackageManager.PERMISSION_GRANTED);
//                perms.put(Manifest.permission.CAMERA,PackageManager.PERMISSION_GRANTED);
//
//                for(int i = 0; i < permissions.length; i ++)
//                    perms.put(permissions[i], grantResults[i]);
//
//                if(perms.get(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
//                        && perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
//                    runStream();
//                } else {
//                    Toast.makeText(this, "Some required permissions have been denied", Toast.LENGTH_LONG).show();
//                }
//            }
//            break;
//            default:
//                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        }
//    }

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