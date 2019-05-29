package bidding.app.view.activity.myprofile.presenter;

import android.app.Activity;
import android.content.Context;

/**
 * Created by Thakur on 11/12/2017.
 */

public interface ProfileActivityPresenterHandler {
     void getProfile(Activity activity,String action,String userId);
     void updateProfile(Activity activity, String action, String userId, String name, String address, String country,String city, String latitude, String longitute);
}
