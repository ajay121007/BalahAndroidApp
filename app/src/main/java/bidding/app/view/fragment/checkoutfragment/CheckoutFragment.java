package bidding.app.view.fragment.checkoutfragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import bidding.app.R;
import bidding.app.extra.CSPreferences;
import bidding.app.extra.Constants;
import bidding.app.model.CartItems;
import bidding.app.view.activity.addnewaddress.NewAddressActivity;
import bidding.app.view.activity.changeaddress.ChangeAddressActivity;
import bidding.app.view.activity.paymentactivity.CustomActivity;
import bidding.app.view.fragment.BaseFragment;
import bidding.app.view.fragment.checkoutfragment.adapter.CartListAdapter;
import bidding.app.view.fragment.checkoutfragment.presenter.CheckOutFragmentPresenter;
import bidding.app.view.fragment.checkoutfragment.presenter.CheckOutFragmentPresenterHandler;
import bidding.app.view.fragment.checkoutfragment.request.ShippingCostEstReq;
import bidding.app.view.fragment.checkoutfragment.request.UpdateCartRequest;
import bidding.app.view.fragment.checkoutfragment.view.CheckOutView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by vijay on 18/8/17.
 */

public class CheckoutFragment extends BaseFragment implements CheckOutView, CartListAdapter.AdapterClick, View.OnClickListener, View.OnTouchListener {
    View view;
    public static TextView tvChangeAddress;
    @BindView(R.id.tv_fullname)
    TextView tvFullname;
    @BindView(R.id.tv_address1)
    TextView tvAddress1;
    @BindView(R.id.tv_address2)
    TextView tvAddress2;
    @BindView(R.id.tv_address3)
    TextView tvAddress3;
    @BindView(R.id.tv_mobile)
    TextView tvMobile;
    @BindView(R.id.ll_delivery_address)
    LinearLayout llDeliveryAddress;
    @BindView(R.id.rv_cart_item)
    RecyclerView rvCartItem;
    Unbinder unbinder;
    String mToken = "";
    double shippingCharge = 0;
    CheckOutFragmentPresenterHandler handler;
    boolean isBillingAddres = false;
    CartListAdapter.AdapterClick adapterClick;
    LinearLayoutManager layoutManager;
    CartListAdapter listAdapter;
    List<CartItems> itemsList = new ArrayList<>();
    List<CartItems> cartItem;
    ShippingCostEstReq.AddressBean request;
    ShippingCostEstReq costEstReq;
    TextView tvGrandTotal;
    Button btnSelectAddress;
    LinearLayout llGrandTotal;
    WindowManager.LayoutParams layoutParams;
    RelativeLayout bottomSheet;
    BottomSheetBehavior sheetBehavior;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    TextView tvTotal, tvShippingCharge, tvNetTotal;
    @BindView(R.id.scrollView)
    ScrollView scrollView;


    public CheckoutFragment() {
        // Required empty public constructor
    }

