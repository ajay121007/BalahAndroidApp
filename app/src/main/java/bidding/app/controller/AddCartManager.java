package bidding.app.controller;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import bidding.app.extra.Constants;
import bidding.app.extra.Event;

/**
 * Created by vijay on 22/8/17.
 */

public class AddCartManager {
    private static final String TAG = LoginManager.class.getSimpleName();


    public void AddCartManager(Context context, String url) {
        new ExecuteApi(context).execute(url);
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
            Log.e(TAG, "addcart-- " + response);
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s != null) {
                try {
                    // {"response":{"status":"1","message":"success","data":{"product_id":"47","user_id":"2","quantity":"17","product_type":"1"}}}
                    JSONObject jsonObject = new JSONObject(s);
                    JSONObject response = jsonObject.getJSONObject("response");
                    int id = Integer.parseInt(response.getString("status"));
                    if (id >= 1) {
                        String message=response.getString("message");
                        JSONObject jsonObject1 = response.getJSONObject("data");
                        String product_id = jsonObject1.getString("product_id");
                        String product_quantity = jsonObject1.getString("quantity");
                        EventBus.getDefault().post(new Event(Constants.ADDCART, message));
                    } else if (String.valueOf(id).equalsIgnoreCase("-2")) {
                        EventBus.getDefault().post(new Event(Constants.PROFILEERROR, ""));
                    } else {
                        EventBus.getDefault().post(new Event(Constants.ERROR, ""));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                EventBus.getDefault().post(new Event(Constants.SERVER_ERROR, ""));
            }
        }
    }
}
