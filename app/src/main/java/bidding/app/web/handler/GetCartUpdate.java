package bidding.app.web.handler;

import bidding.app.model.CartItems;

public interface GetCartUpdate {
    void Success(CartItems cartItems);
    void onFailure(String error);
}
