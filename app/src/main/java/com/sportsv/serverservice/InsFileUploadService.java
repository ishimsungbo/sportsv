package com.sportsv.serverservice;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.sportsv.common.Common;
import com.sportsv.vo.Instructor;
import com.sportsv.vo.ServerResult;
import com.sportsv.widget.VeteranToast;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Created by sungbo on 2016-07-06.
 * 강사 사진 업로드 spring restTemplate
 */
public class InsFileUploadService extends AsyncTask<Void,Void,ServerResult> {

    private static final String TAG = "InsFileUploadService";
    private String instructorId;
    private String FileName;
    private String RealFilePath;  //안드로이드에 생성된 이미지파일
    private String profileimgurl;
    private Instructor instructor;
    private Context context;

    //Http Body
    private MultiValueMap<String, Object> formData;

    public InsFileUploadService(String instructorId, String fileName, String realFilePath, Instructor instructor, Context context) {
        this.instructorId = instructorId;
        FileName = fileName;
        RealFilePath = realFilePath;
        this.instructor = instructor;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {

        Resource resource = new FileSystemResource(RealFilePath);

        profileimgurl = Common.SERVER_INS_IMGFILEADRESS + FileName;

        formData = new LinkedMultiValueMap<String, Object>();
        formData.add("instructorid",instructorId);
        formData.add("filename", FileName);
        formData.add("file", resource);
        formData.add("profileimgurl", profileimgurl);
    }

    @Override
    protected ServerResult doInBackground(Void... params) {
        Log.d(TAG, "강사 이미지 파일 업로드를 시작합니다");

        try{

            final String url = Common.SERVER_ADRESS + "/api/ins/fileupload";

            HttpAuthentication authHeader = new HttpBasicAuthentication(instructor.getEmail(),instructor.getPassword()); //인증
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setAuthorization(authHeader); //인증
            requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(
                    formData, requestHeaders);
            RestTemplate restTemplate = new RestTemplate(true);
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            ResponseEntity<ServerResult> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, ServerResult.class);
            Log.d(TAG,"서버 응답 값은 : " + response.getBody());
            return response.getBody();
        }catch (Exception e){
            VeteranToast.makeToast(context,"강사 사진을 업로드중 에러가 발생 했습니다", Toast.LENGTH_SHORT).show();
            Log.e(TAG, e.getMessage(), e);
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(ServerResult serverResult) {
        VeteranToast.makeToast(context,"업로드가 정상적으로 이루어 졌습니다 : " + serverResult.getResult(),0).show();
    }

}
