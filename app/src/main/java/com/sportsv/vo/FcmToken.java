package com.sportsv.vo;

/**
 * Created by sungbo on 2016-06-30.
 * 유저 및 강사들의 FCM 토큰값 안드로이드 기기당 1개씩
 * 아래 VO를 활용해서 푸쉬서비스를 한다
 * DB테이블은 fcm_token
 */
public class FcmToken {

    private int commontokenid;
    private String fcmtoken;
    private int uid;
    private int instructorid;
    private String serialnumber;
    private String creationdate;
    private String updatedate;
    private String change_creationdate;
    private String change_updatedate;

    private String tranType;

    public FcmToken(){}

    public FcmToken(int commontokenid, String fcmtoken, int uid, int instructorid, String serialnumber, String creationdate, String updatedate, String change_creationdate, String change_updatedate, String tranType) {
        this.commontokenid = commontokenid;
        this.fcmtoken = fcmtoken;
        this.uid = uid;
        this.instructorid = instructorid;
        this.serialnumber = serialnumber;
        this.creationdate = creationdate;
        this.updatedate = updatedate;
        this.change_creationdate = change_creationdate;
        this.change_updatedate = change_updatedate;
        this.tranType = tranType;
    }

    public int getCommontokenid() {
        return commontokenid;
    }

    public void setCommontokenid(int commontokenid) {
        this.commontokenid = commontokenid;
    }

    public String getFcmtoken() {
        return fcmtoken;
    }

    public void setFcmtoken(String fcmtoken) {
        this.fcmtoken = fcmtoken;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getInstructorid() {
        return instructorid;
    }

    public void setInstructorid(int instructorid) {
        this.instructorid = instructorid;
    }

    public String getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(String creationdate) {
        this.creationdate = creationdate;
    }

    public String getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(String updatedate) {
        this.updatedate = updatedate;
    }

    public String getChange_creationdate() {
        return change_creationdate;
    }

    public void setChange_creationdate(String change_creationdate) {
        this.change_creationdate = change_creationdate;
    }

    public String getChange_updatedate() {
        return change_updatedate;
    }

    public void setChange_updatedate(String change_updatedate) {
        this.change_updatedate = change_updatedate;
    }

    public String getSerialnumber() {
        return serialnumber;
    }

    public void setSerialnumber(String serialnumber) {
        this.serialnumber = serialnumber;
    }

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    @Override
    public String toString() {
        return "FcmToken{" +
                "commontokenid=" + commontokenid +
                ", fcmtoken='" + fcmtoken + '\'' +
                ", uid=" + uid +
                ", instructorid=" + instructorid +
                ", serialnumber='" + serialnumber + '\'' +
                ", creationdate='" + creationdate + '\'' +
                ", updatedate='" + updatedate + '\'' +
                ", change_creationdate='" + change_creationdate + '\'' +
                ", change_updatedate='" + change_updatedate + '\'' +
                ", tranType='" + tranType + '\'' +
                '}';
    }
}
