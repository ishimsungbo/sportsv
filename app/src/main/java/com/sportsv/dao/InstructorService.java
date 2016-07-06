package com.sportsv.dao;

import com.sportsv.vo.Instructor;
import com.sportsv.vo.InstructorPointHistory;
import com.sportsv.vo.ServerResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by sungbo on 2016-06-27.
 */
public interface InstructorService {


    @POST("/all/ins/saveInstructor")
    Call<ServerResult> saveInstructor(@Body Instructor ins);

    @POST("/api/ins/insLogin")
    Call<Instructor> insLogin(@Body Instructor instructor);

    /**********
     * 강사의 포인트 정책
     * ************/
    @GET("/api/ins/getPointHis")
    Call<InstructorPointHistory> getPointHis(@Query("insid") int insid);
}
