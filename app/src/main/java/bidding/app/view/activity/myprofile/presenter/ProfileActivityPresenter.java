package bidding.app.view.activity.myprofile.presenter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import bidding.app.extra.CSPreferences;
import bidding.app.view.activity.myprofile.view.ProfileActivityView;
import bidding.app.web.WebServc;
import bidding.app.web.handler.GetLoginHandler;
import bidding.app.web.handler.UpdateProfileHandler;


/**
 * Created by Thakur on 11/12/2017.
 */

public class ProfileActivityPresenter implements ProfileActivityPresenterHandler {

    ProfileActivityView view;
    String user_id = "", fullname = "", address = "", country = "", city = "", latitude = "", longitude = "";

    public ProfileActivityPresenter(ProfileActivityView view) {
        this.view = view;

    }

    @Override
    public void getProfile(Activity activity, String action, String userId) {
        view.showProgess();
        WebServc.getInstance().getProfile(new GetLoginHandler() {
            @Override
            public void onSuccess(String Response) {
                view.hideProgess();
                view.profileSuccess(Response);

            }

            @Override
            public void onFail(String message) {
                view.hideProgess();
                view.showFeedBackMessage(message);
            }
        },action,userId);
    }

    @Override
    public void updateProfile(Activity activity, String action, String userId, String name, String address, String country, String city, String latitude, String longitute) {
        view.showProgess();
        WebServc.getInstance().updateProfile(new UpdateProfileHandler() {
            @Override
            public void onSuccess(String Response) {
                view.hideProgess();
                view.updateProfileSuccess(Response);
            }

            @Override
            public void onFail(String message) {
                view.hideProgess();
                view.showFeedBackMessage(message.toString());
            }
        }, action, userId, name, address, country, city, latitude, longitute);
    }

}

