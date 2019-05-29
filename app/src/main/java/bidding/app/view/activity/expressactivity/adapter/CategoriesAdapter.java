package bidding.app.view.activity.expressactivity.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import bidding.app.R;
import bidding.app.extra.AdapterClickListner;
import bidding.app.model.Categories;
import bidding.app.model.MyAuction;
import bidding.app.view.activity.winnerdetail.WinnerDetailActivity;
import bidding.app.view.fragment.WinnerDetails;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {
    View view;
    Activity activity;
    List<Categories.ChildData> auctionDetail;
    private WinnerDetails fragment;
    private AdapterClickListner clickListner;
    int selectedPosition = -1;

    public CategoriesAdapter(Activity activity, List<Categories.ChildData> auctionDetail, AdapterClickListner listner) {
        this.activity = activity;
        this.auctionDetail = auctionDetail;
        clickListner = listner;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_categories, parent, false);
        return new CategoriesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (selectedPosition == position) {
            holder.radioButton.setChecked(true);
        } else {
            holder.radioButton.setChecked(false);
        }
        holder.tvCategoriesName.setText(auctionDetail.get(position).getName());
        holder.llMainView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListner.onviewClick(position, auctionDetail.get(position).getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return auctionDetail.size();
    }

    public void customNotify(int position) {
        selectedPosition = position;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvCategoriesName;
        LinearLayout llMainView;
        RadioButton radioButton;

        public ViewHolder(View itemView) {
            super(itemView);
            tvCategoriesName = itemView.findViewById(R.id.tv_categorie_name);
            llMainView = itemView.findViewById(R.id.ll_main_view);
            radioButton = itemView.findViewById(R.id.radio_btn);

        }
    }

}
