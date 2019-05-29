package bidding.app.view.activity.changeaddress;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import bidding.app.MyApplication;
import bidding.app.R;
import bidding.app.extra.CSPreferences;
import bidding.app.extra.Constants;
import bidding.app.model.AddressListBean;
import bidding.app.model.SetDefaultAddressBean;
import bidding.app.view.activity.BaseActivity;
import bidding.app.view.activity.addnewaddress.NewAddressActivity;
import bidding.app.view.activity.changeaddress.adapter.AddressAdapter;
import bidding.app.view.activity.changeaddress.presenter.ChangeAddresActivityPresenterHandler;
import bidding.app.view.activity.changeaddress.presenter.ChangeAddressActivityPresenter;
import bidding.app.view.activity.changeaddress.request.SetDefaultAddress;
import bidding.app.view.activity.changeaddress.view.ChangeAddressActivityView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangeAddressActivity extends BaseActivity implements ChangeAddressActivityView, AddressAdapter.ItemClick {

    @BindView(R.id.rvAddress)
    RecyclerView rvAddress;
    ChangeAddresActivityPresenterHandler mHandler;
    String mToken = "";
    AddressListBean addressListBean;
    AddressAdapter addressAdapter;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.llAddNewAddress)
    LinearLayout llAddNewAddress;
    AddressAdapter.ItemClick mListner;
    SetDefaultAddressBean bean;
    int selectedPosition = -1, mLastPosition = -1;
    String mAddressID="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_address);
        ButterKnife.bind(this);

        inti();

    }

    private void inti() {
        mListner = this;
        mToken = "Bearer " + CSPreferences.readString(this, Constants.TOKEN);
        mHandler = new ChangeAddressActivityPresenter(this);
        mHandler.getAddressList(this, mToken);
    }

    private void setAdapter() {
        LinearLayoutManager mlayoutmanger = new LinearLayoutManager(this);
        rvAddress.setLayoutManager(mlayoutmanger);
        rvAddress.setItemAnimator(new DefaultItemAnimator());
        addressAdapter = new AddressAdapter(this, addressListBean.getResponse().getData(), mListner);
        rvAddress.setAdapter(addressAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MyApplication.isAddress == "1") {
            mHandler.getAddressList(this, mToken);
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

    @Override
    public void addressSuccess(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            addressListBean = new Gson().fromJson(jsonObject.toString(), AddressListBean.class);
            setAdapter();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccessfullySetDefault(String response) {

        try {
            JSONArray jsonArray = new JSONArray(response.toString());
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            bean = new Gson().fromJson(jsonObject.toString(), SetDefaultAddressBean.class);
            showFeedBackMessage(bean.getResponse().getData().get(0).getSuccess());

            addressListBean.getResponse().getData().get(mLastPosition).setIs_default(0);
            addressListBean.getResponse().getData().get(selectedPosition).setIs_default(1);

            addressAdapter.customNotify(selectedPosition);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onSuccessfullyDeleted(int position, String response) {
        if (response=="true"){
            addressListBean.getResponse().getData().remove(position);
            addressAdapter.notifyItemRemoved(position);
        }

    }

    @OnClick({R.id.back, R.id.llAddNewAddress})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.llAddNewAddress:
                startActivity(new Intent(this, NewAddressActivity.class));
                break;
        }
    }

    @Override
    public void OnItemClick(int position, String entity_id, int lastPosition) {
        this.mLastPosition = lastPosition;
        this.selectedPosition = position;
        SetDefaultAddress defaultAddress = new SetDefaultAddress();
        SetDefaultAddress.CustomeraddressBean bean = new SetDefaultAddress.CustomeraddressBean();
        bean.setDefault_billing(1);
        bean.setDefault_shipping(1);
        defaultAddress.setCustomeraddress(bean);
        mHandler.setDefaultAddres(this, mToken, entity_id, defaultAddress);
    }

    @Override
    public void OnEditClick(int position, String entityId) {
        this.mAddressID=entityId;
        startActivity(new Intent(this,NewAddressActivity.class).putExtra(Constants.ADDRESSID,mAddressID).putExtra(Constants.TITLE,"Edit Address").putExtra(Constants.BUTTONNAME,"Update"));
    }

    @Override
    public void OnDeleteClick(int position, String entity_id) {
        mHandler.deleteAddress(this,mToken,position,entity_id);
    }
}
