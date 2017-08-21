package co.getchannel.channel.models;

import java.util.Map;

/**
 * Created by Admin on 8/21/2017.
 */

public class Client {
    private String userID;
    private Map<String,String> userData;
    private Map<String,String> deviceInfo;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Map<String, String> getUserData() {
        return userData;
    }

    public void setUserData(Map<String, String> userData) {
        this.userData = userData;
    }

    public Map<String, String> getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(Map<String, String> deviceInfo) {
        this.deviceInfo = deviceInfo;
    }
}
