package bidding.app.view.activity.wonauction;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import bidding.app.R;
import bidding.app.extra.CSPreferences;
import bidding.app.extra.Constants;
import bidding.app.model.WonAuction;
import bidding.app.view.activity.BaseActivity;
import bidding.app.view.activity.wonauction.adapter.WonAuctionsAdapter;
import bidding.app.view.activity.wonauction.presenter.WonAuctionActivityPresenter;
import bidding.app.view.activity.wonauction.presenter.WonAuctionActivityPresenterHandler;
import bidding.app.view.activity.wonauction.view.WonAuctionActivityView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WonAuctionActivity extends BaseActivity implements WonAuctionActivityView {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    WonAuctionActivityPresenterHandler handler;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    private String mToken = "";
    WonAuctionsAdapter adapter;
    private LinearLayoutManager layoutmanger;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_auction);
        ButterKnife.bind(this);
        init();
        getAllAuctionList();
    }

    private void getAllAuctionList() {
        handler.getWonAuction("Bearer " + mToken);
    }

    private void init() {
        mToken = CSPreferences.readString(WonAuctionActivity.this, Constants.TOKEN);
        handler = new WonAuctionActivityPresenter(this);
        toolbarTitle.setText("Won Auction");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransitionExit();
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
    public void setAdapter(WonAuction wonAuction) {
        adapter = new WonAuctionsAdapter(WonAuctionActivity.this, wonAuction.getResponse().getData());
        layoutmanger = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutmanger);
        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.setAdapter(adapter);

    }
}
