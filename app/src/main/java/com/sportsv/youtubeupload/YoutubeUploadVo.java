package com.sportsv.youtubeupload;

/**
 * Created by sungbo on 2016-07-06.
 */
public class YoutubeUploadVo {

    private int misstionid;
    private String missionCategory;
    private String missionType;
    private String userMissionid;
    private String videoSubject;
    private String realFileName;
    private String description;
    private String comDisp;

    public YoutubeUploadVo(){}

    public YoutubeUploadVo(int misstionid, String missionCategory, String missionType, String userMissionid, String videoSubject, String realFileName, String description) {
        this.misstionid = misstionid;
        this.missionCategory = missionCategory;
        this.missionType = missionType;
        this.userMissionid = userMissionid;
        this.videoSubject = videoSubject;
        this.realFileName = realFileName;
        this.description = description;
    }

    public int getMisstionid() {
        return misstionid;
    }

    public void setMisstionid(int misstionid) {
        this.misstionid = misstionid;
    }

    public String getMissionCategory() {
        return missionCategory;
    }

    public void setMissionCategory(String missionCategory) {
        this.missionCategory = missionCategory;
    }

    public String getMissionType() {
        return missionType;
    }

    public void setMissionType(String missionType) {
        this.missionType = missionType;
    }

    public String getUserMissionid() {
        return userMissionid;
    }

    public void setUserMissionid(String userMissionid) {
        this.userMissionid = userMissionid;
    }

    public String getVideoSubject() {
        return videoSubject;
    }

    public void setVideoSubject(String videoSubject) {
        this.videoSubject = videoSubject;
    }

    public String getRealFileName() {
        return realFileName;
    }

    public void setRealFileName(String realFileName) {
        this.realFileName = realFileName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComDisp() {
        return comDisp;
    }

    public void setComDisp(String missionCategory,String missionType) {
        this.comDisp = "[" + missionCategory + " : " + missionType + "]";
    }

    @Override
    public String toString() {
        return "YoutubeUploadVo{" +
                "misstionid=" + misstionid +
                ", missionCategory='" + missionCategory + '\'' +
                ", missionType='" + missionType + '\'' +
                ", userMissionid='" + userMissionid + '\'' +
                ", videoSubject='" + videoSubject + '\'' +
                ", realFileName='" + realFileName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
