package bidding.app.view.activity.paymentactivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.oppwa.mobile.connect.checkout.dialog.CheckoutActivity;
import com.oppwa.mobile.connect.checkout.meta.CheckoutSettings;
import com.oppwa.mobile.connect.checkout.meta.CheckoutSkipCVVMode;
import com.oppwa.mobile.connect.exception.PaymentError;
import com.oppwa.mobile.connect.provider.Connect;
import com.oppwa.mobile.connect.provider.Transaction;
import com.oppwa.mobile.connect.provider.TransactionType;

import org.json.JSONException;
import org.json.JSONObject;

import bidding.app.R;
import bidding.app.extra.Constants;
import bidding.app.view.activity.BaseActivity;


/**
 * Represents a base activity for making the payments with mobile sdk.
 * This activity handles payment callbacks.
 */
@SuppressLint("Registered")
public class BasePaymentActivity extends BaseActivity
        implements CheckoutIdRequestListener, PaymentStatusRequestListener {

    private static final String STATE_RESOURCE_PATH = "STATE_RESOURCE_PATH";

    protected String resourcePath;
    private String mCheckoutId = "";
    static String totalAmount="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            resourcePath = savedInstanceState.getString(STATE_RESOURCE_PATH);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);

        /* Check if the intent contains the callback scheme. */
        if (resourcePath != null && hasCallbackScheme(intent)) {
            requestPaymentStatus(resourcePath);
        }
    }

    /**
     * Returns <code>true</code> if the Intent contains one of the predefined schemes for the app.
     *
     * @param intent the incoming intent
     * @return <code>true</code> if the Intent contains one of the predefined schemes for the app;
     * <code>false</code> otherwise
     */
    protected boolean hasCallbackScheme(Intent intent) {
        String scheme = intent.getScheme();

        return getString(R.string.checkout_ui_callback_scheme).equals(scheme) ||
                getString(R.string.payment_button_callback_scheme).equals(scheme) ||
                getString(R.string.custom_ui_callback_scheme).equals(scheme);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(STATE_RESOURCE_PATH, resourcePath);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /* Override onActivityResult to get notified when the checkout process is done. */
        if (requestCode == CheckoutActivity.CHECKOUT_ACTIVITY) {
            switch (resultCode) {
                case CheckoutActivity.RESULT_OK:
                    /* Transaction completed. */
                    Transaction transaction = data.getParcelableExtra(
                            CheckoutActivity.CHECKOUT_RESULT_TRANSACTION);

                    resourcePath = data.getStringExtra(
                            CheckoutActivity.CHECKOUT_RESULT_RESOURCE_PATH);

                    /* Check the transaction type. */
                    if (transaction.getTransactionType() == TransactionType.SYNC) {
                        /* Check the status of synchronous transaction. */
                        requestPaymentStatus(mCheckoutId);
                    } else {
                        /* The on onNewIntent method may be called before onActivityResult
                           if activity was destroyed in the background, so check
                           if the intent already has the callback scheme */
                        if (hasCallbackScheme(getIntent())) {
                            requestPaymentStatus(mCheckoutId);
                        } else {
                            /* The on onNewIntent method wasn't called yet,
                               wait for the callback. */
                            showProgressDialog(R.string.progress_message_please_wait);
                        }
                    }

                    break;
                case CheckoutActivity.RESULT_CANCELED:
                    hideProgressDialo();

                    break;
                case CheckoutActivity.RESULT_ERROR:
                    PaymentError error = data.getParcelableExtra(
                            CheckoutActivity.CHECKOUT_RESULT_ERROR);

                    showAlertDialog(R.string.error_message);
            }
        }
    }

    protected void requestCheckoutId(String callbackScheme) {
        showProgressDialog(R.string.progress_message_checkout_id);

        new CheckoutIdRequestAsyncTask(this)
                .execute(totalAmount, Constants.Config.CURRENCY, callbackScheme);
    }

    @Override
    public void onCheckoutIdReceived(String checkoutId) {
        hideProgressDialo();
        mCheckoutId = checkoutId;
        if (checkoutId == null) {
            showAlertDialog(R.string.error_message);
        }
    }

    @Override
    public void onErrorOccurred() {
        hideProgressDialo();
        showAlertDialog(R.string.error_message);
    }

    @Override
    public void onPaymentStatusReceived(String paymentStatus) {
        hideProgressDialo();

        /*if ("OK".equals(paymentStatus)) {
            showAlertDialog(R.string.message_successful_payment);
            return;
        }*/

        //showAlertDialog(R.string.message_unsuccessful_payment);
        showAlertDialog(paymentStatus + "");
    }

    protected void requestPaymentStatus(String resourcePath) {
        showProgressDialog(R.string.progress_message_payment_status);
        new PaymentStatusRequestAsyncTask(this).execute(resourcePath);
    }

    /**
     * Creates the new instance of {@link CheckoutSettings}
     * to instantiate the {@link CheckoutActivity}.
     *
     * @param checkoutId the received checkout id
     * @return the new instance of {@link CheckoutSettings}
     */
    protected CheckoutSettings createCheckoutSettings(String checkoutId) {
        return new CheckoutSettings(checkoutId, Constants.Config.PAYMENT_BRANDS,
                Connect.ProviderMode.TEST)
                .setSkipCVVMode(CheckoutSkipCVVMode.FOR_STORED_CARDS)
                .setWindowSecurityEnabled(false);
    }
}
