package bidding.app.web.handler;

public interface GetAllAuctionHandler {
    void onSuccess(String Response);
    void onFail(String message);
}
