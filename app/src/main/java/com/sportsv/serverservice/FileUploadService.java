package com.sportsv.serverservice;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.sportsv.common.Common;
import com.sportsv.vo.ServerResult;
import com.sportsv.vo.User;
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
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


/**
 * Created by sungbo on 2016-06-09.
 * 유저 사진 업로드
 */
public class FileUploadService extends AsyncTask<Void,Void,ServerResult>{


    private static final String TAG = "FileUploadService";
    private String uid; //유저시퀀스
    private String FileName;
    private String RealFilePath;  //안드로이드에 생성된 이미지파일
    private String profileimgurl;
    private User user;
    private Context context;

    //유저 아이디
    //확장자를 포함한 파일명
    //확장자를 포함한 파일의 위치+파일명
    public FileUploadService(String uid, String fileName, String realFilePath, User user,Context context) {
        this.uid = uid;
        FileName = fileName;
        RealFilePath = realFilePath;
        this.user = user;
        this.context = context;
    }

    private MultiValueMap<String, Object> formData;



    @Override
    protected void onPreExecute() {

        Resource resource = new FileSystemResource(RealFilePath);

        profileimgurl = Common.SERVER_IMGFILEADRESS + FileName;

        formData = new LinkedMultiValueMap<String, Object>();
        formData.add("uid",uid);
        formData.add("filename", FileName);
        formData.add("file", resource);
        formData.add("profileimgurl", profileimgurl);
    }

    @Override
    protected ServerResult doInBackground(Void... params) {
        Log.d(TAG, "업로드를 시작합니다");

        try {

            final String url = Common.SERVER_ADRESS + "/api/user/fileupload";

            String useremail = null;
            String pwd = null;

            if(user.getSnstype().equals("app")){
                useremail = user.getUseremail();
                pwd = user.getPassword();
            }else{
                useremail = user.getUseremail();
                pwd = user.getSnsid();
            }

            HttpAuthentication authHeader = new HttpBasicAuthentication(useremail,pwd); //인증

            HttpHeaders requestHeaders = new HttpHeaders();

            requestHeaders.setAuthorization(authHeader); //인증

            requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(
                    formData, requestHeaders);

            RestTemplate restTemplate = new RestTemplate(true);

            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            ResponseEntity<ServerResult> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, ServerResult.class);

            // Return the response body to display to the user

            Log.d(TAG,"서버 응답 값은 : " + response.getBody());

            return response.getBody();

        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(ServerResult serverResult) {
        VeteranToast.makeToast(context,"업로드가 정상적으로 이루어 졌습니다 : " + serverResult.getResult(),0).show();
    }
}

