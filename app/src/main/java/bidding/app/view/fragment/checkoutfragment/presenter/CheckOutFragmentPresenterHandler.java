package bidding.app.view.fragment.checkoutfragment.presenter;

import android.app.Activity;

import bidding.app.view.fragment.checkoutfragment.request.ShippingCostEstReq;
import bidding.app.view.fragment.checkoutfragment.request.UpdateCartRequest;

public interface CheckOutFragmentPresenterHandler {

    void getCartList(String token);

    void deleteItem(Activity activity, String token, int id, int position);

    void updateCart(Activity activity, int postion, String token, UpdateCartRequest request);

    void getBillingAddres(Activity activity, String token);

    void shippingCostEstimation(Activity activity, String token, ShippingCostEstReq costEstReq);
}
