package bidding.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductBySku {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("attribute_set_id")
    @Expose
    private Integer attributeSetId;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("visibility")
    @Expose
    private Integer visibility;
    @SerializedName("type_id")
    @Expose
    private String typeId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("extension_attributes")
    @Expose
    private ExtensionAttributes extensionAttributes;
    @SerializedName("product_links")
    @Expose
    private List<Object> productLinks = null;
    @SerializedName("options")
    @Expose
    private List<Object> options = null;
    @SerializedName("media_gallery_entries")
    @Expose
    private List<MediaGalleryEntry> mediaGalleryEntries = null;
    @SerializedName("tier_prices")
    @Expose
    private List<Object> tierPrices = null;
    @SerializedName("custom_attributes")
    @Expose
    private List<CustomAttribute> customAttributes = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAttributeSetId() {
        return attributeSetId;
    }

    public void setAttributeSetId(Integer attributeSetId) {
        this.attributeSetId = attributeSetId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public ExtensionAttributes getExtensionAttributes() {
        return extensionAttributes;
    }

    public void setExtensionAttributes(ExtensionAttributes extensionAttributes) {
        this.extensionAttributes = extensionAttributes;
    }

    public List<Object> getProductLinks() {
        return productLinks;
    }

    public void setProductLinks(List<Object> productLinks) {
        this.productLinks = productLinks;
    }

    public List<Object> getOptions() {
        return options;
    }

    public void setOptions(List<Object> options) {
        this.options = options;
    }

    public List<MediaGalleryEntry> getMediaGalleryEntries() {
        return mediaGalleryEntries;
    }

    public void setMediaGalleryEntries(List<MediaGalleryEntry> mediaGalleryEntries) {
        this.mediaGalleryEntries = mediaGalleryEntries;
    }

    public List<Object> getTierPrices() {
        return tierPrices;
    }

    public void setTierPrices(List<Object> tierPrices) {
        this.tierPrices = tierPrices;
    }

    public List<CustomAttribute> getCustomAttributes() {
        return customAttributes;
    }

    public void setCustomAttributes(List<CustomAttribute> customAttributes) {
        this.customAttributes = customAttributes;
    }

    public class CategoryLink {

        @SerializedName("position")
        @Expose
        private Integer position;
        @SerializedName("category_id")
        @Expose
        private String categoryId;

        public Integer getPosition() {
            return position;
        }

        public void setPosition(Integer position) {
            this.position = position;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

    }

    public class CustomAttribute {

        @SerializedName("attribute_code")
        @Expose
        private String attributeCode;
        @SerializedName("value")
        @Expose
        private String value;

        public String getAttributeCode() {
            return attributeCode;
        }

        public void setAttributeCode(String attributeCode) {
            this.attributeCode = attributeCode;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

    }

    public class ExtensionAttributes {

        @SerializedName("website_ids")
        @Expose
        private List<Integer> websiteIds = null;
        @SerializedName("category_links")
        @Expose
        private List<CategoryLink> categoryLinks = null;

        public List<Integer> getWebsiteIds() {
            return websiteIds;
        }

        public void setWebsiteIds(List<Integer> websiteIds) {
            this.websiteIds = websiteIds;
        }

        public List<CategoryLink> getCategoryLinks() {
            return categoryLinks;
        }

        public void setCategoryLinks(List<CategoryLink> categoryLinks) {
            this.categoryLinks = categoryLinks;
        }

    }

    public class MediaGalleryEntry {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("media_type")
        @Expose
        private String mediaType;
        @SerializedName("label")
        @Expose
        private Object label;
        @SerializedName("position")
        @Expose
        private Integer position;
        @SerializedName("disabled")
        @Expose
        private Boolean disabled;
        @SerializedName("types")
        @Expose
        private List<String> types = null;
        @SerializedName("file")
        @Expose
        private String file;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getMediaType() {
            return mediaType;
        }

        public void setMediaType(String mediaType) {
            this.mediaType = mediaType;
        }

        public Object getLabel() {
            return label;
        }

        public void setLabel(Object label) {
            this.label = label;
        }

        public Integer getPosition() {
            return position;
        }

        public void setPosition(Integer position) {
            this.position = position;
        }

        public Boolean getDisabled() {
            return disabled;
        }

        public void setDisabled(Boolean disabled) {
            this.disabled = disabled;
        }

        public List<String> getTypes() {
            return types;
        }

        public void setTypes(List<String> types) {
            this.types = types;
        }

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }

    }
}