package bidding.app.web;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import bidding.app.model.CartItems;
import bidding.app.model.Categories;
import bidding.app.model.InventoryBean;
import bidding.app.model.MyAuction;
import bidding.app.model.ProductBySku;
import bidding.app.model.SellerProductListBean;
import bidding.app.model.WonAuction;
import bidding.app.view.activity.addauction.AddAuctionActivity;
import bidding.app.view.activity.addauction.request.AddAuctionRequest;
import bidding.app.view.activity.addnewaddress.NewAddressActivity;
import bidding.app.view.activity.addnewaddress.request.SaveAddressRequest;
import bidding.app.view.activity.changeaddress.ChangeAddressActivity;
import bidding.app.view.activity.changeaddress.request.EditAddress;
import bidding.app.view.activity.changeaddress.request.SetDefaultAddress;
import bidding.app.view.activity.loginactivity.requestpojo.LoginRequest;
import bidding.app.view.activity.myauction.MyAuctionActivity;
import bidding.app.view.activity.productdetails.ProductActivity;
import bidding.app.view.activity.productdetails.cartrequest.AddItemRequest;
import bidding.app.view.activity.register.signuprequest.SellerSignUpRequest;
import bidding.app.view.activity.register.signuprequest.SignUpRequest;
import bidding.app.view.activity.sellerproductactivity.SellerProductActivity;
import bidding.app.view.activity.wishlistactivity.WishListActivity;
import bidding.app.view.fragment.checkoutfragment.request.ShippingCostEstReq;
import bidding.app.view.fragment.checkoutfragment.request.UpdateCartRequest;
import bidding.app.web.handler.AddAuctionHandler;
import bidding.app.web.handler.CallBackHandler;
import bidding.app.web.handler.GetAllAuctionHandler;
import bidding.app.web.handler.GetAuction;
import bidding.app.web.handler.GetCartHandler;
import bidding.app.web.handler.GetCartUpdate;
import bidding.app.web.handler.GetInventoryHandler;
import bidding.app.web.handler.GetLoginHandler;
import bidding.app.web.handler.GetMyAuctionHandler;
import bidding.app.web.handler.GetProductByIdHandler;
import bidding.app.web.handler.GetProductBySkuHandler;
import bidding.app.web.handler.GetProductCategories;
import bidding.app.web.handler.GetProductList;
import bidding.app.web.handler.GetQuoteIdHandler;
import bidding.app.web.handler.GetSellerProductListHandler;
import bidding.app.web.handler.GetWonAuctionHandler;
import bidding.app.web.handler.InventoryHandler;
import bidding.app.web.handler.PendingPaymentHandler;
import bidding.app.web.handler.UpdateProfileHandler;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Thakur on 11/12/2017.
 */

public class WebServc {
    static WebServc sInstance;
    private final WebAPi retrofit;
    private final WebAPi retrofit2;
    OkHttpClient client;
    Gson gson;

    public WebServc() {
        sInstance = this;

        gson = new GsonBuilder()
                .setLenient()
                .create();

        client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS).build();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://206.189.160.58/").client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build().create(WebAPi.class);

