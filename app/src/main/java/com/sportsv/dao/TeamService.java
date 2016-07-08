package com.sportsv.dao;

import com.sportsv.vo.Team;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by sungbo on 2016-07-07.
 */
public interface TeamService {

    //팀 이미지 및 생성 관련, 업데이트는 서버에서 처리
    @Multipart
    @POST("/api/team/saveteam")
    Call<Team> saveTeam(
            @Part("insid") RequestBody insid,
            @Part("teamname") RequestBody teamName,
            @Part("teamdisp") RequestBody teamDisp,
            @Part("filename") RequestBody filename,
            @Part("profileimgurl") RequestBody profileimgurl,
            @Part("trFlag") RequestBody transactionFlag,
            @Part MultipartBody.Part file);
}
