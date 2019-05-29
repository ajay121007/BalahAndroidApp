package bidding.app.model;

/**
 * Created by rishav on 12/8/17.
 */

public class HistoryBean {
    private String Status;
    private String TotalPrice;
    private String UnitPrice;
    private String Quantity;
    private String Product;
    private String AuctionID;
    private String auctionOwnerId;
    private String paymentStatus;
    private String productId;

    public HistoryBean(String status, String totalPrice, String unitPrice, String quantity, String product, String auctionID, String auctionOwnerId, String paymentStatus,String productId) {
        this.Status = status;
        this.TotalPrice = totalPrice;
        this.UnitPrice = unitPrice;
        this.Quantity = quantity;
        this.Product = product;
        this.AuctionID = auctionID;
        this.auctionOwnerId = auctionOwnerId;
        this.paymentStatus = paymentStatus;
        this.productId=productId;
    }


    public String getAuctionOwnerId() {
        return auctionOwnerId;
    }

    public void setAuctionOwnerId(String auctionOwnerId) {
        this.auctionOwnerId = auctionOwnerId;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
    }

    public String getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        UnitPrice = unitPrice;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getProduct() {
        return Product;
    }

    public void setProduct(String product) {
        Product = product;
    }

    public String getAuctionID() {
        return AuctionID;
    }

    public void setAuctionID(String auctionID) {
        AuctionID = auctionID;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getProductId() {
        return productId;
    }
}
