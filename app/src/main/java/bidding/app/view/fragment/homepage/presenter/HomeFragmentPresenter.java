package bidding.app.view.fragment.homepage.presenter;

import android.content.Context;
import android.text.Html;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import bidding.app.extra.Constants;
import bidding.app.extra.Event;
import bidding.app.model.AuctionBean;
import bidding.app.view.fragment.homepage.Home_page;
import bidding.app.view.fragment.homepage.view.HomeFragmentView;
import bidding.app.web.WebServc;
import bidding.app.web.handler.GetAllAuctionHandler;
import bidding.app.web.handler.GetAuction;


/**
 * Created by Thakur on 11/12/2017.
 */

public class HomeFragmentPresenter implements HomeFragmentPresenterHandler {

    HomeFragmentView view;
    String highest_bid = "", quantity = "", image = "", end_date = "", end_time = "", station = "", reserve_price = "" + "", mAllImage = "", minBid = "", maxBid = "",startTime="",startdate="",extendTime="";
    public static ArrayList<AuctionBean> list;

    public HomeFragmentPresenter(Home_page home_page) {
        this.view = home_page;

    }

    @Override
    public void getAllAuction(Context context, String action, String userid, String auctionid) {
        view.showProgess();
        WebServc.getInstance().getAllAuction(new GetAllAuctionHandler() {
            @Override
            public void onSuccess(String Response) {
                view.hideProgess();
                list = new ArrayList<>();
                Log.d("ALLAUCTION", Response + "");
                try {
                    JSONObject jsonObject = new JSONObject(Response);
                    JSONObject response = jsonObject.getJSONObject("response");
                    String status = response.getString("status");
                    String messagestatus = response.getString("message");
                    JSONArray jsonArray = response.getJSONArray("data");
                    if (jsonArray.length() >= 1) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject arrayresponse = jsonArray.getJSONObject(i);
                            String aunctionid = arrayresponse.getString("auction_id");
                            String aunction_status = arrayresponse.getString("auction_status");
                            reserve_price = arrayresponse.getString("auction_title");
                            station = arrayresponse.getString("station");
                            minBid = arrayresponse.getString("minimum_bid_inc");
                            maxBid = arrayresponse.getString("maximum_bid_inc");
                            mAllImage = arrayresponse.getString("image");
                            extendTime = arrayresponse.getString("extended_time");
                            startTime = arrayresponse.getString("start_time");
                            startdate = arrayresponse.getString("start_date");
                            String o_productname = String.valueOf(Html.fromHtml(Html.fromHtml(reserve_price).toString()));

                            String status1 = arrayresponse.getString("status");
                            if (status1.equals("1")) {
                                JSONObject detailobject = arrayresponse.getJSONObject("auction_detail");
                                highest_bid = detailobject.getString("highest_bid");
                                quantity = detailobject.getString("quantity");
                                end_date = arrayresponse.getString("end_date");
                                end_time = arrayresponse.getString("end_time");
                                image = detailobject.getString("image");
                            }
                            Log.d("aunction_statusd", aunction_status + "");
                            AuctionBean auctionBean = new AuctionBean(aunctionid, aunction_status, o_productname, status1, highest_bid, quantity, image, end_date, end_time, station, mAllImage, minBid, maxBid, startTime, startdate,extendTime);
                            list.add(auctionBean);
                        }
                        view.allAuctionSuccess(list);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String message) {
                view.hideProgess();
                view.showFeedBackMessage(message.toString());
                Log.d("ALLAUCTIONERROR", message.toString());

            }
        }, action, userid, auctionid);
    }

    @Override
    public void submitBid(Context context, String action, String userid, String auction_id, String str, String timee) {
        view.showProgess();
        WebServc.getInstance().submitBid(new GetAllAuctionHandler() {
            @Override
            public void onSuccess(String Response) {
                view.hideProgess();
                Log.e("BIDSUCCESS", Response + "");
                try {
                    String bidprice = "1";
                    JSONObject jsonObject = new JSONObject(Response);
                    JSONObject response = jsonObject.getJSONObject("response");
                    String id = response.getString("status");
                    String message = response.getString("message");
                    JSONArray jsonArray = response.getJSONArray("data");
                    if (id.equalsIgnoreCase("-2")) {
                        view.showFeedBackMessage(message);
                    }
                    if (jsonArray.length() >= 1) {
                        for (int i = 1; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            bidprice = jsonObject1.getString("bid_price");

                        }
                    }
                    EventBus.getDefault().post(new Event(Constants.SUBMITBID, message + "-" + bidprice));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String message) {
                view.hideProgess();
                view.showFeedBackMessage(message);
                Log.e("BIDERROR", message + "");

            }
        }, action, userid, auction_id, str, timee);

    }

    @Override
    public void getAuctionByID(Context context, String getall_auctions, String s, String auction_id) {
        view.showProgess();
        WebServc.getInstance().getAuctionByID(new GetAuction() {
            @Override
            public void onSuccess(String Response) {
                list = new ArrayList<>();
                view.hideProgess();
                Log.e("AUCTIONBYID", Response + "");
                try {
                    JSONObject jsonObject = new JSONObject(Response);
                    JSONObject response = jsonObject.getJSONObject("response");
                    String status = response.getString("status");
                    String messagestatus = response.getString("message");
                    JSONArray jsonArray = response.getJSONArray("data");
                    if (jsonArray.length() >= 1) {
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject arrayresponse = jsonArray.getJSONObject(i);
                            String aunctionid = arrayresponse.getString("auction_id");
                            String aunction_status = arrayresponse.getString("auction_status");
                            reserve_price = arrayresponse.getString("auction_title");
                            station = arrayresponse.getString("station");
                            mAllImage = arrayresponse.getString("image");
                            extendTime = arrayresponse.getString("extended_time");
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
                        view.auctionById(list);
                    } else {
                        EventBus.getDefault().post(new Event(Constants.ERROR, ""));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String message) {
                Log.e("AUCTIONBYID", message + "");
                view.hideProgess();
                view.showFeedBackMessage(message.toString());

            }
        }, getall_auctions, s, auction_id);
    }
}

