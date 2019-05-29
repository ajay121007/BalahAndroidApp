package bidding.app.view.activity.register.presenter;

import bidding.app.view.activity.register.signuprequest.SellerSignUpRequest;
import bidding.app.view.activity.register.signuprequest.SignUpRequest;
import bidding.app.view.activity.register.view.RegisterActivityView;
import bidding.app.web.WebServc;
import bidding.app.web.handler.CallBackHandler;
import bidding.app.web.handler.GetLoginHandler;


/**
 * Created by Thakur on 11/12/2017.
 */

public class RegisterActivityPresenter implements RegisterActivityPresenterHandler {

    RegisterActivityView view;
    String user_id = "", fullname = "", address = "", country = "", city = "", latitude = "", longitude = "";

    public RegisterActivityPresenter(RegisterActivityView view) {
        this.view = view;

    }


    @Override
    public void signUp(SignUpRequest request) {
        view.showProgess();
        WebServc.getInstance().signUp(new GetLoginHandler() {
            @Override
            public void onSuccess(String Response) {
                view.hideProgess();
                view.registerSuccess(Response);

            }

            @Override
            public void onFail(String message) {
                view.hideProgess();
                view.showFeedBackMessage(message.toString());

            }

        }, request);

    }

    @Override
    public void sellerSignUp(SellerSignUpRequest request) {
        view.showProgess();
        WebServc.getInstance().sellerSignUp(new CallBackHandler() {
            @Override
            public void Success(String response) {
                view.hideProgess();
                view.registerSuccess(response);
            }

            @Override
            public void Failuer(String error) {
                view.hideProgess();
                view.showFeedBackMessage(error.toString());
            }

        }, request);
    }
}

