package bidding.app.firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.PowerManager;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import bidding.app.R;
import bidding.app.extra.CSPreferences;
import bidding.app.extra.Constants;
import bidding.app.extra.Event;
import bidding.app.view.activity.mainactivity.MainActivity;

/*
 * Created by rishav on 6/3/17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private static final String CHANNEL_ID = "channel_01";
    public static NotificationManager mNotificationManager;

    public static double driverlat,driverlongititude;
    String amount,requestid;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        //Displaying data in log
        //It is optional
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        Log.e(TAG, "noti "+remoteMessage.getData());
        PowerManager.WakeLock screenLock = ((PowerManager) getApplicationContext().getSystemService(POWER_SERVICE)).newWakeLock(
                PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
        screenLock.acquire();
        screenLock.release();

        JSONObject jsonObject1 =new JSONObject(remoteMessage.getData());
        try {
            String notitype = jsonObject1.getString("noti_type");
            String message = jsonObject1.getString("message");
            String auction_id = jsonObject1.getString("auction_id");
            CSPreferences.putString(getApplicationContext(),"noti_id",auction_id);
            switch (notitype){
                case "auction_info":
                    Log.e("nnn","nnn");
                    ((Vibrator)getSystemService(VIBRATOR_SERVICE)).vibrate(300);
                    CSPreferences.putString(getApplicationContext(),"message",message);
                    CSPreferences.putString(getApplicationContext(),"noti_id",auction_id);
                    sendNotification(message);
                   // CSPreferences.putString(getApplicationContext(),"notiauctionid",auction_id);
                    EventBus.getDefault().post(new Event(Constants.NOTI,""));

                    break;
                case "1":
                    Intent intent2 = new Intent();
                    intent2.setClassName("bidding.app", "bidding.app.view.activity.mainactivity.MainActivity");
                    intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplicationContext().startActivity(intent2);
                    break;
                case "2":
                    sendNotification(message);
                    Intent intent1 = new Intent();
                    intent1.setClassName("bidding.app", "bidding.app.view.activity.mainactivity.MainActivity");
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplicationContext().startActivity(intent1);
                    break;
                case "3":
                    sendNotification(message);
                    Intent intent3 = new Intent();
                    intent3.setClassName("bidding.app", "bidding.app.view.activity.mainactivity.MainActivity");
                    intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplicationContext().startActivity(intent3);
                    break;
                case "4":
                    sendNotification(message);
                    Intent intent4 = new Intent();
                    intent4.setClassName("bidding.app", "bidding.app.view.activity.mainactivity.MainActivity");
                    intent4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplicationContext().startActivity(intent4);
                    break;
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    //This method is only generating push notification
    //It is same as we did in earlier posts
    private void sendNotification(String messageBody) {

        // Get an instance of the Notification manager
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Android O requires a Notification Channel.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.app_name);
            // Create the channel for the notification
            NotificationChannel mChannel =
                    new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);

            // Set the Notification Channel for the Notification Manager.
            mNotificationManager.createNotificationChannel(mChannel);
        }




        NotificationCompat.Builder mBuilder =   new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_icon_app_icon) // notification icon
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_icon_app_icon))
                .setContentTitle("Notification!") // title for notification
                .setContentText(messageBody) // message for notification
                .setAutoCancel(true); // clear notification after click

        // Set the Channel ID for Android O.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mBuilder.setChannelId(CHANNEL_ID); // Channel ID
        }

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this,0,intent,Intent.FLAG_ACTIVITY_NEW_TASK);
        mBuilder.setContentIntent(pi);
        mNotificationManager.notify(0, mBuilder.build());
    }
}