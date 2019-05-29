package bidding.app.view.activity.wishlistactivity.presenter;

import bidding.app.view.activity.changeaddress.ChangeAddressActivity;
import bidding.app.view.activity.wishlistactivity.WishListActivity;

/**
 * Created by Thakur on 11/12/2017.
 */

public interface WishListActivityPresenterHandler {

    void getWishlist(WishListActivity wishListActivity, String mToken);

    void removeFromWishListUsingId(WishListActivity wishListActivity, String wishListItemId ,String mToken);

    void moveWishListToCart(WishListActivity wishListActivity, String wishListItemId ,String mToken);
}
