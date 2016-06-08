package com.sportsv.vo;

import java.util.Date;

/**
 * Created by sungbo on 2016-06-01.
 */
public class Instructor {

    private int idinstructorid;
    private String email;
    private String name;
    private String profile;
    private String description;
    private String phone;
    private int location;
    private String teamflag;
    private String teamname;
    private String feedbackflag;
    private String lectureflag;
    private String teamjoinflag;
    private String apppushflag;
    private Date creationdate;

    public Instructor(){}

    public Instructor(int idinstructorid, String email, String name, String profile, String description, String phone, int location, String teamflag, String teamname, String feedbackflag, String lectureflag, String teamjoinflag, String apppushflag, Date creationdate) {
        this.idinstructorid = idinstructorid;
        this.email = email;
        this.name = name;
        this.profile = profile;
        this.description = description;
        this.phone = phone;
        this.location = location;
        this.teamflag = teamflag;
        this.teamname = teamname;
        this.feedbackflag = feedbackflag;
        this.lectureflag = lectureflag;
        this.teamjoinflag = teamjoinflag;
        this.apppushflag = apppushflag;
        this.creationdate = creationdate;
    }

    public int getIdinstructorid() {
        return idinstructorid;
    }

    public void setIdinstructorid(int idinstructorid) {
        this.idinstructorid = idinstructorid;
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

    public String getTeamflag() {
        return teamflag;
    }

    public void setTeamflag(String teamflag) {
        this.teamflag = teamflag;
    }

    public String getTeamname() {
        return teamname;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }

    public String getFeedbackflag() {
        return feedbackflag;
    }

    public void setFeedbackflag(String feedbackflag) {
        this.feedbackflag = feedbackflag;
    }

    public String getLectureflag() {
        return lectureflag;
    }

    public void setLectureflag(String lectureflag) {
        this.lectureflag = lectureflag;
    }

    public String getTeamjoinflag() {
        return teamjoinflag;
    }

    public void setTeamjoinflag(String teamjoinflag) {
        this.teamjoinflag = teamjoinflag;
    }

    public String getApppushflag() {
        return apppushflag;
    }

    public void setApppushflag(String apppushflag) {
        this.apppushflag = apppushflag;
    }

    public Date getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(Date creationdate) {
        this.creationdate = creationdate;
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
}
