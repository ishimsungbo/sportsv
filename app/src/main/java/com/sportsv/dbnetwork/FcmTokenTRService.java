package com.sportsv.dbnetwork;

import android.util.Log;

import com.sportsv.dao.FcmTokenService;
import com.sportsv.retropit.ServiceGenerator;
import com.sportsv.vo.FcmToken;
import com.sportsv.vo.ServerResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sungbo on 2016-07-01.
 */
public class FcmTokenTRService {

    private static final String TAG = "FcmTokenTRService";
    private ServerResult result = new ServerResult();


    //서버코딩으로 제외
    public ServerResult updateToken(String token,int uid,int instructorid){

        FcmToken fcmToken = new FcmToken();
        fcmToken.setFcmtoken(token);

        FcmTokenService fcmTokenService = ServiceGenerator.createService(FcmTokenService.class);
        final Call<ServerResult> callBack = fcmTokenService.updateFcmToken(fcmToken);

        callBack.enqueue(new Callback<ServerResult>() {


            @Override
            public void onResponse(Call<ServerResult> call, Response<ServerResult> response) {
                result = response.body();
            }

            @Override
            public void onFailure(Call<ServerResult> call, Throwable t) {
                Log.d(TAG, "환경 구성 확인 필요 서버와 통신 불가 updateToken : " + t.getMessage());
                result.setCount(0);
                result.setResult("E");
                result.setErrorMsg(t.getMessage());
                t.printStackTrace();
            }
        });


        return result;
    }

    public void refreshToken(String token,int uid,int instructorid){

        FcmToken fcmToken = new FcmToken();
        fcmToken.setFcmtoken(token);

        if(uid!=0){
            fcmToken.setUid(uid);
        }

        if(instructorid!=0){
            fcmToken.setInstructorid(instructorid);
        }

        FcmTokenService fcmTokenService = ServiceGenerator.createService(FcmTokenService.class);
        final Call<ServerResult> callBack = fcmTokenService.saveFcmToken(fcmToken);

        callBack.enqueue(new Callback<ServerResult>() {
            @Override
            public void onResponse(Call<ServerResult> call, Response<ServerResult> response) {
                ServerResult result = response.body();
                Log.d(TAG, " server return value : " + result.toString());
            }

            @Override
            public void onFailure(Call<ServerResult> call, Throwable t) {
                Log.d(TAG, "환경 구성 확인 필요 서버와 통신 불가 refreshToken : " + t.getMessage());
                t.printStackTrace();
            }
        });
    }

    public int getTokenCount(String token){

        Log.d(TAG, "토큰 값을 구하는 쿼리를 실행 합니다");

        FcmTokenService fcmTokenService  = ServiceGenerator.createService(FcmTokenService.class);

        FcmToken fcmToken = new FcmToken();
        fcmToken.setFcmtoken(token);

        final Call<ServerResult> callBack = fcmTokenService.getFcmTokenCount(fcmToken);
        callBack.enqueue(new Callback<ServerResult>() {
            @Override
            public void onResponse(Call<ServerResult> call, Response<ServerResult> response) {
                result = response.body();
                Log.d(TAG,"토큰값은 : " + result.toString());
            }

            @Override
            public void onFailure(Call<ServerResult> call, Throwable t) {
                Log.d(TAG, "환경 구성 확인 필요 서버와 통신 불가 getTokenCount : " + t.getMessage());
                result.setCount(0);
                result.setResult("E");
                result.setErrorMsg(t.getMessage());
                t.printStackTrace();
            }
        });

        return result.getCount();
    }

}
