package bidding.app.view.adapter;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import bidding.app.R;
import bidding.app.model.CartBeans;
import bidding.app.view.fragment.shoppingfragment.ShoppingFragment;

/**
 * Created by vijay on 21/8/17.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyviewHolder> {
    private List<CartBeans> carlist;
    private ShoppingFragment fragment;
    private Activity activity;

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView status, totalprice, unitprice, quantity, product, Auctionid;
        LinearLayout mainView;

        public MyviewHolder(View View) {
            super(View);
            status = (TextView) View.findViewById(R.id.txtview);
            totalprice = (TextView) View.findViewById(R.id.txtviewone);
            unitprice = (TextView) View.findViewById(R.id.txtviewtwo);
            quantity = (TextView) View.findViewById(R.id.txtviewthree);
            product = (TextView) View.findViewById(R.id.txtviewfour);
            Auctionid = (TextView) View.findViewById(R.id.txtviewfive);
            mainView = View.findViewById(R.id.main_view);
        }
    }

    public CartAdapter(Activity activity, List<CartBeans> carlist) {
        this.activity = activity;
        this.carlist = carlist;
    }

    @Override
    public CartAdapter.MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_viewhistory, parent, false);
        return new CartAdapter.MyviewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(CartAdapter.MyviewHolder holder, int position) {
        final CartBeans cartBeans = carlist.get(position);
        if (cartBeans.getStatus().equalsIgnoreCase("0")) {
            holder.status.setText("pending");
        } else if (cartBeans.getStatus().equalsIgnoreCase("1")) {
            holder.status.setText("complete");
        }
        holder.totalprice.setText(cartBeans.getTotalPrice());
        holder.unitprice.setText(cartBeans.getUnitPrice());
        holder.quantity.setText(cartBeans.getQuantity());
        holder.product.setText(cartBeans.getProduct());
        holder.Auctionid.setText(cartBeans.getAuctionID());
        holder.mainView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle args = new Bundle();
                args.putString("product_id", cartBeans.getAuctionID());
                args.putString("product", cartBeans.getProduct());
                args.putString("price", cartBeans.getUnitPrice());
                args.putString("quantity", cartBeans.getQuantity());
                args.putString("totalprice", cartBeans.getTotalPrice());
                args.putString("image", cartBeans.getImage());
                args.putString("currency_type", cartBeans.getCurrency_type());
                args.putString("status", cartBeans.getStatus());
                fragment = new ShoppingFragment();
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
        Log.e("listsize", carlist.size() + "");
        return carlist.size();
    }


}
