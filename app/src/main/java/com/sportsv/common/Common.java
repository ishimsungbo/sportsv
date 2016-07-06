package com.sportsv.common;

import android.os.Build;

/**
 * Created by sungbo on 2016-05-27.
 */
public class Common {
    public static String SERVER_ADRESS = "http://192.168.0.50:8080";
    public static String SERVER_USER_IMGFILEADRESS = SERVER_ADRESS + "/resources/userimg/";
    public static String SERVER_INS_IMGFILEADRESS = SERVER_ADRESS + "/resources/instructorimg/";
    public static String VETERAN_SNSID = "999999999999999";

    public static String getDeviceSerialNumber() {
        try {
            return (String) Build.class.getField("SERIAL").get(null);
        } catch (Exception ignored) {
            return null;
        }
    }

}
