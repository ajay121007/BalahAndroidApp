package bidding.app.model;

public class CartItems {


    /**
     * item_id : 291
     * sku : Test product - Dates
     * qty : 11
     * name : Test product - Dates
     * price : 100
     * product_type : simple
     * quote_id : 180
     * extension_attributes : {"image_url":"http://178.128.35.113/media/catalog/product/placeholder/default/logo-2.png","short_description":"The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. "}
     */

    private int item_id;
    private String sku;
    private int qty;
    private String name;
    private int price;
    private String product_type;
    private String quote_id;
    private ExtensionAttributesBean extension_attributes;

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public String getQuote_id() {
        return quote_id;
    }

    public void setQuote_id(String quote_id) {
        this.quote_id = quote_id;
    }

    public ExtensionAttributesBean getExtension_attributes() {
        return extension_attributes;
    }

    public void setExtension_attributes(ExtensionAttributesBean extension_attributes) {
        this.extension_attributes = extension_attributes;
    }

    public static class ExtensionAttributesBean {
        /**
         * image_url : http://178.128.35.113/media/catalog/product/placeholder/default/logo-2.png
         * short_description : The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English.
         */

        private String image_url;
        private String short_description;

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public String getShort_description() {
            return short_description;
        }

        public void setShort_description(String short_description) {
            this.short_description = short_description;
        }
    }
}