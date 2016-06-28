package com.sportsv.dao;


import com.sportsv.vo.FeedbackHeader;
import com.sportsv.vo.ServerResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


/**
 * Created by sungbo on 2016-06-20.
 */
public interface FeedBackService {

    @POST("/api/feed/feedheaderlist")
    Call<List<FeedbackHeader>> getFeedHeaderList(@Body FeedbackHeader header);

    @POST("/api/feed/feedheader")
    Call<FeedbackHeader> getFeedHeader(@Body FeedbackHeader header);

    @POST("/api/feed/feedheaderCount")
    Call<ServerResult> getFeedHeaderCount(@Body FeedbackHeader header);
}
