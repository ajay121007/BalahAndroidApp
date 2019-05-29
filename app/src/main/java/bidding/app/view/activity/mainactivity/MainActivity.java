package bidding.app.view.activity.mainactivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import bidding.app.R;
import bidding.app.extra.BottomNavigationViewHelper;
import bidding.app.extra.CSPreferences;
import bidding.app.extra.Constants;
import bidding.app.extra.Event;
import bidding.app.MyApplication;
import bidding.app.view.activity.BaseActivity;
import bidding.app.view.activity.homeactivity.HomeActivity;
import bidding.app.view.activity.mainactivity.presenter.MainActivityPresenter;
import bidding.app.view.activity.mainactivity.presenter.MainActivityPresenterHandler;
import bidding.app.view.activity.mainactivity.view.MainActivityView;
import bidding.app.view.fragment.historyfragment.History_Fragment;
import bidding.app.view.fragment.homepage.Home_page;
import bidding.app.view.fragment.Product_Fragment;
import bidding.app.view.fragment.profilefragment.ProfileFragment;
import bidding.app.view.fragment.purchasefragment.PurchaseFragment;
import bidding.app.view.fragment.shoppingfragment.ShoppingFragment;


public class MainActivity extends BaseActivity implements MainActivityView {
    Context context;
    android.app.Fragment fragment;
    android.app.Fragment currentfragment;
    View v;
    BottomNavigationView bottomNavigation;
    String position = "", mUserId = "";
    private String type = "4";
    MainActivityPresenterHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        intiviews();
        BottomNavigationViewHelper.disableShiftMode(bottomNavigation);
        listner();
    }

    private void listner() {
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                android.app.Fragment currentFragment = getFragmentManager().findFragmentById(R.id.framlayout);
                switch (item.getItemId()) {
                    case R.id.action_item1:
                        type = "0";
                        MyApplication.type = type;
                        logoutDailog();
                        break;
                    case R.id.action_item2:
                        type = "1";
                        MyApplication.type = type;
                        if (!(currentFragment instanceof ProfileFragment))
                            replaceFragment(1);
                        break;
                    case R.id.action_item3:
                        type = "2";
                        MyApplication.type = type;
                        if (!(currentFragment instanceof PurchaseFragment))
                            replaceFragment(2);
                        break;
                    case R.id.action_item4:
                        type = "3";
                        MyApplication.type = type;
                        if (!(currentFragment instanceof ShoppingFragment))
                            replaceFragment(3);
                        break;
                    case R.id.action_item5:
                        type = "4";
                        MyApplication.type = type;
                        if (!(currentFragment instanceof Home_page))
                            replaceFragment(4);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    private void intiviews() {
        mUserId = CSPreferences.readString(MainActivity.this, "user_id");
        handler = new MainActivityPresenter(this);
        bottomNavigation = findViewById(R.id.bottom_navigation);

        if (MyApplication.type != null && !MyApplication.type.isEmpty()) {
            position = MyApplication.type;
            if (position.equalsIgnoreCase("0")) {
                bottomNavigation.setSelectedItemId(R.id.action_item1);
                replaceFragment(0);
            } else if (position.equalsIgnoreCase("1")) {
                bottomNavigation.setSelectedItemId(R.id.action_item2);
                replaceFragment(1);
            } else if (position.equalsIgnoreCase("2")) {
                bottomNavigation.setSelectedItemId(R.id.action_item3);
                replaceFragment(2);
            } else if (position.equalsIgnoreCase("3")) {
                bottomNavigation.setSelectedItemId(R.id.action_item4);
                replaceFragment(3);
            } else if (position.equalsIgnoreCase("4")) {
                bottomNavigation.setSelectedItemId(R.id.action_item5);
                replaceFragment(4);
            }
        } else {
            bottomNavigation.setSelectedItemId(R.id.action_item5);
            replaceFragment(4);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void logoutDailog() {
        MyApplication.type = "";
        finish();
       /* final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(getResources().getString(R.string.signout));
        alertDialogBuilder.setPositiveButton(getResources().getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        MyApplication.type="";
                        startActivity(new Intent(MainActivity.this, HomeActivity.class));
                        finish();
                        //handler.logout(MainActivity.this,"logout",mUserId);
                    }
                });

        alertDialogBuilder.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        currentfragment = getFragmentManager().findFragmentById(R.id.framlayout);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {

        switch (event.getKey()) {
            case Constants.CONECTED:
                if (event.isConnected()) {
                } else {
                    Toast.makeText(this, getResources().getString(R.string.str_internet_check), Toast.LENGTH_SHORT).show();
                }
                break;
            case Constants.NOTI:
                fragment = new Home_page();
                replaceFragment(fragment);
                bottomNavigation.setSelectedItemId(R.id.action_item5);

                break;

            case Constants.ADDAUCTIONRESULT:
                fragment = new Home_page();
                replaceFragment(fragment);
                bottomNavigation.setSelectedItemId(R.id.action_item5);
                break;
        }
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

    public void replaceFragment(int position) {
        switch (position) {
            case 0:
                type = "0";
                MyApplication.type = type;
                logoutDailog();
                break;
            case 1:
                type = "1";
                MyApplication.type = type;
                fragment = ProfileFragment.newInstance();
                break;
            case 2:
                type = "2";
                MyApplication.type = type;
                fragment = History_Fragment.newInstance();
                break;
            case 3:
                type = "3";
                MyApplication.type = type;
                fragment = Product_Fragment.newInstance();
                break;
            case 4:
                type = "4";
                MyApplication.type = type;
                fragment = Home_page.newInstance();
                break;
        }
        if (fragment != null)
            replaceFragment(fragment);
    }

    public void replaceFragment(android.app.Fragment fragment) {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.framlayout, fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (bottomNavigation.getSelectedItemId() == R.id.action_item5) {
            super.onBackPressed();
        } else {
            bottomNavigation.setSelectedItemId(R.id.action_item5);
        }
    }

}


