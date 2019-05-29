package bidding.app.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jsibbold.zoomage.ZoomageView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import bidding.app.R;
import bidding.app.controller.AuctionDetailsById;
import bidding.app.controller.AuctionManager;
import bidding.app.model.AuctionBean;
import bidding.app.extra.Config;

/**
 * Created by Thakur on 12/21/2017.
 */

public class ProductImage extends BaseFragment {
    View view;
    ZoomageView imageView;
    String postion;
    int pos;
    Bundle bundle;
    ArrayList<AuctionBean> auctionBeans;
    ImageView back;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragmat_product_image,container,false);
        bundle= this.getArguments();
        if (bundle!=null&&!bundle.isEmpty()){
            postion=bundle.getString("position");
            pos=Integer.parseInt(postion);
            Log.d("ProductPos",pos+"");
        }
        init(view);
        return view;
    }
    private void init(View view) {
        if (pos!=0){
            auctionBeans = new ArrayList<>(AuctionDetailsById.list);
            Log.d("Img1111","http://traala.com/Bidding/images/"+auctionBeans.get(pos).getImage());
        }
       else {
            auctionBeans = new ArrayList<>(AuctionManager.list);
            Log.d("Img","http://traala.com/Bidding/images/"+auctionBeans.get(pos).getImage());
        }
        imageView=view.findViewById(R.id.product_image);
        back=view.findViewById(R.id.back);
        for (int i=0;i< auctionBeans.size();i++){
            if (pos==i){
                Picasso.with(getActivity())
                        .load(Config.imageurl+auctionBeans.get(pos).getImage())
                        .placeholder(R.mipmap.ic_launcher)
                        .into(imageView);
            }
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getFragmentManager().popBackStack();
            }
        });
    }
}
