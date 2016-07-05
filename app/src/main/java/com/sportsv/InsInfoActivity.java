package com.sportsv;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.sportsv.common.PrefUtil;
import com.sportsv.common.SettingActivity;
import com.sportsv.widget.VeteranToast;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class InsInfoActivity extends AppCompatActivity {

    private static final String TAG = "InsInfoActivity";
    private PrefUtil prefUtil;
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_IMAGE = 2;

    private Uri mImageCaptureUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_insinfo_layout);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle("강사정보");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        prefUtil = new PrefUtil(this);
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
}
