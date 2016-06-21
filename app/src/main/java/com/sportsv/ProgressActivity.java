package com.sportsv;

import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.sportsv.widget.VeteranToast;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProgressActivity extends AppCompatActivity {


    private static final String TAG = "ProgressActivity";

    private CustomProgressDialog customProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_layout);

        VeteranToast.makeToast(getApplicationContext(),"프로그레스바 테스트",0).show();
        ButterKnife.bind(this);

        customProgressDialog = new CustomProgressDialog(ProgressActivity.this);
        customProgressDialog .getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }


    @OnClick(R.id.btncicle)
    public void cicle(){
        CehckTypesTask task = new CehckTypesTask();
        task.execute();
    }

    @OnClick(R.id.btnbar)
    public void btnbar(){
        CehckTypesTaski task = new CehckTypesTaski();
        task.execute();
    }

    @OnClick(R.id.btnCustom)
    public void btnCustom(){
        customProgressDialog.show(); // 보여주기
    }


    private class CehckTypesTask extends AsyncTask<Void,Void,Void>{

        CustomProgressDialog dialog = new CustomProgressDialog(ProgressActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setTitle("프로그레스 테스트");

            //dialog.se(ProgressDialog.STYLE_SPINNER);
            //dialog.setMessage("로딩중입니다");

            dialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {

            try{
                for(int i=0; i< 5; i++){
                    Thread.sleep(500);
                    Log.d(TAG," 값은 : " + i );
                }

            }catch (InterruptedException e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
        }
    }


    private class CehckTypesTaski extends AsyncTask<Void,Void,Void>{

        ProgressDialog dialog = new ProgressDialog(ProgressActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog.setTitle("프로그레스 테스트");
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setMessage("로딩중입니다");
            dialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {

            try{
                for(int i=0; i< 10; i++){

                    Thread.sleep(1000);

                    //10초가 끝이니까
                    dialog.setProgress( i * 10);

                    Log.d(TAG,"값은 : " + i);

                }

            }catch (InterruptedException e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
        }
    }

}
