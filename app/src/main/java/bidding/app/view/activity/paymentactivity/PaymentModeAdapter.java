package bidding.app.view.activity.paymentactivity;

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
import bidding.app.view.activity.expressactivity.adapter.SortAdapter;
import bidding.app.view.fragment.WinnerDetails;

public class PaymentModeAdapter extends RecyclerView.Adapter<PaymentModeAdapter.ViewHolder> {
    View view;
    List<String> paymentModeList;
    Activity activity;
    AdapterClick adapterClick;


    public PaymentModeAdapter(CustomActivity customActivity, List<String> paymentModeList, AdapterClick adapterClick) {
        this.adapterClick = adapterClick;
        this.activity = activity;
        this.paymentModeList = paymentModeList;
    }


    @Override
    public PaymentModeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_paymnet_mode, parent, false);
        return new PaymentModeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PaymentModeAdapter.ViewHolder holder, final int position) {
        holder.textView.setText(paymentModeList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterClick.Onclick(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return paymentModeList.size();
    }

    public void customNotify(int position) {
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_mode_type);


        }
    }

    interface AdapterClick {
        void Onclick(int position);
    }

}

