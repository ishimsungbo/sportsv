package com.sportsv.vo;


public class Mission {

    private int missionid;
    private int categoryid;
    private int typeid;
    private int sequence;
    private String missionname;
    private String description;
    private String precon;
    private String videoaddr;
    private String fullyoutubeaddr;
    private String youtubeaddr;
    private String enabled;
    private String feetype;
    private int grade;          //미션업로드점수
    private int passgrade;      //미션클리어점수
    private String creationdate;
    private String updatedate;
    private String change_creationdate;
    private String change_updatedate;

    public Mission(){}

    public Mission(int missionid, int categoryid, int typeid, int sequence, String missionname, String description, String precon, String videoaddr, String fullyoutubeaddr, String youtubeaddr, String enabled, String feetype, int grade, int passgrade, String creationdate, String updatedate, String change_creationdate, String change_updatedate) {
        this.missionid = missionid;
        this.categoryid = categoryid;
        this.typeid = typeid;
        this.sequence = sequence;
        this.missionname = missionname;
        this.description = description;
        this.precon = precon;
        this.videoaddr = videoaddr;
        this.fullyoutubeaddr = fullyoutubeaddr;
        this.youtubeaddr = youtubeaddr;
        this.enabled = enabled;
        this.feetype = feetype;
        this.grade = grade;
        this.passgrade = passgrade;
        this.creationdate = creationdate;
        this.updatedate = updatedate;
        this.change_creationdate = change_creationdate;
        this.change_updatedate = change_updatedate;
    }

    @Override
    public String toString() {
        return "Mission{" +
                "missionid=" + missionid +
                ", categoryid=" + categoryid +
                ", typeid=" + typeid +
                ", sequence=" + sequence +
                ", missionname='" + missionname + '\'' +
                ", description='" + description + '\'' +
                ", precon='" + precon + '\'' +
                ", videoaddr='" + videoaddr + '\'' +
                ", fullyoutubeaddr='" + fullyoutubeaddr + '\'' +
                ", youtubeaddr='" + youtubeaddr + '\'' +
                ", enabled='" + enabled + '\'' +
                ", feetype='" + feetype + '\'' +
                ", grade=" + grade +
                ", passgrade=" + passgrade +
                ", creationdate='" + creationdate + '\'' +
                ", updatedate='" + updatedate + '\'' +
                ", change_creationdate='" + change_creationdate + '\'' +
                ", change_updatedate='" + change_updatedate + '\'' +
                '}';
    }

    public int getMissionid() {
        return missionid;
    }

    public void setMissionid(int missionid) {
        this.missionid = missionid;
    }

    public int getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }

    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getMissionname() {
        return missionname;
    }

    public void setMissionname(String missionname) {
        this.missionname = missionname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrecon() {
        return precon;
    }

    public void setPrecon(String precon) {
        this.precon = precon;
    }

    public String getVideoaddr() {
        return videoaddr;
    }

    public void setVideoaddr(String videoaddr) {
        this.videoaddr = videoaddr;
    }

    public String getFullyoutubeaddr() {
        return fullyoutubeaddr;
    }

    public void setFullyoutubeaddr(String fullyoutubeaddr) {
        this.fullyoutubeaddr = fullyoutubeaddr;
    }

    public String getYoutubeaddr() {
        return youtubeaddr;
    }

    public void setYoutubeaddr(String youtubeaddr) {
        this.youtubeaddr = youtubeaddr;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getFeetype() {
        return feetype;
    }

    public void setFeetype(String feetype) {
        this.feetype = feetype;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getPassgrade() {
        return passgrade;
    }

    public void setPassgrade(int passgrade) {
        this.passgrade = passgrade;
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
}
