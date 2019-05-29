package bidding.app.view.activity.homeactivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import bidding.app.R;
import bidding.app.extra.CSPreferences;
import bidding.app.extra.Constants;
import bidding.app.extra.Utils;
import bidding.app.view.activity.BaseActivity;
import bidding.app.view.activity.expressactivity.ExpressActivity;
import bidding.app.view.activity.homeactivity.adapter.AuctionAdapter;
import bidding.app.view.activity.homeactivity.adapter.OfferAdapter;
import bidding.app.view.activity.homeactivity.adapter.ViewPagerAdapter;
import bidding.app.view.activity.homeactivity.presenter.HomeActivityPresenter;
import bidding.app.view.activity.homeactivity.presenter.HomeActivityPresenterHandler;
import bidding.app.view.activity.homeactivity.view.HomeActivityView;
import bidding.app.view.activity.myprofile.ProfileActivity;
import bidding.app.view.activity.payment.PaymentActivity;
import bidding.app.view.activity.search.SearchActivity;
import bidding.app.view.fragment.Tab1Fragment.Tab1Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.Gravity.LEFT;

public class HomeActivity extends BaseActivity implements View.OnClickListener, HomeActivityView {

    CustomViewPager pager, pagerAuction;
    Toolbar toolBar;
    RecyclerView rvProduct, rvImages;
    AuctionAdapter auctionAdapter;
    OfferAdapter offerAdapter;
    DrawerLayout drawer;
    LinearLayout llHomeActivity;
    @BindView(R.id.flContainerNavigationMenu)
    FrameLayout NavigationMenu;
    ImageView ivMenu, ivExpress;
    RelativeLayout llTitle;
    int clickedNavItem = 0;
    Context context;

    LinearLayoutManager auctionLayoutManager, productLayoutManager, imagesLayoutManager;
    Integer[] buyIcons = {R.drawable.image_2, R.drawable.image_1, R.drawable.image_2, R.drawable.image_1, R.drawable.image_2, R.drawable.image_1, R.drawable.image_2, R.drawable.image_1, R.drawable.image_2, R.drawable.image_1, R.drawable.image_2, R.drawable.image_1, R.drawable.image_2, R.drawable.image_1, R.drawable.image_2, R.drawable.image_1};
    String[] buyTitles = {"Product", "Product", "Product", "Product", "Product", "Product", "Product", "Product", "Product", "Product", "Product", "Product", "Product", "Product", "Product", "Product"};
    Integer[] images = {R.drawable.image_2, R.drawable.image_1, R.drawable.image_2, R.drawable.image_1, R.drawable.image_2};
    HomeActivityPresenterHandler handler;
    private String mUserId = "", mToken = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        init();
        validateCustomer();
        customToolBar(this, toolBar);
        setupViewPager(pager);
        setupViewPagerAuction(pagerAuction);
        listner();
        setDatesAdapter();
        setImagesAdapter();
    }

    private void validateCustomer() {
        handler.isValidCustomer(this, "Bearer " + mToken);
    }

    private void listner() {
        ivMenu.setOnClickListener(this);
        ivExpress.setOnClickListener(this);
        llTitle.setOnClickListener(this);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void init() {
        context = this;
        mUserId = CSPreferences.readString(HomeActivity.this, "user_id");
        mToken = CSPreferences.readString(HomeActivity.this, Constants.TOKEN);
        handler = new HomeActivityPresenter(this);
        llHomeActivity = findViewById(R.id.ll_home_activity);
        drawer = findViewById(R.id.drawer_layout);
        pager = findViewById(R.id.pager);
        pagerAuction = findViewById(R.id.pager_auction);
        toolBar = findViewById(R.id.toolBar);
        rvProduct = findViewById(R.id.rv_product);
        rvImages = findViewById(R.id.rv_images);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {

            }

            @Override
            public void onDrawerOpened(@NonNull View view) {
                clickedNavItem = 0;
            }

            @Override
            public void onDrawerClosed(@NonNull View view) {

                switch (clickedNavItem) {
                    case R.id.rl_home:
                        Toast.makeText(context, "Home", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rl_my_order:
                        Toast.makeText(context, "My orders", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rl_payment:
                        startActivity(new Intent(context, PaymentActivity.class));
                        break;
                    case R.id.rl_profile:
                        startActivity(new Intent(context, ProfileActivity.class));
                        break;
                    case R.id.rl_setting:
                        Toast.makeText(context, "Setting", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rl_about_us:
                        Toast.makeText(context, "About Us", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rl_help:
                        Toast.makeText(context, "Help", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rl_log_out:
                        handler.logout(HomeActivity.this, "logout", mUserId);
                        break;

                }

            }

            @Override
            public void onDrawerStateChanged(int i) {
                Utils.hideKeyboardFrom(HomeActivity.this, llHomeActivity);
            }
        });
        toggle.syncState();
        replaceNavigationFragment();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1Fragment(), "ONE");
        adapter.addFragment(new Tab1Fragment(), "ONE");
        viewPager.setAdapter(adapter);
    }

    private void setupViewPagerAuction(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AuctionMenuFragment(), "ONE");
        viewPager.setAdapter(adapter);
    }


    public void customToolBar(final Activity context, Toolbar toolbar) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.toolbar_home, null);
        getSupportActionBar().setCustomView(view);
        ivMenu = findViewById(R.id.iv_menu);
        ivExpress = findViewById(R.id.iv_express);
        llTitle = findViewById(R.id.ll_title);
    }


    private void setDatesAdapter() {
        productLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        auctionAdapter = new AuctionAdapter(this, buyIcons, buyTitles);
        rvProduct.setLayoutManager(productLayoutManager);
        rvProduct.setAdapter(auctionAdapter);

    }

    private void setImagesAdapter() {
        imagesLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        offerAdapter = new OfferAdapter(this, images);
        rvImages.setLayoutManager(imagesLayoutManager);
        rvImages.setAdapter(offerAdapter);
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(LEFT)) {
            drawer.closeDrawer(LEFT);
        } else {
            super.onBackPressed();
            overridePendingTransitionExit();
        }
    }

    private void openCloseDrawer() {
        if (drawer.isDrawerOpen(LEFT)) {
            drawer.closeDrawer(LEFT);
        } else {
            Utils.hideKeyboardFrom(HomeActivity.this, llHomeActivity);
            drawer.openDrawer(LEFT);
        }
    }


    public void closeNavigationDrawer() {
        if (drawer.isDrawerOpen(LEFT)) drawer.closeDrawer(LEFT);
    }

    public void replaceNavigationFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flContainerNavigationMenu, NavigationFragment.newInstance(), "Navigation").commit();
    }

    @OnClick({R.id.flContainerNavigationMenu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.flContainerNavigationMenu:
                break;
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_menu:
                openCloseDrawer();
                break;
            case R.id.iv_express:
                startActivity(new Intent(this, ExpressActivity.class));
                break;
            case R.id.ll_title:
                startActivity(new Intent(this, SearchActivity.class));
                break;

        }

    }

    public void replaceActivity(int id) {
        clickedNavItem = id;
        closeNavigationDrawer();
    }


    @Override
    public void showProgess() {
        showProgressDialog();
    }

    @Override
    public void hideProgess() {
        hideProgressDialog();
    }

    @Override
    public void showFeedBackMessage(String message) {
        showToast(message);
    }

    @Override
    public void setData(String mFirstName, String mLastName, String mPhone, String mEmail) {
        NavigationFragment.tvName.setText(mFirstName + " " + mLastName);
        NavigationFragment.tvEmail.setText(mEmail + " ");
    }


}
