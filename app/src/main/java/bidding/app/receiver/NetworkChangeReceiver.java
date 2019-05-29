package bidding.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import org.greenrobot.eventbus.EventBus;

import bidding.app.extra.Constants;
import bidding.app.extra.Event;
import bidding.app.MyApplication;

public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {

        try {
            boolean isVisible = MyApplication.isActivityVisible();// Check if activity is visible or not
            Log.i("Activity is Visible ", "Is activity visible : " + isVisible);

            // If it is visible then trigger the task else do nothing
            if (isVisible == true) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager
                        .getActiveNetworkInfo();
                // Check internet connection and accrding to state change the
                if (networkInfo != null && networkInfo.isConnected()) {
                    EventBus.getDefault().post(new Event(Constants.CONECTED, true));
                } else {
                    EventBus.getDefault().post(new Event(Constants.CONECTED, false));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}