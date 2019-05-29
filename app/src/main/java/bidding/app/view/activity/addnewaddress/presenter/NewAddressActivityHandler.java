package bidding.app.view.activity.addnewaddress.presenter;

import bidding.app.view.activity.addnewaddress.NewAddressActivity;
import bidding.app.view.activity.addnewaddress.request.SaveAddressRequest;
import bidding.app.view.activity.changeaddress.request.EditAddress;

public interface NewAddressActivityHandler  {

    void saveAddress(NewAddressActivity activity, String mToken, SaveAddressRequest request);

    void getCustomer(NewAddressActivity activity, String token);

    void editAddress(NewAddressActivity activity, String s, EditAddress request);
}
