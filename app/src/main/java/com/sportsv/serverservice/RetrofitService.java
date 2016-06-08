package com.sportsv.serverservice;

import android.util.Log;

import com.sportsv.common.Common;
import com.sportsv.dao.UserService;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by sungbo on 2016-06-07.
 */
public class RetrofitService {

    private static final String TAG = "RetrofitService";

    private static Retrofit getRetrofit(){

        Log.d(TAG,"RetrofitService.getRetrofit() excuting");

        return new Retrofit.Builder()
                .baseUrl(Common.SERVER_ADRESS)
                .addConverterFactory(
                        GsonConverterFactory.create()
                ).build();
    }

    public static UserService userService(){

        Log.d(TAG,"RetrofitService.userService() excuting");

        return getRetrofit().create(UserService.class);
    }



}
