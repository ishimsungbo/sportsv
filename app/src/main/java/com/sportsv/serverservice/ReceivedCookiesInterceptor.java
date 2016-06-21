package com.sportsv.serverservice;

import android.content.Context;
import android.util.Log;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashSet;

/**
 * Created by sungbo on 2016-06-20.
 */
public class ReceivedCookiesInterceptor implements Interceptor{

    private static final String TAG = "ReceivedCookiesInterceptor";
    private DalgonaSharedPreferences mDsp;

    public ReceivedCookiesInterceptor(Context context){
        mDsp = DalgonaSharedPreferences.getInstanceOf(context);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Response response = chain.proceed(chain.request());

        try {

            if (!response.header("Set-Cookie").isEmpty()) {
                HashSet<String> cookies = new HashSet<>();

                Log.d("추가한 쿠키값은 : ", " 쿠키값을 가져옵니다");

                for (String header : response.headers("Set-Cookie")) {
                    cookies.add(header);
                    Log.d("추가한 쿠키값은 : ", " 쿠키값 : " + header);
                }

                mDsp.putHashSet(DalgonaSharedPreferences.KEY_COOKIE, cookies);
            }
        }catch (Exception e){
            Log.d("쿠키에러","에러는 : "+e.getMessage());
            e.printStackTrace();
        }

        return response;
    }
}
