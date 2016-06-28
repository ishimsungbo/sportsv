package com.sportsv.dbnetwork;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.sportsv.CustomProgressDialog;
import com.sportsv.ProgressActivity;
import com.sportsv.dao.UserMissionService;
import com.sportsv.retropit.ServiceGenerator;
import com.sportsv.vo.ServerResult;
import com.sportsv.vo.User;
import com.sportsv.vo.UserMission;
import com.sportsv.widget.VeteranToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by sungbo on 2016-06-13.
 */
public class UserMissionTRService {

    private String TAG = "UserMissionTRService";

    private Context context;
    private User user;

    private int usermissionid = 0 ;
    private String resultCode="";


    public UserMissionTRService(Context context, User user) {
        this.context = context;
        this.user = user;
    }

    public void createUserMission(UserMission userMission){

        final ProgressDialog dialog;

        dialog = ProgressDialog.show(context, "서버와 통신", "셀프 포인트를 조회합니다", true);
        dialog.show();


        UserMissionService userMissionService = ServiceGenerator.createService(UserMissionService.class,user);

        final Call<ServerResult> createMission = userMissionService.createUserMission(userMission);

        createMission.enqueue(new Callback<ServerResult>() {
            @Override
            public void onResponse(Call<ServerResult> call, Response<ServerResult> response) {
                ServerResult serverResult = response.body();

                Log.d(TAG, "서버에서 생성된 유저미션아이디는  : " + serverResult.getCount() +"  :  "+serverResult.getResult() );

                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<ServerResult> call, Throwable t) {
                Log.d(TAG, "환경 구성 확인 필요 서버와 통신 불가 : " + t.getMessage());
                t.printStackTrace();
                usermissionid = -1;
                dialog.dismiss();
            }
        });
    }

    //유저가 동영상 업로드 후, 다시 찍었을 경우 업데이트 메서드를 이용한다
    public void updateUserMisssion(int userMissionId,
                                   int uId,
                                   String youTubeaddr){

        UserMissionService userMissionService = ServiceGenerator.createService(UserMissionService.class,user);
        final Call<ServerResult> updateUserMission = userMissionService.updateUserMission(userMissionId,uId,youTubeaddr);

        updateUserMission.enqueue(new Callback<ServerResult>() {
            @Override
            public void onResponse(Call<ServerResult> call, Response<ServerResult> response) {

                ServerResult serverResult = response.body();
                Log.d(TAG,"서버요청 결과 값은 " + serverResult.toString());

                if(resultCode.equals("update")){
                    VeteranToast.makeToast(context,"유저미션이 업데이트 되었습니다",Toast.LENGTH_LONG).show();
                }else{
                    VeteranToast.makeToast(context,"유저미션 업데이트 도중 에러가 발생했습니다. 관리자에게 문의해주세요",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResult> call, Throwable t) {
                Log.d(TAG,"Error : " + t.getMessage());
                t.printStackTrace();
            }
        });
    }


}
