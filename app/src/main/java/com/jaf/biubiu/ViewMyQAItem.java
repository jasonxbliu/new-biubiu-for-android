package com.jaf.biubiu;

import android.content.Context;
import android.widget.TextView;

import com.jaf.bean.BeanNearbyItem;
import com.jaf.jcore.BindView;
import com.jaf.jcore.BindableView;

/**
 * Created by jarrah on 2015/4/21.
 */
public class ViewMyQAItem extends BindableView {

    @BindView(id = R.id.locDesc)
    private TextView mLocDesc;

    @BindView(id = R.id.time)
    private TextView mTime;

    @BindView(id = R.id.content)
    private TextView mContent;

    @BindView(id = R.id.msgCount)
    private TextView mMsgCount;

    @BindView(id = R.id.likeCount)
    private TextView mLikeCount;

    @BindView(id = R.id.unlikeCount)
    private TextView mUnlikeCount;

    public ViewMyQAItem(Context context) {
        super(context);
    }

    @Override
    public void onViewDidLoad() {

    }

    @Override
    public int onLoadViewResource() {
        return R.layout.view_my_qa_item;
    }

    public void setData(BeanNearbyItem beanMyQAItem) {
        mTime.setText(DateUtil.getDiffTime(beanMyQAItem.getPubTime() * 1000L));
        mLocDesc.setText(beanMyQAItem.getSelfLocDesc());
        mMsgCount.setText(String.valueOf(beanMyQAItem.getAnsNum()));
        mLikeCount.setText(String.valueOf(beanMyQAItem.getLikeNum()));
        mUnlikeCount.setText(String.valueOf(beanMyQAItem.getUnlikeNum()));
        mContent.setText(beanMyQAItem.getQuest());
    }
}
