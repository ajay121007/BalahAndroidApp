package bidding.app.view.activity.loginactivity.requestpojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Params {

        @SerializedName("country_code")
        @Expose
        private String countryCode;
        @SerializedName("phone_number")
        @Expose
        private String phoneNumber;

        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

    }