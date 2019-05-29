package bidding.app.model;

import java.util.List;

public class SetDefaultAddressBean {
    /**
     * response : {"success":true,"status":1,"code":"200","message":"Record Found","data":[{"success":"Address had been saved successfully!!!","address_id":17}]}
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
         * data : [{"success":"Address had been saved successfully!!!","address_id":17}]
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
             * success : Address had been saved successfully!!!
             * address_id : 17
             */

            private String success;
            private int address_id;

            public String getSuccess() {
                return success;
            }

            public void setSuccess(String success) {
                this.success = success;
            }

            public int getAddress_id() {
                return address_id;
            }

            public void setAddress_id(int address_id) {
                this.address_id = address_id;
            }
        }
    }
}
