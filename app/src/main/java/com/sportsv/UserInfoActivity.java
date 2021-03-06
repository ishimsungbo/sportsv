package com.sportsv;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.sportsv.common.Common;
import com.sportsv.common.Compare;
import com.sportsv.common.PrefUtil;
import com.sportsv.common.SettingActivity;
import com.sportsv.dao.PointService;
import com.sportsv.dbnetwork.PointQueryService;
import com.sportsv.dbnetwork.UserTRService;
import com.sportsv.retropit.ServiceGenerator;
import com.sportsv.team.TeamManageActivity;
import com.sportsv.vo.CpBalanceHeader;
import com.sportsv.vo.ServerResult;
import com.sportsv.vo.SpBalanceHeader;
import com.sportsv.vo.User;
import com.sportsv.widget.VeteranToast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



/**
 * Created by sungbo on 2016-06-03.
 * 유저 정보
 */
public class UserInfoActivity extends AppCompatActivity{

    private static final String TAG = "UserInfoActivity";
    private PrefUtil prefUtil;
    private User user;

    @Bind(R.id.user_image)
    ImageView user_image;

    @Bind(R.id.tx_selfpoint_amount)
    TextView tx_selfpoint_amount;

    @Bind(R.id.tx_cash_point_amount)
    TextView tx_cash_point_amount;

    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_IMAGE = 2;

    private Uri mImageCaptureUri;

    private String absoultePath;
    private String RealFilePath;
    private String fileName;

