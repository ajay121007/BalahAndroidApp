package bidding.app.view.activity.payment.presenter;

import android.app.Activity;

/**
 * Created by Thakur on 11/12/2017.
 */

public interface PaymentActivityPresenterHandler {
     void getCard(Activity activity, String action, String userId);
     void addCard(Activity activity, String action, String UserId, String cardnumber, String cardHolderName, String ex_mnth, String ex_year, String cvc, String brand, String tok);
}
