package bidding.app.view.activity.addnewaddress.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaveAddressRequest {

@SerializedName("customeraddress")
@Expose
private Customeraddress customeraddress;

public Customeraddress getCustomeraddress() {
return customeraddress;
}

public void setCustomeraddress(Customeraddress customeraddress) {
this.customeraddress = customeraddress;
}

}