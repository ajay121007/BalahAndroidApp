package bidding.app.view.activity.payment.view;

/**
 * Created by Thakur on 11/12/2017.
 */

public interface PaymentActivityView {
    void showProgess();
    void hideProgess();
    void showFeedBackMessage(String message);
    void getCardSuccess(String response);
    void addCardSuccess(String response);

}
