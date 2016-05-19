package com.singh.harsukh.mapsa;

import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    GoogleMap mGoogleMap;
    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (google_serv_check()) {
            setContentView(R.layout.activity_main);
            initMaps();
        }
    }

    private void initMaps() //setting the map fragment and loading it
    {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.MapFragment); //find the fragment by id
        mapFragment.getMapAsync(this); //loads maps asynchronously then calls the onMapsReady
    }

    public boolean google_serv_check() //checks if service is available
    {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int isAvailable = apiAvailability.isGooglePlayServicesAvailable(getApplicationContext());
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        }
        if (apiAvailability.isUserResolvableError(isAvailable)) {
            Dialog dialog = apiAvailability.getErrorDialog(MainActivity.this, isAvailable, 0);
            dialog.show();
        }
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) //called by getMapAsync
    {
        mGoogleMap = googleMap;

        if(mGoogleMap != null) {


            mGoogleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng latLng) {
                    MainActivity.this.setMarker("Local", latLng.latitude, latLng.longitude);
                }
            });

            //called when the marker is dragged
            mGoogleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                @Override
                public void onMarkerDragStart(Marker marker) {

                }

                @Override
                public void onMarkerDrag(Marker marker) {

                }

                //resets the marker position
                @Override
                public void onMarkerDragEnd(Marker marker) {

                    Geocoder gc = new Geocoder(MainActivity.this);
                    LatLng ll = marker.getPosition();
                    double lat = ll.latitude;
                    double lng = ll.longitude;
                    List<Address> list = null;
                    try {
                        list = gc.getFromLocation(lat, lng, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address add = list.get(0);
                    marker.setTitle(add.getLocality());
                    marker.showInfoWindow();
                }
            });

            //custom window designed in here too
            mGoogleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    View v = getLayoutInflater().inflate(R.layout.info_window, null); //retrieves the info_window xml layout
                    //the root in the function is set to null because no root is needed
                    TextView tvLocality = (TextView) v.findViewById(R.id.tv_locality);
                    TextView tvLat = (TextView) v.findViewById(R.id.tv_lat);
                    TextView tvLng = (TextView) v.findViewById(R.id.tv_lng);
                    TextView tvSnippet = (TextView) v.findViewById(R.id.tv_snippet);

                    //setting the textViews  use the marker to retrieve this data
                    LatLng ll = marker.getPosition();
                    tvLocality.setText(marker.getTitle());
                    tvLat.setText("Latitude: " + ll.latitude);
                    tvLng.setText("Longitude: " + ll.longitude);
                    tvSnippet.setText(marker.getSnippet());

                    return v;
                }
            });
        }


        goToLocation(38.996098, -76.851609, 15);
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return;
//            }
//        }//checks permissions and android version
//        mGoogleMap.setMyLocationEnabled(true); //this is one way of doing it

        //another way
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .addApi(LocationServices.API) //adds the api for location services
//                .addConnectionCallbacks(this) // callback method for connection
//                .addOnConnectionFailedListener(this) //checks if connection failed
//                .build();
//        mGoogleApiClient.connect();
        //calls onConnected(Bundle)

    }

    private void goToLocation(double x, double y, int zoom) //displays a particular location
    {
        LatLng ll = new LatLng(x, y);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(ll, (float) zoom);
        mGoogleMap.moveCamera(cameraUpdate);
    }

    Marker mMarker;
    Circle circle;


    public void geoLocate(View v) throws IOException //geolocation example
    {
        EditText editText = (EditText) findViewById(R.id.editText);
        String location = editText.getText().toString();
        Geocoder gc = new Geocoder(this); //geocoder object declared
        List<Address> l = gc.getFromLocationName(location, 1);//returns a list of locations of max size 1
        Address address = l.get(0); //retrieves address object
        String locality = address.getLocality();
        Toast.makeText(getApplicationContext(), locality, Toast.LENGTH_LONG).show();
        double x = address.getLatitude();
        double y = address.getLongitude();
        goToLocation(x, y, 15);
        setMarker(locality, x, y);
    }

    private void setMarker(String locality, double x, double y) {
        if(mMarker != null)
        {
            //mMarker.remove(); //removes previous markers
            removeEverything();
        }
        //creating marker
        MarkerOptions options = new MarkerOptions()
                                .title(locality)
                                .draggable(true) //drag a marker
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)) //set custom icon
                                .position(new LatLng(x, y)) //get position of marker
                                .snippet("I am here"); //setting custom snippet

        mMarker = mGoogleMap.addMarker(options);
        circle = drawCircle(new LatLng(x, y));
    }

    private Circle drawCircle(LatLng l)
    {
        CircleOptions options = new CircleOptions()
                                .center(l)
                                .radius(1000)
                                .fillColor(0x33FF0300)
                                .strokeColor(Color.BLUE)
                                .strokeWidth(3);
        return mGoogleMap.addCircle(options);
    }

    private void removeEverything()
    {
        mMarker.remove();
        mMarker = null;
        circle.remove();
        circle = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.maps_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mapTypeNone:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                break;
            case R.id.mapTypeNormal:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.mapTypeSatellite:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.mapTypeTerrain:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case R.id.mapTypeHybrid:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    LocationRequest mLocationRequest;

    //connection call back methods
    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = LocationRequest.create(); //creates location request object
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY); //sets priority of request with high location accuract
        mLocationRequest.setInterval(1000); //location retrieved every second
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this); //updates location and listens for the location
        //calls  onLocationChanged();
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    //connection failed method
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    @Override
    public void onLocationChanged(Location location) //gets location based on updated position
    {
        if(location == null)
        {
            Toast.makeText(getApplicationContext(), "Can't get location", Toast.LENGTH_LONG).show();
        }
        else
        {
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, 15);
            mGoogleMap.animateCamera(update);
        }
    }
}
