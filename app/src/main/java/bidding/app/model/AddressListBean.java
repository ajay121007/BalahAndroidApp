package bidding.app.model;

import java.util.List;

public class AddressListBean {

    /**
     * response : {"success":true,"status":1,"code":"200","message":"Record Found","data":[{"entity_id":"16","created_at":"2018-10-11 12:35:52","updated_at":"2018-10-11 12:35:52","is_active":"1","city":"Sahibzada Ajit Singh Nagar","company":null,"country_id":"IN","firstname":"Akshay","lastname":"Thakur","postcode":"160071","region":null,"region_id":0,"street":["F 372"],"telephone":"9816922193","is_default":0},{"entity_id":"17","created_at":"2018-10-11 12:37:45","updated_at":"2018-10-26 07:14:05","is_active":"1","city":"Sahibzada Ajit Singh Nagar","company":null,"country_id":"IN","firstname":"Akshay","lastname":"Thakur","postcode":"160071","region":null,"region_id":0,"street":["F 372"],"telephone":"9816922193","is_default":1},{"entity_id":"19","created_at":"2018-10-12 07:24:13","updated_at":"2018-10-12 07:24:13","is_active":"1","city":"Sahibzada Ajit Singh Nagar","company":"Appzorro","country_id":"IN","firstname":"Akshay","lastname":"Thakur","postcode":"160071","region":null,"region_id":0,"street":["F 372"],"telephone":"9816922193","is_default":0},{"entity_id":"41","created_at":"2018-10-26 06:36:14","updated_at":"2018-10-26 06:36:14","is_active":"1","city":"Sahibzada Ajit Singh Nagar","company":null,"country_id":"IN","firstname":"Akshay","lastname":"Thakur","postcode":"160071","region":null,"region_id":0,"street":["F-372, Phase 8B"],"telephone":"9816922193","is_default":0}]}
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
         * data : [{"entity_id":"16","created_at":"2018-10-11 12:35:52","updated_at":"2018-10-11 12:35:52","is_active":"1","city":"Sahibzada Ajit Singh Nagar","company":null,"country_id":"IN","firstname":"Akshay","lastname":"Thakur","postcode":"160071","region":null,"region_id":0,"street":["F 372"],"telephone":"9816922193","is_default":0},{"entity_id":"17","created_at":"2018-10-11 12:37:45","updated_at":"2018-10-26 07:14:05","is_active":"1","city":"Sahibzada Ajit Singh Nagar","company":null,"country_id":"IN","firstname":"Akshay","lastname":"Thakur","postcode":"160071","region":null,"region_id":0,"street":["F 372"],"telephone":"9816922193","is_default":1},{"entity_id":"19","created_at":"2018-10-12 07:24:13","updated_at":"2018-10-12 07:24:13","is_active":"1","city":"Sahibzada Ajit Singh Nagar","company":"Appzorro","country_id":"IN","firstname":"Akshay","lastname":"Thakur","postcode":"160071","region":null,"region_id":0,"street":["F 372"],"telephone":"9816922193","is_default":0},{"entity_id":"41","created_at":"2018-10-26 06:36:14","updated_at":"2018-10-26 06:36:14","is_active":"1","city":"Sahibzada Ajit Singh Nagar","company":null,"country_id":"IN","firstname":"Akshay","lastname":"Thakur","postcode":"160071","region":null,"region_id":0,"street":["F-372, Phase 8B"],"telephone":"9816922193","is_default":0}]
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
             * entity_id : 16
             * created_at : 2018-10-11 12:35:52
             * updated_at : 2018-10-11 12:35:52
             * is_active : 1
             * city : Sahibzada Ajit Singh Nagar
             * company : null
             * country_id : IN
             * firstname : Akshay
             * lastname : Thakur
             * postcode : 160071
             * region : null
             * region_id : 0
             * street : ["F 372"]
             * telephone : 9816922193
             * is_default : 0
             */

            private String entity_id;
            private String created_at;
            private String updated_at;
            private String is_active;
            private String city;
            private Object company;
            private String country_id;
            private String firstname;
            private String lastname;
            private String postcode;
            private Object region;
            private int region_id;
            private String telephone;
            private int is_default;
            private List<String> street;

            public String getEntity_id() {
                return entity_id;
            }

            public void setEntity_id(String entity_id) {
                this.entity_id = entity_id;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
            }

            public String getIs_active() {
                return is_active;
            }

            public void setIs_active(String is_active) {
                this.is_active = is_active;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public Object getCompany() {
                return company;
            }

            public void setCompany(Object company) {
                this.company = company;
            }

            public String getCountry_id() {
                return country_id;
            }

            public void setCountry_id(String country_id) {
                this.country_id = country_id;
            }

            public String getFirstname() {
                return firstname;
            }

            public void setFirstname(String firstname) {
                this.firstname = firstname;
            }

            public String getLastname() {
                return lastname;
            }

            public void setLastname(String lastname) {
                this.lastname = lastname;
            }

            public String getPostcode() {
                return postcode;
            }

            public void setPostcode(String postcode) {
                this.postcode = postcode;
            }

            public Object getRegion() {
                return region;
            }

            public void setRegion(Object region) {
                this.region = region;
            }

            public int getRegion_id() {
                return region_id;
            }

            public void setRegion_id(int region_id) {
                this.region_id = region_id;
            }

            public String getTelephone() {
                return telephone;
            }

            public void setTelephone(String telephone) {
                this.telephone = telephone;
            }

            public int getIs_default() {
                return is_default;
            }

            public void setIs_default(int is_default) {
                this.is_default = is_default;
            }

            public List<String> getStreet() {
                return street;
            }

            public void setStreet(List<String> street) {
                this.street = street;
            }
        }
    }
}
