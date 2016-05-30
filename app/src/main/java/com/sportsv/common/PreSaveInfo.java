package com.sportsv.common;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.sportsv.vo.User;

/**
 * Created by sungbo on 2016-05-27.
 */
public class PreSaveInfo {

    private Activity activity;

    // constructor
    public PreSaveInfo(Activity activity) {
        this.activity = activity;
    }


    public void saveUser(User userProfileVo){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sp.edit();

        editor.putInt("uid", userProfileVo.getUid());
        editor.putString("useremail", userProfileVo.getUseremail());
        editor.putString("username", userProfileVo.getUsername());
        editor.putString("snsid", userProfileVo.getSnsid());
        editor.putString("snstype", userProfileVo.getSnstype());
        editor.putString("phone", userProfileVo.getPhone());
        editor.putString("password", userProfileVo.getPassword());
        editor.putString("profileImgUrl", userProfileVo.getProfileimgurl());

        editor.apply();
    }

    public User getUserProfile() {

        User userProfileVo = new User();

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);

        userProfileVo.setUid(sp.getInt("uid", 0));
        userProfileVo.setUseremail(sp.getString("useremail", null));
        userProfileVo.setUsername(sp.getString("username", null));
        userProfileVo.setSnsid(sp.getString("snsid", null));
        userProfileVo.setSnsname(sp.getString("snsname", null));
        userProfileVo.setSnstype(sp.getString("snstype", null));
        userProfileVo.setPhone(sp.getString("phone", null));
        userProfileVo.setPassword(sp.getString("password", null));
        userProfileVo.setProfileimgurl(sp.getString("profileImgUrl", null));


        return userProfileVo;

    }


    public void clearStore() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }

}
