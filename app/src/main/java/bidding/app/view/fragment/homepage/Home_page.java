package bidding.app.view.fragment.homepage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.skyfishjy.library.RippleBackground;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import bidding.app.R;
import bidding.app.controller.AuctionDetailsById;
import bidding.app.controller.AuctionManager;
import bidding.app.controller.ModelManager;
import bidding.app.extra.SingleTapConfirm;
import bidding.app.extra.Validation;
import bidding.app.model.ActionString;
import bidding.app.model.AuctionBean;
import bidding.app.extra.CSPreferences;
import bidding.app.extra.Config;
import bidding.app.extra.Constants;
import bidding.app.extra.Event;
import bidding.app.extra.ItemClickListener;
import bidding.app.extra.Operations;
import bidding.app.extra.Utils;
import bidding.app.extra.CircularSeekBar;
import bidding.app.MyApplication;
import bidding.app.view.activity.addauction.AddAuctionActivity;
import bidding.app.view.fragment.BaseFragment;
import bidding.app.view.fragment.ProductImage;
import bidding.app.view.fragment.historyfragment.presenter.HistoryFragmentPresenter;
import bidding.app.view.fragment.historyfragment.presenter.HistoryFragmentPresenterHandler;
import bidding.app.view.fragment.historyfragment.view.HistoryFragmentView;
import bidding.app.view.fragment.homepage.presenter.HomeFragmentPresenter;
import bidding.app.view.fragment.homepage.presenter.HomeFragmentPresenterHandler;
import bidding.app.view.fragment.homepage.view.HomeFragmentView;
import de.hdodenhof.circleimageview.CircleImageView;

import static bidding.app.extra.Utils.getDateDiff;
import static bidding.app.extra.Utils.setTextValue;

public class Home_page extends BaseFragment implements HomeFragmentView, View.OnTouchListener {
    View view1;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    Context context;
    String higestbid;
    static int adapterpostion;
    TextView view, text_hoghestprice, text_qantity, text_timer, tv_timeover, tv_hours, tv_mins, tv_secs;
    ImageView productimage, image_submitbid, ivBid;
    ProgressDialog progressDialog;
    MainAdapterClassw mainAdapterClass;
    Date date1, date2;
    static int lastposition = 0;
    int currentprogress;
    double mTimeLeft = 0;
    Date timeend, timecurrent;
    String liststatus = "true";
    Dialog alert;
    public static String timestatus = "";
    String submitstatus = "false";
    public static ArrayList<String> auctionList = new ArrayList<String>();
    LinearLayout toplayout;
    CircularSeekBar SeekBar;
    String currentDateandTime = "", currentitme = "", mMinBid = "", mMaxBid = "", mExtendTime = "", hms="";
    String enddatetime = "", mStartDateTime = "";
    CountDownTimer countDownTimer;
    int selectedPos;
    AuctionBean auctionBean;
    String auctionstatus = "status_active.png";
    RippleBackground rippleBackground;
    LinearLayout iv_add, timer_layout;
    android.app.Fragment fragment;
    RelativeLayout layout;
    String imgPosition = "0";
    HomeFragmentPresenterHandler handler;
    private double mCurrAngle = 0;
    private double mPrevAngle = 0;
    GestureDetector detector;


    public static Home_page newInstance() {
        return new Home_page();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view1 = inflater.inflate(R.layout.neewwww, container, false);
        context = getActivity();
        hideProgressDialog();
        intiviews();
        listner();
        return view1;
    }

    private void listner() {
        image_submitbid.setOnTouchListener(this);
        SeekBar.setIsTouchEnabled(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MyApplication.isloading) {
            hideProgressDialog();
            hitFirstService();
        }
    }

