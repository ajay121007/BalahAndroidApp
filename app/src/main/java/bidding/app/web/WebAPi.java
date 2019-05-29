package bidding.app.web;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import bidding.app.model.CartItems;
import bidding.app.model.Categories;
import bidding.app.model.InventoryBean;
import bidding.app.model.ProductBySku;
import bidding.app.model.SellerProductListBean;
import bidding.app.view.activity.addauction.request.AddAuctionRequest;
import bidding.app.view.activity.addnewaddress.request.SaveAddressRequest;
import bidding.app.view.activity.changeaddress.request.EditAddress;
import bidding.app.view.activity.changeaddress.request.SetDefaultAddress;
import bidding.app.view.activity.loginactivity.requestpojo.LoginRequest;
import bidding.app.model.MyAuction;
import bidding.app.model.WonAuction;
import bidding.app.view.activity.productdetails.cartrequest.AddItemRequest;
import bidding.app.view.activity.register.signuprequest.SellerSignUpRequest;
import bidding.app.view.activity.register.signuprequest.SignUpRequest;
import bidding.app.view.fragment.checkoutfragment.request.ShippingCostEstReq;
import bidding.app.view.fragment.checkoutfragment.request.UpdateCartRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Thakur on 11/12/2017.
 */

public interface WebAPi {

    //Login
    @POST("rest/V1/customer/login")
    Call<JsonArray> isLogin(@Body LoginRequest request);

    //Register
    @POST("rest/V1/customer/signup")
    Call<JsonArray> signUp(@Body SignUpRequest request);

    //Seller Register
    @POST("rest/V1/seller/signup")
    Call<JsonArray> sellerSignUp(@Body SellerSignUpRequest request);


    @FormUrlEncoded
    @POST("datesBidding/api.php?")
    Call<JsonObject> getProfile(@Field("action") String action, @Field("user_id") String userid);

    @FormUrlEncoded
    @POST("datesBidding/api.php?")
    Call<JsonObject> getAlluction(@Field("action") String action, @Field("user_id") String userid, @Field("auction_id") String auctionid);


    @FormUrlEncoded
    @POST("datesBidding/api.php?")
    Call<JsonObject> submitBid(@Field("action") String action, @Field("user_id") String userid, @Field("auction_id") String auctionid, @Field("bid_price") String bid_price, @Field("extendTime") String time);

    @FormUrlEncoded
    @POST("datesBidding/api.php?")
    Call<JsonObject> getAuction(@Field("action") String action, @Field("user_id") String userid, @Field("auction_id") String auctionid);

    //Move Inventory
    @FormUrlEncoded
    @POST("datesBidding/api.php?")
    Call<JsonObject> moveToInventory(@Field("action") String action, @Field("user_id") String userid, @Field("auction_id") String auctionid, @Field("inventory") String inventory, @Field("direct_delivery") String directDelivery);

    //Get Inventory
    @FormUrlEncoded
    @POST("datesBidding/api.php?")
    Call<InventoryBean> getInventory(@Field("action") String action, @Field("user_id") String userid);


    //Pending Payment
    @FormUrlEncoded
    @POST("datesBidding/api.php?")
    Call<JsonObject> pendingPayment(@Field("action") String action, @Field("user_id") String userId, @Field("product_id") String productId, @Field("product_type") String productType, @Field("auction_id") String auctionId, @Field("card_number") String card_number);

    //Pending Payment
    @FormUrlEncoded
    @POST("datesBidding/api.php?")
    Call<JsonObject> updateProfile(@Field("action") String action, @Field("user_id") String userId, @Field("fullname") String name, @Field("address") String address, @Field("country") String country, @Field("city") String city, @Field("latitude") String latitute, @Field("longitude") String longitute);

    //Get Card List
    @FormUrlEncoded
    @POST("datesBidding/api.php?")
    Call<JsonObject> getCardList(@Field("action") String action, @Field("user_id") String userId);

    //Add Card
    @FormUrlEncoded
    @POST("datesBidding/api.php?")
    Call<JsonObject> addcard(@Field("action") String action, @Field("user_id") String userId, @Field("card_number") String cardnumber, @Field("card_holderName") String cardHolderName, @Field("ex_month") String ex_mnth, @Field("ex_year") String ex_year, @Field("cvv") String cvc, @Field("card_type") String cardType, @Field("stripe_token") String tok);

    //logout
    @FormUrlEncoded
    @POST("datesBidding/api.php?")
    Call<JsonObject> logout(@Field("action") String action, @Field("user_id") String userId);

    //Get My Auction
    @FormUrlEncoded
    @POST("datesBidding/api.php?")
    Call<MyAuction> getMyAuction(@Field("action") String action, @Field("user_id") String userId);

    //Get Product Categories list
    @GET("rest/V1/categoriesList")
    Call<Categories> getProductCategoriesList();


    //Get Product list
    @GET("rest/default/V1/productsList?")
    Call<JsonObject> getProductList(@Query("searchCriteria[filterGroups][0][filters][0][field]") String key, @Query("searchCriteria[filterGroups][0][filters][0][value]") String id, @Query("searchCriteria[filterGroups][0][filters][0][conditionType]") String conditionType, @Query("searchCriteria[sortOrders][0][field]") String field, @Query("searchCriteria[sortOrders][0][direction]") String direction, @Query("searchCriteria[pageSize]") String pageSize, @Query("searchCriteria[currentPage]") String currentPage);

    //Get Product Details By Id
    @GET("rest/V1/productDetail/productId/{id}")
    Call<JsonArray> getProductById(@Path("id") String id);

