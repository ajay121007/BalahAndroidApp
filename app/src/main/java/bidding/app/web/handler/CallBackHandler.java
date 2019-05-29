package bidding.app.web.handler;

public interface CallBackHandler {
    void Success(String response);
    void Failuer(String error);
}
