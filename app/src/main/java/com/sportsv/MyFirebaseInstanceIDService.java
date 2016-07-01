package com.sportsv;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.sportsv.dao.FcmTokenService;
import com.sportsv.dao.GoogleService;
import com.sportsv.dao.UserService;
import com.sportsv.dbnetwork.FcmTokenTRService;
import com.sportsv.retropit.ServiceGenerator;
import com.sportsv.vo.FcmToken;
import com.sportsv.vo.ServerResult;
import com.sportsv.vo.User;

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

        //서버키와 발신자 아이디
        //AIzaSyAq3dIJEdjsZvydXugP5uPOKnqeLm52UMA
        //819984969479
        //토큰값을 유저 가입 // 강사 로그인시 등록시 서버에 등록해준다
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        FcmTokenTRService service = new FcmTokenTRService();
        service.refreshToken(refreshedToken,0,0);
        Log.d(TAG,"Reresh token = " + refreshedToken);
    }

}
