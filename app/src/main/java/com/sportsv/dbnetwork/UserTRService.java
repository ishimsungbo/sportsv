package com.sportsv.dbnetwork;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Toast;

import com.sportsv.common.Common;
import com.sportsv.dao.UserService;
import com.sportsv.retropit.ServiceGenerator;
import com.sportsv.vo.ServerResult;
import com.sportsv.vo.User;
import com.sportsv.widget.VeteranToast;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sungbo on 2016-07-08.
 */
public class UserTRService {

    private static final String TAG = "UserTRService";
    private Activity activity;
    private User user;
    private String profileimgurl; //유저 사진

    public UserTRService(Activity activity, User user) {
        this.activity = activity;
        this.user = user;
    }

    public void updateUserImage(String uid, String filename, String realFilePath){

        File readFile = new File(realFilePath);
        profileimgurl = Common.SERVER_USER_IMGFILEADRESS + filename;

        //유저아이디
        RequestBody userid =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), String.valueOf(uid));
        //파일명
        RequestBody fileName =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), filename);
        //실제파일위치
        RequestBody serverPath =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), profileimgurl);
        //업로드 사진
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), readFile);

        MultipartBody.Part file =
                MultipartBody.Part.createFormData("file", readFile.getName(), requestFile);

        //통신 준비
        UserService userService = ServiceGenerator.createService(UserService.class,activity,user);

        Log.d(TAG,"유저 사진 없로드 : " + user.toString());
        Log.d(TAG,"픽업된 사진은 : " + realFilePath);

        final Call<ServerResult> resultCall = userService.fileupload(userid,fileName,serverPath,file);

        //프로그레스바 준비
        final ProgressDialog dialog;
        dialog = ProgressDialog.show(activity, "", "유저 프로파일 사진을 업데이트 합니다", true);
        dialog.show();

        resultCall.enqueue(new Callback<ServerResult>() {
            @Override
            public void onResponse(Call<ServerResult> call, Response<ServerResult> response) {
                if(response.isSuccessful()){
                    ServerResult serverResult = response.body();
                    Log.d(TAG,"값 : "+serverResult.toString());
                    dialog.dismiss();
                    VeteranToast.makeToast(activity,"프로파일 사진을 업데이트 했습니다", Toast.LENGTH_SHORT).show();
                }else{
                    Log.d(TAG,"서버 컨트롤러에서 값을 받지 못했습니다");
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ServerResult> call, Throwable t) {
                Log.d(TAG,"통신오류 발생 " + t.getMessage());
                t.printStackTrace();
                dialog.dismiss();
            }
        });
    }


}
