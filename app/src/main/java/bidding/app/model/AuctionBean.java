package bidding.app.model;

/**
 * Created by vijay on 12/8/17.
 */

public class AuctionBean {

    String auction_id, auction_status, reserve_price, status, highest_bid, quantity, image, enddate, endtime, station, mAllImage, minBid, maxBid,mStartTime,mStartDate,extendtime;

    public AuctionBean(String auction_id, String auction_status, String reserve_price, String status,
                       String highest_bid, String quantity, String image, String enddate, String endtime, String station, String mAllImage, String minBid, String maxBid, String startTime, String startdate,String extendtime) {

        this.auction_id = auction_id;
        this.auction_status = auction_status;
        this.reserve_price = reserve_price;
        this.status = status;
        this.highest_bid = highest_bid;
        this.quantity = quantity;
        this.image = image;
        this.enddate = enddate;
        this.endtime = endtime;
        this.station = station;
        this.mAllImage = mAllImage;
        this.minBid = minBid;
        this.maxBid = maxBid;
        this.mStartDate=startdate;
        this.mStartTime=startTime;
        this.extendtime=extendtime;
    }

    public String getAuction_id() {
        return auction_id;
    }

    public String getAuction_status() {
        return auction_status;
    }

    public String getReserve_price() {
        return reserve_price;
    }

    public String getStatus() {
        return status;
    }

    public String getHighest_bid() {
        return highest_bid;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getImage() {
        return image;
    }


    public String getEndtime() {
        return endtime;
    }

    public String getEnddate() {
        return enddate;
    }

    public String getStation() {
        return station;
    }

    public String getmAllImage() {
        return mAllImage;
    }

    public String getMinBid() {
        return minBid;
    }

    public String getMaxBid() {
        return maxBid;
    }

    public String getmStartTime() {
        return mStartTime;
    }

    public String getmStartDate() {
        return mStartDate;
    }

    public String getExtendtime() {
        return extendtime;
    }
}
