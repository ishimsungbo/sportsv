package com.sportsv;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.sportsv.common.Common;
import com.sportsv.common.Compare;
import com.sportsv.common.PrefUtil;
import com.sportsv.dao.UserService;
import com.sportsv.retropit.ServiceGenerator;
import com.sportsv.vo.ServerResult;
import com.sportsv.vo.User;
import com.sportsv.widget.VeteranToast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by sungbo on 2016-06-06.
 */
public class AppLoginActivity extends AppCompatActivity {

    private static final String TAG = "AppLoginActivity";

    TextInputLayout layoutUserEmail;
    TextInputLayout layoutPassword;

    @Bind(R.id.edit_user_email_lg)
    EditText edit_email_lg;

    @Bind(R.id.edit_user_password_lg)
    EditText edit_password_lg;

    private User parameterUser;
    private User SERVERUSER;

    private PrefUtil prefUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_app_login_layout);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        layoutUserEmail = (TextInputLayout) findViewById(R.id.layout_user_email_lg);
        layoutPassword  = (TextInputLayout) findViewById(R.id.layout_user_password_lg);

        ButterKnife.bind(this);

        prefUtil = new PrefUtil(this);

        //아래 객체로 유저정보를 조회 한다
        parameterUser = new User();
        parameterUser.setSnstype("app");
        parameterUser.setSnsid(Common.VETERAN_SNSID);

        SERVERUSER = new User();
    }

    //로그인 버튼
    @OnClick(R.id.lg_btn_join)
    public void lg_btn_join(){

        if(!Compare.checkEmail(edit_email_lg.getText().toString())) {
            layoutUserEmail.setError("이메일 형식이 맞지 않습니다");
            return;
        }else if (!Compare.validatePassword(edit_password_lg.getText().toString())) {
            layoutPassword.setError("비밀번호는 5자 이상 입력해주세요");
            return;
        }else{
            layoutUserEmail.setError(null);
            layoutPassword.setError(null);

            parameterUser.setPassword(edit_password_lg.getText().toString());
            parameterUser.setUseremail(edit_email_lg.getText().toString());

            //서버에서 아이디와 비번 검증
            userLogin();

        }
    }

    public void userLogin(){

        final ProgressDialog dialog;
        dialog = ProgressDialog.show(this, "서버와 통신", "회원검증을 진행합니다", true);
        dialog.show();


        UserService userService = ServiceGenerator.createService(UserService.class,parameterUser);

        final Call<ServerResult> getUserInfo = userService.getUserCheck(parameterUser.getSnstype(),parameterUser.getUseremail());


        getUserInfo.enqueue(new Callback<ServerResult>() {
            @Override
            public void onResponse(Call<ServerResult> call, Response<ServerResult> response) {

                Log.d(TAG, "서버 조회 결과 성공");

                ServerResult serverResult = response.body();

                Log.d(TAG, "서버 조회 결과 값은 : " + serverResult.getResult());

                dialog.dismiss();

                if(serverResult.getCount()==1){

                    getUserSet();

                }else{
                    VeteranToast.makeToast(getApplicationContext(),"입력하신 계정이 존재하지 않습니다",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResult> call, Throwable t) {
                Log.d(TAG, "환경 구성 확인 필요 서버와 통신 불가" + t.getMessage());
                t.printStackTrace();
                dialog.dismiss();
            }
        });

    }

    public void userExist(){

        prefUtil.saveUser(SERVERUSER);
        Log.d("User Info", "************************************************");
        Log.d("User Info", "SNS TYPE : " + SERVERUSER.getSnstype());
        Log.d("User Info", "EMAIL : " + SERVERUSER.getUseremail());
        Log.d("User Info", "PW : " + SERVERUSER.getPassword());
        Log.d("User Info", "************************************************");

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();

    }

    public void getUserSet(){

        final ProgressDialog dialog;
        dialog = ProgressDialog.show(this, "서버와 통신", "회원정보를 서버에서 가져옵니다", true);
        dialog.show();

        UserService userService = ServiceGenerator.createService(UserService.class);
        final Call<User> getUserInfo = userService.getUser(parameterUser.getSnstype(),
                                                                             parameterUser.getSnsid(),
                                                                             parameterUser.getPassword(),
                                                                             parameterUser.getUseremail());
        getUserInfo.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                SERVERUSER = response.body();

                SERVERUSER.setPassword(parameterUser.getPassword());

                dialog.dismiss();

                Log.d(TAG, "2.유저 정보를 가져 왔습니다");

                userExist();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, "환경 구성 확인 필요 서버와 통신 불가" + t.getMessage());

                t.printStackTrace();
                dialog.dismiss();
            }
        });
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

        if(SERVERUSER.getUid() > 0){
            VeteranToast.makeToast(getApplicationContext(),"로그인 되었습니다",Toast.LENGTH_SHORT).show();
        }
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

}
