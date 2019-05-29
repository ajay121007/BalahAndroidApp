package bidding.app.view.fragment.homepage.view;

import java.util.ArrayList;

import bidding.app.model.AuctionBean;

/**
 * Created by Thakur on 11/12/2017.
 */

public interface HomeFragmentView {
    void showProgess();
    void hideProgess();
    void showFeedBackMessage(String message);
    void allAuctionSuccess(ArrayList<AuctionBean> list);
    void bidSuccess(String message);
    void auctionById(ArrayList<AuctionBean> list);
}
