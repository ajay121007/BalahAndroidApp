package bidding.app.view.activity.wonauction.adapter;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import bidding.app.R;
import bidding.app.model.HistoryBean;
import bidding.app.model.MyAuction;
import bidding.app.model.WonAuction;
import bidding.app.view.activity.wondetails.WonDetailActivity;
import bidding.app.view.adapter.HistoryAdapter;
import bidding.app.view.fragment.WinnerDetails;
import bidding.app.view.fragment.purchasefragment.PurchaseFragment;

public class WonAuctionsAdapter extends RecyclerView.Adapter<WonAuctionsAdapter.MyviewHolder> {
    List<WonAuction.Datum> data;
    private PurchaseFragment fragment;
    private Activity activity;


    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView status, totalprice, unitprice, quantity, product, Auctionid;
        LinearLayout mainview;

        public MyviewHolder(View View) {
            super(View);
            mainview = View.findViewById(R.id.main_view);
            status = View.findViewById(R.id.txtview);
            totalprice = View.findViewById(R.id.txtviewone);
            unitprice = View.findViewById(R.id.txtviewtwo);
            quantity = View.findViewById(R.id.txtviewthree);
            product = View.findViewById(R.id.txtviewfour);
            Auctionid = View.findViewById(R.id.txtviewfive);
        }
    }

    public WonAuctionsAdapter(Activity activity, List<WonAuction.Datum> data) {
        this.data = data;
        this.activity = activity;
    }

    @Override
    public WonAuctionsAdapter.MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_viewhistory, parent, false);
        return new WonAuctionsAdapter.MyviewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(WonAuctionsAdapter.MyviewHolder holder, final int position) {
        if (data.get(position).getStatus().equalsIgnoreCase("0")) {
            holder.status.setText("pending");
        } else if (data.get(position).getStatus().equalsIgnoreCase("1")) {
            holder.status.setText("complete");
        }
        holder.totalprice.setText(data.get(position).getTotalPrice());
        holder.unitprice.setText(data.get(position).getUnitPrice());
        holder.quantity.setText(data.get(position).getProductQuantity());
        holder.product.setText(data.get(position).getProductName());
        holder.Auctionid.setText(data.get(position).getAuctionId());
        holder.mainview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("auctionOwnerId", data.get(position).getAuctionOwnerId());
                args.putString("auctionId", data.get(position).getAuctionId());
                args.putString("status", data.get(position).getStatus());
                args.putString("payment_status", data.get(position).getStatus());
                args.putString("product_id", data.get(position).getProductId());
                activity.startActivity(new Intent(activity, WonDetailActivity.class).putExtras(args));
            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }


}
