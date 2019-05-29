package bidding.app.view.fragment.checkoutfragment.adapter;

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
import bidding.app.model.CartItems;
import bidding.app.view.activity.checkout.CheckOutActivity;
import bidding.app.view.fragment.checkoutfragment.CheckoutFragment;
import bidding.app.view.fragment.checkoutfragment.request.CartItem;
import bidding.app.view.fragment.checkoutfragment.request.UpdateCartRequest;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {

    View view;
    Activity activity;
    List<CartItems> cartItem;
    AdapterClick listner;
    int Quntity = 1;
    public static double price = 0, subTotal = 0, grandZTotal = 0;


    public CartListAdapter(Activity activity, List<CartItems> cartItem, AdapterClick adapterClick, int i) {
        this.activity = activity;
        this.cartItem = cartItem;
        listner = adapterClick;
        grandZTotal = i;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        if (cartItem.get(position).getQty() == 1) {
            holder.ivDecrese.setBackgroundResource(R.drawable.ic_icon_remove);
        } else {
            holder.ivDecrese.setBackgroundResource(R.drawable.ic_icon_minus);
        }

        holder.title.setText(cartItem.get(position).getName());
        holder.tvDescription.setText(cartItem.get(position).getExtension_attributes().getShort_description());

        holder.tvQuntity.setText(cartItem.get(position).getQty() + "");
        Picasso.with(activity)
                .load(cartItem.get(position).getExtension_attributes().getImage_url())
                .placeholder(R.drawable.loading)
                .into(holder.productImage);


        Quntity = Integer.parseInt(holder.tvQuntity.getText().toString());
        price = cartItem.get(position).getPrice();

        holder.tvPrice.setText("SAR " + price + "");

        holder.ivIncrese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatecart(position, 1);
            }
        });
        holder.ivDecrese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("CartPostion", position + "");
                if (cartItem.get(position).getQty() == 1)
                    listner.deleteItem(position, cartItem.get(position).getItem_id());
                else
                    updatecart(position, -1);
            }
        });
    }


    @Override
    public int getItemCount() {
        return cartItem.size();
    }


    private void updatecart(int position, int quntity) {
        UpdateCartRequest request = new UpdateCartRequest();
        CartItem item = new CartItem();

        item.setItemId(cartItem.get(position).getItem_id());
        item.setQty(cartItem.get(position).getQty() + quntity);
        item.setQuoteId(cartItem.get(position).getQuote_id() + "");
        request.setCartItem(item);

        listner.updateQuntity(position, request);
        Log.d("Position", position + "");
    }

    public void customNotify(List<CartItems> cartItem) {
        this.cartItem = cartItem;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage, ivIncrese, ivDecrese;
        TextView title, tvPrice, tvQuntity, tvDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.iv_product);
            title = itemView.findViewById(R.id.tv_product_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvQuntity = itemView.findViewById(R.id.tv_quntity);
            tvDescription = itemView.findViewById(R.id.tv_description);
            ivIncrese = itemView.findViewById(R.id.iv_increment);
            ivDecrese = itemView.findViewById(R.id.iv_decrese);

        }
    }

    public interface AdapterClick {

        void updateQuntity(int postion, UpdateCartRequest request);

        void deleteItem(int position, int id);
    }
}