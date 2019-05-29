package bidding.app.extra;

/*
 * Created by rishav on 17/1/17.
 */

import org.json.JSONObject;

public class Event {
    private boolean isConnected;
    private int key;
    private String value, company_id, cab_alias;
    private double latitude, longitude;
    private JSONObject jsonObject;

    public Event(int key, boolean isConnected) {
        this.key = key;
        this.isConnected = isConnected;
    }

    public Event(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public Event(int key, JSONObject jsonObject) {
        this.key = key;
        this.jsonObject = jsonObject;
    }

    public Event(int key, String company_id, String cab_alias) {
        this.key = key;
        this.company_id = company_id;
        this.cab_alias = cab_alias;
    }

    public Event(int key, double latitude, double longitude) {
        this.key = key;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public int getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String getCompany_id() {
        return company_id;
    }

    public String getCab_alias() {
        return cab_alias;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }
}
