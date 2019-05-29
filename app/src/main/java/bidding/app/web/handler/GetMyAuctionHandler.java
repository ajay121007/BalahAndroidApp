package bidding.app.web.handler;

import bidding.app.model.MyAuction;

public interface GetMyAuctionHandler {
    void Success(MyAuction myAuction);
    void Fail(String error);
}
