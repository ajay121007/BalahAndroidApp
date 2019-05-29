package bidding.app.view.activity.addauction;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import bidding.app.R;
import bidding.app.extra.CSPreferences;
import bidding.app.extra.Constants;
import bidding.app.extra.Event;
import bidding.app.extra.Utils;
import bidding.app.model.AuctionAddedBean;
import bidding.app.view.activity.BaseActivity;
import bidding.app.view.activity.addauction.presenter.AuctionActivityPresenter;
import bidding.app.view.activity.addauction.presenter.AuctionActivityPresenterHandler;
import bidding.app.view.activity.addauction.request.AddAuctionRequest;
import bidding.app.view.activity.addauction.view.AuctionActivityView;
import bidding.app.view.activity.sellerproductactivity.SellerProductActivity;
import bidding.app.view.activity.sellerproductactivity.view.SellerActivityView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Thakur on 12/19/2017.
 */

public class AddAuctionActivity extends BaseActivity implements AuctionActivityView, View.OnClickListener {
    EditText reserve_price, base_price, station;
    Button addauction;
    @BindView(R.id.ll_expiry_date)
    LinearLayout llExpiryDate;
    @BindView(R.id.ed_min_bid)
    EditText edMinBid;
    @BindView(R.id.ed_max_bid)
    EditText edMaxBid;
    @BindView(R.id.ed_extend_time)
    EditText edExtendTime;
    @BindView(R.id.ed_number_winner)
    EditText edNumberWinner;
    @BindView(R.id.tv_expire_date)
    TextView tvExpireDate;
    @BindView(R.id.edMinQuntity)
    EditText edMinQuntity;
    @BindView(R.id.edMaxQuntity)
    EditText edMaxQuntity;
    @BindView(R.id.btnSelectProduc)
    Button btnSelectProduc;
    @BindView(R.id.tv_reserveprice)
    TextView tvReserveprice;
    @BindView(R.id.reserve_price)
    EditText reservePrice;
    @BindView(R.id.tv_baseprice)
    TextView tvBaseprice;
    @BindView(R.id.base_price)
    EditText basePrice;
    @BindView(R.id.start_date)
    TextView startDate;
    @BindView(R.id.end_date)
    TextView endDate;
    @BindView(R.id.ad_auction)
    Button adAuction;

    @BindView(R.id.tvProductName)
    TextView tvProductName;


    private boolean b = true;

    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    TimePickerDialog.OnTimeSetListener time;
    private int state = 0;


    Context context;
    DatePickerDialog datePickerDialog;

    ImageView ivBack;
    private String mToken = "",  mExtendtime = "";

    TextView start_date, end_date, tvReservePrice, tvBasePrice;
    AuctionActivityPresenterHandler mPresenter;

    AuctionAddedBean bean;



