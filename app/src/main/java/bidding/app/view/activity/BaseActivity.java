package bidding.app.view.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import bidding.app.MyApplication;
import bidding.app.R;
import bidding.app.extra.Constants;
import bidding.app.extra.Event;
import bidding.app.receiver.NetworkChangeReceiver;

public class BaseActivity extends AppCompatActivity {

    AlertDialog.Builder builder;
    View view;
    Dialog dialog;
    private ProgressDialog progressDialog;


    protected void showProgressDialog(int messageId) {
        if (progressDialog != null && progressDialog.isShowing()) {
            return;
        }

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
        }

        progressDialog.setMessage(getString(messageId));
        progressDialog.show();
    }

    protected void hideProgressDialo() {
        if (progressDialog == null) {
            return;
        }

        progressDialog.dismiss();
    }

    protected void showAlertDialog(String message) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton(R.string.button_ok, null)
                .setCancelable(false)
                .show();
    }

    protected void customToolbarWithBack(final Activity activity, Toolbar toolbar, String title) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.toolbar_with_back, null);
        ImageView back = view.findViewById(R.id.back);
        TextView tvToolbarTitle = view.findViewById(R.id.tvToolbarTitle);
        tvToolbarTitle.setText(title);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();

            }
        });
        getSupportActionBar().setCustomView(view);
    }

    protected void showAlertDialog(int messageId) {
        showAlertDialog(getString(messageId));
    }

    public void showProgressDialog() {
        if (dialog == null) {
            builder = new AlertDialog.Builder(this, getResources().getColor(R.color.transparent));
            view = LayoutInflater.from(this).inflate(R.layout.custom_progress_bar, null);
            builder.setView(view);
            dialog = builder.create();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.transparent)));
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.width = lp.MATCH_PARENT;
            lp.height = lp.MATCH_PARENT;
            dialog.getWindow().setAttributes(lp);
            dialog.setCancelable(false);
        }
        dialog.show();
    }

    public void hideProgressDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showSnackbar(boolean b, final RelativeLayout relativeLayout) {
        if (b) {
            Snackbar snackbar = Snackbar
                    .make(relativeLayout, "Internet Connected !", Snackbar.LENGTH_LONG);
            // Changing action button text color
            View sbView = snackbar.getView();
            sbView.setBackgroundColor(getResources().getColor(R.color.DarkGreen));
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(getResources().getColor(R.color.white));
            textView.setPadding(0, 0, 0, 0);
            snackbar.show();
        } else {
            Snackbar snackbar = Snackbar
                    .make(relativeLayout, "No internet connection !", Snackbar.LENGTH_LONG)
                    .setAction("Setting", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                        }
                    });
            snackbar.setActionTextColor(Color.BLACK);
            View sbView = snackbar.getView();
            sbView.setBackgroundColor(Color.RED);
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(getResources().getColor(R.color.white));
            snackbar.show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MyApplication.activityPaused();
        try {
            unregisterReceiver(new NetworkChangeReceiver());
        } catch (Exception e) {
            Log.d("RECEIVEREXCEPTION", e.getMessage().toString());
        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransitionEnter();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        registerReceiver();
        MyApplication.activityResumed();
    }


    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void onEvent(Event event) {
        switch (event.getKey()) {
        }
    }


    public void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(new NetworkChangeReceiver(), intentFilter);
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransitionExit();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransitionEnter();
    }

    /**
     * Overrides the pending Activity transition by performing the "Enter" animation.
     */
    protected void overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    /**
     * Overrides the pending Activity transition by performing the "Exit" animation.
     */
    protected void overridePendingTransitionExit() {
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
