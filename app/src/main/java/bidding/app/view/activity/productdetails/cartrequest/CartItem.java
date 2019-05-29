package bidding.app.view.activity.productdetails.cartrequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartItem {


    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("qty")
    @Expose
    private Integer qty;
    @SerializedName("quote_id")
    @Expose
    private String quoteId;


    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
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