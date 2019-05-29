package bidding.app.web.handler;

public interface PendingPaymentHandler {
    void onSuccess(String Response);
    void onFail(String message);
}
