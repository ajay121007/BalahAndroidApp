package bidding.app.view.fragment.checkoutfragment.view;

import java.util.List;

import bidding.app.model.CartItems;

public interface CheckOutView {

    void showProgess();

    void hideProgess();

    void showFeedBackMessage(String message);

    void cartList(List<CartItems> cartItem);

    void updateCartSuccess(int postion, CartItems response);

    void setDefaultAddress(String response);

    void setShippingCharge(double amount);


    void onSuccessfullyItemDel(String response, int position);
}
