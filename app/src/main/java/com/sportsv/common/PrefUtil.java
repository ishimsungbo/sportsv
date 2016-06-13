package com.sportsv.common;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.sportsv.vo.User;


public class PrefUtil {

    private Activity activity;

    // constructor
    public PrefUtil(Activity activity) {
        this.activity = activity;
    }

    public void clearPrefereance() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }

    public void saveUser(User user){

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor pre = sp.edit();

        pre.putInt("uid", user.getUid());
        pre.putString("username",user.getUsername());
        pre.putString("email", user.getUseremail());
        pre.putString("snsname", user.getUsername());
        pre.putString("phone", user.getPhone());
        pre.putString("profileImgUrl", user.getProfileimgurl());
        pre.putString("snstype", user.getSnstype());
        pre.putString("snsid", user.getSnsid());
        pre.putInt("location", user.getLocation());
        pre.putString("gmail", user.getGoogleemail());
        pre.putString("teampushflag", user.getTeampushflag());
        pre.putString("apppushflag", user.getApppushflag());

        pre.commit();
    }

    public User getUser(){

        User user = new User();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);

        user.setUid(sp.getInt("uid", -1));
        user.setUsername(sp.getString("username",null));
        user.setUseremail(sp.getString("email", null));
        user.setSnsname(sp.getString("snsname", null));
        user.setPhone(sp.getString("phone", null));
        user.setProfileimgurl(sp.getString("profileImgUrl", null));
        user.setSnstype(sp.getString("snstype", null));
        user.setSnsid(sp.getString("snsid", null));
        user.setLocation(sp.getInt("location", -1));
        user.setGoogleemail(sp.getString("gmail", null));
        user.setTeampushflag(sp.getString("teampushflag", null));
        user.setApppushflag(sp.getString("apppushflag", null));

        return user;
    }

}