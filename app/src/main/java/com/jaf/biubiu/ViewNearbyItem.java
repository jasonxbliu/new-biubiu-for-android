package com.jaf.biubiu;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jaf.bean.BeanAnswerItem;
import com.jaf.bean.BeanNearbyItem;
import com.jaf.bean.ResponseQuestion;
import com.jaf.jcore.BindView;
import com.jaf.jcore.BindableView;
import com.jaf.jcore.Http;
import com.jaf.jcore.HttpCallBack;
import com.jaf.jcore.JacksonWrapper;

import org.json.JSONObject;

import java.util.ArrayList;

import master.flame.danmaku.ui.widget.DanmakuView;

/**
 * Created by jarrah on 2015/4/23.
 */
public class ViewNearbyItem extends BindableView {
    public static final int NO_EXPAND = -1;
    public static int expandPosition = NO_EXPAND;

    @BindView(id = R.id.name)
    private TextView mName;

    @BindView(id = R.id.content)
    private TextView mContent;

    @BindView(id = R.id.locDesc)
    private TextView mLocDesc;

    @BindView(id = R.id.replyCount)
    private TextView mReplyCount;

    @BindView(id = R.id.optionContainer)
    private View mDanmuContainer;

    @BindView(id = R.id.danmuLoadingTips)
    private TextView mDanmuTips;

    @BindView(id = R.id.danmu)
    private DanmakuView mDanmakuView;

    @BindView(id = R.id.itemContainer)
    private View mItemContainer;

    @BindView(id = R.id.itemSubContainer)
    private View mItemSubContainer;

    @BindView(id = R.id.itemClickArea, onClick = "onItemClick")
    private View mItemClickArea;

    @BindView(id = R.id.itemClickArea2, onClick = "onItemClick")
    private View mItemClickArea2;

    private BeanNearbyItem mBeanNearbyItem;
    private Http http;
    private ArrayList<BeanAnswerItem> mDanmuSouce;

    @BindView(id = R.id.listMode, onClick = "onListModeClick")
    View btnListMode;
    private LikePanelHolder mLikePanelHolder;

    @BindView(id = R.id.likeCheck)
    public CheckBox mLike;

    @BindView(id = R.id.unlikeCheck)
    public CheckBox mUnLike;


    @BindView(id = R.id.likePanelContainer, onClick = "doNothing")

    private View mLikePanelContainer;
    private int mPosition;

    public ViewNearbyItem(Context context) {
        super(context);
    }

    @Override
    public void onViewDidLoad() {
        http = new Http();
    }

    private void toggleDanmuPanel() {
        if (mDanmuContainer.getVisibility() == View.VISIBLE) {
            hideDamu();
        } else {
            showDamu();
        }
    }


    public void onShow(int position) {
    }

    public void onHide() {
    }

    private void showDamu() {
        mDanmuContainer.setVisibility(View.VISIBLE);
        if (mBeanNearbyItem.getAnsNum() == 0) {
            mDanmuTips.setVisibility(View.VISIBLE);
            mDanmuTips.setText(R.string.commentCome);
        } else {
            mDanmuTips.setVisibility(View.VISIBLE);
            mDanmuTips.setText(R.string.danmuComming);
        }
        DanmuHelper.setupDanmu(mDanmakuView);
        requestDanmuData();
        onShow(mPosition);
    }

    private void hideDamu() {
        mDanmuContainer.setVisibility(View.GONE);
        if(mDanmakuView.isShown()) {
            mDanmakuView.clearDanmakusOnScreen();
            mDanmakuView.stop();
        }
        onHide();
    }

    private void requestDanmuData() {
        http.url(Constant.API)
                .JSON(U.buildQuestion(false, 999, mBeanNearbyItem.getQuestId()))
                .post(new HttpCallBack() {
                    @Override
                    public void onResponse(JSONObject response) {
                        super.onResponse(response);
                        ResponseQuestion responseQuestion = JacksonWrapper
                                .json2Bean(response, ResponseQuestion.class);
                        if (responseQuestion != null) {
                            L.dbg("danmu get!");
                            mDanmuSouce = responseQuestion.getReturnData()
                                    .getContData();
                            if (mDanmuSouce.size() > 0) {
                                mDanmuTips.setVisibility(View.GONE);
                            }
                            delayDanmu();
                        }
                    }
                });
    }

