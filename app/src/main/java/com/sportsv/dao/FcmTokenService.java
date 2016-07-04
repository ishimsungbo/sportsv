package com.sportsv.dao;

import com.sportsv.vo.FcmToken;
import com.sportsv.vo.ServerResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by sungbo on 2016-06-30.
 */
public interface FcmTokenService {

    @POST("/all/saveToken")
    public Call<ServerResult> saveToken(@Body FcmToken fcmToken);

    @POST("/all/updateToken")
    public Call<ServerResult> updateToken(@Body FcmToken fcmToken);

}
