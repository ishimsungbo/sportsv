package com.sportsv.common;

import com.google.android.gms.common.Scopes;
import com.google.api.services.youtube.YouTubeScopes;

/**
 * Created by sungbo on 2016-05-30.
 * 유투브 api를 사용하기 위한 권한
 * https://console.developers.google.com/apis/library?project=sportsv-1327&hl=ko
 */
public class Auth {
    // Register an API key here: https://console.developers.google.com
    public static final String KEY = "AIzaSyCfQm8zxmo3OIM4oSFZ846jmMWktTt0zdE";
    public static final String[] SCOPES = {Scopes.PROFILE, YouTubeScopes.YOUTUBE};
    public static String accountName="null";

}
