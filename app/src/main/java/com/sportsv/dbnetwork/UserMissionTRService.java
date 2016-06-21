package com.sportsv.dbnetwork;


import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.sportsv.serverservice.RetrofitService;
import com.sportsv.vo.UserMission;
import com.sportsv.widget.VeteranToast;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by sungbo on 2016-06-13.
 */
public class UserMissionTRService {

    private String TAG = "UserMissionTRService";
    private AppCompatActivity activity;
    private int usermissionid = 0 ;

    private String resultCode="";

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    //유저미션을 생성할때 사용하는 생성자
    public UserMissionTRService(){}

    public UserMissionTRService(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void createUserMission(UserMission userMission){


        //RetrofitService retrofitService = new RetrofitService(activity);

        final Call<Integer> createMission = RetrofitService.userMissionService().createUserMission(userMission);

        createMission.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Response<Integer> response, Retrofit retrofit) {
                usermissionid = response.body();
                Log.d(TAG, "서버에서 생성된 유저미션아이디는  : " + usermissionid );
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(TAG, "환경 구성 확인 필요 서버와 통신 불가 : " + t.getMessage());
                t.printStackTrace();
                usermissionid = -1;
            }
        });
    }

    //유저가 동영상 업로드 후, 다시 찍었을 경우 업데이트 메서드를 이용한다
    public void updateUserMisssion(int userMissionId,
                                   int uId,
                                   String youTubeaddr){

        //RetrofitService retrofitService = new RetrofitService(activity);
        final Call<String> updateUserMission = RetrofitService.userMissionService().updateUserMission(userMissionId,uId,youTubeaddr);

        updateUserMission.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Response<String> response, Retrofit retrofit) {

                resultCode = response.body();

                Log.d(TAG,"결과 값은 : "+resultCode);

                if(resultCode.equals("update")){
                    VeteranToast.makeToast(activity,"유저미션이 업데이트 되었습니다",Toast.LENGTH_LONG).show();
                }else{
                    VeteranToast.makeToast(activity,"유저미션 업데이트 도중 에러가 발생했습니다. 관리자에게 문의해주세요",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(TAG,"Error : " + t.getMessage());
                t.printStackTrace();
            }
        });
    }


}
