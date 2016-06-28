package com.sportsv.dao;

import com.sportsv.vo.CpBalanceHeader;
import com.sportsv.vo.ServerResult;
import com.sportsv.vo.SpBalanceHeader;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Created by sungbo on 2016-06-11.
 */
public interface PointService {

    @GET("/api/user/selfpointamount")
    Call<SpBalanceHeader> getSelfAmt(@Query("uid") int uid);

    @GET("/api/user/cashpointamount")
    Call<CpBalanceHeader> getCashAmt(@Query("uid") int uid);

    //출석체크
    @GET("/api/user/userdailycheck")
    Call<ServerResult> daliyCheck(
            @Query("uid") String uid,
            @Query("pointtype") String pointType,
            @Query("lang") String lang
    );
}
