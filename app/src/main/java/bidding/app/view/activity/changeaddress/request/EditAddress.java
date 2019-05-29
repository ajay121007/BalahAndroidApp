package bidding.app.view.activity.changeaddress.request;

public class EditAddress {
    /**
     * customeraddress : {"addressid":"<customer_address_id>","countryid":"IN","street":"602 Manju Block","postcode":"600094","city":"Chennai","phoneno":"9638520741","firstname":"Muniraj","lastname":"M","company":"Ewall","default_billing":0,"default_shipping":0}
     */

    private CustomeraddressBean customeraddress;

    public CustomeraddressBean getCustomeraddress() {
        return customeraddress;
    }

    public void setCustomeraddress(CustomeraddressBean customeraddress) {
        this.customeraddress = customeraddress;
    }

    public static class CustomeraddressBean {
        /**
         * addressid : <customer_address_id>
         * countryid : IN
         * street : 602 Manju Block
         * postcode : 600094
         * city : Chennai
         * phoneno : 9638520741
         * firstname : Muniraj
         * lastname : M
         * company : Ewall
         * default_billing : 0
         * default_shipping : 0
         */

        private String addressid;
        private String countryid;
        private String street;
        private String postcode;
        private String city;
        private String phoneno;
        private String firstname;
        private String lastname;
        private String company;
        private int default_billing;
        private int default_shipping;

        public String getAddressid() {
            return addressid;
        }

        public void setAddressid(String addressid) {
            this.addressid = addressid;
        }

        public String getCountryid() {
            return countryid;
        }

        public void setCountryid(String countryid) {
            this.countryid = countryid;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
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

        public String getPhoneno() {
            return phoneno;
        }

        public void setPhoneno(String phoneno) {
            this.phoneno = phoneno;
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

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public int getDefault_billing() {
            return default_billing;
        }

        public void setDefault_billing(int default_billing) {
            this.default_billing = default_billing;
        }

        public int getDefault_shipping() {
            return default_shipping;
        }

        public void setDefault_shipping(int default_shipping) {
            this.default_shipping = default_shipping;
        }
    }
}
