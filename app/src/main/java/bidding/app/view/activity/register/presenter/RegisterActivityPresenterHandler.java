package bidding.app.view.activity.register.presenter;

import android.content.Context;

import com.google.gson.JsonObject;

import bidding.app.view.activity.register.signuprequest.SellerSignUpRequest;
import bidding.app.view.activity.register.signuprequest.SignUpRequest;

/**
 * Created by Thakur on 11/12/2017.
 */

public interface RegisterActivityPresenterHandler {
    void signUp(SignUpRequest request);
    void sellerSignUp(SellerSignUpRequest request);
}
