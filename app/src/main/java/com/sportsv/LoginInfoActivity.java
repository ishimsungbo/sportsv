package com.sportsv;

import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.util.ExponentialBackOff;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.sportsv.R;
import com.sportsv.common.Auth;
import com.sportsv.common.Common;
import com.sportsv.common.PreSaveInfo;
import com.sportsv.dao.UserService;
import com.sportsv.vo.User;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by sungbo on 2016-05-30.
 */
public class LoginInfoActivity extends AppCompatActivity {

    @Bind(R.id.tx_phone)
    TextView tx_phone;

    @Bind(R.id.tx_snsusername)
    TextView tx_snsusername;

    @Bind(R.id.iv_user_profile)
    ImageView userimageView;

    @Bind(R.id.edit_email)
    EditText edit_email;

    @Bind(R.id.btn_google)
    Button btn_google;

    private int UID=0;

    private User sportvsUser;
    private PreSaveInfo prefUtil;
    private PreSaveInfo preSaveInfo;

    //구글 아이디 선택하기
    GoogleAccountCredential credential;
    private static final int REQUEST_ACCOUNT_PICKER = 2;


    private static final String TAG = "LoginInfoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_login_addinfo_layout);

        ButterKnife.bind(this);
        getSupportActionBar().setTitle("회원가입 부가 정보입력");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        kakaoMe();
        sportvsUser = new User();
        preSaveInfo = new PreSaveInfo(this);

        credential = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(), Arrays.asList(Auth.SCOPES));

        credential.setBackOff(new ExponentialBackOff());

    }

    @OnClick(R.id.btn_join)
    public void btnJoin(){

        Log.d(TAG, "유저 이메일 정보 : " + edit_email.getText().toString());
        sportvsUser.setUseremail(edit_email.getText().toString());




        if(!checkEmail(edit_email.getText().toString())){
            Toast.makeText(getApplicationContext(),"이메일 형식이 맞지 않습니다 : "+edit_email.getText().toString(),Toast.LENGTH_SHORT).show();
            return;
        }else{
            if(sportvsUser.getGoogleemail()!=null){
                //서버에서 uid 생성하기
                int serverUid = userCreate(sportvsUser);
                Log.d(TAG, "계정이 생성되었습니다 : ");
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }else{
                Toast.makeText(getApplicationContext(),"구글 계정은 필수 입니다",Toast.LENGTH_SHORT).show();
            }

        }

    }

    @OnClick(R.id.btn_cancel)
    public void btnCancel() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
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

                //자기전번 가져오기
                TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                String phoneNum = tm.getLine1Number();

                String snsid = String.valueOf(userProfile.getId());

                Glide.with(LoginInfoActivity.this)
                        .load(userProfile.getThumbnailImagePath())
                        .into(userimageView);

                tx_snsusername.setText(userProfile.getNickname());

                sportvsUser.setUsername(userProfile.getNickname());
                sportvsUser.setSnsid(snsid);
                sportvsUser.setSnsname(userProfile.getNickname());
                sportvsUser.setSnstype("kakao");
                sportvsUser.setPhone(phoneNum);
                sportvsUser.setProfileimgurl(userProfile.getProfileImagePath());

                //google,uid, useremail은 서버를 다녀온 후 넣어준다.
            }

            @Override
            public void onNotSignedUp() {

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

    //서버통신 관련 코딩
    public int userCreate(User user){

        final ProgressDialog dialog;

        dialog = ProgressDialog.show(this, "서버와 통신", "회원가입을 진행하고 있습니다", true);
        dialog.show();

        final Call<Integer> userCre = RetrofitServiceImplFactory.userService().createUser(user);
        userCre.enqueue(new Callback<Integer>() {

            @Override
            public void onResponse(Response<Integer> response, Retrofit retrofit) {
                try{

                    //uid 시퀀스가 디비에 생성됨
                    UID = response.body();
                    Log.d(TAG, "서버에서 생성된 아이디는 : " + UID);
                    sportvsUser.setUid(UID);
                    preSaveInfo.saveUser(sportvsUser);
                    dialog.dismiss();
                }catch (Exception e){
                    Log.d(TAG, "서버와 통신중 오류 발생");
                    UID = 0;
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(TAG,"환경 구성 확인 서버와 통신 불가");
                dialog.dismiss();
                UID = -1;
            }
        });

        return UID;
    }


    private boolean checkEmail(String email)
    {
        String mail = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
        Pattern p = Pattern.compile(mail);
        Matcher m = p.matcher(email);

        return m.matches();
    }

    @OnClick(R.id.btn_google)
    public void btn_google(){
        Log.d(TAG, "구글 아이디 선택하기");
        startActivityForResult(credential.newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_ACCOUNT_PICKER:
                if (resultCode == Activity.RESULT_OK && data != null
                        && data.getExtras() != null) {
                    String accountName = data.getExtras().getString(
                            AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        credential.setSelectedAccountName(accountName);
                        sportvsUser.setGoogleemail(accountName);
                        Log.d(TAG,"선택한 아이디는 " +  sportvsUser.getGoogleemail());
                    }
                }
                break;
        }
    }

}
