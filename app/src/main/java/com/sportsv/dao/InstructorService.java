package com.sportsv.dao;

import com.sportsv.vo.Instructor;
import com.sportsv.vo.InstructorPointHistory;
import com.sportsv.vo.ServerResult;

import java.util.List;

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

    @Multipart
    @POST("/api/ins/fileupload")
    Call<ServerResult> fileupload(
            @Part("instructorid") RequestBody instructorid,
            @Part("filename") RequestBody filename,
            @Part("profileimgurl") RequestBody profileimgurl,
            @Part MultipartBody.Part file);

    @GET("/all/team/getInstructorList")
    Call<List<Instructor>> getInstructorList();
}
