package bidding.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyAuction {

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
        @SerializedName("auction_detail")
        @Expose
        private List<AuctionDetail> auctionDetail = null;

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

        public List<AuctionDetail> getAuctionDetail() {
            return auctionDetail;
        }

        public void setAuctionDetail(List<AuctionDetail> auctionDetail) {
            this.auctionDetail = auctionDetail;
        }

    }
    public class AuctionDetail {

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
        @SerializedName("highest_bid")
        @Expose
        private String highestBid;
        @SerializedName("winner")
        @Expose
        private String winner;
        @SerializedName("quantity")
        @Expose
        private String quantity;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("auction_status")
        @Expose
        private String auctionStatus;

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

        public String getHighestBid() {
            return highestBid;
        }

        public void setHighestBid(String highestBid) {
            this.highestBid = highestBid;
        }

        public String getWinner() {
            return winner;
        }

        public void setWinner(String winner) {
            this.winner = winner;
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

        public String getAuctionStatus() {
            return auctionStatus;
        }

        public void setAuctionStatus(String auctionStatus) {
            this.auctionStatus = auctionStatus;
        }

    }

}