package bidding.app.view.fragment.shoppingfragment;

import android.content.Context;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import bidding.app.R;
import bidding.app.extra.CSPreferences;
import bidding.app.extra.Config;
import bidding.app.extra.Constants;
import bidding.app.extra.Utils;
import bidding.app.view.fragment.BaseFragment;
import bidding.app.view.fragment.shoppingfragment.presenter.ShoppingFragmentPresenter;
import bidding.app.view.fragment.shoppingfragment.presenter.ShoppingFragmentPresenterHandler;
import bidding.app.view.fragment.shoppingfragment.view.ShoppingFragmentView;

public class ShoppingFragment extends BaseFragment implements ShoppingFragmentView, View.OnClickListener {
    View view;
    String mProductName = "", mQuantity = "", mPrice = "", mTotalprice = "", mImage = "", mCurrencyType = "", mStatus = "", mUserId = "", mProductId = "", mCardNumber = "";
    TextView tvPruduct, tvPrice, tvQuantity, tvTotalPrice, tvAddAuction, tvPayment, tvAlertTitle, tvAlertDescription, tvPay, tvTotalPrices, tvPrices;
    ImageView back, productImage;
    ShoppingFragmentPresenterHandler handler;
    AlertDialog.Builder dialog;
    Button ok, cancel;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shopping, container, false);
        init(view);
        listner();
        return view;

    }

    private void listner() {
        back.setOnClickListener(this);
        tvPay.setOnClickListener(this);

        if (!mStatus.isEmpty() && mStatus.equalsIgnoreCase("0")) {
            tvPayment.setText("");
            tvPay.setVisibility(View.VISIBLE);
        } else if (!mStatus.isEmpty() && mStatus.equalsIgnoreCase("1")) {
            tvPayment.setText("Complete");
            tvPay.setVisibility(View.GONE);
        }
    }

    private void init(View view) {
        Bundle b = getArguments();
        mProductName = b.getString("product");
        mProductId = b.getString("product_id");
        mPrice = b.getString("price");
        mQuantity = b.getString("quantity");
        mTotalprice = b.getString("totalprice");
        mImage = b.getString("image");
        mCurrencyType = b.getString("currency_type");
        mStatus = b.getString("status");
        mUserId = CSPreferences.readString(getActivity(), Constants.USERID);

        back = view.findViewById(R.id.back);
        tvPruduct = view.findViewById(R.id.tv_product);
        tvPrice = view.findViewById(R.id.tv_price);
        tvPrices = view.findViewById(R.id.tv_prices);
        tvTotalPrice = view.findViewById(R.id.tv_totalprice);
        tvTotalPrices = view.findViewById(R.id.tv_totalprices);
        tvQuantity = view.findViewById(R.id.tv_quntity);
        tvTotalPrice = view.findViewById(R.id.tv_totalprice);
        tvAddAuction = view.findViewById(R.id.tv_add_auction);
        productImage = view.findViewById(R.id.product_image);
        tvPayment = view.findViewById(R.id.tv_payment);
        tvPay = view.findViewById(R.id.tv_pay);


        setData();
    }

    private void setData() {
        tvPrices.append(" ( in " + Utils.getCurrencySymbol(mCurrencyType) + "  )");
        tvTotalPrices.append(" ( in " + Utils.getCurrencySymbol(mCurrencyType) + "  )");
        handler = new ShoppingFragmentPresenter(this, tvPayment, tvPay);
        tvPruduct.setText(mProductName);
        tvPrice.setText(mPrice);
        tvQuantity.setText(mQuantity);
        tvTotalPrice.setText(mTotalprice);
        Picasso.with(getActivity())
                .load(mImage)
                .placeholder(R.mipmap.ic_launcher)
                .into(productImage);

        if (!CSPreferences.readString(getActivity(), Constants.SELECTEDCARDNUMBER).isEmpty() && CSPreferences.readString(getActivity(), Constants.SELECTEDCARDNUMBER) != null) {
            mCardNumber = CSPreferences.readString(getActivity(), Constants.SELECTEDCARDNUMBER);
        }
    }

    public void showAlert(String title, String message) {
        dialog = new AlertDialog.Builder(getActivity());
        view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_alert, null);
        dialog.setView(view);

        final AlertDialog alertDialog = dialog.create();
        tvAlertTitle = (TextView) view.findViewById(R.id.tv_alert);
        tvAlertDescription = (TextView) view.findViewById(R.id.tv_message);
        tvAlertTitle.setText(title);
        tvAlertDescription.setText(message);
        ok = (Button) view.findViewById(R.id.ok);
        cancel = (Button) view.findViewById(R.id.cancel);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.pendingPayment(getActivity(), "pendingPaymentComplete", mUserId, mProductId, "1", "",mCardNumber);
                alertDialog.dismiss();
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

            case R.id.tv_pay:
                showAlert(getResources().getString(R.string.payment) + "", "Do you want to pay this product ?" + "");
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
