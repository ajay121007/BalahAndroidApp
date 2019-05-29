package bidding.app.extra;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import bidding.app.model.ActionString;

/*
 * Created by rishav on 17/1/17.
 */

public class Operations {
    private static final String TAG = Operations.class.getSimpleName();
    public static String loginUser(Context context,String action, String accesstoken,String devicetoken) {
        String params = Config.BASE_URL+action+"&access_token="+accesstoken+"&device_token="+devicetoken+"&device_type=A";
        Log.e(TAG, "cab_company params-- "+params);
        return params;
    }

    public static String getallAcuntion(Context context, String action,String userid,String auctionid){
        String parms = Config.BASE_URL+action+"&user_id="+userid+"&auction_id="+auctionid;
        Log.e("parms"," "+parms);
        return parms;
    }
    public static String addAuction(Context context, String action,String reserve_price,String base_price ,String quntitiy, String image, String station, String start_date, String start_time, String end_date,String end_time, String auction_title, String auction_description){
        String parms = Config.BASE_URL+action+"&reserve_price="+reserve_price+"&base_price="+base_price+"&quantity="+quntitiy
             +"&station="+station+"&start_date="+start_date+"&start_time="+start_time+"&end_date="+end_date+"&end_time="+end_time
                +"&auction_title="+auction_title+"&auction_description="+auction_description+"&image="+image;

        Log.e("parms"," "+parms);
        return parms;
    }


    public static String getHistoryDetail(Context context,String action,String userid){
        String parms = Config.BASE_URL+ ActionString.gethistory_action+"&user_id="+userid;
        return parms;
    }
    public static String getAllAuctionDetail(Context context,String action,String user_id){
        String parms = Config.BASE_URL+action+"&user_id="+user_id;
        return parms;
    }
    public static String getProduct(Context context,String action,String productid){
        String parms = Config.BASE_URL+action+"&product_id="+productid;
        Log.e("product",parms);
        return parms;
    }
    public static String getProdductDeatil(Context context,String action,String productid){
        String parms = Config.BASE_URL+action+"&product_id="+productid;
        Log.e("productdetail",parms);
        return parms;
    }
    public static String getCartProduct(Context context,String action,String userid){
        String parms = Config.BASE_URL+action+"&user_id="+userid;
        Log.e("cartdetail",parms);
        return parms;
    }
    public static String submitBid(Context context,String action,String userid,String auctionid ,String bidprice,String currenttime){
        String parms = Config.BASE_URL+action+"&user_id="+userid+"&auction_id="+auctionid+"&bid_price="+bidprice+"&time="+currenttime.replaceAll(" ","");
        Log.e("submitbid url",parms);
        return parms;
    }
    public static String updateProfile(Context context,String action,String userid,String name,String address,
                                       String country,String city,String lat,String lng){
        String parms = Config.BASE_URL+action+"&user_id="+userid+"&fullname="+name+"&address="+address+"&country="+country+"&city="+city+"&latitude="+lat+"&longitude="+lng;
        Log.e("updateprofile",parms);
        return parms;
    }

    public static String addToCart(Context context, String action, String userid, String productid, String quantity, String cardNumber){
        String parms = Config.BASE_URL+action+"&user_id="+userid+"&product_id="+productid+"&quantity="+quantity+"&cardNumber="+cardNumber;
        Log.e("addToCart",parms);
        return parms;
    }
    public static String profilrdeatil(Context context,String action,String userid){
        String parms = Config.BASE_URL+action+"&user_id="+userid;
        Log.e("profiledetail",parms);
        return parms;
    }

    public static String getWinnerDetail(Activity activity, String auction, String user_id) {
        String parms = Config.BASE_URL+auction+"&user_id="+user_id;
        Log.e("winnerdetail",parms);
        return parms;
    }

    public static String getAuctionOwnerInfo(Activity activity, String auctionOwner_detail, String ownerid, String auction_id) {
        //http://206.189.160.58/datesBidding/api.php?action=auctionOwner_detail&user_id=2&auction_id=1
        String parms = Config.BASE_URL+auctionOwner_detail+"&user_id="+ownerid+"&auction_id="+auction_id;
        Log.e("AuctionOwnerInfoBean",parms);
        return parms;
    }
}
