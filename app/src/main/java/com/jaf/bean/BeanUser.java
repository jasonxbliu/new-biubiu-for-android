package com.jaf.bean;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by jarrah on 2015/4/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BeanUser {
    private int answerNum;
    private int isNewAnsPush;
    private int isNewLikePush;
    private int isNewQuestPush;
    private int otherAnswerNum;
    private int otherLikeNum;
    private int questionNum;
    private int rank;
    private int rankRate;
    private int recDay;
    private int score;
    private int unionNum;
    private int isPush;

    public int getIsPush() {
        return isPush;
    }

    public void setIsPush(int isPush) {
        this.isPush = isPush;
    }

    public int getAnswerNum() {
        return answerNum;
    }

    public void setAnswerNum(int answerNum) {
        this.answerNum = answerNum;
    }

    public int getIsNewAnsPush() {
        return isNewAnsPush;
    }

    public void setIsNewAnsPush(int isNewAnsPush) {
        this.isNewAnsPush = isNewAnsPush;
    }

    public int getIsNewLikePush() {
        return isNewLikePush;
    }

    public void setIsNewLikePush(int isNewLikePush) {
        this.isNewLikePush = isNewLikePush;
    }

    public int getIsNewQuestPush() {
        return isNewQuestPush;
    }

    public void setIsNewQuestPush(int isNewQuestPush) {
        this.isNewQuestPush = isNewQuestPush;
    }

    public int getOtherAnswerNum() {
        return otherAnswerNum;
    }

    public void setOtherAnswerNum(int otherAnswerNum) {
        this.otherAnswerNum = otherAnswerNum;
    }

    public int getOtherLikeNum() {
        return otherLikeNum;
    }

    public void setOtherLikeNum(int otherLikeNum) {
        this.otherLikeNum = otherLikeNum;
    }

    public int getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(int questionNum) {
        this.questionNum = questionNum;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getRankRate() {
        return rankRate;
    }

    public void setRankRate(int rankRate) {
        this.rankRate = rankRate;
    }

    public int getRecDay() {
        return recDay;
    }

    public void setRecDay(int recDay) {
        this.recDay = recDay;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getUnionNum() {
        return unionNum;
    }

    public void setUnionNum(int unionNum) {
        this.unionNum = unionNum;
    }
}
