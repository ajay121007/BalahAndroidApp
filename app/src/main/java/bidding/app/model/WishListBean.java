package bidding.app.model;

import java.util.List;

public class WishListBean {


    /**
     * response : {"success":true,"status":1,"code":"200","message":"Record Found","data":[{"wishlist_item_id":"27","wishlist_id":"38","product_id":"125","store_id":"1","added_at":"2018-10-29 13:25:01","description":null,"qty":1,"product":{"entity_id":"125","attribute_set_id":"4","type_id":"simple","vendor_id":"18","sku":"MSH02-36-Black","has_options":"0","required_options":"0","created_at":"2018-07-17 10:20:17","updated_at":"2018-10-19 09:12:09","ves_category_ids":"","search_weight":"0","price":"32.5000","tax_class_id":"2","final_price":null,"minimal_price":"32.5000","min_price":"32.5000","max_price":"32.5000","tier_price":null,"name":"خلاص الاحساء","image":"/4/_/4_4.jpg","small_image":"/4/_/4_4.jpg","thumbnail":"/4/_/4_4.jpg","msrp_display_actual_price_type":"0","url_key":"apollo-running-short-36-black","swatch_image":"no_selection","short_description":"The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. ","status":"1","visibility":"4","featured":"0","ves_enable_order":"1","ves_enable_quote":"0","request_path":"apollo-running-short-36-black.html"}}]}
     */

    private ResponseBean response;

    public ResponseBean getResponse() {
        return response;
    }

    public void setResponse(ResponseBean response) {
        this.response = response;
    }

    public static class ResponseBean {
        /**
         * success : true
         * status : 1
         * code : 200
         * message : Record Found
         * data : [{"wishlist_item_id":"27","wishlist_id":"38","product_id":"125","store_id":"1","added_at":"2018-10-29 13:25:01","description":null,"qty":1,"product":{"entity_id":"125","attribute_set_id":"4","type_id":"simple","vendor_id":"18","sku":"MSH02-36-Black","has_options":"0","required_options":"0","created_at":"2018-07-17 10:20:17","updated_at":"2018-10-19 09:12:09","ves_category_ids":"","search_weight":"0","price":"32.5000","tax_class_id":"2","final_price":null,"minimal_price":"32.5000","min_price":"32.5000","max_price":"32.5000","tier_price":null,"name":"خلاص الاحساء","image":"/4/_/4_4.jpg","small_image":"/4/_/4_4.jpg","thumbnail":"/4/_/4_4.jpg","msrp_display_actual_price_type":"0","url_key":"apollo-running-short-36-black","swatch_image":"no_selection","short_description":"The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. ","status":"1","visibility":"4","featured":"0","ves_enable_order":"1","ves_enable_quote":"0","request_path":"apollo-running-short-36-black.html"}}]
         */

        private boolean success;
        private int status;
        private String code;
        private String message;
        private List<DataBean> data;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * wishlist_item_id : 27
             * wishlist_id : 38
             * product_id : 125
             * store_id : 1
             * added_at : 2018-10-29 13:25:01
             * description : null
             * qty : 1
             * product : {"entity_id":"125","attribute_set_id":"4","type_id":"simple","vendor_id":"18","sku":"MSH02-36-Black","has_options":"0","required_options":"0","created_at":"2018-07-17 10:20:17","updated_at":"2018-10-19 09:12:09","ves_category_ids":"","search_weight":"0","price":"32.5000","tax_class_id":"2","final_price":null,"minimal_price":"32.5000","min_price":"32.5000","max_price":"32.5000","tier_price":null,"name":"خلاص الاحساء","image":"/4/_/4_4.jpg","small_image":"/4/_/4_4.jpg","thumbnail":"/4/_/4_4.jpg","msrp_display_actual_price_type":"0","url_key":"apollo-running-short-36-black","swatch_image":"no_selection","short_description":"The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. ","status":"1","visibility":"4","featured":"0","ves_enable_order":"1","ves_enable_quote":"0","request_path":"apollo-running-short-36-black.html"}
             */

