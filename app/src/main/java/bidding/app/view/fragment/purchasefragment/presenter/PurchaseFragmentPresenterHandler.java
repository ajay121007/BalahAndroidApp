package bidding.app.view.fragment.purchasefragment.presenter;

import android.content.Context;

/**
 * Created by Thakur on 11/12/2017.
 */

public interface PurchaseFragmentPresenterHandler {
    void moveToInventory(Context context, String action, String userid, String auction_id, String Inventory, String Delivery);
    void pendingPayment(Context context, String action, String userid, String productId, String productType, String auctionId, String card_number);
}
