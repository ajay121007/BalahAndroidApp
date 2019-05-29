package bidding.app.view.activity.changeaddress.view;

/**
 * Created by Thakur on 11/12/2017.
 */

public interface ChangeAddressActivityView {

    void showProgess();

    void hideProgess();

    void showFeedBackMessage(String message);

    void addressSuccess(String response);

    void onSuccessfullySetDefault(String response);

    void onSuccessfullyDeleted(int pos,String response);
}
