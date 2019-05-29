package bidding.app.web.handler;

import bidding.app.model.ProductBySku;
import bidding.app.model.ProductDeatils;

public interface GetProductBySkuHandler {
    void Success(ProductBySku productBySku);
    void Fail(String error);
}
