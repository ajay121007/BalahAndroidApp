package bidding.app.web.handler;

import bidding.app.model.MyAuction;
import bidding.app.model.WonAuction;

public interface GetWonAuctionHandler {

    void Success(String response);
    void Fail(String error);
}
