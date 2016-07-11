package com.sportsv.dbnetwork;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import com.sportsv.common.Common;
import com.sportsv.dao.InstructorService;
import com.sportsv.retropit.ServiceGenerator;
import com.sportsv.vo.Instructor;
import com.sportsv.vo.ServerResult;
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
public class InstructorTRService {

    private static final String TAG = "InstructorTRService";

    private Activity activity;
    private Instructor instructor;
    private String profileimgurl; //강사 사진 풀 주소
    private GridView mGridView;

    public InstructorTRService(Activity activity, Instructor instructor) {
        this.activity = activity;
        this.instructor = instructor;
    }

    public void upladteInsImage(String filename, String realFilePath){

        File readFile = new File(realFilePath);
        profileimgurl = Common.SERVER_INS_IMGFILEADRESS + filename;

        //유저아이디
        RequestBody instructorid =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), String.valueOf(instructor.getInstructorid()));
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

        InstructorService instructorService = ServiceGenerator.createService(InstructorService.class,activity,instructor);

        Log.d(TAG,"강사 사진 없로드 : " + instructor.toString());
        Log.d(TAG,"픽업된 사진은 : " + realFilePath);

        final Call<ServerResult> call = instructorService.fileupload(instructorid,fileName,serverPath,file);

        //프로그레스바 준비
        final ProgressDialog dialog;
        dialog = ProgressDialog.show(activity, "", "강사 프로파일 사진을 업데이트 합니다", true);
        dialog.show();

        call.enqueue(new Callback<ServerResult>() {
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
