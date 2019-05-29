package bidding.app.view.activity.payment.presenter;

import android.app.Activity;

import bidding.app.view.activity.payment.view.PaymentActivityView;
import bidding.app.web.WebServc;
import bidding.app.web.handler.GetLoginHandler;
import bidding.app.web.handler.UpdateProfileHandler;


/**
 * Created by Thakur on 11/12/2017.
 */

public class PaymentActivityPresenter implements PaymentActivityPresenterHandler {

    PaymentActivityView view;
    String user_id = "", fullname = "", address = "", country = "", city = "", latitude = "", longitude = "";

    public PaymentActivityPresenter(PaymentActivityView view) {
        this.view = view;

    }

    @Override
    public void getCard(Activity activity, String action, String userId) {
        view.showProgess();
        WebServc.getInstance().getCardList(new UpdateProfileHandler() {
            @Override
            public void onSuccess(String Response) {
                view.hideProgess();
                view.getCardSuccess(Response);
            }

            @Override
            public void onFail(String message) {
                view.hideProgess();
                view.showFeedBackMessage(message.toString());

            }
        }, action, userId);
    }

    @Override
    public void addCard(Activity activity, String action, String UserId, String cardnumber, String cardHolderName, String ex_mnth, String ex_year, String cvc, String brand, String tok) {
        view.showProgess();
        WebServc.getInstance().addCard(new UpdateProfileHandler() {
            @Override
            public void onSuccess(String Response) {
                view.hideProgess();
                view.addCardSuccess(Response);

            }

            @Override
            public void onFail(String message) {
                view.hideProgess();

            }
        },action,UserId,cardnumber,cardHolderName,ex_mnth,ex_year,cvc,brand,tok);
    }

}

