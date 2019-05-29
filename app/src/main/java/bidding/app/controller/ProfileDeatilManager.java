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
 * Created by vijay on 24/8/17.
 */

public class ProfileDeatilManager {

    private static final String TAG = ProfileDeatilManager.class.getSimpleName();


    public void ProfileDeatilManager(Context context, String url) {
        new ExecuteApi(context).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,url);
    }

    private class ExecuteApi extends AsyncTask<String, String, String> {

        Context mContext;

        public ExecuteApi(Context context) {

            this.mContext = context;
        }

        @Override
        protected String doInBackground(String... params) {
            HttpHandler httpHandler = new HttpHandler();
            String response = httpHandler.makeServiceCall(params[0]);
            Log.e(TAG, "Profile detail manager-- " + response);
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String user_id = "", fullname = "", address = "", country = "", city = "", latitude = "", longitude = "";

            if (s != null) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONObject response = jsonObject.getJSONObject("response");
                    String message = response.getString("message");
                    int id = Integer.parseInt(response.getString("status"));
                    CSPreferences.putString(mContext,"profile_status",String.valueOf(id));
                    JSONArray jsonArray = response.getJSONArray("data");


                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        user_id = jsonObject1.getString("user_id");
                        fullname = jsonObject1.getString("fullname");
                        address = jsonObject1.getString("address");
                        country = jsonObject1.getString("country");
                        city = jsonObject1.getString("city");
                        latitude = jsonObject1.getString("latitude");
                        longitude = jsonObject1.getString("longitude");
                        CSPreferences.putString(mContext,"fullname",fullname);
                        CSPreferences.putString(mContext,"latitude",latitude);
                        CSPreferences.putString(mContext,"longitude",longitude);
                    }
                    EventBus.getDefault().post(new Event(Constants.PROFILEDEATIL,jsonObject));
                } catch (JSONException e) {
                    e.printStackTrace();
                    EventBus.getDefault().post(new Event(Constants.SERVER_ERROR, ""));
                }
            } else {
                EventBus.getDefault().post(new Event(Constants.SERVER_ERROR, ""));
            }
        }
    }
}