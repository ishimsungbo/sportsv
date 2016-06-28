package com.sportsv.dao;

import com.sportsv.vo.Instructor;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by sungbo on 2016-06-27.
 */
public interface InstructorService {

    @POST("/ins/instructor/getInstrutor")
    Call<Instructor> getInstrutor(@Body Instructor ins);

}
