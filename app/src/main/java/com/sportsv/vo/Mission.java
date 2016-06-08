package com.sportsv.vo;

import java.util.Date;

/**
 * Created by sungbo on 2016-05-31.
 */
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
    private Date creationdate;
    private Date lastupdate;

    public Mission() {}

    public Mission(int missionid, int categoryid, int typeid, int sequence, String missionname, String description, String precon, String videoaddr, String fullyoutubeaddr, String youtubeaddr, String enabled, String feetype, Date creationdate, Date lastupdate) {
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
        this.creationdate = creationdate;
        this.lastupdate = lastupdate;
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

    public Date getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(Date creationdate) {
        this.creationdate = creationdate;
    }

    public Date getLastupdate() {
        return lastupdate;
    }

    public void setLastupdate(Date lastupdate) {
        this.lastupdate = lastupdate;
    }
}
