package bidding.app.view.fragment.homepage.presenter;

import android.content.Context;

/**
 * Created by Thakur on 11/12/2017.
 */

public interface HomeFragmentPresenterHandler {
    void getAllAuction(Context context, String action, String userid, String auctionid);
    void submitBid(Context context, String action, String userid, String auction_id, String str, String timee);
    void getAuctionByID(Context context, String getall_auctions, String s, String auction_id);
}
