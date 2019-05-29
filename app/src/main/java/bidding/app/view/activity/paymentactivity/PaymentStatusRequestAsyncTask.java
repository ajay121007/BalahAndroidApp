package bidding.app.view.activity.paymentactivity;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import bidding.app.extra.Constants;
import retrofit2.http.POST;


/**
 * Represents an async task to request a payment status from the server.
 */
public class PaymentStatusRequestAsyncTask extends AsyncTask<String, Void, String> {

    private PaymentStatusRequestListener listener;

    public PaymentStatusRequestAsyncTask(PaymentStatusRequestListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        if (params.length != 1) {
            return null;
        }

        String resourcePath = params[0];

        if (resourcePath != null) {
            return requestPaymentStatus(resourcePath);
        }

        return null;
    }

    @Override
    protected void onPostExecute(String paymentStatus) {
        if (listener != null) {
            if (paymentStatus == null) {
                listener.onErrorOccurred();

                return;
            }

            listener.onPaymentStatusReceived(paymentStatus);
        }
    }

    private String requestPaymentStatus(String resourcePath) {
        if (resourcePath == null) {
            return null;
        }

        URL url;
        String urlString;
        HttpURLConnection connection = null;
        String paymentStatus = null;

        try {
            urlString = Constants.BASE_URL + "rest/V1/hyper/paymentstatus/" +
                    resourcePath;

            Log.d(Constants.LOG_TAG, "Status request url: " + urlString);

            url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(Constants.CONNECTION_TIMEOUT);
            connection.setRequestMethod("POST");

            InputStream inputStream;
            // get stream
            if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                inputStream = connection.getInputStream();
            } else {
                inputStream = connection.getErrorStream();
            }
            // parse stream
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String temp, response = "";
            while ((temp = bufferedReader.readLine()) != null) {
                response += temp;
            }

            JSONArray jsonArray = new JSONArray(response.toString());
            JSONObject data = jsonArray.getJSONObject(0);
            JSONObject object=data.getJSONObject("response");
            JSONArray array=object.getJSONArray("data");
            JSONArray array1=array.getJSONArray(0);
            JSONObject dataJSONObject = array1.getJSONObject(0);
            JSONObject jsonObject=dataJSONObject.getJSONObject("result");
            paymentStatus = jsonObject.getString("description");

            Log.d(Constants.LOG_TAG, "Status: " + jsonArray.toString());
        } catch (Exception e) {
            Log.e(Constants.LOG_TAG, "Error: ", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return paymentStatus;
    }
}
