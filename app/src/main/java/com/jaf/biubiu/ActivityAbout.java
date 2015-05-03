package com.jaf.biubiu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import com.jaf.jcore.BaseActionBarActivity;


public class ActivityAbout extends BaseActionBarActivity {

    private WebView mWebView;

    @Override
    protected int onLoadViewResource() {
        return R.layout.activity_about;
    }

    @Override
    protected void onViewDidLoad(Bundle savedInstanceState) {
        mWebView = (WebView) findViewById(R.id.aboutWeb);
        mWebView.loadUrl(getAssetsUrl());
    }

    protected String getAssetsUrl() {
        return "file:///android_asset/about/about.html";
    }

    public static void start(Activity activity) {
        activity.startActivity(new Intent(activity, ActivityAbout.class));
    }
}
