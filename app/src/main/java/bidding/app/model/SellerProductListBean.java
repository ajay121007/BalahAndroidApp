package bidding.app.model;

import java.util.List;

public class SellerProductListBean {
    /**
     * items : [{"id":"299","name":"Organic Food","sku":"Organic Food-118-1","attribute_set_id":"4","type_id":"simple","status":"1","visibility":"4","featured":"0","vendor_id":"24","created_at":"2018-10-30 10:26:34","updated_at":"2018-10-30 10:26:34","price":"140.0000","tax_class_id":"2","ves_enable_order":"0","ves_enable_quote":"0","description":null,"short_description":null,"url_key":"test-html","quantity_and_stock_status":{"is_in_stock":false,"qty":null},"warehouse_qty":{},"image":null,"small_image":null,"thumbnail":null,"media_gallery":{"images":[],"values":[]},"category_ids":["2"],"is_salable":false}]
     * search_criteria : {"filter_groups":[{"filters":[{"field":"status","value":"1","condition_type":"eq"}]}],"sort_orders":[{"field":"price","direction":"DESC"}],"page_size":5,"current_page":1}
     * total_count : 1
     */

    private SearchCriteriaBean search_criteria;
    private int total_count;
    private List<ItemsBean> items;

    public SearchCriteriaBean getSearch_criteria() {
        return search_criteria;
    }

    public void setSearch_criteria(SearchCriteriaBean search_criteria) {
        this.search_criteria = search_criteria;
    }

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class SearchCriteriaBean {
        /**
         * filter_groups : [{"filters":[{"field":"status","value":"1","condition_type":"eq"}]}]
         * sort_orders : [{"field":"price","direction":"DESC"}]
         * page_size : 5
         * current_page : 1
         */

        private int page_size;
        private int current_page;
        private List<FilterGroupsBean> filter_groups;
        private List<SortOrdersBean> sort_orders;

        public int getPage_size() {
            return page_size;
        }

        public void setPage_size(int page_size) {
            this.page_size = page_size;
        }

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public List<FilterGroupsBean> getFilter_groups() {
            return filter_groups;
        }

        public void setFilter_groups(List<FilterGroupsBean> filter_groups) {
            this.filter_groups = filter_groups;
        }

        public List<SortOrdersBean> getSort_orders() {
            return sort_orders;
        }

        public void setSort_orders(List<SortOrdersBean> sort_orders) {
            this.sort_orders = sort_orders;
        }

        public static class FilterGroupsBean {
            private List<FiltersBean> filters;

            public List<FiltersBean> getFilters() {
                return filters;
            }

            public void setFilters(List<FiltersBean> filters) {
                this.filters = filters;
            }

            public static class FiltersBean {
                /**
                 * field : status
                 * value : 1
                 * condition_type : eq
                 */

                private String field;
                private String value;
                private String condition_type;

                public String getField() {
                    return field;
                }

                public void setField(String field) {
                    this.field = field;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }

                public String getCondition_type() {
                    return condition_type;
                }

                public void setCondition_type(String condition_type) {
                    this.condition_type = condition_type;
                }
            }
        }

        public static class SortOrdersBean {
            /**
             * field : price
             * direction : DESC
             */

            private String field;
            private String direction;

            public String getField() {
                return field;
            }

            public void setField(String field) {
                this.field = field;
            }

            public String getDirection() {
                return direction;
            }

            public void setDirection(String direction) {
                this.direction = direction;
            }
        }
    }

    public static class ItemsBean {
        /**
         * id : 299
         * name : Organic Food
         * sku : Organic Food-118-1
         * attribute_set_id : 4
         * type_id : simple
         * status : 1
         * visibility : 4
         * featured : 0
         * vendor_id : 24
         * created_at : 2018-10-30 10:26:34
         * updated_at : 2018-10-30 10:26:34
         * price : 140.0000
         * tax_class_id : 2
         * ves_enable_order : 0
         * ves_enable_quote : 0
         * description : null
         * short_description : null
         * url_key : test-html
         * quantity_and_stock_status : {"is_in_stock":false,"qty":null}
         * warehouse_qty : {}
         * image : null
         * small_image : null
         * thumbnail : null
         * media_gallery : {"images":[],"values":[]}
         * category_ids : ["2"]
         * is_salable : false
         */

        private String id;
        private String name;
        private String sku;
        private String attribute_set_id;
        private String type_id;
        private String status;
        private String visibility;
        private String featured;
        private String vendor_id;
        private String created_at;
        private String updated_at;
        private String price;
        private String tax_class_id;
        private String ves_enable_order;
        private String ves_enable_quote;
        private Object description;
        private Object short_description;
        private String url_key;
        private QuantityAndStockStatusBean quantity_and_stock_status;
        private WarehouseQtyBean warehouse_qty;
        private Object image;
        private Object small_image;
        private Object thumbnail;
        private MediaGalleryBean media_gallery;
        private boolean is_salable;
        private List<String> category_ids;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
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

        public String getVendor_id() {
            return vendor_id;
        }

        public void setVendor_id(String vendor_id) {
            this.vendor_id = vendor_id;
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

        public Object getDescription() {
            return description;
        }

        public void setDescription(Object description) {
            this.description = description;
        }

        public Object getShort_description() {
            return short_description;
        }

        public void setShort_description(Object short_description) {
            this.short_description = short_description;
        }

        public String getUrl_key() {
            return url_key;
        }

        public void setUrl_key(String url_key) {
            this.url_key = url_key;
        }

        public QuantityAndStockStatusBean getQuantity_and_stock_status() {
            return quantity_and_stock_status;
        }

        public void setQuantity_and_stock_status(QuantityAndStockStatusBean quantity_and_stock_status) {
            this.quantity_and_stock_status = quantity_and_stock_status;
        }

        public WarehouseQtyBean getWarehouse_qty() {
            return warehouse_qty;
        }

        public void setWarehouse_qty(WarehouseQtyBean warehouse_qty) {
            this.warehouse_qty = warehouse_qty;
        }

        public Object getImage() {
            return image;
        }

        public void setImage(Object image) {
            this.image = image;
        }

        public Object getSmall_image() {
            return small_image;
        }

        public void setSmall_image(Object small_image) {
            this.small_image = small_image;
        }

        public Object getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(Object thumbnail) {
            this.thumbnail = thumbnail;
        }

        public MediaGalleryBean getMedia_gallery() {
            return media_gallery;
        }

        public void setMedia_gallery(MediaGalleryBean media_gallery) {
            this.media_gallery = media_gallery;
        }

        public boolean isIs_salable() {
            return is_salable;
        }

        public void setIs_salable(boolean is_salable) {
            this.is_salable = is_salable;
        }

        public List<String> getCategory_ids() {
            return category_ids;
        }

        public void setCategory_ids(List<String> category_ids) {
            this.category_ids = category_ids;
        }

        public static class QuantityAndStockStatusBean {
        }

        public static class WarehouseQtyBean {
        }

        public static class MediaGalleryBean {
            private List<?> images;
            private List<?> values;

            public List<?> getImages() {
                return images;
            }

            public void setImages(List<?> images) {
                this.images = images;
            }

            public List<?> getValues() {
                return values;
            }

            public void setValues(List<?> values) {
                this.values = values;
            }
        }
    }
}
