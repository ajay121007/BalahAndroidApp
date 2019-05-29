package bidding.app.view.activity.productdetails.view;

import bidding.app.model.ProductDeatils;

/**
 * Created by Thakur on 11/12/2017.
 */

public interface ProductActivityView {

    void showProgess();

    void hideProgess();

    void showFeedBackMessage(String message);

    void productDetails(ProductDeatils deatils);

    void quoteId(String id);

    void wishSucces(String response);

    void removeFromWishListSucces(String response);

    void addWishListSucces(String success);
}
