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

    @POST("/api/user/insert")
    Call<Integer> createUser(@Body User user);

    @GET("/api/user/existuser")
    Call<User> getUser(
            @Query("snstype") String snstype,
            @Query("snsid") String snsid,
            @Query("password") String password,
            @Query("useremail") String useremail);

    @GET("/api/user/userCheck")
    Call<Integer> getUserCheck(
            @Query("snstype") String snstype,
            @Query("email") String email,
            @Query("pw") String pw);

}
