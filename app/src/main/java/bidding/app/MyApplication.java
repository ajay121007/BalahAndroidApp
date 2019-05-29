package bidding.app;

import android.app.Application;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.facebook.accountkit.AccountKit;

import bidding.app.extra.Utility;
import bidding.app.extra.Validation;
import bidding.app.receiver.NetworkChangeReceiver;
import bidding.app.web.WebServc;


/**
 * Created by rishav on 31/7/17.
 */

public class MyApplication extends Application {

    // Gloabl declaration of variable to use in whole app

    public static boolean isloading=false;
    public static String type="4";
    public static String isAddress="0";

    public static boolean activityVisible; // Variable that will check the
    // current activity state

    public static boolean isActivityVisible() {
        return activityVisible; // return true or false
    }

    public static void activityResumed() {
        activityVisible = true;// this will set true when activity resumed

    }

    public static void activityPaused() {
        activityVisible = false;// this will set false when activity paused

    }

    public void onCreate() {
        super.onCreate();
        //Initialize Facebook Account Kit
        AccountKit.initialize(getApplicationContext());
        new Validation();
        new WebServc();
        new Utility();

    }


}

