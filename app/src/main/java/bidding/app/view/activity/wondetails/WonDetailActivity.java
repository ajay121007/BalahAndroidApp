package bidding.app.view.activity.wondetails;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import bidding.app.controller.AuctionOwnerInfo;
import bidding.app.controller.ModelManager;
import bidding.app.extra.CSPreferences;
import bidding.app.extra.Constants;
import bidding.app.extra.Event;
import bidding.app.extra.Operations;
import bidding.app.model.AuctionOwnerInfoBean;
import bidding.app.model.WinnerDetailBean;
import bidding.app.view.activity.BaseActivity;
import bidding.app.view.activity.wondetails.presenter.WonDetailPresenter;
import bidding.app.view.activity.wondetails.presenter.WonDetailPresenterHandler;
import bidding.app.view.activity.wondetails.view.WonDetailView;
import bidding.app.view.fragment.BaseFragment;
import bidding.app.view.fragment.purchasefragment.presenter.PurchaseFragmentPresenter;
import bidding.app.view.fragment.purchasefragment.presenter.PurchaseFragmentPresenterHandler;
import bidding.app.view.fragment.purchasefragment.view.PurchaseFragmentView;

/**
 * Created by Thakur on 12/29/2017.
 */

public class WonDetailActivity extends BaseActivity implements WonDetailView, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback, LocationListener, View.OnClickListener {
    View view;
    String auctionOwnerId = "", auctionId = "", mInventory = "", mDelivery = "", mStatus = "", mPaymentStatus = "", mUserId = "", mProductId = "",mCardNumber="";
    List<WinnerDetailBean.Response> responses;
    MapView mapView;
    LatLng latLng;
    Marker mMarker;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    private GoogleMap googleMap;
    LocationManager locationManager;
    double currentlat, currentlang;
    ImageView navigation, back;
    TextView fullname, phone, address, city, country, tvManageQuntity, quntity, tvOk, tvPayment,tvPay,tvAlertTitle, tvAlertDescription;
    AlertDialog.Builder dialog;
    Button ok, cancel;
    RadioGroup radioGroup;
    RadioButton rdInventory, rdDeliver;
    WonDetailPresenterHandler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_map);
        init();
        listner();
        mapView = (MapView) findViewById(R.id.maps);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
        Intent intent = getIntent();
        if (intent.getExtras().containsKey("auctionOwnerId")){

            auctionOwnerId = intent.getStringExtra("auctionOwnerId");
            auctionId = intent.getStringExtra("auctionId");
            mStatus = intent.getStringExtra("status");
            mPaymentStatus = intent.getStringExtra("payment_status");
            mProductId = intent.getStringExtra("product_id");
        }

        mUserId = CSPreferences.readString(this, Constants.USERID);
        auctionOwnerInfo(auctionOwnerId, auctionId);
    }


    private void init() {

        navigation = findViewById(R.id.navigation);
        back = findViewById(R.id.back);
        fullname = findViewById(R.id.full_name);
        phone = findViewById(R.id.phone_no);
        address = findViewById(R.id.address);
        city = findViewById(R.id.city);
        country = findViewById(R.id.country);
        tvManageQuntity = findViewById(R.id.tv_manage_Quntity);
        quntity = findViewById(R.id.quntity);
        tvOk = findViewById(R.id.tv_ok);
        tvPayment = findViewById(R.id.tv_payment);
        tvPay = findViewById(R.id.tv_pay);

        if (!CSPreferences.readString(this, Constants.SELECTEDCARDNUMBER).isEmpty() && CSPreferences.readString(this, Constants.SELECTEDCARDNUMBER) != null) {
            mCardNumber = CSPreferences.readString(this, Constants.SELECTEDCARDNUMBER);
        }

        handler = new WonDetailPresenter(this, tvPayment,tvPay);
    }

    private void listner() {
        navigation.setOnClickListener(this);
        back.setOnClickListener(this);
        tvManageQuntity.setOnClickListener(this);
        tvOk.setOnClickListener(this);
        tvPay.setOnClickListener(this);
    }

    private void auctionOwnerInfo(String ownerid, String auction_id) {
        showProgressDialog();
        ModelManager.getInstance().getAuctionOwnerInfo().AuctionOwnerInfo(this,
                Operations.getAuctionOwnerInfo(this, "auctionOwner_detail", ownerid, auction_id));

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
            case Constants.OWNERINFO:
                hideProgressDialog();
                addMarker_setData(AuctionOwnerInfo.dataList);
                break;

            case Constants.SERVER_ERROR:
                hideProgressDialog();
                Toast.makeText(this, "Server Error", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void addMarker_setData(AuctionOwnerInfoBean.Data dataList) {
        if (googleMap != null) {
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(Double.parseDouble(AuctionOwnerInfo.sLatitude), Double.parseDouble(AuctionOwnerInfo.sLongitude)))
                    .title(AuctionOwnerInfo.sFullName)).showInfoWindow();
        }

        fullname.setText(dataList.getFullname());
        phone.setText(dataList.getPhoneNo());
        address.setText(dataList.getAddress());
        city.setText(dataList.getCity());
        country.setText(dataList.getCountry());
        quntity.setText(dataList.getQuantity());

        if (mPaymentStatus.equalsIgnoreCase("0")) {
            tvPayment.setText("");
            tvPay.setVisibility(View.VISIBLE);
        } else if (mPaymentStatus.equalsIgnoreCase("1")) {
            tvPayment.setText("Complete");
            tvPay.setVisibility(View.GONE);
        }

    }

    @Override
    public void onMapReady(GoogleMap Map) {

        googleMap = Map;
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
            Log.e("Current LAT", String.valueOf(currentlat));
            Log.e("Current Long", String.valueOf(currentlang));
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
                android.Manifest.permission.ACCESS_FINE_LOCATION)
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

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                if (AuctionOwnerInfo.sLatitude != null && !AuctionOwnerInfo.sLatitude.isEmpty()) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?" + "saddr=" + currentlat + "," + currentlang + "&daddr=" + AuctionOwnerInfo.sLatitude + "," + AuctionOwnerInfo.sLongitude));
                        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Null", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.back:
            finish();
                break;

            case R.id.tv_manage_Quntity:
                //showAlert(getActivity(), "Hello", "1");
                break;
            case R.id.tv_ok:

                mInventory = quntity.getText().toString() + "";
                handler.moveToInventory(this, "moveToInventory", CSPreferences.readString(this, Constants.USERID), auctionId, mInventory, mDelivery);
                break;

            case R.id.tv_pay:
                showConfirmAlert(getResources().getString(R.string.payment) + "", "Do you want to pay this product ?" + "");
                break;
        }
    }

    public void showConfirmAlert(String title, String message) {

        dialog = new AlertDialog.Builder(this);
        view = LayoutInflater.from(this).inflate(R.layout.dialog_alert, null);
        dialog.setView(view);

        final AlertDialog alertDialog = dialog.create();
        tvAlertTitle = (TextView) view.findViewById(R.id.tv_alert);
        tvAlertDescription = (TextView) view.findViewById(R.id.tv_message);
        tvAlertTitle.setText(title);
        tvAlertDescription.setText(message);
        ok = (Button) view.findViewById(R.id.ok);
        cancel = (Button) view.findViewById(R.id.cancel);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.pendingPayment(WonDetailActivity.this, "pendingPaymentComplete", mUserId, mProductId, "2",auctionId,mCardNumber);
                alertDialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = lp.WRAP_CONTENT;
        lp.height = lp.WRAP_CONTENT;
        alertDialog.getWindow().setAttributes(lp);
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();

    }
    public void showAlert(final Context context, String message, final String eventId) {
        dialog = new AlertDialog.Builder(context);
        view = LayoutInflater.from(context).inflate(R.layout.dialog, null);
        dialog.setView(view);

        final AlertDialog alertDialog = dialog.create();
        radioGroup = view.findViewById(R.id.rd_group);
        rdDeliver = radioGroup.findViewById(R.id.rd_deliver);
        rdInventory = radioGroup.findViewById(R.id.rd_inventory);
        ok = (Button) view.findViewById(R.id.ok);
        cancel = (Button) view.findViewById(R.id.cancel);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioGroup.getCheckedRadioButtonId() == -1) {
                    // no radio buttons are checked
                    Toast.makeText(context, getResources().getString(R.string.select_one), Toast.LENGTH_SHORT).show();
                } else {
                    // one of the radio buttons is checked
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    if (selectedId == R.id.rd_deliver) {
                        mDelivery = quntity.getText().toString() + "";
                    } else if (selectedId == R.id.rd_inventory) {
                        mInventory = quntity.getText().toString() + "";
                    }
                    handler.moveToInventory(WonDetailActivity.this, "moveToInventory", CSPreferences.readString(WonDetailActivity.this, Constants.USERID), auctionId, mInventory, mDelivery);
                    alertDialog.dismiss();
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = lp.WRAP_CONTENT;
        lp.height = lp.WRAP_CONTENT;
        alertDialog.getWindow().setAttributes(lp);
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
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
}

