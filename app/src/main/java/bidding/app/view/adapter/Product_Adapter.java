package bidding.app.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import bidding.app.R;
import bidding.app.extra.ItemClickListener;
import bidding.app.model.ProductBean;

/**
 * Created by vijay on 18/8/17.
 */

public class Product_Adapter extends  RecyclerView.Adapter<Product_Adapter.ViewHolder> {
    ArrayList<ProductBean> list;



    public  Context context;


    public Product_Adapter(Context context,ArrayList<ProductBean>list) {
        super();
        this.context = context;

        this.list=list;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.custom_product, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        ProductBean productBean = list.get(i);


        viewHolder.nameText.setText(productBean.getProduct_name());
        viewHolder.namePrice.setText("$"+productBean.getPrice());
        Picasso.with(context)
                .load(productBean.getImage())
                .placeholder(R.drawable.loading)
                .into(viewHolder.imgThumbnail);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgThumbnail;
        public TextView nameText;
        public TextView namePrice;
        private ItemClickListener clickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            imgThumbnail = (ImageView) itemView.findViewById(R.id.product_image);
            nameText = (TextView) itemView.findViewById(R.id.text_name);
            namePrice = (TextView) itemView.findViewById(R.id.text_price);
           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {


               }
           });
        }


    }


}
