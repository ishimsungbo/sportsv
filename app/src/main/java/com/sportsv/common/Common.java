package com.sportsv.common;

import android.os.Build;

/**
 * Created by sungbo on 2016-05-27.
 */
public class Common {
    public static String SERVER_ADRESS = "http://192.168.10.4:8080"; //192.168.10.4//"http://192.168.0.50:8080";
    public static String SERVER_IMGFILEADRESS = SERVER_ADRESS + "/resources/userimg/";
    public static String VETERAN_SNSID = "999999999999999";

    public static String getDeviceSerialNumber() {
        try {
            return (String) Build.class.getField("SERIAL").get(null);
        } catch (Exception ignored) {
            return null;
        }
    }

}
