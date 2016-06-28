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
    private String creationdate;
    private String   lastupdate;

    public MissionCategory(){}

    public MissionCategory(int categoryid, String name, String description, String enabled, String creationdate, String lastupdate) {
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

    public String getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(String creationdate) {
        this.creationdate = creationdate;
    }

    public String getLastupdate() {
        return lastupdate;
    }

    public void setLastupdate(String lastupdate) {
        this.lastupdate = lastupdate;
    }

    @Override
    public String toString() {
        return "MissionCategory{" +
                "categoryid=" + categoryid +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", enabled='" + enabled + '\'' +
                ", creationdate='" + creationdate + '\'' +
                ", lastupdate='" + lastupdate + '\'' +
                '}';
    }
}
