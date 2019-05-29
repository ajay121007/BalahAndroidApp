package bidding.app.view.fragment.historyfragment.view;

import java.util.ArrayList;
import java.util.List;

import bidding.app.model.AuctionBean;
import bidding.app.model.InventoryBean;

/**
 * Created by Thakur on 11/12/2017.
 */

public interface HistoryFragmentView {
    void showProgess();
    void hideProgess();
    void showFeedBackMessage(String message);
    void inventorySuccess(List<InventoryBean.Datum> list);
}
