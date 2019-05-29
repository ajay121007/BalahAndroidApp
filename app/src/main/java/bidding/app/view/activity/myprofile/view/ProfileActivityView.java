package bidding.app.view.activity.myprofile.view;

/**
 * Created by Thakur on 11/12/2017.
 */

public interface ProfileActivityView {
    void showProgess();
    void hideProgess();
    void showFeedBackMessage(String message);
    void updateProfileSuccess(String response);
    void profileSuccess(String response);

}
