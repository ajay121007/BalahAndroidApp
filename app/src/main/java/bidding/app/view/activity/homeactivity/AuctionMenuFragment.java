package bidding.app.view.activity.homeactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import bidding.app.R;
import bidding.app.extra.CSPreferences;
import bidding.app.extra.Constants;
import bidding.app.model.WonAuction;
import bidding.app.view.activity.addauction.AddAuctionActivity;
import bidding.app.view.activity.mainactivity.MainActivity;
import bidding.app.view.activity.myauction.MyAuctionActivity;
import bidding.app.view.activity.wonauction.WonAuctionActivity;

public class AuctionMenuFragment extends Fragment implements View.OnClickListener {
    View view;
    LinearLayout llAddAuction, llLiveAuction, llMyAuction, llWonAuction;

    public AuctionMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate layout for this fragment
        view = inflater.inflate(R.layout.fragment_tab2, container, false);
        initView(view);
        listner();
        return view;
    }


    private void initView(View view) {
        llAddAuction = view.findViewById(R.id.ll_add_auction);
        llLiveAuction = view.findViewById(R.id.ll_live_auction);
        llMyAuction = view.findViewById(R.id.ll_my_auction);
        llWonAuction = view.findViewById(R.id.ll_won_auction);
    }

    private void listner() {
        llAddAuction.setOnClickListener(this);
        llLiveAuction.setOnClickListener(this);
        llMyAuction.setOnClickListener(this);
        llWonAuction.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_add_auction:
                if (CSPreferences.readString(getActivity(), Constants.ISVENDOR).equalsIgnoreCase("1"))
                    startActivity(new Intent(getActivity(), AddAuctionActivity.class));
                else
                    Toast.makeText(getActivity(), R.string.register_as_seller, Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_live_auction:
                startActivity(new Intent(getActivity(), MainActivity.class));
                break;
            case R.id.ll_my_auction:
                startActivity(new Intent(getActivity(), MyAuctionActivity.class));
                break;
            case R.id.ll_won_auction:
                startActivity(new Intent(getActivity(), WonAuctionActivity.class));
                break;

        }
    }
}