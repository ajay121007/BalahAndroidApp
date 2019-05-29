package bidding.app.view.activity.sellerproductactivity.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import bidding.app.R;
import bidding.app.extra.Constants;
import bidding.app.model.SellerProductListBean;
import bidding.app.view.activity.sellerproductactivity.SellerProductActivity;

public class SellerProductListAdapter extends RecyclerView.Adapter<SellerProductListAdapter.ViewHolder> {
    View view;
    Activity activity;
    List<SellerProductListBean.ItemsBean> items;
    public final int TYPE_LOAD = 1;
    SellerProductListBean.ItemsBean itemsBean;
    ItemClickListner mlistner;


    public SellerProductListAdapter(SellerProductActivity activity, List<SellerProductListBean.ItemsBean> items, ItemClickListner mListner) {
        this.activity = activity;
        this.items = items;
        this.mlistner=mListner;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new SellerProductListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        itemsBean = items.get(position);

        holder.tvPrice.setText(itemsBean.getPrice() + "");
        holder.tvName.setText(itemsBean.getName() + "");

        Log.d("Image", itemsBean.getImage() + "");
        if (itemsBean.getImage() != null) {
            Picasso.with(activity)
                    .load(Constants.IMAGE_BASE_URL + itemsBean.getImage())
                    .placeholder(R.drawable.loading)
                    .into(holder.imageView);
        } else {
            Picasso.with(activity)
                    .load("Error")
                    .placeholder(R.drawable.loading)
                    .into(holder.imageView);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mlistner.OnClickItem(position, itemsBean.getId(), itemsBean.getName());

            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_LOAD;

    }

    public void customNotify(List<SellerProductListBean.ItemsBean> list) {
        items = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvPrice, tvName;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.product_image);
            tvName = itemView.findViewById(R.id.text_price);
            tvPrice = itemView.findViewById(R.id.text_name);

        }
    }

    public interface ItemClickListner {
        void OnClickItem(int position, String id, String name);

    }
}
