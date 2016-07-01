package com.sportsv.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sungbo on 2016-06-01.
 */
public class Instructor {

    private int instructorid;
    private String email;
    private String password;
    private String name;
    private String profile;
    private String description;
    private String phone;
    private int location;
    private int teamid;
    private int pointhistoryid;
    private String feedbackflag;
    private String apppushflag;
    private int commontokenid;
    private String creationdate;
    private String change_creationdate;

    public Instructor(){}

    public Instructor(int instructorid, String email, String password, String name, String profile, String description, String phone, int location, int teamid, int pointhistoryid, String feedbackflag, String apppushflag, int commontokenid, String creationdate, String change_creationdate) {
        this.instructorid = instructorid;
        this.email = email;
        this.password = password;
        this.name = name;
        this.profile = profile;
        this.description = description;
        this.phone = phone;
        this.location = location;
        this.teamid = teamid;
        this.pointhistoryid = pointhistoryid;
        this.feedbackflag = feedbackflag;
        this.apppushflag = apppushflag;
        this.commontokenid = commontokenid;
        this.creationdate = creationdate;
        this.change_creationdate = change_creationdate;
    }


    public int getCommontokenid() {
        return commontokenid;
    }

    public void setCommontokenid(int commontokenid) {
        this.commontokenid = commontokenid;
    }

    public int getInstructorid() {
        return instructorid;
    }

    public void setInstructorid(int instructorid) {
        this.instructorid = instructorid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public int getTeamid() {
        return teamid;
    }

    public void setTeamid(int teamid) {
        this.teamid = teamid;
    }

    public int getPointhistoryid() {
        return pointhistoryid;
    }

    public void setPointhistoryid(int pointhistoryid) {
        this.pointhistoryid = pointhistoryid;
    }

    public String getFeedbackflag() {
        return feedbackflag;
    }

    public void setFeedbackflag(String feedbackflag) {
        this.feedbackflag = feedbackflag;
    }

    public String getApppushflag() {
        return apppushflag;
    }

    public void setApppushflag(String apppushflag) {
        this.apppushflag = apppushflag;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(String creationdate) {
        this.creationdate = creationdate;
    }

    public String getChange_creationdate() {
        return change_creationdate;
    }

    public void setChange_creationdate(String change_creationdate) {
        this.change_creationdate = change_creationdate;
    }

    @Override
    public String toString() {
        return "Instructor{" +
                "instructorid=" + instructorid +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", profile='" + profile + '\'' +
                ", description='" + description + '\'' +
                ", phone='" + phone + '\'' +
                ", location=" + location +
                ", teamid=" + teamid +
                ", pointhistoryid=" + pointhistoryid +
                ", feedbackflag='" + feedbackflag + '\'' +
                ", apppushflag='" + apppushflag + '\'' +
                ", commontokenid=" + commontokenid +
                ", creationdate='" + creationdate + '\'' +
                ", change_creationdate='" + change_creationdate + '\'' +
                '}';
    }
}
