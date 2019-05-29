package bidding.app.view.fragment.profilefragment.presenter;

import android.app.Activity;

import bidding.app.view.fragment.profilefragment.ProfileFragment;
import bidding.app.view.fragment.profilefragment.view.ProfileFragmentView;
import bidding.app.web.WebServc;
import bidding.app.web.handler.GetLoginHandler;
import bidding.app.web.handler.UpdateProfileHandler;


/**
 * Created by Thakur on 11/12/2017.
 */

public class ProfileFragmentPresenter implements ProfileFragmentPresenterHandler {

    ProfileFragmentView view;
    String mStatus = "";

    public ProfileFragmentPresenter(ProfileFragment profileFragment) {
        this.view = profileFragment;

    }


    @Override
    public void updateProfile(Activity activity, String action, String userId, String name, String address, String country, String city, String latitude, String longitute) {
        view.showProgess();
        WebServc.getInstance().updateProfile(new UpdateProfileHandler() {
            @Override
            public void onSuccess(String Response) {
                view.hideProgess();
                view.updateProfileSuccess(Response);
            }

            @Override
            public void onFail(String message) {
                view.hideProgess();
                view.showFeedBackMessage(message.toString());
            }
        }, action, userId, name, address, country, city, latitude, longitute);
    }

    @Override
    public void getCard(Activity activity, String action, String userId) {
        view.showProgess();
        WebServc.getInstance().getCardList(new UpdateProfileHandler() {
            @Override
            public void onSuccess(String Response) {
                view.hideProgess();
                view.getCardSuccess(Response);
            }

            @Override
            public void onFail(String message) {
                view.hideProgess();
                view.showFeedBackMessage(message.toString());

            }
        }, action, userId);
    }

    @Override
    public void addCard(Activity activity, String action, String UserId, String cardnumber, String cardHolderName, String ex_mnth, String ex_year, String cvc, String brand, String tok) {
        view.showProgess();
        WebServc.getInstance().addCard(new UpdateProfileHandler() {
            @Override
            public void onSuccess(String Response) {
                view.hideProgess();
                view.addCardSuccess(Response);

            }

            @Override
            public void onFail(String message) {
                view.hideProgess();

            }
        },action,UserId,cardnumber,cardHolderName,ex_mnth,ex_year,cvc,brand,tok);
    }

    @Override
    public void getProfile(Activity activity, String action, String userId) {
        view.showProgess();
        WebServc.getInstance().getProfile(new GetLoginHandler() {
            @Override
            public void onSuccess(String Response) {
                view.hideProgess();
                view.profileSuccess(Response);

            }

            @Override
            public void onFail(String message) {
                view.hideProgess();
                view.showFeedBackMessage(message);
            }
        },action,userId);
    }
}

