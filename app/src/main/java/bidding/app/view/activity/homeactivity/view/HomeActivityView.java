package bidding.app.view.activity.homeactivity.view;

public interface HomeActivityView {

    void showProgess();
    void hideProgess();

    void showFeedBackMessage(String message);
    void setData(String mFirstName, String mLastName, String mPhone, String mEmail);
}