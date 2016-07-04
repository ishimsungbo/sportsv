package com.sportsv;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.sportsv.common.Common;
import com.sportsv.dao.FcmTokenService;
import com.sportsv.dbnetwork.FcmTokenTRService;
import com.sportsv.retropit.ServiceGenerator;
import com.sportsv.vo.FcmToken;
import com.sportsv.vo.ServerResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sungbo on 2016-06-29.
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "InstanceIDService";

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG,"Reresh token = " + refreshedToken);

        //디비 저장
        FcmTokenTRService service = new FcmTokenTRService(this);
        FcmToken token = new FcmToken();
        token.setSerialnumber(Common.getDeviceSerialNumber());
        token.setFcmtoken(FirebaseInstanceId.getInstance().getToken());
        token.setUid(0);
        token.setCommontokenid(0);

        FcmTokenService fcmTokenService = ServiceGenerator.createService(FcmTokenService.class);

        final Call<ServerResult> call = fcmTokenService.saveToken(token);

        call.enqueue(new Callback<ServerResult>() {
            @Override
            public void onResponse(Call<ServerResult> call, Response<ServerResult> response) {
                ServerResult result = response.body();
            }
            @Override
            public void onFailure(Call<ServerResult> call, Throwable t) {
                t.printStackTrace();
            }
        });

    /*
        final Intent intent = new Intent("tokenReceiver");
        final LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        intent.putExtra("token",refreshedToken);
        broadcastManager.sendBroadcast(intent);
    */
    }
}
