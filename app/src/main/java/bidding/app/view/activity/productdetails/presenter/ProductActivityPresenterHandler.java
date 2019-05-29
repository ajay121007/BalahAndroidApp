package bidding.app.view.activity.productdetails.presenter;

import bidding.app.view.activity.productdetails.ProductActivity;
import bidding.app.view.activity.productdetails.cartrequest.AddItemRequest;

/**
 * Created by Thakur on 11/12/2017.
 */

public interface ProductActivityPresenterHandler {

    void productDetailById(String id);

    void productDetailBySku(String id);

    void quoteCreation(String token);

    void addToCart(String mToken, AddItemRequest itemRequest, String navigationtatus);

    void addToWhislist(ProductActivity productActivity, String token, String productId);

    void removeFromWhislist(ProductActivity productActivity, String token, String id);

    void wishListDetailByID(ProductActivity productActivity, String token, String id);
}
