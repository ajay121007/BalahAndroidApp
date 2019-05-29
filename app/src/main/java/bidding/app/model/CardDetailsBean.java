package bidding.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CardDetailsBean {

    @SerializedName("response")
    @Expose
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public class Response {

        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("data")
        @Expose
        private List<Datum> data = null;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<Datum> getData() {
            return data;
        }

        public void setData(List<Datum> data) {
            this.data = data;
        }

    }

    public class Datum {

        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("card_type")
        @Expose
        private String cardType;
        @SerializedName("card_number")
        @Expose
        private String cardNumber;
        @SerializedName("card_holderName")
        @Expose
        private String cardHolderName;
        @SerializedName("ex_month")
        @Expose
        private String exMonth;
        @SerializedName("ex_year")
        @Expose
        private String exYear;
        @SerializedName("cvv")
        @Expose
        private String cvv;
        @SerializedName("stripe_token")
        @Expose
        private String stripeToken;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public void setCardType(String cardType) {
            this.cardType = cardType;
        }

        public String getCardType() {
            return cardType;
        }

        public String getCardNumber() {
            return cardNumber;
        }

        public void setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
        }

        public String getCardHolderName() {
            return cardHolderName;
        }

        public void setCardHolderName(String cardHolderName) {
            this.cardHolderName = cardHolderName;
        }

        public String getExMonth() {
            return exMonth;
        }

        public void setExMonth(String exMonth) {
            this.exMonth = exMonth;
        }

        public String getExYear() {
            return exYear;
        }

        public void setExYear(String exYear) {
            this.exYear = exYear;
        }

        public String getCvv() {
            return cvv;
        }

        public void setCvv(String cvv) {
            this.cvv = cvv;
        }

        public String getStripeToken() {
            return stripeToken;
        }

        public void setStripeToken(String stripeToken) {
            this.stripeToken = stripeToken;
        }

    }
}