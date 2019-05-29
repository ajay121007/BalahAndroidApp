package bidding.app.web.handler;

public interface InventoryHandler {
    void onSuccess(String Response);
    void onFail(String message);
}
