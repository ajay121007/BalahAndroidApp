package bidding.app.view.fragment.checkoutfragment.request;

import java.util.List;

public class ShippingCostEstReq {

    /**
     * address : {"region":"Tamil Nadu","region_id":563,"region_code":"TN","country_id":"IN","street":["602 CAFOWA,Nungambakkam"],"postcode":"600094","city":"Chennai","firstname":"Muniraj","lastname":"M","customer_id":1,"email":"muniraj.m@ewallsolutions.com","telephone":"9638520741","same_as_billing":1}
     */

    private AddressBean address;

    public AddressBean getAddress() {
        return address;
    }

    public void setAddress(AddressBean address) {
        this.address = address;
    }

    public static class AddressBean {
        /**
         * region : Tamil Nadu
         * region_id : 563
         * region_code : TN
         * country_id : IN
         * street : ["602 CAFOWA,Nungambakkam"]
         * postcode : 600094
         * city : Chennai
         * firstname : Muniraj
         * lastname : M
         * customer_id : 1
         * email : muniraj.m@ewallsolutions.com
         * telephone : 9638520741
         * same_as_billing : 1
         */

        private String region;
        private int region_id;
        private String region_code;
        private String country_id;
        private String postcode;
        private String city;
        private String firstname;
        private String lastname;
        private int customer_id;
        private String email;
        private String telephone;
        private int same_as_billing;
        private List<String> street;

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public int getRegion_id() {
            return region_id;
        }

        public void setRegion_id(int region_id) {
            this.region_id = region_id;
        }

        public String getRegion_code() {
            return region_code;
        }

        public void setRegion_code(String region_code) {
            this.region_code = region_code;
        }

        public String getCountry_id() {
            return country_id;
        }

        public void setCountry_id(String country_id) {
            this.country_id = country_id;
        }

        public String getPostcode() {
            return postcode;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
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

        public int getCustomer_id() {
            return customer_id;
        }

        public void setCustomer_id(int customer_id) {
            this.customer_id = customer_id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public int getSame_as_billing() {
            return same_as_billing;
        }

        public void setSame_as_billing(int same_as_billing) {
            this.same_as_billing = same_as_billing;
        }

        public List<String> getStreet() {
            return street;
        }

        public void setStreet(List<String> street) {
            this.street = street;
        }
    }
}
