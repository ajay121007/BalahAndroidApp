package bidding.app.view.fragment.historyfragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import bidding.app.R;
import bidding.app.controller.CartManager;
import bidding.app.controller.HistoryManager;
import bidding.app.controller.ModelManager;
import bidding.app.model.ActionString;
import bidding.app.model.AllAuctionDetailsBean;
import bidding.app.extra.CSPreferences;
import bidding.app.extra.Constants;
import bidding.app.extra.Event;
import bidding.app.model.HistoryBean;
import bidding.app.extra.Operations;
import bidding.app.model.InventoryBean;
import bidding.app.view.adapter.AllAuctionDetailsAdapter;
import bidding.app.view.adapter.CartAdapter;
import bidding.app.view.adapter.HistoryAdapter;
import bidding.app.view.adapter.InventoryAdapter;
import bidding.app.view.fragment.BaseFragment;
import bidding.app.view.fragment.Product_Fragment;
import bidding.app.view.fragment.historyfragment.presenter.HistoryFragmentPresenter;
import bidding.app.view.fragment.historyfragment.presenter.HistoryFragmentPresenterHandler;
import bidding.app.view.fragment.historyfragment.view.HistoryFragmentView;

/**
 * Created by vijay on 18/8/17.
 */

public class History_Fragment extends BaseFragment implements HistoryFragmentView, View.OnClickListener {
    View view1;
    TextView text_cart, text_history, all_auction, tvInventory;
    private List<HistoryBean> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private HistoryAdapter mAdapter;
    private CartAdapter cartadapter;
    private InventoryAdapter inventoryAdapter;
    ProgressDialog progressDialog;
    private AllAuctionDetailsAdapter auctionDetailsAdapter;
    List<AllAuctionDetailsBean.AuctionDetail> list;
    HistoryFragmentPresenterHandler handler;
    String state = "0";


    public History_Fragment() {
        // Required empty public constructor
    }

    public static History_Fragment newInstance() {
        return new History_Fragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view1 = inflater.inflate(R.layout.historyfragment, container, false);
        initviews();
        listner();
        // Inflate the layout for this fragment
        return view1;
    }

    private void listner() {
        tvInventory.setOnClickListener(this);
        all_auction.setOnClickListener(this);
        text_history.setOnClickListener(this);
        text_cart.setOnClickListener(this);
    }

    private void initviews() {
        handler = new HistoryFragmentPresenter(this);
        progressDialog = new ProgressDialog(getActivity());
        text_cart = (TextView) view1.findViewById(R.id.text_cart);
        text_history = (TextView) view1.findViewById(R.id.text_history);
        recyclerView = (RecyclerView) view1.findViewById(R.id.recycler);
        all_auction = view1.findViewById(R.id.all_auction);
        tvInventory = view1.findViewById(R.id.tv_inventory);
        hideProgressDialog();

        if (CSPreferences.readString(getActivity(), Constants.STATE) != null && !CSPreferences.readString(getActivity(), Constants.STATE).isEmpty()) {
            state = CSPreferences.readString(getActivity(), Constants.STATE);
            if (state.equalsIgnoreCase("1")) {
                hitCartService();

            } else if (state.equalsIgnoreCase("2")) {
                hitServiceHistory();

            } else if (state.equalsIgnoreCase("3")) {
                hitAllAuction();

            } else if (state.equalsIgnoreCase("4")) {
                hitInventory();
            }
        } else {
            CSPreferences.putString(getActivity(), Constants.STATE, "1");
            hitCartService();
        }


    }

    private void hitAllAuction() {
        all_auction.setTextColor(getResources().getColor(R.color.light_yellow));
        text_history.setTextColor(getResources().getColor(R.color.black));
        text_cart.setTextColor(getResources().getColor(R.color.black));
        tvInventory.setTextColor(getResources().getColor(R.color.black));

        all_auction.setBackgroundResource(R.drawable.btn_active);
        text_history.setBackgroundResource(R.drawable.btn_inactive);
        text_cart.setBackgroundResource(R.drawable.btn_inactive);
        tvInventory.setBackgroundResource(R.drawable.btn_inactive);
        showProgressDialog();
        ModelManager.getInstance().getAllAuctionManger().AllAuctionManger(getActivity(),
                Operations.getAllAuctionDetail(getActivity(), "auction_detail", CSPreferences.readString(getActivity(), "user_id")));

    }

