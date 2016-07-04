package com.sportsv.common;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.sportsv.vo.Instructor;
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

    public void saveIns(Instructor ins){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor pre = sp.edit();

        pre.putInt("uid", ins.getInstructorid());

        pre.commit();
    }

    public Instructor getIns(){
        Instructor instructor = new Instructor();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);

        instructor.setInstructorid(sp.getInt("instructorid", 0));

        return instructor;
    }

    /**********************************************************************************/

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
        pre.putInt("teamid", user.getTeamid());
        pre.putString("gmail", user.getGoogleemail());
        pre.putString("teampushflag", user.getTeampushflag());
        pre.putString("apppushflag", user.getApppushflag());
        pre.putString("password",user.getPassword());
        pre.putInt("commontokenid",user.getCommontokenid());

        pre.commit();
    }

    public User getUser(){

        User user = new User();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);

        user.setUid(sp.getInt("uid", 0));
        user.setUsername(sp.getString("username",null));
        user.setUseremail(sp.getString("email", null));
        user.setSnsname(sp.getString("snsname", null));
        user.setPhone(sp.getString("phone", null));
        user.setProfileimgurl(sp.getString("profileImgUrl", null));
        user.setSnstype(sp.getString("snstype", null));
        user.setSnsid(sp.getString("snsid", null));
        user.setLocation(sp.getInt("location", 0));
        user.setGoogleemail(sp.getString("gmail", null));
        user.setTeampushflag(sp.getString("teampushflag", null));
        user.setApppushflag(sp.getString("apppushflag", null));
        user.setTeamid(sp.getInt("teamid", 0));
        user.setPassword(sp.getString("password", null));
        user.setCommontokenid(sp.getInt("commontokenid", 0));
        return user;
    }

    public void saveFcmToken(String token){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor pre = sp.edit();
        pre.putString("fcmtoken",token);
        pre.commit();
    }

    public String getFcmToken(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        String preFcmToken = sp.getString("fcmtoken", null);
        return preFcmToken;
    }

}