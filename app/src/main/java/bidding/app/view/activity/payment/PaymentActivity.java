package bidding.app.view.activity.payment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import bidding.app.R;
import bidding.app.extra.CSPreferences;
import bidding.app.extra.Constants;
import bidding.app.extra.Event;
import bidding.app.extra.MyTextView;
import bidding.app.extra.Utils;
import bidding.app.model.CardDetailsBean;
import bidding.app.view.activity.BaseActivity;
import bidding.app.view.activity.payment.presenter.PaymentActivityPresenter;
import bidding.app.view.activity.payment.presenter.PaymentActivityPresenterHandler;
import bidding.app.view.activity.payment.view.PaymentActivityView;
import bidding.app.view.adapter.CardAddAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;

public class PaymentActivity extends BaseActivity implements PaymentActivityView, TextWatcher {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appBar)
    AppBarLayout appBar;
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.clickPay)
    TextView clickPay;
    @BindView(R.id.ll_addpayment)
    LinearLayout llAddpayment;
    @BindView(R.id.rl_card_list)
    RelativeLayout rlCardList;
    @BindView(R.id.card_holder)
    EditText cardHolder;
    @BindView(R.id.card_number)
    EditText cardNumber;
    @BindView(R.id.sp_month)
    Spinner spMonth;
    @BindView(R.id.sp_year)
    Spinner spYear;
    @BindView(R.id.cvv)
    EditText cvv;
    @BindView(R.id.scan_c)
    TextView scanC;
    @BindView(R.id.cancel)
    Button cancel;
    @BindView(R.id.add_card)
    Button addCard;
    @BindView(R.id.ll_addcard)
    LinearLayout llAddcard;
    ArrayAdapter<String> adapteryear;
    ArrayAdapter<String> adaptemonth;
    ArrayList<String> year = new ArrayList<>();
    String month[] = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    PaymentActivityPresenterHandler handler;
    private CardDetailsBean bean;
    List<CardDetailsBean.Datum> datumList;
    CardAddAdapter arrayAdapter;
    String a, cardHolderName = "", cardnumber = "", exMnth = "", exYear = "", cvc = "", stripetoken = "", userId = "";
    int keyDel;
    private Card cardToSave;
    private LinearLayoutManager recylerViewLayoutManager;
    private static final int REQUEST_SCAN = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.bind(this);
        init();
        getCardApi();
    }

    private void init() {
        userId = CSPreferences.readString(this, "user_id");
        handler = new PaymentActivityPresenter(this);
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        int next50Year = thisYear + 50;
        for (int i = thisYear; i <= next50Year; i++) {
            year.add(Integer.toString(i));
        }

        adapteryear = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, year);
        adaptemonth = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, month);
        adapteryear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adaptemonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spYear.setAdapter(adapteryear);
        spMonth.setAdapter(adaptemonth);

        cardNumber.addTextChangedListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransitionExit();
    }


    @Override
    public void showProgess() {
        showProgressDialog();
    }

    @Override
    public void hideProgess() {
        hideProgressDialog();
    }

    @Override
    public void showFeedBackMessage(String message) {
        showToast(message);
    }

    @Override
    public void getCardSuccess(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject mainResponse = jsonObject.getJSONObject("response");
            if (mainResponse.has("data") && (mainResponse.getJSONArray("data").length()) != 0) {
                bean = new Gson().fromJson(response, CardDetailsBean.class);
                llAddcard.setVisibility(View.GONE);
                rlCardList.setVisibility(View.VISIBLE);
                setAdapter();
            } else {
                rlCardList.setVisibility(View.GONE);
                llAddcard.setVisibility(View.VISIBLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setAdapter() {
        recylerViewLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(recylerViewLayoutManager);
        datumList = bean.getResponse().getData();
        Log.d("Size", datumList.size() + "");
        arrayAdapter = new CardAddAdapter(this, datumList);
        list.setAdapter(arrayAdapter);
    }

    @Override
    public void addCardSuccess(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject mainresp = jsonObject.getJSONObject("response");
            String apistatus = mainresp.getString("status");
            if (apistatus.equals("0")) {
                Toast.makeText(this, "Server Error", Toast.LENGTH_SHORT).show();
            } else if (apistatus.equals("1")) {
                Toast.makeText(this, mainresp.getString("message"), Toast.LENGTH_SHORT).show();
                rlCardList.setVisibility(View.VISIBLE);
                llAddcard.setVisibility(View.GONE);
                handler.getCard(this, "fetchCardDetails", CSPreferences.readString(this, "user_id"));
            } else if (apistatus.equals("2")) {
                Toast.makeText(this, mainresp.getString("message"), Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.ll_addpayment, R.id.ll_addcard, R.id.back, R.id.add_card, R.id.cancel, R.id.scan_c})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_addpayment:
                rlCardList.setVisibility(View.GONE);
                llAddcard.setVisibility(View.VISIBLE);
                cardHolder.setText("");
                cardNumber.setText("");
                cvv.setText("");
                break;
            case R.id.ll_addcard:

                break;

            case R.id.scan_c:
                scanCard();
                break;

            case R.id.add_card:
                cardHolderName = cardHolder.getText().toString();
                cardnumber = cardNumber.getText().toString();
                exMnth = spMonth.getSelectedItem().toString();
                exYear = spYear.getSelectedItem().toString();
                cvc = cvv.getText().toString();
                if (cardHolderName.isEmpty() || cardnumber.isEmpty() || exMnth.isEmpty() || exYear.isEmpty() || cvc.isEmpty()) {
                    Toast.makeText(this, "Please fill all fileds.", Toast.LENGTH_SHORT).show();
                } else {
                    Utils.hideKeyboardFrom(this, addCard);
                    onClickPay(cardHolderName, cardnumber, exMnth, exYear, cvc);
                }
                break;
            case R.id.back:
                finish();
                overridePendingTransitionExit();
                break;
            case R.id.cancel:

                break;
        }
    }

    private void scanCard() {
        Intent intent = new Intent(this, CardIOActivity.class)
                .putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true)
                .putExtra(CardIOActivity.EXTRA_SCAN_EXPIRY, true)
                .putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, true)
                .putExtra(CardIOActivity.EXTRA_REQUIRE_CARDHOLDER_NAME, true)
                .putExtra(CardIOActivity.EXTRA_LANGUAGE_OR_LOCALE, "en")
                .putExtra(CardIOActivity.EXTRA_GUIDE_COLOR, Color.GREEN)
                .putExtra(CardIOActivity.EXTRA_RETURN_CARD_IMAGE, true);
        startActivityForResult(intent, REQUEST_SCAN);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        switch (event.getKey()) {
            case Constants.CONECTED:
                if (event.isConnected()) {

                } else {
                    Toast.makeText(this, getResources().getString(R.string.str_internet_check), Toast.LENGTH_SHORT).show();
                }
                break;
            case Constants.DELETECARDSUCCESS:
                getCardApi();
                break;
            case Constants.DEFAULTCARD:
                arrayAdapter.customNotify(datumList, this);
                break;

        }
    }

    private void getCardApi() {
        handler.getCard(this, "fetchCardDetails", CSPreferences.readString(this, "user_id"));
    }

    public void addCardApi(final String tok) {
        handler.addCard(this, "addCardDetails", userId, cardnumber, cardHolderName, exMnth, exYear, cvc, cardToSave.getBrand(), tok);
    }

    private void onClickPay(String cardHolderName, String cardnumber, String exMnth, String exYear, String cvc) {
        cardToSave = new Card(
                cardnumber,
                Integer.parseInt(exMnth),
                Integer.parseInt(exYear),
                cvc
        );

        boolean validation = cardToSave.validateCard();
        if (validation) {
            showProgressDialog();
            new Stripe(this).createToken(
                    cardToSave,
                    Constants.STRIPE_PUBLISH_KEY,
                    new TokenCallback() {
                        @Override
                        public void onError(Exception error) {
                            hideProgressDialog();
                            Log.d("Stripe", error.toString());
                        }

                        @Override
                        public void onSuccess(Token token) {
                            hideProgressDialog();
                            stripetoken = token.getId().toString();
                            addCardApi(stripetoken);
                        }
                    });
        } else if (!cardToSave.validateNumber()) {
            Toast.makeText(this, "The card number that you entered is invalid", Toast.LENGTH_SHORT).show();
        } else if (!cardToSave.validateExpiryDate()) {
            Toast.makeText(this, "The expiration date that you entered is invalid", Toast.LENGTH_SHORT).show();
        } else if (!cardToSave.validateCVC()) {
            Toast.makeText(this, "The CVC code that you entered is invalid", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "The card details that you entered are invalid", Toast.LENGTH_SHORT).show();

        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == REQUEST_SCAN) && data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
            String resultDisplayStr;
            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);
                resultDisplayStr = "Card Number: " + scanResult.getRedactedCardNumber() + "\n";
                cardNumber.setText(scanResult.getRedactedCardNumber());
                if (scanResult.isExpiryValid()) {
                    resultDisplayStr += "Expiration Date: " + scanResult.expiryMonth + "/" + scanResult.expiryYear + "\n";
                }
                if (scanResult.cvv != null) {
                    resultDisplayStr += "CVV has " + scanResult.cvv.length() + " digits.\n";
                }
                if (scanResult.postalCode != null) {
                    resultDisplayStr += "Postal Code: " + scanResult.postalCode + "\n";
                }
            } else {
                resultDisplayStr = "Scan was canceled.";
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        boolean flag = true;

        String eachBlock[] = cardNumber.getText().toString().split("-");
        for (int j = 0; j < eachBlock.length; j++) {
            if (eachBlock[j].length() > 4) {
                flag = false;
            }
        }
        if (flag) {

            cardNumber.setOnKeyListener(new View.OnKeyListener() {

                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {

                    if (keyCode == KeyEvent.KEYCODE_DEL)
                        keyDel = 1;
                    return false;
                }
            });

            if (keyDel == 0) {

                if (((cardNumber.getText().length() + 1) % 5) == 0) {

                    if (cardNumber.getText().toString().split("-").length <= 3) {
                        cardNumber.setText(cardNumber.getText() + "-");
                        cardNumber.setSelection(cardNumber.getText().length());
                    }
                }
                a = cardNumber.getText().toString();
            } else {
                a = cardNumber.getText().toString();
                keyDel = 0;
            }

        } else {
            cardNumber.setText(a);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }


}
