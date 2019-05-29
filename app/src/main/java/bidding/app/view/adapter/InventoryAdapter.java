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
import bidding.app.model.InventoryBean;
import bidding.app.view.fragment.storagefragment.StorageFragment;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.MyviewHolder> {
    private List<InventoryBean.Datum> data;
    private StorageFragment fragment;
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

    public InventoryAdapter(Activity activity, List<InventoryBean.Datum> historyBeanList) {
        this.data = historyBeanList;
        this.activity = activity;
    }

    @Override
    public MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_viewhistory, parent, false);
        return new MyviewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(MyviewHolder holder, int position) {
        final InventoryBean.Datum  inventoryBean = data.get(position);
        holder.status.setText("Complete");
        holder.totalprice.setText(inventoryBean.getTotalPrice());
        holder.unitprice.setText(inventoryBean.getUnitPrice());
        holder.quantity.setText(inventoryBean.getInventory());
        holder.product.setText(inventoryBean.getProductName());
        holder.Auctionid.setText(inventoryBean.getAuctionId());
        holder.mainview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("productid", inventoryBean.getProductId());
                args.putString("product", inventoryBean.getProductName());
                args.putString("price", inventoryBean.getUnitPrice());
                args.putString("quantity", inventoryBean.getInventory());
                args.putString("totalprice", inventoryBean.getTotalPrice());
                args.putString("auctionid", inventoryBean.getAuctionId());
                args.putString("currency_type", inventoryBean.getCurrencyType());
                fragment = new StorageFragment();
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
        return data.size();
    }
}