    private void intiviews() {
        detector = new GestureDetector(getActivity(), new SingleTapConfirm());
        handler = new HomeFragmentPresenter(this);
        progressDialog = new ProgressDialog(context);
        view = (TextView) view1.findViewById(R.id.textview);
        iv_add = view1.findViewById(R.id.iv_add);
        layout = view1.findViewById(R.id.product_image);
        timer_layout = view1.findViewById(R.id.time_layout);
        productimage = (ImageView) view1.findViewById(R.id.product_image1);
        image_submitbid = (ImageView) view1.findViewById(R.id.image_submitbid);
        ivBid = view1.findViewById(R.id.iv_bid);
        rippleBackground = (RippleBackground) view1.findViewById(R.id.content);
        text_timer = (TextView) view1.findViewById(R.id.text_timer);
        tv_hours = (TextView) view1.findViewById(R.id.time1);
        tv_mins = (TextView) view1.findViewById(R.id.time2);
        tv_secs = (TextView) view1.findViewById(R.id.time3);
        tv_timeover = (TextView) view1.findViewById(R.id.tv_timeover);
        SeekBar = (CircularSeekBar) view1.findViewById(R.id.circularSeekBar1);
        SeekBar.setMax(99);

        recyclerView = (RecyclerView) view1.findViewById(R.id.recyclerview);
        toplayout = (LinearLayout) view1.findViewById(R.id.layout1);
        text_hoghestprice = (TextView) view1.findViewById(R.id.highestprice);
        text_qantity = (TextView) view1.findViewById(R.id.qantity_text);

        alert = new Dialog(context);
        alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alert.setContentView(R.layout.dialoglayout);
        alert.setCancelable(true);

        hitFirstService();

        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, AddAuctionActivity.class));
            }
        });

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Pos", imgPosition + "");
                Bundle bundle = new Bundle();
                bundle.putString("position", imgPosition + "");
                fragment = new ProductImage();
                fragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.framlayout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        ivBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!hms.isEmpty() && hms.contains(":")) {
                    Log.d("SecondLeft", Utils.timeToSecond(hms));
                    mTimeLeft = Double.parseDouble(Utils.timeToSecond(hms));
                    if (mTimeLeft < 30) {
                        mExtendTime = mExtendTime;
                    } else {
                        mExtendTime = "0";
                    }
                }

                boolean b;
                if (AuctionManager.list.size() > 0) {
                    if (timestatus == "left") {
                        b = Validation.getInstanse().isValidBid(getActivity(), currentprogress, mMinBid, mMaxBid);
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                        String timee = sdf.format(new Date());

                        if (currentprogress >= 1) {
                            if (b) {
                                if (CSPreferences.readString(context, "bid_status") != "1") {
                                    image_submitbid.setBackgroundResource(R.drawable.ic_icon_button_2);
                                    final Handler handle = new Handler();
                                    handle.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            image_submitbid.setBackgroundResource(R.drawable.ic_icon_button_out);
                                            SeekBar.setProgress(1);
                                            image_submitbid.clearAnimation();
                                        }
                                    }, 300);

                                    handler.submitBid(context, ActionString.submitbid, CSPreferences.readString(context, "user_id"), CSPreferences.readString(context, "auction_id"), view.getText().toString(), mExtendTime /*timee.replaceAll(" ", "")*/);

                                  /*  ModelManager.getInstance().getSubmitBidManager().SubmitBidManager(context, Operations.submitBid(context,
                                            ActionString.submitbid, CSPreferences.readString(context, "user_id"), CSPreferences.readString(context, "auction_id"), view.getText().toString(), timee).replaceAll(" ", ""));*/
                                } else {

                                    // Toast.makeText(context, "Black Auction is not started yet", Toast.LENGTH_SHORT).show();
                                }
                            } else {

                            }
                        } else {

                            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                            alertDialogBuilder.setMessage("لا يمكنك رفع المزاد بمقدار");
                            alertDialogBuilder.setPositiveButton(getResources().getString(R.string.yes),
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {

                                            arg0.dismiss();
                                        }
                                    });

                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        }


                    } else {
                        Toast.makeText(context, getResources().getString(R.string.auctionend), Toast.LENGTH_SHORT).show();
                    }
                } else

                {

                    Toast.makeText(context, getResources().getString(R.string.noauction), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void hitFirstService() {
        if (CSPreferences.readString(context, "noti_id").equals(null) || CSPreferences.readString(context, "noti_id").equals("")) {
            showProgressDialog();
            ModelManager.getInstance().getAuctionManager().AuctionManager(context, Operations.getallAcuntion(context,
                    ActionString.getall_auctions, CSPreferences.readString(context, "user_id"), ""));

        } else {
            showProgressDialog();
            ModelManager.getInstance().getAuctionManager().AuctionManager(context, Operations.getallAcuntion(context,
                    ActionString.getall_auctions, CSPreferences.readString(context, "user_id"), CSPreferences.readString(context, "noti_id")));
            CSPreferences.putString(context, "notiauctionid", "");
        }


    }

    @Override
    public void onStart() {
        super.onStart();
        imgPosition = "0";
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        /*progressView.setVisibility(View.GONE);*/
        switch (event.getKey()) {
            case Constants.CONECTED:
                if (event.isConnected()) {
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.str_internet_check), Toast.LENGTH_SHORT).show();
                }
                break;
            case Constants.Aunctionstatus:
                hideProgressDialog();
                setAdapter();
                CSPreferences.putString(context, "noti_id", "");
                break;
            case Constants.AUCTIONSETAILBYID:
                for (int i = 0; i < AuctionDetailsById.list.size(); i++) {
                    Log.d("listImg", AuctionDetailsById.list.get(i).getImage() + "");
                }
                mainAdapterClass.updateData(AuctionDetailsById.list);
                break;
            case Constants.ERROR:
                hideProgressDialog();
                if (alert != null && !alert.isShowing()) {
                    alert.show();
                }
                text_timer.setText("00:00:00");
                tv_hours.setText("00");
                tv_mins.setText("00");
                tv_secs.setText("00");


                TextView textnoti = (TextView) alert.findViewById(R.id.text_notimessage);
                TextView txtokay = (TextView) alert.findViewById(R.id.text_okay);
                textnoti.setText(getResources().getString(R.string.noauction));
                txtokay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.dismiss();
                    }
                });
                break;
            case Constants.Textupdate:
                String splitarray = event.getValue();
                Log.e("dddd", splitarray);
                break;
            case Constants.SUBMITBID:
                // rippleBackground.stopRippleAnimation();
                // progressDialog.dismiss();
                String eventmessage[] = event.getValue().split("-");
                String message = eventmessage[eventmessage.length - 2];
                String bidprice = eventmessage[eventmessage.length - 1];
                if (bidprice.equals("1")) {

                } else {
                    text_hoghestprice.setText(bidprice);
                }
                //image.setBackgroundResource(R.drawable.ic_icon_inner_rotator_bghelo);
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
                aftersubmit();

                break;
            case Constants.SERVER_ERROR:
                hideProgressDialog();
                Toast.makeText(context, "\n" +
                        getResources().getString(R.string.tryagain), Toast.LENGTH_SHORT).show();
                break;


        }
    }

    private void aftersubmit() {
        AuctionBean auctionBean1 = AuctionManager.list.get(lastposition);

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        liststatus = "false";
        CSPreferences.putString(context, "auction_id", auctionBean1.getAuction_id());
        ModelManager.getInstance().getAuctionManager().AuctionManager(context, Operations.getallAcuntion(context,
                ActionString.getall_auctions, CSPreferences.readString(context, "user_id"), auctionBean1.getAuction_id()));


    }

    private void setAdapter() {
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);

        if (mLayoutManager.getItemCount() > 0) {
            mainAdapterClass.updateData(AuctionManager.list);
            //mainAdapterClass.notifyDataSetChanged();
        } else {
            mainAdapterClass = new MainAdapterClassw(context, AuctionManager.list);
            recyclerView.setAdapter(mainAdapterClass);
        }


    }

    private void setTolastPosition(ArrayList<AuctionBean> list) {
        if (lastposition != 0) {
            recyclerView.smoothScrollToPosition(lastposition);

            adapterpostion = (lastposition);
            AuctionBean auctionBean1 = list.get(lastposition);
            Log.d("LogPosition", lastposition + "");
            if (countDownTimer != null) {
                countDownTimer.cancel();
                countDownTimer = null;
            }
            imgPosition = String.valueOf(lastposition);
            Log.e("ddd", auctionBean1.getAuction_id());
            liststatus = "false";
            CSPreferences.putString(context, "auction_id", auctionBean1.getAuction_id());
            ModelManager.getInstance().getAuctionDetailsById().AuctionDetailsById(context, Operations.getallAcuntion(context,
                    ActionString.getall_auctions, CSPreferences.readString(context, "user_id"), auctionBean1.getAuction_id()));

        }
    }


    public void onProgressChanged(int progress) {

        currentprogress = progress;
        SeekBar.setLockEnabled(false);

        if (AuctionManager.list.size() >= 1) {
            currentprogress = progress;
            setTextValue(context, view, currentprogress + "");
            SeekBar.setProgress(progress);
            SeekBar.setCircleColor(getResources().getColor(R.color.colorGray));
        } else {
            setTextValue(context, view, progress + "");
            SeekBar.setProgress(progress);
            SeekBar.setCircleColor(getResources().getColor(R.color.colorGray));
        }


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
    public void allAuctionSuccess(ArrayList<AuctionBean> list) {

    }

    @Override
    public void bidSuccess(String message) {

    }

    @Override
    public void auctionById(ArrayList<AuctionBean> list) {

    }

    @Override
    public boolean onTouch(View myView, MotionEvent event) {

      /*  if (detector.onTouchEvent(event)) {
            // single tap
            if (AuctionManager.list.size() > 0) {

                if (timestatus == "left") {
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                    String timee = sdf.format(new Date());
                    if (currentprogress >= 1) {
                        if (CSPreferences.readString(context, "bid_status") != "1") {
                            image_submitbid.setBackgroundResource(R.drawable.ic_icon_button_2);
                            final Handler handle = new Handler();
                            handle.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    image_submitbid.setBackgroundResource(R.drawable.ic_icon_button_out);
                                    SeekBar.setProgress(1);
                                }
                            }, 300);

                            handler.submitBid(context, ActionString.submitbid, CSPreferences.readString(context, "user_id"), CSPreferences.readString(context, "auction_id"), view.getText().toString(), timee.replaceAll(" ", ""));

                                *//*ModelManager.getInstance().getSubmitBidManager().SubmitBidManager(context, Operations.submitBid(context,
                                        ActionString.submitbid, CSPreferences.readString(context, "user_id"), CSPreferences.readString(context, "auction_id"), view.getText().toString(), timee).replaceAll(" ", ""));

                            *//*
                        } else {

                            // Toast.makeText(context, "Black Auction is not started yet", Toast.LENGTH_SHORT).show();
                        }
                    } else {

                        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                        alertDialogBuilder.setMessage("لا يمكنك رفع المزاد بمقدار");
                        alertDialogBuilder.setPositiveButton(getResources().getString(R.string.yes),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {

                                        arg0.dismiss();
                                    }
                                });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                } else {
                    SeekBar.setProgress(1);
                    view.setText("1");
                    image_submitbid.clearAnimation();
                    Toast.makeText(context, getResources().getString(R.string.auctionend), Toast.LENGTH_SHORT).show();
                }
            } else {

                Toast.makeText(context, getResources().getString(R.string.noauction), Toast.LENGTH_SHORT).show();
            }
            return true;
        } else {*/
        final float xc = image_submitbid.getWidth() / 2;
        final float yc = image_submitbid.getHeight() / 2;

        final float x = event.getX();
        final float y = event.getY();

        double mProgress = 0;
        int mSeekbarProgress = 0;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                image_submitbid.clearAnimation();
                mCurrAngle = Math.toDegrees(Math.atan2(x - xc, yc - y));

                animate(mPrevAngle, mCurrAngle, 0);
                if (mCurrAngle < 0) {
                    mProgress = 360 + (mCurrAngle);
                    mSeekbarProgress = (int) ((int) mProgress / 3.6);
                    SeekBar.setProgress(mSeekbarProgress);
                } else {
                    mSeekbarProgress = (int) ((int) mCurrAngle / 3.6);
                    SeekBar.setProgress(mSeekbarProgress);

                }
                System.out.println(mSeekbarProgress);
                onProgressChanged(mSeekbarProgress);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                mPrevAngle = mCurrAngle;
                mCurrAngle = Math.toDegrees(Math.atan2(x - xc, yc - y));
                animate(mPrevAngle, mCurrAngle, 0);
                if (mCurrAngle < 0) {
                    mProgress = 360 + (mCurrAngle);
                    mSeekbarProgress = (int) ((int) mProgress / 3.6);
                    SeekBar.setProgress(mSeekbarProgress);
                } else {
                    mSeekbarProgress = (int) ((int) mCurrAngle / 3.6);
                    SeekBar.setProgress(mSeekbarProgress);

                }
                System.out.println(mSeekbarProgress);
                onProgressChanged(mSeekbarProgress);
                break;
            }
            case MotionEvent.ACTION_UP: {
                mPrevAngle = mCurrAngle = 0;
                break;
            }
        }

        return true;

    }

    private void animate(double fromDegrees, double toDegrees, long durationMillis) {
        final RotateAnimation rotate = new RotateAnimation((float) fromDegrees, (float) toDegrees,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(durationMillis);
        rotate.setFillEnabled(true);
        rotate.setFillAfter(true);
        image_submitbid.startAnimation(rotate);
        System.out.println(mCurrAngle);
    }
// create a Adappter to bind the auction data at the bottom of the design

    public class MainAdapterClassw extends RecyclerView.Adapter<MainAdapterClassw.ViewHolder> {
        ArrayList<AuctionBean> list;
        Context context;

        public MainAdapterClassw(Context context, ArrayList<AuctionBean> list) {
            super();
            this.context = context;
            this.list = list;
        }

        private void customNotify(ArrayList<AuctionBean> list) {
            this.list = list;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.customelayout, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, int i) {
            viewHolder.imgThumbnail.setTag(i);
            auctionBean = list.get(i);
            Picasso.with(getActivity())
                    .load(Config.imageurl + auctionBean.getmAllImage())
                    .placeholder(R.mipmap.ic_launcher)
                    .into(viewHolder.imgThumbnail);
            viewHolder.top_sr_no.setText(auctionBean.getAuction_id());
            viewHolder.view2.setVisibility(View.GONE);
            viewHolder.itemView.setSelected(selectedPos == i);
            viewHolder.bottom_sr_no.setText(auctionBean.getReserve_price());
            if (auctionBean.getStatus().equals("1")) {
                mMinBid = auctionBean.getMinBid();
                mMaxBid = auctionBean.getMaxBid();
                mExtendTime = auctionBean.getExtendtime();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                currentitme = sdf.format(new Date());
                currentDateandTime = sdf.format(new Date());
                SeekBar.setProgress(0);
                CSPreferences.putString(context, "bid_status", auctionBean.getAuction_status());
                //adapterpostion = (i + 1);
                Log.e("adposs", i + "");
                image_submitbid.setRotation(0);
                enddatetime = auctionBean.getEnddate() + " " + auctionBean.getEndtime();
                mStartDateTime = auctionBean.getmStartDate() + " " + auctionBean.getmStartTime();
                String dateDifference = getDateDiff(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), currentDateandTime, enddatetime);
                String totalTime = getDateDiff(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), mStartDateTime, enddatetime);
                Log.e("dateDifference: ", dateDifference + "");
                Log.e("TotalDifference: ", totalTime + "");
                if (!dateDifference.isEmpty() && Long.parseLong(dateDifference) > 0 && Long.parseLong(dateDifference) < Long.parseLong(totalTime)) {
                    //text_timer.setVisibility(View.VISIBLE);
                    tv_timeover.setVisibility(View.GONE);
                    timer_layout.setVisibility(View.VISIBLE);
                    startTimer(Long.parseLong(dateDifference));
                } else {
                    stopTimer();
                    //Toast.makeText(context, getResources().getString(R.string.auctionend), Toast.LENGTH_SHORT).show();
                    text_timer.setVisibility(View.GONE);
                    timer_layout.setVisibility(View.GONE);
                    tv_timeover.setVisibility(View.VISIBLE);
                    timestatus = "over";
                }
                higestbid = auctionBean.getHighest_bid();
                String pricee = auctionBean.getHighest_bid();
                CSPreferences.putString(context, "bid_price", pricee);
                String qantity1 = auctionBean.getQuantity();
                CSPreferences.putString(context, "auction_id", auctionBean.getAuction_id());
                viewHolder.view2.setVisibility(View.VISIBLE);
                text_hoghestprice.setText(pricee);
                setTextValue(context, view, "0" + "");
                text_qantity.setText(qantity1);
                Picasso.with(context)
                        .load(Config.imageurl + auctionBean.getImage())
                        .placeholder(R.mipmap.ic_launcher)
                        .into(productimage);

            }

            viewHolder.setClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                    adapterpostion = (position + 1);
                    AuctionBean auctionBean1 = list.get(position);
                    Log.d("LogPosition", position + "");
                    if (countDownTimer != null) {
                        countDownTimer.cancel();
                        countDownTimer = null;
                    }
                    imgPosition = String.valueOf(position);
                    Log.e("ddd", auctionBean1.getAuction_id());
                    liststatus = "false";
                    CSPreferences.putString(context, "auction_id", auctionBean1.getAuction_id());
                    ModelManager.getInstance().getAuctionDetailsById().AuctionDetailsById(context, Operations.getallAcuntion(context,
                            ActionString.getall_auctions, CSPreferences.readString(context, "user_id"), auctionBean1.getAuction_id()));


                }
            });
            Log.d("auctiond", auctionBean.getAuction_status() + "");

            switch (auctionBean.getAuction_status()) {

                case "3":
                    viewHolder.imgThumbnail.setBorderColor(getResources().getColor(R.color.colorGreen));
                    //viewHolder.imgThumbnail.setImageResource(R.drawable.status_active);
                    break;
                case "1":
                    viewHolder.imgThumbnail.setBorderColor(getResources().getColor(R.color.gray));
                    //viewHolder.imgThumbnail.setImageResource(R.drawable.status_pending);
                    break;
                case "4":
                    viewHolder.imgThumbnail.setBorderColor(getResources().getColor(R.color.light_yellow));
                    //viewHolder.imgThumbnail.setImageResource(R.drawable.ic_icon_auction_red);
                    break;
                case "2":
                    viewHolder.imgThumbnail.setBorderColor(getResources().getColor(R.color.colorBrown));
                    //viewHolder.imgThumbnail.setImageResource(R.drawable.button_icon_);
                    break;


            }
        }

        private void compareTimes(String endtime) throws ParseException {

            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            timeend = dateFormat.parse(endtime);

            timecurrent = dateFormat.parse(dateFormat.format(new Date()));

            if (timecurrent.after(timeend)) {
                Log.e("time is finish", "");
            }
        }


        // here we start the the timer for a particular auction
        private void startTimer(long noOfMinutes) {

            countDownTimer = null;
            countDownTimer = new CountDownTimer(noOfMinutes, 1000) {
                public void onTick(long millisUntilFinished) {
                    timestatus = "left";
                    SeekBar.setIsTouchEnabled(false);
                    long millis = millisUntilFinished;
                    //Convert milliseconds into hour,minute and seconds

                    hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));

                    text_timer.setText(hms);
                    String time[] = text_timer.getText().toString().split(":");
                    tv_hours.setText(time[0]);
                    tv_mins.setText(time[1]);
                    tv_secs.setText(time[2]);
                }

                public void onFinish() {
                    timestatus = "over";
                    text_timer.setText("00:00:00");
                    tv_hours.setText("00");
                    tv_mins.setText("00");
                    tv_secs.setText("00");
                }
            }.start();

        }

        private void stopTimer() {
            if (countDownTimer != null) {
                countDownTimer.cancel();
                countDownTimer.onFinish();
                countDownTimer = null;
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public void updateData(ArrayList<AuctionBean> countryList) {
            this.list = countryList;
            notifyDataSetChanged();
        }


        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

            public CircleImageView imgThumbnail;
            public TextView top_sr_no;
            public TextView bottom_sr_no;
            View view2;
            private ItemClickListener clickListener;

            public ViewHolder(View itemView) {
                super(itemView);
                imgThumbnail = itemView.findViewById(R.id.imageview);
                top_sr_no = itemView.findViewById(R.id.textsrno);
                bottom_sr_no = itemView.findViewById(R.id.textbottom_srno);
                view2 = itemView.findViewById(R.id.view1);
                itemView.setOnClickListener(this);
                itemView.setOnLongClickListener(this);
            }

            public void setClickListener(ItemClickListener itemClickListener) {
                this.clickListener = itemClickListener;

            }

            @Override
            public void onClick(View view) {

                clickListener.onClick(view, getAdapterPosition(), false);
              /*adapterpostion =getAdapterPosition();
                auctionstatus =auctionList.get(getAdapterPosition());*/
                lastposition = getAdapterPosition();
            }

            @Override
            public boolean onLongClick(View view) {
                clickListener.onClick(view, getAdapterPosition(), true);
                return true;
            }


        }


    }


    @Override
    public void onStop() {
        super.onStop();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

}
