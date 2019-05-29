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

import bidding.app.extra.Config;
import bidding.app.extra.Constants;
import bidding.app.extra.Event;
import bidding.app.model.ProductBean;

/**
 * Created by vijay on 19/8/17.
 */

public class ProductManager  {

   public static ArrayList<ProductBean>list;
    private static final String TAG = ProductManager.class.getSimpleName();


    public void ProductManager(Context context, String url) {
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
            Log.e(TAG, "productall-- " + response);
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
                    if (jsonArray.length()>=1) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String product_id = jsonObject1.getString("product_id");
                            String product_name = jsonObject1.getString("product_name");
                            String o_productname = String.valueOf(Html.fromHtml(Html.fromHtml(product_name).toString()));

                            String product_image = Config.imageurl + jsonObject1.getString("product_image");

                            String product_quantity = jsonObject1.getString("product_quantity");
                            String product_detail = jsonObject1.getString("product_detail");
                            String o_productdetail = String.valueOf(Html.fromHtml(Html.fromHtml(product_detail).toString()));
                            String product_price = jsonObject1.getString("product_price");
                            String product_number = jsonObject1.getString("product_number");
                            String currency_type = jsonObject1.getString("currency_type");

                            ProductBean productBean = new ProductBean(product_id, o_productname, product_image, product_quantity, o_productdetail, product_price, product_number,currency_type);
                            list.add(productBean);
                        }
                        EventBus.getDefault().post(new Event(Constants.Productstatus,""));
                    }
                    else {

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