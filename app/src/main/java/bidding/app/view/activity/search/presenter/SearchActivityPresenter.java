package bidding.app.view.activity.search.presenter;

import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import bidding.app.model.CustomAttributes;
import bidding.app.model.ProductData;
import bidding.app.model.ProductList;
import bidding.app.model.SearchCriteria;
import bidding.app.view.activity.search.SearchActivity;
import bidding.app.view.activity.search.view.SearchActivityView;
import bidding.app.web.WebServc;
import bidding.app.web.handler.GetProductList;

public class SearchActivityPresenter implements SearchActivityPresenterHandler {

    SearchActivityView view;
    int mId = 0, attributeSetId = 0, status, visibility, weight, mCurrentPage = 0, mPageSize = 0;
    double price = 0;
    String sku = "", name = "", typeId = "", createdAt = "", updatedAt = "", description = "", short_description = "", meta_keyword = "", meta_description = "", image = "", small_image = "", thumbnail = "", approval = "";


    public SearchActivityPresenter(SearchActivityView view) {
        this.view = view;
    }

    @Override
    public void productlist(String key, String value, String conditionType, String direction, String pageSize, String currentPage) {
        view.showProgess();
        WebServc.getInstance().getProductByName(new GetProductList() {
            @Override
            public void Success(String response) {
                Log.d("Product List By Name", response.toString());
                view.hideProgess();

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

                view.hideProgess();
                view.showFeedBackMessage(error.toString());

            }
        }, key, value, conditionType, direction, pageSize, currentPage);
    }
}

