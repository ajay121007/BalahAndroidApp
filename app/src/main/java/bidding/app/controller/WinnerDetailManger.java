package bidding.app.controller;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import bidding.app.extra.Constants;
import bidding.app.extra.Event;
import bidding.app.model.WinnerDetailBean;

/**
 * Created by Thakur on 12/21/2017.
 */

public class WinnerDetailManger {
    private static final String TAG = WinnerDetailManger.class.getSimpleName();
    public WinnerDetailBean bean;


    public void WinnerDetailManger(Context context, String url) {
        new WinnerDetailManger.ExecuteApi(context).execute(url);
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
            Log.e(TAG, "winnerdeails-- " + response);
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s != null) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    bean=new Gson().fromJson(s, WinnerDetailBean.class);
                    EventBus.getDefault().post(new Event(Constants.WINNERDETAIL_RESULT,""));
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
