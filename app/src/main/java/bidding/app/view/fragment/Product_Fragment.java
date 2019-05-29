package bidding.app.view.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Currency;

import bidding.app.R;
import bidding.app.controller.ModelManager;
import bidding.app.controller.ProductManager;
import bidding.app.extra.Utils;
import bidding.app.model.ActionString;
import bidding.app.extra.CSPreferences;
import bidding.app.extra.Constants;
import bidding.app.extra.Event;
import bidding.app.extra.ItemClickListener;
import bidding.app.extra.Operations;
import bidding.app.model.ProductBean;
import bidding.app.view.fragment.homepage.Home_page;

/**
 * Created by vijay on 18/8/17.
 */

public class Product_Fragment extends BaseFragment {

    View view1;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    Product_Adapterr product_adapter;
    Context context;
    ArrayList<String> name;
    ImageView backimage;
    LinearLayout linearLayout;
    ImageView productimage;
    TextView plus_text, minus_text, qantity_text, addcart_text, productprice_text, productname_text, txtdetail, titletext, tvCurrency;
    int qantityvalue = 1;
    double totalprice, singleprice;
    private Handler mHandler = new Handler();
    DecimalFormat df;
    String cardNumber = "";


    public static Product_Fragment newInstance() {
        return new Product_Fragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view1 = inflater.inflate(R.layout.productfragment, container, false);
        recyclerView = (RecyclerView) view1.findViewById(R.id.recycler);
        linearLayout = (LinearLayout) view1.findViewById(R.id.detaillayout);
        df = new DecimalFormat("###.00");
        linearLayout.setVisibility(View.GONE);
        cardNumber = CSPreferences.readString(getActivity(), Constants.SELECTEDCARDNUMBER);
        secondLayout();
        hideProgressDialog();
        hitService();
        return view1;

        // Inflate the layout for this fragment

    }

    private void secondLayout() {
        productimage = view1.findViewById(R.id.productimage);
        plus_text = view1.findViewById(R.id.textplus);
        minus_text = view1.findViewById(R.id.textminus);
        qantity_text = view1.findViewById(R.id.text_qantity);
        addcart_text = view1.findViewById(R.id.textadd_cart);
        minus_text = view1.findViewById(R.id.textminus);
        backimage = view1.findViewById(R.id.backimage);
        productprice_text = view1.findViewById(R.id.textproduct_price);
        productname_text = view1.findViewById(R.id.text_productname);
        txtdetail = view1.findViewById(R.id.text_detail);
        titletext = view1.findViewById(R.id.text_title);
        tvCurrency = view1.findViewById(R.id.tv_currrency);

        if (cardNumber != null && !cardNumber.isEmpty()) {
            addcart_text.setText(getString(R.string.buy_now));
        } else {
            addcart_text.setText(getString(R.string.addtocart));
        }
    }

