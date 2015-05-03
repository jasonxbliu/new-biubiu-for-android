package com.jaf.biubiu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;

import com.jaf.bean.BeanRequestTopicQuestionList;
import com.jaf.jcore.BaseActionBarActivity;

import java.io.Serializable;

public class ActivityUnionTopic extends BaseActionBarActivity {

	private static final String KEY_ARGS = "union_topic";
	private Fragment mDisplayFragment;
	private Extra mExtra;

	@Override
	protected int onLoadViewResource() {
		return R.layout.activity_union_topic;
	}

    @Override
    protected View getActionBarView() {
        mActionBarView = LayoutInflater.from(this).inflate(R.layout.view_action_bar_union_topic, null);
        mActionBarView.findViewById(R.id.barOption).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityPublish.Extra e = new ActivityPublish.Extra();
                e.unionId = mExtra.fromTopic.getUnionId();
                e.isUnionQuestion = true;
                ActivityPublish.start(ActivityUnionTopic.this, e);
            }
        });
        return mActionBarView;
    }



    @Override
	protected void onViewDidLoad(Bundle savedInstanceState) {
		mExtra = getData();
		if (mExtra == null) {
			L.dbg("activity question getData is null!");
			return;
		} else {
			if (mExtra.fromTopic != null) { // 板块列表
				setTitle(mExtra.topicTitle);
				mDisplayFragment = FragmentQATopic.newInstance(mExtra.fromTopic);
			}
			if (mDisplayFragment != null) {
				FragmentTransaction trans = getSupportFragmentManager()
						.beginTransaction();
				trans.replace(R.id.container, mDisplayFragment);
				trans.commit();
			} else {
				L.dbg("display fragment is null");
			}
		}
	}

	public Extra getData() {
		return (Extra) getIntent().getSerializableExtra(KEY_ARGS);
	}

	public static void start(Activity activity, final Extra extra) {
		Intent intent = new Intent();
		intent.putExtra(KEY_ARGS, extra);
		intent.setClass(activity, ActivityUnionTopic.class);
		activity.startActivity(intent);
	}

	public static class Extra implements Serializable {
		// topic pair
		String topicTitle;
		BeanRequestTopicQuestionList fromTopic;
	}
}