    private void hitServiceHistory() {

        all_auction.setTextColor(getResources().getColor(R.color.black));
        text_history.setTextColor(getResources().getColor(R.color.light_yellow));
        text_cart.setTextColor(getResources().getColor(R.color.black));
        tvInventory.setTextColor(getResources().getColor(R.color.black));

        text_history.setBackgroundResource(R.drawable.btn_active);
        text_cart.setBackgroundResource(R.drawable.btn_inactive);
        all_auction.setBackgroundResource(R.drawable.btn_inactive);
        tvInventory.setBackgroundResource(R.drawable.btn_inactive);
        showProgressDialog();
        ModelManager.getInstance().getHistoryManager().HistoryManager(getActivity(),
                Operations.getHistoryDetail(getActivity(), ActionString.gethistory_action, CSPreferences.readString(getActivity(), "user_id")));

    }

    private void hitInventory() {
        all_auction.setTextColor(getResources().getColor(R.color.black));
        text_history.setTextColor(getResources().getColor(R.color.black));
        text_cart.setTextColor(getResources().getColor(R.color.black));
        tvInventory.setTextColor(getResources().getColor(R.color.light_yellow));

        all_auction.setBackgroundResource(R.drawable.btn_inactive);
        text_history.setBackgroundResource(R.drawable.btn_inactive);
        text_cart.setBackgroundResource(R.drawable.btn_inactive);
        tvInventory.setBackgroundResource(R.drawable.btn_active);
        handler.getInventory(getActivity(), "fetchInventoryRecord", CSPreferences.readString(getActivity(), Constants.USERID));
    }

    private void hitCartService() {

        all_auction.setTextColor(getResources().getColor(R.color.black));
        text_history.setTextColor(getResources().getColor(R.color.black));
        text_cart.setTextColor(getResources().getColor(R.color.light_yellow));
        tvInventory.setTextColor(getResources().getColor(R.color.black));

        text_cart.setBackgroundResource(R.drawable.btn_active);
        text_history.setBackgroundResource(R.drawable.btn_inactive);
        all_auction.setBackgroundResource(R.drawable.btn_inactive);
        tvInventory.setBackgroundResource(R.drawable.btn_inactive);
        showProgressDialog();
        ModelManager.getInstance().getCartManager().CartManager(getActivity(),
                Operations.getCartProduct(getActivity(), ActionString.cartproductaction, CSPreferences.readString(getActivity(), "user_id")));

    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        switch (event.getKey()) {
            case Constants.CONECTED:
                if (event.isConnected()) {
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.str_internet_check), Toast.LENGTH_SHORT).show();
                }
                break;
            case Constants.Historystatus:
                hideProgressDialog();
                if (HistoryManager.list.size() == 0) {
                    recyclerView.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "No item found.", Toast.LENGTH_SHORT).show();
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    mAdapter = new HistoryAdapter(getActivity(), HistoryManager.list);
                    RecyclerView.LayoutManager mlayoutmanger = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(mlayoutmanger);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(mAdapter);
                }

                break;
            case Constants.CARTSTATUS:
                hideProgressDialog();
                if (CartManager.list.size() == 0) {
                    recyclerView.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "No item found.", Toast.LENGTH_SHORT).show();
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    cartadapter = new CartAdapter(getActivity(), CartManager.list);
                    RecyclerView.LayoutManager mlayoutmanger1 = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(mlayoutmanger1);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(cartadapter);
                }
                break;

            case Constants.SERVER_ERROR:
                hideProgressDialog();
                recyclerView.setVisibility(View.GONE);
                Toast.makeText(getActivity(), getResources().getString(R.string.tryagain), Toast.LENGTH_SHORT).show();
                break;

            case Constants.ALLAUCTION_RESULT:
                hideProgressDialog();
                recyclerView.setVisibility(View.VISIBLE);
                list = ModelManager.getInstance().getAllAuctionManger().bean.getResponse().getAuctionDetail();
                auctionDetailsAdapter = new AllAuctionDetailsAdapter(getActivity(), list);
                RecyclerView.LayoutManager layoutmanger = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutmanger);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(auctionDetailsAdapter);
                break;

            case Constants.NO_ITEM:
                hideProgressDialog();
                recyclerView.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "No item found.", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_inventory:
                state = "4";
                CSPreferences.putString(getActivity(), Constants.STATE, state);
                hitInventory();
                break;

            case R.id.text_cart:
                state = "1";
                CSPreferences.putString(getActivity(), Constants.STATE, state);
                hitCartService();
                break;

            case R.id.text_history:
                state = "2";
                CSPreferences.putString(getActivity(), Constants.STATE, state);
                hitServiceHistory();
                break;

            case R.id.all_auction:
                state = "3";
                CSPreferences.putString(getActivity(), Constants.STATE, state);
                hitAllAuction();
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
    public void inventorySuccess(List<InventoryBean.Datum> list) {
        if (list.size() == 0) {
            recyclerView.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "No item found.", Toast.LENGTH_SHORT).show();
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            inventoryAdapter = new InventoryAdapter(getActivity(), list);
            RecyclerView.LayoutManager mlayoutmanger1 = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(mlayoutmanger1);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(inventoryAdapter);
        }
    }
}
