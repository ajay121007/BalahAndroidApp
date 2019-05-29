package bidding.app.view.activity.productdetails.cartrequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddItemRequest {

    @SerializedName("cartItem")
    @Expose
    private CartItem cartItem;

    public CartItem getCartItem() {
        return cartItem;
    }

    public void setCartItem(CartItem cartItem) {
        this.cartItem = cartItem;
    }

}