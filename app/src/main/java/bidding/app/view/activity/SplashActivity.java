package bidding.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import bidding.app.R;
import bidding.app.extra.CSPreferences;
import bidding.app.view.activity.mainactivity.MainActivity;


public class SplashActivity extends BaseActivity {
    private static int SPLASH_TIME_OUT = 3000;
    Context  context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);
        context =this;
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                String userid = CSPreferences.readString(getApplicationContext(),"user_id");
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                CSPreferences.putString(context,"login_status","true");
                finish();
            }
        }, SPLASH_TIME_OUT);
    }    }

