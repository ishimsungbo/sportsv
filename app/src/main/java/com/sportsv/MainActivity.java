package com.sportsv;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.sportsv.common.Compare;
import com.sportsv.common.PrefUtil;
import com.sportsv.test.TestActivity;
import com.sportsv.vo.User;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private User user;
    private PrefUtil prefUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("메인액티비티");

        //뒤로가기 버튼
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //액션바 배경
        //getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.listhigh));
        //액션바 홈키
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.homeback);

        //apply ButterKnife
        ButterKnife.bind(this);

        prefUtil = new PrefUtil(this);

        user = prefUtil.getUser();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Intent intent = getIntent();
            String joinFlag = intent.getExtras().getString("join");
            Log.d(TAG,"회원가입 후 메인으로 옴 " + joinFlag);

        }else{
            Log.d(TAG,"초기 앱을 실행");
        }

        Log.d(TAG, "onCreate ===========================================================");
    }

    @OnClick(R.id.btn_move)
    public void btn_move(){

        if(Compare.isEmpty(user.getUseremail())){

            Log.d(TAG, "유저 정보가 없다");
            Intent login_intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(login_intent);
            overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);

        }else{

            Log.d(TAG,"유저 정보가 있다 : "+user.getUsername());
            Intent login_intent = new Intent(this,UserInfoActivity.class);
            startActivity(login_intent);
            overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);

        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart ===========================================================");
        user = prefUtil.getUser();
        Log.d(TAG,"onStart() : "+user.toString());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"OnPause ===========================================================");
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart ===========================================================");
    }

    //재개하다...
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume ===========================================================");

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Toast.makeText(getApplicationContext(),"앱을 종료합니다",Toast.LENGTH_SHORT).show();
        android.os.Process.killProcess(android.os.Process.myPid());
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    //기타테스트화면
    @OnClick(R.id.btn_module_test)
    public void btn_module_test(){

        Intent intent = new Intent(this,TestActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

}
