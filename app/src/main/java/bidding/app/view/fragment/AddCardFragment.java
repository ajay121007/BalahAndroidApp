package bidding.app.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import bidding.app.R;
import bidding.app.extra.CSPreferences;
import bidding.app.extra.Config;
import bidding.app.extra.Constants;

/**
 * Created by Thakur on 10/18/2017.
 */

public class AddCardFragment extends BaseFragment implements View.OnClickListener {
    EditText card_holder, card_number, cvv;
    Spinner sp_month, sp_year;
    View view;
    TextView scan_c;
    ArrayAdapter<String> adapteryear;
    ArrayAdapter<String> adaptemonth;
    String year[] = {"2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025"};
    String month[] = {"Jan", "Feb", "Mar", "April", "June", "July", "Aug", "Sep", "Oct", "Nov", "Dec"};
    Button add_card;
    Card cardToSave;
    String strcard_number = "", last4 = "", strcvv = "", ex_mnth = "", ex_year = "", card_type = "", token = "", apistatus = "", message = "", stripetoken = "";
    String TAG = AddCardFragment.class.getSimpleName();
    public static String PUBLISHABLE_KEY = "";
    private static final int REQUEST_SCAN = 100;
    private static final int REQUEST_AUTOTEST = 200;
    private boolean autotestMode;
    private int numAutotestsPassed;
    String cardnumber;
    String mnth;
    String card_year;
    String expiry;
    String cvc;
    String type;
    String cardHolderName;
    String card_lastfour;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frahment_card_info, container, false);
        init();
        listner();
        return view;
    }

    private void listner() {
        add_card.setOnClickListener(this);
        scan_c.setOnClickListener(this);
    }

    private void init() {
        scan_c=view.findViewById(R.id.scan_c);
        add_card = (Button) view.findViewById(R.id.add_card);
        card_holder = (EditText) view.findViewById(R.id.card_holder);
        card_number = (EditText) view.findViewById(R.id.card_number);
        cvv = (EditText) view.findViewById(R.id.cvv);
        sp_month = (Spinner) view.findViewById(R.id.sp_month);
        sp_year = (Spinner) view.findViewById(R.id.sp_year);

        adapteryear = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, year);
        adaptemonth = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, month);
        adapteryear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adaptemonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_year.setAdapter(adapteryear);
        sp_month.setAdapter(adaptemonth);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_card:
                cardHolderName = card_holder.getText().toString();
                cardnumber = card_number.getText().toString();
                ex_mnth = sp_month.getSelectedItem().toString();
                ex_year = sp_year.getSelectedItem().toString();
                cvc = cvv.getText().toString();
                if (cardHolderName.isEmpty() || cardnumber.isEmpty() || ex_mnth.isEmpty() || ex_year.isEmpty() || cvc.isEmpty()) {
                    Toast.makeText(getActivity(), "Please fill all fileds.", Toast.LENGTH_SHORT).show();
                } else {
                    onClickPay(cardHolderName, cardnumber, ex_mnth, ex_year, cvc);
                }

                break;

            case R.id.scan_c:
                //onScanPress();
                break;
        }
    }

    public void addCardApi(final String tok) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.BASE_URI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response + "");
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject mainresp=jsonObject.getJSONObject("response");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage() + "", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("action", "addCardDetails");
                params.put("user_id", CSPreferences.readString(getActivity(), "user_id"));
                params.put("card_number", cardnumber);
                params.put("card_holderName", cardHolderName);
                params.put("ex_month", ex_mnth);
                params.put("ex_year", ex_mnth);
                params.put("cvv", cvc);
                params.put("stripe_token", tok);
                Log.d("Params", params + "");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