            private String wishlist_item_id;
            private String wishlist_id;
            private String product_id;
            private String store_id;
            private String added_at;
            private Object description;
            private int qty;
            private ProductBean product;

            public String getWishlist_item_id() {
                return wishlist_item_id;
            }

            public void setWishlist_item_id(String wishlist_item_id) {
                this.wishlist_item_id = wishlist_item_id;
            }

            public String getWishlist_id() {
                return wishlist_id;
            }

            public void setWishlist_id(String wishlist_id) {
                this.wishlist_id = wishlist_id;
            }

            public String getProduct_id() {
                return product_id;
            }

            public void setProduct_id(String product_id) {
                this.product_id = product_id;
            }

            public String getStore_id() {
                return store_id;
            }

            public void setStore_id(String store_id) {
                this.store_id = store_id;
            }

            public String getAdded_at() {
                return added_at;
            }

            public void setAdded_at(String added_at) {
                this.added_at = added_at;
            }

            public Object getDescription() {
                return description;
            }

            public void setDescription(Object description) {
                this.description = description;
            }

            public int getQty() {
                return qty;
            }

            public void setQty(int qty) {
                this.qty = qty;
            }

            public ProductBean getProduct() {
                return product;
            }

            public void setProduct(ProductBean product) {
                this.product = product;
            }

            public static class ProductBean {
                /**
                 * entity_id : 125
                 * attribute_set_id : 4
                 * type_id : simple
                 * vendor_id : 18
                 * sku : MSH02-36-Black
                 * has_options : 0
                 * required_options : 0
                 * created_at : 2018-07-17 10:20:17
                 * updated_at : 2018-10-19 09:12:09
                 * ves_category_ids :
                 * search_weight : 0
                 * price : 32.5000
                 * tax_class_id : 2
                 * final_price : null
                 * minimal_price : 32.5000
                 * min_price : 32.5000
                 * max_price : 32.5000
                 * tier_price : null
                 * name : خلاص الاحساء
                 * image : /4/_/4_4.jpg
                 * small_image : /4/_/4_4.jpg
                 * thumbnail : /4/_/4_4.jpg
                 * msrp_display_actual_price_type : 0
                 * url_key : apollo-running-short-36-black
                 * swatch_image : no_selection
                 * short_description : The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English.
                 * status : 1
                 * visibility : 4
                 * featured : 0
                 * ves_enable_order : 1
                 * ves_enable_quote : 0
                 * request_path : apollo-running-short-36-black.html
                 */

                private String entity_id;
                private String attribute_set_id;
                private String type_id;
                private String vendor_id;
                private String sku;
                private String has_options;
                private String required_options;
                private String created_at;
                private String updated_at;
                private String ves_category_ids;
                private String search_weight;
                private String price;
                private String tax_class_id;
                private Object final_price;
                private String minimal_price;
                private String min_price;
                private String max_price;
                private Object tier_price;
                private String name;
                private String image;
                private String small_image;
                private String thumbnail;
                private String msrp_display_actual_price_type;
                private String url_key;
                private String swatch_image;
                private String short_description;
                private String status;
                private String visibility;
                private String featured;
                private String ves_enable_order;
                private String ves_enable_quote;
                private String request_path;

                public String getEntity_id() {
                    return entity_id;
                }

                public void setEntity_id(String entity_id) {
                    this.entity_id = entity_id;
                }

                public String getAttribute_set_id() {
                    return attribute_set_id;
                }

                public void setAttribute_set_id(String attribute_set_id) {
                    this.attribute_set_id = attribute_set_id;
                }

                public String getType_id() {
                    return type_id;
                }

                public void setType_id(String type_id) {
                    this.type_id = type_id;
                }

                public String getVendor_id() {
                    return vendor_id;
                }

