package bidding.app.view.activity.mainactivity.presenter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import bidding.app.MyApplication;
import bidding.app.extra.CSPreferences;
import bidding.app.view.activity.FirstScreen;
import bidding.app.view.activity.mainactivity.view.MainActivityView;
import bidding.app.web.WebServc;
import bidding.app.web.handler.UpdateProfileHandler;


/**
 * Created by Thakur on 11/12/2017.
 */

public class MainActivityPresenter implements MainActivityPresenterHandler {

    MainActivityView view;

    public MainActivityPresenter(MainActivityView view) {
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
                    CSPreferences.putString(activity, "user_id", "");
                    CSPreferences.putString(activity, "seclectedAuction", "");
                    MyApplication.type="";
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
}

