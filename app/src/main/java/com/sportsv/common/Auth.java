package com.sportsv.common;

import com.google.android.gms.common.Scopes;
import com.google.api.services.youtube.YouTubeScopes;

/**
 * Created by sungbo on 2016-05-30.
 */
public class Auth {
    // Register an API key here: https://console.developers.google.com
    public static final String KEY = "AIzaSyCfQm8zxmo3OIM4oSFZ846jmMWktTt0zdE";

    public static final String[] SCOPES = {Scopes.PROFILE, YouTubeScopes.YOUTUBE};
}
