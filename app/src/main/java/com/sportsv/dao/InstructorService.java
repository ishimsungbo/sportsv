package com.sportsv.dao;

import com.sportsv.vo.Instructor;
import com.sportsv.vo.ServerResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by sungbo on 2016-06-27.
 */
public interface InstructorService {


    @POST("/all/ins/saveInstructor")
    Call<ServerResult> saveInstructor(@Body Instructor ins);

    @GET("/api/ins/insLogin")
    Call<ServerResult> insLogin();
}
