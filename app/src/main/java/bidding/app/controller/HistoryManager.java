package bidding.app.controller;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import bidding.app.extra.Constants;
import bidding.app.extra.Event;
import bidding.app.model.HistoryBean;

/**
 * Created by vijay on 19/8/17.
 */

public class HistoryManager {

 public static ArrayList<HistoryBean>list;


    private static final String TAG = HistoryManager.class.getSimpleName();


    public void HistoryManager(Context context, String url) {
        new ExecuteApi(context).execute(url);
    }
    private class ExecuteApi extends AsyncTask<String, String, String> {

        Context mContext;
        public ExecuteApi(Context context) {


            list = new ArrayList<>();
            this.mContext =context;
        }

        @Override
        protected String doInBackground(String... params) {
            HttpHandler httpHandler = new HttpHandler();
            String response = httpHandler.makeServiceCall(params[0]);
            Log.e(TAG, "history-- " + response);
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONObject response = jsonObject.getJSONObject("response");

                    int id = Integer.parseInt(response.getString("status"));

                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String auctionid = jsonObject1.getString("auction_id");
                        String auctionOwnerId = jsonObject1.getString("auctionOwner_id");
                        String status = jsonObject1.getString("status");
                        String totalprice = jsonObject1.getString("total_price");
                        String unit_price = jsonObject1.getString("unit_price");
                        String product_quantity = jsonObject1.getString("product_quantity");
                        String product_name = jsonObject1.getString("product_name");
                        String paymentStatus = jsonObject1.getString("status");
                        String productId = jsonObject1.getString("product_id");

                        HistoryBean historyBean = new HistoryBean(status,totalprice,unit_price,product_quantity,product_name,auctionid,auctionOwnerId,paymentStatus,productId);
                        list.add(historyBean);
                    }


                    EventBus.getDefault().post(new Event(Constants.Historystatus, ""));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                EventBus.getDefault().post(new Event(Constants.SERVER_ERROR, ""));
            }
        }
    }
}
