package bidding.app.view.activity.loginactivity.presenter;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import bidding.app.extra.CSPreferences;
import bidding.app.view.activity.loginactivity.requestpojo.LoginRequest;
import bidding.app.view.activity.loginactivity.view.LoginActivityView;
import bidding.app.web.WebServc;
import bidding.app.web.handler.GetLoginHandler;


/**
 * Created by Thakur on 11/12/2017.
 */

public class LoginActivityPresenter implements LoginActivityPresenterHandler {

    LoginActivityView view;
    String user_id = "", fullname = "", address = "", country = "", city = "", latitude = "", longitude = "";

    public LoginActivityPresenter(LoginActivityView view) {
        this.view = view;

    }

    @Override
    public void getLogin(LoginRequest json) {
        Log.d("RawData",json.toString());
        view.showProgess();
        WebServc.getInstance().getLogin(new GetLoginHandler() {
            @Override
            public void onSuccess(String s) {
                view.hideProgess();
                Log.d("LOGINRESPONSE", s + "");
                view.loginSuccess(s);
            }

            @Override
            public void onFail(String message) {
                Log.e("LOGINERROR", message + "");
                view.hideProgess();
                view.showFeedBackMessage(message.toString());
            }
        },json);
    }

    @Override
    public void getProfile(final Context context, String action, String userid) {
        view.showProgess();
        WebServc.getInstance().getProfile(new GetLoginHandler() {
            @Override
            public void onSuccess(String Response) {
                view.hideProgess();
                Log.d("GETPROFILERESPONSE", Response + "");
                try {
                    JSONObject jsonObject = new JSONObject(Response);
                    JSONObject response = jsonObject.getJSONObject("response");
                    String message = response.getString("message");
                    int id = Integer.parseInt(response.getString("status"));
                    CSPreferences.putString(context, "profile_status", String.valueOf(id));
                    JSONArray jsonArray = response.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        user_id = jsonObject1.getString("user_id");
                        fullname = jsonObject1.getString("fullname");
                        address = jsonObject1.getString("address");
                        country = jsonObject1.getString("country");
                        city = jsonObject1.getString("city");
                        latitude = jsonObject1.getString("latitude");
                        longitude = jsonObject1.getString("longitude");
                        CSPreferences.putString(context, "fullname", fullname);
                        CSPreferences.putString(context, "latitude", latitude);
                        CSPreferences.putString(context, "longitude", longitude);
                    }
                    CSPreferences.putString(context, "login_status", "true");
                    view.profileSuccess();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("Exception", e.getMessage().toString());
                }
            }

            @Override
            public void onFail(String message) {
                view.hideProgess();
                Log.d("GETPROFILEERROR", message + "");
                view.showFeedBackMessage(message);
            }
        }, action, userid);
    }
}

