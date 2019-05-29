package bidding.app.controller;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import bidding.app.extra.CSPreferences;
import bidding.app.extra.Constants;
import bidding.app.extra.Event;

/**
 * Created by vijay on 12/8/17.
 */

public class LoginManager {

    private static final String TAG = LoginManager.class.getSimpleName();


    public void doLogin(Context context, String url) {
        new ExecuteApi(context).execute(url);
    }
    private class ExecuteApi extends AsyncTask<String, String, String> {

        Context mContext;
        public ExecuteApi(Context context) {

            this.mContext =context;
        }
        @Override
        protected String doInBackground(String... params) {
            HttpHandler httpHandler = new HttpHandler();
            String response = httpHandler.makeServiceCall(params[0]);
            Log.e(TAG, "loginmanager-- " + response);
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONObject response = jsonObject.getJSONObject("response");
                    //String  message = jsonObject.getString("message");
                    int id = Integer.parseInt(response.getString("status"));
                    if (id >= 1) {
                        JSONObject jsonObject1 = response.getJSONObject("data");
                        String userid = jsonObject1.getString("user_id");
                        CSPreferences.putString(mContext,"user_id",userid);
                        EventBus.getDefault().post(new Event(Constants.LOGINRESULT, ""));
                    } else {
                        EventBus.getDefault().post(new Event(Constants.ERROR,""));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                EventBus.getDefault().post(new Event(Constants.SERVER_ERROR,""));
            }
        }
    }
}