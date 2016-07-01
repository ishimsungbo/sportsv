package com.sportsv.vo;

/**
 * Created by sungbo on 2016-06-22.
 * 카운트와 스트링 전용
 */
public class ServerResult {

    private int count;
    private String result;
    private String errorMsg;

    public ServerResult(){}

    public ServerResult(int count, String result) {
        this.count = count;
        this.result = result;
    }

    public ServerResult(int count, String result, String errorMsg) {
        this.count = count;
        this.result = result;
        this.errorMsg = errorMsg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "ServerResult{" +
                "count=" + count +
                ", result='" + result + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
