package com.sportsv.vo;

/**
 * Created by sungbo on 2016-06-11.
 */
public class SpBalanceHeader {

    private int headerid;
    private int uid;
    private int amount;
    private String updatedate;
    private String change_updatedate;

    public SpBalanceHeader(){}

    public SpBalanceHeader(int headerid, int uid, int amount, String updatedate, String change_updatedate) {
        this.headerid = headerid;
        this.uid = uid;
        this.amount = amount;
        this.updatedate = updatedate;
        this.change_updatedate = change_updatedate;
    }

    public int getHeaderid() {
        return headerid;
    }

    public void setHeaderid(int headerid) {
        this.headerid = headerid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getChange_updatedate() {
        return change_updatedate;
    }

    public void setChange_updatedate(String change_updatedate) {
        this.change_updatedate = change_updatedate;
    }

    public String getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(String updatedate) {
        this.updatedate = updatedate;
    }

    @Override
    public String toString() {
        return "SpBalanceHeader{" +
                "headerid=" + headerid +
                ", uid=" + uid +
                ", amount=" + amount +
                ", updatedate='" + updatedate + '\'' +
                ", change_updatedate='" + change_updatedate + '\'' +
                '}';
    }
}
