package com.jaf.bean;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by jarrah on 2015/4/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseUser {
    private int statusCode;
    private String statusInfo;
    private BeanUser returnData;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusInfo() {
        return statusInfo;
    }

    public void setStatusInfo(String statusInfo) {
        this.statusInfo = statusInfo;
    }

    public BeanUser getReturnData() {
        return returnData;
    }

    public void setReturnData(BeanUser returnData) {
        this.returnData = returnData;
    }
}
