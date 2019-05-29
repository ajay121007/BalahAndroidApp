package bidding.app.view.activity.checkout;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import bidding.app.MyApplication;
import bidding.app.R;
import bidding.app.view.activity.BaseActivity;
import bidding.app.view.activity.checkout.view.CheckOutView;
import bidding.app.view.fragment.checkoutfragment.CheckoutFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CheckOutActivity extends BaseActivity implements CheckOutView {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.checkout_container)
    FrameLayout checkoutContainer;
    @BindView(R.id.tv_grand_total)
    TextView tvGrandTotal;
    @BindView(R.id.btn_select_address)
    Button btnSelectAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        ButterKnife.bind(this);
        loadView();
        init();
    }

    private void loadView() {
        CheckoutFragment fragment = new CheckoutFragment();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.checkout_container, fragment)
                .commit();
    }

    private void init() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MyApplication.isAddress == "1") {
            loadView();
            MyApplication.isAddress = "0";
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


    @OnClick({R.id.back, R.id.ll_total, R.id.btn_select_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }
}
