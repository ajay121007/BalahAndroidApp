package bidding.app.controller;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import bidding.app.extra.Constants;
import bidding.app.extra.Event;
import bidding.app.extra.Utils;

/**
 * Created by Thakur on 12/19/2017.
 */

public class AddAuctionManager {
    Context context;
    private static final String TAG = AddAuctionManager.class.getSimpleName();
    public void addAuction(Context context, String url) {
        this.context=context;
        Utils.showProgressDialog(context);
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
            Log.e(TAG, "addauction-- " + response);
            Utils.hideProgressDialog();
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s != null) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONObject response = jsonObject.getJSONObject("response");
                    int id = Integer.parseInt(response.getString("id"));
                    String message= response.getString("message");
                    Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post(new Event(Constants.ADDAUCTIONRESULT,""));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                EventBus.getDefault().post(new Event(Constants.SERVER_ERROR,""));
            }
        }
    }
}
