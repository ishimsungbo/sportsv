package com.sportsv.serverservice;


import android.util.Log;

import com.sportsv.common.Common;
import com.sportsv.dao.PointService;
import com.sportsv.dao.UserMissionService;
import com.sportsv.dao.UserService;
import com.squareup.okhttp.Authenticator;
import com.squareup.okhttp.Credentials;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.Proxy;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;


/**
 * Created by sungbo on 2016-06-07.
 */
public class RetrofitService {

    private static final String TAG = "RetrofitService";

    private static Retrofit getRetrofit(){


        Log.d(TAG, "RetrofitService.getRetrofit() excuting");

        OkHttpClient httpClient = new OkHttpClient();


        httpClient.setAuthenticator(new Authenticator() {
            @Override
            public Request authenticate(Proxy proxy, Response response) throws IOException {

                //비번설정
                String credential = Credentials.basic("SportsVeteranUser","sksmsRhrtjdrhdgkrhakfxpek");
                return response.request().newBuilder().header("Authorization", credential).build();
            }

            @Override
            public Request authenticateProxy(Proxy proxy, Response response) throws IOException {
                return null;
            }
        });

        return new Retrofit.Builder().client(httpClient)
                .baseUrl(Common.SERVER_ADRESS)
                .addConverterFactory(
                        GsonConverterFactory.create()
                ).build();
    }

    public static UserService userService(){
        Log.d(TAG,"RetrofitService.userService() excuting");
        return getRetrofit().create(UserService.class);
    }

    public static PointService pointService(){
        Log.d(TAG,"RetrofitService.pointService() excuting");
        return getRetrofit().create(PointService.class);
    }

    public static UserMissionService userMissionService(){
        Log.d(TAG,"RetrofitService.userMissionService() excuting");
        return getRetrofit().create(UserMissionService.class);
    }



}