    private void hitService() {

        showProgressDialog();
        ModelManager.getInstance().getProductManager().ProductManager(context, Operations.getProduct(context,
                ActionString.allproductaction, ""));


    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        switch (event.getKey()) {
            case Constants.Productstatus:
                hideProgressDialog();
                setAdapter();
                break;
            case Constants.ProductDetail:

                productimage.setScaleType(ImageView.ScaleType.FIT_CENTER);
                recyclerView.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
                backimage.setVisibility(View.VISIBLE);
                titletext.setText(getResources().getString(R.string.addtocart));
                String productname = CSPreferences.readString(context, "product_name");
                String product_image = CSPreferences.readString(context, "product_image");
                String product_quantity = CSPreferences.readString(context, "product_quantity");
                String product_detail = CSPreferences.readString(context, "product_detail");
                String product_price = CSPreferences.readString(context, "product_price");
                String currency_type = CSPreferences.readString(context, "currency_type");
                double strProductPrice = Double.parseDouble(product_price);

                productprice_text.setText(df.format(Double.parseDouble(product_price)) + "");
                productname_text.setText(productname);
                txtdetail.setText(product_detail);
                qantityvalue = 1;
                qantity_text.setText(qantityvalue + "");
                try {
                    tvCurrency.setText(Utils.getCurrencySymbol(currency_type));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                totalprice = strProductPrice;
                singleprice = strProductPrice;

                Picasso.with(context)
                        .load(product_image)
                        .placeholder(R.drawable.loading)
                        .into(productimage);


                plus_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        qantityvalue = qantityvalue + 1;
                        qantity_text.setText(qantityvalue + "");
                        totalprice = totalprice + singleprice;
                        productprice_text.setText(df.format(totalprice) + "");


                    }
                });
                minus_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (qantityvalue > 1) {

                            qantityvalue = qantityvalue - 1;
                            qantity_text.setText(qantityvalue + "");
                            totalprice = totalprice - singleprice;
                            productprice_text.setText(df.format(totalprice) + "");


                        }

                    }
                });
                addcart_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recyclerView.setVisibility(View.VISIBLE);
                        linearLayout.setVisibility(View.GONE);
                        titletext.setText(getResources().getString(R.string.product));
                        showProgressDialog();
                        ModelManager.getInstance().getAddCartManager().AddCartManager(context, Operations.addToCart(context,
                                ActionString.addCartaction, CSPreferences.readString(context, "user_id"), CSPreferences.readString(context, "product_id"), String.valueOf(qantityvalue), cardNumber));
                       /* mHandler.postDelayed(new Runnable() {
                            public void run() {
                                if (progressDialog != null && progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                    Toast.makeText(context, getResources().getString(R.string.addedtocart), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, 2000);*/

                    }
                });


                backimage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        hideProgressDialog();
                        backimage.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        linearLayout.setVisibility(View.GONE);
                        titletext.setText(getResources().getString(R.string.product));

                    }
                });

                break;
            case Constants.ADDCART:
                if (backimage != null && backimage.getVisibility() == View.VISIBLE) {
                    backimage.setVisibility(View.GONE);
                }
                hideProgressDialog();
                recyclerView.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.GONE);
                Toast.makeText(context, getResources().getString(R.string.addedtocart), Toast.LENGTH_SHORT).show();
                break;

            case Constants.SERVER_ERROR:
                //progressDialog.dismiss();
                hideProgressDialog();
                Toast.makeText(context, getResources().getString(R.string.tryagain), Toast.LENGTH_SHORT).show();
                break;
            case Constants.PROFILEERROR:
                hideProgressDialog();
                Toast.makeText(context, getResources().getString(R.string.profileerror), Toast.LENGTH_SHORT).show();
                break;
            case Constants.ERROR:
                //progressDialog.dismiss();
                hideProgressDialog();
                Log.e("No data ", "d");
                Toast.makeText(context, getResources().getString(R.string.noproduct), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void setAdapter() {
        product_adapter = new Product_Adapterr(context, ProductManager.list);
        RecyclerView.LayoutManager mlayoutmanger = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mlayoutmanger);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(product_adapter);
    }

    public class Product_Adapterr extends RecyclerView.Adapter<Product_Adapterr.ViewHolder> {
        ArrayList<ProductBean> list;
        View v;


        public Context context;


        public Product_Adapterr(Context context, ArrayList<ProductBean> list) {
            super();
            this.context = context;

            this.list = list;

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.custom_product, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {

            final ProductBean productBean = list.get(i);

            viewHolder.nameText.setText(productBean.getProduct_name());
            viewHolder.namePrice.setText(productBean.getPrice());
            viewHolder.productnumber.setText(productBean.getProductnumber());
            Picasso.with(context)
                    .load(productBean.getImage())
                    .placeholder(R.drawable.loading)
                    .into(viewHolder.imgThumbnail);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ModelManager.getInstance().getProductDetailManager().ProductDetailManager(context,
                            Operations.getProdductDeatil(context, ActionString.allproductaction, productBean.getProduct_id()));

                }
            });


        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public ImageView imgThumbnail;
            public TextView nameText;
            public TextView namePrice, productnumber;
            private ItemClickListener clickListener;

            public ViewHolder(View itemView) {
                super(itemView);
                imgThumbnail = (ImageView) itemView.findViewById(R.id.product_image);
                nameText = (TextView) itemView.findViewById(R.id.text_name);
                namePrice = (TextView) itemView.findViewById(R.id.text_price);
                productnumber = (TextView) itemView.findViewById(R.id.text_productno);

              /*  itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ModelManager.getInstance().getProductDetailManager().ProductDetailManager(context,
                                Operations.getProdductDeatil(context,ActionString.allproductaction,));
                    }
                });*/
            }
        }


    }
}
