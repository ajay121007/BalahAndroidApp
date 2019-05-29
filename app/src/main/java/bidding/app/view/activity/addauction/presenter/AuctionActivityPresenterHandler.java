package bidding.app.view.activity.addauction.presenter;

import android.app.Activity;

import bidding.app.view.activity.addauction.AddAuctionActivity;
import bidding.app.view.activity.addauction.request.AddAuctionRequest;

/**
 * Created by Thakur on 11/12/2017.
 */

public interface AuctionActivityPresenterHandler {


    void addAuction(AddAuctionActivity activity, String mToken, AddAuctionRequest request);
}
