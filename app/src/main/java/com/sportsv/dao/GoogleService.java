package com.sportsv.dao;

import com.sportsv.vo.ServerResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by sungbo on 2016-06-29.
 */
public interface GoogleService {

    @GET("/all/sendfcmtoek")
    Call<ServerResult> sendFcmToken(@Query("token") String token);
}
