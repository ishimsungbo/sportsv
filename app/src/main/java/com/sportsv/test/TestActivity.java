package com.sportsv.test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sportsv.R;
import com.sportsv.common.PrefUtil;
import com.sportsv.dao.UserMissionService;
import com.sportsv.dbnetwork.UserMissionTRService;
import com.sportsv.serverservice.RetrofitService;
import com.sportsv.vo.User;
import com.sportsv.vo.UserMission;
import com.sportsv.youtubeupload.StartUploadActivity;

import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.Body;

/**
 * Created by sungbo on 2016-06-08.
 */
public class TestActivity extends AppCompatActivity {

    private static final String TAG = "TestActivity";

    //와이파이 및 모바일 네트워크 설정
    ConnectivityManager cManager;
    NetworkInfo mobile;
    NetworkInfo wifi;

    //기타설정
    private SharedPreferences mPref;
    Button btngent;

    //사용자정보
    PrefUtil prefUtil;
    User user;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

        }

    }
    /****************************************************************************************************
     ****************************************************************************************************
     * **************************************************************************************************
     * **************************************************************************************************
     * **************************************************************************************************/

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

    @OnClick(R.id.btnupdate)
    public void btnupdate(){

        UserMissionTRService service = new UserMissionTRService(this);
        service.updateUserMisssion(9,174,"dajisdpodmkl");

    }



}
