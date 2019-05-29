package bidding.app.extra;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import bidding.app.MyApplication;
import bidding.app.R;


/**
 * Created by rishav on 9/8/17.
 */

public class Utils {
    static ProgressDialog progressDialog = null;
    static AlertDialog.Builder builder;
    static View view;
    static Dialog dialog;

    public static SortedMap<Currency, Locale> currencyLocaleMap;

    static {
        currencyLocaleMap = new TreeMap<Currency, Locale>(new Comparator<Currency>() {
            public int compare(Currency c1, Currency c2) {
                return c1.getCurrencyCode().compareTo(c2.getCurrencyCode());
            }
        });
        for (Locale locale : Locale.getAvailableLocales()) {
            try {
                Currency currency = Currency.getInstance(locale);
                currencyLocaleMap.put(currency, locale);
            } catch (Exception e) {
            }
        }
    }


    public static String getCurrencySymbol(String currencyCode) {
        Currency currency = Currency.getInstance(currencyCode);
        System.out.println(currencyCode + ":-" + currency.getSymbol(currencyLocaleMap.get(currency)));
        return currency.getSymbol(currencyLocaleMap.get(currency));
    }

    public static void FullScreen(Activity context) {
        context.requestWindowFeature(Window.FEATURE_NO_TITLE);
        context.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    public static void showProgressDialog(Context context) {
        if (dialog == null) {
            builder = new AlertDialog.Builder(context, context.getResources().getColor(R.color.transparent));
            view = LayoutInflater.from(context).inflate(R.layout.custom_progress_bar, null);
            builder.setView(view);
            dialog = builder.create();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.transparent)));
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.width = lp.MATCH_PARENT;
            lp.height = lp.MATCH_PARENT;
            dialog.getWindow().setAttributes(lp);
            dialog.setCancelable(false);
        }
        dialog.show();
        MyApplication.isloading = true;
    }

    public static void hideProgressDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            MyApplication.isloading = false;
        }
    }

    public static String getTime(Context context) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy, hh:mmaa");

        String formattedDate = dateFormat.format(new Date()).toString();
        String splittime[] = formattedDate.split(",");
        String time = splittime[splittime.length - 1];
        Log.e("time", formattedDate);
        return time;

    }

    public static String getDateDiff(SimpleDateFormat format, String oldDate, String newDate) {

        try {
           /* long hors =TimeUnit.HOURS.convert(format.parse(newDate).getTime() - format.parse(oldDate).getTime(), TimeUnit.MILLISECONDS);
            long minute =TimeUnit.MINUTES.convert(format.parse(newDate).getTime() - format.parse(oldDate).getTime(), TimeUnit.MILLISECONDS);*/
            long seconds = TimeUnit.MILLISECONDS.convert(format.parse(newDate).getTime() - format.parse(oldDate).getTime(), TimeUnit.MILLISECONDS);
            // return hors+" "+minute+" "+seconds;


            return seconds + "";
        } catch (Exception e) {
            e.printStackTrace();

        }
        return "";
    }

    public static Dialog notidialog(Context context) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialoglayout);
        dialog.setCancelable(true);
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }

    public static String getCompleteAddressString(Context context, double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");
                String country = returnedAddress.getCountryName();
                String city = returnedAddress.getLocality();
                CSPreferences.putString(context, "city", city);
                CSPreferences.putString(context, "country", country);
                Log.e("dddddf", country + "   " + city);

              /*  for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i));
                }*/
                strReturnedAddress.append(returnedAddress.getAddressLine(0));

                if (returnedAddress.getSubLocality() != null)
                    strReturnedAddress.append(", ").append(returnedAddress.getSubLocality());

                strAdd = strReturnedAddress.toString();
                // Log.e(TAG, "address-- "+strReturnedAddress.toString());
            } else {
                Log.e("", "No address returned");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strAdd;
    }

    public static void logMessage(Context context, String firstmessage, String secondmessage) {

        Log.e(firstmessage, secondmessage);

    }

    public static void setTextValue(Context context, TextView view, String mesage) {

        if (mesage.equals("0"))
            view.setText("1");
        else
            view.setText(mesage);
    }

    public static String decompose(String s) {
        return java.text.Normalizer.normalize(s, java.text.Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    public static Dialog progressDialog(Context context, String tiltle, String message) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(tiltle);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();

        return null;
    }

    public static void hideProgress(Context context) {

        if (progressDialog.isShowing()) {
            progressDialog.dismiss();

        }
    }

    public static void hideKeyboardFrom(Context context, View view) {

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public static String timeToSecond(String hms) {
        String secLeft="";
        String Time []=hms.split(":");
        String hrs=Time[0];
        String min=Time[1];
        String sec=Time[2];
        secLeft=(Integer.parseInt(hrs)*60*60)+(Integer.parseInt(min)*60+sec)+"";
        return secLeft;
    }

}
