package com.jaf.bean;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

/**
 * Created by jarrah on 2015/4/23.
 */
public class ResponseRandomFeedback {
    private String statusInfo;
    private int statusCode;
    private ReturnData returnData;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ReturnData {
        private ArrayList<FeedbackItem> contData;

        public ArrayList<FeedbackItem> getContData() {
            return contData;
        }

        public void setContData(ArrayList<FeedbackItem> contData) {
            this.contData = contData;
        }
    }

    public String getStatusInfo() {
        return statusInfo;
    }

    public void setStatusInfo(String statusInfo) {
        this.statusInfo = statusInfo;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public ReturnData getReturnData() {
        return returnData;
    }

    public void setReturnData(ReturnData returnData) {
        this.returnData = returnData;
    }


    public static class FeedbackItem {
        String cont;

        public String getCont() {
            return cont;
        }

        public void setCont(String cont) {
            this.cont = cont;
        }
    }
}
