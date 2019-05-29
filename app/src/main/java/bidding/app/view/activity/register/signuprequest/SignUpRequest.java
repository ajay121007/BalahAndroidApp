package bidding.app.view.activity.register.signuprequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignUpRequest {

    @SerializedName("customerData")
    @Expose
    private CustomerData customerData;

    public CustomerData getCustomerData() {
        return customerData;
    }

    public void setCustomerData(CustomerData customerData) {
        this.customerData = customerData;
    }

}