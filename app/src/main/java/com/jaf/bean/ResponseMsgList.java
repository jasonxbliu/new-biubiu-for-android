package com.jaf.bean;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by jarrah on 2015/4/21.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseMsgList implements Serializable {
    private String statusInfo;
    private int statusCode;
    private ReturnData returnData;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ReturnData {
        private ArrayList<BeanMsgItem> contData;

        public ArrayList<BeanMsgItem> getContData() {
            return contData;
        }

        public void setContData(ArrayList<BeanMsgItem> contData) {
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
}
