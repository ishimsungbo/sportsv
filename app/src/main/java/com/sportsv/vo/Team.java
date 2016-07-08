package com.sportsv.vo;

/**
 * Created by sungbo on 2016-06-24.
 */
public class Team {
    private int teamid;
    private int instructorid;
    private String name;
    private String emblem;
    private String description;
    private String enabled;
    private String instructorname;
    private String creationdate;
    private String updatedate;

    private String change_creationdate;
    private String change_updatedate;

    public Team(){}

    public Team(int teamid, int instructorid, String name, String emblem, String description, String enabled, String instructorname, String creationdate, String updatedate, String change_creationdate, String change_updatedate) {
        this.teamid = teamid;
        this.instructorid = instructorid;
        this.name = name;
        this.emblem = emblem;
        this.description = description;
        this.enabled = enabled;
        this.instructorname = instructorname;
        this.creationdate = creationdate;
        this.updatedate = updatedate;
        this.change_creationdate = change_creationdate;
        this.change_updatedate = change_updatedate;
    }

    public String getInstructorname() {
        return instructorname;
    }

    public void setInstructorname(String instructorname) {
        this.instructorname = instructorname;
    }

    public int getTeamid() {
        return teamid;
    }

    public void setTeamid(int teamid) {
        this.teamid = teamid;
    }

    public int getInstructorid() {
        return instructorid;
    }

    public void setInstructorid(int instructorid) {
        this.instructorid = instructorid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmblem() {
        return emblem;
    }

    public void setEmblem(String emblem) {
        this.emblem = emblem;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
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

    @Override
    public String toString() {
        return "Team{" +
                "teamid=" + teamid +
                ", instructorid=" + instructorid +
                ", name='" + name + '\'' +
                ", emblem='" + emblem + '\'' +
                ", description='" + description + '\'' +
                ", enabled='" + enabled + '\'' +
                ", instructorname='" + instructorname + '\'' +
                ", creationdate='" + creationdate + '\'' +
                ", updatedate='" + updatedate + '\'' +
                ", change_creationdate='" + change_creationdate + '\'' +
                ", change_updatedate='" + change_updatedate + '\'' +
                '}';
    }
}