    public static String mProductName = "",mProductId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_auction);
        ButterKnife.bind(this);
        context = this;
        init();

        listner();
    }



    private void init() {

        mProductName="";
        mProductId="";

        mToken = CSPreferences.readString(AddAuctionActivity.this, Constants.TOKEN);
        mPresenter = new AuctionActivityPresenter(this);
        ivBack = (ImageView) findViewById(R.id.back);
        myCalendar = Calendar.getInstance();
        start_date = findViewById(R.id.start_date);
        end_date = findViewById(R.id.end_date);
        tvReservePrice = findViewById(R.id.tv_reserveprice);
        tvBasePrice = findViewById(R.id.tv_baseprice);
        reserve_price = findViewById(R.id.reserve_price);
        base_price = findViewById(R.id.base_price);
        station = findViewById(R.id.station);
        addauction = findViewById(R.id.ad_auction);
        station = findViewById(R.id.station);
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minute);
                updateLabel();
            }
        };

    }

    private void listner() {
        ivBack.setOnClickListener(this);
        addauction.setOnClickListener(this);
        start_date.setOnClickListener(this);
        end_date.setOnClickListener(this);
    }

    private void updateLabel() {
        String myDate = "yyyy-MM-dd";
        String myTime = "HH:mm:ss ";
        //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myDate, Locale.US);
        SimpleDateFormat sdf1 = new SimpleDateFormat(myTime, Locale.US);


        if (state == 1) {
            start_date.setText(sdf.format(myCalendar.getTime()));
            state = 2;
            new TimePickerDialog(context, time, myCalendar
                    .get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), false)
                    .show();
        } else if (state == 2) {
            start_date.append(" " + sdf1.format(myCalendar.getTime()));
        } else if (state == 3) {
            end_date.setText(sdf.format(myCalendar.getTime()));
            state = 4;
            new TimePickerDialog(context, time, myCalendar
                    .get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), false)
                    .show();

        } else if (state == 4) {
            end_date.append(" " + sdf1.format(myCalendar.getTime()));
        }

    }
    @Override
    protected void onResume() {
        super.onResume();
        if (!mProductId.isEmpty()){
            tvProductName.setText(mProductName);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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
    public void onSuccessfulyAuctionAdded(String response) {

        try {
            JSONArray jsonArray = new JSONArray(response);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            bean = new Gson().fromJson(jsonObject.toString(), AuctionAddedBean.class);
            if (bean.getResponse().getStatus() == 1) {
                showToast(getString(R.string.auction_successfully));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ad_auction:
                validate_AddAuction();
                break;
            case R.id.start_date:

                Utils.hideKeyboardFrom(this, start_date);
                state = 1;
                datePickerDialog = new DatePickerDialog(context, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
                break;

            case R.id.end_date:
                Utils.hideKeyboardFrom(this, end_date);
                state = 3;
                dateDialog();
                break;

            case R.id.back:
                finish();
                overridePendingTransitionExit();
                break;
        }

    }

    private void validate_AddAuction() {
        AddAuctionRequest request = new AddAuctionRequest();
        AddAuctionRequest.AuctionBean auctionBean;
        final String startDate, endDate, mStation, mAuction_title, mAuction_description;

        int reserve_Price = 0, base_Price = 0, minBid = 0, maxBid = 0, numWinner = 0, expiryday = 0, minQuntity = 0, maxQuntity = 0;

        reserve_Price = Integer.parseInt(reserve_price.getText().toString().trim());
        base_Price = Integer.parseInt(base_price.getText().toString().trim());
        minBid = Integer.parseInt(edMinBid.getText().toString().trim());
        maxBid = Integer.parseInt(edMaxBid.getText().toString().trim());
        numWinner = Integer.parseInt(edNumberWinner.getText().toString().trim());
        expiryday = Integer.parseInt(tvExpireDate.getText().toString().trim());
        minQuntity = Integer.parseInt(edMinQuntity.getText().toString().trim());
        maxQuntity = Integer.parseInt(edMaxQuntity.getText().toString().trim());
        startDate = start_date.getText().toString().trim();
        endDate = end_date.getText().toString().trim();


        mStation = station.getText().toString().trim();
        mExtendtime = edExtendTime.getText().toString().trim();


        // b = Validation.getInstanse().isValidAuction(context, Quntity, mQuntitiy, reserve_Price, base_Price, mStation, mAuction_title, startDate, endDate, mAuction_description, minBid, maxBid, extendtime);

        if (b) {


            String sdate = startDate;
            String edate = endDate;

            DateFormat oldFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            Date date1 = null;
            Date date2 = null;
            try {
                date1 = oldFormat.parse(sdate);
                date2 = oldFormat.parse(edate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (date2.after(date1)) {

                auctionBean = new AddAuctionRequest.AuctionBean(mProductId, mProductName, base_Price, reserve_Price, minBid, maxBid, startDate, endDate, "1", minQuntity, maxQuntity, numWinner, expiryday);
                request.setAuction(auctionBean);
                mPresenter.addAuction(this, "Bearer " + mToken, request);

            } else {
                Toast.makeText(context, "Please select valid end time.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void dateDialog() {
        datePickerDialog = new DatePickerDialog(context, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }


    @Subscribe
    public void onEvent(Event event) {
        switch (event.getKey()) {

            case Constants.CONECTED:
                if (event.isConnected()) {
                } else {
                    Toast.makeText(this, getResources().getString(R.string.str_internet_check), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @OnClick(R.id.btnSelectProduc)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSelectProduc:
                startActivity(new Intent(this, SellerProductActivity.class));
                break;
        }
    }
}