    public static CheckoutFragment newInstance() {
        return new CheckoutFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.content_checkout, container, false);
        // Inflate the layout for this fragment
        unbinder = ButterKnife.bind(this, view);
        initviews();
        listner();
        return view;
    }

    private void listner() {
        llGrandTotal.setOnClickListener(this);
        btnSelectAddress.setOnClickListener(this);
        scrollView.setOnTouchListener(this);


    }

    private void initviews() {


        bottomSheet = view.findViewById(R.id.bottom_sheet);
        sheetBehavior = BottomSheetBehavior.from(bottomSheet);
        tvTotal = bottomSheet.findViewById(R.id.tv_total);
        tvShippingCharge = bottomSheet.findViewById(R.id.tv_shipping_charge);
        tvNetTotal = bottomSheet.findViewById(R.id.tv_net_total);
        llGrandTotal = getActivity().findViewById(R.id.ll_total);
        tvGrandTotal = getActivity().findViewById(R.id.tv_grand_total);
        btnSelectAddress = getActivity().findViewById(R.id.btn_select_address);


        adapterClick = this;
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvCartItem.setLayoutManager(layoutManager);
        mToken = CSPreferences.readString(getActivity(), Constants.TOKEN);
        handler = new CheckOutFragmentPresenter(this, getActivity(), itemsList);
        handler.getBillingAddres(getActivity(), "Bearer " + mToken);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
    public void cartList(List<CartItems> cartItem) {
        this.cartItem = cartItem;
        listAdapter = new CartListAdapter(getActivity(), cartItem, adapterClick, 0);
        rvCartItem.setAdapter(listAdapter);

        double price = 0, subTotal = 0, grandZTotal = 0, totalWithShipping = 0;
        int quntity = 0;

        for (int i = 0; i < cartItem.size(); i++) {

            quntity = cartItem.get(i).getQty();
            price = cartItem.get(i).getPrice();

            subTotal = price * quntity;
            grandZTotal = grandZTotal + subTotal;
        }

        tvTotal.setText(grandZTotal + "");
        totalWithShipping = grandZTotal + shippingCharge;
        tvNetTotal.setText(totalWithShipping + "");
        tvGrandTotal.setText(totalWithShipping + "");

    }

    @Override
    public void updateCartSuccess(int postion, CartItems response) {
        cartItem.remove(postion);
        cartItem.add(postion, response);
        listAdapter.notifyItemChanged(postion);

        double price = 0, subTotal = 0, grandZTotal = 0, totalWithShipping = 0;
        int quntity = 0;

        for (int i = 0; i < cartItem.size(); i++) {

            quntity = cartItem.get(i).getQty();
            price = cartItem.get(i).getPrice();

            subTotal = price * quntity;
            grandZTotal = grandZTotal + subTotal;
        }

        tvTotal.setText(grandZTotal + "");
        totalWithShipping = grandZTotal + shippingCharge;
        tvNetTotal.setText(totalWithShipping + "");
        tvGrandTotal.setText(totalWithShipping + "");

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setDefaultAddress(String response) {

        if (response.isEmpty()) {
            llDeliveryAddress.setVisibility(View.GONE);
            handler.getCartList("Bearer " + mToken);
        } else {
            llDeliveryAddress.setVisibility(View.VISIBLE);

            try {
                JSONObject jsonObject = new JSONObject(response);
                int customerId = jsonObject.getInt("customer_id");
                JSONObject object = jsonObject.getJSONObject("region");
                String regionCode = object.getString("region_code");
                String region = object.getString("region_code");
                int region_id = object.getInt("region_id");
                String countryId = jsonObject.getString("country_id");
                JSONArray jsonArray = jsonObject.getJSONArray("street");
                List<String> streetList = new ArrayList<>();
                String street = "";
                for (int i = 0; i < jsonArray.length(); i++) {
                    street = jsonArray.getString(i);
                    streetList.add(street);
                }

                String telephone = jsonObject.getString("telephone");
                String postCode = jsonObject.getString("postcode");
                String city = jsonObject.getString("city");
                String firstname = jsonObject.getString("firstname");
                String lastname = jsonObject.getString("lastname");

                costEstReq = new ShippingCostEstReq();
                request = new ShippingCostEstReq.AddressBean();
                request.setRegion(region);
                request.setRegion_id(region_id);
                request.setRegion_code(regionCode);
                request.setCountry_id(countryId);
                request.setStreet(streetList);
                request.setPostcode(postCode);
                request.setCity(city);
                request.setFirstname(firstname);
                request.setLastname(lastname);
                request.setCustomer_id(customerId);
                request.setEmail("akshat@gmail.com");
                request.setTelephone(telephone);
                request.setSame_as_billing(1);
                costEstReq.setAddress(request);

                tvFullname.setText(firstname + " " + lastname + " ");
                tvAddress1.setText(street + " ");
                tvAddress2.setText(city + " ");
                tvAddress3.setText(postCode + " " + countryId + " ");
                tvMobile.setText(getString(R.string.mobiles) + telephone + "");
                isBillingAddres = true;
                btnSelectAddress.setText(R.string.proceed_to_pay);

                handler.shippingCostEstimation(getActivity(), "Bearer " + mToken, costEstReq);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void setShippingCharge(double amount) {
        shippingCharge = amount;
        tvShippingCharge.setText(shippingCharge + "");
        handler.getCartList("Bearer " + mToken);

    }

    @Override
    public void onSuccessfullyItemDel(String response, int position) {
        if (response == "true") {

            cartItem.remove(position);
            listAdapter.customNotify(cartItem);

            Log.d("ListSize", cartItem.size() + "");

            double price = 0, subTotal = 0, grandZTotal = 0, totalWithShipping = 0;
            int quntity = 0;

            if (cartItem.size() > 0) {

                for (int i = 0; i < cartItem.size(); i++) {

                    quntity = cartItem.get(i).getQty();
                    price = cartItem.get(i).getPrice();

                    subTotal = price * quntity;
                    grandZTotal = grandZTotal + subTotal;
                }
                tvTotal.setText(grandZTotal + "");
                totalWithShipping = grandZTotal + shippingCharge;
                tvNetTotal.setText(totalWithShipping + "");
                tvGrandTotal.setText(totalWithShipping + "");
            } else {
                tvShippingCharge.setText(0.0 + "");
                tvTotal.setText(grandZTotal + "");
                totalWithShipping = grandZTotal + 0;
                tvNetTotal.setText(totalWithShipping + "");
                tvGrandTotal.setText(totalWithShipping + "");
            }


        }
    }


    @Override
    public void updateQuntity(int postion, UpdateCartRequest request) {
        handler.updateCart(getActivity(), postion, "Bearer " + mToken, request);

    }

    @Override
    public void deleteItem(int position, int id) {
        handler.deleteItem(getActivity(), "Bearer " + mToken, id, position);

        Log.d("ID", "   " + id + "   " + "Position     " + position);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_total:
                toggleBottomSheet();
                break;
            case R.id.btn_select_address:
                if (isBillingAddres)
                    getActivity().startActivity(new Intent(getActivity(), CustomActivity.class).putExtra(Constants.GRANDTOTAL, tvGrandTotal.getText().toString()));
                else
                    getActivity().startActivity(new Intent(getActivity(), NewAddressActivity.class));
                break;
        }
    }

    public void toggleBottomSheet() {
        if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    @OnClick({R.id.tv_change_address, R.id.iv_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_change_address:
                startActivity(new Intent(getActivity(), ChangeAddressActivity.class));
                break;

            case R.id.iv_close:
                toggleBottomSheet();
                break;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            if (sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
            return true;
        }

        return false;
    }

}
