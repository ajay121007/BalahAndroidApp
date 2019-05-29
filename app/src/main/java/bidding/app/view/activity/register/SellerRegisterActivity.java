package bidding.app.view.activity.register;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.List;

import bidding.app.R;
import bidding.app.extra.CSPreferences;
import bidding.app.extra.Constants;
import bidding.app.extra.Utils;
import bidding.app.extra.Validation;
import bidding.app.view.activity.BaseActivity;
import bidding.app.view.activity.homeactivity.HomeActivity;
import bidding.app.view.activity.register.adapter.CountryListAdapter;
import bidding.app.view.activity.register.presenter.RegisterActivityPresenter;
import bidding.app.view.activity.register.presenter.RegisterActivityPresenterHandler;
import bidding.app.view.activity.register.signuprequest.SellerSignUpRequest;
import bidding.app.view.activity.register.view.RegisterActivityView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SellerRegisterActivity extends BaseActivity implements RegisterActivityView {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.ed_id)
    EditText edId;
    @BindView(R.id.ed_name)
    EditText edName;
    @BindView(R.id.ed_last_name)
    EditText edLastName;
    @BindView(R.id.ed_mobile)
    EditText edMobile;
    @BindView(R.id.ed_email)
    EditText edEmail;
    @BindView(R.id.ed_location)
    EditText edLocation;
    @BindView(R.id.ed_country)
    TextView edCountry;
    @BindView(R.id.ed_city)
    EditText edCity;
    @BindView(R.id.ed_state)
    EditText edState;
    @BindView(R.id.ed_postal_code)
    EditText edPostalCode;
    @BindView(R.id.ed_company)
    EditText edCompany;
    @BindView(R.id.rl_create_account)
    RelativeLayout rlCreateAccount;
    RegisterActivityPresenterHandler handler;
    String mFirstName = "", mLastName = "", mEmail = "", mPhonNumber = "", mVendorId = "", mCity = "", mPostCode = "", mStreet = "", mCountryCode = "", mState = "", mCompany = "";
    @BindView(R.id.ll_country)
    LinearLayout llCountry;
    private AlertDialog.Builder alertDialog;
    View view;
    RecyclerView recyclerView;
    ImageView ivClose;
    private String[] countryArray;
    private List<String> countryList;
    CountryListAdapter listAdapter;
    LinearLayoutManager layoutManager;
    AlertDialog countryDialog;
    boolean isValid;
    private Intent intent;
    int isVendor = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_register);
        ButterKnife.bind(this);

        inti();
        setCountry();
    }

    private void inti() {

        intent = getIntent();
        if (intent != null && intent.getExtras().containsKey("phone_number")) {
            mPhonNumber = intent.getExtras().getString("phone_number");
            edMobile.setText(mPhonNumber);
        }

        countryArray = getResources().getStringArray(R.array.country_list);
        countryList = Arrays.asList(countryArray);
        handler = new RegisterActivityPresenter(this);
    }

    public void setCountry() {
        alertDialog = new AlertDialog.Builder(this);
        view = LayoutInflater.from(this).inflate(R.layout.dialog_country, null);
        alertDialog.setView(view);
        recyclerView = view.findViewById(R.id.rv_country);
        ivClose = view.findViewById(R.id.iv_close);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countryDialog.dismiss();
            }
        });


        listAdapter = new CountryListAdapter(this, countryList, new CountryListAdapter.ItemClick() {
            @Override
            public void OnItemClick(int position) {
                String[] parts = countryList.get(position).split(",");
                edCountry.setText(parts[0] + "");
                mCountryCode = parts[1];
                countryDialog.dismiss();
                listAdapter.customNotify(position);
            }
        });
        recyclerView.setAdapter(listAdapter);
        countryDialog = alertDialog.create();

        countryDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = lp.WRAP_CONTENT;
        lp.height = lp.WRAP_CONTENT;
        countryDialog.getWindow().setAttributes(lp);
        countryDialog.setCanceledOnTouchOutside(true);
    }

    @OnClick({R.id.back, R.id.rl_create_account, R.id.ll_country, R.id.ed_country})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.rl_create_account:
                sellerSignUp();
                break;
            case R.id.ll_country:
                Utils.hideKeyboardFrom(this, llCountry);
                countryDialog.show();
                break;
            case R.id.ed_country:
                Utils.hideKeyboardFrom(this, edCountry);
                countryDialog.show();
                break;
        }
    }

    private void sellerSignUp() {
        SellerSignUpRequest request = new SellerSignUpRequest();
        SellerSignUpRequest.SellerDataBean sellerDataBean;


        mFirstName = edName.getText().toString();
        mLastName = edLastName.getText().toString();
        mEmail = edEmail.getText().toString();
        mPhonNumber = edMobile.getText().toString();
        mVendorId = edId.getText().toString();
        mCity = edCity.getText().toString();
        mPostCode = edPostalCode.getText().toString();
        mStreet = edLocation.getText().toString();
        mState = edState.getText().toString();
        mCompany = edCompany.getText().toString();

        isValid = Validation.getInstanse().isValidSeller(this, mFirstName, mLastName, mEmail, mPhonNumber, mVendorId, mCity, mCountryCode, mPostCode, mStreet, mState, mCompany);

        if (isValid) {
            sellerDataBean = new SellerSignUpRequest.SellerDataBean(mFirstName, mLastName, mEmail, mPhonNumber, mVendorId, mCity, mPostCode, mStreet, mCountryCode, mState, mCompany);
            request.setSellerData(sellerDataBean);
            handler.sellerSignUp(request);
        }


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
    public void registerSuccess(String response) {
        Log.d("SellerRegister", response.toString());
        try {
            JSONArray jsonArray = new JSONArray(response.toString());
            JSONObject object = jsonArray.getJSONObject(0);
            JSONObject data = object.getJSONObject("response");
            JSONArray array = data.getJSONArray("data");
            JSONObject jsonObject = array.getJSONObject(0);
            if (data.getInt("status") == 0) {
                showToast(jsonObject.getString("message"));
            } else {
                showToast(jsonObject.getString("message"));
                CSPreferences.putString(SellerRegisterActivity.this, Constants.TOKEN, jsonObject.getString("token"));
                isVendor = jsonObject.getInt("is_vendor");
                CSPreferences.putString(SellerRegisterActivity.this, Constants.ISVENDOR, isVendor + "");

                if (isVendor == 1)
                    CSPreferences.putString(SellerRegisterActivity.this, Constants.USERID, jsonObject.getString("vendor_id"));
                else
                    CSPreferences.putString(SellerRegisterActivity.this, Constants.USERID, jsonObject.getString("customer_id"));


                startActivity(new Intent(SellerRegisterActivity.this, HomeActivity.class));
                finish();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
