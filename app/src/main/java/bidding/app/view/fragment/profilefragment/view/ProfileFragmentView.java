package bidding.app.view.fragment.profilefragment.view;

/**
 * Created by Thakur on 11/12/2017.
 */

public interface ProfileFragmentView {
    void showProgess();
    void hideProgess();
    void showFeedBackMessage(String message);
    void updateProfileSuccess(String response);
    void profileSuccess(String response);
    void getCardSuccess(String response);
    void addCardSuccess(String response);
}
