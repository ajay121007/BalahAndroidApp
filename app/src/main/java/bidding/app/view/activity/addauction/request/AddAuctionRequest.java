package bidding.app.view.activity.addauction.request;

public class AddAuctionRequest {
    /**
     * auction : {"product_id":"Vendor's Product Id","name":"Vendor's Product Name","init_price":10,"reserve_price":55,"min_bid_increment":10,"max_bid_increment":15,"start_time":"2018-09-22 17:50:17","end_time":"2018-09-30 17:55:00","status":"1","min_qty":1,"max_qty":1,"no_of_winner":1,"expiration_day":15}
     */

    private AuctionBean auction;

    public AuctionBean getAuction() {
        return auction;
    }

    public void setAuction(AuctionBean auction) {
        this.auction = auction;
    }

    public static class AuctionBean {
        /**
         * product_id : Vendor's Product Id
         * name : Vendor's Product Name
         * init_price : 10
         * reserve_price : 55
         * min_bid_increment : 10
         * max_bid_increment : 15
         * start_time : 2018-09-22 17:50:17
         * end_time : 2018-09-30 17:55:00
         * status : 1
         * min_qty : 1
         * max_qty : 1
         * no_of_winner : 1
         * expiration_day : 15
         */

        private String product_id;
        private String name;
        private int init_price;
        private int reserve_price;
        private int min_bid_increment;
        private int max_bid_increment;
        private String start_time;
        private String end_time;
        private String status;
        private int min_qty;
        private int max_qty;
        private int no_of_winner;
        private int expiration_day;

        public AuctionBean(String product_id, String name, int init_price, int reserve_price, int min_bid_increment, int max_bid_increment, String start_time, String end_time, String status, int min_qty, int max_qty, int no_of_winner, int expiration_day) {
            this.product_id = product_id;
            this.name = name;
            this.init_price = init_price;
            this.reserve_price = reserve_price;
            this.min_bid_increment = min_bid_increment;
            this.max_bid_increment = max_bid_increment;
            this.start_time = start_time;
            this.end_time = end_time;
            this.status = status;
            this.min_qty = min_qty;
            this.max_qty = max_qty;
            this.no_of_winner = no_of_winner;
            this.expiration_day = expiration_day;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getInit_price() {
            return init_price;
        }

        public void setInit_price(int init_price) {
            this.init_price = init_price;
        }

        public int getReserve_price() {
            return reserve_price;
        }

        public void setReserve_price(int reserve_price) {
            this.reserve_price = reserve_price;
        }

        public int getMin_bid_increment() {
            return min_bid_increment;
        }

        public void setMin_bid_increment(int min_bid_increment) {
            this.min_bid_increment = min_bid_increment;
        }

        public int getMax_bid_increment() {
            return max_bid_increment;
        }

        public void setMax_bid_increment(int max_bid_increment) {
            this.max_bid_increment = max_bid_increment;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getMin_qty() {
            return min_qty;
        }

        public void setMin_qty(int min_qty) {
            this.min_qty = min_qty;
        }

        public int getMax_qty() {
            return max_qty;
        }

        public void setMax_qty(int max_qty) {
            this.max_qty = max_qty;
        }

        public int getNo_of_winner() {
            return no_of_winner;
        }

        public void setNo_of_winner(int no_of_winner) {
            this.no_of_winner = no_of_winner;
        }

        public int getExpiration_day() {
            return expiration_day;
        }

        public void setExpiration_day(int expiration_day) {
            this.expiration_day = expiration_day;
        }
    }
}
