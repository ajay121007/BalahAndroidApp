package bidding.app.view.activity.changeaddress.request;

public class SetDefaultAddress {
    /**
     * customeraddress : {"default_billing":1,"default_shipping":1}
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
         * default_billing : 1
         * default_shipping : 1
         */

        private int default_billing;
        private int default_shipping;

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
