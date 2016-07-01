package com.sportsv.dao;

import com.sportsv.vo.FcmToken;
import com.sportsv.vo.FeedbackHeader;
import com.sportsv.vo.ServerResult;
import com.sportsv.vo.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by sungbo on 2016-06-30.
 */
public interface FcmTokenService {

    @POST("/all/saveFcmToken")
    Call<ServerResult> saveFcmToken(@Body FcmToken fcmToken);

    @POST("/all/getFcmTokenCount")
    Call<ServerResult> getFcmTokenCount(@Body FcmToken fcmToken);

    @POST("/all/updateFcmToken")
    Call<ServerResult> updateFcmToken(@Body FcmToken fcmToken);

    @POST("/api/user/checkToken")
    Call<ServerResult> checkToken(@Body FcmToken fcmToken);

}
