package bidding.app.view.activity.sellerproductactivity.presenter;

import android.util.Log;
import android.widget.ProgressBar;

import bidding.app.model.SellerProductListBean;
import bidding.app.view.activity.sellerproductactivity.SellerProductActivity;
import bidding.app.view.activity.sellerproductactivity.view.SellerActivityView;
import bidding.app.web.WebServc;
import bidding.app.web.handler.CallBackHandler;
import bidding.app.web.handler.GetSellerProductListHandler;


/**
 * Created by Thakur on 11/12/2017.
 */

public class SellerActivityPresenter implements SellerActivityPresenterHandler {

    ProgressBar progressBar;
    SellerActivityView view;


    public SellerActivityPresenter(SellerActivityView view) {
        this.view = view;

    }


    @Override
    public void getProductlist(SellerProductActivity activity, String mToken, String key, final String id, String conditionType, String field, String direction, String pageSize, final String currentPage) {

        view.showProgess();

        WebServc.getInstance().getSellerProductList(new GetSellerProductListHandler() {
            @Override
            public void onSuccess(SellerProductListBean bean) {
                view.hideProgess();
                view.productListSuccess(bean);

            }


            @Override
            public void onFailuer(String error) {
                Log.d("ProductListError", error.toString());
                view.hideProgess();

            }
        },activity, mToken, key, id, conditionType, field, direction, pageSize, currentPage);
    }
}

