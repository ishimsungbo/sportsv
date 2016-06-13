package com.sportsv.dao;

import com.sportsv.vo.CpBalanceHeader;
import com.sportsv.vo.SpBalanceHeader;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by sungbo on 2016-06-11.
 */
public interface PointService {

    @GET("api/user/selfpointamount")
    Call<SpBalanceHeader> getSelfAmt(@Query("uid") int uid);

    @GET("api/user/cashpointamount")
    Call<CpBalanceHeader> getCashAmt(@Query("uid") int uid);
}
