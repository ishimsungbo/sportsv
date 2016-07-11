package com.sportsv.retropit;

import android.content.Context;
import android.util.Log;


import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by sungbo on 2016-06-20.
 * * 쿠키 인터셉터
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
