package com.sportsv.retropit;

import android.content.Context;
import android.util.Log;


import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by sungbo on 2016-06-20.
 * * 쿠키 인터셉터
 */
public class ReceivedCookiesInterceptor implements Interceptor{
    private static final String TAG = "ReceivedCookiesIt";
    private DalgonaSharedPreferences mDsp;

    public ReceivedCookiesInterceptor(Context context){
        mDsp = DalgonaSharedPreferences.getInstanceOf(context);
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {

        Response response = chain.proceed(chain.request());

        try {

            if (!response.header("Set-Cookie").isEmpty()) {

                HashSet<String> cookies = new HashSet<>();

                for (String header : response.headers("Set-Cookie")) {
                    cookies.add(header);
                }

                mDsp.putHashSet(DalgonaSharedPreferences.KEY_COOKIE, cookies);
            }
        }catch (Exception e){
            Log.d(TAG," : 쿠키 값을 이미 받았습니다 : "+e.getMessage());
        }

        return response;
    }

}
