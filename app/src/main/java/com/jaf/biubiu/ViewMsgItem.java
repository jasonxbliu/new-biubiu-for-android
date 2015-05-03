package com.jaf.biubiu;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.jaf.bean.BeanMsgItem;
import com.jaf.bean.BeanNearbyItem;
import com.jaf.bean.BeanRequestTopicQuestionList;
import com.jaf.jcore.Application;
import com.jaf.jcore.BindView;
import com.jaf.jcore.BindableView;
import com.jaf.jcore.Http;
import com.jaf.jcore.HttpCallBack;
import com.jaf.jcore.JacksonWrapper;

import org.json.JSONObject;

/**
 * Created by jarrah on 2015/4/21.
 */
public class ViewMsgItem extends BindableView implements View.OnClickListener{

    @BindView(id = R.id.msgTypeIcon)
    private ImageView mMsgIcon;

    @BindView(id = R.id.msgTypeDesc)
    private TextView mMsgTypeDesc;

    @BindView(id = R.id.time)
    private TextView mTime;

    @BindView(id = R.id.msgContent)
    private TextView mMsgContent;
    private BeanMsgItem mData;
    private Dialog mDialog;


    public ViewMsgItem(Context context) {
        super(context);
    }

    @Override
    public void onViewDidLoad() {
        setOnClickListener(this);
    }

    public void setData(BeanMsgItem data) {
        mData = data;
        mTime.setText(DateUtil.getDiffTime(data.getPubTime() * 1000L));
        loadImage(data.getTypePic());

        String title = getResources().getString(R.string.noRelText);
        String content = getResources().getString(R.string.noRelText);
        if (data.getType() == 1) { //reply
            title = data.getAns();
            content = data.getQuest();
        } else if (data.getType() == 2) {// like
            title = data.getAnsId() == 0 ? getResources().getString(R.string.qLike) : getResources().getString(R.string.commentLike);
            content = data.getAnsId() == 0 ? data.getQuest() : data.getAns();
        } else if (data.getType() == 4) {//union
            title = data.getIsPass() == 1 ? getResources().getString(R.string.unionPass) : getResources().getString(R.string.unionBlock);
            content = data.getUnionName();
        } else if (data.getType() == 5) {//comment reply
            title = data.getSecAns();
            content = data.getAns();
        }

        mMsgTypeDesc.setText(title);
        mMsgContent.setText(content);
    }

    @Override
    public int onLoadViewResource() {
        return R.layout.view_msg_item;
    }


    private void loadImage(String url) {
        Application.getInstance().getAQuery().id(mMsgIcon).image(url, false, true, 500, 0, new BitmapAjaxCallback() {
            @Override
            protected void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                super.callback(url, iv, bm, status);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (mData.getType() == 1 || mData.getType() == 2 || mData.getType() == 5) {
            Http http = new Http();
            JSONObject jo = U.buildGetQuestion(mData.getQuestId());
            http.url(Constant.API).JSON(jo).post(new HttpCallBack() {
                @Override
                public void onResponse(JSONObject response) {
                    super.onResponse(response);
                    L.dbg("msg response :" + response);
                    if (response != null && response.optJSONObject("returnData") != null) {
                        JSONObject returnData = response.optJSONObject("returnData");
                        BeanNearbyItem beanNearbyItem = JacksonWrapper.json2Bean(returnData, BeanNearbyItem.class);
                        if (beanNearbyItem != null) {
                            ActivityDetail.Extra e = new ActivityDetail.Extra();
                            e.fromNearby = beanNearbyItem;
                            e.questId = beanNearbyItem.getQuestId();
                            ActivityDetail.start((android.app.Activity) getContext(), e);
                        }
                    } else {
                        Toast.makeText(getContext(), R.string.network_err, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        if(mData.getType() == 4) {
            if(mData.getIsPass() == 1) {
                ActivityUnionTopic.Extra e = new ActivityUnionTopic.Extra();
                e.topicTitle = mData.getUnionName();
                BeanRequestTopicQuestionList bean = U.buildTopicQuestionListArg(mData.getUnionId());
                e.fromTopic = bean;
                ActivityUnionTopic.start((android.app.Activity) getContext(), e);
            } else {
                ActivityMyUnion.start((android.app.Activity) getContext());
            }

        }

    }


}
