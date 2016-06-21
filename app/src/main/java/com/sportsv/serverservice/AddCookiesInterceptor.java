package com.sportsv.serverservice;

import android.content.Context;
import android.util.Log;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashSet;

/**
 * Created by sungbo on 2016-06-20.
 */
public class AddCookiesInterceptor implements Interceptor {

    private DalgonaSharedPreferences mDsp;

    public AddCookiesInterceptor(Context context){
        mDsp = DalgonaSharedPreferences.getInstanceOf(context);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        HashSet<String> preferences = (HashSet) mDsp.getHashSet(DalgonaSharedPreferences.KEY_COOKIE, new HashSet<String>());
        for (String cookie : preferences) {
            builder.addHeader("Cookie", cookie);
            Log.v("추가한 쿠키값은 ==== : ", "Adding Header: ================  " + cookie);
        }

        return chain.proceed(builder.build());
    }
}
