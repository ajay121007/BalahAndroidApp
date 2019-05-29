package bidding.app.model;

import java.util.List;

public class ProductData {

    /**
     * id : 132
     * sku : MSH02-33-Black
     * name : ونانة
     * attribute_set_id : 4
     * price : 32.5
     * status : 1
     * visibility : 4
     * type_id : simple
     * created_at : 2018-07-17 10:20:17
     * updated_at : 2018-07-17 23:15:37
     * weight : 1
     * product_links : []
     * tier_prices : []
     * custom_attributes : [{"attribute_code":"description","value":"<p>Fleet of foot or slow and steady, you'll be in complete comfort with the Apollo Running Short. Lightweight polyester material lets you move with ease, and mesh side panels promise plenty of ventilation as you work up a sweat. The stretchy elastic waistband delivers a flexible fit.<\/p>\n<p>\u2022 Black shorts with green accents.<br />\u2022 Side pockets. <br />\u2022 4\" inseam. <br />\u2022 Machine wash/dry.<\/p>"},{"attribute_code":"short_description","value":"<p>Reach your limit and keep on going in the Atomic Endurance Running Tee. Built to help any athlete push past the wall with loads of performance features.<\/p>"},{"attribute_code":"meta_keyword","value":"ونانة"},{"attribute_code":"meta_description","value":"Apollo Running Short-33-Black <p>Fleet of foot or slow and steady, you'll be in complete comfort with the Apollo Running Short. Lightweight polyester material lets you move with ease, and mesh side panels promise plenty of ventilation as you work up a swe"},{"attribute_code":"image","value":"/6/_/6.jpg"},{"attribute_code":"small_image","value":"/6/_/6.jpg"},{"attribute_code":"thumbnail","value":"/6/_/6.jpg"},{"attribute_code":"category_ids","value":["22"]},{"attribute_code":"options_container","value":"container2"},{"attribute_code":"required_options","value":"0"},{"attribute_code":"has_options","value":"0"},{"attribute_code":"msrp_display_actual_price_type","value":"0"},{"attribute_code":"url_key","value":"apollo-running-short-33"},{"attribute_code":"gift_message_available","value":"2"},{"attribute_code":"swatch_image","value":"/6/_/6.jpg"},{"attribute_code":"tax_class_id","value":"2"},{"attribute_code":"featured","value":"0"},{"attribute_code":"ves_enable_order","value":"1"},{"attribute_code":"vendor_id","value":"0"},{"attribute_code":"approval","value":"2"},{"attribute_code":"search_weight","value":"0"},{"attribute_code":"in_html_sitemap","value":"1"},{"attribute_code":"use_in_crosslinking","value":"1"}]
     */

    private int id;
    private String sku;
    private String name;
    private int attribute_set_id;
    private double price;
    private int status;
    private int visibility;
    private String type_id;
    private String created_at;
    private String updated_at;
    private int weight;
    private String image;
    private List<?> product_links;
    private List<?> tier_prices;
    private List<CustomAttributes> custom_attributes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAttribute_set_id() {
        return attribute_set_id;
    }

    public void setAttribute_set_id(int attribute_set_id) {
        this.attribute_set_id = attribute_set_id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
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

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public List<?> getProduct_links() {
        return product_links;
    }

    public void setProduct_links(List<?> product_links) {
        this.product_links = product_links;
    }

    public List<?> getTier_prices() {
        return tier_prices;
    }

    public void setTier_prices(List<?> tier_prices) {
        this.tier_prices = tier_prices;
    }

    public List<CustomAttributes> getCustom_attributes() {
        return custom_attributes;
    }

    public void setCustom_attributes(List<CustomAttributes> custom_attributes) {
        this.custom_attributes = custom_attributes;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
