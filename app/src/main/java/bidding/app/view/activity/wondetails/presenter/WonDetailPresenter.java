package bidding.app.view.activity.wondetails.presenter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import bidding.app.view.activity.wondetails.WonDetailActivity;
import bidding.app.view.activity.wondetails.view.WonDetailView;
import bidding.app.view.fragment.purchasefragment.PurchaseFragment;
import bidding.app.web.WebServc;
import bidding.app.web.handler.InventoryHandler;
import bidding.app.web.handler.PendingPaymentHandler;


/**
 * Created by Thakur on 11/12/2017.
 */

public class WonDetailPresenter implements WonDetailPresenterHandler {

    WonDetailView view;
    TextView tvpayment,tvPay;
    String mStatus = "";

    public WonDetailPresenter(WonDetailActivity activity , TextView tvPayment, TextView tvPay) {
        this.view = activity;
        this.tvpayment = tvPayment;
        this.tvPay = tvPay;
    }

    @Override
    public void moveToInventory(Context context, String action, String userid, String auction_id, String Inventory, String Delivery) {
        view.showProgess();
        WebServc.getInstance().moveToInventory(new InventoryHandler() {
            @Override
            public void onSuccess(String Response) {
                Log.e("MOVEINVRES", Response.toString());
                view.hideProgess();
                try {
                    JSONObject jsonObject = new JSONObject(Response);
                    JSONObject response = jsonObject.getJSONObject("response");
                    view.showFeedBackMessage(response.getString("message"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String message) {
                view.hideProgess();
                Log.e("MOVEINVRES", message.toString());
            }
        }, action, userid, auction_id, Inventory, Delivery);


    }

    @Override
    public void pendingPayment(Context context, String action, String userid, String productId, String productType, String auctionId, String card_number) {
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
                        tvpayment.setText("Complete");
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
        }, action, userid, productId, productType,auctionId, card_number);
    }
}

