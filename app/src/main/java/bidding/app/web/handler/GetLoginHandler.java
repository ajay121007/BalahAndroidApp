package bidding.app.web.handler;

/**
 * Created by Thakur on 11/12/2017.
 */

public interface GetLoginHandler {
    void onSuccess(String Response);
    void onFail(String message);
}
