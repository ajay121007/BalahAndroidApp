package bidding.app.view.activity.myauction.presenter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import javax.security.auth.callback.CallbackHandler;

import bidding.app.MyApplication;
import bidding.app.extra.CSPreferences;
import bidding.app.model.MyAuction;
import bidding.app.view.activity.FirstScreen;
import bidding.app.view.activity.myauction.MyAuctionActivity;
import bidding.app.view.activity.myauction.view.MyAuctionActivityView;
import bidding.app.web.WebServc;
import bidding.app.web.handler.CallBackHandler;
import bidding.app.web.handler.GetAllAuctionHandler;
import bidding.app.web.handler.GetMyAuctionHandler;
import bidding.app.web.handler.UpdateProfileHandler;


/**
 * Created by Thakur on 11/12/2017.
 */

public class MyAuctionActivityPresenter implements MyAuctionActivityPresenterHandler {

    MyAuctionActivityView view;

    public MyAuctionActivityPresenter(MyAuctionActivityView view) {
        this.view = view;

    }

    @Override
    public void getMyAuction(MyAuctionActivity activity, String mToken, String key, String id, String conditionType, String field, String direction, String pageSize, String currentPage) {
        view.showProgess();
        WebServc.getInstance().getVendorAuctionList(new CallBackHandler() {
            @Override
            public void Success(String response) {
                Log.d("MYAUCTION",response.toString());
                view.hideProgess();
                view.myAuctionList(response);

            }

            @Override
            public void Failuer(String error) {
                view.hideProgess();
                view.showFeedBackMessage(error.toString());
                Log.d("MYAUCTION",error.toString());
            }

        }, activity,mToken, key,id,conditionType,field,direction,pageSize,currentPage);
    }
}
