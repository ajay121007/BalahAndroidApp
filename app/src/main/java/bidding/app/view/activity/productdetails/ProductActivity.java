package bidding.app.view.activity.productdetails;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import bidding.app.R;
import bidding.app.extra.CSPreferences;
import bidding.app.extra.Constants;
import bidding.app.model.ProductDeatils;
import bidding.app.model.WishStatusBean;
import bidding.app.view.activity.BaseActivity;
import bidding.app.view.activity.productdetails.adapter.ViewPagerAdapter;
import bidding.app.view.activity.productdetails.cartrequest.AddItemRequest;
import bidding.app.view.activity.productdetails.cartrequest.CartItem;
import bidding.app.view.activity.productdetails.presenter.ProductActivityPresenter;
import bidding.app.view.activity.productdetails.presenter.ProductActivityPresenterHandler;
import bidding.app.view.activity.productdetails.view.ProductActivityView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductActivity extends BaseActivity implements ProductActivityView, ViewPager.OnPageChangeListener {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.SliderDots)
    LinearLayout SliderDots;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_add_to_cart)
    TextView tvAddToCart;
    @BindView(R.id.tv_buy_now)
    TextView tvBuyNow;
    @BindView(R.id.iv_product)
    ImageView ivProduct;
    @BindView(R.id.rl_bottom_bar)
    LinearLayout rlBottomBar;
    @BindView(R.id.tv_product_name)
    TextView tvProductName;
    @BindView(R.id.tv_product_decription)
    TextView tvProductDecription;
    @BindView(R.id.tv_product_price)
    TextView tvProductPrice;
    @BindView(R.id.ivLike)
    ImageView ivLike;
    @BindView(R.id.ivDislike)
    ImageView ivDislike;
    @BindView(R.id.ivShare)
    ImageView ivShare;
    private int dotscount;
    private ImageView[] dots;
    ViewPagerAdapter viewPagerAdapter;
    private Integer[] images = {R.drawable.image_2, R.drawable.image_1, R.drawable.image_2, R.drawable.image_1, R.drawable.image_2};
    ProductActivityPresenterHandler handler;
    ProductDeatils productDeatils;
    Intent intent;
    String productId = "", mToken = "", mQuoteId = "", mDesc = "", skuId = "", navigationtatus = "0", wishListItemId = "";
    int isLike = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        strictModePermission();
        ButterKnife.bind(this);

        init();
        //setImageAdapter();
        listner();

    }

    private void strictModePermission() {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    private void init() {
        mToken = "Bearer " + CSPreferences.readString(this, Constants.TOKEN);
        intent = getIntent();
        handler = new ProductActivityPresenter(this, ProductActivity.this);

        Uri data = getIntent().getData();
        if (data != null && data.getPathSegments().size() >= 1) {
            List<String> params = data.getPathSegments();
            productId = params.get(0);
        }

        if (intent != null && intent.getExtras().containsKey("id"))
            productId = intent.getExtras().getString("id");

        handler.wishListDetailByID(this, mToken, productId);
    }

    private void listner() {
        viewPager.addOnPageChangeListener(this);
    }

    private void setImageAdapter() {

        viewPagerAdapter = new ViewPagerAdapter(this, images);
        viewPager.setAdapter(viewPagerAdapter);

        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for (int i = 0; i < dotscount; i++) {

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);
            SliderDots.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(), 2000, 4000);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < dotscount; i++) {
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active));
        }

        dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

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
    public void productDetails(ProductDeatils deatils) {

        skuId = deatils.getSku().toString();
        mDesc = deatils.getShort_description();
        if (mDesc != null && !mDesc.isEmpty())
            mDesc = mDesc.replaceAll("\\<.*?\\>", "");

        DecimalFormat f = new DecimalFormat("##.00");
        Picasso.with(this)
                .load(Constants.IMAGE_BASE_URL + deatils.getImage())
                .placeholder(R.drawable.loading)
                .into(ivProduct);

        tvProductName.setText(deatils.getName());
        tvProductDecription.setText(mDesc);
        tvProductPrice.setText("SAR " + f.format(Double.parseDouble(deatils.getPrice())) + "");

        if (isLike == 0) {
            ivLike.setVisibility(View.GONE);
            ivDislike.setVisibility(View.VISIBLE);
        } else {
            ivDislike.setVisibility(View.GONE);
            ivLike.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void quoteId(String id) {
        mQuoteId = id;

        AddItemRequest itemRequest = new AddItemRequest();
        CartItem cartItem = new CartItem();
        cartItem.setQty(1);
        cartItem.setSku(skuId);
        cartItem.setQuoteId(id);
        itemRequest.setCartItem(cartItem);

        handler.addToCart(mToken, itemRequest, navigationtatus);

        Log.d("SKUID", skuId.toString() + "  QUOTEID  " + id);
    }

    @Override
    public void wishSucces(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            JSONObject object = jsonArray.getJSONObject(0);
            WishStatusBean wishStatusBean = new Gson().fromJson(object + "", WishStatusBean.class);
            isLike = wishStatusBean.getResponse().getData().get(0).getIs_in_wishlist();
            wishListItemId = wishStatusBean.getResponse().getData().get(0).getId();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void removeFromWishListSucces(String response) {
        showToast(response + "");
        ivLike.setVisibility(View.GONE);
        ivDislike.setVisibility(View.VISIBLE);
    }

    @Override
    public void addWishListSucces(String success) {
        showToast(success + "");
        ivDislike.setVisibility(View.GONE);
        ivLike.setVisibility(View.VISIBLE);
    }


    @OnClick({R.id.back, R.id.tv_add_to_cart, R.id.tv_buy_now, R.id.ivLike, R.id.ivShare, R.id.ivDislike})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_add_to_cart:
                navigationtatus = "0";
                handler.quoteCreation(mToken);
                break;
            case R.id.tv_buy_now:
                navigationtatus = "1";
                handler.quoteCreation(mToken);
                break;
            case R.id.ivLike:
                handler.removeFromWhislist(this, mToken, wishListItemId);
                break;

            case R.id.ivDislike:
                handler.addToWhislist(this, mToken, productId);
                break;

            case R.id.ivShare:
                inviteIntent();
                break;
        }
    }


    public void inviteIntent() {

        Bitmap loadedImage = getBitmapFromURL(Constants.IMAGE_BASE_URL + "/d/a/dates3.jpeg");

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, "Check out this product on Balah! http://balah.com/" + productId);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), loadedImage, "", null);
        Uri screenshotUri = Uri.parse(path);

        intent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
        intent.setType("image/*");
        startActivity(Intent.createChooser(intent, "Share using"));

    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }


    private class SliderTimer extends TimerTask {

        @Override
        public void run() {
            ProductActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem() < images.length - 1) {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    } else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }
}