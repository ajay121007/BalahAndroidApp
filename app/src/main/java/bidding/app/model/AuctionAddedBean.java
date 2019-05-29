package bidding.app.model;

import java.util.List;

public class AuctionAddedBean {
    /**
     * response : {"success":true,"status":1,"code":"200","message":"Records Found","data":[{"vendor_id":"24","product_id":"299","name":"Organic Food","init_price":8,"reserve_price":8,"min_bid_increment":8,"max_bid_increment":8,"start_time":"2018-10-31          10:55:19","end_time":"2018-10-31 11:55:19","min_qty":0,"max_qty":9,"winner_expiration_day":5,"multiple_winner":6,"status":"1","auction_id":"54"}]}
     */

    private ResponseBean response;

    public ResponseBean getResponse() {
        return response;
    }

    public void setResponse(ResponseBean response) {
        this.response = response;
    }

    public static class ResponseBean {
        /**
         * success : true
         * status : 1
         * code : 200
         * message : Records Found
         * data : [{"vendor_id":"24","product_id":"299","name":"Organic Food","init_price":8,"reserve_price":8,"min_bid_increment":8,"max_bid_increment":8,"start_time":"2018-10-31          10:55:19","end_time":"2018-10-31 11:55:19","min_qty":0,"max_qty":9,"winner_expiration_day":5,"multiple_winner":6,"status":"1","auction_id":"54"}]
         */

        private boolean success;
        private int status;
        private String code;
        private String message;
        private List<DataBean> data;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * vendor_id : 24
             * product_id : 299
             * name : Organic Food
             * init_price : 8
             * reserve_price : 8
             * min_bid_increment : 8
             * max_bid_increment : 8
             * start_time : 2018-10-31          10:55:19
             * end_time : 2018-10-31 11:55:19
             * min_qty : 0
             * max_qty : 9
             * winner_expiration_day : 5
             * multiple_winner : 6
             * status : 1
             * auction_id : 54
             */

            private String vendor_id;
            private String product_id;
            private String name;
            private int init_price;
            private int reserve_price;
            private int min_bid_increment;
            private int max_bid_increment;
            private String start_time;
            private String end_time;
            private int min_qty;
            private int max_qty;
            private int winner_expiration_day;
            private int multiple_winner;
            private String status;
            private String auction_id;

            public String getVendor_id() {
                return vendor_id;
            }

            public void setVendor_id(String vendor_id) {
                this.vendor_id = vendor_id;
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

            public int getWinner_expiration_day() {
                return winner_expiration_day;
            }

            public void setWinner_expiration_day(int winner_expiration_day) {
                this.winner_expiration_day = winner_expiration_day;
            }

            public int getMultiple_winner() {
                return multiple_winner;
            }

            public void setMultiple_winner(int multiple_winner) {
                this.multiple_winner = multiple_winner;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getAuction_id() {
                return auction_id;
            }

            public void setAuction_id(String auction_id) {
                this.auction_id = auction_id;
            }
        }
    }
}
