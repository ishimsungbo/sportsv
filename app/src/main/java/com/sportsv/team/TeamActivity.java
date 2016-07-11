package com.sportsv.team;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.sportsv.InsInfoActivity;
import com.sportsv.R;
import com.sportsv.common.Common;
import com.sportsv.common.Compare;
import com.sportsv.common.PrefUtil;
import com.sportsv.dbnetwork.TeamTRService;
import com.sportsv.vo.Instructor;
import com.sportsv.vo.Team;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TeamActivity extends AppCompatActivity {
    private static final String TAG = "TeamActivity";

    private PrefUtil prefUtil;
    private Instructor instructor;

    TextInputLayout layout_teamName;
    TextInputLayout layout_teamDisp;

    @Bind(R.id.edit_teamName)
    EditText edit_teamName;

    @Bind(R.id.edit_teamDisp)
    EditText edit_teamDisp;

    @Bind(R.id.im_teamImage)
    ImageView im_teamImage;

    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_IMAGE = 2;

    private Uri mImageCaptureUri;

    private String absoultePath;
    private String RealFilePath;
    private String fileName;
    private String TEAMTRFLAG = "NEW";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        getSupportActionBar().setTitle("팀만들기");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
        prefUtil = new PrefUtil(this);
        instructor = prefUtil.getIns();

        layout_teamName = (TextInputLayout) findViewById(R.id.layout_teamName);
        layout_teamDisp = (TextInputLayout) findViewById(R.id.layout_teamDisp);

        LocalBroadcastManager.getInstance(this).registerReceiver(createTeam, new IntentFilter("createTeam"));

    }

    BroadcastReceiver createTeam = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG,"팀 생성 정보가 서버에서 왔음....");
            intent = new Intent(TeamActivity.this, InsInfoActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        }
    };

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

    @OnClick(R.id.btn_TeamCreate)
    public void teamCreate(){
        if(Compare.isEmpty(edit_teamName.getText().toString())) {
            layout_teamName.setError("팀명을 입력해주세요");
            return;
        }else if (Compare.isEmpty(edit_teamName.getText().toString())) {
            layout_teamDisp.setError("팀 설명을 입력해주세요");
            return;
        }else{

            //필드 초기화
            layout_teamName.setError(null);
            layout_teamDisp.setError(null);

            //이미지 업로드 및 팀 정보 생성
            Team team = new Team();
            team.setName(edit_teamName.getText().toString());
            team.setDescription(edit_teamDisp.getText().toString());

            TeamTRService teamTRService = new TeamTRService(this,instructor);
            //팀을 생성하고 내부에서 팀정보를 preutil에 저장한다
            teamTRService.saveTeam(fileName,RealFilePath,TEAMTRFLAG,team);

        }
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

        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/SmartWheel/";
        File directory_SmartWheel = new File(dirPath);

        if(!directory_SmartWheel.exists())
            directory_SmartWheel.mkdir();

        //서버에 파일을 업로드 합니다
        String profileimgurl = Common.SERVER_TEAM_IMGFILEADRESS + fileName;

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
                    im_teamImage.setImageBitmap(photo);

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


    public void teamImageBtn(View v){
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

}
