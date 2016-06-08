package com.sportsv.vo;

import java.util.Date;

/**
 * Created by sungbo on 2016-06-02.
 */
public class MissionCategory {


    private int categoryid;
    private String name;
    private String description;
    private String enabled;
    private Date creationdate;
    private Date   lastupdate;

    public MissionCategory(){}

    public MissionCategory(int categoryid, String name, String description, String enabled, Date creationdate, Date lastupdate) {
        this.categoryid = categoryid;
        this.name = name;
        this.description = description;
        this.enabled = enabled;
        this.creationdate = creationdate;
        this.lastupdate = lastupdate;
    }

    public int getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
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

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
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
