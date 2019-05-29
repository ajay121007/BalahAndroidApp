package bidding.app.view.activity.sellerproductactivity.presenter;

import bidding.app.view.activity.sellerproductactivity.SellerProductActivity;

/**
 * Created by Thakur on 11/12/2017.
 */

public interface SellerActivityPresenterHandler {

    void getProductlist(SellerProductActivity activity, String mToken, String key, String id, String conditionType, String field, String direction, String pageSize, String currentPage);

}
