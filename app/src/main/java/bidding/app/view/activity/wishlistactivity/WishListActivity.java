package bidding.app.view.activity.wishlistactivity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import bidding.app.R;
import bidding.app.extra.CSPreferences;
import bidding.app.extra.Constants;
import bidding.app.model.WishListBean;
import bidding.app.view.activity.BaseActivity;
import bidding.app.view.activity.wishlistactivity.adapter.WishListAdapter;
import bidding.app.view.activity.wishlistactivity.presenter.WishListActivityPresenter;
import bidding.app.view.activity.wishlistactivity.presenter.WishListActivityPresenterHandler;
import bidding.app.view.activity.wishlistactivity.view.WishListActivityView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WishListActivity extends BaseActivity implements WishListActivityView, WishListAdapter.ItemClick {

    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.recyclerviewWishList)
    RecyclerView recyclerviewWishList;
    WishListActivityPresenterHandler mHandler;
    String mToken = "";
    WishListBean listBean;
    WishListAdapter adapter;
    LinearLayoutManager mLayoutManager;
    WishListAdapter.ItemClick mClickListenr;
    int selectedPostion = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);
        ButterKnife.bind(this);

        init();

        customToolbarWithBack(this, toolBar, "WishList" + "");


    }

    private void init() {
        mClickListenr = this;
        mToken = "Bearer " + CSPreferences.readString(this, Constants.TOKEN);
        mHandler = new WishListActivityPresenter(this);

        mHandler.getWishlist(this, mToken);
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
    public void wishListSuccess(String response) {

        try {
            JSONArray jsonArray = new JSONArray(response);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            listBean = new Gson().fromJson(jsonObject.toString(), WishListBean.class);

            if (listBean != null && listBean.getResponse().getStatus() == 1) {
                setAdapter();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void removeFromWishListSucces(String success) {
        showToast(success + "");
        listBean.getResponse().getData().remove(selectedPostion);
        adapter.notifyItemRemoved(selectedPostion);


    }

    @Override
    public void onSuccessfullyMoved(String success) {
        showToast(success + "");
        listBean.getResponse().getData().remove(selectedPostion);
        adapter.notifyItemRemoved(selectedPostion);
    }

    private void setAdapter() {
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerviewWishList.setLayoutManager(mLayoutManager);
        adapter = new WishListAdapter(this, listBean.getResponse().getData(), mClickListenr);
        recyclerviewWishList.setAdapter(adapter);
    }

    @Override
    public void OnItemClick(int id, int position, String wishlist_id) {
        selectedPostion = position;
        switch (id) {
            case R.id.btnRemove:
                mHandler.removeFromWishListUsingId(this, wishlist_id, mToken);
                break;
            case R.id.btnAddToBag:
                mHandler.moveWishListToCart(this, wishlist_id, mToken);
                break;
        }
    }
}
