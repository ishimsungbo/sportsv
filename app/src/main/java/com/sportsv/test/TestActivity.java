package com.sportsv.test;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sportsv.R;
import com.sportsv.WelcomeActivity;
import com.sportsv.common.PrefManager;
import com.sportsv.common.PrefUtil;
import com.sportsv.dbnetwork.UserTRService;
import com.sportsv.viewpage.ViewPage;
import com.sportsv.vo.User;
import com.sportsv.widget.VeteranToast;
import com.sportsv.youtubeupload.StartUploadActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by sungbo on 2016-06-08.
 */
public class TestActivity extends AppCompatActivity {

    private static final int RESULT_WEBSITE = 1;

    private static final String TAG = "TestActivity";

    //와이파이 및 모바일 네트워크 설정
    private ConnectivityManager cManager;
    private NetworkInfo mobile;
    private NetworkInfo wifi;

    //기타설정
    private SharedPreferences mPref;
    Button btngent;

    //사용자정보
    private PrefUtil prefUtil;
    private User user;

    private ProgressDialog asyncDialog;
    private ProgressBar mProgressLarge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);

        ButterKnife.bind(this);

        prefUtil = new PrefUtil(this);
        user = prefUtil.getUser();
        Log.d(TAG,"유저 정보 : " +user.toString());

        //기타 설정 값 가져오기 주석 처리 상태
        // 프리퍼런스 화면에서 array.xml로 값을 설정한 예제
        /*
        mPref = PreferenceManager.getDefaultSharedPreferences(this);

        //자동업데이트 기능을 유저가 설정 했는지 알아본다
        Log.d("TestActivity","업데이트 설정 : " + mPref.getBoolean("autoUpdate", false));
        Log.d("TestActivity", "업데이트 후 알람 : " + mPref.getBoolean("useUpdateNofiti", false));

        String[] array = getResources().getStringArray(R.array.userNameOpen);

        int index = getArrayIndex(R.array.userNameOpen_values, mPref.getString("userNameOpen", "0"));

        Log.d("TestActivity","설정한 배열값은  : " + index);

        if (index != -1) {
            String userNameOpenString = array[index];
            Log.d("TestActivity"," 설정 한 값은 : "+ userNameOpenString);
        }
        */


        //와이파이 연결 설정 여부 확인
        cManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        mobile = cManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        wifi = cManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        btngent = (Button) findViewById(R.id.btngent);

        btngent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mobile.isConnected()) {
                    Log.d(TAG,"통신사 연결 되어 있습니다");
                }else{
                    Log.d(TAG,"통신사 연결이 안되어 있습니다");
                }

                if(wifi.isConnected()){
                    Log.d(TAG,"WIFI 연결 되어 있습니다");
                }else{
                    Log.d(TAG,"WIFI 연결 안되어 있습니다");
                }
            }
        });


        //프로그레스바 테스트
        mProgressLarge = (ProgressBar) findViewById(R.id.progcircle);
        mProgressLarge.setVisibility(ProgressBar.GONE);

        asyncDialog = new ProgressDialog(this);
        asyncDialog.setTitle("안녕하세요 프로그레스바 돌립니다");


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

    //기본 배열중에 , 자신이 기타설정에서 선택한 배열 값과 동일하다면 그것은...없다면 비공개를 리턴 한다
    private int getArrayIndex(int array, String findIndex) {
        String[] arrayString = getResources().getStringArray(array);
        for (int e = 0; e < arrayString.length; e++) {
            if (arrayString[e].equals(findIndex))
                return e;
        }
        return -1;
    }

    @OnClick(R.id.btn_api)
    public void btn_api(){
        Intent intent = new Intent(this,StartUploadActivity.class);
        startActivity(intent);
    }


    @OnClick(R.id.btn_webview)
    public void btn_webview(){

        //1.인텐트 방식으로 웹 브라우저 호출. 독립적으로 실행하기 때문에 안드로이드 컨트룰 불가능
        //String url ="selphone://post_detail?post_id=10"; get 방식으로 데이터에 맞는 url를 호출할 수 있다.

        String url = "https://m.youtube.com/create_channel?chromeless=1&next=/channel_creation_done";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.youtube.com/create_channel?chromeless=1&next=/channel_creation_done"));
        intent.setPackage("com.android.chrome");
        startActivityForResult(intent, RESULT_WEBSITE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_WEBSITE:

                Log.d(TAG,"값은 : "+requestCode);

                break;
        }
    }

    @OnClick(R.id.progressstart)
    public void progress(){
        mProgressLarge.setVisibility(ProgressBar.VISIBLE);
        mProgressLarge.setIndeterminate(true);
        mProgressLarge.setMax(100);

        asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        asyncDialog.setMessage("로딩중입니다..");
        asyncDialog.show();
    }

    @OnClick(R.id.progressstop)
    public void progressstop(){
        mProgressLarge.setVisibility(ProgressBar.INVISIBLE);
        asyncDialog.dismiss();
    }

    @OnClick(R.id.btn_play_again)
    public void initialSlide(){
        PrefManager prefManager = new PrefManager(getApplicationContext());

        // make first time launch TRUE
        prefManager.setFirstTimeLaunch(true);

        startActivity(new Intent(this, WelcomeActivity.class));
        finish();
    }

    @OnClick(R.id.btn_uplaod_user)
    public void btnup(){
        UserTRService userTRService = new UserTRService(this,user);
        userTRService.updateUserImage("254","1467950181857.jpg","/storage/emulated/0/SmartWheel/1467950181857.jpg");
    }

    @OnClick(R.id.btn_viewPage_test)
    public void viewPageTest(){
        Intent intent = new Intent(this, ViewPage.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_gridview)
    public void btn_gridview(){
        VeteranToast.makeToast(getApplicationContext(),"그리드 레이아웃 사용예제", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, GridViewTest.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_gridview1)
    public void btn_gridview1(){
        VeteranToast.makeToast(getApplicationContext(),"그리드 레이아웃 사용예제", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, GridViewTest2.class);
        startActivity(intent);
    }

}
