package bidding.app.view.activity.register.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import bidding.app.R;
import bidding.app.extra.AdapterClickListner;

public class CountryListAdapter extends RecyclerView.Adapter<CountryListAdapter.ViewHolder> {
    View view;
    Activity activity;
    List<String> title;
    int selectedPosition = -1;
    ItemClick clickListner;

    public CountryListAdapter(Activity activity, List<String> title, ItemClick listner) {
        this.activity = activity;
        this.title = title;
        clickListner = listner;

    }

    @Override
    public CountryListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_country, parent, false);
        return new CountryListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CountryListAdapter.ViewHolder holder, final int position) {
        Log.d("SelectPosion", selectedPosition + "");
        Log.d("Posion", position + "");

        if (selectedPosition == position) {
            holder.rlMainView.setBackgroundColor(activity.getResources().getColor(R.color.colorAccent));
            holder.tvName.setTextColor(activity.getResources().getColor(R.color.white));
        } else {
            holder.rlMainView.setBackgroundColor(activity.getResources().getColor(R.color.white));
            holder.tvName.setTextColor(activity.getResources().getColor(R.color.black));
        }

        String[] countryArray = title.get(position).split(",");
        holder.tvName.setText(countryArray[0]);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListner.OnItemClick(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return title.size();
    }

    public void customNotify(int position) {
        selectedPosition = position;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        RelativeLayout rlMainView;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_title);
            rlMainView = itemView.findViewById(R.id.rl_main_view);


        }
    }

    public interface ItemClick {
        void OnItemClick(int position);
    }

}

