package bidding.app.view.activity.expressactivity.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import bidding.app.R;
import bidding.app.extra.AdapterClickListner;
import bidding.app.model.Categories;
import bidding.app.view.fragment.WinnerDetails;

public class SortAdapter extends RecyclerView.Adapter<SortAdapter.ViewHolder> {
    View view;
    Activity activity;
    String[] title;
    private WinnerDetails fragment;
    private AdapterClickListner clickListner;
    int selectedPosition = -1;
    String direction = "", field = "";

    public SortAdapter(Activity activity, String[] title, AdapterClickListner listner) {
        this.activity = activity;
        this.title = title;
        clickListner = listner;

    }

    @Override
    public SortAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_categories, parent, false);
        return new SortAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SortAdapter.ViewHolder holder, final int position) {
        Log.d("SelectPosion",selectedPosition+"");
        Log.d("Posion",position+"");
        if (selectedPosition == position) {
            holder.radioButton.setChecked(true);
        } else {
            holder.radioButton.setChecked(false);
        }
        holder.tvCategoriesName.setText(title[position]);
        holder.llMainView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position == 0) {
                    direction = "ASC";
                    field = "name";
                } else if (position == 1) {
                    direction = "DESC";
                    field = "name";
                } else if (position == 2) {
                    direction = "DESC";
                    field = "price";
                } else if (position == 3) {
                    direction = "ASC";
                    field = "price";
                }
                clickListner.onSortViewClick(position, direction, field);
            }
        });

    }

    @Override
    public int getItemCount() {
        return title.length;
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

