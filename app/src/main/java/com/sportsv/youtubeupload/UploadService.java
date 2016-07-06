package com.sportsv.youtubeupload;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeScopes;
import com.google.common.collect.Lists;
import com.sportsv.common.Auth;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by sungbo on 2016-06-13.
 */
public class UploadService extends IntentService {


    private static long mStartTime;
    final HttpTransport transport = AndroidHttp.newCompatibleTransport();
    final JsonFactory jsonFactory = new GsonFactory();
    GoogleAccountCredential credential;

    //upload 관련
    private static final int PROCESSING_TIMEOUT_SEC = 60 * 20; // 20 minutes
    private static final int PROCESSING_POLL_INTERVAL_SEC = 60;

    private int mUploadAttemptCount;
    private static final int MAX_RETRY = 3;
    private static final int UPLOAD_REATTEMPT_DELAY_SEC = 60;
    private static final String TAG = "UploadService";
    private String filename;
    private String category;

    private YoutubeUploadVo youtubeUploadVo;

    public UploadService() {
        super("UploadService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "IntentService onCreate  ========================================");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "IntentService onStartCommand  ========================================");

        filename = intent.getStringExtra("filename");
        category = intent.getStringExtra("category");

        Log.d(TAG,"파일 명은"+filename);

        youtubeUploadVo = new YoutubeUploadVo();

        youtubeUploadVo.setRealFileName(filename);

        return super.onStartCommand(intent, flags, startId);
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "IntentService onHandleIntent ========================================");



        Uri fileUri = intent.getData();
        String chosenAccountName = Auth.accountName;

        Log.d(TAG, "서비스 이벤트 onHandleIntent 아이디는 : " + chosenAccountName);

        credential = GoogleAccountCredential.usingOAuth2(getApplicationContext(), Lists.newArrayList(Auth.SCOPES));
        credential = GoogleAccountCredential.usingOAuth2(getApplicationContext(), YouTubeScopes.all());
        credential.setSelectedAccountName(chosenAccountName);
        credential.setBackOff(new ExponentialBackOff());

        String appName = "몸 싸커 업로드";

        Log.d(TAG, "====================업로드 Intent 시작합니다.");
        final YouTube youtube =  new YouTube.Builder(transport, jsonFactory, credential).setApplicationName(appName).build();
        String videoId = tryUpload(fileUri, youtube);
        Log.d(TAG, "====================업로드 종료 업로드");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "IntentService onDestroy  ========================================");
        //Toast.makeText(this, "Intent 서비스 종료", Toast.LENGTH_SHORT).show();

        final Intent intent = new Intent("uploadReceiver");
        final LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        intent.putExtra("uploadMessage","업로드가 종료 되었습니다");

        /*알림 메세지
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("업로드 알림")
                .setContentText("선택하신 파일이 업로드 되었습다")
                .setColor(Color.BLUE) //빽그라운드 컬러
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                ;

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 *//* ID of notification *//*, notificationBuilder.build());
        */

        broadcastManager.sendBroadcast(intent);

        Log.d(TAG, "업로드가 완료 되었습니다. 메인 페이지 브로드 캐스트 리시버를 호출합니다");
    }

    private String tryUpload(Uri mFileUri, YouTube youtube) {

        long fileSize;
        InputStream fileInputStream = null;
        String videoId = null;
        try {

            fileSize = getContentResolver().openFileDescriptor(mFileUri, "r").getStatSize();
            fileInputStream = getContentResolver().openInputStream(mFileUri);
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(mFileUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();


            videoId = ResumableUpload.upload(youtube, fileInputStream, fileSize, mFileUri, cursor.getString(column_index), getApplicationContext(),youtubeUploadVo);


        } catch (FileNotFoundException e) {
            Log.e(getApplicationContext().toString(), e.getMessage());
        } finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {

            }
        }
        return videoId;
    }

}

