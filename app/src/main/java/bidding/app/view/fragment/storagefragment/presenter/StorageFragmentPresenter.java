package bidding.app.view.fragment.storagefragment.presenter;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import bidding.app.view.fragment.purchasefragment.PurchaseFragment;
import bidding.app.view.fragment.storagefragment.StorageFragment;
import bidding.app.view.fragment.storagefragment.view.StorageFragmentView;
import bidding.app.web.WebServc;
import bidding.app.web.handler.InventoryHandler;


/**
 * Created by Thakur on 11/12/2017.
 */

public class StorageFragmentPresenter implements StorageFragmentPresenterHandler {

    StorageFragmentView view;

    public StorageFragmentPresenter(StorageFragment storageFragment) {
        this.view = storageFragment;
    }

    @Override
    public void moveToInventory(Context context, String action, String userid, String auction_id, String Inventory, String Delivery) {
        view.showProgess();
        WebServc.getInstance().moveToInventory(new InventoryHandler() {
            @Override
            public void onSuccess(String Response) {
                Log.e("MOVEINVRES",Response.toString());
                view.hideProgess();
                try {
                    JSONObject jsonObject =new JSONObject(Response);
                    JSONObject response= jsonObject.getJSONObject("response");
                    view.showFeedBackMessage(response.getString("message"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFail(String message) {
                view.hideProgess();
                Log.e("MOVEINVRES",message.toString());
            }
        }, action, userid, auction_id, Inventory, Delivery);


    }
}

