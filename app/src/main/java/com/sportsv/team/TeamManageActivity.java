package com.sportsv.team;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.sportsv.R;
import com.sportsv.fragment.InsSerchFragment;
import com.sportsv.vo.User;

/***
 *
 *
 *
 *
 *
 *
 */


public class TeamManageActivity extends AppCompatActivity {
    private static final String TAG = "TeamManageActivity";
    InsSerchFragment insSerchFragment;

    User user = new User();

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_manage);

        insSerchFragment = new InsSerchFragment(getApplicationContext());

        user.setUsername("저는 액티비티 이름 입니다");

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction tc = fm.beginTransaction();

        String name = "제 이름은 짱구 입니다";

        InsSerchFragment ins = InsSerchFragment.newInstance(name);
        tc.add(R.id.list_fragment,ins,"값은");
        tc.commit();

        Log.d(TAG,"onCreate================================");
        Log.d(TAG,"넘기는 값은 : " + name);
    }


}
