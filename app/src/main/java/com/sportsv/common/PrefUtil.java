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

        pre.putInt("instructorid", ins.getInstructorid());
        pre.putString("ins_email",ins.getEmail());
        pre.putString("ins_name",ins.getName());
        pre.putString("ins_profileimgurl",ins.getProfileimgurl());
        pre.putString("ins_password",ins.getPassword());
        pre.putString("ins_profile",ins.getProfile());
        pre.putString("ins_description",ins.getDescription());
        pre.putString("ins_phone", ins.getPhone());
        pre.putInt("ins_location", ins.getLocation());
        pre.putString("ins_feedbackflag",ins.getFeedbackflag());
        pre.putString("appushflag",ins.getApppushflag());
        pre.putString("serialnumber",ins.getSerialnumber());
        pre.putInt("commontokenid",ins.getCommontokenid());
        pre.putInt("pointhistoryid",ins.getPointhistoryid());


        pre.commit();
    }

    public Instructor getIns(){
        Instructor instructor = new Instructor();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);

        instructor.setInstructorid(sp.getInt("instructorid", 0));
        instructor.setEmail(sp.getString("ins_email",null));
        instructor.setProfileimgurl(sp.getString("ins_profileimgurl",null));
        instructor.setName(sp.getString("ins_name",null));
        instructor.setPassword(sp.getString("ins_password",null));
        instructor.setProfile(sp.getString("ins_profile",null));
        instructor.setDescription(sp.getString("ins_description",null));
        instructor.setPhone(sp.getString("ins_phone",null));
        instructor.setLocation(sp.getInt("ins_location",0));
        instructor.setFeedbackflag(sp.getString("ins_feedbackflag",null));
        instructor.setApppushflag(sp.getString("appushflag",null));
        instructor.setSerialnumber(sp.getString("serialnumber",null));
        instructor.setCommontokenid(sp.getInt("commontokenid",0));
        instructor.setPointhistoryid(sp.getInt("pointhistoryid",0));

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

    public void uploadFlag(String upFlag){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor pre = sp.edit();
        pre.putString("uploadflag",upFlag);
        pre.commit();
    }
    public String getUploadFlag(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        String upFlag = sp.getString("uploadflag", null);
        return upFlag;
    }

}