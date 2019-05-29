package bidding.app.view.activity.paymentactivity;


public interface PaymentStatusRequestListener {

    void onErrorOccurred();
    void onPaymentStatusReceived(String paymentStatus);
}
