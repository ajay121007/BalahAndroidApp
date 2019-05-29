package bidding.app.view.activity.productdetails.presenter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import bidding.app.model.ProductBySku;
import bidding.app.model.ProductDeatils;
import bidding.app.view.activity.checkout.CheckOutActivity;
import bidding.app.view.activity.productdetails.ProductActivity;
import bidding.app.view.activity.productdetails.cartrequest.AddItemRequest;
import bidding.app.view.activity.productdetails.view.ProductActivityView;
import bidding.app.web.WebServc;
import bidding.app.web.handler.CallBackHandler;
import bidding.app.web.handler.GetProductByIdHandler;
import bidding.app.web.handler.GetProductBySkuHandler;
import bidding.app.web.handler.GetQuoteIdHandler;


/**
 * Created by Thakur on 11/12/2017.
 */

public class ProductActivityPresenter implements ProductActivityPresenterHandler {

    ProductActivityView view;
    String user_id = "", fullname = "", address = "", country = "", city = "", latitude = "", longitude = "";
    Activity activity;

    public ProductActivityPresenter(ProductActivityView view, Activity activity) {
        this.view = view;
        this.activity = activity;

    }


    @Override
    public void productDetailById(String id) {
        view.showProgess();
        WebServc.getInstance().getProductById(new GetProductByIdHandler() {
            @Override
            public void Success(String response) {
                Log.d("ProductById", response);
                view.hideProgess();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject object = jsonArray.getJSONObject(0);
                    JSONObject data = object.getJSONObject("response");
                    if (data.getInt("status") == 1) {
                        JSONArray array = data.getJSONArray("data");
                        JSONObject jsonObject = array.getJSONObject(0);
                        JSONObject quantityObject = jsonObject.getJSONObject("quantity_and_stock_status");
                        ProductDeatils deatils = new ProductDeatils();

                        deatils.setId(jsonObject.getString("id"));
                        deatils.setName(jsonObject.getString("name"));
                        deatils.setSku(jsonObject.getString("sku"));
                        deatils.setType_id(jsonObject.getString("type_id"));
                        deatils.setStatus(jsonObject.getString("status"));
                        deatils.setVisibility(jsonObject.getString("visibility"));
                        deatils.setCreated_at(jsonObject.getString("created_at"));
                        deatils.setUpdated_at(jsonObject.getString("updated_at"));
                        deatils.setPrice(jsonObject.getString("price"));
                        deatils.setDescription(jsonObject.getString("description"));
                        deatils.setShort_description(jsonObject.getString("short_description"));
                        deatils.setIsinstock(quantityObject.getBoolean("is_in_stock"));
                        deatils.setQty(quantityObject.getString("qty"));
                        deatils.setImage(jsonObject.getString("image"));

                        view.productDetails(deatils);

                    } else {
                        view.showFeedBackMessage(data.getString("message"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void Fail(String error) {
                view.hideProgess();
                view.showFeedBackMessage(error.toString());
                Log.d("ProductByIdError", error.toString());
            }
        }, id);

    }

    @Override
    public void productDetailBySku(String id) {
        view.showProgess();
        WebServc.getInstance().getProductBySku(new GetProductBySkuHandler() {
            @Override
            public void Success(ProductBySku productBySku) {
                view.hideProgess();
            }

            @Override
            public void Fail(String error) {
                view.hideProgess();
                view.showFeedBackMessage(error.toString());
                Log.d("ProductByIdError", error.toString());
            }
        }, id);
    }

    @Override
    public void quoteCreation(String token) {
        view.showProgess();
        WebServc.getInstance().getQuoteId(new GetQuoteIdHandler() {
            @Override
            public void Success(String response) {
                view.hideProgess();
                view.quoteId(response.toString());
                Log.d("QuoteId", response.toString());
            }

            @Override
            public void Fail(String error) {
                view.hideProgess();
                view.showFeedBackMessage(error.toString());
            }
        }, token);
    }

    @Override
    public void addToCart(String mToken, AddItemRequest itemRequest, final String navigationtatus) {
        view.showProgess();
        WebServc.getInstance().addToCart(new GetQuoteIdHandler() {
            @Override
            public void Success(String response) {
                Log.d("AddCart", response.toString());
                view.hideProgess();
                try {
                    JSONObject object = new JSONObject(response);
                    if (navigationtatus.equalsIgnoreCase("0"))
                        view.showFeedBackMessage(object.getString("name") + " Added Succesfully.");
                    else
                        activity.startActivity(new Intent(activity, CheckOutActivity.class));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void Fail(String error) {
                view.hideProgess();
                view.showFeedBackMessage(error.toString());
            }
        }, activity, mToken, itemRequest);

    }

    @Override
    public void addToWhislist(ProductActivity productActivity, String token, String productId) {
        view.showProgess();
        WebServc.getInstance().addToWhislist(new CallBackHandler() {
            @Override
            public void Success(String response) {
                view.hideProgess();
                Log.d("ADDWHISLIST", response.toString());
                try {
                    JSONArray array = new JSONArray(response);
                    JSONObject jsonObject = array.getJSONObject(0);
                    JSONObject responseObject = jsonObject.getJSONObject("response");
                    if (responseObject.getBoolean("success")) {
                        JSONArray data = responseObject.getJSONArray("data");
                        JSONObject object = data.getJSONObject(0);
                        view.addWishListSucces(object.getString("success"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void Failuer(String error) {
                view.hideProgess();
                view.showFeedBackMessage(error.toString());
            }
        }, productActivity, token, productId);
    }

    @Override
    public void removeFromWhislist(ProductActivity productActivity, String token, String id) {
        Log.d("Id",id+"");
        view.showProgess();
        WebServc.getInstance().removeFromWhislist(new CallBackHandler() {
            @Override
            public void Success(String response) {
                view.hideProgess();
                Log.d("REMOVEWHISLIST", response.toString());

                try {
                    JSONArray array = new JSONArray(response);
                    JSONObject jsonObject = array.getJSONObject(0);
                    JSONObject responseObject = jsonObject.getJSONObject("response");
                    if (responseObject.getBoolean("success")) {
                        JSONArray data = responseObject.getJSONArray("data");
                        JSONObject object = data.getJSONObject(0);
                        view.removeFromWishListSucces(object.getString("success"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }

            @Override
            public void Failuer(String error) {
                view.hideProgess();
                view.showFeedBackMessage(error.toString());
            }
        }, productActivity, token, id);
    }

    @Override
    public void wishListDetailByID(ProductActivity productActivity, String token, final String id) {
        view.showProgess();
        WebServc.getInstance().wishListDetailByID(new CallBackHandler() {
            @Override
            public void Success(String response) {
                view.hideProgess();
                Log.d("WishListDetailByID", response.toString());
                view.wishSucces(response);
                productDetailById(id);
            }

            @Override
            public void Failuer(String error) {
                view.hideProgess();
                view.showFeedBackMessage(error.toString());
                productDetailById(id);
            }
        }, productActivity, token, id);
    }
}

