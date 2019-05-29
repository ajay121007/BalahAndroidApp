package bidding.app.view.activity.winnerdetail;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import bidding.app.R;
import bidding.app.controller.ModelManager;
import bidding.app.extra.Constants;
import bidding.app.extra.Event;
import bidding.app.extra.Operations;
import bidding.app.model.WinnerDetailBean;
import bidding.app.view.activity.BaseActivity;
import bidding.app.view.fragment.BaseFragment;

/**
 * Created by Thakur on 12/20/2017.
 */

public class WinnerDetailActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback, LocationListener, View.OnClickListener {
    ProgressDialog progressDialog;
    String winnerId = "";
    List<WinnerDetailBean.Datum> list;
    List<WinnerDetailBean.Response> responses;
    TextView fullname, phone, address, city, country;
    MapView mapView;
    LatLng latLng;
    Marker mMarker;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    private GoogleMap googleMap;
    LocationManager locationManager;
    double currentlat, currentlang;
    ImageView navigation;
    ImageView back;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_winnerdetails);
        init();
        listner();
        mapView = (MapView) findViewById(R.id.maps);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
        Intent intent = getIntent();
        if (intent.getExtras().containsKey("winner_id")) {
            winnerId = intent.getStringExtra("winner_id");
            hitWinnerInfo(winnerId);
        }

    }

    private void init() {
        navigation = findViewById(R.id.navigation);
        progressDialog = new ProgressDialog(this);
        fullname = findViewById(R.id.full_name);
        city = findViewById(R.id.city);
        address = findViewById(R.id.address);
        phone = findViewById(R.id.phone_no);
        country = findViewById(R.id.country);
        back = findViewById(R.id.back);
    }

    private void listner() {
        back.setOnClickListener(this);
        navigation.setOnClickListener(this);
    }

    private void hitWinnerInfo(String s) {
        showProgressDialog();
        ModelManager.getInstance().getWinnerDetailManger().WinnerDetailManger(this,
                Operations.getWinnerDetail(this, "get_winner_detail", s));

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        switch (event.getKey()) {
            case Constants.CONECTED:
                if (event.isConnected()) {
                } else {
                    Toast.makeText(this, getResources().getString(R.string.str_internet_check), Toast.LENGTH_SHORT).show();
                }
                break;
            case Constants.WINNERDETAIL_RESULT:
                hideProgressDialog();
                list = ModelManager.getInstance().getWinnerDetailManger().bean.getResponse().getData();
                fullname.setText(list.get(0).getFullname());
                phone.setText(list.get(0).getPhoneNo());
                address.setText(list.get(0).getAddress());
                country.setText(list.get(0).getCountry());
                city.setText(list.get(0).getCity());
                break;

            case Constants.SERVER_ERROR:
                hideProgressDialog();
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap Map) {

        googleMap = Map;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(false);
        googleMap.getUiSettings().setMapToolbarEnabled(false);

        Location location = getLastBestStaleLocation();
        initCamera(location);


        googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {

                latLng = googleMap.getCameraPosition().target;


            }
        });

    }

    private void initCamera(Location mLocation) {

        if (mLocation != null) {

            Log.e("init camera", "fuction are called");

            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            CameraPosition position = CameraPosition
                    .builder().target(new LatLng(mLocation.getLatitude(), mLocation.getLongitude()))
                    .zoom(13f)
                    .bearing(0.0f)
                    .tilt(0.0f)
                    .build();
            currentlat = mLocation.getLatitude();
            currentlang = mLocation.getLongitude();
            Log.e("Current_LAT", String.valueOf(currentlat));
            Log.e("Current_Long", String.valueOf(currentlang));
            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(position));

            MarkerOptions options = new MarkerOptions();
            options.position(new LatLng(currentlat, currentlang));

        } else {

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    getLastBestStaleLocation();
                }
            }, 1000);
        }

    }

    @Override
    public void onLocationChanged(Location location) {

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

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public Location getLastBestStaleLocation() {

        Location bestresult = null;
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            // ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            // public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            return null;
        }
        Location gpslocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location networklocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (gpslocation != null && networklocation != null) {
            if (gpslocation.getTime() > networklocation.getTime()) {
                bestresult = gpslocation;
                Log.e("result", "both location are found---- " + bestresult.getLatitude());

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);
            } else {
                bestresult = networklocation;
                Log.e("result", "network location ---- " + bestresult.getLatitude());

                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0, this);
            }
        } else if (gpslocation != null) {
            bestresult = gpslocation;
            Log.e("result", "gps location only found---- " + bestresult.getLatitude());

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);
        } else if (networklocation != null) {
            bestresult = networklocation;
            Log.e("result", "network location only found--- " + bestresult.getLatitude());
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0, this);
            Log.e("", bestresult.toString());
        }
        return bestresult;
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.navigation:
                Log.d("clat", currentlat + "   " + "clong" + "    " + currentlang + "     " + "dlati" + "    " + list.get(0).getLatitude() + "    " + "dlong" + "     " + list.get(0).getLongitude() + "");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?" + "saddr=" + currentlat + "," + currentlang + "&daddr=" + list.get(0).getLatitude() + "," + list.get(0).getLongitude()));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);
                break;

            case R.id.back:
                finish();
                break;
        }
    }
}
