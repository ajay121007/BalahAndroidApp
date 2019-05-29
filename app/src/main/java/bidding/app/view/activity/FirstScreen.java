package bidding.app.view.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import bidding.app.R;
import bidding.app.extra.CSPreferences;
import bidding.app.extra.Constants;
import bidding.app.extra.Utils;
import bidding.app.view.activity.homeactivity.HomeActivity;
import bidding.app.view.activity.loginactivity.LoginActivity;
import bidding.app.view.activity.mainactivity.MainActivity;

public class FirstScreen extends BaseActivity {
    private static int SPLASH_TIME_OUT = 3000;
    Context context;
    String mToken="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.FullScreen(this);
        setContentView(R.layout.activity_first_screen);
        context = this;


        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "bidding.app",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

        CSPreferences.putString(context, "noti_id", "");
        CSPreferences.putString(context, "bid_status", "");
        mToken = CSPreferences.readString(getApplicationContext(), Constants.TOKEN);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Camerapermission();
            }
        }, SPLASH_TIME_OUT);
    }

    //************************************************Camera Permission**********************************************
    @TargetApi(Build.VERSION_CODES.M)
    private void Camerapermission() {
        if (ContextCompat.checkSelfPermission(FirstScreen.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(FirstScreen.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                &&ContextCompat.checkSelfPermission(FirstScreen.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED&&ContextCompat.checkSelfPermission(FirstScreen.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED&&ContextCompat.checkSelfPermission(FirstScreen.this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(FirstScreen.this, new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_SMS,Manifest.permission.RECEIVE_SMS}, 12);

        } else

            Camera();
    }

    private void Camera() {
        if (!mToken.isEmpty() && mToken != null) {
            Intent intent = new Intent(context, HomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent i = new Intent(FirstScreen.this, LoginActivity.class);
            startActivity(i);
            finish();
        }
    }
//************************************************PERMISSION RESULT***********************************************

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // TODO Auto-generated method stub
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case 12:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Camera();

                } else {
                    // Permission Denied
                    Camera();
                    Toast.makeText(FirstScreen.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
        }
    }
}
