package com.jaf.bean;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by jarrah on 2015/4/21.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BeanMyQAItem implements Serializable{
    private int ansNum;
    private int isImpeach;
    private int isMine;
    private int latitude;
    private int likeFlag;
    private int likeNum;
    private int longitude;
    private int opType;
    private long pubTime;
    private String quest;
    private int questId;
    private String selfLocDesc;
    private String sign;
    private int sortId;
    private int unlikeNum;

    public int getAnsNum() {
        return ansNum;
    }

    public void setAnsNum(int ansNum) {
        this.ansNum = ansNum;
    }

    public int getIsImpeach() {
        return isImpeach;
    }

    public void setIsImpeach(int isImpeach) {
        this.isImpeach = isImpeach;
    }

    public int getIsMine() {
        return isMine;
    }

    public void setIsMine(int isMine) {
        this.isMine = isMine;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public int getLikeFlag() {
        return likeFlag;
    }

    public void setLikeFlag(int likeFlag) {
        this.likeFlag = likeFlag;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public int getOpType() {
        return opType;
    }

    public void setOpType(int opType) {
        this.opType = opType;
    }

    public long getPubTime() {
        return pubTime;
    }

    public void setPubTime(long pubTime) {
        this.pubTime = pubTime;
    }

    public String getQuest() {
        return quest;
    }

    public void setQuest(String quest) {
        this.quest = quest;
    }

    public int getQuestId() {
        return questId;
    }

    public void setQuestId(int questId) {
        this.questId = questId;
    }

    public String getSelfLocDesc() {
        return selfLocDesc;
    }

    public void setSelfLocDesc(String selfLocDesc) {
        this.selfLocDesc = selfLocDesc;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public int getSortId() {
        return sortId;
    }

    public void setSortId(int sortId) {
        this.sortId = sortId;
    }

    public int getUnlikeNum() {
        return unlikeNum;
    }

    public void setUnlikeNum(int unlikeNum) {
        this.unlikeNum = unlikeNum;
    }
}
