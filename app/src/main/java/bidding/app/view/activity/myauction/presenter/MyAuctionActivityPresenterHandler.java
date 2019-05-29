package bidding.app.view.activity.myauction.presenter;

import android.app.Activity;

import bidding.app.view.activity.myauction.MyAuctionActivity;

/**
 * Created by Thakur on 11/12/2017.
 */

public interface MyAuctionActivityPresenterHandler {

    void getMyAuction(MyAuctionActivity myAuctionActivity, String mToken, String key, String id, String conditionType, String field, String direction, String pageSize, String currentPage);

}
