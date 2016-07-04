package com.sportsv;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;
import com.sportsv.common.PrefUtil;
import com.sportsv.dao.UserService;
import com.sportsv.retropit.ServiceGenerator;
import com.sportsv.vo.User;

import org.json.JSONObject;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    //회원가입추가 정보를 받아온다.
    final static int FACEBOOK_RESULT=64206; //페이스북

    private User SERVERUSER;
    private PrefUtil prefUtil;

    //kakao callback
    private SessionCallback kakaoCallback;
    //facebook calback
    private CallbackManager callbackManager;


    @Bind(R.id.kakaologin)
    Button kakaologin;

    @Bind(R.id.facebooklogin)
    Button facebooklogin;

    //회원가입 상세로 넘길 변수들
    private String username;
    private String snsname;
    private String snsid;
    private String useremail;
    private String snstype;
    private String profileImgUrl;

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

        prefUtil = new PrefUtil(this);
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

    @OnClick(R.id.kakaologin)
    public void kakaologin(){

        //카카오세션 오픈
        kakaoCallback = new SessionCallback();
        Session.getCurrentSession().addCallback(kakaoCallback);

        if(!Session.getCurrentSession().checkAndImplicitOpen()){
            Session.getCurrentSession().open(AuthType.KAKAO_TALK_EXCLUDE_NATIVE_LOGIN, LoginActivity.this);
        }

        Log.d(TAG, "카카오 로그인 시도");
    }

    public class SessionCallback implements ISessionCallback {


        @Override
        public void onSessionOpened() {
            Log.d(TAG, "카카오 SessionCallback");
            kakaoMeCallbackInfo();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if(exception != null) {
                Logger.e(exception);
            }
            Log.d(TAG,"카카오 로그인 취소를 했습니다.");
        }
    }

    //페이스북 로그인 처리
    @OnClick(R.id.facebooklogin)
    public void facebooklogin(){
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                error.printStackTrace();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d(TAG, "---------------------onActivityResult---------------------");

        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        callbackManager.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case FACEBOOK_RESULT:
                Log.d(TAG, "페이스북 로그인 처리 onActivityResult()");
                facebookMeCallbackInfo();
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    public void facebookMeCallbackInfo(){

        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                try{
                    Log.d(TAG, "페이스북 회원 정보가져오기");

                    snstype ="facebook";
                    username = object.getString("name");
                    snsname  = object.getString("name");
                    useremail = object.getString("email");
                    snsid = object.getString("id");
                    profileImgUrl = "https://graph.facebook.com/" + snsid + "/picture?type=large";

                    getUserInfo(snstype, snsid, "", "");

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender");
        request.setParameters(parameters);
        request.executeAsync();
    }

    protected void kakaoMeCallbackInfo() {
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

                Log.d(TAG, "카카오 회원 정보가져오기");

                snstype = "kakao";
                username = userProfile.getNickname();
                snsname = userProfile.getNickname();
                //useremail = 카카오는 정책상 메일을 제공하지 않는다
                snsid = String.valueOf(userProfile.getId());
                profileImgUrl = userProfile.getProfileImagePath();

                getUserInfo(snstype, snsid,"","");
            }

            @Override
            public void onNotSignedUp() {

            }
        });
    }

    public void getUserInfo(
                            String type, //snstype
                            String id,  //snsid
                            String pw,  //password
                            String email //useremail
    ){

        final ProgressDialog dialog;
        dialog = ProgressDialog.show(this, "서버와 통신", "회원검증을 진행합니다", true);
        dialog.show();

        UserService userService = ServiceGenerator.createService(UserService.class);
        final Call<User> getUserInfo = userService.getUser(type, id, pw, email);


        getUserInfo.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (response.isSuccessful()) {
                    Log.d(TAG, "서버 조회 결과 성공");

                    SERVERUSER = response.body();

                    if (SERVERUSER.getUseremail().equals("null@co.com")) {

                        Log.d(TAG, "상세회원 가입 정보 페이지로 넘어 갑니다");

                        Intent intent = new Intent(getApplicationContext(), LoginInfoActivity.class);

                        intent.putExtra("snstype", snstype);
                        intent.putExtra("username", username);
                        intent.putExtra("snsname", snsname);
                        intent.putExtra("useremail", useremail);
                        intent.putExtra("snsid", snsid);
                        intent.putExtra("profileImgUrl", profileImgUrl);
                        startActivity(intent);
                        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);

                    } else {
                        userExist();
                    }

                } else {
                    Log.d(TAG, "조회 결과 실패 ===");

                }

                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, "환경 구성 확인 서버와 통신 불가" + t.getMessage());
                t.printStackTrace();
                dialog.dismiss();
            }
        });

    }

    public void userExist(){

        prefUtil.saveUser(SERVERUSER);
        Log.d("User Info", "************************************************");
        Log.d("User Info", "SNS TYPE : " + SERVERUSER.getSnstype());
        Log.d("User Info", "UID : " + SERVERUSER.getUid());
        Log.d("User Info", "NAME : " + SERVERUSER.getUsername());
        Log.d("User Info", "EMAIL : " + SERVERUSER.getUseremail());
        Log.d("User Info", "GOOGLE ID : " + SERVERUSER.getGoogleemail());
        Log.d("User Info", "************************************************");

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("join_y", "join_flag");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
        finish();
    }

    @OnClick(R.id.btn_join)
    public void btn_join(){
        Log.d(TAG, "상세회원 가입 정보 페이지로 넘어 갑니다");

        Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

    @OnClick(R.id.btn_login)
    public void btn_login(){
        Log.d(TAG, "로그인 페이지로 이동");

        Intent intent = new Intent(getApplicationContext(), AppLoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

    /*
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
    }
     */

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() ========================================");
        Session.getCurrentSession().removeCallback(kakaoCallback);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() ========================================");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() ========================================");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() ========================================");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(kakaoCallback);
    }

}
