package com.jaf.bean;

/**
 * Created by jarrah on 2015/4/22.
 */
public class PostFeedback extends  BeanRequest{

    private String comment;
    private int verId;
    private String osVer;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getVerId() {
        return verId;
    }

    public void setVerId(int verId) {
        this.verId = verId;
    }

    public String getOsVer() {
        return osVer;
    }

    public void setOsVer(String osVer) {
        this.osVer = osVer;
    }
}