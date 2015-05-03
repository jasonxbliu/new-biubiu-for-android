package com.jaf.biubiu;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.jaf.jcore.BaseActionBarActivity;

import java.io.Serializable;


public class ActivityMyQA extends BaseActionBarActivity {


    private static final String KEY_MY_QA = "my_qa";

    private Fragment mDisplayFragment;

    @Override
    protected int onLoadViewResource() {
        return R.layout.activity_my_qa;
    }

    @Override
    protected void onViewDidLoad(Bundle savedInstanceState) {
        if (getExtra() != null) {
            mDisplayFragment = getExtra().isQ ? FragmentMyQ.newInstance(getExtra()) : FragmentMyA.newInstance(getExtra());
        }

        if (mDisplayFragment != null) {
            FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
            trans.replace(R.id.container, mDisplayFragment);
            trans.commit();
        }
    }

    public Extra getExtra() {
        return (Extra) getIntent().getSerializableExtra(KEY_MY_QA);
    }

    public static void start(Activity activity, Extra extra) {
        Intent intent = new Intent(activity, ActivityMyQA.class);
        intent.putExtra(KEY_MY_QA, extra);
        activity.startActivity(intent);
    }

    public static class Extra implements Serializable {
        boolean isQ;

        public static Extra newExtra(boolean isQuestion) {
            Extra e = new Extra();
            e.isQ = isQuestion;
            return e;
        }
    }
}
