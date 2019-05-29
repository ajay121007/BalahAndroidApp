package bidding.app.view.fragment.historyfragment.presenter;

import android.content.Context;
import android.text.Html;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import bidding.app.model.AuctionBean;
import bidding.app.extra.Constants;
import bidding.app.extra.Event;
import bidding.app.model.InventoryBean;
import bidding.app.view.fragment.historyfragment.History_Fragment;
import bidding.app.view.fragment.historyfragment.view.HistoryFragmentView;
import bidding.app.web.WebServc;
import bidding.app.web.handler.GetAllAuctionHandler;
import bidding.app.web.handler.GetAuction;
import bidding.app.web.handler.GetInventoryHandler;
import bidding.app.web.handler.InventoryHandler;


/**
 * Created by Thakur on 11/12/2017.
 */

public class HistoryFragmentPresenter implements HistoryFragmentPresenterHandler {

    HistoryFragmentView view;

    public HistoryFragmentPresenter(History_Fragment history_fragment) {
        this.view=history_fragment;
    }

    @Override
    public void getInventory(Context context, String action, String userid) {
        view.showProgess();
        WebServc.getInstance().getInventory(new GetInventoryHandler() {
            @Override
            public void onSuccess(InventoryBean Response) {
                Log.d("GetInventory",Response+"");
                view.hideProgess();
                view.inventorySuccess(Response.getResponse().getData());

            }

            @Override
            public void onFail(String message) {
                view.hideProgess();
                Log.d("GetInventory",message+"");
            }
        },action,userid);


    }
}

