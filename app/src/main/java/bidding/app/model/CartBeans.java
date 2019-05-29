package bidding.app.model;

/**
 * Created by vijay on 21/8/17.
 */

public class CartBeans {

    private String Status;
    private String TotalPrice;
    private String UnitPrice;
    private String Quantity;
    private String Product;
    private String AuctionID;
    private String image;
    private String currency_type;

    public CartBeans(String status, String totalPrice, String unitPrice, String quantity, String product, String auctionID,String image,String currency_type) {
       this.Status = status;
        this.TotalPrice = totalPrice;
        this.UnitPrice = unitPrice;
        this.Quantity = quantity;
        this.Product = product;
        this.AuctionID = auctionID;
        this.image = image;
        this.currency_type=currency_type;

    }


    public String getStatus() {
        return Status;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public String getUnitPrice() {
        return UnitPrice;
    }

    public String getQuantity() {
        return Quantity;
    }

    public String getProduct() {
        return Product;
    }

    public String getAuctionID() {
        return AuctionID;
    }

    public String getImage() {
        return image;
    }

    public String getCurrency_type() {
        return currency_type;
    }
}