        retrofit2 = new Retrofit.Builder()
                .baseUrl("http://178.128.35.113/").client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build().create(WebAPi.class);

    }

    public static WebServc getInstance() {
        return sInstance;
    }

    //Login
    public void getLogin(final GetLoginHandler loginHandler, LoginRequest json) {
        retrofit2.isLogin(json).enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {

                if (response.body() != null) {
                    loginHandler.onSuccess(response.body().toString());
                } else {
                    try {
                        loginHandler.onFail(response.errorBody().string().toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                loginHandler.onFail(t.toString());
            }
        });
    }

    //Get Profile
    public void getProfile(final GetLoginHandler getLoginHandler, String action, String userid) {
        retrofit.getProfile(action, userid).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null) {
                    getLoginHandler.onSuccess(response.body().toString());
                } else {
                    try {
                        getLoginHandler.onSuccess(response.errorBody().string().toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                getLoginHandler.onFail(t.toString());
            }
        });
    }

    //Update Profile
    public void updateProfile(final UpdateProfileHandler handler, String action, String userId, String name, String address, String country, String city, String latitute, String longitute) {
        retrofit.updateProfile(action, userId, name, address, country, city, latitute, longitute).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null) {
                    handler.onSuccess(response.body().toString());
                } else {
                    try {
                        handler.onSuccess(response.errorBody().string().toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                handler.onFail(t.toString());
            }
        });
    }

    //Get AllAuctoin
    public void getAllAuction(final GetAllAuctionHandler allAuctionHandler, String action, String userid, String auctionid) {
        retrofit.getAlluction(action, userid, auctionid).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null) {
                    allAuctionHandler.onSuccess(response.body().toString());
                } else {
                    try {
                        allAuctionHandler.onSuccess(response.errorBody().string().toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                allAuctionHandler.onFail(t.getMessage().toString());
            }
        });
    }

    //Get Auctoin By Id
    public void getAuctionByID(final GetAuction getAuction, String action, String userid, String auctionid) {
        retrofit.getAuction(action, userid, auctionid).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null) {
                    getAuction.onSuccess(response.body().toString());
                } else {
                    try {
                        getAuction.onSuccess(response.errorBody().string().toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                getAuction.onFail(t.getMessage().toString());
            }
        });
    }

    //Submit Bid
    public void submitBid(final GetAllAuctionHandler allAuctionHandler, String action, String userid, String auctionid, String str, String time) {
        retrofit.submitBid(action, userid, auctionid, str, time).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null) {
                    allAuctionHandler.onSuccess(response.body().toString());
                } else {
                    try {
                        allAuctionHandler.onSuccess(response.errorBody().string().toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                allAuctionHandler.onFail(t.toString());
            }
        });
    }

    //Move To Inventory
    public void moveToInventory(final InventoryHandler handler, String action, String userid, String auctionid, String inventory, String delivery) {
        retrofit.moveToInventory(action, userid, auctionid, inventory, delivery).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null) {
                    handler.onSuccess(response.body().toString());
                } else {
                    try {
                        handler.onFail(response.errorBody().string().toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                handler.onFail(t.toString());
            }
        });
    }

    //Get Inventory
    public void getInventory(final GetInventoryHandler handler, String action, String userid) {
        retrofit.getInventory(action, userid).enqueue(new Callback<InventoryBean>() {
            @Override
            public void onResponse(Call<InventoryBean> call, Response<InventoryBean> response) {
                if (response.body() != null) {
                    handler.onSuccess(response.body());
                } else {
                    handler.onFail(response.toString());
                }
            }

            @Override
            public void onFailure(Call<InventoryBean> call, Throwable t) {
                handler.onFail(t.toString());
            }
        });
    }

    //Pending Payment
    public void pendingPayment(final PendingPaymentHandler handler, String action, String userId, String productId, String productType, String auctionId, String card_number) {
        retrofit.pendingPayment(action, userId, productId, productType, auctionId, card_number).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null) {
                    handler.onSuccess(response.body().toString());
                } else {
                    try {
                        handler.onFail(response.errorBody().string().toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                handler.onFail(t.toString());
            }
        });
    }

    //Get Card List
    public void getCardList(final UpdateProfileHandler handler, String action, String userId) {
        retrofit.getCardList(action, userId).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null) {
                    handler.onSuccess(response.body().toString());
                } else {
                    try {
                        handler.onFail(response.errorBody().string().toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                handler.onFail(t.getMessage().toString());
            }
        });
    }

    //Add Card
    public void addCard(final UpdateProfileHandler handler, String action, String userId, String cardnumber, String cardHolderName, String ex_mnth, String ex_year, String cvc, String brand, String tok) {
        retrofit.addcard(action, userId, cardnumber, cardHolderName, ex_mnth, ex_year, cvc, brand, tok).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null) {
                    handler.onSuccess(response.body().toString());
                } else {
                    try {
                        handler.onFail(response.errorBody().string().toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                handler.onFail(t.getMessage().toString());
            }
        });
    }

    //Logout
    public void logout(final UpdateProfileHandler handler, String action, String userId) {
        retrofit.logout(action, userId).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null) {
                    handler.onSuccess(response.body().toString());
                } else {
                    try {
                        handler.onFail(response.errorBody().string().toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                handler.onFail(t.getMessage().toString());
            }
        });
    }

    //Get My Auction
    public void getMyAuction(final GetMyAuctionHandler handler, String action, String userId) {
        retrofit.getMyAuction(action, userId).enqueue(new Callback<MyAuction>() {
            @Override
            public void onResponse(Call<MyAuction> call, Response<MyAuction> response) {
                if (response.body() != null) {
                    handler.Success(response.body());
                } else {
                    try {
                        handler.Fail(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MyAuction> call, Throwable t) {
                handler.Fail(t.getMessage().toString());
            }
        });
    }

    //Get Won Auction
    public void getWonAuction(final GetWonAuctionHandler handler, String token) {
        retrofit2.getWonAuction(token).enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.body() != null) {
                    handler.Success(response.body().toString());
                } else {
                    try {
                        handler.Fail(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                handler.Fail(t.getMessage().toString());
            }
        });
    }

    public void getProductCategories(final GetProductCategories productCategories, Activity activity) {
        if (isNetworkConnected(activity)) {
            retrofit2.getProductCategoriesList().enqueue(new Callback<Categories>() {
                @Override
                public void onResponse(Call<Categories> call, Response<Categories> response) {
                    if (response.body() != null) {
                        productCategories.Success(response.body());
                    } else {
                        try {
                            productCategories.Fail(response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Categories> call, Throwable t) {
                    productCategories.Fail(t.getMessage().toString());
                }
            });
        } else {
            productCategories.Fail("No internet conection");
        }


    }

    public void getProductList(final GetProductList getProductList, String key, String id, String conditionType, String field, String direction, String pageSize, String currentPage) {
        retrofit2.getProductList(key, id, conditionType, field, direction, pageSize, currentPage).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null) {
                    getProductList.Success(response.body().toString());
                } else {
                    try {
                        getProductList.Fail(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                getProductList.Fail(t.getMessage().toString());
            }
        });


    }


    public void getProductById(final GetProductByIdHandler getProductByIdHandler, String id) {

        retrofit2.getProductById(id).enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                Log.w("ProductById=> ", new Gson().toJson(response).toString());
                if (response.body() != null) {
                    getProductByIdHandler.Success(response.body().toString());
                } else {
                    try {
                        getProductByIdHandler.Fail(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                getProductByIdHandler.Fail(t.getMessage().toString());
            }
        });
    }

    //Product By Sku

    public void getProductBySku(final GetProductBySkuHandler getProductByIdHandler, String id) {

        retrofit2.getProductBySku(id).enqueue(new Callback<ProductBySku>() {
            @Override
            public void onResponse(Call<ProductBySku> call, Response<ProductBySku> response) {
                Log.w("ProductById=> ", new Gson().toJson(response).toString());
                if (response.body() != null) {
                    getProductByIdHandler.Success(response.body());
                } else {
                    try {
                        getProductByIdHandler.Fail(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ProductBySku> call, Throwable t) {
                getProductByIdHandler.Fail(t.getMessage().toString());
            }
        });
    }

    //Sign Up
    public void signUp(final GetLoginHandler loginHandler, SignUpRequest request) {
        retrofit2.signUp(request).enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {

                if (response.body() != null) {
                    loginHandler.onSuccess(response.body().toString());
                } else {
                    try {
                        loginHandler.onFail(response.errorBody().string().toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                loginHandler.onFail(t.toString());
            }
        });
    }

    //Seller Sign Up
    public void sellerSignUp(final CallBackHandler handler, SellerSignUpRequest request) {
        retrofit2.sellerSignUp(request).enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {

                if (response.body() != null) {
                    handler.Success(response.body().toString());
                } else {
                    try {
                        handler.Failuer(response.errorBody().string().toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                handler.Failuer(t.toString());
            }
        });
    }

    //Get QuoteId
    public void getQuoteId(final GetQuoteIdHandler handler, String token) {
        retrofit2.getQuoteId(token).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    handler.Success(response.body().toString());
                } else {
                    handler.Fail(response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                handler.Fail(t.getMessage().toString());
            }
        });
    }

    //Add to Cart
    public void addToCart(final GetQuoteIdHandler handler, Activity activity, String mToken, AddItemRequest itemRequest) {
        if (isNetworkConnected(activity)) {
            retrofit2.addToCart(mToken, itemRequest).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    Log.w("Add",response.toString());
                    if (response.body() != null) {
                        handler.Success(response.body().toString());
                    } else {
                        handler.Fail(response.errorBody().toString());
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    handler.Fail(t.getMessage().toString());
                }
            });
        } else {
            handler.Fail("No Internet connection.");
        }
    }


    //Get Cart Items
    public void getCartList(final GetCartHandler handler, Activity activity, String mToken) {
        if (isNetworkConnected(activity)) {
            retrofit2.getCartList(mToken).enqueue(new Callback<JsonArray>() {
                @Override
                public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {

                    if (response.body() != null) {
                        handler.Success(response.body().toString());
                    } else {
                        handler.Fail(response.errorBody().toString());
                    }
                }

                @Override
                public void onFailure(Call<JsonArray> call, Throwable t) {
                    handler.Fail(t.getMessage().toString());
                }
            });
        } else {
            handler.Fail("No Internet connection.");
        }

    }

    //Add New Address
    public void addNewAddress(final CallBackHandler handler, Activity activity, String mToken, SaveAddressRequest request) {
        if (isNetworkConnected(activity)) {
            retrofit2.addNewAddress(mToken, request).enqueue(new Callback<JsonArray>() {
                @Override
                public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                    if (response.body() != null) {
                        handler.Success(response.body().toString());
                    } else {
                        handler.Failuer(response.errorBody().toString());
                    }
                }

                @Override
                public void onFailure(Call<JsonArray> call, Throwable t) {
                    handler.Failuer(t.toString());
                }
            });

        } else {
            handler.Failuer("No Internet connection.");
        }
    }

    //Get Profile
    public void getProfile(final CallBackHandler handler, Activity activity, String mToken) {
        if (isNetworkConnected(activity)) {
            retrofit2.getCurrenetCusomer(mToken).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.body() != null) {
                        handler.Success(response.body().toString());
                    } else {
                        handler.Failuer(response.errorBody().toString());
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    handler.Failuer(t.getMessage().toString());
                }
            });

        } else {
            handler.Failuer("No Internet connection.");
        }
    }

    //Check Network
    private boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.
            return false;
        } else
            return true;
    }


    //Delete Item
    public void deleteCartItem(final CallBackHandler handler, Activity activity, String token, int id) {
        if (isNetworkConnected(activity)) {
            retrofit2.deleteItem(token, id).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.body() != null) {
                        handler.Success(response.body().toString());
                    } else {
                        handler.Failuer(response.errorBody().toString());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    handler.Failuer(t.toString());
                }
            });

        } else {
            handler.Failuer("No Internet connection.");
        }
    }

    //Default Billing address
    public void billingAddress(final CallBackHandler handler, Activity activity, String token) {
        if (isNetworkConnected(activity)) {
            retrofit2.billingAddress(token).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.body() != null) {
                        handler.Success(response.body().toString());
                    } else {
                        handler.Failuer(response.errorBody().toString());
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    handler.Failuer(t.toString());
                }
            });

        } else {
            handler.Failuer("No Internet connection.");
        }
    }

    //Update cart item
    public void updateCartItem(final GetCartUpdate handler, Activity activity, String token, UpdateCartRequest request) {
        if (isNetworkConnected(activity)) {
            retrofit2.updateItem(token, request).enqueue(new Callback<CartItems>() {
                @Override
                public void onResponse(Call<CartItems> call, Response<CartItems> response) {
                    if (response.body() != null) {
                        handler.Success(response.body());
                    } else {
                        handler.onFailure(response.errorBody().toString());
                    }
                }

                @Override
                public void onFailure(Call<CartItems> call, Throwable t) {
                    handler.onFailure(t.toString());
                }
            });

        } else {
            handler.onFailure("No Internet connection.");
        }
    }


    //Get Product By Name
    public void getProductByName(final GetProductList getProductList, String key, String value, String conditionType, String direction, String pageSize, String currentPage) {

        retrofit2.getProductByName(key, value, conditionType, direction, pageSize, currentPage).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null) {
                    getProductList.Success(response.body().toString());
                } else {
                    try {
                        getProductList.Fail(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                getProductList.Fail(t.getMessage().toString());
            }
        });
    }


    //Get Product By Name
    public void getShippingCost(final CallBackHandler handler, Activity activity, String token, ShippingCostEstReq costEstReq) {
        if (isNetworkConnected(activity)) {
            retrofit2.shippingEsti(token, costEstReq).enqueue(new Callback<JsonArray>() {
                @Override
                public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                    if (response.body() != null) {
                        handler.Success(response.body().toString());
                    } else {
                        try {
                            handler.Failuer(response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonArray> call, Throwable t) {
                    handler.Failuer(t.getMessage().toString());
                }
            });
        } else {
            handler.Failuer("No Internet connection.");
        }

    }

    //Add Auction
    public void addAuction(final CallBackHandler handler, AddAuctionActivity activity, String mToken, AddAuctionRequest request) {
        if (isNetworkConnected(activity)) {
            retrofit2.addNewAuction(mToken, request).enqueue(new Callback<JsonArray>() {
                @Override
                public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                    if (response.body() != null) {
                        handler.Success(response.body().toString());
                    } else {
                        try {
                            handler.Failuer(response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonArray> call, Throwable t) {
                    handler.Failuer(t.getMessage().toString());
                }
            });

        } else {
            handler.Failuer("No Internet connection.");
        }
    }

    //Add To WhisList
    public void addToWhislist(final CallBackHandler handler, ProductActivity activity, String token, String productId) {
        if (isNetworkConnected(activity)) {
            retrofit2.addToWhislist(token, productId).enqueue(new Callback<JsonArray>() {
                @Override
                public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                    if (response.body() != null) {
                        handler.Success(response.body().toString());
                    } else {
                        try {
                            handler.Failuer(response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonArray> call, Throwable t) {
                    handler.Failuer(t.getMessage().toString());
                }
            });

        } else {
            handler.Failuer("No Internet connection.");
        }
    }

    //Remove From WhisList
    public void removeFromWhislist(final CallBackHandler handler, ProductActivity activity, String token, String id) {
        if (isNetworkConnected(activity)) {
            retrofit2.removeFromWhislist(token, id).enqueue(new Callback<JsonArray>() {
                @Override
                public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                    if (response.body() != null) {
                        handler.Success(response.body().toString());
                    } else {
                        try {
                            handler.Failuer(response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonArray> call, Throwable t) {
                    handler.Failuer(t.getMessage().toString());
                }
            });

        } else {
            handler.Failuer("No Internet connection.");
        }
    }

    public void removeFromWhislistUsingId(final CallBackHandler handler, WishListActivity activity, String token, String id) {
        if (isNetworkConnected(activity)) {
            retrofit2.removeFromWhislistUsingId(token, id).enqueue(new Callback<JsonArray>() {
                @Override
                public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                    if (response.body() != null) {
                        handler.Success(response.body().toString());
                    } else {
                        try {
                            handler.Failuer(response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonArray> call, Throwable t) {
                    handler.Failuer(t.getMessage().toString());
                }
            });

        } else {
            handler.Failuer("No Internet connection.");
        }
    }

    public void moveWishListToCart(final CallBackHandler handler, WishListActivity activity, String token, String wishListId) {
        if (isNetworkConnected(activity)) {
            retrofit2.moveWishListToCart(token, wishListId).enqueue(new Callback<JsonArray>() {
                @Override
                public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                    if (response.body() != null) {
                        handler.Success(response.body().toString());
                    } else {
                        try {
                            handler.Failuer(response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonArray> call, Throwable t) {
                    handler.Failuer(t.getMessage().toString());
                }
            });

        } else {
            handler.Failuer("No Internet connection.");
        }
    }

    //WhisListDrtail By Id
    public void wishListDetailByID(final CallBackHandler handler, ProductActivity activity, String token, String id) {
        if (isNetworkConnected(activity)) {
            retrofit2.wishListDetailByID(token, id).enqueue(new Callback<JsonArray>() {
                @Override
                public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                    if (response.body() != null) {
                        handler.Success(response.body().toString());
                    } else {
                        try {
                            handler.Failuer(response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonArray> call, Throwable t) {
                    handler.Failuer(t.getMessage().toString());
                }
            });

        } else {
            handler.Failuer("No Internet connection.");
        }
    }

    //Get Address List
    public void getAddress(final CallBackHandler handler, ChangeAddressActivity actvity, String mToken) {
        if (isNetworkConnected(actvity)) {
            retrofit2.addressList(mToken).enqueue(new Callback<JsonArray>() {
                @Override
                public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                    if (response.body() != null) {
                        handler.Success(response.body().toString());
                    } else {
                        try {
                            handler.Failuer(response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonArray> call, Throwable t) {
                    handler.Failuer(t.getMessage().toString());
                }
            });

        } else {
            handler.Failuer("No Internet connection.");
        }
    }

    public void setAsDefaultAddress(final CallBackHandler handler, ChangeAddressActivity activity, String mToken, String entity_id, SetDefaultAddress defaultAddress) {
        if (isNetworkConnected(activity)) {
            retrofit2.setAsDefaultAddress(mToken, entity_id, defaultAddress).enqueue(new Callback<JsonArray>() {
                @Override
                public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                    if (response.body() != null) {
                        handler.Success(response.body().toString());
                    } else {
                        try {
                            handler.Failuer(response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonArray> call, Throwable t) {
                    handler.Failuer(t.getMessage().toString());
                }
            });

        } else {
            handler.Failuer("No Internet connection.");
        }
    }

    public void deleteAddress(final CallBackHandler handler, ChangeAddressActivity activity, String mToken, String entity_id) {
        if (isNetworkConnected(activity)) {
            retrofit2.deleteAddress(mToken, entity_id).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.body() != null) {
                        handler.Success(response.body().toString());
                    } else {
                        try {
                            handler.Failuer(response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    handler.Failuer(t.getMessage().toString());
                }
            });

        } else {
            handler.Failuer("No Internet connection.");
        }
    }

    public void editAddress(final CallBackHandler handler, NewAddressActivity activity, String mToken, EditAddress request) {

        if (isNetworkConnected(activity)) {
            retrofit2.editAddress(mToken, request).enqueue(new Callback<JsonArray>() {
                @Override
                public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                    if (response.body() != null) {
                        handler.Success(response.body().toString());
                    } else {
                        handler.Failuer(response.errorBody().toString());
                    }
                }

                @Override
                public void onFailure(Call<JsonArray> call, Throwable t) {
                    handler.Failuer(t.toString());
                }
            });

        } else {
            handler.Failuer("No Internet connection.");
        }
    }

    public void getWishList(final CallBackHandler handler, WishListActivity activity, String mToken) {
        if (isNetworkConnected(activity)) {
            retrofit2.getWishList(mToken).enqueue(new Callback<JsonArray>() {
                @Override
                public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                    if (response.body() != null) {
                        handler.Success(response.body().toString());
                    } else {
                        handler.Failuer(response.errorBody().toString());
                    }
                }

                @Override
                public void onFailure(Call<JsonArray> call, Throwable t) {
                    handler.Failuer(t.toString());
                }
            });

        } else {
            handler.Failuer("No Internet connection.");
        }
    }

    public void getSellerProductList(final GetSellerProductListHandler handler, SellerProductActivity activity, String mToken, String key, String id, String conditionType, String field, String direction, String pageSize, String currentPage) {

        if (isNetworkConnected(activity)) {
            retrofit2.getSellerProductList(mToken, key, id, conditionType, field, direction, pageSize, currentPage).enqueue(new Callback<SellerProductListBean>() {
                @Override
                public void onResponse(Call<SellerProductListBean> call, Response<SellerProductListBean> response) {
                    if (response.body() != null) {
                        handler.onSuccess(response.body());
                    } else {
                        try {
                            handler.onFailuer(response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<SellerProductListBean> call, Throwable t) {
                    handler.onFailuer(t.getMessage().toString());
                }
            });
        } else {
            handler.onFailuer("No Internet connection.");
        }


    }

    public void getVendorAuctionList(final CallBackHandler handler, MyAuctionActivity activity, String mToken, String key, String id, String conditionType, String field, String direction, String pageSize, String currentPage) {

        if (isNetworkConnected(activity)) {
            retrofit2.getVendorAuctionList(mToken, key, id, conditionType, field, direction, pageSize, currentPage).enqueue(new Callback<JSONArray>() {
                @Override
                public void onResponse(Call<JSONArray> call, Response<JSONArray> response) {
                    if (response.body() != null) {
                        handler.Success(response.body().toString());
                    } else {
                        try {
                            handler.Failuer(response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<JSONArray> call, Throwable t) {
                    handler.Failuer(t.getMessage().toString());
                }
            });
        } else {
            handler.Failuer("No Internet connection.");
        }


    }
}
