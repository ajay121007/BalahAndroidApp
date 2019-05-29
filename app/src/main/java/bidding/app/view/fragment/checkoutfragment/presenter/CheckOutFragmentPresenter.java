package bidding.app.view.fragment.checkoutfragment.presenter;


import android.app.Activity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import bidding.app.model.CartItems;
import bidding.app.view.fragment.checkoutfragment.request.ShippingCostEstReq;
import bidding.app.view.fragment.checkoutfragment.request.UpdateCartRequest;
import bidding.app.view.fragment.checkoutfragment.view.CheckOutView;
import bidding.app.web.WebServc;
import bidding.app.web.handler.CallBackHandler;
import bidding.app.web.handler.GetCartHandler;
import bidding.app.web.handler.GetCartUpdate;

public class CheckOutFragmentPresenter implements CheckOutFragmentPresenterHandler {

    CheckOutView view;
    CartItems cartItem;
    List<CartItems> itemsList;
    Activity activity;
    double amount = 0;

    public CheckOutFragmentPresenter(CheckOutView view, Activity activity, List<CartItems> itemsList) {
        this.view = view;
        this.itemsList = itemsList;
        this.activity = activity;
    }

    @Override
    public void getCartList(String token) {
        view.showProgess();
        WebServc.getInstance().getCartList(new GetCartHandler() {
            @Override
            public void Success(String response) {
                Log.d("CartList", response.toString());
                view.hideProgess();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if (jsonArray.length() != 0 && jsonArray != null) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            {
                                JSONObject object = jsonArray.getJSONObject(i);
                                CartItems items = new CartItems();
                                CartItems.ExtensionAttributesBean attributesBean = new CartItems.ExtensionAttributesBean();

                                items.setItem_id(object.getInt("item_id"));
                                items.setSku(object.getString("sku"));
                                items.setQty(object.getInt("qty"));
                                items.setName(object.getString("name"));
                                items.setPrice(object.getInt("price"));
                                items.setProduct_type(object.getString("product_type"));
                                items.setQuote_id(object.getString("quote_id"));
                                JSONObject jsonObject = object.getJSONObject("extension_attributes");
                                attributesBean.setImage_url(jsonObject.getString("image_url"));

                                if (jsonObject.has("short_description")) {
                                    attributesBean.setShort_description(jsonObject.getString("short_description"));
                                }

                                items.setExtension_attributes(attributesBean);
                                itemsList.add(items);
                            }
                        }
                        view.cartList(itemsList);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void Fail(String error) {
                view.hideProgess();
                view.showFeedBackMessage(error.toString());
            }
        }, activity, token);

    }

    @Override
    public void deleteItem(Activity activity, String token, int id, final int position) {
        view.showProgess();
        WebServc.getInstance().deleteCartItem(new CallBackHandler() {
            @Override
            public void Success(String response) {
                Log.d("DeleteItem", response.toString());
                view.hideProgess();
                view.onSuccessfullyItemDel(response, position);

            }

            @Override
            public void Failuer(String error) {
                view.hideProgess();
                view.showFeedBackMessage(error.toString());

            }
        }, activity, token, id);
    }

    @Override
    public void updateCart(Activity activity, final int postion, String token, UpdateCartRequest request) {
        view.showProgess();
        WebServc.getInstance().updateCartItem(new GetCartUpdate() {
            @Override
            public void Success(CartItems cartItems) {

                view.hideProgess();
                view.updateCartSuccess(postion, cartItems);

            }

            @Override
            public void onFailure(String error) {
                view.hideProgess();
                view.showFeedBackMessage(error.toString());

            }
        }, activity, token, request);
    }

    @Override
    public void getBillingAddres(Activity activity, final String token) {
        view.showProgess();
        WebServc.getInstance().billingAddress(new CallBackHandler() {
            @Override
            public void Success(String response) {
                view.hideProgess();
                view.setDefaultAddress(response);

            }

            @Override
            public void Failuer(String error) {
                view.hideProgess();
                view.setDefaultAddress("");

            }
        }, activity, token);
    }

    @Override
    public void shippingCostEstimation(Activity activity, String token, ShippingCostEstReq costEstReq) {
        view.showProgess();
        WebServc.getInstance().getShippingCost(new CallBackHandler() {
            @Override
            public void Success(String response) {
                view.hideProgess();
                Log.d("Shipping_Cost", response + "");

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    amount = jsonObject.getInt("amount");
                    view.setShippingCharge(amount);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void Failuer(String error) {
                view.hideProgess();
            }
        }, activity, token, costEstReq);
    }
}
