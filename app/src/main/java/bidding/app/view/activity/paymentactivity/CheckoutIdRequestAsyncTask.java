package bidding.app.view.activity.paymentactivity;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;


import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import bidding.app.extra.Constants;


/**
 * Represents an async task to request a checkout id from the server.
 */
public class CheckoutIdRequestAsyncTask extends AsyncTask<String, Void, String> {

    private CheckoutIdRequestListener listener;

    public CheckoutIdRequestAsyncTask(CheckoutIdRequestListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        if (params.length != 3) {
            return null;
        }

        String amount = params[0];
        String currency = params[1];
        String callbackScheme = params[2];

        return requestCheckoutId(amount, currency, callbackScheme);
    }

    @Override
    protected void onPostExecute(String checkoutId) {
        if (listener != null) {
            listener.onCheckoutIdReceived(checkoutId);
        }
    }

    private String requestCheckoutId(String amount,
                                     String currency,
                                     String callbackScheme) {


        JSONObject jsonObject = new JSONObject();
        JSONObject object = new JSONObject();
        try {
            object.put("amount", amount);
            object.put("currency", currency);
            object.put("shopperResultUrl", callbackScheme + "://result");
            object.put("notificationUrl", "");
            jsonObject.put("datas", object);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String urlString = Constants.BASE_URL + "rest/V1/hyper/checkoutid";
        URL url;
        HttpURLConnection connection = null;
        String checkoutId = null;

        String requestBody = jsonObject.toString();
        try {
            url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(Constants.CONNECTION_TIMEOUT);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            OutputStream outputStream = new BufferedOutputStream(connection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "utf-8"));
            writer.write(requestBody);
            writer.flush();
            writer.close();
            outputStream.close();

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

            Log.d(Constants.LOG_TAG, "Checkout ID: " + response.toString());

            JSONArray jsonArray = new JSONArray(response.toString());
            JSONObject data = jsonArray.getJSONObject(0);
            JSONObject respons=data.getJSONObject("response");
            JSONArray array= respons.getJSONArray("data");
            JSONArray array1=array.getJSONArray(0);
            JSONObject jsonObject1=array1.getJSONObject(0);
            checkoutId = jsonObject1.getString("id");

            Log.d(Constants.LOG_TAG, "Checkout ID: " + jsonArray.toString());
        } catch (Exception e) {
            Log.d(Constants.LOG_TAG, " Exception" + e.getMessage().toString());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return checkoutId;
    }
}