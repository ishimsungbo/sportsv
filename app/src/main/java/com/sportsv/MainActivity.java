package com.sportsv;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.sportsv.common.Common;
import com.sportsv.common.Compare;
import com.sportsv.common.PrefManager;
import com.sportsv.common.PrefUtil;
import com.sportsv.dbnetwork.FcmTokenTRService;
import com.sportsv.test.TestActivity;
import com.sportsv.vo.FcmToken;
import com.sportsv.vo.Instructor;
import com.sportsv.vo.User;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private User user;
    private Instructor instructor;
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
        instructor = prefUtil.getIns();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Intent intent = getIntent();
            String joinFlag = intent.getExtras().getString("join");
            Log.d(TAG,"회원가입 후 메인으로 옴 " + joinFlag);

        }else{
            Log.d(TAG,"초기 앱을 실행");
        }

        Log.d(TAG, "onCreate ===========================================================");
        FirebaseMessaging.getInstance().subscribeToTopic("news");


        //초기슬라이딩 화면
        findViewById(R.id.btn_play_again).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // We normally won't show the welcome slider again in real app
                // but this is for testing
                PrefManager prefManager = new PrefManager(getApplicationContext());

                // make first time launch TRUE
                prefManager.setFirstTimeLaunch(true);

                startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
                finish();
            }
        });

        //브로드 캐스트 수신 받는 함수 동적으로...
        //LocalBroadcastManager.getInstance(this).registerReceiver(tokenReceiver, new IntentFilter("tokenReceiver"));
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
        instructor = prefUtil.getIns();

        Log.d(TAG,"onStart() : "+user.toString());
        Log.d(TAG,"onStart() : "+instructor.toString());

        String shToken = prefUtil.getFcmToken();

        if(Compare.isEmpty(shToken)){
            //토큰이 없다면 프리퍼런스/DB에 저장한다
            prefUtil.saveFcmToken(FirebaseInstanceId.getInstance().getToken());
        }else{
            if(!shToken.equals(FirebaseInstanceId.getInstance().getToken())){

                //디비의 토큰을 일괄로 업데이트 한다
                FcmToken token = new FcmToken();
                token.setSerialnumber(Common.getDeviceSerialNumber());
                token.setFcmtoken(shToken);
                FcmTokenTRService service = new FcmTokenTRService(this);
                service.updateToken(token);

                //쉐어프리퍼런스 토큰값을 변경
                prefUtil.saveFcmToken(FirebaseInstanceId.getInstance().getToken());
            }

        }
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

    @OnClick(R.id.btn_getToken)
    public void btn_getToken(){
        Log.d(TAG, "InstanceID token: " + FirebaseInstanceId.getInstance().getToken());
        Log.d(TAG, "기기고유 아이디 " + Common.getDeviceSerialNumber());
    }

    //팝업 뛰우기
    @OnClick(R.id.btn_pop)
    public void btn_pop(){

    }

    //FCM관련 토크값 처리 로직
    //1. 서버에 토큰값이 있는지 검색
    //2. 토큰 값이 없다면 최초 실행이므로 DB저장
    //   --만약 user정보가 있다면 토큰값을 현재 토큰으로 변경을 해준다.
    //

    //FCM 토큰 관련 처리
/*    BroadcastReceiver tokenReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String token = intent.getStringExtra("token");
            if(token != null)
            {
                //send token to your server or what you want to do
                Log.d(TAG,"**************************************************************");
                Log.d(TAG,"토큰 값은 : " + token);
                Log.d(TAG,"**************************************************************");

                FcmToken fcmToken = new FcmToken();
                fcmToken.setSerialnumber(Common.getDeviceSerialNumber());
                fcmToken.setFcmtoken(token);
                fcmToken.setUid(user.getUid());
                fcmToken.setCommontokenid(user.getCommontokenid());

                FcmTokenService fcmTokenService = ServiceGenerator.createService(FcmTokenService.class);
                final Call<ServerResult> call = fcmTokenService.saveToken(fcmToken);

                call.enqueue(new Callback<ServerResult>() {
                    @Override
                    public void onResponse(Call<ServerResult> call, Response<ServerResult> response) {
                        ServerResult serverResult = response.body();
                        Log.d(TAG,"정상정으로 생성되었습니다." + serverResult.toString());
                    }

                    @Override
                    public void onFailure(Call<ServerResult> call, Throwable t) {
                        t.printStackTrace();
                    }
                });

            }

        }
    };
    */

}
