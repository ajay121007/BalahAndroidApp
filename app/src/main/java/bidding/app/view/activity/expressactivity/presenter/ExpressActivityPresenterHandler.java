package bidding.app.view.activity.expressactivity.presenter;

import android.app.Activity;

/**
 * Created by Thakur on 11/12/2017.
 */

public interface ExpressActivityPresenterHandler {

    void catagories();
    void productlist(String key, String id, String conditionType, String field, String direction, String pageSize, String currentPage);

}
