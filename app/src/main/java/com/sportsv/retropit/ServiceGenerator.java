package com.sportsv.retropit;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sportsv.common.Common;
import com.sportsv.vo.Instructor;
import com.sportsv.vo.User;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by sungbo on 2016-06-21.
 */
public class ServiceGenerator {

    private static final String TAG = "ServiceGenerator";

    public static final String API_BASE_URL = Common.SERVER_ADRESS;

    private static okhttp3.OkHttpClient.Builder httpClient = new okhttp3.OkHttpClient.Builder();

    private static Gson gson = new GsonBuilder().setLenient().create();

    public ServiceGenerator() {}

    //신규가입 및 인증 없이 서버결과 조회
    public static <S> S createService(Class<S> serviceClass) {

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(API_BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                ;

        Log.d(TAG,"레트로핏 서비스 호출(범용) =======================================");

        /*******************************************************************************
         인터셉터 옵션. 개발시 요청 헤더 바디를 볼때 주석을 풀어서 사용
         HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
         loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

         //로그 인터셉터
         httpClient.addInterceptor(loggingInterceptor);

         //서버에 값을 보낼때 설정

         httpClient.addInterceptor(new Interceptor() {
        @Override
        public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
        okhttp3.Request original = chain.request();

        okhttp3.Request.Builder requestBuilder = original.newBuilder()
        .addHeader("appName", BuildConfig.APPLICATION_ID)
        .addHeader("platform", "android")
        .addHeader("appVersion", BuildConfig.VERSION_NAME)
        .addHeader("User-Agent", "Mom Soccer Android");

        okhttp3.Request request = requestBuilder.build();
        return chain.proceed(request);
        }
        });
         *****************************************************************************/

        okhttp3.OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }


    //회원 및 인증이 필요한 일반 유저용
    public static <S> S createService(Class<S> serviceClass, Context context, User user) {

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(API_BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                ;

        Log.d(TAG,"레트로핏 서비스 호출(유저인증용) =======================================");

        if (user.getUseremail() != null && user.getPassword() != null) {

            String credentials = null;

            //sns 사용자와 자체 가입 사용자를 분리해준다
            if(user.getSnstype().equals("app")){
                credentials = user.getUseremail() + ":" + user.getPassword();
            }else{
                //유저가 sns 가입자일 경우에는 유저메일이, sns 아이디값으로 인증
                credentials = user.getUseremail() + ":" + user.getSnsid();
            }

            final String basic =
                    "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);

            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request original = chain.request();

                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Authorization", basic)
                            .header("Accept", "application/json")
                            .method(original.method(), original.body());

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });

        }

        if(!Common.NETWORK_COOKIE){

            Log.d(TAG,"쿠키값은 : " + Common.NETWORK_COOKIE);

            ReceivedCookiesInterceptor receivedCookiesInterceptor = new ReceivedCookiesInterceptor(context);
            httpClient.addInterceptor(receivedCookiesInterceptor);

            AddCookiesInterceptor addCookiesInterceptor = new AddCookiesInterceptor(context);
            httpClient.addNetworkInterceptor(addCookiesInterceptor);

            Common.NETWORK_COOKIE = true;
        }

        /*****************************************************************************
         HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
         loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

         //로그 인터셉터
         httpClient.addInterceptor(loggingInterceptor);


         //서버에 값을 보낼때 설정
         /*
         httpClient.addInterceptor(new Interceptor() {
        @Override
        public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
        okhttp3.Request original = chain.request();

        okhttp3.Request.Builder requestBuilder = original.newBuilder()
        .addHeader("appName", BuildConfig.APPLICATION_ID)
        .addHeader("platform", "android")
        .addHeader("appVersion", BuildConfig.VERSION_NAME)
        .addHeader("User-Agent", "Mom Soccer Android");

        okhttp3.Request request = requestBuilder.build();
        return chain.proceed(request);
        }
        });
         *****************************************************************************/

        okhttp3.OkHttpClient client = httpClient.build();


        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }

    //강사 인증용
    public static <S> S createService(Class<S> serviceClass,Context context,Instructor instructor) {

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(API_BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                ;

        Log.d(TAG,"레트로핏 서비스 호출(강사인증용) =======================================");

        String adminEmail = instructor.getEmail();
        String adminPwd   = instructor.getPassword();

        String credentials =  adminEmail+ ":" + adminPwd;

        final String basic =
                "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                Request.Builder requestBuilder = original.newBuilder()
                        .header("Authorization", basic)
                        .header("Accept", "application/json")
                        .method(original.method(), original.body());

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        if(!Common.NETWORK_COOKIE){

            Log.d(TAG,"쿠키값은 : " + Common.NETWORK_COOKIE);

            ReceivedCookiesInterceptor receivedCookiesInterceptor = new ReceivedCookiesInterceptor(context);
            httpClient.addInterceptor(receivedCookiesInterceptor);

            AddCookiesInterceptor addCookiesInterceptor = new AddCookiesInterceptor(context);
            httpClient.addNetworkInterceptor(addCookiesInterceptor);

            Common.NETWORK_COOKIE = true;
        }

        /*****************************************************************************
         HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
         loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

         //로그 인터셉터
         httpClient.addInterceptor(loggingInterceptor);


         //서버에 값을 보낼때 설정
         /*
         httpClient.addInterceptor(new Interceptor() {
        @Override
        public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
        okhttp3.Request original = chain.request();

        okhttp3.Request.Builder requestBuilder = original.newBuilder()
        .addHeader("appName", BuildConfig.APPLICATION_ID)
        .addHeader("platform", "android")
        .addHeader("appVersion", BuildConfig.VERSION_NAME)
        .addHeader("User-Agent", "Mom Soccer Android");

        okhttp3.Request request = requestBuilder.build();
        return chain.proceed(request);
        }
        });
         *****************************************************************************/

        okhttp3.OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }


}
