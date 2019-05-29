package bidding.app.view.fragment.historyfragment.presenter;

import android.content.Context;

/**
 * Created by Thakur on 11/12/2017.
 */

public interface HistoryFragmentPresenterHandler {
    void getInventory(Context context, String action, String userid);
}
