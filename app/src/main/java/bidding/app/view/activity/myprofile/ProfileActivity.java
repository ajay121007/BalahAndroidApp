package bidding.app.view.activity.myprofile;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import bidding.app.R;
import bidding.app.extra.CSPreferences;
import bidding.app.extra.Constants;
import bidding.app.extra.Event;
import bidding.app.extra.Utils;
import bidding.app.model.ActionString;
import bidding.app.view.activity.BaseActivity;
import bidding.app.view.activity.myprofile.presenter.ProfileActivityPresenter;
import bidding.app.view.activity.myprofile.presenter.ProfileActivityPresenterHandler;
import bidding.app.view.activity.myprofile.view.ProfileActivityView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileActivity extends BaseActivity implements OnMapReadyCallback, LocationListener, ProfileActivityView {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    GoogleMap googleMap;
    LatLng latLng;
    String address = "", textUpdate = "Edit", mUserId = "", mFullName = "", mAddress = "", mLatitude = "", mLongitude = "", mCity = "", mCountry = "";
    double currentlat, currentlang;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.ed_name)
    EditText edName;
    @BindView(R.id.ed_country)
    EditText edCountry;
    @BindView(R.id.ed_mobile)
    EditText edMobile;
    @BindView(R.id.ed_location)
    EditText edLocation;
    LocationManager locationManager;
    ProfileActivityPresenterHandler handler;
    @BindView(R.id.tv_update)
    TextView tvUpdate;
    private int status = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        try {
            // Loading map
            initilizeMap();

        } catch (Exception e) {
            e.printStackTrace();
        }
        init();
        handler.getProfile(this, ActionString.profiledeatil, mUserId);
    }

    private void initilizeMap() {
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }

    private void init() {
        mUserId = CSPreferences.readString(this, "user_id");
        handler = new ProfileActivityPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransitionExit();
    }

    @Override
    public void onMapReady(GoogleMap Map) {
        this.googleMap = Map;
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
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
        googleMap.getUiSettings().setZoomControlsEnabled(true);


        Location location = getLastBestStaleLocation();
        initCamera(location);


        googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {

                latLng = googleMap.getCameraPosition().target;
                address = Utils.getCompleteAddressString(ProfileActivity.this, latLng.latitude, latLng.longitude);
                if (textUpdate.equalsIgnoreCase("update")) {
                    currentlat = latLng.latitude;
                    currentlang = latLng.longitude;
                    edLocation.setText("" + address);
                    //text_city.setText("" + CSPreferences.readString(getActivity(), "city"));
                    edCountry.setText("" + CSPreferences.readString(ProfileActivity.this, "country"));
                }
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

            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(position));
            address = Utils.getCompleteAddressString(ProfileActivity.this, currentlat, currentlang);
            /*addresstext.setText("" + address);
            text_city.setText("" + CSPreferences.readString(getActivity(), "city"));
            text_country.setText("" + CSPreferences.readString(getActivity(), "country"));*/
            if (textUpdate.equals("Edit")) {
                googleMap.getUiSettings().setAllGesturesEnabled(false);
            } else {

            }
           /* MarkerOptions options = new MarkerOptions();
            options.position(new LatLng(currentlat, currentlang));*/

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

    public Location getLastBestStaleLocation() {

        Location bestresult = null;
        Log.e("call", "getbesccctlocation");
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
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
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void showProgess() {
        showProgressDialog();
    }

    @Override
    public void hideProgess() {
        hideProgressDialog();
    }

    @Override
    public void showFeedBackMessage(String message) {
        showToast(message);
    }

    @Override
    public void updateProfileSuccess(String response) {
        String fullname = "";
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject respons = jsonObject.getJSONObject("response");
            String message = respons.getString("message");
            int id = Integer.parseInt(respons.getString("status"));
            if (id >= 1) {
                JSONArray jsonObject1 = respons.getJSONArray("user_detail");
                for (int i = 0; i < jsonObject1.length(); i++) {
                    JSONObject jsonObject11 = jsonObject1.getJSONObject(i);
                    String userid = jsonObject11.getString("user_id");
                    fullname = jsonObject11.getString("fullname");
                    String address = jsonObject11.getString("address");
                    String country = jsonObject11.getString("country");
                    String city = jsonObject11.getString("city");
                    CSPreferences.putString(this, "user_id", userid);
                    CSPreferences.putString(this, "user_name", fullname);
                }
                status = 0;
                googleMap.getUiSettings().setAllGesturesEnabled(false);
                textUpdate = "Edit";
                tvUpdate.setBackgroundResource(R.drawable.ic_icon_edit);
                tvName.setText(fullname);
                edName.setClickable(false);
                edName.setEnabled(false);
            } else {
                EventBus.getDefault().post(new Event(Constants.ERROR, ""));
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }
    }

    @Override
    public void profileSuccess(String response) {
        Log.d("Profile Success", response + "");
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject respons = jsonObject.getJSONObject("response");
            String message = respons.getString("message");
            int id = Integer.parseInt(respons.getString("status"));
            CSPreferences.putString(this, "profile_status", String.valueOf(id));
            JSONArray jsonArray = respons.getJSONArray("data");


            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                mFullName = jsonObject1.getString("fullname");
                mAddress = jsonObject1.getString("address");
                mCity = jsonObject1.getString("city");
                mCountry = jsonObject1.getString("country");
                mLatitude = jsonObject1.getString("latitude");
                mLongitude = jsonObject1.getString("longitude");
                CSPreferences.putString(this, "fullname", mFullName);
                CSPreferences.putString(this, "latitude", mLatitude);
                CSPreferences.putString(this, "longitude", mLongitude);
            }

            edName.setText(mFullName);
            tvName.setText(mFullName);
            edLocation.setText(mAddress);
            //text_city.setText(mCity);
            edCountry.setText(mCountry);

        } catch (JSONException e) {
            e.printStackTrace();
            showToast(e.getMessage().toString());
        }
    }

    @OnClick({R.id.tv_update, R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_update:
                if (status == 0) {
                    googleMap.getUiSettings().setAllGesturesEnabled(true);
                    textUpdate = "Update";
                    tvUpdate.setBackgroundResource(R.drawable.ic_icon_save);
                    edName.setClickable(true);
                    edName.setEnabled(true);
                    status = 1;
                } else if (status == 1) {
                    if (edName.length() > 1) {
                        handler.updateProfile(this, "update_profile", CSPreferences.readString(this, "user_id"), edName.getText().toString(), address, edCountry.getText().toString(), edCountry.getText().toString(), String.valueOf(currentlat), String.valueOf(currentlang));
                    } else {
                        Toast.makeText(this, "Please enter fullname.", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case R.id.back:
                finish();
                overridePendingTransitionExit();
                break;
        }
    }
}
