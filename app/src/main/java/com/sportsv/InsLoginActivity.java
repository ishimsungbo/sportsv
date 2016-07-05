package com.sportsv;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.sportsv.common.Compare;
import com.sportsv.dao.InstructorService;
import com.sportsv.retropit.ServiceGenerator;
import com.sportsv.vo.Instructor;
import com.sportsv.vo.ServerResult;
import com.sportsv.widget.VeteranToast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsLoginActivity extends AppCompatActivity {

    TextInputLayout layoutInsEmail;
    TextInputLayout layoutPassword;

    @Bind(R.id.edit_ins_email)
    EditText edit_email_lg;

    @Bind(R.id.edit_ins_password)
    EditText edit_password_lg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ins_join);
        ButterKnife.bind(this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        layoutInsEmail = (TextInputLayout) findViewById(R.id.layout_ins_email_lg);
        layoutPassword  = (TextInputLayout) findViewById(R.id.layout_ins_password_lg);


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

            Instructor ins = new Instructor();
            ins.setEmail(edit_email_lg.getText().toString());
            ins.setPassword(edit_password_lg.getText().toString());

            InstructorService instructorService = ServiceGenerator.createService(InstructorService.class,ins);
            final Call<ServerResult> call = instructorService.insLogin();
            call.enqueue(new Callback<ServerResult>() {
                @Override
                public void onResponse(Call<ServerResult> call, Response<ServerResult> response) {
                    if(response.isSuccessful()){
                        dialog.dismiss();



                        VeteranToast.makeToast(getApplicationContext(),"반갑 습니다 " + edit_email_lg.getText().toString()+" 님", Toast.LENGTH_SHORT).show();
                    }else{
                        VeteranToast.makeToast(getApplicationContext(),"비밀번호 또는 아이디를 확인해주세요", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<ServerResult> call, Throwable t) {
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

    private void insSetInfo(){



    }

}
