package bidding.app.view.fragment.profilefragment;

import android.Manifest;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import bidding.app.R;
import bidding.app.model.ActionString;
import bidding.app.extra.CSPreferences;
import bidding.app.model.CardDetailsBean;
import bidding.app.extra.Constants;
import bidding.app.extra.Event;
import bidding.app.extra.Utils;
import bidding.app.view.adapter.CardAddAdapter;
import bidding.app.view.fragment.BaseFragment;
import bidding.app.view.fragment.profilefragment.presenter.ProfileFragmentPresenter;
import bidding.app.view.fragment.profilefragment.presenter.ProfileFragmentPresenterHandler;
import bidding.app.view.fragment.profilefragment.view.ProfileFragmentView;
import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;

/**
 * Created by vijay on 18/8/17.
 */
public class ProfileFragment extends BaseFragment implements ProfileFragmentView, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback, LocationListener, View.OnClickListener, AdapterView.OnItemSelectedListener, TextWatcher {
    private static final int REQUEST_SCAN = 100;
    private GoogleMap googleMap;
    MapView mapView;
    TextView addresstext, text_country, text_city, text_update, text_card, text_profile;
    LocationManager locationManager;
    EditText text_name;
    String address = "";
    LinearLayout profilelayout, cardlayout;
    double currentlat, currentlang;
    ProgressDialog progressDialog;
    Context context;
    LatLng latLng;
    View view1;
    String stripetoken = "", textUpdate = "Edit";
    RelativeLayout rlCardList;
    LinearLayout llAddCard, llAddPayment;
    private int status = 0;
    private Fragment fragment;
    ArrayAdapter<String> adapteryear;
    ArrayAdapter<String> adaptemonth;
    ArrayList<String> year = new ArrayList<>();
    String month[] = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    Button addCard, cancel;
    private TextView scan_c;
    private EditText card_holder;
    private EditText card_number;
    private EditText cvv;
    private Spinner sp_month;
    private Spinner sp_year,sp_dummy;
    String cardnumber;
    String cvc, userId = "", mFullName = "", mAddress = "", mLatitude = "", mLongitude = "", mCity = "", mCountry = "";
    String cardHolderName;
    private String ex_mnth;
    private String ex_year;
    private Card cardToSave;
    RecyclerView cardList;
    RecyclerView.LayoutManager recylerViewLayoutManager;
    private CardDetailsBean bean;
    CardAddAdapter arrayAdapter;
    List<CardDetailsBean.Datum> datumList;
    String a;
    int keyDel;
    ProfileFragmentPresenterHandler handler;


    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = getActivity();
        view1 = inflater.inflate(R.layout.profile_layout, container, false);
        mapView = (MapView) view1.findViewById(R.id.maps);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
        initviews();
        listner();
        handler.getProfile(getActivity(), ActionString.profiledeatil, userId);
        return view1;
    }


    @Override
    public void onMapReady(GoogleMap Map) {
        googleMap = Map;
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                address = Utils.getCompleteAddressString(getActivity(), latLng.latitude, latLng.longitude);
                if (textUpdate.equalsIgnoreCase("update")) {
                    currentlat = latLng.latitude;
                    currentlang = latLng.longitude;
                    addresstext.setText("" + address);
                    text_city.setText("" + CSPreferences.readString(getActivity(), "city"));
                    text_country.setText("" + CSPreferences.readString(getActivity(), "country"));
                }
            }
        });
    }


    private void initviews() {
        userId = CSPreferences.readString(getActivity(), "user_id");
        handler = new ProfileFragmentPresenter(this);
        cardList = view1.findViewById(R.id.list);
        recylerViewLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        cardList.setLayoutManager(recylerViewLayoutManager);
        addCard = view1.findViewById(R.id.add_card);
        cancel = view1.findViewById(R.id.cancel);
        rlCardList = view1.findViewById(R.id.rl_card_list);
        llAddPayment = view1.findViewById(R.id.ll_addpayment);
        llAddCard = view1.findViewById(R.id.ll_addcard);
        cardlayout = (LinearLayout) view1.findViewById(R.id.cardlayout);
        profilelayout = (LinearLayout) view1.findViewById(R.id.layoutprofile);
        progressDialog = new ProgressDialog(getActivity());
        text_name = (EditText) view1.findViewById(R.id.text_name);
        addresstext = (TextView) view1.findViewById(R.id.text_address);
        text_country = (TextView) view1.findViewById(R.id.text_country);
        text_card = (TextView) view1.findViewById(R.id.text_card);
        text_profile = (TextView) view1.findViewById(R.id.text_profile);
        text_city = (TextView) view1.findViewById(R.id.text_city);
        text_update = (TextView) view1.findViewById(R.id.text_update);
        text_name.setClickable(false);
        text_name.setEnabled(false);
        scan_c = view1.findViewById(R.id.scan_c);
        card_holder = (EditText) view1.findViewById(R.id.card_holder);
        card_number = (EditText) view1.findViewById(R.id.card_number);
        cvv = (EditText) view1.findViewById(R.id.cvv);
        sp_month = (Spinner) view1.findViewById(R.id.sp_month);
        sp_year = (Spinner) view1.findViewById(R.id.sp_year);
        sp_dummy = (Spinner) view1.findViewById(R.id.sp_dummy);


        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        int next50Year = thisYear + 50;
        for (int i = thisYear; i <= next50Year; i++) {
            year.add(Integer.toString(i));
        }

        adapteryear = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, year);
        adaptemonth = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, month);
        adapteryear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adaptemonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_year.setAdapter(adapteryear);
        sp_month.setAdapter(adaptemonth);
        sp_dummy.setAdapter(adaptemonth);


    }

    private void scanCard() {
        Intent intent = new Intent(getActivity(), CardIOActivity.class)
                .putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true)
                .putExtra(CardIOActivity.EXTRA_SCAN_EXPIRY, true)
                .putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, true)
                .putExtra(CardIOActivity.EXTRA_REQUIRE_CARDHOLDER_NAME, true)
                .putExtra(CardIOActivity.EXTRA_LANGUAGE_OR_LOCALE, "en")
                .putExtra(CardIOActivity.EXTRA_GUIDE_COLOR, Color.GREEN)
                .putExtra(CardIOActivity.EXTRA_RETURN_CARD_IMAGE, true);
        startActivityForResult(intent, REQUEST_SCAN);
    }

    private void listner() {
        text_card.setOnClickListener(this);
        text_profile.setOnClickListener(this);
        rlCardList.setOnClickListener(this);
        llAddCard.setOnClickListener(this);
        text_update.setOnClickListener(this);
        scan_c.setOnClickListener(this);
        llAddPayment.setOnClickListener(this);
        cancel.setOnClickListener(this);
        addCard.setOnClickListener(this);
        sp_year.setOnItemSelectedListener(this);
        sp_month.setOnItemSelectedListener(this);
        card_number.addTextChangedListener(this);

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
            address = Utils.getCompleteAddressString(getActivity(), currentlat, currentlang);
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
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
        locationManager.removeUpdates(this);
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
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        switch (event.getKey()) {
            case Constants.CONECTED:
                if (event.isConnected()) {

                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.str_internet_check), Toast.LENGTH_SHORT).show();
                }
                break;

            case Constants.SERVER_ERROR:
                hideProgressDialog();
                Toast.makeText(getActivity(), "No data", Toast.LENGTH_SHORT).show();
                break;
            case Constants.DELETECARDSUCCESS:
                getCardApi();
                break;
            case Constants.DEFAULTCARD:
                arrayAdapter.customNotify(datumList, getActivity());
                break;

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_card_list:

                break;

            case R.id.ll_addpayment:
                rlCardList.setVisibility(View.GONE);
                llAddCard.setVisibility(View.VISIBLE);
                card_holder.setText("");
                card_number.setText("");
                cvv.setText("");
                break;

            case R.id.add_card:
                cardHolderName = card_holder.getText().toString();
                cardnumber = card_number.getText().toString();
                ex_mnth = sp_month.getSelectedItem().toString();
                ex_year = sp_year.getSelectedItem().toString();
                cvc = cvv.getText().toString();
                if (cardHolderName.isEmpty() || cardnumber.isEmpty() || ex_mnth.isEmpty() || ex_year.isEmpty() || cvc.isEmpty()) {
                    Toast.makeText(getActivity(), "Please fill all fileds.", Toast.LENGTH_SHORT).show();
                } else {
                    Utils.hideKeyboardFrom(getActivity(), addCard);
                    onClickPay(cardHolderName, cardnumber, ex_mnth, ex_year, cvc);
                }
                break;

            case R.id.cancel:
                Utils.hideKeyboardFrom(getActivity(), cancel);
                rlCardList.setVisibility(View.VISIBLE);
                llAddCard.setVisibility(View.GONE);
                break;

            case R.id.text_update:
                if (status == 0) {
                    googleMap.getUiSettings().setAllGesturesEnabled(true);
                    textUpdate = "Update";
                    text_update.setBackgroundResource(R.drawable.ic_icon_save);
                    text_name.setHintTextColor(Color.YELLOW);
                    text_name.setHighlightColor(Color.GREEN);
                    text_name.setClickable(true);
                    text_name.setEnabled(true);
                    status = 1;

                } else if (status == 1) {
                    if (text_name.length() > 1) {
                        handler.updateProfile(getActivity(), "update_profile", CSPreferences.readString(context, "user_id"), text_name.getText().toString(), address, text_country.getText().toString(), text_city.getText().toString(), String.valueOf(currentlat), String.valueOf(currentlang));
                    }
                } else {
                    Toast.makeText(context, "Please enter fullname.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.scan_c:
                scanCard();
                break;

            case R.id.text_profile:
                profilelayout.setVisibility(View.VISIBLE);
                cardlayout.setVisibility(View.GONE);
                text_profile.setBackgroundResource(R.drawable.btn_active);
                text_card.setBackgroundResource(R.drawable.btn_inactive);
                text_profile.setTextColor(getResources().getColor(R.color.light_yellow));
                text_card.setTextColor(getResources().getColor(R.color.black));
                break;

            case R.id.text_card:
                text_profile.setBackgroundResource(R.drawable.btn_inactive);
                text_card.setBackgroundResource(R.drawable.btn_active);
                text_profile.setTextColor(getResources().getColor(R.color.black));
                text_card.setTextColor(getResources().getColor(R.color.light_yellow));
                profilelayout.setVisibility(View.GONE);
                cardlayout.setVisibility(View.VISIBLE);
                getCardApi();
                break;
        }
    }

    public void onClickPay(String holderName, String cardNumber, String cardExpMonth, String cardExpYear, String cardCVC) {
                cardToSave = new Card(
                cardNumber,
                Integer.parseInt(cardExpMonth),
                Integer.parseInt(cardExpYear),
                cardCVC
        );

        boolean validation = cardToSave.validateCard();
        if (validation) {
            showProgressDialog();
            new Stripe(getActivity()).createToken(
                    cardToSave,
                    Constants.STRIPE_PUBLISH_KEY,
                    new TokenCallback() {
                        @Override
                        public void onError(Exception error) {
                            hideProgressDialog();
                            Log.d("Stripe", error.toString());
                        }

                        @Override
                        public void onSuccess(Token token) {
                            hideProgressDialog();
                            stripetoken = token.getId().toString();
                            addCardApi(stripetoken);
                        }
                    });
        } else if (!cardToSave.validateNumber()) {
            Toast.makeText(getActivity(), "The card number that you entered is invalid", Toast.LENGTH_SHORT).show();
        } else if (!cardToSave.validateExpiryDate()) {
            Toast.makeText(getActivity(), "The expiration date that you entered is invalid", Toast.LENGTH_SHORT).show();
        } else if (!cardToSave.validateCVC()) {
            Toast.makeText(getActivity(), "The CVC code that you entered is invalid", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "The card details that you entered are invalid", Toast.LENGTH_SHORT).show();
        }
    }

    public void addCardApi(final String tok) {
        handler.addCard(getActivity(), "addCardDetails", userId, cardnumber, cardHolderName, ex_mnth, ex_year, cvc, cardToSave.getBrand(), tok);
    }

    public void getCardApi() {
        handler.getCard(getActivity(), "fetchCardDetails", CSPreferences.readString(getActivity(), "user_id"));
    }

    private void setAdapter() {
        datumList = bean.getResponse().getData();
        Log.d("Size", datumList.size() + "");
        arrayAdapter = new CardAddAdapter(getActivity(), datumList);
        cardList.setAdapter(arrayAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == REQUEST_SCAN) && data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
            String resultDisplayStr;
            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);
                resultDisplayStr = "Card Number: " + scanResult.getRedactedCardNumber() + "\n";
                card_number.setText(scanResult.getRedactedCardNumber());
                if (scanResult.isExpiryValid()) {
                    resultDisplayStr += "Expiration Date: " + scanResult.expiryMonth + "/" + scanResult.expiryYear + "\n";
                }
                if (scanResult.cvv != null) {
                    resultDisplayStr += "CVV has " + scanResult.cvv.length() + " digits.\n";
                }
                if (scanResult.postalCode != null) {
                    resultDisplayStr += "Postal Code: " + scanResult.postalCode + "\n";
                }
            } else {
                resultDisplayStr = "Scan was canceled.";
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/abc.ttf");
        ((TextView) view).setTextColor(Color.BLACK);
        ((TextView) view).setTypeface(tf);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        boolean flag = true;

        String eachBlock[] = card_number.getText().toString().split("-");
        for (int j = 0; j < eachBlock.length; j++) {
            if (eachBlock[j].length() > 4) {
                flag = false;
            }
        }
        if (flag) {

            card_number.setOnKeyListener(new View.OnKeyListener() {

                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {

                    if (keyCode == KeyEvent.KEYCODE_DEL)
                        keyDel = 1;
                    return false;
                }
            });

            if (keyDel == 0) {

                if (((card_number.getText().length() + 1) % 5) == 0) {

                    if (card_number.getText().toString().split("-").length <= 3) {
                        card_number.setText(card_number.getText() + "-");
                        card_number.setSelection(card_number.getText().length());
                    }
                }
                a = card_number.getText().toString();
            } else {
                a = card_number.getText().toString();
                keyDel = 0;
            }

        } else {
            card_number.setText(a);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

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
                    String fullname = jsonObject11.getString("fullname");
                    String address = jsonObject11.getString("address");
                    String country = jsonObject11.getString("country");
                    String city = jsonObject11.getString("city");
                    CSPreferences.putString(getActivity(), "user_id", userid);
                    CSPreferences.putString(getActivity(), "user_name", fullname);
                }
                status = 0;
                googleMap.getUiSettings().setAllGesturesEnabled(false);
                textUpdate = "Edit";
                text_update.setBackgroundResource(R.drawable.ic_icon_edit);
                text_name.setClickable(false);
                text_name.setEnabled(false);
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
            CSPreferences.putString(getActivity(), "profile_status", String.valueOf(id));
            JSONArray jsonArray = respons.getJSONArray("data");


            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                mFullName = jsonObject1.getString("fullname");
                mAddress = jsonObject1.getString("address");
                mCity = jsonObject1.getString("city");
                mCountry = jsonObject1.getString("country");
                mLatitude = jsonObject1.getString("latitude");
                mLongitude = jsonObject1.getString("longitude");
                CSPreferences.putString(getActivity(), "fullname", mFullName);
                CSPreferences.putString(getActivity(), "latitude", mLatitude);
                CSPreferences.putString(getActivity(), "longitude", mLongitude);
            }

            text_name.setText(mFullName);
            addresstext.setText(mAddress);
            text_city.setText(mCity);
            text_country.setText(mCountry);

        } catch (JSONException e) {
            e.printStackTrace();
            showToast(e.getMessage().toString());
        }
    }

    @Override
    public void getCardSuccess(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject mainResponse = jsonObject.getJSONObject("response");
            if (mainResponse.has("data") && (mainResponse.getJSONArray("data").length()) != 0) {
                bean = new Gson().fromJson(response, CardDetailsBean.class);
                llAddCard.setVisibility(View.GONE);
                rlCardList.setVisibility(View.VISIBLE);
                setAdapter();
            } else {
                rlCardList.setVisibility(View.GONE);
                llAddCard.setVisibility(View.VISIBLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void addCardSuccess(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject mainresp = jsonObject.getJSONObject("response");
            String apistatus = mainresp.getString("status");
            if (apistatus.equals("0")) {
                Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show();
            } else if (apistatus.equals("1")) {
                Toast.makeText(context, mainresp.getString("message"), Toast.LENGTH_SHORT).show();
                rlCardList.setVisibility(View.VISIBLE);
                llAddCard.setVisibility(View.GONE);
                getCardApi();
            } else if (apistatus.equals("2")) {
                Toast.makeText(context, mainresp.getString("message"), Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}