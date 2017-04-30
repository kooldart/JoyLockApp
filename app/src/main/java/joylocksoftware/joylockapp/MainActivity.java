package joylocksoftware.joylockapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.GeoDataApi;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    LocationManager locationMan;
    String CityName;
    Location location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        setContentView(R.layout.activity_main);

        locationMan = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
        location = locationMan.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        try {
            locationMan.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
        } catch (SecurityException e) {
            e.printStackTrace();
        }

//      ChangeLocation(location);
        Log.d("TAG", "City: " + CityName);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        location = locationMan.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        locationMan.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    }
                }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void Clickim(View v) {
        Log.d("TAG", "City: " + CityName);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location loc) {
            ChangeLocation(loc);
        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(MainActivity.this, "GPS is disabled!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderEnabled(String provider) {
            Toast.makeText(MainActivity.this, "GPS is enabled successfully!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
    };

    public void setLocationName(String NewName) {
        ((TextView) findViewById(R.id.locname)).setText(NewName);
    }


    public void ChangeLocation(Location loc) {

        String url = geoApiUrlBuilder(loc);
        JsonObjectRequest request = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Tag", "Response: " + response);
                String status = response.optString("status");
                if (status.equals("OK")) {
                    JSONArray results = response.optJSONArray("results");
                    JSONObject object = results.optJSONObject(0);
                    String placeID = object.optString("place_id");


                    // Get the first photo in the list.
                    PlacePhotoMetadata photo = photoMetadataBuffer.get(0);
// Get a full-size bitmap for the photo.
                    Bitmap image = photo.getPhoto(mGoogleApiClient).await()
                            .getBitmap();
// Get the attribution text.
                    CharSequence attribution = photo.getAttributions();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAg", "Error: " + error.getMessage());
            }
        });


        MySingleton.getInstance(MainActivity.this).addToRequestQueue(request);
//        String longitude = "Longitude: " + loc.getLongitude();
//        String latitude = "Latitude: " + loc.getLatitude();
//
//        String cityName = null;
//        Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
//        List<Address> addresses;
//        try {
//            addresses = gcd.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
//            if (addresses.size() > 0) {
//                cityName = addresses.get(0).getLocality();
//                Log.d("TAG", "City: " + cityName);
//                setLocationName(cityName);
//
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            Log.d("TAG", "FAILED");
//        }
//        CityName = cityName;
//        Log.d("TAG", "City: " + cityName);
    }

    public String geoApiUrlBuilder(Location location) {
        return "https://maps.googleapis.com/maps/api/geocode/json?latlng=".concat(location.getLatitude() + "," + location.getLongitude()).concat("&key=")
                .concat("AIzaSyDdRhuykLm4OWfA8dKVGG61BCrjqwmjHV8");
    }


//
//    public void GooglePics(){
//        String url = mPlaces.getIcon();
//        String photoreference = mPlaces.getPhotoreference();
//        String restaurantpic = "http://maps.googleapis.com/maps/api/place/photo?" +
//                "maxwidth=400" +
//                "&photoreference=" +photoreference +
//                "&key="+"AIzaSyDdRhuykLm4OWfA8dKVGG61BCrjqwmjHV8";
//
//        Log.d("Loading restaurantpic" , restaurantpic);
//
//        Glide
//                .with(mContext)
//                .load(restaurantpic)
//                .centerCrop()
//                // .placeholder(R.drawable.loading_spinner)
//                //.crossFade()
//                .into(mImageViewIcon);
//    }


    private void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();

    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
