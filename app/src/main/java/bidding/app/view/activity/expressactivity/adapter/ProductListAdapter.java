package bidding.app.view.activity.expressactivity.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import bidding.app.R;
import bidding.app.extra.Constants;
import bidding.app.model.MyAuction;
import bidding.app.model.ProductData;
import bidding.app.model.ProductList;
import bidding.app.view.activity.productdetails.ProductActivity;
import bidding.app.view.activity.winnerdetail.WinnerDetailActivity;
import bidding.app.view.fragment.WinnerDetails;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {
    View view;
    Activity activity;
    List<ProductData> dataList;
    ArrayList<String> images = new ArrayList<>();
    public final int TYPE_PRODUCT = 0;
    public final int TYPE_LOAD = 1;

    public ProductListAdapter(Activity activity, List<ProductData> dataList) {
        this.activity = activity;
        this.dataList = dataList;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new ProductListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.tvPrice.setText(dataList.get(position).getPrice() + "");
        holder.tvName.setText(dataList.get(position).getName() + "");

        Log.d("Image", dataList.get(position).getImage() + "");
        if (dataList.get(position).getImage() != null) {
            Picasso.with(activity)
                    .load(Constants.IMAGE_BASE_URL + dataList.get(position).getImage())
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
                Log.d("ProductId", dataList.get(position).getId() + "");
                activity.startActivity(new Intent(activity, ProductActivity.class).putExtra("id", dataList.get(position).getId() + ""));
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
            return TYPE_LOAD;

    }

    public void customNotify(List<ProductData> list) {
        dataList = list;
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
}
