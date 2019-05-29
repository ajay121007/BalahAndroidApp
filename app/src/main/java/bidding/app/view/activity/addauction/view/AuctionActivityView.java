package bidding.app.view.activity.addauction.view;

/**
 * Created by Thakur on 11/12/2017.
 */

public interface AuctionActivityView {

    void showProgess();

    void hideProgess();

    void showFeedBackMessage(String message);

    void onSuccessfulyAuctionAdded(String response);
}
