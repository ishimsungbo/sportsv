package com.sportsv.common;


import android.app.Activity;
import android.content.Context;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.youtube.YouTubeScopes;

import java.util.Arrays;

public class GoogleAuth  {

    private static final String TAG = "GoogleAuth";

    private Activity activity;
    private GoogleAccountCredential credential;
    private String googleEmail;

    public GoogleAuth(Activity activity, GoogleAccountCredential credential, String googleEmail) {
        this.activity = activity;
        this.credential = credential;
        this.googleEmail = googleEmail;
    }

    public GoogleAccountCredential setYutubeCredential(){
        credential = GoogleAccountCredential.usingOAuth2(activity, Arrays.asList(Auth.SCOPES));
        credential = GoogleAccountCredential.usingOAuth2(activity, YouTubeScopes.all());
        credential.setBackOff(new ExponentialBackOff());
        credential.setSelectedAccountName(googleEmail);
        return credential;
    }

    /****
     * 유튜부를 최초로 이용하는 사용자라면 아래 메세지가 나타나게 된다. 업로드시...
     * https://developers.google.com/youtube/v3/docs/errors?hl=ko#-
     * youtubeSignupRequired	이 오류는 사용자에게 연결되지 않은 Google 계정이 있음을 나타냅니다. 즉 Google 계정이 있지만 YouTube 채널은 없습니다. 이러한 사용자는 사용자 인증에 따라 동영상을 평가하거나 동영상을 watch_later 재생목록에 추가하는 등 다양한 기능을 사용할 수 있습니다. 하지만 동영상을 업로드하려면 YouTube 채널이 필요합니다. Gmail 계정이나 Android 기기가 있는 사용자는 Google 계정이 있겠지만, 해당 Google 계정이 YouTube 채널과 연결되어 있지 않을 수도 있습니다.

     OAuth 2.0 서비스 계정 흐름을 사용할 때 일반적으로 나타나는 오류입니다. YouTube는 서비스 계정을 지원하지 않으며, 서비스 계정으로 인증을 시도할 경우 이 오류가 발생합니다.

     Google 계정 지원에 대해 소개하는 YouTube API 블로그 게시물에서도 youtubeSignupRequired 오류를 자세히 논의하고 있습니다. 게시물에서 설명하는 오류는 API 버전 2.1이지만 오류의 의미는 같습니다.
     *
     *
     */

}
