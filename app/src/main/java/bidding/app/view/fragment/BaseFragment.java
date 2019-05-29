package bidding.app.view.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import bidding.app.R;
import bidding.app.MyApplication;
import bidding.app.extra.Constants;
import bidding.app.extra.Event;

public class BaseFragment extends Fragment {

    AlertDialog.Builder builder;
    View view;
    Dialog dialog;

    public void showProgressDialog() {
        if (dialog == null) {
            builder = new AlertDialog.Builder(getActivity(), getResources().getColor(R.color.transparent));
            view = LayoutInflater.from(getActivity()).inflate(R.layout.custom_progress_bar, null);
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
        MyApplication.isloading = true;
    }

    public void hideProgressDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            MyApplication.isloading = false;
        }
    }

    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
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
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void onEvent(Event event) {
        switch (event.getKey()) {
        }
    }
}

