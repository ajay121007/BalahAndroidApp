package bidding.app.view.fragment.checkoutfragment.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartItem {

@SerializedName("item_id")
@Expose
private Integer itemId;
@SerializedName("qty")
@Expose
private Integer qty;
@SerializedName("quote_id")
@Expose
private String quoteId;

public Integer getItemId() {
return itemId;
}

public void setItemId(Integer itemId) {
this.itemId = itemId;
}

public Integer getQty() {
return qty;
}

public void setQty(Integer qty) {
this.qty = qty;
}

public String getQuoteId() {
return quoteId;
}

public void setQuoteId(String quoteId) {
this.quoteId = quoteId;
}

}