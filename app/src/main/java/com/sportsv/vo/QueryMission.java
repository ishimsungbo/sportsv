package com.sportsv.vo;

/**
 * Created by sungbo on 2016-05-31.
 */
public class QueryMission {

    private String categoryid;
    private String typeid;
    private String missionname;
    private String youtubeaddr;
    private String enabled;

    public QueryMission(){}

    public QueryMission(String categoryid, String typeid, String missionname, String youtubeaddr, String enabled) {
        this.categoryid = categoryid;
        this.typeid = typeid;
        this.missionname = missionname;
        this.youtubeaddr = youtubeaddr;
        this.enabled = enabled;
    }

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public String getMissionname() {
        return missionname;
    }

    public void setMissionname(String missionname) {
        this.missionname = missionname;
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
}
