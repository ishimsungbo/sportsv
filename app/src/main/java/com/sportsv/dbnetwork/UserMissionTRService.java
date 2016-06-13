package com.sportsv.dbnetwork;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.sportsv.serverservice.RetrofitService;
import com.sportsv.vo.UserMission;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by sungbo on 2016-06-13.
 */
public class UserMissionTRService {

    private String TAG;
    private int usermissionid = 0 ;
    //유저미션을 생성할때 사용하는 생성자


    public void createUserMission(UserMission userMission){

        final Call<Integer> createMission = RetrofitService.userMissionService().createUserMission(userMission);

        createMission.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Response<Integer> response, Retrofit retrofit) {
                usermissionid = response.body();
                Log.d(TAG, "서버에서 생성된 유저미션아이디는  : " + usermissionid );
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(TAG, "환경 구성 확인 필요 서버와 통신 불가 : " + t.getMessage());
                t.printStackTrace();
                usermissionid = -1;
            }
        });

    }
}
