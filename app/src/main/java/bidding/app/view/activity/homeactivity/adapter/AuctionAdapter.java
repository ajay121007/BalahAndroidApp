package bidding.app.view.activity.homeactivity.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import bidding.app.R;
import bidding.app.view.activity.homeactivity.HomeActivity;

public class AuctionAdapter extends RecyclerView.Adapter<AuctionAdapter.ViewHolder> {

    View view;
    Integer[] Icons;
    String [] title;
    Activity activity;

    public AuctionAdapter(HomeActivity homeActivity, Integer[] auctionIcons, String[] auctionTitles) {
        this.Icons=auctionIcons;
        this.activity=homeActivity;
        this.title=auctionTitles;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_list_auction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.icon.setImageResource(Icons[position]);
        holder.title.setText(title[position]);

    }


    @Override
    public int getItemCount() {
        return Icons.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView title;
        public ViewHolder(View itemView) {
            super(itemView);
            icon=itemView.findViewById(R.id.iv_icon);
            title=itemView.findViewById(R.id.tv_title);

        }
    }
}