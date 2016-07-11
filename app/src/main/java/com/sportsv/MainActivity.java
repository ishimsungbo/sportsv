package com.sportsv;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.sportsv.common.Auth;
import com.sportsv.common.Common;
import com.sportsv.common.Compare;
import com.sportsv.common.PrefUtil;
import com.sportsv.dbnetwork.FcmTokenTRService;
import com.sportsv.test.TestActivity;
import com.sportsv.vo.FcmToken;
import com.sportsv.vo.Instructor;
import com.sportsv.vo.User;

import java.security.MessageDigest;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private User user;
    private Instructor instructor;
    private PrefUtil prefUtil;

    private String hasykey=null;

    @Bind(R.id.btn_move)
    Button button;


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

        Log.d(TAG, "onCreate ===========================================================");
        FirebaseMessaging.getInstance().subscribeToTopic("news");

        getAppKeyHash();
        Log.d(TAG,"해쉬키는 : " + hasykey);
    }

    @OnClick(R.id.btn_move)
    public void btn_move(){

        if(Compare.isEmpty(user.getUseremail()) && Compare.isEmpty(instructor.getEmail())
                ) {
            Log.d(TAG,"가입 페이지로 이동합니다");
            Intent login_intent = new Intent(getApplicationContext(), AppJoinActivity.class);
            startActivity(login_intent);
        }else{

            if(!Compare.isEmpty(user.getUseremail())){
                Log.d(TAG,"유저 입니다.");
                Intent login_intent = new Intent(this,UserInfoActivity.class);
                startActivity(login_intent);
            }

            if(!Compare.isEmpty(instructor.getEmail())){
                Log.d(TAG,"강사 입니다");
                Intent login_intent = new Intent(this,InsInfoActivity.class);
                startActivity(login_intent);
            }

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

        Log.d(TAG,"토큰 값은 : " + shToken);

        if(Compare.isEmpty(shToken)){
            //토큰이 없다면 프리퍼런스/DB에 저장한다
            Log.d(TAG,"토큰이 없습니다");
            prefUtil.saveFcmToken(FirebaseInstanceId.getInstance().getToken());
        }else{
            if(!shToken.equals(FirebaseInstanceId.getInstance().getToken())){
                Log.d(TAG,"토큰이 값이 다르기 때문에 업데이트를 합니다");
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


        if(!Compare.isEmpty(user.getUseremail())){
            button.setText("유저정보 및 설정");
            Auth.accountName = user.getGoogleemail();
        }

        if(!Compare.isEmpty(instructor.getEmail())){
            button.setText("강사정보 및 설정");
            Auth.accountName = instructor.getEmail();
        }

        if(Compare.isEmpty(user.getUseremail()) && Compare.isEmpty(instructor.getEmail())){
            button.setText("로그인 및 회원 가입");
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

    private void getAppKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                Log.d("Hash key", something);
                hasykey=something;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("name not found", e.toString());
        }
    }


}
