package bidding.app.view.activity.expressactivity;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bidding.app.R;
import bidding.app.extra.AdapterClickListner;
import bidding.app.model.Categories;
import bidding.app.model.ProductData;
import bidding.app.model.ProductList;
import bidding.app.view.activity.BaseActivity;
import bidding.app.view.activity.expressactivity.adapter.CategoriesAdapter;
import bidding.app.view.activity.expressactivity.adapter.ProductListAdapter;
import bidding.app.view.activity.expressactivity.adapter.SortAdapter;
import bidding.app.view.activity.expressactivity.presenter.ExpressActivityPresenter;
import bidding.app.view.activity.expressactivity.presenter.ExpressActivityPresenterHandler;
import bidding.app.view.activity.expressactivity.view.ExpressActivityView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExpressActivity extends BaseActivity implements ExpressActivityView, AdapterClickListner {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.rv_product_list)
    RecyclerView rvProductList;
    RecyclerView rvCategoriesList;
    ExpressActivityPresenterHandler handler;
    LinearLayoutManager layoutManager;
    BottomSheetDialog categoriesDialog, sortDialog;
    CategoriesAdapter categoriesAdapter;
    SortAdapter sortAdapter;
    @BindView(R.id.tv_categories)
    TextView tvCategories;
    ProductListAdapter productListAdapter;
    String key = "category_id", id = "", conditionType = "eq", field = "name", direction = "DESC", pageSize = "10", currentPage = "1";
    public AdapterClickListner listner;
    int selectedPostion = -1, mCurrentPage = 0, mLastPage = 0, mTotalCount = 0;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    private boolean loading = false;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    @BindView(R.id.rl_sort)
    RelativeLayout rlSort;
    Categories categories;
    List<ProductData> dataList = new ArrayList<>();
    TextView tvFilterTitle;
    String sortTitle[] = {"Name : A-Z", "Name : Z-A", "Price : High to Low", "Price : Low to High"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_express);
        ButterKnife.bind(this);
        inti();
        setAdapter();
        viewlistner();
        handler.catagories();

    }


    private void inti() {
        listner = this;
        handler = new ExpressActivityPresenter(this, progressBar,ExpressActivity.this);
        setSortFilter();
    }


    private void viewlistner() {
        rvProductList.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            if (mCurrentPage < mLastPage) {
                                loading = false;
                                mCurrentPage++;
                                currentPage = String.valueOf(mCurrentPage);

                                handler.productlist(key, id + "", conditionType, field, direction, pageSize, currentPage);
                            }
                            Log.v("SelfLastPage", "Last Item Wow !");
                        }
                    }
                } else if (dy < 0) {

                }
            }

        });
    }

    @OnClick({R.id.back, R.id.tv_categories, R.id.rl_sort})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;

            case R.id.tv_categories:
                categoriesDialog.show();
                break;
            case R.id.rl_sort:
                sortDialog.show();
                break;


        }
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
    public void getProductCategoriesSuccess(Categories categories) {
        id = categories.getId() + "";
        setCategoriesAdapter(categories.getChildrenData());
        handler.productlist(key, id, conditionType, field, direction, pageSize, currentPage);
    }

    @Override
    public void productList(ProductList list) {
        mCurrentPage = list.getSearchCriteria().getCurrentPage();
        pageSize = list.getSearchCriteria().getPageSize() + "";
        mTotalCount = list.getTotal_count();

        int reminder = mTotalCount % Integer.parseInt(pageSize);
        if (reminder == 0) {
            mLastPage = mTotalCount / Integer.parseInt(pageSize);
        } else {
            mLastPage = mTotalCount / Integer.parseInt(pageSize);
            mLastPage++;
        }

        for (int i = 0; i < list.getProductData().size(); i++) {
            dataList.add(list.getProductData().get(i));
        }
        updateAdapter(dataList);
    }

    private void setSortFilter() {
        sortAdapter = new SortAdapter(this, sortTitle, listner);
        View view = getLayoutInflater().inflate(R.layout.categories_bottom_sheet, null);
        sortDialog = new BottomSheetDialog(this);
        sortDialog.setContentView(view);
        RecyclerView rvSortList = view.findViewById(R.id.rv_categories_list);
        TextView tvSortTitle = view.findViewById(R.id.tv_title);
        tvSortTitle.setText("Sort By");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvSortList.setLayoutManager(layoutManager);
        rvSortList.setAdapter(sortAdapter);

    }

    private void setCategoriesAdapter(List<Categories.ChildData> childrenData) {
        categoriesAdapter = new CategoriesAdapter(this, childrenData, listner);
        View view = getLayoutInflater().inflate(R.layout.categories_bottom_sheet, null);
        categoriesDialog = new BottomSheetDialog(this);
        categoriesDialog.setContentView(view);
        rvCategoriesList = view.findViewById(R.id.rv_categories_list);
        tvFilterTitle = view.findViewById(R.id.tv_title);
        layoutManager = new LinearLayoutManager(this);
        rvCategoriesList.setLayoutManager(layoutManager);
        tvFilterTitle.setText("Categories");
        rvCategoriesList.setAdapter(categoriesAdapter);
    }

    private void setAdapter() {
        RecyclerView.LayoutManager mlayoutmanger = new GridLayoutManager(this, 2);
        rvProductList.setLayoutManager(mlayoutmanger);
        rvProductList.setItemAnimator(new DefaultItemAnimator());
        productListAdapter = new ProductListAdapter(this, dataList);
        rvProductList.setAdapter(productListAdapter);
    }

    private void updateAdapter(List<ProductData> list) {
        loading = true;
        Log.d("CurrentPage", mCurrentPage + "");
        productListAdapter.customNotify(list);

    }

    @Override
    public void onviewClick(int position, int id) {
        this.id = id + "";
        this.selectedPostion = position;
        categoriesAdapter.customNotify(selectedPostion);
        categoriesDialog.dismiss();
        dataList.clear();
        handler.productlist(key, id + "", conditionType, field, direction, pageSize, "1");
    }

    @Override
    public void onSortViewClick(int position, String direction, String field) {
        sortAdapter.customNotify(position);
        sortDialog.dismiss();
        dataList.clear();
        handler.productlist(key, id + "", conditionType, field, direction, pageSize, "1");
    }

}
