package com.sportsv.vo;

import java.util.Date;

/**
 * Created by sungbo on 2016-06-02.
 */
public class MissionType {

    private int typeid;
    private String name;
    private String description;
    private String enable;
    private Date creationdate;
    private Date   lastupdate;

    public MissionType(){}

    public MissionType(int typeid, String name, String description, String enable, Date creationdate, Date lastupdate) {
        this.typeid = typeid;
        this.name = name;
        this.description = description;
        this.enable = enable;
        this.creationdate = creationdate;
        this.lastupdate = lastupdate;
    }

    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
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
