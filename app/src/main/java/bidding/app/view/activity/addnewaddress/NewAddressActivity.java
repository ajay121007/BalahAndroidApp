package bidding.app.view.activity.addnewaddress;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Locale;

import bidding.app.R;
import bidding.app.extra.CSPreferences;
import bidding.app.extra.Constants;
import bidding.app.extra.CustomMapView;
import bidding.app.view.activity.BaseActivity;
import bidding.app.view.activity.addnewaddress.presenter.NewAddressActivityHandler;
import bidding.app.view.activity.addnewaddress.presenter.NewAddressPresenter;
import bidding.app.view.activity.addnewaddress.request.Customeraddress;
import bidding.app.view.activity.addnewaddress.request.SaveAddressRequest;
import bidding.app.view.activity.addnewaddress.view.NewAddresView;
import bidding.app.view.activity.changeaddress.request.EditAddress;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewAddressActivity extends BaseActivity implements OnMapReadyCallback, LocationListener, NewAddresView {

    @BindView(R.id.back)
    ImageView back;
    GoogleMap googleMap;
    LatLng latLng;
    LocationManager locationManager;
    String address = "", mAddressId = "";
    double currentlat, currentlang;
    @BindView(R.id.ed_name)
    EditText edName;
    @BindView(R.id.ed_last_name)
    EditText edLastName;
    @BindView(R.id.ed_mobile)
    EditText edMobile;
    @BindView(R.id.ed_location)
    EditText edLocation;
    @BindView(R.id.ed_country)
    EditText edCountry;
    @BindView(R.id.ed_city)
    EditText edCity;
    @BindView(R.id.ed_postal_code)
    EditText edPostalCode;
    @BindView(R.id.ed_company)
    EditText edCompany;
    @BindView(R.id.rl_save_address)
    RelativeLayout rlSaveAddress;
    String mToken = "", mFirstName = "", mLastname = "", mPhoneNumber = "", mStreet = "", mCountryID = "", mCity = "", mPostCode = "", mCompany = "", mToolBarTitle = "", mButtonName = "";
    NewAddressActivityHandler handler;
    CustomMapView mapView;
    @BindView(R.id.cb_default_address)
    CheckBox cbDefaultAddress;
    Intent intent;
    int defaultAddress = 0, shippingAddress = 0;
    @BindView(R.id.tvToolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.tvSaveButton)
    TextView tvSaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_add);
        ButterKnife.bind(this);


        try {
            // Loading map
            initilizeMap(savedInstanceState);
        } catch (Exception e) {
            e.printStackTrace();
        }

        findId();

    }

    private void findId() {

        handler = new NewAddressPresenter(this);
        mToken = CSPreferences.readString(this, Constants.TOKEN);

        intent = getIntent();
        if (intent != null && intent.getExtras().containsKey(Constants.BUTTONNAME)) {
            mToolBarTitle = intent.getExtras().getString(Constants.TITLE);
            mButtonName = intent.getExtras().getString(Constants.BUTTONNAME);
            tvToolbarTitle.setText(mToolBarTitle + "");
            tvSaveButton.setText(mButtonName + "");

        }

        //Get Profile
        handler.getCustomer(this, "Bearer " + mToken);
    }

    private void initilizeMap(Bundle savedInstanceState) {
        mapView = findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
    }

    private void getData() {
        mFirstName = edName.getText().toString();
        mLastname = edLastName.getText().toString();
        mPhoneNumber = edMobile.getText().toString();
        mStreet = edLocation.getText().toString();
        mCity = edCity.getText().toString();
        mPostCode = edPostalCode.getText().toString();
        mCompany = edCompany.getText().toString();
    }

    @OnClick({R.id.back, R.id.rl_save_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.rl_save_address:

                if (cbDefaultAddress.isChecked()) {
                    defaultAddress = 1;
                    shippingAddress = 1;
                } else {
                    defaultAddress = 0;
                    shippingAddress = 0;
                }

                if (intent != null && intent.getExtras().containsKey(Constants.ADDRESSID)) {
                    mAddressId = intent.getExtras().getString(Constants.ADDRESSID);
                    editAddress(mAddressId);
                } else {
                    addNewAddress();
                }

                break;
        }
    }

    private void editAddress(String mAddressId) {

        getData();

        EditAddress request = new EditAddress();
        EditAddress.CustomeraddressBean bean = new EditAddress.CustomeraddressBean();

        bean.setAddressid(mAddressId);
        bean.setFirstname(mFirstName);
        bean.setLastname(mLastname);
        bean.setPhoneno(mPhoneNumber);
        bean.setStreet(mStreet);
        bean.setCity(mCity);
        bean.setCountryid(mCountryID);
        bean.setPostcode(mPostCode);
        bean.setCompany(mCompany);
        bean.setDefault_billing(defaultAddress);
        bean.setDefault_shipping(shippingAddress);

        request.setCustomeraddress(bean);

        handler.editAddress(this, "Bearer " + mToken, request);

    }

    private void addNewAddress() {


        getData();

        SaveAddressRequest request = new SaveAddressRequest();
        Customeraddress customeraddress = new Customeraddress();
        customeraddress.setFirstname(mFirstName);
        customeraddress.setLastname(mLastname);
        customeraddress.setPhoneno(mPhoneNumber);
        customeraddress.setStreet(mStreet);
        customeraddress.setCity(mCity);
        customeraddress.setCountryid(mCountryID);
        customeraddress.setPostcode(mPostCode);
        customeraddress.setCompany(mCompany);
        customeraddress.setDefaultBilling(defaultAddress);
        customeraddress.setDefaultShipping(shippingAddress);
        request.setCustomeraddress(customeraddress);

        handler.saveAddress(this, "Bearer " + mToken, request);
    }

    @Override
    public void onMapReady(GoogleMap gMap) {
        this.googleMap = gMap;
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
                Log.d("Address", address + "");
                latLng = googleMap.getCameraPosition().target;
                getCompleteAddressString(latLng.latitude, latLng.longitude);
            }
        });
    }

    private void initCamera(Location mLocation) {
        if (mLocation != null) {

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
    public void setData(String mFirstName, String mLastName, String mPhone) {
        edName.setText(mFirstName);
        edLastName.setText(mLastName);
        edMobile.setText(mPhone);
    }

    public void getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(NewAddressActivity.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                String country = returnedAddress.getCountryName();
                String city = returnedAddress.getLocality();
                String postCode = returnedAddress.getPostalCode();
                mCountryID = returnedAddress.getCountryCode();

                edCountry.setText(country + "");
                edCity.setText(city + "");
                edPostalCode.setText(postCode + "");


                strReturnedAddress.append(returnedAddress.getAddressLine(0));

                if (returnedAddress.getSubLocality() != null)
                    strReturnedAddress.append(", ").append(returnedAddress.getSubLocality());

                strAdd = strReturnedAddress.toString();
                Log.e("FULLADDRESS", strAdd);


                if (strAdd.contains(",")) {
                    String fullAdd[] = strAdd.split(",");
                    edLocation.setText(fullAdd[0]);
                    if (!fullAdd[1].isEmpty() && fullAdd[1] != null)
                        edLocation.append("," + fullAdd[1]);
                    if (!fullAdd[2].isEmpty() && fullAdd[2] != null)
                        edLocation.append("," + fullAdd[2]);
                }

            } else {
                Log.e("", "No address returned");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
