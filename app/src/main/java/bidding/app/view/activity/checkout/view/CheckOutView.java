package bidding.app.view.activity.checkout.view;

import java.util.List;

import bidding.app.model.CartItems;

public interface CheckOutView {

    void showProgess();
    void hideProgess();

    void showFeedBackMessage(String message);

}
