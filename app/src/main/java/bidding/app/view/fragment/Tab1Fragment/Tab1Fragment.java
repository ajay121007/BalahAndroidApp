package bidding.app.view.fragment.Tab1Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import bidding.app.R;
import bidding.app.view.activity.wishlistactivity.WishListActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class Tab1Fragment extends Fragment {

    View view;
    Unbinder unbinder;
    @BindView(R.id.llMyOrder)
    LinearLayout llMyOrder;


    public Tab1Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tab1, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick(R.id.llMyOrder)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.llMyOrder:
                startActivity(new Intent(getActivity(), WishListActivity.class));
                break;
        }
    }


}