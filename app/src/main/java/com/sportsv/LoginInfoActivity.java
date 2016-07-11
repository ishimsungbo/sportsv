package com.sportsv;

import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.util.ExponentialBackOff;

import com.google.firebase.iid.FirebaseInstanceId;
import com.sportsv.common.Auth;

import com.sportsv.common.Common;
import com.sportsv.common.Compare;
import com.sportsv.common.PrefUtil;
import com.sportsv.dao.UserService;
import com.sportsv.retropit.ServiceGenerator;
import com.sportsv.vo.ServerResult;
import com.sportsv.vo.User;
import com.sportsv.widget.VeteranToast;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by sungbo on 2016-05-30.
 */
public class LoginInfoActivity extends AppCompatActivity {


    @Bind(R.id.tx_snsusername)
    TextView tx_snsusername;

    @Bind(R.id.iv_user_profile)
    ImageView userimageView;

    @Bind(R.id.edit_email)
    EditText edit_email;

    @Bind(R.id.googleid)
    TextView tx_googleid;

    private User userVo;
    private PrefUtil prefUtil;
    int UID;

    //구글 아이디 선택하기
    GoogleAccountCredential credential;
    private static final int REQUEST_ACCOUNT_PICKER = 2;
    private static final String TAG = "LoginInfoActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        getSupportActionBar().setTitle("회원가입 부가 정보입력");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.ac_login_addinfo_layout);

        userVo = new User();
        prefUtil = new PrefUtil(this);

        ButterKnife.bind(this);

        Intent intent = getIntent();

        initialSet(intent);

        credential = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(), Arrays.asList(Auth.SCOPES));

        credential.setBackOff(new ExponentialBackOff());
    }

    @OnClick(R.id.btn_join)
    public void btnJoin(){

        userVo.setUseremail(edit_email.getText().toString());

        if(!Compare.checkEmail(edit_email.getText().toString())){
            Toast.makeText(getApplicationContext(),"이메일 형식이 맞지 않습니다 : "+edit_email.getText().toString(),Toast.LENGTH_SHORT).show();
            return;
        }else{
            if(userVo.getGoogleemail()!=null){
                //서버에서 uid 생성하기
                userCreate(userVo);

                Log.d(TAG,"btnJoin() ===========================================================");

            }else{
                Toast.makeText(getApplicationContext(),"구글 계정은 필수 입니다",Toast.LENGTH_SHORT).show();
            }

        }
    }


    //유저정보 화면 뷰에 채워주기
    protected void widgetSet(String username,String email,String img,String phoneNum){

        //tx_phone.setText(phoneNum);
        tx_snsusername.setText(username);
        edit_email.setText(email);


        Glide.with(LoginInfoActivity.this)
                .load(img)
                .into(userimageView);
    }

    protected void initialSet(Intent intent){

        //TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        //String phoneNum = tm.getLine1Number();

        String snstype = intent.getExtras().getString("snstype");
        String username = intent.getExtras().getString("username");
        String snsname = intent.getExtras().getString("snsname");
        String useremail = intent.getExtras().getString("useremail");
        String snsid = intent.getExtras().getString("snsid");
        String profileImgUrl = intent.getExtras().getString("profileImgUrl");

        Log.d(TAG, "값 확인 " + snstype);

        widgetSet(username, useremail, profileImgUrl, "");

        userVo.setUsername(username);
        userVo.setSnsid(snsid);
        userVo.setSnsname(snsname);
        userVo.setUseremail(useremail);
        userVo.setProfileimgurl(profileImgUrl);
        userVo.setSnstype(snstype);
        userVo.setLocation(0);
        userVo.setPassword(snsid);
        userVo.setTeampushflag("Y");
        userVo.setApppushflag("Y");
        userVo.setSerialnumber(Common.getDeviceSerialNumber());
        userVo.setFcmToken(FirebaseInstanceId.getInstance().getToken());
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


    //서버통신 관련 코딩
    public void userCreate(final User user){

        final ProgressDialog dialog;

        dialog = ProgressDialog.show(this, "서버와 통신", "회원가입을 진행하고 있습니다", true);
        dialog.show();

        UserService userService = ServiceGenerator.createService(UserService.class);


        final Call<ServerResult> userCre = userService.createUser(user);

        userCre.enqueue(new Callback<ServerResult>() {

            @Override
            public void onResponse(Call<ServerResult> call, Response<ServerResult> response) {
                try {

                    ServerResult serverResult = response.body();

                    UID = serverResult.getCount();

                    Log.d(TAG, "서버에서 생성된 아이디는 : " + UID);

                    userVo.setUid(UID);
                    userVo.setCommontokenid(serverResult.getGetid());
                    prefUtil.saveUser(userVo);

                    dialog.dismiss();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("join_y", "join_flag");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();

                } catch (Exception e) {
                    Log.d(TAG, "서버와 통신중 오류 발생");
                    UID = 0;
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ServerResult> call, Throwable t) {
                Log.d(TAG, "환경 구성 확인 필요 서버와 통신 불가 : " + t.getMessage());
                t.printStackTrace();
                dialog.dismiss();
                UID = -1;
            }
        });

    }

    @OnClick(R.id.btn_cancel)
    public void btnCancel() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        finish();
    }

    @OnClick(R.id.btn_google)
    public void btn_google(){
        startActivityForResult(credential.newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult ===========================================================");

        switch (requestCode) {
            case REQUEST_ACCOUNT_PICKER:
                if (resultCode == Activity.RESULT_OK && data != null
                        && data.getExtras() != null) {
                    String accountName = data.getExtras().getString(
                            AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        credential.setSelectedAccountName(accountName);
                        userVo.setGoogleemail(accountName);
                        tx_googleid.setText(userVo.getGoogleemail());
                        Log.d(TAG,"선택한 아이디는 " +  userVo.getGoogleemail());
                    }
                }
                break;
        }
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

        if(UID !=0){
            VeteranToast.makeToast(getApplicationContext(),"회원가입이 되었습니다",Toast.LENGTH_SHORT).show();
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
