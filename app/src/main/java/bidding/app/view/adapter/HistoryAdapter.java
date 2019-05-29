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

import java.util.List;

import bidding.app.R;
import bidding.app.model.HistoryBean;
import bidding.app.view.fragment.purchasefragment.PurchaseFragment;

/**
 * Created by rishav on 12/8/17.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyviewHolder> {
    private List<HistoryBean> historyBeanList;
    private PurchaseFragment fragment;
    private Activity activity;


    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView status, totalprice, unitprice, quantity, product, Auctionid;
        LinearLayout mainview;

        public MyviewHolder(View View) {
            super(View);
            mainview = View.findViewById(R.id.main_view);
            status = (TextView) View.findViewById(R.id.txtview);
            totalprice = (TextView) View.findViewById(R.id.txtviewone);
            unitprice = (TextView) View.findViewById(R.id.txtviewtwo);
            quantity = (TextView) View.findViewById(R.id.txtviewthree);
            product = (TextView) View.findViewById(R.id.txtviewfour);
            Auctionid = (TextView) View.findViewById(R.id.txtviewfive);
        }
    }

    public HistoryAdapter(Activity activity, List<HistoryBean> historyBeanList) {
        this.historyBeanList = historyBeanList;
        this.activity=activity;
    }

    @Override
    public MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_viewhistory, parent, false);
        return new MyviewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(MyviewHolder holder, int position) {
        final HistoryBean history = historyBeanList.get(position);
        if (history.getStatus().equalsIgnoreCase("0")) {
            holder.status.setText("pending");
        } else if (history.getStatus().equalsIgnoreCase("1")) {
            holder.status.setText("complete");
        }
        holder.totalprice.setText(history.getTotalPrice());
        holder.unitprice.setText(history.getUnitPrice());
        holder.quantity.setText(history.getQuantity());
        holder.product.setText(history.getProduct());
        holder.Auctionid.setText(history.getAuctionID());
        holder.mainview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("auctionOwnerId", history.getAuctionOwnerId());
                args.putString("auctionId", history.getAuctionID());
                args.putString("status", history.getStatus());
                args.putString("payment_status", history.getPaymentStatus());
                args.putString("product_id", history.getProductId());
                fragment = new PurchaseFragment();
                fragment.setArguments(args);
                FragmentTransaction fragmentTransaction = activity.getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.framlayout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


    }

    @Override
    public int getItemCount() {
        return historyBeanList.size();
    }


}
