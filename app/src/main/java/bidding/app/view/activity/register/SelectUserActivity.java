package bidding.app.view.activity.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import bidding.app.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectUserActivity extends AppCompatActivity {

    @BindView(R.id.btn_customer)
    Button btnCustomer;
    @BindView(R.id.btn_seller)
    Button btnSeller;
    Intent intent;
    String mPhoneNumber = "", mCountyCode = "", mPhoneWithCountry = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);
        ButterKnife.bind(this);

        intent = getIntent();
        if (intent != null && intent.getExtras().containsKey("phone_number")) {
            mPhoneNumber = intent.getExtras().getString("phone_number");
            mCountyCode = intent.getExtras().getString("county_code");
        }
    }

    @OnClick({R.id.btn_customer, R.id.btn_seller})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_customer:
                startActivity(new Intent(this, CustomerRegisterActivity.class).putExtra("phone_number", mPhoneNumber).putExtra("county_code", mCountyCode));
                break;
            case R.id.btn_seller:
                startActivity(new Intent(this, SellerRegisterActivity.class).putExtra("phone_number", mPhoneNumber).putExtra("county_code", mCountyCode));
                break;
        }
    }
}
