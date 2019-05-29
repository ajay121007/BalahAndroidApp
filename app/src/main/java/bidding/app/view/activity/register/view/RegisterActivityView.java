package bidding.app.view.activity.register.view;

/**
 * Created by Thakur on 11/12/2017.
 */

public interface RegisterActivityView {

    void showProgess();
    void hideProgess();
    void showFeedBackMessage(String message);
    void registerSuccess(String response);
}
