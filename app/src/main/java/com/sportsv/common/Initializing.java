package com.sportsv.common;

import android.content.Context;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by sungbo on 2016-06-30.
 * 앱 실행시 초기값 셋팅
 * FCM 토큰 유효성검사 및 DB 저장
 */
public class Initializing {

    private static final String TAG = "Initializing";
    private Context context;



    public Initializing(Context context) {
        this.context = context;
    }

}
