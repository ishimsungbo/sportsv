package com.sportsv;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;
import com.sportsv.common.Common;
import com.sportsv.dao.UserService;
import com.sportsv.common.PreSaveInfo;
import com.sportsv.vo.User;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class LoginActivity extends AppCompatActivity {

    //회원가입추가 정보를 받아온다.
    final static int USER_INFO_RESULT = 0;

    private PreSaveInfo preSaveInfo;
    private User users;

    private SessionCallback kakaoCallback;
    private static final String TAG = "LoginActivity";

    @Bind(R.id.kakaologin)
    Button kakaologin;

    @Bind(R.id.connectout)
    Button connectout;


    //@Bind(R.id.iv_user_profile)
    public static ImageView userImage;

    private int existCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_login_layout);


        getSupportActionBar().setTitle("로그인액티비티");

        //뒤로가기 버튼
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //액션바 배경
        //getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.listhigh));
        //액션바 홈키
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.homeback);
        //apply ButterKnife
        ButterKnife.bind(this);

        preSaveInfo = new PreSaveInfo(this);

        users = preSaveInfo.getUserProfile();

        if(users.getUsername()!=null){
            Log.d(TAG,"계정있음");
            kakaologin.setVisibility(View.INVISIBLE);
            connectout.setVisibility(View.VISIBLE);
        }else if(users.getUsername()==null){
            Log.d(TAG,"계정없음");
            kakaologin.setVisibility(View.VISIBLE);
            connectout.setVisibility(View.INVISIBLE);
        }

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

    public class SessionCallback implements ISessionCallback {
        @Override
        public void onSessionOpened() {
            getApplicationContext();
            kakaoMe();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if(exception != null) {
                Logger.e(exception);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(kakaoCallback);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }

        Log.d(TAG, "다른 곳에서 다시 왔습니다 : " + requestCode);

        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case USER_INFO_RESULT:
                if(resultCode == RESULT_OK){
                    Log.d(TAG, "회원가입 클릭 후 돌아왔습니다 : " + requestCode + " : " + RESULT_OK);
                }else if(resultCode == RESULT_CANCELED){
                    Log.d(TAG, "회원가입 취소 클릭 후 돌아왔습니다 : " + requestCode + " : " + RESULT_CANCELED);
                }
                break;
        }

    }


    @OnClick(R.id.kakaologin)
    public void kakaologin(){

        //카카오세션 오픈
        kakaoCallback = new SessionCallback();
        Session.getCurrentSession().addCallback(kakaoCallback);
        Session.getCurrentSession().checkAndImplicitOpen();
        Session.getCurrentSession().open(AuthType.KAKAO_TALK_EXCLUDE_NATIVE_LOGIN, LoginActivity.this);

        //세션 및 상태 확인
        //String s = Session.getCurrentSession().getAccessToken();
        //Toast.makeText(getApplicationContext(), "카카오 토큰 : " + s, Toast.LENGTH_SHORT).show();
        //if(Session.getCurrentSession().hasValidAccessToken()){
        //    Toast.makeText(getApplicationContext(), "유효한 토큰", Toast.LENGTH_SHORT).show();
        //}

        Log.d(TAG,"KAKAO 로그인을 버튼을 클릭하고 있습니다.");
    }

    protected void kakaoMe() {
        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                int ErrorCode = errorResult.getErrorCode();
                int ClientErrorCode = -777;

                if (ErrorCode == ClientErrorCode) {
                    Log.d("KAKAO_LOG", "서버 네트워크에 문제가 있습니다");
                } else {
                    Log.d("KAKAO_LOG", "오류로 카카오로그인 실패");
                }
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.d("TAG", "오류로 카카오로그인 실패 ");
            }

            @Override
            public void onSuccess(UserProfile userProfile) {


                Intent intent = new Intent(getApplicationContext(), LoginInfoActivity.class);
                startActivity(intent);

                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);

            }

            @Override
            public void onNotSignedUp() {

            }
        });
    }

    public static class RetrofitServiceImplFactory {
        private static Retrofit getretrofit(){
            return new Retrofit.Builder()
                    .baseUrl(Common.SERVER_ADRESS)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }

        public static UserService userService(){
            return getretrofit().create(UserService.class);
        }

    }


    public int userExist(String email){

        final ProgressDialog dialog;

        dialog = ProgressDialog.show(this, "서버와 통신", "회원검증을 진행합니다", true);
        dialog.show();

        final Call<Integer> userExist = RetrofitServiceImplFactory.userService().existUser(email);
        userExist.enqueue(new Callback<Integer>() {

            @Override
            public void onResponse(Response<Integer> response, Retrofit retrofit) {
                try{
                    //uid 시퀀스가 디비에 생성됨
                    existCount = response.body();
                    Log.d(TAG, "서버에서 생성된 아이디는 : " + existCount);

                }catch (Exception e){
                    existCount = 2;
                    Log.d(TAG, "서버와 통신중 오류 발생 :" +existCount);

                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(TAG,"환경 구성 확인 서버와 통신 불가");
                dialog.dismiss();
                existCount = 2;
            }
        });

        return existCount;
    }

    @OnClick(R.id.getusercheck)
    public void getusercheck(){


        AccountManager manager = AccountManager.get(this);
        Account[] accounts =  manager.getAccounts();
        final int count = accounts.length;
        Account account = null;

        for(int i=0;i<count;i++) {
            account = accounts[i];
            Log.d("HSGIL", "Account - name: " + account.name + ", type :" + account.type);

            if(account.type.equals("com.google")){		//이러면 구글 계정 구분 가능
                Log.d(TAG, "Account - name: " + account.name + ", type :" + account.type);
            }
        }

        Log.d(TAG,"1 : "+users.getUid());
        Log.d(TAG,"2 : "+users.getUsername());
        Log.d(TAG,"3 : "+users.getPhone());
        Log.d(TAG,"4 : "+users.getUseremail());

    }

    @OnClick(R.id.connectout)
    public void connectout(){

        Log.d(TAG,"로그아웃 호출" + users.getSnstype());
        if(users.getSnstype().equals("kakao")){
            Log.d(TAG,"카카오 로그 아웃을 합니다.");
            onClickLogout();
        }else if(users.getSnstype().equals("facebook")){
            Log.d(TAG,"페이스북 로그 아웃을 합니다.");
        }else if(users.getSnstype().equals("sportsv")){
            Log.d(TAG,"SportsV 로그 아웃을 합니다.");
        }
    }

    private void onClickLogout() {
        UserManagement.requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
                Log.d(TAG, "카카오 세션을 끈습니다");
                preSaveInfo.clearStore();
                Log.d(TAG, "메인페이지로 이동합니다");
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
    }

}
