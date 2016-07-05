package com.sportsv;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AppJoinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_join);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_user)
    public void btn_user(){
        Intent login_intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(login_intent);
    }

    @OnClick(R.id.btn_ins)
    public void btn_ins(){
        Intent login_intent = new Intent(getApplicationContext(),InsLoginActivity.class);
        startActivity(login_intent);
    }
}
