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

import bidding.app.extra.CSPreferences;
import bidding.app.extra.Config;
import bidding.app.extra.Constants;
import bidding.app.extra.Event;
import bidding.app.model.ProductBean;

/**
 * Created by vijay on 19/8/17.
 */

public class ProductDetailManager {

    public static ArrayList<ProductBean> list;
    private static final String TAG = ProductDetailManager.class.getSimpleName();


    public void ProductDetailManager(Context context, String url) {
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
            Log.e(TAG, "productdetail-- " + response);
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
                    for (int i=0;i<jsonArray.length();i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String product_id = jsonObject1.getString("product_id");
                        CSPreferences.putString(mContext, "product_id", product_id);
                        String product_name = jsonObject1.getString("product_name");
                        String o_productname = String.valueOf(Html.fromHtml(Html.fromHtml(product_name).toString()));

                        CSPreferences.putString(mContext, "product_name", o_productname);
                        String product_image = Config.imageurl + jsonObject1.getString("product_image");
                        CSPreferences.putString(mContext, "product_image", product_image);
                        String product_quantity = jsonObject1.getString("product_quantity");
                        CSPreferences.putString(mContext, "product_quantity", product_quantity);
                        String product_detail = jsonObject1.getString("product_detail");
                        String o_productdetail = String.valueOf(Html.fromHtml(Html.fromHtml(product_detail).toString()));
                        CSPreferences.putString(mContext, "product_detail", o_productdetail);
                        String product_price = jsonObject1.getString("product_price");
                        CSPreferences.putString(mContext, "product_price", product_price);
                        String product_number = jsonObject1.getString("product_number");
                        CSPreferences.putString(mContext,"product_number",product_number);
                        String currency_type = jsonObject1.getString("currency_type");
                        CSPreferences.putString(mContext,"currency_type",currency_type);
                        EventBus.getDefault().post(new Event(Constants.ProductDetail,""));
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
