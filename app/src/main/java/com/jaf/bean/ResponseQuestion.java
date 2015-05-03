package com.jaf.bean;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

/**
 * Created by jarrah on 2015/4/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseQuestion {
    private String statusInfo;
    private int statusCode;
    private ReturnData returnData;

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

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ReturnData {
        private ArrayList<BeanAnswerItem> contData;

        public ArrayList<BeanAnswerItem> getContData() {
            return contData;
        }

        public void setContData(ArrayList<BeanAnswerItem> contData) {
            this.contData = contData;
        }
    }
}
