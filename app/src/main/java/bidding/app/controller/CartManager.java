package bidding.app.controller;

import android.content.Context;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import bidding.app.model.CartBeans;
import bidding.app.extra.Config;
import bidding.app.extra.Constants;
import bidding.app.extra.Event;

/**
 * Created by vijay on 21/8/17.
 */

public class CartManager {

    public static ArrayList<CartBeans> list;


    private static final String TAG = CartManager.class.getSimpleName();


    public void CartManager(Context context, String url) {
        new ExecuteApi(context).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,url);
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
            Log.e(TAG, "cart response-- " + response);
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
                        Log.e("aaa","aa");
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String auctionid = jsonObject1.getString("product_id");
                        String image = Config.imageurl+jsonObject1.getString("product_image");
                        String status = jsonObject1.getString("payment_status");
                        String totalprice = jsonObject1.getString("total_price");
                        String unit_price = jsonObject1.getString("product_price");
                        String product_quantity = jsonObject1.getString("product_quantity");
                        String product_name = jsonObject1.getString("product_name");
                        String currency_type = jsonObject1.getString("currency_type");
                        String o_productname = String.valueOf(Html.fromHtml(Html.fromHtml(product_name).toString()));
                        CartBeans cartBeans = new CartBeans(status,totalprice,unit_price,product_quantity,o_productname,auctionid,image,currency_type);
                        list.add(cartBeans);
                    }


                    EventBus.getDefault().post(new Event(Constants.CARTSTATUS, ""));

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

