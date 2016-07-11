package com.sportsv;

import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.util.ExponentialBackOff;
import com.google.firebase.iid.FirebaseInstanceId;
import com.sportsv.common.Auth;
import com.sportsv.common.Common;
import com.sportsv.common.Compare;
import com.sportsv.common.PrefUtil;
import com.sportsv.dao.InstructorService;
import com.sportsv.retropit.ServiceGenerator;
import com.sportsv.vo.Instructor;
import com.sportsv.vo.ServerResult;
import com.sportsv.widget.VeteranToast;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsJoinActivity extends AppCompatActivity {

    //구글 아이디 선택하기
    private GoogleAccountCredential credential;
    private static final int REQUEST_ACCOUNT_PICKER = 2;
    private static final String TAG = "InsJoinActivity";

    TextInputLayout layout_ins_nm;
    TextInputLayout layout_ins_em;
    TextInputLayout layout_ins_pw;

    @Bind(R.id.edit_ins_nm)
    EditText edit_ins_nm;

    @Bind(R.id.edit_ins_em)
    EditText edit_ins_em;

    @Bind(R.id.edit_ins_pw)
    EditText edit_ins_pw;

    @Bind(R.id.ch_insjoincheckBox)
    CheckBox ch_insjoincheckBox;

    private String JoinCheck="N";
    private String phoneNumber = "";

    private PrefUtil prefUtil;
    private Instructor instructor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ins_join_activity);
        ButterKnife.bind(this);

        //구글 계정을 PickUp 하기위한 메서드들..
        credential = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(), Arrays.asList(Auth.SCOPES));

        credential.setBackOff(new ExponentialBackOff());

        layout_ins_nm = (TextInputLayout) findViewById(R.id.layout_ins_nm);
        layout_ins_em = (TextInputLayout) findViewById(R.id.layout_ins_em);
        layout_ins_pw = (TextInputLayout) findViewById(R.id.layout_ins_pw);

        ch_insjoincheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ch_insjoincheckBox.isChecked()){
                    JoinCheck="Y";
                }else{
                    JoinCheck="N";
                }
            }
        });

        //get Phone number
        TelephonyManager telManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        phoneNumber = telManager.getLine1Number();

        prefUtil = new PrefUtil(this);

    }

    @OnClick(R.id.btn_ins_cancel)
    public void cancel(){
        finish();
    }

    @OnClick(R.id.btn_ins_join)
    public void insJoin(){
        if(Compare.isEmpty(edit_ins_nm.getText().toString())) {
            layout_ins_nm.setError("강사님 이름을 입력해주세요");
            return;
        }else if (!Compare.checkEmail(edit_ins_em.getText().toString())) {
            layout_ins_em.setError("이메일 형식이 맞지 않습니다");
            return;
        }else if(!Compare.validatePassword(edit_ins_pw.getText().toString())) {
            layout_ins_pw.setError("비밀번호는 5자 이상 입력해주세요");
            return;
        }else{

            if(JoinCheck=="Y"){

                if(edit_ins_em.getText().toString().length()!=0){
                    //서버에서 uid 생성하기
                    layout_ins_nm.setError(null);
                    layout_ins_em.setError(null);
                    layout_ins_pw.setError(null);

                }else{
                    VeteranToast.makeToast(getApplicationContext(),"구글 이메일 계정은 필수 입니다", Toast.LENGTH_SHORT).show();
                }
            }else{
                VeteranToast.makeToast(getApplicationContext(), "강사님 회원가입 약관에 동의를 해주셔야 합니다", Toast.LENGTH_SHORT).show();
            }
        }

        /****************************************************/
        //강사 정보를 담을 객체 생성

        instructor = new Instructor();

        instructor.setEmail(edit_ins_em.getText().toString());
        instructor.setName(edit_ins_nm.getText().toString());
        instructor.setPassword(edit_ins_pw.getText().toString());
        instructor.setProfile("프로파일");
        instructor.setDescription("강사설명");
        instructor.setPhone(phoneNumber);
        instructor.setLocation(0);
        instructor.setFeedbackflag("Y");
        instructor.setApppushflag("Y");
        instructor.setPointhistoryid(0);
        instructor.setFcmtoken(FirebaseInstanceId.getInstance().getToken());
        instructor.setSerialnumber(Common.getDeviceSerialNumber());
        instructor.setCommontokenid(0);
        /****************************************************/

        final ProgressDialog dialog;
        dialog = ProgressDialog.show(this, "","강사님 정보를 등록합니다", true);
        dialog.show();

        InstructorService instructorService = ServiceGenerator.createService(InstructorService.class);
        final Call<ServerResult> call = instructorService.saveInstructor(instructor);
        call.enqueue(new Callback<ServerResult>() {
            @Override
            public void onResponse(Call<ServerResult> call, Response<ServerResult> response) {

                if(response.isSuccessful()){

                    ServerResult ins = response.body();
                    Log.d(TAG,"작업결과 : "+ ins.toString());
                    dialog.dismiss();

                    if(ins.getCount() == -5){
                        VeteranToast.makeToast(getApplicationContext(), "이미 가입되어 있는 아이디입니다", Toast.LENGTH_SHORT).show();
                    }else{
                        VeteranToast.makeToast(getApplicationContext(), "강사님의 몸싸커 가입을 축하드립니다", Toast.LENGTH_SHORT).show();
                        instructor.setInstructorid(ins.getCount());
                        prefUtil.saveIns(instructor);

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        finish();
                    }
                }else{
                    dialog.dismiss();
                    VeteranToast.makeToast(getApplicationContext(), "강사 정보 등록중 문제발생", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResult> call, Throwable t) {
                dialog.dismiss();
                VeteranToast.makeToast(getApplicationContext(), "강사정보를 등록하는 중 서버통신 에러발생 : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    @OnClick(R.id.btn_ins_google)
    public void chooseGoogleAccount(){
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
                        edit_ins_em.setText(accountName);
                    }
                }
                break;
        }
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
