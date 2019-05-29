package bidding.app.view.activity.addnewaddress.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Customeraddress {

    @SerializedName("countryid")
    @Expose
    private String countryid;
    @SerializedName("street")
    @Expose
    private String street;
    @SerializedName("postcode")
    @Expose
    private String postcode;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("phoneno")
    @Expose
    private String phoneno;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("default_billing")
    @Expose
    private Integer defaultBilling;
    @SerializedName("default_shipping")
    @Expose
    private Integer defaultShipping;

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

    public Integer getDefaultBilling() {
        return defaultBilling;
    }

    public void setDefaultBilling(Integer defaultBilling) {
        this.defaultBilling = defaultBilling;
    }

    public Integer getDefaultShipping() {
        return defaultShipping;
    }

    public void setDefaultShipping(Integer defaultShipping) {
        this.defaultShipping = defaultShipping;
    }

}