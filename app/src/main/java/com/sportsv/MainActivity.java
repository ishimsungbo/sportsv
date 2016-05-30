package com.sportsv;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.sportsv.common.PreSaveInfo;
import com.sportsv.vo.User;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private PreSaveInfo prefUtil;
    public static Activity mainActivity;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivity=this;

        getSupportActionBar().setTitle("메인액티비티");

        //뒤로가기 버튼
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //액션바 배경
        //getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.listhigh));
        //액션바 홈키
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.homeback);

        //apply ButterKnife
        ButterKnife.bind(this);


        prefUtil = new PreSaveInfo(mainActivity);
        user = prefUtil.getUserProfile();

        Log.d(TAG, "유저명 : " + user.getUsername());
        Log.d(TAG, "프리퍼런스 유저 이미지 경로 : " + user.getProfileimgurl());
        Log.d(TAG,"프리퍼런스 uid : " + user.getUid());

    }

    @OnClick(R.id.userjoin)
    public void kakaologin(){
        Intent login_intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(login_intent);
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

}
