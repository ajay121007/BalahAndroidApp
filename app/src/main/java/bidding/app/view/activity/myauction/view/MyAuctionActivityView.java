package bidding.app.view.activity.myauction.view;

import bidding.app.model.MyAuction;

public interface MyAuctionActivityView {

    void showProgess();

    void hideProgess();

    void showFeedBackMessage(String message);

    void setAdapter(MyAuction myAuction );

    void myAuctionList(String response);
}