package com.sportsv;

import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.util.ExponentialBackOff;
import com.sportsv.common.Auth;
import com.sportsv.common.Common;
import com.sportsv.common.Compare;
import com.sportsv.common.PrefUtil;
import com.sportsv.dao.UserService;
import com.sportsv.retropit.ServiceGenerator;
import com.sportsv.vo.User;
import com.sportsv.widget.VeteranToast;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by sungbo on 2016-06-06.
 */
public class JoinActivity extends AppCompatActivity {

    TextInputLayout layoutUserEmail;
    TextInputLayout layoutPassword;

    @Bind(R.id.sgoogleid)
    TextView tx_sgoogleid;

    @Bind(R.id.edit_user_name)
    TextView edit_user_name;

    @Bind(R.id.edit_user_email)
    EditText tx_edit_user_email;

    @Bind(R.id.edit_user_password)
    EditText tx_edit_user_password;

    @Bind(R.id.joincheckBox)
    CheckBox joincheckBox;

    //구글 아이디 선택하기
    GoogleAccountCredential credential;
    private static final int REQUEST_ACCOUNT_PICKER = 2;
    private static final String TAG = "JoinActivity";

    private User userVo;
    private PrefUtil prefUtil;
    int UID;

    String JoinCheck="N";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() ========================================");

        setContentView(R.layout.ac_join_layout);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        layoutUserEmail = (TextInputLayout) findViewById(R.id.layout_user_email);
        layoutPassword  = (TextInputLayout) findViewById(R.id.layout_user_password);

        ButterKnife.bind(this);

        credential = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(), Arrays.asList(Auth.SCOPES));

        credential.setBackOff(new ExponentialBackOff());

        prefUtil = new PrefUtil(this);

        userVo = new User();

        //자체 회원 가입이기 때문에 초기값 설정 app
        userVo.setSnstype("app");
        userVo.setSnsid(Common.VETERAN_SNSID); //자체가입은 9999999999 로 강제 입력

        joincheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(joincheckBox.isChecked()){
                    JoinCheck="Y";
                }else{
                    JoinCheck="N";
                }
            }
        });
    }

    @OnClick(R.id.btn_sjoin)
    public void btn_sjoin(){

        if(!Compare.checkEmail(tx_edit_user_email.getText().toString())) {
            layoutUserEmail.setError("이메일 형식이 맞지 않습니다");
            return;
        }else if (!Compare.validatePassword(tx_edit_user_password.getText().toString())) {
            layoutPassword.setError("비밀번호는 5자 이상 입력해주세요");
            return;
        }else{

            if(JoinCheck=="Y"){

                if(tx_sgoogleid.getText().toString().length()!=0){
                    //서버에서 uid 생성하기
                    layoutUserEmail.setError(null);
                    layoutPassword.setError(null);

                    Log.d(TAG, "회원 가입을 진행합니다 : " + tx_sgoogleid.getText().toString());

                    userVo.setUseremail(tx_edit_user_email.getText().toString());
                    userVo.setPassword(tx_edit_user_password.getText().toString());
                    userVo.setUsername(edit_user_name.getText().toString());
                    userCreate(userVo);

                }else{
                    VeteranToast.makeToast(getApplicationContext(),"구글 계정은 필수 입니다",Toast.LENGTH_SHORT).show();
                }
            }else{
                VeteranToast.makeToast(getApplicationContext(), "회원가입 약관에 동의를 해주셔야 합니다", Toast.LENGTH_SHORT).show();
            }


        }
    }

    @OnClick(R.id.btn_scancel)
    public void btn_scancel(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        finish();
    }

    @OnClick(R.id.btn_sgoogle)
    public void btn_sgoogle(){
        startActivityForResult(credential.newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult() ========================================");

        switch (requestCode) {
            case REQUEST_ACCOUNT_PICKER:
                if (resultCode == Activity.RESULT_OK && data != null
                        && data.getExtras() != null) {
                    String accountName = data.getExtras().getString(
                            AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        credential.setSelectedAccountName(accountName);
                        tx_sgoogleid.setText(accountName);
                        userVo.setGoogleemail(accountName);
                        userVo.setLocation(0);
                        //userVo.setPhone(phoneNum);
                        userVo.setTeampushflag("Y");
                        userVo.setApppushflag("Y");
                    }
                }
                break;
        }
    }

    public void userCreate(final User user){

        final ProgressDialog dialog;

        dialog = ProgressDialog.show(this, "서버와 통신", "일반회원가입을 진행하고 있습니다", true);
        dialog.show();


        UserService userService = ServiceGenerator.createService(UserService.class);

        final Call<Integer> userCre = userService.createUser(user);

        userCre.enqueue(new Callback<Integer>() {

            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                try {

                    UID = response.body();
                    dialog.dismiss();

                    Log.d(TAG, "서버에서 생성된 아이디는 : " + UID);


                    if (UID == 0) {
                        Log.d(TAG, "이미 회원으로 가입 되어 있는 메일주소 : " + UID);

                        VeteranToast.makeToast(getApplicationContext(), user.getUseremail() +" 은 이미 가입되어 있는 주소입니다",Toast.LENGTH_SHORT).show();
                    } else {
                        userVo.setUid(UID);
                        prefUtil.saveUser(userVo);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("join_y", "join_flag");
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        finish();
                    }

                } catch (Exception e) {
                    Log.d(TAG, "서버와 통신중 오류 발생");
                    UID = 0;
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.d(TAG, "환경 구성 확인 필요 서버와 통신 불가 : " + t.getMessage());
                t.printStackTrace();
                dialog.dismiss();
                UID = -1;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() ========================================");
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
        Log.d(TAG, "onDestroy() ========================================");
    }

}
