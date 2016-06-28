package com.sportsv.dao;

import com.sportsv.vo.ServerResult;
import com.sportsv.vo.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


/**
 * Created by sungbo on 2016-05-27.
 */
public interface UserService {

    @POST("/all/insert")
    Call<Integer> createUser(@Body User user);

    @GET("/all/existuser")
    Call<User> getUser(
            @Query("snstype") String snstype,
            @Query("snsid") String snsid,
            @Query("password") String password,
            @Query("useremail") String useremail);

    @GET("/api/user/userCheck")
    Call<ServerResult> getUserCheck(
            @Query("snstype") String snstype,
            @Query("email") String email);


    @GET("/all/usertest")
    Call<User> getUserTest();

}
