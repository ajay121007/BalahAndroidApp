package bidding.app.extra;

import android.content.Context;
import android.widget.Toast;

import bidding.app.view.activity.register.CustomerRegisterActivity;
import bidding.app.view.activity.register.SellerRegisterActivity;

/**
 * Created by Thakur on 12/19/2017.
 */

public class Validation {
    public static Validation mInstane;

    public Validation() {
        mInstane = this;
    }

    public static Validation getInstanse() {
        return mInstane;
    }

    public static String namevalidation = "[a-zA-Z]+";
    public static String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


   /* public boolean isValidAuction(Context context, int quntity, String mQuntity, int reserve_Price, int base_Price, String mStation, String mAuction_title, String startDate, String endDate, String mAuction_description, int minBid, int maxBid, String extendtime) {

        if (quntity==0) {
            Toast.makeText(context, "Please select quntity.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (Long.parseLong(quntity) > Long.parseLong(mQuntity)) {
            Toast.makeText(context, "Invalid quntity ! Your inventory have only " + mQuntity + " products.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (reserve_Price.isEmpty()) {
            Toast.makeText(context, "Please select reserve price.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (base_Price.isEmpty()) {
            Toast.makeText(context, "Please select base price.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mStation.isEmpty()) {
            Toast.makeText(context, "Please select station.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mAuction_title.isEmpty()) {
            Toast.makeText(context, "Please select auction title.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (startDate.isEmpty()) {
            Toast.makeText(context, "Please select start date.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (endDate.isEmpty()) {
            Toast.makeText(context, "Please select end date.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mAuction_description.isEmpty()) {
            Toast.makeText(context, "Please enter auction description.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (minBid.isEmpty()) {
            Toast.makeText(context, "Please enter min bid.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!minBid.isEmpty() && Integer.parseInt(minBid) < 1) {
            Toast.makeText(context, "Please enter Min Bid 1 to 99.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!minBid.isEmpty() && Integer.parseInt(minBid) > 99) {
            Toast.makeText(context, "Please enter Min Bid 1 to 99.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (maxBid.isEmpty()) {
            Toast.makeText(context, "Please enter max bid.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!maxBid.isEmpty() && Integer.parseInt(maxBid) < 1) {
            Toast.makeText(context, "Please enter Max Bid 1 to 99.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!maxBid.isEmpty() && Integer.parseInt(maxBid) > 99) {
            Toast.makeText(context, "Please enter Max Bid 1 to 99.", Toast.LENGTH_SHORT).show();
            return false;
        } else if ((Integer.parseInt(maxBid) < Integer.parseInt(minBid))) {
            Toast.makeText(context, "Please enter Max Bid greater than " + minBid, Toast.LENGTH_SHORT).show();
            return false;
        } else if (extendtime.isEmpty()) {
            Toast.makeText(context, "Please enter extend time in sec.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }*/
    public boolean isValidBid(Context context, int currentprogress, String mMinBid, String mMaxBid) {

        if (!mMinBid.isEmpty() && currentprogress < (Integer.parseInt(mMinBid))) {
            Toast.makeText(context, "Min Bid Increment " + mMinBid, Toast.LENGTH_SHORT).show();
            return false;
        } else if (!mMaxBid.isEmpty() && currentprogress > Integer.parseInt(mMaxBid)) {
            Toast.makeText(context, "Max Bid Increment " + mMaxBid, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    public static boolean isValidSignUp(CustomerRegisterActivity context, String mFirstName, String mLastName, String mEmail, String mPassword, String mConfirmPassword) {

        if (mFirstName.isEmpty()) {
            Toast.makeText(context, "Please enter first name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!mFirstName.isEmpty() && !mFirstName.matches(namevalidation)) {
            Toast.makeText(context, "Please enter valid first name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mLastName.isEmpty()) {
            Toast.makeText(context, "Please enter last name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!mLastName.isEmpty() && !mLastName.matches(namevalidation)) {
            Toast.makeText(context, "Please enter valid last name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mEmail.isEmpty()) {
            Toast.makeText(context, "Please enter E-mail Id ", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!mEmail.isEmpty() && !mEmail.matches(emailPattern)) {
            Toast.makeText(context, "Please enter valid E-mail Id", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public boolean isValidSeller(SellerRegisterActivity activity, String mFirstName, String mLastName, String mEmail, String mPhonNumber, String mVendorId, String mCity, String mCountryCode, String mPostCode, String mStreet, String mState, String mCompany) {
        if (mFirstName.isEmpty()) {
            Toast.makeText(activity, "Please enter first name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!mFirstName.isEmpty() && !mFirstName.matches(namevalidation)) {
            Toast.makeText(activity, "Please enter valid first name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mLastName.isEmpty()) {
            Toast.makeText(activity, "Please enter last name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!mLastName.isEmpty() && !mLastName.matches(namevalidation)) {
            Toast.makeText(activity, "Please enter valid last name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mEmail.isEmpty()) {
            Toast.makeText(activity, "Please enter E-mail Id ", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!mEmail.isEmpty() && !mEmail.matches(emailPattern)) {
            Toast.makeText(activity, "Please enter valid E-mail Id", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mPhonNumber.isEmpty()) {
            Toast.makeText(activity, "Please enter Mobile Number ", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mVendorId.isEmpty()) {
            Toast.makeText(activity, "Please enter Vendor Id ", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mCity.isEmpty()) {
            Toast.makeText(activity, "Please enter City ", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mCountryCode.isEmpty()) {
            Toast.makeText(activity, "Please select Country ", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mPostCode.isEmpty()) {
            Toast.makeText(activity, "Please enter Post Code.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mStreet.isEmpty()) {
            Toast.makeText(activity, "Please enter Street.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mState.isEmpty()) {
            Toast.makeText(activity, "Please enter State.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mCompany.isEmpty()) {
            Toast.makeText(activity, "Please enter Company.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
