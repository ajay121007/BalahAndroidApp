package bidding.app.view.activity.changeaddress.presenter;

import bidding.app.view.activity.changeaddress.ChangeAddressActivity;
import bidding.app.view.activity.changeaddress.request.SetDefaultAddress;

/**
 * Created by Thakur on 11/12/2017.
 */

public interface ChangeAddresActivityPresenterHandler {

    void getAddressList(ChangeAddressActivity activity,String mToken);

    void setDefaultAddres(ChangeAddressActivity activity, String mToken, String entity_id, SetDefaultAddress defaultAddress);

    void deleteAddress(ChangeAddressActivity changeAddressActivity, String mToken, int position, String entity_id);
}
