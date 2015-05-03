package com.jaf.biubiu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.jaf.jcore.BindableActivity;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class ActivitySplash extends BindableActivity {

	private static final long SPLASH_DELAY = 1000;
	private final Handler mHandler = new Handler();
	private Runnable mDelayStart;

	@Override
	protected int onLoadViewResource() {
		return R.layout.activity_splash;
	}

	@Override
	protected void onViewDidLoad(Bundle savedInstanceState) {
        registerAlias();
		mDelayStart = new Runnable() {

			@Override
			public void run() {
				start();
			}
		};

		splash();
	}

	private void splash() {
		mHandler.postDelayed(mDelayStart, SPLASH_DELAY);
	}

	private void start() {
		mHandler.removeCallbacks(mDelayStart);
		Intent i = new Intent(ActivitySplash.this, ActivityTab.class);
		startActivity(i);
		finish();
	}

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    public void registerAlias() {
        String alias = Device.getId(this);
        //call back not ui thread
        JPushInterface.setAliasAndTags(getApplicationContext(), alias, null, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> strings) {
                switch (i) {
                    case 0:
                        L.dbg("Set tag and alias success");
                        break;

                    case 6002:
                        L.dbg("Failed to set alias and tags due to timeout. Try again after 60s.");
                        break;

                    default:
                        L.dbg("Failed with errorCode = " + i);
                        break;
                }
            }
        });
    }
}