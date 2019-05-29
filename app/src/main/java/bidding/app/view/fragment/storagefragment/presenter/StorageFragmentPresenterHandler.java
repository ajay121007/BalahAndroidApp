package bidding.app.view.fragment.storagefragment.presenter;

import android.content.Context;

/**
 * Created by Thakur on 11/12/2017.
 */

public interface StorageFragmentPresenterHandler {
    void moveToInventory(Context context, String action, String userid, String auction_id, String Inventory, String Delivery);
}
