package com.example.isabellamaki.mp6;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "FirstFragment";

    private static final float ZOOM = 20f;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1423;
    private LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(new LatLng(-40, -168),
            new LatLng(71, 136));

    private Boolean mLocationPermission = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private PlaceAutocomplete mPlaceAutocomplete;
    private GoogleApiClient mGoogleApiClient;
    private PlaceInfo mPlace;
    private AutoCompleteTextView mSearch;
    private ImageView mInfo;
    private Marker mMarker;

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    private void init() {
        if (mGoogleApiClient == null || !mGoogleApiClient.isConnected()) {
            try {
                mGoogleApiClient = new GoogleApiClient
                        .Builder(this)
                        .addApi(Places.GEO_DATA_API)
                        .addApi(Places.PLACE_DETECTION_API)
                        .enableAutoManage(this, this)
                        .build();
                mGoogleApiClient.connect();
            } catch (Exception e) {
                e.printStackTrace();
            }

            mSearch.setOnItemClickListener(mAutocompleteClickListener);
            mPlaceAutocomplete = new PlaceAutocomplete(this,
                    Places.getGeoDataClient(this.getApplicationContext()),
                    LAT_LNG_BOUNDS, null);
            mSearch.setAdapter(mPlaceAutocomplete);

            mSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent keyEvent) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH
                            || actionId == EditorInfo.IME_ACTION_DONE
                            || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                            || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {
                        geoLocate();
                    }
                    return false;
                }
            });

            mInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if(mMarker.isInfoWindowShown()) {
                            mMarker.hideInfoWindow();
                        } else {
                            mMarker.showInfoWindow();
                        }
                    } catch (NullPointerException e) {
                        Log.e(TAG, "onClick: NullPointerException: " + e.getMessage() );
                    }
                }
            });
            hideSoftKeyboard();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.first_fragment);
        mSearch = (AutoCompleteTextView) findViewById(R.id.search);
        mInfo = (ImageView) findViewById(R.id.ic_info);
        getLocationPermission();
        mSearch = (AutoCompleteTextView) findViewById(R.id.search);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    private void initMap(){
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(FirstFragment.this);
    }


    @Override
    public void onMapReady(GoogleMap map) {
        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady: map is ready");
        mMap = map;

        if (mLocationPermission) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            init();
        }
    }


    private void getLocationPermission() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(), COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mLocationPermission = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this, permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this, permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int request, @NonNull String[] permissions,
                                           @NonNull int[] results) {
        mLocationPermission = false;
        switch (request) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (results.length > 0) {
                    for (int i = 0; i < results.length; i++) {
                        if (results[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermission= false;
                            return;
                        }
                    }
                    mLocationPermission = true;
                    initMap();
                }
            }
        }
    }


    private void getDeviceLocation() {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (mLocationPermission) {
                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Location currentLocation = (Location) task.getResult();
                            moveCamera(new LatLng(currentLocation.getLatitude(),
                                            currentLocation.getLongitude()), ZOOM,
                                    "Location");

                        } else {
                            Toast.makeText(FirstFragment.this,
                                    "Location is not available", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }
    }


    private void geoLocate() {
        String searchString = mSearch.getText().toString();
        Geocoder geocoder = new Geocoder(FirstFragment.this);
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(searchString, 1);
        } catch (IOException e) {
            Log.e(TAG, "geoLocate: IOException: " + e.getMessage());
        }

        if (list.size() > 0) {
            Address address = list.get(0);
            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), ZOOM,
                    address.getAddressLine(0));
        }
    }


    private void moveCamera(LatLng latLng, float zoom, PlaceInfo placeInfo){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        mMap.clear();
        mMap.setInfoWindowAdapter(new InfoWindow(FirstFragment.this));
        if (placeInfo != null) {
            try {
                String info = "Address: " + placeInfo.getAddress() + "\n" +
                        "Phone Number: " + placeInfo.getPhoneNumber() + "\n" +
                        "Website: " + placeInfo.getWebsiteUri() + "\n" +
                        "Price Rating: " + placeInfo.getRating() + "\n";
                MarkerOptions options = new MarkerOptions()
                        .position(latLng)
                        .title(placeInfo.getName())
                        .snippet(info);
                mMarker = mMap.addMarker(options);
            } catch (NullPointerException e) {
                Log.e(TAG, "moveCamera: NullPointerException: " + e.getMessage());
            }
        } else {
            mMap.addMarker(new MarkerOptions().position(latLng));
        }
        hideSoftKeyboard();
    }


    private void moveCamera(LatLng latLng, float zoom, String title){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        if (!title.equals("Location")) {
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title);
            mMap.addMarker(options);
        }
        hideSoftKeyboard();
    }


    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams
                .SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
            hideSoftKeyboard();
            final AutocompletePrediction item = mPlaceAutocomplete.getItem(pos);
            final String placeId = item.getPlaceId();
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mPlaceInfoCallback);
        }
    };


    private ResultCallback<PlaceBuffer> mPlaceInfoCallback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(@NonNull PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                places.release();
                return;
            }

            final Place place = places.get(0);

            try {
                mPlace = new PlaceInfo();
                mPlace.setName(place.getName().toString());
                mPlace.setAddress(place.getAddress().toString());
                mPlace.setId(place.getId());
                mPlace.setLatlng(place.getLatLng());
                mPlace.setRating(place.getRating());
                mPlace.setPhoneNumber(place.getPhoneNumber().toString());
                mPlace.setWebsiteUri(place.getWebsiteUri());
            } catch (NullPointerException e) {
                Log.e(TAG, "onResult: NullPointerException: " + e.getMessage() );
            }

            moveCamera(new LatLng(place.getViewport().getCenter().latitude,
                    place.getViewport().getCenter().longitude), ZOOM, mPlace);
            places.release();
        }
    };
}