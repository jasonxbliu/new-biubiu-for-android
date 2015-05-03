package com.jaf.bean;

/**
 * Created by jarrahwu on 15/4/26.
 */
public class PostCreateUnion extends BeanRequest{
    private String selfLocDesc;
    private String picPath;
    private String unionName;

    public String getSelfLocDesc() {
        return selfLocDesc;
    }

    public void setSelfLocDesc(String selfLocDesc) {
        this.selfLocDesc = selfLocDesc;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getUnionName() {
        return unionName;
    }

    public void setUnionName(String unionName) {
        this.unionName = unionName;
    }
}