    private SpBalanceHeader spBalanceHeader;
    private CpBalanceHeader cpBalanceHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinfo_layout);

        getSupportActionBar().setTitle("유저정보");

        //뒤로가기 버튼
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //액션바 배경
        //getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.listhigh));
        //액션바 홈키
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.homeback);
        //apply ButterKnife
        ButterKnife.bind(this);

        prefUtil = new PrefUtil(this);
        user = prefUtil.getUser();

        if(!Compare.isEmpty(user.getProfileimgurl())) {
            Glide.with(UserInfoActivity.this)
                    .load(user.getProfileimgurl())
                    .into(user_image);
        }

        Log.d(TAG," 유저 값은 : " + user.toString());

    }


    @OnClick(R.id.logout)
    public void logout(){

        Log.d(TAG, "SNS 타입은 : " + user.getSnstype());

        if(user.getSnstype().equals("facebook")){
            Log.d(TAG,"페이스북 회원가입 유저 로그아웃을 합니다");

            //페이스북 토큰 및 세션 끈기
            FacebookSdk.sdkInitialize(getApplicationContext());
            LoginManager.getInstance().logOut();

        }else if(user.getSnstype().equals("kakao")){
            Log.d(TAG,"카카오 회원가입 유저 로그아웃을 합니다");
            onClickLogout();
        }else if(user.getSnstype().equals("app")){
            Log.d(TAG,"자체 회원가입 유저 로그아웃을 합니다");
        }

        prefUtil.clearPrefereance();
        VeteranToast.makeToast(getApplicationContext(), "로그아웃 합니다", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    //카카오 세션 끈기 메소드
    private void onClickLogout() {
        UserManagement.requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //홈버튼클릭시
        if (id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() start ===========================================================");

        spBalanceHeader = new SpBalanceHeader();
        cpBalanceHeader = new CpBalanceHeader();

        PointQueryService queryServiceSelf = new PointQueryService(TAG,this,spBalanceHeader,tx_selfpoint_amount,user);
        PointQueryService queryServiceCash = new PointQueryService(TAG,this,cpBalanceHeader,tx_cash_point_amount,user);

        queryServiceSelf.getSelfPoint();
        queryServiceCash.getCashPoint();
        Log.d(TAG, "onStart() log 포인트 정보를 가져옵니다");
        Log.d(TAG, "onStart() last ===========================================================");

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState() ===========================================================");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume ===========================================================");
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop ===========================================================");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy ===========================================================");
    }

    @OnClick(R.id.setting)
    public void setting(){
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != RESULT_OK){
            return;
        }

        switch (requestCode){
            case PICK_FROM_ALBUM:

                Log.d(TAG,"사진선택");
                mImageCaptureUri = data.getData();
                Intent intenti = new Intent("com.android.camera.action.CROP");
                intenti.setDataAndType(mImageCaptureUri, "image/*");

                //크롭할 이미지를 100*100 크기로 저장한다
                intenti.putExtra("outputX",100);
                intenti.putExtra("outputY",100);
                intenti.putExtra("aspectX",100); //크롭 박스의 x축 비율
                intenti.putExtra("aspectY",100);
                intenti.putExtra("scale",true);
                intenti.putExtra("return-data", true);
                startActivityForResult(intenti,CROP_FROM_IMAGE);

                break;

            case PICK_FROM_CAMERA:
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri, "image/*");

                //크롭할 이미지를 100*100 크기로 저장한다
                intent.putExtra("outputX",100);
                intent.putExtra("outputY",100);
                intent.putExtra("aspectX",100); //크롭 박스의 x축 비율
                intent.putExtra("aspectY",100);
                intent.putExtra("scale",true);
                intent.putExtra("return-data", true);
                startActivityForResult(intent,CROP_FROM_IMAGE);
                break;

            case CROP_FROM_IMAGE:
                if(resultCode != RESULT_OK){
                    return;
                }

                final Bundle extras = data.getExtras();

                //크롭된 이미지를 저장하기 위한 file 경로
                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+Common.IMAGE_MOM_PATH+System.currentTimeMillis()+".jpg";

                Log.d(TAG,"파일 경로는 : " + filePath);

                RealFilePath = filePath;
                fileName    = System.currentTimeMillis()+".jpg";

                if(extras != null){
                    Bitmap photo = extras.getParcelable("data");
                    user_image.setImageBitmap(photo);

                    storeCropImage(photo, filePath);
                    absoultePath = filePath;
                    break;
                }
                File file = new File(mImageCaptureUri.getPath());
                if(file.exists()){
                    file.delete();
                }

        }
    }

    public void imageOnClick(View view){

        DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                doTakeAlbumAction();
            }
        };

        DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                doTakePhotoAction();
            }
        };

        DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };

        new AlertDialog.Builder(this)
                .setTitle("업로드할 이미지 선택")
                .setPositiveButton("사진촬영", cameraListener)
                .setNeutralButton("앨범선택", albumListener)
                .setNegativeButton("취소", cancelListener)
                .show();
    }

    //앨범에서 이미지 가져오기
    public void doTakeAlbumAction(){
        //앨범호출
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    //카메라에서 사진촬영
    public void doTakePhotoAction(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String uri = "tmp_"+String.valueOf(System.currentTimeMillis())+".jpg";
        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),uri));

        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        startActivityForResult(intent,PICK_FROM_CAMERA);
    }

    private void storeCropImage(Bitmap bitmap,String filePath){

        Log.d(TAG,"이미지 잘라서 저장하기 시작 ============================");

        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath()+Common.IMAGE_MOM_PATH;
        File directory_SmartWheel = new File(dirPath);

        if(!directory_SmartWheel.exists())
            directory_SmartWheel.mkdir();

        //서버에 파일을 업로드 합니다
        String profileimgurl = Common.SERVER_USER_IMGFILEADRESS + fileName;
        String StrUid = String.valueOf(user.getUid());


        File copyFile = new File(filePath);
        BufferedOutputStream out = null;

        try{
            copyFile.createNewFile();
            out = new BufferedOutputStream(new FileOutputStream(copyFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,out);

            Log.d(TAG,"파일 갱신 할 정보는  : " + copyFile);

            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,Uri.fromFile(copyFile)));
            out.flush();
            out.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        //이미지 업로드
        Log.d(TAG,"User Upload End===================================================================");
        UserTRService userTRService = new UserTRService(this,user);
        userTRService.updateUserImage(StrUid,fileName,RealFilePath);

        //유저사진을 쉐어퍼런스에 저장해준다(업데이트)
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor pre = sp.edit();
        pre.putString("profileImgUrl", profileimgurl);
        pre.commit();
        Log.d(TAG,"User Upload End ===================================================================");

    }


    @OnClick(R.id.btn_daily_check)
    public void btn_daily_check(){

        PointService pointService = ServiceGenerator.createService(PointService.class,this,user);
        final Call<ServerResult> call = pointService.daliyCheck(String.valueOf(user.getUid()),"DALIY","KR");

        call.enqueue(new Callback<ServerResult>() {
            @Override
            public void onResponse(Call<ServerResult> call, Response<ServerResult> response) {

                if(response.isSuccessful()){

                    ServerResult result = response.body();

                    if(result.getResult().equals("exist")){
                        VeteranToast.makeToast(getApplicationContext(),"오늘 출석을 하셨습니다",0).show();
                        Log.d(TAG,"***************************");
                    }else{
                        Log.d(TAG,"===========================");
                        VeteranToast.makeToast(getApplicationContext(),"출석 포인트가 지급 되었습니다",0).show();
                        tx_selfpoint_amount.setText(result.getResult());
                    }

                }else{
                    VeteranToast.makeToast(getApplicationContext(),"출석 체크 값을 못가져왔습니다",0).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResult> call, Throwable t) {
                VeteranToast.makeToast(getApplicationContext(),"실패",0).show();
                t.printStackTrace();
            }
        });
    }

    @OnClick(R.id.btn_TeamManage)
    public void btn_TeamManage(){
        Intent intent = new Intent(this, TeamManageActivity.class);
        startActivity(intent);
    }

}
