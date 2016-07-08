package com.sportsv.dao;

import com.sportsv.vo.ServerResult;
import com.sportsv.vo.User;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;


/**
 * Created by sungbo on 2016-05-27.
 */
public interface UserService {

    @POST("/all/insert")
    Call<ServerResult> createUser(@Body User user);

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

    //유저사진 업데이트
    @Multipart
    @POST("/api/user/fileupload")
    Call<ServerResult> fileupload(
            @Part("uid") RequestBody uid,
            @Part("filename") RequestBody filename,
            @Part("profileimgurl") RequestBody profileimgurl,
            @Part MultipartBody.Part file
    );

}
