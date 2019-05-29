package bidding.app.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import bidding.app.R;
import bidding.app.model.AuctionBean;
import bidding.app.extra.ItemClickListener;

/**
 * Created by vijay on 18/8/17.
 */

public class CustomAdapter extends BaseAdapter {


    Context context;
    ArrayList<AuctionBean> list;
    private LayoutInflater inflater=null;

    public CustomAdapter(Context context, ArrayList<AuctionBean> list) {

        this.list = list;
        this.context = context;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub

        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final Holder holder=new Holder();



         AuctionBean users = list.get(position);
        convertView = inflater.inflate(R.layout.customelayout, null);

        holder.imgThumbnail = (ImageView) convertView.findViewById(R.id.imageview);
        holder.top_sr_no = (TextView) convertView.findViewById(R.id.textsrno);
        holder.bottom_sr_no = (TextView) convertView.findViewById(R.id.textbottom_srno);

        return convertView;
    }


    private  class Holder
    {

        public ImageView imgThumbnail;
        public TextView top_sr_no;
        public TextView bottom_sr_no;
        private ItemClickListener clickListener;


    }

}
