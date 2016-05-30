package com.sportsv.dao;

import com.sportsv.vo.User;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by sungbo on 2016-05-27.
 */
public interface UserService {

    @POST("/api/usercreate")
    Call<Integer> createUser(@Body User user);

    @GET("/api/existuser")
    Call<Integer> existUser(@Query("email") String email);

}