                public void setVendor_id(String vendor_id) {
                    this.vendor_id = vendor_id;
                }

                public String getSku() {
                    return sku;
                }

                public void setSku(String sku) {
                    this.sku = sku;
                }

                public String getHas_options() {
                    return has_options;
                }

                public void setHas_options(String has_options) {
                    this.has_options = has_options;
                }

                public String getRequired_options() {
                    return required_options;
                }

                public void setRequired_options(String required_options) {
                    this.required_options = required_options;
                }

                public String getCreated_at() {
                    return created_at;
                }

                public void setCreated_at(String created_at) {
                    this.created_at = created_at;
                }

                public String getUpdated_at() {
                    return updated_at;
                }

                public void setUpdated_at(String updated_at) {
                    this.updated_at = updated_at;
                }

                public String getVes_category_ids() {
                    return ves_category_ids;
                }

                public void setVes_category_ids(String ves_category_ids) {
                    this.ves_category_ids = ves_category_ids;
                }

                public String getSearch_weight() {
                    return search_weight;
                }

                public void setSearch_weight(String search_weight) {
                    this.search_weight = search_weight;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public String getTax_class_id() {
                    return tax_class_id;
                }

                public void setTax_class_id(String tax_class_id) {
                    this.tax_class_id = tax_class_id;
                }

                public Object getFinal_price() {
                    return final_price;
                }

                public void setFinal_price(Object final_price) {
                    this.final_price = final_price;
                }

                public String getMinimal_price() {
                    return minimal_price;
                }

                public void setMinimal_price(String minimal_price) {
                    this.minimal_price = minimal_price;
                }

                public String getMin_price() {
                    return min_price;
                }

                public void setMin_price(String min_price) {
                    this.min_price = min_price;
                }

                public String getMax_price() {
                    return max_price;
                }

                public void setMax_price(String max_price) {
                    this.max_price = max_price;
                }

                public Object getTier_price() {
                    return tier_price;
                }

                public void setTier_price(Object tier_price) {
                    this.tier_price = tier_price;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }

                public String getSmall_image() {
                    return small_image;
                }

                public void setSmall_image(String small_image) {
                    this.small_image = small_image;
                }

                public String getThumbnail() {
                    return thumbnail;
                }

                public void setThumbnail(String thumbnail) {
                    this.thumbnail = thumbnail;
                }

                public String getMsrp_display_actual_price_type() {
                    return msrp_display_actual_price_type;
                }

                public void setMsrp_display_actual_price_type(String msrp_display_actual_price_type) {
                    this.msrp_display_actual_price_type = msrp_display_actual_price_type;
                }

                public String getUrl_key() {
                    return url_key;
                }

                public void setUrl_key(String url_key) {
                    this.url_key = url_key;
                }

                public String getSwatch_image() {
                    return swatch_image;
                }

                public void setSwatch_image(String swatch_image) {
                    this.swatch_image = swatch_image;
                }

                public String getShort_description() {
                    return short_description;
                }

                public void setShort_description(String short_description) {
                    this.short_description = short_description;
                }

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }

                public String getVisibility() {
                    return visibility;
                }

                public void setVisibility(String visibility) {
                    this.visibility = visibility;
                }

                public String getFeatured() {
                    return featured;
                }

                public void setFeatured(String featured) {
                    this.featured = featured;
                }

                public String getVes_enable_order() {
                    return ves_enable_order;
                }

                public void setVes_enable_order(String ves_enable_order) {
                    this.ves_enable_order = ves_enable_order;
                }

                public String getVes_enable_quote() {
                    return ves_enable_quote;
                }

                public void setVes_enable_quote(String ves_enable_quote) {
                    this.ves_enable_quote = ves_enable_quote;
                }

                public String getRequest_path() {
                    return request_path;
                }

                public void setRequest_path(String request_path) {
                    this.request_path = request_path;
                }
            }
        }
    }
}
