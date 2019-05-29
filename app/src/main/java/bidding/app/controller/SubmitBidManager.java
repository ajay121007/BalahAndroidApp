package bidding.app.controller;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import bidding.app.extra.Constants;
import bidding.app.extra.Event;

/**
 * Created by vijay on 21/8/17.
 */

public class SubmitBidManager {


    private static final String TAG = SubmitBidManager.class.getSimpleName();


    public void SubmitBidManager(Context context, String url) {
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
            Log.e(TAG, "submitbid response-- " + response);
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
           Log.d("stringhelo",s+"");
            if (s != null) {
                try {
                    String bidprice="1";
                    JSONObject jsonObject = new JSONObject(s);
                    JSONObject response = jsonObject.getJSONObject("response");
                    int id = Integer.parseInt(response.getString("status"));
                    String message = response.getString("message");
                    JSONArray jsonArray = response.getJSONArray("data");
                    if (jsonArray.length()>=1){
                        for (int i =0; i<jsonArray.length();i++){
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            bidprice = jsonObject1.getString("bid_price");

                        }
                    }
                    EventBus.getDefault().post(new Event(Constants.SUBMITBID,message+"-"+bidprice));
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                EventBus.getDefault().post(new Event(Constants.SERVER_ERROR,""));
            }
        }
    }
}