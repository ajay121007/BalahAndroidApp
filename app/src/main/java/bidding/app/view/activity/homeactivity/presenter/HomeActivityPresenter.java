package bidding.app.view.activity.homeactivity.presenter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import bidding.app.MyApplication;
import bidding.app.extra.CSPreferences;
import bidding.app.extra.Constants;
import bidding.app.view.activity.FirstScreen;
import bidding.app.view.activity.homeactivity.HomeActivity;
import bidding.app.view.activity.homeactivity.view.HomeActivityView;
import bidding.app.view.activity.loginactivity.LoginActivity;
import bidding.app.view.activity.mainactivity.view.MainActivityView;
import bidding.app.web.WebServc;
import bidding.app.web.handler.CallBackHandler;
import bidding.app.web.handler.UpdateProfileHandler;


/**
 * Created by Thakur on 11/12/2017.
 */

public class HomeActivityPresenter implements HomeActivityPresenterHandler {

    HomeActivityView view;

    public HomeActivityPresenter(HomeActivityView view) {
        this.view = view;

    }

    @Override
    public void logout(final Activity activity, String action, String userId) {
        view.showProgess();
        WebServc.getInstance().logout(new UpdateProfileHandler() {
            @Override
            public void onSuccess(String Response) {
                view.hideProgess();
                Log.d("Logout Response", Response + "");
                try {
                    JSONObject jsonObject = new JSONObject(Response);
                    JSONObject jsonObject1 = jsonObject.getJSONObject("response");
                    view.showFeedBackMessage(jsonObject1.getString("message"));
                    CSPreferences.putString(activity, "login_status", "false");
                    CSPreferences.putString(activity, Constants.TOKEN, "");
                    CSPreferences.putString(activity, "seclectedAuction", "");
                    MyApplication.type = "";
                    activity.startActivity(new Intent(activity, FirstScreen.class));
                    activity.finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                    view.showFeedBackMessage(e.getMessage().toString());
                }
            }

            @Override
            public void onFail(String message) {
                view.hideProgess();
                view.showFeedBackMessage(message.toString());
            }
        }, action, userId);
    }

    @Override
    public void isValidCustomer(final HomeActivity activity, String token) {
        view.showProgess();
        WebServc.getInstance().getProfile(new CallBackHandler() {
            @Override
            public void Success(String response) {
                Log.d("CustomerResponse", response.toString());
                view.hideProgess();
                String mFirstName = "", mLastName = "", mPhone = "", mEmail = "";
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.has("message")) {
                        view.showFeedBackMessage(object.getString("message"));
                        CSPreferences.putString(activity, "login_status", "false");
                        CSPreferences.putString(activity, Constants.TOKEN, "");
                        CSPreferences.putString(activity, "seclectedAuction", "");
                        MyApplication.type = "";
                        activity.startActivity(new Intent(activity, LoginActivity.class));
                        activity.finish();
                    } else {
                        mFirstName = object.getString("firstname");
                        mLastName = object.getString("lastname");
                        mEmail = object.getString("email");

                        JSONArray jsonArray = object.getJSONArray("custom_attributes");
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        if (jsonObject.getString("attribute_code").equalsIgnoreCase("phone_number"))
                            mPhone = jsonObject.getString("value");

                        view.setData(mFirstName, mLastName, mPhone, mEmail);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void Failuer(String error) {
                view.hideProgess();
                view.showFeedBackMessage("Session erpired.");
                CSPreferences.putString(activity, "login_status", "false");
                CSPreferences.putString(activity, Constants.TOKEN, "");
                CSPreferences.putString(activity, "seclectedAuction", "");
                MyApplication.type = "";
                activity.startActivity(new Intent(activity, LoginActivity.class));
                activity.finish();
            }
        }, activity, token);
    }
}

