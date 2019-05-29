package bidding.app.view.activity.loginactivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.facebook.accountkit.ui.SkinManager;
import com.facebook.accountkit.ui.UIManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import bidding.app.R;
import bidding.app.extra.CSPreferences;
import bidding.app.extra.Constants;
import bidding.app.view.activity.loginactivity.requestpojo.LoginRequest;
import bidding.app.view.activity.homeactivity.HomeActivity;
import bidding.app.view.activity.BaseActivity;
import bidding.app.view.activity.loginactivity.presenter.LoginActivityPresenter;
import bidding.app.view.activity.loginactivity.presenter.LoginActivityPresenterHandler;
import bidding.app.view.activity.loginactivity.requestpojo.Params;
import bidding.app.view.activity.loginactivity.view.LoginActivityView;
import bidding.app.view.activity.register.CustomerRegisterActivity;
import bidding.app.view.activity.register.SelectUserActivity;
import bidding.app.view.activity.register.SellerRegisterActivity;

import static com.facebook.accountkit.ui.SkinManager.Skin.CLASSIC;
import static com.facebook.accountkit.ui.SkinManager.Skin.CONTEMPORARY;
import static com.facebook.accountkit.ui.SkinManager.Skin.TRANSLUCENT;


public class LoginActivity extends BaseActivity implements LoginActivityView {
    Button btnLogoin;
    CheckBox checkBox;
    Context context;
    private static final int REQUEST = 112;
    LoginActivityPresenterHandler handler;
    public static int APP_REQUEST_CODE = 99;
    String mToken = "", mPhoneNumber = "", mCountryCode = "";
    int isVendor = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        handler = new LoginActivityPresenter(this);
        btnLogoin = findViewById(R.id.btn_login);
        checkBox = (CheckBox) findViewById(R.id.terms_condtions);

        CheckPermission();

        btnLogoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkBox.isChecked()) {
                    initAccountKitSmsFlow();
                } else {
                    Toast.makeText(context, getResources().getString(R.string.termconditon), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void CheckPermission() {

        if (Build.VERSION.SDK_INT >= 23) {

            Log.d("TAG", "@@@ IN IF Build.VERSION.SDK_INT >= 23");
            String[] PERMISSIONS = {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            };

            if (!hasPermissions(context, PERMISSIONS)) {
                Log.d("TAG", "@@@ IN IF hasPermissions");
                ActivityCompat.requestPermissions((Activity) context, PERMISSIONS, REQUEST);
            } else {
                Log.d("TAG", "@@@ IN ELSE hasPermissions");
                //  startActivity();
            }
        } else {

            //  startActivity();
            Log.d("TAG", "@@@ IN ELSE  Build.VERSION.SDK_INT >= 23");

        }

    }

    @SuppressLint("ResourceAsColor")
    public void initAccountKitSmsFlow() {
        final Intent intent = new Intent(this, AccountKitActivity.class);

        UIManager uiManager;
        uiManager = new SkinManager( TRANSLUCENT,R.color.colorPrimary,R.drawable.ic_icon_login_background, SkinManager.Tint.WHITE,50-80);

        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        LoginType.PHONE,
                        AccountKitActivity.ResponseType.TOKEN);
        intent.putExtra(
                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configurationBuilder.setUIManager(uiManager).setReadPhoneStateEnabled(true).setReceiveSMS(true).build());
        startActivityForResult(intent, APP_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == APP_REQUEST_CODE) {
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            String toastMessage = "";
            if (loginResult.getError() != null) {
                toastMessage = loginResult.getError().getErrorType().getMessage();

            } else if (loginResult.wasCancelled()) {
                toastMessage = "Login Cancelled";
            } else {
                if (loginResult.getAccessToken() != null) {
                    toastMessage = loginResult.getAccessToken().getToken();
                    AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                        @Override
                        public void onSuccess(Account account) {
                            if (account.getId() != null) {
                                try {
                                    LoginRequest loginRequest = new LoginRequest();
                                    Params params = new Params();
                                    mPhoneNumber = account.getPhoneNumber().getPhoneNumber().toString();
                                    mCountryCode = "+" + account.getPhoneNumber().getCountryCode().toString();
                                    params.setCountryCode(mCountryCode);
                                    params.setPhoneNumber(mPhoneNumber);
                                    loginRequest.setParams(params);
                                    handler.getLogin(loginRequest);
                                } catch (Exception e) {
                                    Log.d("JSONERROR", e.getMessage().toString());
                                }
                            }
                        }


                        @Override
                        public void onError(AccountKitError accountKitError) {
                            Log.d("AccountKitError", accountKitError.toString());
                        }
                    });
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("TAG", "@@@ PERMISSIONS grant");
                    //  startActivity();

                } else {
                    Log.d("TAG", "@@@ PERMISSIONS Denied");
                    Toast.makeText(getApplicationContext(), "PERMISSIONS Denied", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
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
    public void loginSuccess(String response) {

        try {
            JSONArray jsonArray = new JSONArray(response);
            JSONObject object = jsonArray.getJSONObject(0);
            JSONObject mainResponse = object.getJSONObject("response");
            JSONArray data = mainResponse.getJSONArray("data");
            JSONObject jsonObject = data.getJSONObject(0);
            if (mainResponse.getInt("status") == 0) {
                startActivity(new Intent(LoginActivity.this, SelectUserActivity.class).putExtra("phone_number", mPhoneNumber).putExtra("county_code", mCountryCode));
                showToast(object.getString("success_message"));
            } else if (mainResponse.getInt("status") == 1) {
                mToken = jsonObject.getString("token");
                isVendor = jsonObject.getInt("is_vendor");
                mPhoneNumber = jsonObject.getString("phone_number");
                CSPreferences.putString(LoginActivity.this, Constants.TOKEN, mToken);
                CSPreferences.putString(LoginActivity.this, Constants.ISVENDOR, jsonObject.getInt("is_vendor") + "");

                if (isVendor == 1)
                    CSPreferences.putString(LoginActivity.this, Constants.USERID, jsonObject.getString("vendor_id"));
                /*else
                    CSPreferences.putString(LoginActivity.this, Constants.USERID, jsonObject.getString("customer_id"));*/

                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void profileSuccess() {
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        finish();
    }
}
