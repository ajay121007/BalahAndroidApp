package bidding.app.view.activity.changeaddress.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import bidding.app.R;
import bidding.app.model.AddressListBean;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    View view;
    Activity activity;
    List<String> title;
    int selectedPosition = -1, lastPosition = -1;
    ItemClick clickListner;
    List<AddressListBean.ResponseBean.DataBean> data;

    public AddressAdapter(Activity activity, List<AddressListBean.ResponseBean.DataBean> data, ItemClick mListner) {
        this.activity = activity;
        this.data = data;
        this.clickListner = mListner;

    }


    @Override
    public AddressAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_address, parent, false);
        return new AddressAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AddressAdapter.ViewHolder holder, final int position) {

        if (data.get(position).getIs_default() == 1) {
            holder.checkbox.setChecked(true);
            lastPosition = position;
        } else {
            holder.checkbox.setChecked(false);
        }

        holder.tvName.setText(data.get(position).getFirstname() + " " + data.get(position).getLastname());
        holder.tvAddress1.setText(data.get(position).getStreet().get(0));
        holder.tvAddress2.setText(data.get(position).getCity());
        holder.tvAddress3.setText(data.get(position).getCountry_id() + "," + data.get(position).getPostcode());
        holder.tvMobile.setText(data.get(position).getTelephone());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListner.OnItemClick(position, data.get(position).getEntity_id(), lastPosition);
            }
        });

        holder.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListner.OnEditClick(position,data.get(position).getEntity_id());
            }
        });

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListner.OnDeleteClick(position,data.get(position).getEntity_id());
            }
        });



    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void customNotify(int selectedPosition) {
        this.selectedPosition=selectedPosition;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvEdit,tvName, tvMobile, tvAddress1, tvAddress2, tvAddress3;
        RadioButton checkbox;
        ImageView ivDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            tvEdit=itemView.findViewById(R.id.tvEdit);
            checkbox = itemView.findViewById(R.id.checkbox);
            ivDelete = itemView.findViewById(R.id.ivDelete);
            tvName = itemView.findViewById(R.id.tvName);
            tvMobile = itemView.findViewById(R.id.tv_mobile);
            tvAddress1 = itemView.findViewById(R.id.tv_address1);
            tvAddress2 = itemView.findViewById(R.id.tv_address2);
            tvAddress3 = itemView.findViewById(R.id.tv_address3);


        }
    }

    public interface ItemClick {
        void OnItemClick(int position, String entity_id, int lastPosition);
        void OnEditClick(int position, String entityId);
        void OnDeleteClick(int position,String entity_id);
    }

}

