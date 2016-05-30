package com.sportsv.vo;

/**
 * Created by sungbo on 2016-05-26.
 */
public class User {

    private int uid;
    private String useremail;
    private String username;
    private String snsid;
    private String snsname;
    private String snstype;
    private String phone;
    private String password;
    private String profileimgurl;
    private String googleemail;

    public User() {
    }

    public User(String useremail) {
        this.useremail = useremail;
    }

    public User(int uid, String useremail, String username, String snsid, String snsname, String snstype, String phone, String password, String profileimgurl, String googleemail) {
        this.uid = uid;
        this.useremail = useremail;
        this.username = username;
        this.snsid = snsid;
        this.snsname = snsname;
        this.snstype = snstype;
        this.phone = phone;
        this.password = password;
        this.profileimgurl = profileimgurl;
        this.googleemail = googleemail;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getSnsname() {
        return snsname;
    }

    public void setSnsname(String snsname) {
        this.snsname = snsname;
    }

    public String getSnstype() {
        return snstype;
    }

    public void setSnstype(String snstype) {
        this.snstype = snstype;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfileimgurl() {
        return profileimgurl;
    }

    public void setProfileimgurl(String profileimgurl) {
        this.profileimgurl = profileimgurl;
    }

    public String getSnsid() {
        return snsid;
    }

    public void setSnsid(String snsid) {
        this.snsid = snsid;
    }

    public String getGoogleemail() {
        return googleemail;
    }

    public void setGoogleemail(String googleemail) {
        this.googleemail = googleemail;
    }
}
