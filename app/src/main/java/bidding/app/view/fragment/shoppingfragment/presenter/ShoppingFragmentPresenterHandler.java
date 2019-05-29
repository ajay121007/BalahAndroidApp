package bidding.app.view.fragment.shoppingfragment.presenter;

import android.content.Context;

/**
 * Created by Thakur on 11/12/2017.
 */

public interface ShoppingFragmentPresenterHandler {
    void pendingPayment(Context context, String action, String userid, String productId, String productType, String auction_id, String card_number);
}
