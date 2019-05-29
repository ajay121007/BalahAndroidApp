package bidding.app.web.handler;

import org.json.JSONObject;

import bidding.app.model.MyAuction;
import bidding.app.model.ProductDeatils;

public interface GetProductByIdHandler {
    void Success(String response);
    void Fail(String error);
}
