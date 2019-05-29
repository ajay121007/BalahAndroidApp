package bidding.app.view.activity.loginactivity.presenter;

import android.content.Context;

import bidding.app.view.activity.loginactivity.requestpojo.LoginRequest;

/**
 * Created by Thakur on 11/12/2017.
 */

public interface LoginActivityPresenterHandler {
    void getLogin(LoginRequest rawData);
    void getProfile(Context context, String action, String userid);
}
