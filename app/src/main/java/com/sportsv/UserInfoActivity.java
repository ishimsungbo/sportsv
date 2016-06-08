package com.sportsv;

import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.sportsv.common.PrefUtil;
import com.sportsv.common.SettingActivity;
import com.sportsv.vo.User;
import com.sportsv.widget.VeteranToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sungbo on 2016-06-03.
 */
public class UserInfoActivity extends AppCompatActivity {

    private static final String TAG = "UserInfoActivity";
    private PrefUtil prefUtil;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinfo_layout);

        getSupportActionBar().setTitle("유저정보");

        //뒤로가기 버튼
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //액션바 배경
        //getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.listhigh));
        //액션바 홈키
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.homeback);
        //apply ButterKnife
        ButterKnife.bind(this);

        prefUtil = new PrefUtil(this);
        user = prefUtil.getUser();
    }


    @OnClick(R.id.logout)
    public void logout(){

        Log.d(TAG, "SNS 타입은 : " + user.getSnstype());

        if(user.getSnstype().equals("facebook")){
            Log.d(TAG,"페이스북 회원가입 유저 로그아웃을 합니다");

            //페이스북 토큰 및 세션 끈기
            FacebookSdk.sdkInitialize(getApplicationContext());
            LoginManager.getInstance().logOut();

        }else if(user.getSnstype().equals("kakao")){
            Log.d(TAG,"카카오 회원가입 유저 로그아웃을 합니다");
            onClickLogout();
        }else if(user.getSnstype().equals("app")){
            Log.d(TAG,"자체 회원가입 유저 로그아웃을 합니다");
        }

        prefUtil.clearPrefereance();

        VeteranToast.makeToast(getApplicationContext(), "로그아웃 합니다", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    //카카오 세션 끈기 메소드
    private void onClickLogout() {
        UserManagement.requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //홈버튼클릭시
        if (id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState ===========================================================");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume ===========================================================");
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop ===========================================================");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy ===========================================================");
    }

    @OnClick(R.id.setting)
    public void setting(){
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

}
