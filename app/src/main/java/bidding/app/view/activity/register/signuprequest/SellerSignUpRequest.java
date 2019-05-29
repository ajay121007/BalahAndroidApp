package bidding.app.view.activity.register.signuprequest;

public class SellerSignUpRequest {
    /**
     * sellerData : {"firstname":"test","lastname":"seller","email":"seller@seller.com","phone_number":"1111111111","vendor_id":"seller_1","city":"chennai","postcode":"","street":"","country_code":"US","state":"12","company":""}
     */

    private SellerDataBean sellerData;

    public SellerDataBean getSellerData() {
        return sellerData;
    }

    public void setSellerData(SellerDataBean sellerData) {
        this.sellerData = sellerData;
    }

    public static class SellerDataBean {
        /**
         * firstname : test
         * lastname : seller
         * email : seller@seller.com
         * phone_number : 1111111111
         * vendor_id : seller_1
         * city : chennai
         * postcode :
         * street :
         * country_code : US
         * state : 12
         * company :
         */

        private String firstname;
        private String lastname;
        private String email;
        private String phone_number;
        private String vendor_id;
        private String city;
        private String postcode;
        private String street;
        private String country_code;
        private String state;
        private String company;

        public SellerDataBean(String firstname, String lastname, String email, String phone_number, String vendor_id, String city, String postcode, String street, String country_code, String state, String company) {
            this.firstname = firstname;
            this.lastname = lastname;
            this.email = email;
            this.phone_number = phone_number;
            this.vendor_id = vendor_id;
            this.city = city;
            this.postcode = postcode;
            this.street = street;
            this.country_code = country_code;
            this.state = state;
            this.company = company;
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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone_number() {
            return phone_number;
        }

        public void setPhone_number(String phone_number) {
            this.phone_number = phone_number;
        }

        public String getVendor_id() {
            return vendor_id;
        }

        public void setVendor_id(String vendor_id) {
            this.vendor_id = vendor_id;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getPostcode() {
            return postcode;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getCountry_code() {
            return country_code;
        }

        public void setCountry_code(String country_code) {
            this.country_code = country_code;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }
    }
}