    //Get Product Details By Sku
    @GET("rest/V1/productDetail/{id}")
    Call<ProductBySku> getProductBySku(@Path("id") String id);

    //Get Won Auction List
    @GET("rest/V1/auction/wonProductList")
    Call<JsonArray> getWonAuction(@Header("Authorization") String token);

    //Get Quote Id
    @POST("rest/default/V1/carts/mine")
    Call<String> getQuoteId(@Header("Authorization") String token);

    //Add To Cart
    @POST("rest/default/V1/carts/mine/items")
    Call<JsonObject> addToCart(@Header("Authorization") String mToken, @Body AddItemRequest itemRequest);


    //Get Cart List
    @GET("rest/V1/carts/mine/items")
    Call<JsonArray> getCartList(@Header("Authorization") String mToken);

    //Add New Address
    @POST("rest/V1/address/create")
    Call<JsonArray> addNewAddress(@Header("Authorization") String mToken, @Body SaveAddressRequest request);

    //Get Profile
    @GET("rest/V1/customers/me")
    Call<JsonObject> getCurrenetCusomer(@Header("Authorization") String mToken);

    //Delete Item from cart
    @DELETE("rest/V1/carts/mine/items/{id}")
    Call<String> deleteItem(@Header("Authorization") String token, @Path("id") int id);

    //Update Item from cart
    @POST("rest/V1/carts/mine/items")
    Call<CartItems> updateItem(@Header("Authorization") String token, @Body UpdateCartRequest request);

    //Get Default Billing Address
    @GET("rest/V1/customers/me/billingAddress")
    Call<JsonObject> billingAddress(@Header("Authorization") String token);

    //Get Product By Name
    @GET("rest/default/V1/productsList?")
    Call<JsonObject> getProductByName(@Query("searchCriteria[filterGroups][0][filters][0][field]") String key, @Query("searchCriteria[filterGroups][0][filters][0][value]") String value, @Query("searchCriteria[filterGroups][0][filters][0][conditionType]") String conditionType, @Query("searchCriteria[sortOrders][0][direction]") String direction, @Query("searchCriteria[pageSize]") String pageSize, @Query("searchCriteria[currentPage]") String currentPage);

    //Shipping cost estimation
    @POST("rest/default/V1/carts/mine/estimate-shipping-methods")
    Call<JsonArray> shippingEsti(@Header("Authorization") String token, @Body ShippingCostEstReq request);

    //Add New Auction
    @POST("rest/V1/auction/add")
    Call<JsonArray> addNewAuction(@Header("Authorization") String token, @Body AddAuctionRequest request);

    //Add To Whislist
    @POST("rest/V1/wishlist/add/{id}")
    Call<JsonArray> addToWhislist(@Header("Authorization") String token, @Path("id") String productId);

    //Remove From Whislist
    @DELETE("rest/V1/wishlist/delete/productId/{id}")
    Call<JsonArray> removeFromWhislist(@Header("Authorization") String token, @Path("id") String productId);

    @DELETE("rest/V1/wishlist/delete/{id}")
    Call<JsonArray> removeFromWhislistUsingId(@Header("Authorization") String token, @Path("id") String wishlistid);

    @POST("rest/V1/wishlist/addToCart/{id}")
    Call<JsonArray> moveWishListToCart(@Header("Authorization") String token, @Path("id") String wishlistid);

    //WishListDetail By Id
    @GET("rest/V1/product/wishListDetail/{id}")
    Call<JsonArray> wishListDetailByID(@Header("Authorization") String token, @Path("id") String productId);

    //Get Address List
    @GET("rest/V1/address/list")
    Call<JsonArray> addressList(@Header("Authorization") String token);

    @POST("rest/V1/address/update/{id}")
    Call<JsonArray> setAsDefaultAddress(@Header("Authorization") String mToken, @Path("id") String entity_id, @Body SetDefaultAddress defaultAddress);

    @DELETE("rest/V1/address/delete/{id}")
    Call<String> deleteAddress(@Header("Authorization") String mToken, @Path("id") String entity_id);

    @POST("rest/V1/address/edit")
    Call<JsonArray> editAddress(@Header("Authorization") String mToken, @Body EditAddress request);

    @GET("rest/default/V1/wishlist/items")
    Call<JsonArray> getWishList(@Header("Authorization") String mToken);

    @GET("rest/default/V1/seller/productsList")
    Call<SellerProductListBean> getSellerProductList(@Header("Authorization") String mToken, @Query("searchCriteria[filterGroups][0][filters][0][field]") String key, @Query("searchCriteria[filterGroups][0][filters][0][value]") String id, @Query("searchCriteria[filterGroups][0][filters][0][conditionType]") String conditionType, @Query("searchCriteria[sortOrders][0][field]") String field, @Query("searchCriteria[sortOrders][0][direction]") String direction, @Query("searchCriteria[pageSize]") String pageSize, @Query("searchCriteria[currentPage]") String currentPage);

    @GET("rest/V1/auction/list")
    Call<JSONArray> getVendorAuctionList(@Header("Authorization") String mToken, @Query("searchCriteria[filterGroups][0][filters][0][field]") String key, @Query("searchCriteria[filterGroups][0][filters][0][value]") String id, @Query("searchCriteria[filterGroups][0][filters][0][conditionType]") String conditionType, @Query("searchCriteria[sortOrders][0][field]") String field, @Query("searchCriteria[sortOrders][0][direction]") String direction, @Query("searchCriteria[pageSize]") String pageSize, @Query("searchCriteria[currentPage]") String currentPage);

}