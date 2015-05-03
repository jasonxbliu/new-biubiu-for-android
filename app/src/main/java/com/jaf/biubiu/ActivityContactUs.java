package com.jaf.biubiu;

import android.app.Activity;
import android.content.Intent;


public class ActivityContactUs extends ActivityAbout {

    @Override
    protected String getAssetsUrl() {
        return "file:///android_asset/contactus/contactus.html";
    }

    public static void start(Activity activity) {
        activity.startActivity(new Intent(activity, ActivityContactUs.class));
    }
}
