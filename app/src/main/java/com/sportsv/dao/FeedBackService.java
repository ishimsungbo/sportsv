package com.sportsv.dao;

import com.sportsv.vo.FeedbackHeader;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by sungbo on 2016-06-20.
 */
public interface FeedBackService {

    @POST("/api/feed/getfeedheader")
    Call<List<FeedbackHeader>> getFeedHeaderList(@Body FeedbackHeader header);

}
