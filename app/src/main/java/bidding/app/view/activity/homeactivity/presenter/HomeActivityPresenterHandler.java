package bidding.app.view.activity.homeactivity.presenter;

import android.app.Activity;

import bidding.app.view.activity.homeactivity.HomeActivity;

/**
 * Created by Thakur on 11/12/2017.
 */

public interface HomeActivityPresenterHandler {

    void logout(Activity activity, String action, String userId);
    void isValidCustomer(HomeActivity homeActivity, String s);
}
