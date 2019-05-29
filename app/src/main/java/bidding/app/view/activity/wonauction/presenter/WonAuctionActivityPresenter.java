package bidding.app.view.activity.wonauction.presenter;

import android.app.Activity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import bidding.app.model.MyAuction;
import bidding.app.model.WonAuction;
import bidding.app.view.activity.wonauction.view.WonAuctionActivityView;
import bidding.app.web.WebServc;
import bidding.app.web.handler.GetMyAuctionHandler;
import bidding.app.web.handler.GetWonAuctionHandler;


/**
 * Created by Thakur on 11/12/2017.
 */

public class WonAuctionActivityPresenter implements WonAuctionActivityPresenterHandler {

    WonAuctionActivityView view;

    public WonAuctionActivityPresenter(WonAuctionActivityView view) {
        this.view = view;

    }


    @Override
    public void getWonAuction(String token) {
        view.showProgess();
        Log.d("Token", token.toString());
        WebServc.getInstance().getWonAuction(new GetWonAuctionHandler() {
            @Override
            public void Success(String response) {
                Log.d("Won", response.toString());
                view.hideProgess();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject object = jsonArray.getJSONObject(0);
                    JSONObject jsonObject = object.getJSONObject("response");
                    if (jsonObject.getInt("status") == 0) {
                        view.showFeedBackMessage(jsonObject.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void Fail(String s) {
                view.hideProgess();
                view.showFeedBackMessage(s.toString());
            }
        }, token);
    }

}

