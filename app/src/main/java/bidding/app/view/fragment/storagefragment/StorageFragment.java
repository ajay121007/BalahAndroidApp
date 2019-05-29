package bidding.app.view.fragment.storagefragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.zip.Inflater;

import bidding.app.R;
import bidding.app.extra.CSPreferences;
import bidding.app.extra.Constants;
import bidding.app.extra.Utils;
import bidding.app.view.activity.addauction.AddAuctionActivity;
import bidding.app.view.fragment.BaseFragment;
import bidding.app.view.fragment.storagefragment.presenter.StorageFragmentPresenter;
import bidding.app.view.fragment.storagefragment.presenter.StorageFragmentPresenterHandler;
import bidding.app.view.fragment.storagefragment.view.StorageFragmentView;

public class StorageFragment extends BaseFragment implements StorageFragmentView, View.OnClickListener {

    View view;
    String mProductName = "", mQuantity = "", mPrice = "", mTotalprice = "", mProductId = "", mAuctionId = "", mCurrencyType = "", mCurrencySym = "", mInventory = "", mDelivery = "";
    TextView tvPruduct, tvPrice,tvPrices, tvQuantity, tvTotalPrice,tvTotalPrices, tvAddAuction, tvDeliver;
    ImageView back;
    EditText edDeliver;
    AlertDialog.Builder builder;
    private Button ok, cancel;
    StorageFragmentPresenterHandler handler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_inventory, container, false);
        init(view);
        listner();
        return view;
    }

    private void listner() {
        back.setOnClickListener(this);
        tvAddAuction.setOnClickListener(this);
        tvDeliver.setOnClickListener(this);
    }

    private void init(View view) {
        handler = new StorageFragmentPresenter(this);
        Bundle b = getArguments();
        mProductName = b.getString("product");
        mProductId = b.getString("productid");
        mPrice = b.getString("price");
        mQuantity = b.getString("quantity");
        mTotalprice = b.getString("totalprice");
        mAuctionId = b.getString("auctionid");
        mCurrencyType = b.getString("currency_type");


        back = view.findViewById(R.id.back);
        tvPruduct = view.findViewById(R.id.tv_product);
        tvDeliver = view.findViewById(R.id.tv_deliver);
        tvPrice = view.findViewById(R.id.tv_price);
        tvPrices = view.findViewById(R.id.tv_prices);
        tvQuantity = view.findViewById(R.id.tv_quntity);
        tvTotalPrice = view.findViewById(R.id.tv_totalprice);
        tvTotalPrices = view.findViewById(R.id.tv_totalprices);
        tvAddAuction = view.findViewById(R.id.tv_add_auction);

        setData();
    }

    private void setData() {

        mCurrencySym = Utils.getCurrencySymbol(mCurrencyType);
        tvPrices.append(" ( in "+mCurrencySym+" )");
        tvTotalPrices.append(" ( in "+mCurrencySym+" )");
        tvPruduct.setText(mProductName);
        tvPrice.setText(mPrice);
        tvQuantity.setText(mQuantity);
        tvTotalPrice.setText(mTotalprice);
    }

    public void showAlert() {
        builder = new AlertDialog.Builder(getActivity());
        view = LayoutInflater.from(getActivity()).inflate(R.layout.deliver_dialog, null);
        builder.setView(view);

        final AlertDialog alertDialog = builder.create();
        ok = (Button) view.findViewById(R.id.ok);
        cancel = (Button) view.findViewById(R.id.cancel);
        edDeliver = view.findViewById(R.id.ed_deliver);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean b = true;
                mDelivery = edDeliver.getText().toString();
                if (mDelivery.isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter quantity.", Toast.LENGTH_SHORT).show();
                    b = false;
                } else if (!mDelivery.isEmpty() && !mDelivery.matches("[0-9]+")) {
                    Toast.makeText(getActivity(), "Invalid quantity.", Toast.LENGTH_SHORT).show();
                    b = false;
                } else if ((Long.parseLong(mDelivery) > Long.parseLong(mQuantity))) {
                    Toast.makeText(getActivity(), "Invalid quantity.", Toast.LENGTH_SHORT).show();
                    b = false;
                }
                if (b) {
                    handler.moveToInventory(getActivity(), "moveToInventory", CSPreferences.readString(getActivity(), Constants.USERID), mAuctionId, mInventory, mDelivery);
                    alertDialog.dismiss();
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = lp.WRAP_CONTENT;
        lp.height = lp.WRAP_CONTENT;
        alertDialog.getWindow().setAttributes(lp);
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                getActivity().getFragmentManager().popBackStack();
                break;
            case R.id.tv_add_auction:
                if (Long.parseLong(mQuantity) <= 0) {
                    Toast.makeText(getActivity(), "Insufficient quantity.", Toast.LENGTH_SHORT).show();
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("productid", mProductId);
                    bundle.putString("productname", mProductName);
                    bundle.putString("quantity", mQuantity);
                    bundle.putString("auctionid", mAuctionId);
                    bundle.putString("currency", mCurrencySym);
                    startActivity(new Intent(getActivity(), AddAuctionActivity.class).putExtras(bundle));
                }

                break;

            case R.id.tv_deliver:
                showAlert();
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
}
