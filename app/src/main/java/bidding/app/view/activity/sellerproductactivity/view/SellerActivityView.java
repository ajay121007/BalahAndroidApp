package bidding.app.view.activity.sellerproductactivity.view;

import bidding.app.model.Categories;
import bidding.app.model.ProductList;
import bidding.app.model.SellerProductListBean;

public interface SellerActivityView {

    void showProgess();

    void hideProgess();

    void showFeedBackMessage(String message);

    void productListSuccess(SellerProductListBean bean);
}