    int startIndex = 0;

    private void delayDanmu() {
        if (mDanmuSouce == null || mDanmuSouce.isEmpty() || mDanmuContainer.getVisibility() == View.GONE)
            return;

        postDelayed(new Runnable() {
            @Override
            public void run() {
                BeanAnswerItem item = null;
                try {
                    item = mDanmuSouce.get(startIndex);
                } catch (Exception e) {
                    item = null;
                }
                if (item == null)
                    return;

                DanmuHelper.addDanmaku(mDanmakuView, false, item.getAns());
                startIndex++;
                startIndex = startIndex == mDanmuSouce.size() ? 0 : startIndex;
                delayDanmu();
            }
        }, 300);
    }

    @Override
    public int onLoadViewResource() {
        return R.layout.view_nearby_item;
    }

    public void setData(BeanNearbyItem beanNearbyItem, int position) {
        mPosition = position;
        mBeanNearbyItem = beanNearbyItem;
        String name = TextUtils.isEmpty(beanNearbyItem.getSign())
                ? getContext().getString(R.string.anonymity)
                : beanNearbyItem.getSign();
        mName.setText(name);
        mContent.setText(beanNearbyItem.getQuest());
        // mLocDesc.setText(beanNearbyItem.getSelfLocDesc());
        mReplyCount.setText(getContext().getString(R.string.replyCount,
                beanNearbyItem.getAnsNum()));

        // distance
        String distance = beanNearbyItem.getDistance() < 3 ? " <3" : String
                .valueOf(beanNearbyItem.getDistance());
        mLocDesc.setText(getContext().getString(R.string.distance, distance));

        // manage sign color
        if (beanNearbyItem.getIsYellow() == 1) {
            mName.setTextColor(getResources().getColor(R.color.dfYellow));
        } else {
            mName.setTextColor(getResources().getColor(R.color.dfBlue));
        }

        //danmu
        if(expandPosition == position) {
            showDamu();
        }else {
            hideDamu();
        }

        // padding color
        int index = position % 3;
        int res = R.color.tagYellow;
        switch (index) {
            case 1:
                res = R.color.tagRed;
                break;
            case 2:
                res = R.color.tagGreen;
                break;
            default:
                break;
        }
        setLikePanel(beanNearbyItem);

        // List ITEM INTERVAL COLOR
        int color = getResources().getColor(R.color.white);
        if (position % 2 == 1) {
            color = getResources().getColor(R.color.listItemDark);
        } else {
            color = getResources().getColor(R.color.white);
        }
        mItemSubContainer.setBackgroundColor(color);

        mItemContainer.setBackgroundColor(getResources().getColor(res));
    }

    public void setLikePanel(BeanNearbyItem beanNearbyItem) {
        //like unlike

        boolean likeEnable = true;
        boolean isUnlike;
        boolean isLike;

        switch (beanNearbyItem.getLikeFlag()) {
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

        mLike.setChecked(isLike);
        mUnLike.setChecked(isUnlike);

        mLike.setEnabled(likeEnable);
        mUnLike.setEnabled(likeEnable);
    }

    public void onListModeClick(View v) {
        ActivityDetail.Extra extra = new ActivityDetail.Extra();
        extra.fromNearby = mBeanNearbyItem;
        extra.questId = mBeanNearbyItem.getQuestId();
        ActivityDetail.start((Activity) getContext(), extra);
    }

    public void doNothing(View v) {
        L.dbg("do nothing");
    }

    public void onItemClick(View v) {
        toggleDanmuPanel();
    }

    public static void resetExpandPosition() {
        expandPosition = NO_EXPAND;
    }
}
