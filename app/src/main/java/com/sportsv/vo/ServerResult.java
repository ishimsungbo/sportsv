package com.sportsv.vo;

/**
 * Created by sungbo on 2016-06-22.
 * 카운트와 스트링 전용
 */
public class ServerResult {

    private int count;
    private String result;

    public ServerResult(){}

    public ServerResult(int count, String result) {
        this.count = count;
        this.result = result;
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

    @Override
    public String toString() {
        return "ServerResult{" +
                "count=" + count +
                ", result='" + result + '\'' +
                '}';
    }
}
