package bidding.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InventoryBean {

@SerializedName("response")
@Expose
private Response response;

public Response getResponse() {
return response;
}

public void setResponse(Response response) {
this.response = response;
}
    public class Response {

        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("data")
        @Expose
        private List<Datum> data = null;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<Datum> getData() {
            return data;
        }

        public void setData(List<Datum> data) {
            this.data = data;
        }

    }

    public class Datum {

        @SerializedName("auction_id")
        @Expose
        private String auctionId;
        @SerializedName("auctionOwner_id")
        @Expose
        private String auctionOwnerId;
        @SerializedName("product_id")
        @Expose
        private String productId;
        @SerializedName("product_name")
        @Expose
        private String productName;
        @SerializedName("product_quantity")
        @Expose
        private String productQuantity;
        @SerializedName("currency_type")
        @Expose
        private String currencyType;
        @SerializedName("unit_price")
        @Expose
        private String unitPrice;
        @SerializedName("total_price")
        @Expose
        private String totalPrice;
        @SerializedName("inventory")
        @Expose
        private String inventory;
        @SerializedName("directDelivery")
        @Expose
        private String directDelivery;

        public String getAuctionId() {
            return auctionId;
        }

        public void setAuctionId(String auctionId) {
            this.auctionId = auctionId;
        }

        public String getAuctionOwnerId() {
            return auctionOwnerId;
        }

        public void setAuctionOwnerId(String auctionOwnerId) {
            this.auctionOwnerId = auctionOwnerId;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductQuantity() {
            return productQuantity;
        }

        public void setProductQuantity(String productQuantity) {
            this.productQuantity = productQuantity;
        }

        public String getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(String unitPrice) {
            this.unitPrice = unitPrice;
        }

        public String getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getInventory() {
            return inventory;
        }

        public void setInventory(String inventory) {
            this.inventory = inventory;
        }

        public String getDirectDelivery() {
            return directDelivery;
        }

        public void setDirectDelivery(String directDelivery) {
            this.directDelivery = directDelivery;
        }

        public String getCurrencyType() {
            return currencyType;
        }

        public void setCurrencyType(String currencyType) {
            this.currencyType = currencyType;
        }
    }

}