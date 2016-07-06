package com.sportsv.youtubeupload;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoSnippet;
import com.google.api.services.youtube.model.VideoStatus;
import com.sportsv.common.Constants;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by sungbo on 2016-06-13.
 */
public class ResumableUpload {

    private static String VIDEO_FILE_FORMAT = "video/*";
    private static final String SUCCEEDED = "succeeded";
    private static final String TAG = "ResumableUpload";
    private static int UPLOAD_NOTIFICATION_ID = 1001;
    private static int PLAYBACK_NOTIFICATION_ID = 1002;

    public static String upload(YouTube youtube, final InputStream fileInputStream,
                                final long fileSize, final Uri mFileUri, final String path, final Context context, final YoutubeUploadVo uploadVo) {
        String videoId = null;
        try {
            // Add extra information to the video before uploading.
            Video videoObjectDefiningMetadata = new Video();
      /*
       * Set the video to public, so it is available to everyone (what most people want). This is
       * actually the default, but I wanted you to see what it looked like in case you need to set
       * it to "unlisted" or "private" via API.
       */
            VideoStatus status = new VideoStatus();
            status.setPrivacyStatus("public");
            videoObjectDefiningMetadata.setStatus(status);

            // We set a majority of the metadata with the VideoSnippet object.
            VideoSnippet snippet = new VideoSnippet();

      /*
       * The Calendar instance is used to create a unique name and description for test purposes, so
       * you can see multiple files being uploaded. You will want to remove this from your project
       * and use your own standard names.
       */
            //String format = new String("yyyyMMddHHmm");
            //SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.KOREA);
            //cal.getTime()

            Calendar cal = Calendar.getInstance();
            snippet.setTitle(uploadVo.getVideoSubject());
            snippet.setDescription(uploadVo.getDescription() + "  " + uploadVo.getComDisp());

            // Set your keywords.
            snippet.setTags(Arrays.asList(Constants.DEFAULT_KEYWORD,
                    Upload.generateKeywordFromPlaylistId(Constants.UPLOAD_PLAYLIST+" 유저의 채널을 가져오면 좋다...")));

            // Set completed snippet to the video object.
            videoObjectDefiningMetadata.setSnippet(snippet);

            InputStreamContent mediaContent =
                    new InputStreamContent(VIDEO_FILE_FORMAT, new BufferedInputStream(fileInputStream));
            mediaContent.setLength(fileSize);

      /*
       * The upload command includes: 1. Information we want returned after file is successfully
       * uploaded. 2. Metadata we want associated with the uploaded video. 3. Video file itself.
       */
            YouTube.Videos.Insert videoInsert =
                    youtube.videos().insert("snippet,statistics,status", videoObjectDefiningMetadata,
                            mediaContent);

            // Set the upload type and add event listener.
            MediaHttpUploader uploader = videoInsert.getMediaHttpUploader();

      /*
       * Sets whether direct media upload is enabled or disabled. True = whole media content is
       * uploaded in a single request. False (default) = resumable media upload protocol to upload
       * in data chunks.
       */
            uploader.setDirectUploadEnabled(false);

            MediaHttpUploaderProgressListener progressListener = new MediaHttpUploaderProgressListener() {
                Intent intent = new Intent("uploadVideo");
                LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(context);

                public void progressChanged(MediaHttpUploader uploader) throws IOException {
                    switch (uploader.getUploadState()) {
                        case INITIATION_STARTED:
                            Log.d(TAG,"INITIATION_STARTED  ========================");
                            intent.putExtra("upTitle",uploadVo.getRealFileName() + " 영상 업로드 준비 및 서버 확인중");
                            broadcastManager.sendBroadcast(intent);
                            break;
                        case INITIATION_COMPLETE:
                            Log.d(TAG,"INITIATION_COMPLETE ========================");
                            break;
                        case MEDIA_IN_PROGRESS:
                            Log.d(TAG,"MEDIA_IN_PROGRESS 1 ========================" + uploader.getProgress() * 100 + "%");

                            int integer = (int) (uploader.getProgress() * 100);
                            StartUploadActivity.progressBar.setProgress(integer);
                            intent.putExtra("upPercent",String.valueOf(integer));

                            if(integer > 90){
                                intent.putExtra("upTitle",uploadVo.getRealFileName() + " 영상 업로드가 완료 되었습니다");
                            }else{
                                intent.putExtra("upTitle", uploadVo.getRealFileName()+" 영상을 업로드 중입니다");
                            }
                            broadcastManager.sendBroadcast(intent);
                            break;
                        case MEDIA_COMPLETE:
                            Log.d(TAG,"MEDIA_COMPLETE ========================");
                            broadcastManager.sendBroadcast(intent);
                        case NOT_STARTED:
                            Log.d(TAG,"NOT_STARTED ========================");
                            break;
                    }
                }
            };

            uploader.setProgressListener(progressListener);

            // Execute upload.
            Video returnedVideo = videoInsert.execute();

            Log.d(TAG, "미션 업로드가 완료 되었습니다");
            videoId = returnedVideo.getId();
            Log.d(TAG, String.format("videoId = [%s]", videoId));

        } catch (final GooglePlayServicesAvailabilityIOException availabilityException) {
            Log.e(TAG, "GooglePlayServicesAvailabilityIOException", availabilityException);
            //notifyFailedUpload(context, context.getString(R.string.cant_access_play), notifyManager, builder);
        } catch (UserRecoverableAuthIOException userRecoverableException) {
            Log.i(TAG, String.format("UserRecoverableAuthIOException: %s",
                    userRecoverableException.getMessage()));
            //userRecoverableException.printStackTrace();
            //최초가입일 경우 구글 유투브 업로드 설정이 안되어 있기 떄문에 다시 재 로그인한다
            requestAuth(context, userRecoverableException);
        } catch (IOException e) {
            Log.e(TAG, "IOException", e);
            //구글계정은 있지만 유투브 계정이 없기 떄문에 에러가 난다면...웹뷰로 유투브 인증을 해줘야 한다.
        }
        return videoId;
    }


    private static void requestAuth(Context context,
                                    UserRecoverableAuthIOException userRecoverableException) {
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(context);

        Intent authIntent = userRecoverableException.getIntent();

        Intent runReqAuthIntent = new Intent(StartUploadActivity.REQUEST_AUTHORIZATION_INTENT);
        runReqAuthIntent.putExtra(StartUploadActivity.REQUEST_AUTHORIZATION_INTENT_PARAM, authIntent);

        manager.sendBroadcast(runReqAuthIntent);
    }
}

