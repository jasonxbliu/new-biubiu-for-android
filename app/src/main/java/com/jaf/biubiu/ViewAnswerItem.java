package com.jaf.biubiu;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jaf.bean.BeanAnswerItem;
import com.jaf.jcore.BindView;
import com.jaf.jcore.BindableView;

/**
 * Created by jarrah on 2015/4/17.
 */
public class ViewAnswerItem extends BindableView {

    @BindView(id = R.id.likeCheck)
    public CheckBox mLike;

    @BindView(id = R.id.unlikeCheck)
    public CheckBox mUnLike;

    @BindView(id = R.id.content)
    private TextView mContent;

    @BindView(id = R.id.time)
    private TextView mTime;

    @BindView(id = R.id.floorNum)
    private TextView mFloorNum;

    @BindView(id = R.id.relText)
    private TextView mRelText;


    private LikePanelHolder mLikePanelHolder;

    public ViewAnswerItem(Context context) {
        super(context);
    }

    @Override
    public void onViewDidLoad() {
    }

    @Override
    public int onLoadViewResource() {
        return R.layout.view_answer_item;
    }

    public void setData(BeanAnswerItem beanAnswerItem, int position) {
        if (beanAnswerItem != null) {
            mContent.setText(beanAnswerItem.getAns());
//            mTime.setText(String.valueOf(beanAnswerItem.getPubTime()));
            mTime.setText(DateUtil.getDiffTime(beanAnswerItem.getPubTime() * 1000L));
            mLike.setText(String.valueOf(beanAnswerItem.getLikeNum()));
            mUnLike.setText(String.valueOf(beanAnswerItem.getUnlikeNum()));

            //other answer
            if(beanAnswerItem.getOtherAnsData() != null) {
                int floor = beanAnswerItem.getOtherAnsData().getFloorId();
                mRelText.setVisibility(View.VISIBLE);
                mRelText.setText(getResources().getString(R.string.replyFloorAt, floor, beanAnswerItem.getOtherAnsData().getAns()));
            }else{
                mRelText.setVisibility(View.GONE);
            }


            if(beanAnswerItem.getIsMine() == 1) {
                mContent.setTextColor(getResources().getColor(R.color.dfYellow));
            }else{
                mContent.setTextColor(getResources().getColor(R.color.dfTextColor));
            }

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

            mLike.setChecked(isLike);
            mUnLike.setChecked(isUnlike);

            mLike.setEnabled(likeEnable);
            mUnLike.setEnabled(likeEnable);
        }
    }

    public void setFloor(int position) {
        int index = position % 3;
        int res = R.drawable.shape_oval_yellow;
        switch (index) {
            case 1:
                res = R.drawable.shape_oval_red;
                break;
            case 2:
                res = R.drawable.shape_oval_green;
                break;
            default:
                break;
        }
        mFloorNum.setBackgroundResource(res);
        mFloorNum.setText(String.valueOf(position + 1));
    }
}
