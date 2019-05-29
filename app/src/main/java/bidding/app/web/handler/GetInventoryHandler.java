package bidding.app.web.handler;

import bidding.app.model.InventoryBean;

public interface GetInventoryHandler {
    void onSuccess(InventoryBean bean);
    void onFail(String message);
}