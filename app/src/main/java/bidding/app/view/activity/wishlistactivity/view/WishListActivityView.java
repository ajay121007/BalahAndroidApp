package bidding.app.view.activity.wishlistactivity.view;

/**
 * Created by Thakur on 11/12/2017.
 */

public interface WishListActivityView {

    void showProgess();

    void hideProgess();

    void showFeedBackMessage(String message);

    void wishListSuccess(String response);

    void removeFromWishListSucces(String success);

    void onSuccessfullyMoved(String success);
}
