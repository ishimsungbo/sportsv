package com.sportsv.dao;

import com.sportsv.vo.User;
import com.sportsv.vo.UserMission;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by sungbo on 2016-06-13.
 */
public interface UserMissionService {

    @POST("/api/usermission/insert")
    Call<Integer> createUserMission(@Body UserMission userMission);

}
