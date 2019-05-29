package bidding.app.view.fragment.shoppingfragment.presenter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import bidding.app.view.fragment.shoppingfragment.ShoppingFragment;
import bidding.app.view.fragment.shoppingfragment.view.ShoppingFragmentView;
import bidding.app.web.WebServc;
import bidding.app.web.handler.PendingPaymentHandler;


/**
 * Created by Thakur on 11/12/2017.
 */

public class ShoppingFragmentPresenter implements ShoppingFragmentPresenterHandler {

    ShoppingFragmentView view;
    TextView tvpaymnet, tvPay;
    String mStatus = "";

    public ShoppingFragmentPresenter(ShoppingFragment shoppingFragment, TextView tvPayment, TextView tvPay) {
        this.view = shoppingFragment;
        this.tvpaymnet = tvPayment;
        this.tvPay = tvPay;
    }


    @Override
    public void pendingPayment(Context context, String action, String userid, String productId, String productType, String auction_id, String card_number) {
        view.showProgess();
        WebServc.getInstance().pendingPayment(new PendingPaymentHandler() {
            @Override
            public void onSuccess(String Response) {
                Log.e("PAYMENTSUCCESS", Response.toString());
                view.hideProgess();
                try {
                    JSONObject jsonObject = new JSONObject(Response);
                    JSONObject response = jsonObject.getJSONObject("response");
                    mStatus = response.getString("status");
                    if (mStatus.equalsIgnoreCase("1")) {
                        tvpaymnet.setText("Complete");
                        tvPay.setVisibility(View.GONE);
                        view.showFeedBackMessage(response.getString("message"));
                    } else if (mStatus.equalsIgnoreCase("0")) {
                        view.showFeedBackMessage(response.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String message) {
                view.hideProgess();
                Log.e("PAYMENTFAILER", message.toString());
            }
        }, action, userid, productId, productType,auction_id,card_number);

    }
}

