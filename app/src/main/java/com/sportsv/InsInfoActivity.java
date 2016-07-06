package com.sportsv;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sportsv.common.Common;
import com.sportsv.common.Compare;
import com.sportsv.common.PrefUtil;
import com.sportsv.common.SettingActivity;
import com.sportsv.dao.InstructorService;
import com.sportsv.retropit.ServiceGenerator;
import com.sportsv.serverservice.InsFileUploadService;
import com.sportsv.vo.Instructor;
import com.sportsv.vo.InstructorPointHistory;
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

/***
 *
 * 강사정보를 관리하는 페이지
 *
 */

public class InsInfoActivity extends AppCompatActivity {

    private static final String TAG = "InsInfoActivity";

    @Bind(R.id.imageView_ins)
    ImageView imageView;

    @Bind(R.id.tx_videopoint)
    TextView tx_videopoint;

    @Bind(R.id.tx_wordpoint)
    TextView tx_wordpoint;

    private PrefUtil prefUtil;
    private Instructor instructor;
    //private InstructorPointHistory pointHistory;

    //파일 업로드 관련
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_IMAGE = 2;

    private Uri mImageCaptureUri;
    private String absoultePath;
    private String RealFilePath;
    private String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_insinfo_layout);
        getSupportActionBar().setTitle("강사정보");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
        prefUtil = new PrefUtil(this);
        instructor = prefUtil.getIns();

        if(!Compare.isEmpty(instructor.getProfileimgurl())) {
            Glide.with(this)
                    .load(instructor.getProfileimgurl())
                    .into(imageView);
        }
        Log.d(TAG," 강사 정보는  : " + instructor.toString());
        getInsPointSetup();

    }

    //이미지 버튼 클릭시 강사사진 업로드
    @OnClick(R.id.imageView_ins)
    public void uploadImage(){
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


    @OnClick(R.id.btn_ins_logout)
    public void logOut(){
        prefUtil.clearPrefereance();
        VeteranToast.makeToast(getApplicationContext(), "로그아웃 합니다", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.ins_setting)
    public void setting(){
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
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

    //카메라를 실행하거나 갤러리에서 이미지를 선택 후 Action


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
                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/SmartWheel/"+System.currentTimeMillis()+".jpg";

                Log.d(TAG,"파일 경로는 : " + filePath);

                RealFilePath = filePath;
                fileName    = System.currentTimeMillis()+".jpg";

                if(extras != null){
                    Bitmap photo = extras.getParcelable("data");
                    imageView.setImageBitmap(photo);
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
    //파일을 업로드하기 위한 Spring RestTemplate Http Network
    private void storeCropImage(Bitmap bitmap,String filePath){

        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/SmartWheel/";
        File directory_SmartWheel = new File(dirPath);

        if(!directory_SmartWheel.exists())
            directory_SmartWheel.mkdir();

        //서버에 파일을 업로드 합니다
        String profileimgurl = Common.SERVER_INS_IMGFILEADRESS + fileName;
        String strInsId = String.valueOf(instructor.getInstructorid());

        //강사 아이디
        //확장자를 포함한 파일명
        //확장자를 포함한 파일의 위치+파일명

        new InsFileUploadService(strInsId,fileName,RealFilePath,instructor,this).execute();

        //유저사진을 쉐어퍼런스에 저장해준다(업데이트)
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor pre = sp.edit();
        pre.putString("ins_profileimgurl", profileimgurl);

        Log.d(TAG,"서버 파일 저장 경로는 : " + RealFilePath);

        pre.commit();

        File copyFile = new File(filePath);
        BufferedOutputStream out = null;

        try{
            copyFile.createNewFile();
            out = new BufferedOutputStream(new FileOutputStream(copyFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,out);

            //sendBrodcast를 통해 Crop된 사진을 앨범에 보이도록 갱신한다
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,Uri.fromFile(copyFile)));

            out.flush();
            out.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getInsPointSetup(){
        final ProgressDialog dialog;
        dialog = ProgressDialog.show(this, "","강사의 포인트 정책을 가져옵니다", true);
        dialog.show();

        InstructorService instructorService = ServiceGenerator.createService(InstructorService.class,instructor);
        final Call<InstructorPointHistory> call = instructorService.getPointHis(instructor.getInstructorid());
        call.enqueue(new Callback<InstructorPointHistory>() {
            @Override
            public void onResponse(Call<InstructorPointHistory> call, Response<InstructorPointHistory> response) {
                if(response.isSuccessful()){
                    InstructorPointHistory history = response.body();
                    Log.d(TAG,"강사의 포인트 정책 : " + history.toString());

                    tx_videopoint.setText(String.valueOf(history.getVideopoint()));
                    tx_wordpoint.setText(String.valueOf(history.getWordpoint()));

                    dialog.dismiss();
                }else{
                    dialog.dismiss();
                    Log.d(TAG,"포인트 정책을 가져오는 중 에러발생");
                }
            }

            @Override
            public void onFailure(Call<InstructorPointHistory> call, Throwable t) {
                dialog.dismiss();
                t.printStackTrace();
            }
        });
    }

}
