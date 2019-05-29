package bidding.app.view.fragment.profilefragment.presenter;

import android.app.Activity;

/**
 * Created by Thakur on 11/12/2017.
 */

public interface ProfileFragmentPresenterHandler {

    void updateProfile(Activity activity, String action, String userId, String name, String address, String country,String city, String latitude, String longitute);
    void getCard(Activity activity, String action, String userId);
    void addCard(Activity activity, String action, String UserId, String cardnumber, String cardHolderName, String ex_mnth, String ex_year, String cvc, String brand, String tok);
    void getProfile(Activity activity, String action, String userId);
}
