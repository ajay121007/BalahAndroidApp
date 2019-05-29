package bidding.app.extra;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by vijay on 12/8/17.
 */

public class Constants {

    //Event Bus key
    public static final int LOGINRESULT = 1001;
    public static final int ERROR = 10002;
    public static final int SERVER_ERROR = 1003;
    public static final int Aunctionstatus = 1004;
    public static final int Textupdate=1005;
    public static final int Historystatus=1006;
    public static final int Productstatus=1007;
    public static final int ProductDetail=1008;
    public static final int CARTSTATUS=1008;
    public static final int  SUBMITBID=1009;
    public static final int UPDATEPROFILE=1010;
    public static final int NOTI =1011;
    public static final int PROFILEDEATIL=1012;
    public static final int ADDCART =1013;
    public static final int ADDAUCTIONRESULT = 1014;
    public static final int ALLAUCTION_RESULT = 1015;
    public static final int WINNERDETAIL_RESULT = 1016 ;
    public static final int AUCTIONSETAILBYID = 1017 ;
    public static final int DELETECARDSUCCESS = 1018;
    public static final int DEFAULTCARD =1019 ;
    public static final int NO_ITEM =1020 ;
    public static final int OWNERINFO = 1021;
    public static final int PROFILEERROR = 1022;
    public static final int CONECTED = 1023;

    //SharedPeference key
    public static final String USERID ="user_id" ;
    public static final String STATE ="state" ;
    public static final String SELECTEDCARD ="selectedcatrd" ;
    public static final String SELECTEDCARDNUMBER ="selectedcaednumber" ;
    public static final String TOKEN = "token";
    public static final String VENDORID = "vendor_id";
    public static final String ISVENDOR ="is_vendor" ;
    // E-mail: appzorroteam@gmail.com  Password: Appzorro@f195
    public static final String STRIPE_PUBLISH_KEY="pk_test_MWOlizbfqIKnuqeNMmn41nOu";
    public static final String STRIPE_SECRET_KEY="sk_test_lv8n3y4TZ00SqjTbnHbhKHqw";
    public static final String GRANDTOTAL = "grandTotal";
    public static final String TITLE = "title";
    public static final String BUTTONNAME = "btn_name";
    public static final String ADDRESSID="address_id";


    //Base Url
    public static String IMAGE_BASE_URL="http://178.128.35.113/media/catalog/product";


    /* The configuration values to change across the app */
    public static class Config {

        /* The payment brands for Ready-to-Use UI and Payment Button */
        public static final Set<String> PAYMENT_BRANDS;

        static {
            PAYMENT_BRANDS = new LinkedHashSet<>();
            PAYMENT_BRANDS.add("VISA");
            PAYMENT_BRANDS.add("MASTER");
            PAYMENT_BRANDS.add("PAYPAL");
        }

        /* The default payment brand for payment button */
        public static final String PAYMENT_BUTTON_BRAND = "VISA";

        /* The default amount and currency */
        public static final String AMOUNT = "49.99";
        public static final String CURRENCY = "SAR";

        /* The card info for SDK & Your Own UI*/
        public static final String CARD_BRAND = "VISA";
        public static final String CARD_HOLDER_NAME = "JOHN DOE";
        public static final String CARD_NUMBER = "4200000000000000";
        public static final String CARD_EXPIRY_MONTH = "07";
        public static final String CARD_EXPIRY_YEAR = "21";
        public static final String CARD_CVV = "123";
    }

    public static final int CONNECTION_TIMEOUT = 5000;
    //public static final String BASE_URL = "http://52.59.56.185";
    public static final String BASE_URL = "http://178.128.35.113/";
    public static final String LOG_TAG = "msdk.demo";
}
