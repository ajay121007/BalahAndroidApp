package bidding.app.view.activity.addauction.presenter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import bidding.app.view.activity.addauction.AddAuctionActivity;
import bidding.app.view.activity.addauction.request.AddAuctionRequest;
import bidding.app.view.activity.addauction.view.AuctionActivityView;
import bidding.app.view.activity.mainactivity.MainActivity;
import bidding.app.web.WebServc;
import bidding.app.web.handler.AddAuctionHandler;
import bidding.app.web.handler.CallBackHandler;


/**
 * Created by Thakur on 11/12/2017.
 */

public class AuctionActivityPresenter implements AuctionActivityPresenterHandler {

    AuctionActivityView view;

    public AuctionActivityPresenter(AuctionActivityView view) {
        this.view = view;

    }

    @Override
    public void addAuction(AddAuctionActivity activity, String mToken, AddAuctionRequest request) {
        view.showProgess();
        WebServc.getInstance().addAuction(new CallBackHandler() {
            @Override
            public void Success(String response) {
                view.hideProgess();
                Log.d("ADDAUCTION",response.toString());
                view.onSuccessfulyAuctionAdded(response);
              }

            @Override
            public void Failuer(String error) {
                view.hideProgess();
                Log.d("ADDAUCTIONERROR",error.toString());
            }
        }, activity, mToken, request);
    }


}

