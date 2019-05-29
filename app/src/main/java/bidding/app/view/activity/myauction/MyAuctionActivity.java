package bidding.app.view.activity.myauction;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import bidding.app.R;
import bidding.app.controller.ModelManager;
import bidding.app.extra.CSPreferences;
import bidding.app.extra.Constants;
import bidding.app.extra.Operations;
import bidding.app.model.MyAuction;
import bidding.app.view.activity.BaseActivity;
import bidding.app.view.activity.myauction.adapter.MyAuctionsAdapter;
import bidding.app.view.activity.myauction.presenter.MyAuctionActivityPresenter;
import bidding.app.view.activity.myauction.presenter.MyAuctionActivityPresenterHandler;
import bidding.app.view.activity.myauction.view.MyAuctionActivityView;
import bidding.app.view.adapter.AllAuctionDetailsAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyAuctionActivity extends BaseActivity implements MyAuctionActivityView {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    MyAuctionActivityPresenterHandler handler;
    private String mUserId = "";
    MyAuctionsAdapter adapter;
    private LinearLayoutManager layoutmanger;
    MyAuctionActivityPresenterHandler mPresenter;
    String key = "status", id = "3", conditionType = "eq", field = "auction_id", direction = "DESC", pageSize = "10", currentPage = "1", mToken = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_auction);
        ButterKnife.bind(this);
        init();
    }


    private void init() {
        mPresenter = new MyAuctionActivityPresenter(this);
        mToken = "Bearer " + CSPreferences.readString(MyAuctionActivity.this, Constants.TOKEN);
        handler = new MyAuctionActivityPresenter(this);

        mPresenter.getMyAuction(this, mToken, key, id, conditionType, field, direction, pageSize, currentPage);
    }

    @OnClick(R.id.back)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                overridePendingTransitionExit();
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
    public void setAdapter(MyAuction myAuction) {
        adapter = new MyAuctionsAdapter(MyAuctionActivity.this, myAuction.getResponse().getAuctionDetail());
        layoutmanger = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutmanger);
        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.setAdapter(adapter);

    }

    @Override
    public void myAuctionList(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            if (jsonArray.length() == 0) {
                Toast.makeText(this, "No auction yet.", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransitionExit();
    }
}
