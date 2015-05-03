package com.jaf.biubiu;

import android.app.Activity;
import android.content.Intent;

public class ActivityTerms extends ActivityAbout {

    @Override
    protected String getAssetsUrl() {
        return "file:///android_asset/protocol/protocol-zh-Hans.html";
    }

    public static void start(Activity activity) {
        activity.startActivity(new Intent(activity, ActivityTerms.class));
    }
}
