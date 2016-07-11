package com.sportsv.dbnetwork;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.sportsv.common.Common;
import com.sportsv.common.PrefUtil;
import com.sportsv.dao.TeamService;
import com.sportsv.retropit.ServiceGenerator;
import com.sportsv.vo.Instructor;
import com.sportsv.vo.Team;
import com.sportsv.widget.VeteranToast;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sungbo on 2016-07-07.
 */
public class TeamTRService {

    private static final String TAG = "TeamTRService";
    private PrefUtil prefUtil;
    private Activity activity;
    private Instructor instructor;
    private String profileimgurl; //엠블렘(이미지)

    public TeamTRService(Activity activity, Instructor instructor) {
        this.activity = activity;
        this.instructor = instructor;
    }

    public void saveTeam(String filename, String realFilePath,String trflag, Team team){

        File readFile = new File(realFilePath);
        profileimgurl = Common.SERVER_TEAM_IMGFILEADRESS + filename;

        //강사번호
        RequestBody insid =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), String.valueOf(instructor.getInstructorid()));
        //팀명
        RequestBody teamName =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), team.getName());
        //팀설명
        RequestBody teamDisp =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), team.getDescription());

        //파일명
        RequestBody fileName =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), filename);
        //실제파일위치
        RequestBody serverPath =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), profileimgurl);

        //NEW,UPDATE 이미지 트랜잭션 종류
        RequestBody trFlag =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), trflag);

        //업로드 사진
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), readFile);

        MultipartBody.Part file =
                MultipartBody.Part.createFormData("file", readFile.getName(), requestFile);

        //통신 준비
        TeamService teamService = ServiceGenerator.createService(TeamService.class,activity,instructor);
        Call<Team> call = teamService.saveTeam(insid,teamName,teamDisp,fileName,serverPath,trFlag,file);

        final ProgressDialog dialog;
        dialog = ProgressDialog.show(activity, "", "팀을 생성합니다", true);
        dialog.show();

        call.enqueue(new Callback<Team>() {
            @Override
            public void onResponse(Call<Team> call, Response<Team> response) {
                if(response.isSuccessful()){
                    Team resultTeam = response.body();
                    Log.d(TAG,"서버에서 생성된 팀정보 : "+resultTeam.toString());

                    prefUtil = new PrefUtil(activity);
                    dialog.dismiss();
                    VeteranToast.makeToast(activity,"팀이 생성 되었습니다", Toast.LENGTH_SHORT).show();

                    //강사정보로 이동
                    final Intent intent = new Intent("createTeam");
                    final LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(activity);
                    broadcastManager.sendBroadcast(intent);

                }else{
                    Log.d(TAG,"서버 컨트롤러에서 값을 받지 못했습니다");
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Team> call, Throwable t) {
                Log.d(TAG,"통신오류 발생 " + t.getMessage());
                t.printStackTrace();
                dialog.dismiss();
            }
        });

    }

}
