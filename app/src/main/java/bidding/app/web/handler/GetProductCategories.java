package bidding.app.web.handler;

import bidding.app.model.Categories;
import bidding.app.model.MyAuction;

public interface GetProductCategories {

    void Success(Categories categories);
    void Fail(String error);
}
