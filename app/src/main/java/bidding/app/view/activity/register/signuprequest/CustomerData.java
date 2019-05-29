package bidding.app.view.activity.register.signuprequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerData {

@SerializedName("firstname")
@Expose
private String firstname;
@SerializedName("lastname")
@Expose
private String lastname;
@SerializedName("email")
@Expose
private String email;
@SerializedName("country_code")
@Expose
private String countryCode;
@SerializedName("phone_number")
@Expose
private String phoneNumber;

public String getFirstname() {
return firstname;
}

public void setFirstname(String firstname) {
this.firstname = firstname;
}

public String getLastname() {
return lastname;
}

public void setLastname(String lastname) {
this.lastname = lastname;
}

public String getEmail() {
return email;
}

public void setEmail(String email) {
this.email = email;
}

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