package bidding.app.view.adapter;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import bidding.app.R;
import bidding.app.model.AllAuctionDetailsBean;
import bidding.app.view.fragment.WinnerDetails;

/**
 * Created by Thakur on 12/20/2017.
 */

public class AllAuctionDetailsAdapter extends RecyclerView.Adapter<AllAuctionDetailsAdapter.ViewHolder> {
    View view;
    Activity activity;
    List<AllAuctionDetailsBean.AuctionDetail> auctionDetail;
    private WinnerDetails fragment;

    public AllAuctionDetailsAdapter(Activity activity, List<AllAuctionDetailsBean.AuctionDetail> auctionDetail) {
        this.activity = activity;
        this.auctionDetail = auctionDetail;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_viewhistory, parent, false);
        return new AllAuctionDetailsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        if (auctionDetail.get(position).getWinner().equalsIgnoreCase("0")) {
            holder.status.setText("Pending");
        } else {
            holder.status.setText("Sold");
        }
        holder.totalprice.setText(Integer.parseInt(auctionDetail.get(position).getQuantity()) * Integer.parseInt(auctionDetail.get(position).getReservePrice()) + "");
        holder.unitprice.setText(auctionDetail.get(position).getReservePrice());
        holder.quantity.setText(auctionDetail.get(position).getQuantity());
        holder.product.setText(auctionDetail.get(position).getAuctionTitle());
        holder.Auctionid.setText(auctionDetail.get(position).getAuctionId());


        holder.main_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!auctionDetail.get(position).getWinner().equalsIgnoreCase("0")) {
                    Bundle args = new Bundle();
                    args.putString("winner_id", auctionDetail.get(position).getWinner());
                    fragment = new WinnerDetails();
                    fragment.setArguments(args);
                    FragmentTransaction fragmentTransaction = activity.getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.framlayout, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                } else {
                    Toast.makeText(activity, "No winner for yet.", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return auctionDetail.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView status, totalprice, unitprice, quantity, product, Auctionid;
        LinearLayout main_view;

        public ViewHolder(View itemView) {
            super(itemView);
            main_view = itemView.findViewById(R.id.main_view);
            status = itemView.findViewById(R.id.txtview);
            totalprice = itemView.findViewById(R.id.txtviewone);
            unitprice = itemView.findViewById(R.id.txtviewtwo);
            quantity = itemView.findViewById(R.id.txtviewthree);
            product = itemView.findViewById(R.id.txtviewfour);
            Auctionid = itemView.findViewById(R.id.txtviewfive);
        }
    }
}
