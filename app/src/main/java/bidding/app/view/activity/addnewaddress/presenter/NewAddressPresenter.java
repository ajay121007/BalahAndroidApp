package bidding.app.view.activity.addnewaddress.presenter;

import android.app.Application;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import bidding.app.MyApplication;
import bidding.app.view.activity.addnewaddress.NewAddressActivity;
import bidding.app.view.activity.addnewaddress.request.SaveAddressRequest;
import bidding.app.view.activity.addnewaddress.view.NewAddresView;
import bidding.app.view.activity.changeaddress.request.EditAddress;
import bidding.app.web.WebServc;
import bidding.app.web.handler.CallBackHandler;

public class NewAddressPresenter implements NewAddressActivityHandler {

    NewAddresView view;
    String mFirstName = "", mLastName = "", mPhone = "";

    public NewAddressPresenter(NewAddressActivity activity) {
        view = activity;

    }

    @Override
    public void saveAddress(final NewAddressActivity activity, String mToken, SaveAddressRequest request) {
        view.showProgess();
        WebServc.getInstance().addNewAddress(new CallBackHandler() {
            @Override
            public void Success(String response) {
                Log.d("AddNewAddress", response.toString());
                view.hideProgess();

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject object = jsonArray.getJSONObject(0);
                    JSONObject jsonObject = object.getJSONObject("response");
                    if (jsonObject.getInt("status") == 0) {
                        JSONArray array = jsonObject.getJSONArray("data");
                        JSONObject data = array.getJSONObject(0);
                        view.showFeedBackMessage(data.getString("message").replaceAll("\\<.*?\\>", ""));
                    } else {
                        MyApplication.isAddress="1";
                        view.showFeedBackMessage("Address had been saved successfully");
                        activity.finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void Failuer(String error) {
                view.hideProgess();
                Log.d("Error", error.toString());
            }
        }, activity, mToken, request);
    }

    @Override
    public void getCustomer(NewAddressActivity activity, String token) {
        view.showProgess();
        WebServc.getInstance().getProfile(new CallBackHandler() {
            @Override
            public void Success(String response) {
                Log.d("CustomerResponse", response.toString());
                view.hideProgess();
                try {
                    JSONObject object = new JSONObject(response);
                    mFirstName = object.getString("firstname");
                    mLastName = object.getString("lastname");

                    JSONArray jsonArray = object.getJSONArray("custom_attributes");
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    if (jsonObject.getString("attribute_code").equalsIgnoreCase("phone_number"))
                        mPhone = jsonObject.getString("value");

                    view.setData(mFirstName, mLastName, mPhone);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                view.setData(mFirstName, mLastName, mPhone);
            }

            @Override
            public void Failuer(String error) {
                view.hideProgess();
                Log.d("CustomerError", error.toString());
            }
        }, activity, token);
    }

    @Override
    public void editAddress(final NewAddressActivity activity, String mToken, EditAddress request) {
        view.showProgess();
        WebServc.getInstance().editAddress(new CallBackHandler() {
            @Override
            public void Success(String response) {
                Log.d("EditAddress", response.toString());
                view.hideProgess();

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject object = jsonArray.getJSONObject(0);
                    JSONObject jsonObject = object.getJSONObject("response");
                    if (jsonObject.getInt("status") == 0) {
                        JSONArray array = jsonObject.getJSONArray("data");
                        JSONObject data = array.getJSONObject(0);
                        view.showFeedBackMessage(data.getString("message").replaceAll("\\<.*?\\>", ""));
                    } else {
                        MyApplication.isAddress="1";
                        view.showFeedBackMessage("Address had been Updated successfully.");
                        activity.finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void Failuer(String error) {
                view.hideProgess();
                Log.d("Error", error.toString());
            }
        }, activity, mToken, request);
    }
}
