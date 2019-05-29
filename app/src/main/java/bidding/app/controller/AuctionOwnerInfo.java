package bidding.app.controller;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import bidding.app.extra.Constants;
import bidding.app.extra.Event;
import bidding.app.model.AuctionOwnerInfoBean;
import bidding.app.model.WinnerDetailBean;

public class AuctionOwnerInfo {
    private static final String TAG = AuctionDetailsById.class.getSimpleName();
    public static String sLatitude = "";
    public static String sLongitude = "";
    public static String sFullName = "";
    public static AuctionOwnerInfoBean ownerInfoBean;
    public static AuctionOwnerInfoBean.Data dataList;

    public void AuctionOwnerInfo(Context context, String url) {
        new AuctionOwnerInfo.ExecuteApi(context).execute(url);

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
            Log.e(TAG, "AuctionOwnerInfoBean" + response);
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("OWNERRESPONSE", s + "");
            if (s != null) {
                try {
                    ownerInfoBean = new Gson().fromJson(s, AuctionOwnerInfoBean.class);
                    dataList = ownerInfoBean.getResponse().getData();
                    JSONObject jsonObject = new JSONObject(s);
                    JSONObject response = jsonObject.getJSONObject("response");
                    JSONObject data;
                    String status = response.getString("status");
                    if (status.equalsIgnoreCase("1")) {
                        data = response.getJSONObject("data");
                        sFullName = data.getString("fullname");
                        sLatitude = data.getString("latitude");
                        sLongitude = data.getString("longitude");
                        EventBus.getDefault().post(new Event(Constants.OWNERINFO, ""));
                    } else {
                        EventBus.getDefault().post(new Event(Constants.SERVER_ERROR, ""));
                    }
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
