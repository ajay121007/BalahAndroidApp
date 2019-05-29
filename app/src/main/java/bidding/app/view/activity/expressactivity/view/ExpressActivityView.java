package bidding.app.view.activity.expressactivity.view;

import bidding.app.model.Categories;
import bidding.app.model.ProductList;

public interface ExpressActivityView {
    void showProgess();
    void hideProgess();
    void showFeedBackMessage(String message);
    void getProductCategoriesSuccess(Categories categories);
    void productList(ProductList list);
}
