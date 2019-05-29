package bidding.app.view.fragment.checkoutfragment.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateCartRequest {

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