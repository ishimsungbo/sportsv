package com.sportsv.dbnetwork;

import android.app.ProgressDialog;
import android.content.Context;

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
    private Context context;
    private ServerResult result = new ServerResult();

    public FcmTokenTRService(Context context) {
        this.context = context;
    }

    public void updateToken(FcmToken token){

        final ProgressDialog dialog;

        dialog = ProgressDialog.show(context, "", "서버와 데이터 통신 중입니다(푸쉬토큰_업데이트)", true);
        dialog.show();

        FcmTokenService fcmTokenService = ServiceGenerator.createService(FcmTokenService.class);

        final Call<ServerResult> call = fcmTokenService.updateToken(token);

        call.enqueue(new Callback<ServerResult>() {
            @Override
            public void onResponse(Call<ServerResult> call, Response<ServerResult> response) {
                ServerResult result = response.body();
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<ServerResult> call, Throwable t) {
                dialog.dismiss();
                t.printStackTrace();
            }
        });
    }

    public void saveToken(FcmToken token){
        //비회원일때 토큰 로직 비회원도 푸쉬를 받는다 전체 공지 일경우
        final ProgressDialog dialog;

        dialog = ProgressDialog.show(context, "", "서버와 데이터 통신 중입니다(푸쉬토큰_생성)", true);
        dialog.show();

        FcmTokenService fcmTokenService = ServiceGenerator.createService(FcmTokenService.class);

        final Call<ServerResult> call = fcmTokenService.saveToken(token);

        call.enqueue(new Callback<ServerResult>() {
            @Override
            public void onResponse(Call<ServerResult> call, Response<ServerResult> response) {
                ServerResult result = response.body();
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<ServerResult> call, Throwable t) {
                dialog.dismiss();
                t.printStackTrace();
            }
        });
    }

}
