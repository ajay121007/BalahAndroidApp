package bidding.app.view.activity.search.view;

import bidding.app.model.ProductList;

public interface SearchActivityView {
    void showProgess();
    void hideProgess();
    void showFeedBackMessage(String message);

    void productList(ProductList productList);
}
