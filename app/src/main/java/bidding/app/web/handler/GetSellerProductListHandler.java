package bidding.app.web.handler;

import bidding.app.model.SellerProductListBean;

public interface GetSellerProductListHandler {
    void onSuccess(SellerProductListBean bean);
    void onFailuer(String error);
}
