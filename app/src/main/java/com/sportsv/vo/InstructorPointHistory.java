package com.sportsv.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sungbo on 2016-06-15.
 */
public class InstructorPointHistory {

    private int  pointhistoryid;
    private int  instructorid;
    private int  videopoint;
    private int  wordpoint;
    private Date creationdate;
    private String change_creationdate;

    public InstructorPointHistory(){}

    public InstructorPointHistory(int pointhistoryid, int instructorid, int videopoint, int wordpoint, Date creationdate, String change_creationdate) {
        this.pointhistoryid = pointhistoryid;
        this.instructorid = instructorid;
        this.videopoint = videopoint;
        this.wordpoint = wordpoint;
        this.creationdate = creationdate;
        this.change_creationdate = change_creationdate;
    }

    public String getChange_creationdate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
        change_creationdate = dateFormat.format(creationdate);
        return change_creationdate;
    }

    public void setChange_creationdate(String change_creationdate) {
        this.change_creationdate = change_creationdate;
    }

    public int getPointhistoryid() {
        return pointhistoryid;
    }

    public void setPointhistoryid(int pointhistoryid) {
        this.pointhistoryid = pointhistoryid;
    }

    public int getInstructorid() {
        return instructorid;
    }

    public void setInstructorid(int instructorid) {
        this.instructorid = instructorid;
    }

    public int getVideopoint() {
        return videopoint;
    }

    public void setVideopoint(int videopoint) {
        this.videopoint = videopoint;
    }

    public int getWordpoint() {
        return wordpoint;
    }

    public void setWordpoint(int wordpoint) {
        this.wordpoint = wordpoint;
    }

    public Date getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(Date creationdate) {
        this.creationdate = creationdate;
    }
}
