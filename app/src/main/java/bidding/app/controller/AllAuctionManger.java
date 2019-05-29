package bidding.app.controller;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import bidding.app.model.AllAuctionDetailsBean;
import bidding.app.extra.Constants;
import bidding.app.extra.Event;

/**
 * Created by Thakur on 12/20/2017.
 */

public class AllAuctionManger {
    private static final String TAG = AllAuctionManger.class.getSimpleName();
    public AllAuctionDetailsBean bean;


    public void AllAuctionManger(Context context, String url) {
        new AllAuctionManger.ExecuteApi(context).execute(url);
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
            Log.e(TAG, "allauction-- " + response);
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
// {"response":{"message":"failure","status":"0","auction_detail":[]}}
            if (s != null) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONObject response = jsonObject.getJSONObject("response");
                    String status= response.getString("status");
                    if (status.equalsIgnoreCase("1")){
                        bean = new Gson().fromJson(s, AllAuctionDetailsBean.class);
                        EventBus.getDefault().post(new Event(Constants.ALLAUCTION_RESULT, ""));
                    }
                    else {
                        EventBus.getDefault().post(new Event(Constants.NO_ITEM, ""));
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