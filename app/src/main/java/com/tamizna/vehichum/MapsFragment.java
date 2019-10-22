package com.tamizna.vehichum;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MapsFragment extends SupportMapFragment implements OnMapReadyCallback {
    private static final String TAG = "MAPS_FRAGMENT";
    private static final int REQUEST_CHECK_SETTINGS = 0x1;
    LocationManager locationManager;
    double latitude, longitude, longitude2, latitude2;
    LatLng latLng, location;
    String jenis, nama, alamat, user;
    ProgressDialog dialog;

    ChildEventListener mChildEventListener;
    DatabaseReference mProfileRef;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getMapAsync(this);
        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Harap tunggu, sedang memuat lokasi Anda");
        dialog.setCancelable(false);
        dialog.show();

    }


    @SuppressLint("LongLogTag")
    @Override
    public void onMapReady(final GoogleMap googleMap) {

        jenis = String.valueOf(getActivity().getClass().getSimpleName());
        /*Log.e("jenis : ", jenis);*/

        if (jenis.equals("BengkelMobil")) {
            mProfileRef = FirebaseDatabase.getInstance().getReference("BengkelMobil");
        } else if (jenis.equals("BengkelMotor")) {
            mProfileRef = FirebaseDatabase.getInstance().getReference("BengkelMotor");
        } else if (jenis.equals("BensinMobil")) {
            mProfileRef = FirebaseDatabase.getInstance().getReference("SPBU");
        } else if (jenis.equals("BensinMotor")) {
            mProfileRef = FirebaseDatabase.getInstance().getReference("POMMotor");
        } else if (jenis.equals("CuciMotor")) {
            mProfileRef = FirebaseDatabase.getInstance().getReference("CuciMotor");
        } else {
            mProfileRef = FirebaseDatabase.getInstance().getReference("CuciMobil");
        }
        // check gps
        locationManager = (LocationManager)
                getContext().getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        String bestProvider = String.valueOf(locationManager.getBestProvider(criteria, true));

        LatLng depok = new LatLng(-6.3912, 106.824886);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(depok, 13));

        // show gps settings request alertdialog
        displayLocationSettingsRequest(getContext());
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        googleMap.setMyLocationEnabled(true);

        try {
            if (locationManager != null) {

                locationManager.requestLocationUpdates(bestProvider, 1000, 0, (LocationListener) new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {


                        locationManager.removeUpdates(this);

                        latitude = location.getLatitude();
                        longitude = location.getLongitude();

                        latLng = new LatLng(latitude, longitude);
                        user = "Anda berada di sini";

                        googleMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.your_location)).title(user));
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
                        googleMap.animateCamera(CameraUpdateFactory.zoomTo(17));
                        dialog.dismiss();

                        //get marker info from Firebase Database and add to map
                        addMarkersToMap(googleMap);
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {
                    }

                    @Override
                    public void onProviderEnabled(String provider) {
                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                });
            }
        } catch (Exception e) {
            Log.e("Error : ", e.getMessage());
        }

//        //get marker info from Firebase Database and add to map
//        addMarkersToMap(googleMap);
    }

    private void addMarkersToMap(final GoogleMap googleMap) {
        mChildEventListener = mProfileRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                FirebaseMarker marker = dataSnapshot.getValue(FirebaseMarker.class);
                nama = marker.getNama();
                alamat = marker.getAlamat();
                longitude2 = marker.getLongitude();
                latitude2 = marker.getLatitude();
                String jamOp = marker.getJamBuka() + " - " + marker.getJamTutup();
                location = new LatLng(latitude2, longitude2);


                System.out.println("lat cur : " + latitude);
                System.out.println("long cur : " + longitude);

                System.out.println("lat loc : " + latitude2);
                System.out.println("long loc : " + longitude2);

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(location).title(nama).snippet(alamat).icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView("Custom Marker")));

                Context context = getContext();
                MyInfoWindowAdapter myInfoWindowAdapter = new MyInfoWindowAdapter(context, jamOp);
                googleMap.setInfoWindowAdapter(myInfoWindowAdapter);
                googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        DetailFragment bottomSheetFragment = new DetailFragment();
                    }
                });

                Marker m = googleMap.addMarker(markerOptions);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void displayLocationSettingsRequest(Context context) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.i(TAG, "All location settings are satisfied.");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            Log.i(TAG, "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });
    }

    private Bitmap getMarkerBitmapFromView(String name) {

        View customMarkerView = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.map_marker, null);

        ImageView logo = customMarkerView.findViewById(R.id.markerlogo);

        if (jenis.equals("BengkelMobil")) {
            logo.setImageResource(R.drawable.bengkel_location);
        } else if (jenis.equals("BengkelMotor")) {
            logo.setImageResource(R.drawable.bengkel_location);
        } else if (jenis.equals("BensinMobil")) {
            logo.setImageResource(R.drawable.spbu_location);
        } else if (jenis.equals("BensinMotor")) {
            if (nama.contains("SPBU")) {
                logo.setImageResource(R.drawable.spbu_location);
            } else {
                logo.setImageResource(R.drawable.eceran_location);
            }
        } else if (jenis.equals("CuciMotor")) {
            logo.setImageResource(R.drawable.cuci_location);
        } else {
            logo.setImageResource(R.drawable.cuci_location);
        }

        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);
        return returnedBitmap;
    }

}
