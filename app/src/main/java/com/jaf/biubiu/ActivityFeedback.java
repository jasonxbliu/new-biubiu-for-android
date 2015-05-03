package com.jaf.biubiu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jaf.bean.ResponseRandomFeedback;
import com.jaf.jcore.BaseActionBarActivity;
import com.jaf.jcore.BindView;
import com.jaf.jcore.Http;
import com.jaf.jcore.HttpCallBack;
import com.jaf.jcore.JacksonWrapper;

import org.json.JSONObject;

import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.danmaku.loader.ILoader;
import master.flame.danmaku.danmaku.loader.IllegalDataException;
import master.flame.danmaku.danmaku.loader.android.DanmakuLoaderFactory;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.android.DanmakuGlobalConfig;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.danmaku.parser.IDataSource;
import master.flame.danmaku.danmaku.parser.android.BiliDanmukuParser;
import master.flame.danmaku.ui.widget.DanmakuView;


public class ActivityFeedback extends BaseActionBarActivity {

    @BindView(id = R.id.msgEdit)
    private EditText mContent;

    @BindView(id = R.id.send, onClick = "onSendClick")
    private View mSend;

    @BindView(id = R.id.danmu)
    private DanmakuView mDanmakuView;

    private BaseDanmakuParser mParser;

    private Timer mTimer;
    private TimerTask mTask;
    private Http mHttp;

    @Override
    protected int onLoadViewResource() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void onViewDidLoad(Bundle savedInstanceState) {
        mHttp = new Http();

        mContent.setHint(R.string.feedback_tips);
        mParser = createParser(this.getResources().openRawResource(R.raw.empty));
        DanmakuGlobalConfig.DEFAULT.setDanmakuStyle(DanmakuGlobalConfig.DANMAKU_STYLE_STROKEN, 3).setDuplicateMergingEnabled(false);
        mDanmakuView.setCallback(new DrawHandler.Callback() {

            @Override
            public void updateTimer(DanmakuTimer timer) {

            }

            @Override
            public void prepared() {
                mDanmakuView.start();
            }
        });
        mDanmakuView.enableDanmakuDrawingCache(true);
        mDanmakuView.prepare(mParser);

        mTimer = new Timer();
        mTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new randomPull());
            }
        };
        mTimer.scheduleAtFixedRate(mTask, 0, 5000);
    }

    public class randomPull implements Runnable {

        @Override
        public void run() {
            mHttp.url(Constant.API).JSON(U.buildPostFeedBackRandom()).post(new HttpCallBack() {
                @Override
                public void onResponse(JSONObject response) {
                    ResponseRandomFeedback responseRandomFeedback = JacksonWrapper.json2Bean(response, ResponseRandomFeedback.class);
                    if (responseRandomFeedback != null) {
                        for (ResponseRandomFeedback.FeedbackItem item : responseRandomFeedback.getReturnData().getContData()) {
                            DanmuHelper.addDanmaku(mDanmakuView, false, item.getCont());
                        }
                    }
                }
            });
        }
    }

    private BaseDanmakuParser createParser(InputStream stream) {

        if (stream == null) {
            return new BaseDanmakuParser() {

                @Override
                protected Danmakus parse() {
                    return new Danmakus();
                }
            };
        }


        ILoader loader = DanmakuLoaderFactory.create(DanmakuLoaderFactory.TAG_BILI);

        try {
            loader.load(stream);
        } catch (IllegalDataException e) {
            e.printStackTrace();
        }
        BaseDanmakuParser parser = new BiliDanmukuParser();
        IDataSource<?> dataSource = loader.getDataSource();
        parser.load(dataSource);
        return parser;

    }

    public static void start(Activity activity) {
        activity.startActivity(new Intent(activity, ActivityFeedback.class));
    }

    public void onSendClick(View v) {
        Http http = new Http();
        if (TextUtils.isEmpty(mContent.getText())) {
            Toast.makeText(this, R.string.saySomething, Toast.LENGTH_SHORT).show();
        } else {
            JSONObject jo = U.buildPostFeedback(mContent.getText().toString());
            http.url(Constant.API).JSON(jo).post(new HttpCallBack() {
                @Override
                public void onResponse(JSONObject response) {
                    super.onResponse(response);
                    Toast.makeText(ActivityFeedback.this, R.string.feedback_success, Toast.LENGTH_SHORT).show();
                    mContent.setText("");
                    U.hideSoftKeyboard(ActivityFeedback.this);
                }
            });
        }

    }
}
