package bidding.app.controller;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import bidding.app.extra.CSPreferences;
import bidding.app.extra.Constants;
import bidding.app.extra.Event;

/**
 * Created by vijay on 21/8/17.
 */

public class UpdateProfileManager {

    private static final String TAG = UpdateProfileManager.class.getSimpleName();


    public void UpdateProfileManager(Context context, String url) {
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
            Log.e(TAG, "update profile-- " + response);
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s != null) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONObject response = jsonObject.getJSONObject("response");
                    String  message = response.getString("message");
                    int id = Integer.parseInt(response.getString("status"));
                    if (id >= 1) {
                        JSONArray jsonObject1 = response.getJSONArray("user_detail");
                        for (int i =0;i<jsonObject1.length();i++) {
                            JSONObject jsonObject11 = jsonObject1.getJSONObject(i);
                            String userid = jsonObject11.getString("user_id");
                            String fullname = jsonObject11.getString("fullname");
                            String address = jsonObject11.getString("address");
                            String country = jsonObject11.getString("country");
                            String city = jsonObject11.getString("city");
                            CSPreferences.putString(mContext, "user_id", userid);
                            CSPreferences.putString(mContext, "user_name", fullname);
                        }
                        EventBus.getDefault().post(new Event(Constants.UPDATEPROFILE, ""));
                    } else {
                        EventBus.getDefault().post(new Event(Constants.ERROR,""));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    EventBus.getDefault().post(new Event(Constants.SERVER_ERROR,""));
                }
            } else {
                EventBus.getDefault().post(new Event(Constants.SERVER_ERROR,""));
            }
        }
    }
}