package com.sportsv.youtubeupload;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import com.sportsv.AppLoginActivity;
import com.sportsv.R;
import com.sportsv.common.Auth;
import com.sportsv.common.Constants;
import com.sportsv.common.GoogleAuth;
import com.sportsv.widget.VeteranToast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by sungbo on 2016-06-13.
 */
public class StartUploadActivity extends AppLoginActivity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "StartUploadActivity";

    YouTube.Videos.Insert videoInsert = null;
    /***********************************************************************************/
    private static final int REQUEST_GOOGLE_PLAY_SERVICES = 0;
    private static final int REQUEST_ACCOUNT_PICKER = 2;
    private static final int REQUEST_AUTHORIZATION = 3;
    private static final int RESULT_PICK_IMAGE_CROP = 4;

    static final String REQUEST_AUTHORIZATION_INTENT = "com.google.example.yt.RequestAuth";
    static final String REQUEST_AUTHORIZATION_INTENT_PARAM = "com.google.example.yt.RequestAuth.param";

    private Uri mFileURI = null;

    //유투브 업로드를 사용하기 위한 필요 객체들
    GoogleAccountCredential credential;
    private GoogleApiClient mGoogleApiClient;
    final HttpTransport transport = AndroidHttp.newCompatibleTransport();
    final JsonFactory jsonFactory = new GsonFactory();

    private UploadBroadcastReceiver broadcastReceiver;

    /******************************************
     * 영상 업로드 관련 설정
     * *****************************************/
    public static ProgressBar progressBar;
    public static TextView textView_up_title;
    public static TextView textView_up_percent;
    public static RelativeLayout uploadLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_upload_layout);

        Log.d(TAG,"구글 업로드 계정 : " + Auth.accountName);

        GoogleAuth googleAuth = new GoogleAuth(this,credential,Auth.accountName);
        credential = googleAuth.setYutubeCredential();
        googleAPI();

        new Async(getApplicationContext()).execute();
        mGoogleApiClient.connect();

        //업로드 프로그레스바 설정
        uploadLayout = (RelativeLayout) findViewById(R.id.upload_layout);
        uploadLayout.setVisibility(View.GONE); // 최초 화면에서 제외

        progressBar = (ProgressBar) findViewById(R.id.upload_progress);
        textView_up_title = (TextView) findViewById(R.id.tx_upload_title);
        textView_up_percent = (TextView) findViewById(R.id.upload_percent);

        //브로드 캐스트 수신 받는 함수 동적으로...
        LocalBroadcastManager.getInstance(this).registerReceiver(uploadReceiver, new IntentFilter("uploadReceiver"));
        LocalBroadcastManager.getInstance(this).registerReceiver(uploadVideo, new IntentFilter("uploadVideo"));
    }

    BroadcastReceiver uploadVideo = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String upPercent = intent.getStringExtra("upPercent");
            String upTitle = intent.getStringExtra("upTitle");
            if(upPercent != null) {
                Log.d(TAG,"업로드 진행도 : " + upPercent + "%");
                textView_up_percent.setText(upPercent + "%");
                textView_up_title.setText(upTitle);
            }

        }
    };

    BroadcastReceiver uploadReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String uploadMessage = intent.getStringExtra("uploadMessage");
            if(uploadMessage != null)
            {
                Log.d(TAG,"****************************************************************************************");
                Log.d(TAG,"******************************* 업로드 종료 *******************************");
                Log.d(TAG,"****************************************************************************************");

                uploadLayout.setVisibility(View.GONE);

                //start Database transaction


                VeteranToast.makeToast(getApplicationContext(),"받은 값 :" + uploadMessage,Toast.LENGTH_SHORT).show();
            }

        }
    };

    public void mOnClick(View view){
        switch (view.getId()){
            //유투브 비디오 목록 가져오기 테스트
            case R.id.btn_getvideo:
                googleAPI();
                mGoogleApiClient.connect();
                loadUploadedVideos();
                break;

            case R.id.btn_upload:
                googleAPI();
                mGoogleApiClient.connect();
                //업로드
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("video/*");
                startActivityForResult(intent, RESULT_PICK_IMAGE_CROP);

        }
    }

    public void googleAPI(){
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .setAccountName(Auth.accountName)
                .addScope(Plus.SCOPE_PLUS_PROFILE)
                .build();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "구글 플레이 연결이 되었습니다.");

        if (!mGoogleApiClient.isConnected() || Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) == null) {
            Log.d(TAG, "onConnected 연결 실패");
        } else {
            Log.d(TAG, "onConnected 연결 성공");
            Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
            if (currentPerson.hasImage()) {

                Log.d(TAG, "이미지 경로는 : " + currentPerson.getImage().getUrl());

            }
            if (currentPerson.hasDisplayName()) {
                Log.d(TAG,"디스플레이 이름 : "+ currentPerson.getDisplayName());
                Log.d(TAG, "디스플레이 아이디는 : " + currentPerson.getId());
            }

        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "구글 계정 Connection Suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "연결 에러 " + connectionResult);
        Log.i(TAG, "Connection failed. Error: " + connectionResult.getErrorCode());
        if (connectionResult.hasResolution()) {

            Log.e(TAG,
                    String.format(
                            "Connection to Play Services Failed, error: %d, reason: %s",
                            connectionResult.getErrorCode(),
                            connectionResult.toString()));
            try {
                connectionResult.startResolutionForResult(this, 0);
            } catch (IntentSender.SendIntentException e) {
                Log.e(TAG, e.toString(), e);
            }
        }else{
            Log.d(TAG,"이미 로그인 중입니다");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode == Activity.RESULT_OK) {
                    Log.d(TAG,"Google Play Services User chose......");
                    mGoogleApiClient.connect();
                } else {
                    VeteranToast.makeToast(getApplicationContext(),"업로드를 하시려면 구글 계정이 필요합니다",Toast.LENGTH_LONG).show();
                }
                break;

            //업로드할 비디오를 선택한후에.... 창으로 다시 돌아온다
            case RESULT_PICK_IMAGE_CROP:
                if (resultCode == RESULT_OK) {
                    mFileURI = data.getData();

                    if (mFileURI != null) {
                        Intent uploadIntent = new Intent(this, UploadService.class);

                        uploadIntent.setData(mFileURI);
                        uploadIntent.putExtra("filename",getName(mFileURI));
                        uploadIntent.putExtra("category","카테고리");
                        /************
                         * 업로드할 비디오에 대한 상세 값들 셋팅
                         * *************/
                        uploadLayout.setVisibility(View.VISIBLE);
                        startService(uploadIntent);
                    }

                }
                break;
            case REQUEST_AUTHORIZATION:
                //재인증 필요
                if (resultCode != Activity.RESULT_OK) {
                    chooseAccount();
                }
                break;
            case REQUEST_ACCOUNT_PICKER:
                if (resultCode == Activity.RESULT_OK && data != null
                        && data.getExtras() != null) {
                    String accountName = data.getExtras().getString(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        Auth.accountName = accountName;
                        credential.setSelectedAccountName(accountName);
                    }
                }
                break;
        }
    }


    private void chooseAccount() {
        startActivityForResult(credential.newChooseAccountIntent(),
                REQUEST_ACCOUNT_PICKER);
    }

    /*****************************************************************
    /* 유투부 동영상 목록 가져오기 API 테스트용
    *****************************************************************/
    private void loadUploadedVideos() {

        setProgressBarIndeterminateVisibility(true);

        new AsyncTask<Void, Void, List<VideoData>>() {

            @Override
            protected List<VideoData> doInBackground(Void... voids) {

                Log.d(TAG,"데이터 가져오기 진입");

                YouTube youtube = new YouTube.Builder(transport, jsonFactory,
                        credential).setApplicationName(Constants.APP_NAME)
                        .build();

                try {
                    ChannelListResponse clr = youtube.channels()
                            .list("contentDetails").setMine(true).execute();

                    List<Channel> channelsList =  clr.getItems();

                    // Get the user's uploads playlist's id from channel list
                    // response
                    String uploadsPlaylistId = clr.getItems().get(0)
                            .getContentDetails().getRelatedPlaylists()
                            .getUploads();

                    List<VideoData> videos = new ArrayList<VideoData>();


                    // Get videos from user's upload playlist with a playlist
                    // items list request
                    PlaylistItemListResponse pilr = youtube.playlistItems()
                            .list("id,contentDetails")
                            .setPlaylistId(uploadsPlaylistId)
                            .setMaxResults(20l).execute();

                    List<String> videoIds = new ArrayList<String>();

                    // Iterate over playlist item list response to get uploaded
                    // videos' ids.
                    for (PlaylistItem item : pilr.getItems()) {
                        videoIds.add(item.getContentDetails().getVideoId());
                    }

                    // Get details of uploaded videos with a videos list
                    // request.
                    VideoListResponse vlr = youtube.videos()
                            .list("id,snippet,status")
                            .setId(TextUtils.join(",", videoIds)).execute();

                    // Add only the public videos to the local videos list.
                    for (Video video : vlr.getItems()) {
                        if ("public".equals(video.getStatus()
                                .getPrivacyStatus())) {
                            VideoData videoData = new VideoData();
                            videoData.setVideo(video);
                            videos.add(videoData);

                            Log.d(TAG,"비디오 목록 은 : " + videoData.getTitle());

                        }
                    }

                    Log.d(TAG,"가져오기 성공");

                    return videos;

                } catch (final GooglePlayServicesAvailabilityIOException availabilityException) {

                    Log.d(TAG,"GooglePlayServicesAvailabilityIOException : " + availabilityException.getMessage());
                    availabilityException.printStackTrace();
                } catch (UserRecoverableAuthIOException e) {
                    //구글 신규 아이디일경우 에러가 발생한다 다시 재인증 필요
                    startActivityForResult(e.getIntent(), REQUEST_AUTHORIZATION);
                } catch (IOException e) {
                    Log.d(TAG,"IOException : " + e.getMessage());
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(List<VideoData> videos) {
                setProgressBarIndeterminateVisibility(false);

                if (videos == null) {
                    return;
                }
            }
        }.execute((Void) null);
    }


    class Async extends AsyncTask<Void, Void, Void> {

        Context credential = null;

        public Async(Context credential) {
            this.credential = credential;
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.d(TAG,"구글 토큰 가져오기가 실행이 되었습니다");
            getAccessToken(credential);
            return null;
        }

    }

    //구글 토큰값 확인해보기
    public void getAccessToken(Context mContext) {

        try {

            //String token = credential.getToken();
            //Log.d(TAG, "구글 토큰값은 : "+token);

        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (broadcastReceiver == null)
            broadcastReceiver = new UploadBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(
                REQUEST_AUTHORIZATION_INTENT);
        LocalBroadcastManager.getInstance(this).registerReceiver(
                broadcastReceiver, intentFilter);
    }

    private class UploadBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(REQUEST_AUTHORIZATION_INTENT)) {
                Log.d(TAG, "Request auth received - executing the intent");
                Intent toRun = intent
                        .getParcelableExtra(REQUEST_AUTHORIZATION_INTENT_PARAM);
                startActivityForResult(toRun, REQUEST_AUTHORIZATION);
            }
        }
    }


    //선택한 Uri 파일명
    private String getName(Uri uri)
    {
        String[] projection = { MediaStore.Images.ImageColumns.DISPLAY_NAME };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DISPLAY_NAME);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }



}
