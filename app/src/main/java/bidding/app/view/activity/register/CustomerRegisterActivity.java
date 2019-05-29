package bidding.app.view.activity.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import bidding.app.R;
import bidding.app.extra.CSPreferences;
import bidding.app.extra.Constants;
import bidding.app.extra.Validation;
import bidding.app.view.activity.BaseActivity;
import bidding.app.view.activity.homeactivity.HomeActivity;
import bidding.app.view.activity.register.presenter.RegisterActivityPresenter;
import bidding.app.view.activity.register.presenter.RegisterActivityPresenterHandler;
import bidding.app.view.activity.register.signuprequest.CustomerData;
import bidding.app.view.activity.register.signuprequest.SignUpRequest;
import bidding.app.view.activity.register.view.RegisterActivityView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomerRegisterActivity extends BaseActivity implements RegisterActivityView {
    Intent intent;
    String mPhoneNumber = "", mFirstName = "", mLastName = "", mEmail = "", mPassword = "", mConfirmPassword = "", mCountyCode = "", mPhoneWithCountry = "";
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.ed_name)
    EditText edName;
    @BindView(R.id.ed_last_name)
    EditText edLastName;
    @BindView(R.id.ed_ohone_number)
    EditText edOhoneNumber;
    @BindView(R.id.ed_email)
    EditText edEmail;
    @BindView(R.id.ed_password)
    EditText edPassword;
    @BindView(R.id.ed_confirm_password)
    EditText edConfirmPassword;
    @BindView(R.id.rl_create_account)
    RelativeLayout rlCreateAccount;
    RegisterActivityPresenterHandler handler;
    boolean isValid = false;
    private int isVendor=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        inti();
    }

    private void inti() {


        handler = new RegisterActivityPresenter(this);
        intent = getIntent();
        if (intent != null && intent.getExtras().containsKey("phone_number")) {
            mPhoneNumber = intent.getExtras().getString("phone_number");
            mCountyCode = intent.getExtras().getString("county_code");
            mPhoneWithCountry = mCountyCode + " " + mPhoneNumber;
            edOhoneNumber.setText(mPhoneWithCountry);
        }
    }

    @OnClick({R.id.back, R.id.rl_create_account})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.rl_create_account:
                signUp();
                break;
        }
    }

    private void signUp() {
        SignUpRequest request = new SignUpRequest();
        CustomerData data = new CustomerData();

        mFirstName = edName.getText().toString();
        mLastName = edLastName.getText().toString();
        mEmail = edEmail.getText().toString();
        mPassword = edPassword.getText().toString();
        mConfirmPassword = edConfirmPassword.getText().toString();

        isValid = Validation.isValidSignUp(this, mFirstName, mLastName, mEmail, mPassword, mConfirmPassword);

        if (isValid) {
            data.setFirstname(mFirstName);
            data.setLastname(mLastName);
            data.setEmail(mEmail);
            data.setCountryCode(mCountyCode);
            data.setPhoneNumber(mPhoneNumber);
            request.setCustomerData(data);

            handler.signUp(request);
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

        try {
            JSONArray jsonArray = new JSONArray(response);
            JSONObject object = jsonArray.getJSONObject(0);
            JSONObject mainResponse = object.getJSONObject("response");
            JSONArray data = mainResponse.getJSONArray("data");
            JSONObject jsonObject = data.getJSONObject(0);
            if (mainResponse.getInt("status") == 1) {
                showToast(jsonObject.getString("message"));
                CSPreferences.putString(CustomerRegisterActivity.this, Constants.TOKEN, jsonObject.getString("token"));

                isVendor = jsonObject.getInt("is_vendor");
                CSPreferences.putString(CustomerRegisterActivity.this, Constants.ISVENDOR, isVendor + "");

                if (isVendor == 1)
                    CSPreferences.putString(CustomerRegisterActivity.this, Constants.USERID, jsonObject.getString("vendor_id"));
                else
                    CSPreferences.putString(CustomerRegisterActivity.this, Constants.USERID, jsonObject.getString("customer_id"));

                startActivity(new Intent(CustomerRegisterActivity.this, HomeActivity.class));
                finish();
            } else if (mainResponse.getInt("status") == 0) {
                showToast(jsonObject.getString("message"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
