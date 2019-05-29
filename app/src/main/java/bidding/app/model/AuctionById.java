package bidding.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AuctionById {

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

        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("data")
        @Expose
        private List<Datum> data = null;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public List<Datum> getData() {
            return data;
        }

        public void setData(List<Datum> data) {
            this.data = data;
        }

    }


    public class AuctionDetail {

        @SerializedName("highest_bid")
        @Expose
        private String highestBid;
        @SerializedName("quantity")
        @Expose
        private String quantity;
        @SerializedName("image")
        @Expose
        private String image;

        public String getHighestBid() {
            return highestBid;
        }

        public void setHighestBid(String highestBid) {
            this.highestBid = highestBid;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

    }

    public class Datum {

        @SerializedName("auction_id")
        @Expose
        private String auctionId;
        @SerializedName("auction_title")
        @Expose
        private String auctionTitle;
        @SerializedName("station")
        @Expose
        private String station;
        @SerializedName("start_date")
        @Expose
        private String startDate;
        @SerializedName("start_time")
        @Expose
        private String startTime;
        @SerializedName("end_date")
        @Expose
        private String endDate;
        @SerializedName("end_time")
        @Expose
        private String endTime;
        @SerializedName("reserve_price")
        @Expose
        private String reservePrice;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("auction_status")
        @Expose
        private Integer auctionStatus;
        @SerializedName("auction_detail")
        @Expose
        private AuctionDetail auctionDetail;

        public String getAuctionId() {
            return auctionId;
        }

        public void setAuctionId(String auctionId) {
            this.auctionId = auctionId;
        }

        public String getAuctionTitle() {
            return auctionTitle;
        }

        public void setAuctionTitle(String auctionTitle) {
            this.auctionTitle = auctionTitle;
        }

        public String getStation() {
            return station;
        }

        public void setStation(String station) {
            this.station = station;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getReservePrice() {
            return reservePrice;
        }

        public void setReservePrice(String reservePrice) {
            this.reservePrice = reservePrice;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Integer getAuctionStatus() {
            return auctionStatus;
        }

        public void setAuctionStatus(Integer auctionStatus) {
            this.auctionStatus = auctionStatus;
        }

        public AuctionDetail getAuctionDetail() {
            return auctionDetail;
        }

        public void setAuctionDetail(AuctionDetail auctionDetail) {
            this.auctionDetail = auctionDetail;
        }

    }


}