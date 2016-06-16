package com.sportsv.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sungbo on 2016-06-14.
 */
public class CpBalanceLine {

    private int lineid;
    private int headerid;
    private String type;
    private int previous_amount;
    private int in_amount;
    private int out_amount;
    private int last_amount;
    private String description;
    private Date creationdate;
    private String change_creationdate;

    public CpBalanceLine(){}

    public CpBalanceLine(int lineid, int headerid, String type, int previous_amount, int in_amount, int out_amount, int last_amount, String description, Date creationdate, String change_creationdate) {
        this.lineid = lineid;
        this.headerid = headerid;
        this.type = type;
        this.previous_amount = previous_amount;
        this.in_amount = in_amount;
        this.out_amount = out_amount;
        this.last_amount = last_amount;
        this.description = description;
        this.creationdate = creationdate;
        this.change_creationdate = change_creationdate;
    }

    public int getLineid() {
        return lineid;
    }

    public void setLineid(int lineid) {
        this.lineid = lineid;
    }

    public int getHeaderid() {
        return headerid;
    }

    public void setHeaderid(int headerid) {
        this.headerid = headerid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrevious_amount() {
        return previous_amount;
    }

    public void setPrevious_amount(int previous_amount) {
        this.previous_amount = previous_amount;
    }

    public int getIn_amount() {
        return in_amount;
    }

    public void setIn_amount(int in_amount) {
        this.in_amount = in_amount;
    }

    public int getOut_amount() {
        return out_amount;
    }

    public void setOut_amount(int out_amount) {
        this.out_amount = out_amount;
    }

    public int getLast_amount() {
        return last_amount;
    }

    public void setLast_amount(int last_amount) {
        this.last_amount = last_amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(Date creationdate) {
        this.creationdate = creationdate;
    }

    public String getChange_updatedate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
        change_creationdate = dateFormat.format(creationdate);
        return change_creationdate;
    }

    public void setChange_updatedate(String change_updatedate) {
        this.change_creationdate = change_updatedate;
    }
}