/*
    public void onScanPress() {
        Intent scanIntent = new Intent(getActivity(), CardIOActivity.class);
        // customize these values to suit your needs.
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, true); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, true); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_HIDE_CARDIO_LOGO, true);
        // MY_SCAN_REQUEST_CODE is arbitrary and is only used within this activity.
        startActivityForResult(scanIntent, REQUEST_SCAN);
    }

    public void onAutotest(View v) {
        Log.e("TAGTeST", "\n\n\n ============================== \n" + "successfully completed "
                + numAutotestsPassed + " tests\n" + "beginning new test run\n");

        Intent intent = new Intent(getActivity(), CardIOActivity.class)
                .putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, false)
                .putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false)
                .putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false)
                .putExtra(CardIOActivity.EXTRA_REQUIRE_CARDHOLDER_NAME, false)
                .putExtra("debug_autoAcceptResult", true);

        startActivityForResult(intent, REQUEST_AUTOTEST);

        autotestMode = true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SCAN || requestCode == REQUEST_AUTOTEST && data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
            String outStr = new String();
            Bitmap cardTypeImage = null;
            if (data == null) {
            } else {
                CreditCard result = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);
                if (result != null) {
                    outStr += "Card number: " + result.getFormattedCardNumber() + "\n";

                    CardType cardType = result.getCardType();
                    cardTypeImage = cardType.imageBitmap(getActivity());
                    outStr += "Card type: " + cardType.name() + "Card Display Name="
                            + cardType.getDisplayName(null) + "\n";

                    // if (mEnableExpiryToggle.isChecked()) {
                    outStr += "Expiry: " + result.expiryMonth + "/" + result.expiryYear + "\n";
                    // }

                    // if (mCvvToggle.isChecked()) {
                    outStr += "CVV: " + result.cvv + "\n";
                    // }

                    //if (mPostalCodeToggle.isChecked()) {
                    outStr += "Postal Code: " + result.postalCode + "\n";
                    //  }

                    //  if (mCardholderNameToggle.isChecked()) {
                    outStr += "Cardholder Name: " + result.cardholderName + "\n";
                    //  }
                } else if (result == null) {

                }

                if (autotestMode) {
                    numAutotestsPassed++;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            onAutotest(null);
                        }
                    }, 500);
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    autotestMode = false;
                }

                Log.e("Resultis", "Set result: " + outStr);

                cardnumber = result.cardNumber;
                mnth = String.valueOf(result.expiryMonth);
                card_year = String.valueOf(result.expiryYear);
                expiry = *//*result.expiryMonth + "/" +*//* String.valueOf(result.expiryYear);
                cvc = result.cvv;
                type = String.valueOf(result.getCardType());
                card_lastfour = result.getLastFourDigitsOfCardNumber();
                Log.d("allss", cardnumber + " " + mnth + "  " + card_year + "   " + expiry + "  " + type + "    " + last4);
                Log.e(TAG, "Set result: " + outStr);
                card_type = String.valueOf(result.getCardType());
                //cardToSave = Card.fromString("{\"brand\":"+type+",\"exp_month\":"+mnth+",\"exp_year\":"+expiry+",\"last4\":"+card_lastfour+",\"object\":\"card\"}");

                Log.d(TAG, " " + result.cardNumber + " " + result.expiryMonth + " " + result.expiryYear + " " + result.cvv);
                cardToSave = new Card(result.cardNumber, result.expiryMonth, result.expiryYear, result.cvv);
                card_type = cardToSave.getBrand();
                strcard_number = cardToSave.getLast4();
                last4 = cardToSave.getLast4();
                strcvv = cardToSave.getCVC();
                ex_mnth = String.valueOf(cardToSave.getExpMonth());
                ex_year = String.valueOf(cardToSave.getExpYear());
                getToken();

            }
        }
    }*/

    public void onClickPay(String holderName, String cardNumber, String cardExpMonth, String cardExpYear, String cardCVC) {
        cardToSave = new Card(
                cardNumber,
                Integer.parseInt(cardExpMonth),
                Integer.parseInt(cardExpYear),
                cardCVC
        );

        boolean validation = cardToSave.validateCard();
        if (validation) {
            new Stripe(getActivity()).createToken(
                    cardToSave,
                    Constants.STRIPE_PUBLISH_KEY,
                    new TokenCallback() {
                        @Override
                        public void onError(Exception error) {
                            Log.d("Stripe", error.toString());
                        }

                        @Override
                        public void onSuccess(Token token) {
                            addCardApi(stripetoken);
                            Log.d("TokenIs", token.getId().toString() + " ");
                        }
                    });
        } else if (!cardToSave.validateNumber()) {
            Toast.makeText(getActivity(), "The card number that you entered is invalid", Toast.LENGTH_SHORT).show();
        } else if (!cardToSave.validateExpiryDate()) {
            Toast.makeText(getActivity(), "The expiration date that you entered is invalid", Toast.LENGTH_SHORT).show();
        } else if (!cardToSave.validateCVC()) {
            Toast.makeText(getActivity(), "The CVC code that you entered is invalid", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "The card details that you entered are invalid", Toast.LENGTH_SHORT).show();
        }
    }
}






