package com.jaf.biubiu;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.jaf.bean.BeanNearbyItem;
import com.jaf.jcore.Http;
import com.jaf.jcore.HttpCallBack;

import org.json.JSONObject;

/**
 * Created by jarrahwu on 15/4/19.
 */
public class LikePanelHolder implements View.OnClickListener {
    public CheckBox like;
    public CheckBox unLike;
    public Extra extra;

    public LikePanelHolder(Extra extra, View panelParent) {
        like = (CheckBox) panelParent.findViewById(R.id.likeCheck);
        unLike = (CheckBox) panelParent.findViewById(R.id.unlikeCheck);
        this.extra = extra;
    }

    public void listenForChecking() {
        unLike.setOnClickListener(this);
        like.setOnClickListener(this);
    }


    // 方便header 使用
    public void setData(BeanNearbyItem beanAnswerItem) {
        if (beanAnswerItem != null) {
            like.setText(String.valueOf(beanAnswerItem.getLikeNum()));
            unLike.setText(String.valueOf(beanAnswerItem.getUnlikeNum()));


            boolean likeEnable = true;
            boolean isUnlike;
            boolean isLike;

            switch (beanAnswerItem.getLikeFlag()) {
                case 1://zan
                    likeEnable = false;
                    isLike = true;
                    isUnlike = false;
                    break;
                case 2://cai
                    likeEnable = false;
                    isUnlike = true;
                    isLike = false;
                    break;
                default://no status
                    likeEnable= true;
                    isUnlike = false;
                    isLike = false;
                    break;
            }
            like.setChecked(isLike);
            unLike.setChecked(isUnlike);

            like.setEnabled(likeEnable);
            unLike.setEnabled(likeEnable);
        }
    }

//    public void stopListen() {
//        unLike.setOnCheckedChangeListener(null);
//        like.setOnCheckedChangeListener(null);
//    }

    public static class Extra {
        int aid = 0;//answer id
        int qid = 0;//question id;
    }

    @Override
    public void onClick(View v) {
        CompoundButton cb = (CompoundButton) v;
        cb.toggle();
        boolean isLike = v == like ? true : false;
        doPost(isLike, extra);
    }

    //true 赞 false 踩
    public void onLike(boolean isLike) {

    }

    public void doPost(final boolean isLike, final Extra extra) {
        onPrePost(isLike);
        Http http = new Http();
        JSONObject jo = U.buildPostLike(isLike, extra.qid, extra.aid);
        http.url(Constant.API).JSON(jo).post(new HttpCallBack() {
            @Override
            public void onResponse(JSONObject response) {
                super.onResponse(response);
                L.dbg("do like post success in like panel qid:%d, aid:%d", extra.qid, extra.aid);
                onPostSuccess(isLike);
            }
        });
    }

    public void onPrePost(boolean isLike) {

    }

    public void onPostSuccess(boolean isLike) {
    }
}
