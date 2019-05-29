package bidding.app.view.activity.changeaddress.presenter;

import android.util.Log;

import bidding.app.view.activity.changeaddress.ChangeAddressActivity;
import bidding.app.view.activity.changeaddress.request.SetDefaultAddress;
import bidding.app.view.activity.changeaddress.view.ChangeAddressActivityView;
import bidding.app.web.WebServc;
import bidding.app.web.handler.CallBackHandler;


/**
 * Created by Thakur on 11/12/2017.
 */

public class ChangeAddressActivityPresenter implements ChangeAddresActivityPresenterHandler {

    ChangeAddressActivityView view;

    public ChangeAddressActivityPresenter(ChangeAddressActivityView view) {
        this.view=view;

    }


    @Override
    public void getAddressList(ChangeAddressActivity actvity, String mToken) {
        view.showProgess();
        WebServc.getInstance().getAddress(new CallBackHandler() {
            @Override
            public void Success(String response) {
                Log.d("ADDRESSLIST",response.toString());
                view.hideProgess();
                view.addressSuccess(response);
            }

            @Override
            public void Failuer(String error) {
                view.hideProgess();
                view.showFeedBackMessage(error.toString());
            }
        },actvity,mToken);
    }

    @Override
    public void setDefaultAddres(ChangeAddressActivity activity, String mToken, String entity_id, SetDefaultAddress defaultAddress) {
        view.showProgess();
        WebServc.getInstance().setAsDefaultAddress(new CallBackHandler() {
            @Override
            public void Success(String response) {
                Log.d("SETDEFAULTADD",response.toString());
                view.hideProgess();
                view.onSuccessfullySetDefault(response);
            }

            @Override
            public void Failuer(String error) {
                view.hideProgess();
                view.showFeedBackMessage(error.toString());
            }
        },activity,mToken,entity_id,defaultAddress);
    }

    @Override
    public void deleteAddress(ChangeAddressActivity activity, String mToken, final int position, String entity_id) {
        view.showProgess();
        WebServc.getInstance().deleteAddress(new CallBackHandler() {
            @Override
            public void Success(String response) {
                Log.d("DELETEADDS",response.toString());
                view.hideProgess();
                view.onSuccessfullyDeleted(position,response);
            }

            @Override
            public void Failuer(String error) {
                view.hideProgess();
                view.showFeedBackMessage(error.toString());
            }
        },activity,mToken,entity_id);
    }
}

