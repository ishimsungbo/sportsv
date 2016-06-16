package com.sportsv.dbnetwork;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.sportsv.serverservice.RetrofitService;
import com.sportsv.widget.VeteranToast;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by sungbo on 2016-06-16.
 */
public class UserTRService {

    private String TAG;
    private AppCompatActivity activity;

    //유저미션을 생성할때 사용하는 생성자
    public UserTRService(){}

    public UserTRService(AppCompatActivity activity) {
        this.activity = activity;
    }

    //출석체크용
    public void daliyCheck(int uid, String description, String lang, final TextView textView){
        final Call<String> dailyCheck = RetrofitService.userService().daliyCheck(String.valueOf(uid),description,lang);
        dailyCheck.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Response<String> response, Retrofit retrofit) {

                String result = response.body();
                Log.d(TAG,"결과는 : " + result);

                if(result.equals("exist")){

                    VeteranToast.makeToast(activity,"이미 오늘 출석체크가 되었습니다", Toast.LENGTH_LONG).show();

                }else if(result.equals("error")){

                    VeteranToast.makeToast(activity,"서버와 통신중 에러가 발견되었습니다. 관리자에게 문의해주세요", Toast.LENGTH_LONG).show();

                }else{

                    VeteranToast.makeToast(activity,"500점의 출석셀프 포인트가 지급 되었습니다", Toast.LENGTH_LONG).show();
                    textView.setText(result);

                }


            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(TAG,"오류 발생 : "+ t.getMessage());
                t.printStackTrace();
            }
        });
    }

}
