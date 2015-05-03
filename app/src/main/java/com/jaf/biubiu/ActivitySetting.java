package com.jaf.biubiu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jaf.bean.PostPushSwitch;
import com.jaf.jcore.BaseActionBarActivity;
import com.jaf.jcore.BindView;
import com.jaf.jcore.Http;
import com.jaf.jcore.HttpCallBack;
import com.jaf.jcore.JacksonWrapper;

import org.json.JSONObject;

public class ActivitySetting extends BaseActionBarActivity {

    private static boolean sIsPush;

    @BindView(id = R.id.feedback, onClick = "onFeedbackClick")
	private TextView mFeedback;

	@BindView(id = R.id.about, onClick = "onABoutClick")
	private TextView mAbout;

	@BindView(id = R.id.contactUs, onClick = "onContactUsClick")
	private TextView mContactUs;

	@BindView(id = R.id.userTerms, onClick = "onUserTermsClick")
	private TextView mUserTerms;

    @BindView(id = R.id.push, onClick = "onPushClick")
    private CheckBox mPushCheckBox;

	@Override
	protected int onLoadViewResource() {
		return R.layout.activity_setting;
	}

	@Override
	protected void onViewDidLoad(Bundle savedInstanceState) {
		mFeedback.setText(R.string.userFeedback);
		mAbout.setText(getString(R.string.aboutUs));
		mContactUs.setText(getString(R.string.contactUs));
		mUserTerms.setText(getString(R.string.userTerms));
        mPushCheckBox.setChecked(sIsPush);
	}

	public static void start(Activity activity, boolean isPush) {
        ActivitySetting.sIsPush = isPush;
		activity.startActivity(new Intent(activity, ActivitySetting.class));
	}

    public void onPushClick(View v) {
        sIsPush = mPushCheckBox.isChecked();
        Http http = new Http();
        PostPushSwitch switchPush = new PostPushSwitch();
        switchPush = (PostPushSwitch) U.buildBaseRequest(switchPush, Constant.CMD.POST_PUSH_SWITCH);
        switchPush.setIsPush(mPushCheckBox.isChecked() ? 1 : 2);
        JSONObject jo = JacksonWrapper.bean2Json(switchPush);
        http.url(Constant.API).JSON(jo).post(new HttpCallBack() {
            @Override
            public void onResponse(JSONObject response) {
                super.onResponse(response);
                L.dbg("switch push");
            }
        });
    }

	public void onFeedbackClick(View v) {
		ActivityFeedback.start(this);
	}

	public void onContactUsClick(View v) {
		ActivityContactUs.start(this);
	}

	public void onABoutClick(View v) {
		ActivityAbout.start(this);
	}

	public void onUserTermsClick(View v) {
		ActivityTerms.start(this);
	}
}
