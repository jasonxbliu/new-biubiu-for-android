package com.jaf.bean;

/**
 * Created by jarrahwu on 15/4/25.
 */
public class PostReportAbuse extends BeanRequest{
    private int contType;
    private int contId;
    private String reasonCont;
    private int reason;

    public int getContType() {
        return contType;
    }

    public void setContType(int contType) {
        this.contType = contType;
    }

    public int getContId() {
        return contId;
    }

    public void setContId(int contId) {
        this.contId = contId;
    }

    public String getReasonCont() {
        return reasonCont;
    }

    public void setReasonCont(String reasonCont) {
        this.reasonCont = reasonCont;
    }

    public int getReason() {
        return reason;
    }

    public void setReason(int reason) {
        this.reason = reason;
    }
}
