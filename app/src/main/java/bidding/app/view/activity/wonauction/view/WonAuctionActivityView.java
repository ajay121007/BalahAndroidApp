package bidding.app.view.activity.wonauction.view;

import bidding.app.model.MyAuction;
import bidding.app.model.WonAuction;

public interface WonAuctionActivityView {
    void showProgess();
    void hideProgess();
    void showFeedBackMessage(String message);
    void setAdapter(WonAuction wonAuction);

}