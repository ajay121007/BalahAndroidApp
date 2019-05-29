package bidding.app.view.activity.homeactivity.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.zip.Inflater;

import bidding.app.R;
import bidding.app.view.activity.homeactivity.HomeActivity;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.ViewHolder> {
    View view;
    HomeActivity homeActivity;
    Integer[] images;

    public OfferAdapter(HomeActivity homeActivity, Integer[] images) {
        this.homeActivity = homeActivity;
        this.images = images;
    }

    @Override
    public OfferAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_list_offer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OfferAdapter.ViewHolder holder, int position) {
        holder.ivOffer.setImageResource(images[position]);

    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivOffer;

        public ViewHolder(View itemView) {
            super(itemView);
            ivOffer = itemView.findViewById(R.id.iv_offer);
        }
    }
}
