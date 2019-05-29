package bidding.app.model;

/**
 * Created by vijay on 19/8/17.
 */

public class ProductBean {
    String product_id,product_name, image, qantity, detail, price,productnumber,currency_type;

    public ProductBean(String product_id,String product_name,String image,String qantity,String detail,String price,String productnumber,String currency_type){


        this.product_id =product_id;
        this.product_name =product_name;
        this.image =image;
        this.qantity =qantity;
        this.detail =detail;
        this.price =price;
        this.productnumber =productnumber;
        this.currency_type=currency_type;

    }

    public String getProduct_id() {
        return product_id;
    }

    public String getPrice() {
        return price;
    }

    public String getDetail() {
        return detail;
    }

    public String getImage() {
        return image;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getQantity() {
        return qantity;
    }

    public String getProductnumber() {
        return productnumber;
    }

    public String getCurrency_type() {
        return currency_type;
    }
}
