package bidding.app.view.activity.loginactivity.view;

/**
 * Created by Thakur on 11/12/2017.
 */

public interface LoginActivityView {
    void showProgess();
    void hideProgess();
    void showFeedBackMessage(String message);
    void loginSuccess(String response);
    void profileSuccess();
}
