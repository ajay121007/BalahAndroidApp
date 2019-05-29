package bidding.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuctionOwnerInfoBean {

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
        private Data data;

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

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

    }
    public class Data {

        @SerializedName("quantity")
        @Expose
        private String quantity;
        @SerializedName("auctionOwner_id")
        @Expose
        private String auctionOwnerId;
        @SerializedName("fullname")
        @Expose
        private String fullname;
        @SerializedName("phone_no")
        @Expose
        private String phoneNo;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("latitude")
        @Expose
        private String latitude;
        @SerializedName("longitude")
        @Expose
        private String longitude;

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getAuctionOwnerId() {
            return auctionOwnerId;
        }

        public void setAuctionOwnerId(String auctionOwnerId) {
            this.auctionOwnerId = auctionOwnerId;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public String getPhoneNo() {
            return phoneNo;
        }

        public void setPhoneNo(String phoneNo) {
            this.phoneNo = phoneNo;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

    }
}