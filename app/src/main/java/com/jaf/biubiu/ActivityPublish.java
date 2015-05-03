package com.jaf.biubiu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.jaf.jcore.Application;
import com.jaf.jcore.BaseActionBarActivity;
import com.jaf.jcore.BindView;
import com.jaf.jcore.Http;
import com.jaf.jcore.HttpCallBack;
import com.jaf.jcore.JacksonWrapper;

import org.json.JSONObject;

import java.io.Serializable;


public class ActivityPublish extends BaseActionBarActivity {

    private static final String TAG = "Activity Publish";
    public static final String PUBLISH_EXTRA = "publish_extra";
    @BindView(id = R.id.location)
    private TextView mLocation;

    @BindView(id = R.id.refresh, onClick = "refreshLocation")
    private View mRefreshLocation;

    @BindView(id = R.id.content)
    private EditText mContent;

    @BindView(id = R.id.sign)
    private EditText mSign;

    @BindView(id = R.id.textCount)
    private TextView mTextCount;
    
    @Override
    protected int onLoadViewResource() {
        return R.layout.activity_publish;
    }


    @Override
    protected View getActionBarView() {
        mActionBarView = getLayoutInflater().inflate(R.layout.view_action_bar_publish, null);
        mActionBarView.findViewById(R.id.barOption).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publish();
            }
        });
        return mActionBarView;
    }

    @Override
    protected void onViewDidLoad(Bundle savedInstanceState) {
        updateLocation();
        mContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int max = 200;
                int input = mContent.length();
                mTextCount.setText(String.valueOf(max - input));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void publish() {
        String text = mContent.getText().toString();
        boolean valid = !TextUtils.isEmpty(text) && text.length() >= 5;
        if(valid) {
            Http http = new Http(TAG);
            String sign = mSign.getText().toString();
            String locDesc = Application.getInstance().getAppExtraInfo().addrStr;
            String quest = mContent.getText().toString();
            Extra extra = (Extra) getIntent().getSerializableExtra(PUBLISH_EXTRA);
            int questType = extra.isUnionQuestion ? 2 : 1;
            int unionId = extra.unionId;
            JSONObject jo = JacksonWrapper.bean2Json(U.buildPublishQuestion(sign, locDesc, questType, unionId, quest));
            http.url(Constant.API).JSON(jo).post(new HttpCallBack() {
                @Override
                public void onResponse(JSONObject response) {
                    super.onResponse(response);
                    Toast.makeText(ActivityPublish.this, R.string.publishSuccess, Toast.LENGTH_SHORT).show();
                    ActivityPublish.this.finish();
                }
            });
        }else{
            Toast.makeText(this, R.string.publish_at_leasst, Toast.LENGTH_SHORT).show();
        }
    }

    public void refreshLocation(View v) {
        updateLocation();
    }

    private void updateLocation() {
        Toast.makeText(this, R.string.refresh_location, Toast.LENGTH_SHORT).show();
        LocationManager.getInstance().requestLocation(new LocationManager.JLsn() {
            @Override
            public void onResult(double latitude, double longitude, BDLocation location) {
                super.onResult(latitude, longitude, location);
                Application.getInstance().getAppExtraInfo().lat = latitude;
                Application.getInstance().getAppExtraInfo().lon = longitude;
                Application.getInstance().getAppExtraInfo().city = location.getCity();
                Application.getInstance().getAppExtraInfo().addrStr = location.getAddrStr();
                mLocation.setText(location.getAddrStr());
            }
        });
    }

    public static void start(Activity activity, Extra extra) {
        Intent intent = new Intent(activity, ActivityPublish.class);
        intent.putExtra(PUBLISH_EXTRA, extra);
        activity.startActivity(intent);
    }

    public static class Extra implements Serializable{
        boolean isUnionQuestion;
        int unionId;
    }
}
