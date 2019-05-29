package bidding.app.view.activity.expressactivity.presenter;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import bidding.app.model.Categories;
import bidding.app.model.CustomAttributes;
import bidding.app.model.ProductData;
import bidding.app.model.ProductList;
import bidding.app.model.SearchCriteria;
import bidding.app.view.activity.expressactivity.ExpressActivity;
import bidding.app.view.activity.expressactivity.view.ExpressActivityView;
import bidding.app.web.WebServc;
import bidding.app.web.handler.GetProductCategories;
import bidding.app.web.handler.GetProductList;


/**
 * Created by Thakur on 11/12/2017.
 */

public class ExpressActivityPresenter implements ExpressActivityPresenterHandler {

    Activity activity;
    static boolean loading = false;
    ProgressBar progressBar;
    ExpressActivityView view;
    int mId = 0, attributeSetId = 0, status, visibility, weight, mCurrentPage = 0, mPageSize = 0;
    double price = 0;
    String sku = "", name = "", typeId = "", createdAt = "", updatedAt = "", description = "", short_description = "", meta_keyword = "", meta_description = "", image = "", small_image = "", thumbnail = "", approval = "";

    public ExpressActivityPresenter(ExpressActivity view, ProgressBar progressBar, Activity activity) {
        this.view = view;
        this.progressBar = progressBar;
        this.activity=activity;
    }


    @Override
    public void catagories() {
        view.showProgess();
        WebServc.getInstance().getProductCategories(new GetProductCategories() {
            @Override
            public void Success(Categories categories) {
                view.hideProgess();
                view.getProductCategoriesSuccess(categories);
            }

            @Override
            public void Fail(String error) {
                view.hideProgess();
            }
        },activity);

    }

    @Override
    public void productlist(String key, final String id, String conditionType, String field, String direction, String pageSize, final String currentPage) {
        if (currentPage.equalsIgnoreCase("1"))
            view.showProgess();
        else
            progressBar.setVisibility(View.VISIBLE);
        WebServc.getInstance().getProductList(new GetProductList() {
            @Override
            public void Success(String response) {
                Log.d("Product List", response.toString());
                if (currentPage.equalsIgnoreCase("1"))
                    view.hideProgess();
                else
                    progressBar.setVisibility(View.GONE);

                ProductList productList = new ProductList();
                SearchCriteria criteria = new SearchCriteria();
                List<ProductData> dataList = new ArrayList<>();

                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray items = object.getJSONArray("items");
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject jsonObject = items.getJSONObject(i);
                        ProductData data = new ProductData();
                        mId = jsonObject.getInt("id");
                        sku = jsonObject.getString("sku");
                        name = jsonObject.getString("name");
                        attributeSetId = jsonObject.getInt("attribute_set_id");

                        if (jsonObject.has("price")) {
                            price = jsonObject.getDouble("price");
                        }
                        status = jsonObject.getInt("status");
                        visibility = jsonObject.getInt("visibility");
                        typeId = jsonObject.getString("type_id");
                        createdAt = jsonObject.getString("created_at");
                        updatedAt = jsonObject.getString("updated_at");


                        data.setId(mId);
                        data.setSku(sku);
                        data.setName(name);
                        data.setAttribute_set_id(attributeSetId);
                        data.setPrice(price);
                        data.setStatus(status);
                        data.setVisibility(visibility);
                        data.setType_id(typeId);
                        data.setCreated_at(createdAt);
                        data.setUpdated_at(updatedAt);
                        data.setWeight(weight);

                        List<CustomAttributes> attributesList = new ArrayList<>();
                        JSONArray jsonArray = jsonObject.getJSONArray("custom_attributes");
                        for (int j = 0; j < jsonArray.length(); j++) {
                            JSONObject attData = jsonArray.getJSONObject(j);
                            CustomAttributes attributes = new CustomAttributes();
                            if (attData.get("attribute_code").equals("description")) {
                                description = attData.getString("value");
                                attributes.setDescription(description);
                            } else if (attData.get("attribute_code").equals("short_description")) {
                                short_description = attData.getString("value");
                                attributes.setShort_description(short_description);
                            } else if (attData.get("attribute_code").equals("meta_keyword")) {
                                meta_keyword = attData.getString("value");
                                attributes.setMeta_keyword(meta_keyword);
                            } else if (attData.get("attribute_code").equals("meta_description")) {
                                meta_description = attData.getString("value");
                                attributes.setMeta_description(meta_description);
                            } else if (attData.get("attribute_code").equals("image")) {
                                image = attData.getString("value");
                                data.setImage(image);
                            } else if (attData.get("attribute_code").equals("small_image")) {
                                small_image = attData.getString("value");
                                attributes.setSmall_image(small_image);
                            } else if (attData.get("attribute_code").equals("thumbnail")) {
                                thumbnail = attData.getString("value");
                                attributes.setThumbnail(thumbnail);
                            } else if (attData.get("attribute_code").equals("approval")) {
                                approval = attData.getString("value");
                                attributes.setApproval(approval);
                            }
                            attributesList.add(attributes);
                        }
                        data.setCustom_attributes(attributesList);
                        dataList.add(data);
                    }
                    productList.setProductData(dataList);


                    JSONObject searchCriteria = object.getJSONObject("search_criteria");
                    mCurrentPage = searchCriteria.getInt("current_page");
                    mPageSize = searchCriteria.getInt("page_size");
                    criteria.setCurrentPage(mCurrentPage);
                    criteria.setPageSize(mPageSize);

                    productList.setSearchCriteria(criteria);
                    productList.setTotal_count(object.getInt("total_count"));

                    view.productList(productList);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("PListException", e.toString());
                }


            }

            @Override
            public void Fail(String error) {
                Log.d("ProductListError", error.toString());
                if (currentPage.equalsIgnoreCase("1"))
                    view.hideProgess();
                else
                    progressBar.setVisibility(View.GONE);

                loading = true;
            }
        }, key, id, conditionType, field, direction, pageSize, currentPage);
    }
}

