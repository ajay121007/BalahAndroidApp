package bidding.app.view.activity.wishlistactivity.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import bidding.app.R;
import bidding.app.extra.Constants;
import bidding.app.model.WishListBean;
import bidding.app.view.activity.wishlistactivity.WishListActivity;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.ViewHolder> {
    View view;
    Activity activity;
    List<String> title;
    int selectedPosition = -1;
    ItemClick clickListner;
    List<WishListBean.ResponseBean.DataBean> data;
    WishListBean.ResponseBean.DataBean dataBean;


    public WishListAdapter(WishListActivity activity, List<WishListBean.ResponseBean.DataBean> data, ItemClick mClickListenr) {
        this.activity = activity;
        this.data = data;
        this.clickListner = mClickListenr;
    }


    @Override
    public WishListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_wishlist, parent, false);
        return new WishListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WishListAdapter.ViewHolder holder, final int position) {

        dataBean = data.get(position);
        DecimalFormat format = new DecimalFormat("##.00");

        Picasso.with(activity)
                .load(Constants.IMAGE_BASE_URL + dataBean.getProduct().getImage())
                .placeholder(R.drawable.loading)
                .into(holder.imageView);

        holder.tvProductName.setText(dataBean.getProduct().getName());
        holder.tvPrice.setText(format.format(Double.parseDouble(dataBean.getProduct().getPrice())));

        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListner.OnItemClick(R.id.btnRemove, position, dataBean.getWishlist_item_id());
            }
        });

        holder.btnAddToBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListner.OnItemClick(R.id.btnAddToBag, position, dataBean.getWishlist_item_id());
            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void customNotify(int position) {
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvProductName, tvSellerName, tvInStock, tvPrice;
        Button btnRemove, btnAddToBag;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvSellerName = itemView.findViewById(R.id.tvSellerName);
            tvInStock = itemView.findViewById(R.id.tvInstock);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            btnRemove = itemView.findViewById(R.id.btnRemove);
            btnAddToBag = itemView.findViewById(R.id.btnAddToBag);
            imageView = itemView.findViewById(R.id.ivProductImage);

        }
    }

    public interface ItemClick {
        void OnItemClick(int id, int position, String wishlist_id);
    }

}

