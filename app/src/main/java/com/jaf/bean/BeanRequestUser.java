package com.jaf.bean;

/**
 * Created by jarrah on 2015/4/16.
 */
public class BeanRequestUser {
    private String appVersion;
    private int cmd;
    private String dvcId;

    private String platform;

    public String getPlatform() {
        return "Android";
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public int getCmd() {
        return cmd;
    }

    public void setCmd(int cmd) {
        this.cmd = cmd;
    }

    public String getDvcId() {
        return dvcId;
    }

    public void setDvcId(String dvcId) {
        this.dvcId = dvcId;
    }
}
