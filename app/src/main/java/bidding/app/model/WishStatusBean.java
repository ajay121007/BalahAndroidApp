package bidding.app.model;

import java.util.List;

public class WishStatusBean {

    /**
     * response : {"success":true,"status":1,"code":"200","message":"Record Found","data":[{"id":"128","name":"عجوة المدينة","sku":"WH09-XS-Green","is_in_wishlist":0}]}
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
         * message : Record Found
         * data : [{"id":"128","name":"عجوة المدينة","sku":"WH09-XS-Green","is_in_wishlist":0}]
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
             * id : 128
             * name : عجوة المدينة
             * sku : WH09-XS-Green
             * is_in_wishlist : 0
             */

            private String id;
            private String name;
            private String sku;
            private int is_in_wishlist;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getSku() {
                return sku;
            }

            public void setSku(String sku) {
                this.sku = sku;
            }

            public int getIs_in_wishlist() {
                return is_in_wishlist;
            }

            public void setIs_in_wishlist(int is_in_wishlist) {
                this.is_in_wishlist = is_in_wishlist;
            }
        }
    }
}
