package bidding.app.view.activity.sellerproductactivity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import bidding.app.R;
import bidding.app.extra.CSPreferences;
import bidding.app.extra.Constants;
import bidding.app.model.SellerProductListBean;
import bidding.app.view.activity.BaseActivity;
import bidding.app.view.activity.addauction.AddAuctionActivity;
import bidding.app.view.activity.sellerproductactivity.adapter.SellerProductListAdapter;
import bidding.app.view.activity.sellerproductactivity.presenter.SellerActivityPresenter;
import bidding.app.view.activity.sellerproductactivity.presenter.SellerActivityPresenterHandler;
import bidding.app.view.activity.sellerproductactivity.view.SellerActivityView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SellerProductActivity extends BaseActivity implements SellerActivityView,SellerProductListAdapter.ItemClickListner {

    SellerActivityPresenterHandler mPresenter;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.rvProductList)
    RecyclerView rvProductList;
    String mToken = "";
    String key = "status", id = "1", conditionType = "eq", field = "price", direction = "DESC", pageSize = "10", currentPage = "1";
    SellerProductListAdapter adapter;
    RecyclerView.LayoutManager mLinearLayoutManager;
    SellerProductListAdapter.ItemClickListner mListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_product);
        ButterKnife.bind(this);

        init();
        customToolbarWithBack(this, toolBar, "My Product");
    }

    private void init() {

        mListner=this;

        mToken = "Bearer " + CSPreferences.readString(this, Constants.TOKEN);
        mLinearLayoutManager = new GridLayoutManager(this, 2);

        rvProductList.setLayoutManager(mLinearLayoutManager);
        rvProductList.setItemAnimator(new DefaultItemAnimator());
        mPresenter = new SellerActivityPresenter(this);

        mPresenter.getProductlist(this,mToken, key, id + "", conditionType, field, direction, pageSize, currentPage);
    }

    @Override
    public void showProgess() {
        showProgressDialog();

    }

    @Override
    public void hideProgess() {
        hideProgressDialog();
    }

    @Override
    public void showFeedBackMessage(String message) {
        showToast(message);
    }

    @Override
    public void productListSuccess(SellerProductListBean bean) {
        setAdapter(bean);

    }

    private void setAdapter(SellerProductListBean bean) {
        adapter= new SellerProductListAdapter(this,bean.getItems(),mListner);
        rvProductList.setAdapter(adapter);
    }

    @Override
    public void OnClickItem(int position, String id, String name) {
        AddAuctionActivity.mProductId=id;
        AddAuctionActivity.mProductName=name;
        finish();
    }
}
