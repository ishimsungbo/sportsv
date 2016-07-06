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

import com.sportsv.common.Compare;
import com.sportsv.common.PrefUtil;
import com.sportsv.dao.InstructorService;
import com.sportsv.retropit.ServiceGenerator;
import com.sportsv.vo.Instructor;
import com.sportsv.widget.VeteranToast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsLoginActivity extends AppCompatActivity {

    private static final String TAG = "InsLoginActivity";

    TextInputLayout layoutInsEmail;
    TextInputLayout layoutPassword;

    @Bind(R.id.edit_ins_email)
    EditText edit_email_lg;

    @Bind(R.id.edit_ins_password)
    EditText edit_password_lg;

    private PrefUtil prefUtil;
    private Instructor ins;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ins_join);
        ButterKnife.bind(this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        layoutInsEmail = (TextInputLayout) findViewById(R.id.layout_ins_email_lg);
        layoutPassword  = (TextInputLayout) findViewById(R.id.layout_ins_password_lg);

        prefUtil = new PrefUtil(this);

    }

    //서버와 로그인 통신
    @OnClick(R.id.btn_inslogin)
    public void btn_inslogin(){
        if(!Compare.checkEmail(edit_email_lg.getText().toString())) {
            layoutInsEmail.setError("이메일 형식이 맞지 않습니다");
            return;
        }else if (!Compare.validatePassword(edit_password_lg.getText().toString())) {
            layoutPassword.setError("비밀번호는 5자 이상 입력해주세요");
            return;
        }else{

            layoutInsEmail.setError(null);
            layoutPassword.setError(null);

            //서버로 데이터를 보냄 강사인증
            final ProgressDialog dialog;
            dialog = ProgressDialog.show(this, "","강사님 로그인을 시도합니다", true);
            dialog.show();

            ins = new Instructor();
            ins.setEmail(edit_email_lg.getText().toString());
            ins.setPassword(edit_password_lg.getText().toString());

            InstructorService instructorService = ServiceGenerator.createService(InstructorService.class,ins);
            final Call<Instructor> call = instructorService.insLogin(ins);
            call.enqueue(new Callback<Instructor>() {
                @Override
                public void onResponse(Call<Instructor> call, Response<Instructor> response) {
                    if(response.isSuccessful()){

                        Instructor instructor = response.body();
                        Log.d(TAG,"강사 정보를 가져옵니다 : "+ instructor.toString());

                        instructor.setPassword(ins.getPassword());
                        prefUtil.saveIns(instructor);

                        Log.d("Ins Info", "************************************************");
                        Log.d("Ins Info", "Name : " + instructor.getName());
                        Log.d("Ins Info", "EMAIL : " + instructor.getEmail());
                        Log.d("Ins Info", "PW : " + instructor.getPassword());
                        Log.d("Ins Info", "************************************************");

                        dialog.dismiss();
                        VeteranToast.makeToast(getApplicationContext(),"반갑 습니다 " + edit_email_lg.getText().toString()+" 님", Toast.LENGTH_SHORT).show();

                        //메인 페이지로 이동한다
                       Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        finish();

                    }else{
                        VeteranToast.makeToast(getApplicationContext(),"비밀번호 또는 아이디를 확인해주세요", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<Instructor> call, Throwable t) {
                    dialog.dismiss();
                }
            });
        }
    }

    //강사 가입 페이지로 이동한다
    @OnClick(R.id.btn_insjoin)
    public void btn_insjoin(){
        Intent login_intent = new Intent(this,InsJoinActivity.class);
        startActivity(login_intent);
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
