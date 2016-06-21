package com.sportsv.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sungbo on 2016-06-20.
 */
public class FeedbackHeader {

    private int feedbackid;
    private String feedbacktpe;
    private int missionid;
    private int usermissionid;
    private int uid;
    private int instructorid;
    private int cashpoint;
    private int Frequency;
    private String subject;
    private String change_creationdate;

    public FeedbackHeader(){}

    public FeedbackHeader(int uid) {
        this.uid = uid;
    }

    public FeedbackHeader(int feedbackid, String feedbacktpe, int missionid, int usermissionid, int uid, int instructorid, int cashpoint, int frequency, String subject, String change_creationdate) {
        this.feedbackid = feedbackid;
        this.feedbacktpe = feedbacktpe;
        this.missionid = missionid;
        this.usermissionid = usermissionid;
        this.uid = uid;
        this.instructorid = instructorid;
        this.cashpoint = cashpoint;
        Frequency = frequency;
        this.subject = subject;
        this.change_creationdate = change_creationdate;
    }

    public int getFeedbackid() {
        return feedbackid;
    }

    public void setFeedbackid(int feedbackid) {
        this.feedbackid = feedbackid;
    }

    public String getFeedbacktpe() {
        return feedbacktpe;
    }

    public void setFeedbacktpe(String feedbacktpe) {
        this.feedbacktpe = feedbacktpe;
    }

    public int getMissionid() {
        return missionid;
    }

    public void setMissionid(int missionid) {
        this.missionid = missionid;
    }

    public int getUsermissionid() {
        return usermissionid;
    }

    public void setUsermissionid(int usermissionid) {
        this.usermissionid = usermissionid;
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

    public int getCashpoint() {
        return cashpoint;
    }

    public void setCashpoint(int cashpoint) {
        this.cashpoint = cashpoint;
    }

    public int getFrequency() {
        return Frequency;
    }

    public void setFrequency(int frequency) {
        Frequency = frequency;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getChange_creationdate() {
        return change_creationdate;
    }

    public void setChange_creationdate(String change_creationdate) {
        this.change_creationdate = change_creationdate;
    }

    @Override
    public String toString() {
        return "FeedbackHeader{" +
                "feedbackid=" + feedbackid +
                ", feedbacktpe='" + feedbacktpe + '\'' +
                ", missionid=" + missionid +
                ", usermissionid=" + usermissionid +
                ", uid=" + uid +
                ", instructorid=" + instructorid +
                ", cashpoint=" + cashpoint +
                ", Frequency=" + Frequency +
                ", subject='" + subject + '\'' +
                ", change_creationdate='" + change_creationdate + '\'' +
                '}';
    }
}
