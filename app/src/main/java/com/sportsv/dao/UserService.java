package com.sportsv.dao;

import com.sportsv.vo.User;
import com.squareup.okhttp.RequestBody;

import org.springframework.core.io.Resource;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;
import retrofit.http.Streaming;

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

    //출석체크
    @GET("/api/user/userdailycheck")
    Call<String> daliyCheck(
            @Query("uid") String uid,
            @Query("pointtype") String pointType,
            @Query("lang") String lang
    );

    @Multipart
    @POST("/api/user/fileupload")
    Call<String> fileupload(
            @Part(value = "uid") RequestBody  uid
            //@Part(value = "filename") RequestBody  filename,
            //@Part("file") RequestBody file,
            //@Part(value = "profileimgurl") RequestBody  profileimgurl
    );



}
