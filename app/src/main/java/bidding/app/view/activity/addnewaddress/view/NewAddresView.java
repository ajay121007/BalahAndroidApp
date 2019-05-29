package bidding.app.view.activity.addnewaddress.view;

public interface NewAddresView {
    void showProgess();
    void hideProgess();
    void showFeedBackMessage(String message);
    void setData(String mFirstName, String mLastName, String mPhone);
}
