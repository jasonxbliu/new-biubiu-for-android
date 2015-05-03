package com.jaf.bean;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by jarrahwu on 15/4/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BeanUnionItem {
    private long creTime;
    private int distance;
    private int latitude;
    private String locDesc;
    private int longitude;
    private String ownerNick;
    private String picPath;
    private int questionNum;
    private String selfLocDesc;
    private int sortId;
    private int unionId;
    private String unionName;

    public long getCreTime() {
        return creTime;
    }

    public void setCreTime(long creTime) {
        this.creTime = creTime;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public String getLocDesc() {
        return locDesc;
    }

    public void setLocDesc(String locDesc) {
        this.locDesc = locDesc;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public String getOwnerNick() {
        return ownerNick;
    }

    public void setOwnerNick(String ownerNick) {
        this.ownerNick = ownerNick;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public int getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(int questionNum) {
        this.questionNum = questionNum;
    }

    public String getSelfLocDesc() {
        return selfLocDesc;
    }

    public void setSelfLocDesc(String selfLocDesc) {
        this.selfLocDesc = selfLocDesc;
    }

    public int getSortId() {
        return sortId;
    }

    public void setSortId(int sortId) {
        this.sortId = sortId;
    }

    public int getUnionId() {
        return unionId;
    }

    public void setUnionId(int unionId) {
        this.unionId = unionId;
    }

    public String getUnionName() {
        return unionName;
    }

    public void setUnionName(String unionName) {
        this.unionName = unionName;
    }
}
