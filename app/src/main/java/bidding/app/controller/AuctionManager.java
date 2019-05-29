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

import bidding.app.model.AuctionBean;
import bidding.app.extra.Constants;
import bidding.app.extra.Event;

/**
 * Created by vijay on 12/8/17.
 */

public class AuctionManager {


    private static final String TAG = AuctionManager.class.getSimpleName();
    public static ArrayList<AuctionBean> list;


    public void AuctionManager(Context context, String url) {
        list = new ArrayList<>();
        new ExecuteApi(context).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);

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
            Log.e(TAG, "Aunction manager -- " + response);
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String highest_bid = "", quantity = "", image = "", end_date = "", end_time = "", station = "", reserve_price = "" + "", mAllImage = "", minBid = "", maxBid = "",startTime="",startdate="",extendTime="";
            list.clear();
            if (s != null) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONObject response = jsonObject.getJSONObject("response");
                    String status = response.getString("status");
                    String messagestatus = response.getString("message");
                    JSONArray jsonArray = response.getJSONArray("data");
                    Log.d("responshelo", jsonArray + "");
                    if (jsonArray.length() >= 1) {
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject arrayresponse = jsonArray.getJSONObject(i);
                            String aunctionid = arrayresponse.getString("auction_id");
                            String aunction_status = arrayresponse.getString("auction_status");
                            reserve_price = arrayresponse.getString("auction_title");
                            if (arrayresponse.has("minimum_bid_inc")) {
                                minBid = arrayresponse.getString("minimum_bid_inc");
                                maxBid = arrayresponse.getString("maximum_bid_inc");
                            }
                            station = arrayresponse.getString("station");
                            extendTime = arrayresponse.getString("extended_time");
                            if (arrayresponse.has("image")) {
                                mAllImage = arrayresponse.getString("image");
                            }
                            String o_productname = String.valueOf(Html.fromHtml(Html.fromHtml(reserve_price).toString()));
                            String status1 = arrayresponse.getString("status");

                            if (status1.equals("1")) {
                                JSONObject detailobject = arrayresponse.getJSONObject("auction_detail");
                                highest_bid = detailobject.getString("highest_bid");
                                quantity = detailobject.getString("quantity");
                                end_date = arrayresponse.getString("end_date");
                                end_time = arrayresponse.getString("end_time");
                                startTime = arrayresponse.getString("start_time");
                                startdate = arrayresponse.getString("start_date");
                                image = detailobject.getString("image");
                            }
                            Log.d("aunction_statusd", aunction_status + "");
                            AuctionBean auctionBean = new AuctionBean(aunctionid, aunction_status, o_productname, status1, highest_bid, quantity, image, end_date, end_time, station, mAllImage, minBid, maxBid, startTime, startdate,extendTime);
                            list.add(auctionBean);
                        }
                        EventBus.getDefault().post(new Event(Constants.Aunctionstatus, ""));
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