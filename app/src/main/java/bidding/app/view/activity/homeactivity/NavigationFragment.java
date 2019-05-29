package bidding.app.view.activity.homeactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import bidding.app.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class NavigationFragment extends Fragment {
    public static final String TAG = NavigationFragment.class.getSimpleName();
    @BindView(R.id.rl_home)
    RelativeLayout rlHome;
    @BindView(R.id.rl_my_order)
    RelativeLayout rlMyOrder;
    @BindView(R.id.rl_payment)
    RelativeLayout rlPayment;
    @BindView(R.id.rl_profile)
    RelativeLayout rlProfile;
    @BindView(R.id.rl_setting)
    RelativeLayout rlSetting;
    @BindView(R.id.rl_about_us)
    RelativeLayout rlAboutus;
    @BindView(R.id.rl_help)
    RelativeLayout rlHelp;
    @BindView(R.id.rl_log_out)
    RelativeLayout rlLogOut;
    Unbinder unbinder;
    public static TextView tvName, tvEmail;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static NavigationFragment newInstance() {
        NavigationFragment navigationFragment = new NavigationFragment();
        Bundle args = new Bundle();
        navigationFragment.setArguments(args);
        return navigationFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        findView(view);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void findView(View view) {
        tvEmail = view.findViewById(R.id.tv_email);
        tvName = view.findViewById(R.id.tv_name);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.rl_home, R.id.rl_my_order, R.id.rl_payment, R.id.rl_profile, R.id.rl_setting, R.id.rl_about_us, R.id.rl_help, R.id.rl_log_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_home:
                replaceActivity(R.id.rl_home);
                break;
            case R.id.rl_my_order:
                replaceActivity(R.id.rl_my_order);
                break;
            case R.id.rl_payment:
                replaceActivity(R.id.rl_payment);
                break;
            case R.id.rl_profile:
                replaceActivity(R.id.rl_profile);
                break;
            case R.id.rl_setting:
                replaceActivity(R.id.rl_setting);
                break;
            case R.id.rl_about_us:
                replaceActivity(R.id.rl_about_us);
                break;
            case R.id.rl_help:
                replaceActivity(R.id.rl_help);
                break;
            case R.id.rl_log_out:
                replaceActivity(R.id.rl_log_out);
                break;

        }


    }

    private void replaceActivity(final int id) {
        ((HomeActivity) getActivity()).replaceActivity(id);
    }
}