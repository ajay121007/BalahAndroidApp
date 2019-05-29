package bidding.app.view.activity.wishlistactivity.presenter;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import bidding.app.view.activity.changeaddress.ChangeAddressActivity;
import bidding.app.view.activity.wishlistactivity.WishListActivity;
import bidding.app.view.activity.wishlistactivity.view.WishListActivityView;
import bidding.app.web.WebServc;
import bidding.app.web.handler.CallBackHandler;


/**
 * Created by Thakur on 11/12/2017.
 */

public class WishListActivityPresenter implements WishListActivityPresenterHandler {

    WishListActivityView view;

    public WishListActivityPresenter(WishListActivityView view) {
        this.view = view;

    }


    @Override
    public void getWishlist(WishListActivity activity, String mToken) {
        view.showProgess();
        WebServc.getInstance().getWishList(new CallBackHandler() {
            @Override
            public void Success(String response) {
                Log.d("WISHLIST", response.toString());
                view.hideProgess();
                view.wishListSuccess(response);
            }

            @Override
            public void Failuer(String error) {
                view.hideProgess();
                view.showFeedBackMessage(error.toString());
            }
        }, activity, mToken);
    }

    @Override
    public void removeFromWishListUsingId(WishListActivity activity, String wishListItemId, String mToken) {
        Log.d("ITEMID",wishListItemId+"");
        view.showProgess();
        WebServc.getInstance().removeFromWhislistUsingId(new CallBackHandler() {
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
        }, activity, mToken,wishListItemId);
    }

    @Override
    public void moveWishListToCart(WishListActivity activity, String wishListItemId, String mToken) {
        view.showProgess();
        WebServc.getInstance().moveWishListToCart(new CallBackHandler() {
            @Override
            public void Success(String response) {
                view.hideProgess();
                Log.d("MOVEWISHLISTTOCART", response.toString());
                try {
                    JSONArray array = new JSONArray(response);
                    JSONObject jsonObject = array.getJSONObject(0);
                    JSONObject responseObject = jsonObject.getJSONObject("response");
                    if (responseObject.getBoolean("success")) {
                        JSONArray data = responseObject.getJSONArray("data");
                        JSONObject object = data.getJSONObject(0);
                        view.onSuccessfullyMoved(object.getString("success"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void Failuer(String error) {
                Log.e("Error",error.toString());
                view.hideProgess();
                view.showFeedBackMessage(error.toString());
            }
        }, activity, mToken, wishListItemId);
    }
}

