package com.jaf.biubiu;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jaf.bean.BeanAnswerItem;
import com.jaf.bean.BeanNearbyItem;
import com.jaf.bean.BeanRequestAnswerList;
import com.jaf.bean.ResponseQuestion;
import com.jaf.jcore.AbsWorker;
import com.jaf.jcore.BindView;
import com.jaf.jcore.BindableFragment;
import com.jaf.jcore.Http;
import com.jaf.jcore.HttpCallBack;
import com.jaf.jcore.JacksonWrapper;
import com.jaf.jcore.NetworkListView;
import com.jarrah.photo.PopupUtil;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by jarrah on 2015/4/16. 一个完整的问答模块 包含一个 问题header 一些答案list 附近 , 板块 公共
 */
public class FragmentQA extends BindableFragment {

    private static final String KEY_ARGS = "args";

    @BindView(id = R.id.questionList)
    public NetworkListView<ViewAnswerItem, BeanAnswerItem> mListView;

    private com.jaf.jcore.AbsWorker.AbsLoader<ViewAnswerItem, com.jaf.bean.BeanAnswerItem> mLoader;
    private View mHeader;
    private HeaderHolder mHeaderHolder;
    private ArrayList<BeanAnswerItem> mDataSource;
    private BeanNearbyItem mFromNearby;
    private Dialog mPopupDialog;
    private BeanAnswerItem mBeanAnswerItem;
    private int mFloorNum;

    private BeanRequestAnswerList mData;

    //0 qid 1 aid
    private int[] mCommentParams = new int[2];

    public FragmentQA() {
    }

    public static FragmentQA newInstance(BeanRequestAnswerList b) {
        Bundle args = new Bundle();
        args.putSerializable(KEY_ARGS, b);
        FragmentQA fragmentQuestionList = new FragmentQA();
        fragmentQuestionList.setArguments(args);
        return fragmentQuestionList;
    }

    @Override
    protected int onLoadViewResource() {
        return R.layout.fragment_qa;
    }

    @Override
    protected void onViewDidLoad(Bundle savedInstanceState) {
        super.onViewDidLoad(savedInstanceState);
        mData = getData();
        mDataSource = new ArrayList<BeanAnswerItem>();
        if (mData != null) {
            headerViewFromNearby();
            setupList();
        } else {
            L.dbg("get data is null!");
        }
    }

    private void headerViewFromNearby() {
        mHeaderHolder = new HeaderHolder();
        mHeader = getActivity().getLayoutInflater().inflate(
                R.layout.layout_question_top, null);

        mHeaderHolder.author = (TextView) mHeader.findViewById(R.id.author);
        mHeaderHolder.title = (TextView) mHeader
                .findViewById(R.id.questionText);
        mHeaderHolder.locDesc = (TextView) mHeader
                .findViewById(R.id.topLocDesc);
        mListView.getRefreshableView().addHeaderView(mHeader);

        Activity activity = getActivity();
        if (activity instanceof ActivityDetail) {
            ActivityDetail activityDetail = (ActivityDetail) activity;
            mFromNearby = activityDetail.getData().fromNearby;
            if (mFromNearby == null)
                return;

            if(!TextUtils.isEmpty(mFromNearby.getSign()))
                mHeaderHolder.author.setText(mFromNearby.getSign());

            mHeaderHolder.title.setText(mFromNearby.getQuest());

            if(!TextUtils.isEmpty(mFromNearby.getSelfLocDesc()))
                mHeaderHolder.locDesc.setText(mFromNearby.getSelfLocDesc());

            LikePanelHolder.Extra extra = new LikePanelHolder.Extra();
            extra.aid = 0;
            extra.qid = mFromNearby.getQuestId();
            LikePanelHolder likePanelHolder = new LikePanelHolder(extra,
                    mHeader) {
                @Override
                public void onPostSuccess(boolean isLike) {
                    super.onPostSuccess(isLike);
                }

                @Override
                public void onPrePost(boolean isLike) {
                    super.onPrePost(isLike);
                    int count = isLike ? Integer.valueOf(like.getText()
                            .toString()) : Integer.valueOf(unLike.getText()
                            .toString());
                    count++;

                    if (isLike) {
                        mFromNearby.setLikeNum(count);
                        mFromNearby.setLikeFlag(1);
                    } else {
                        mFromNearby.setUnlikeNum(count);
                        mFromNearby.setLikeFlag(2);
                    }
                    setData(mFromNearby);
                }
            };
            likePanelHolder.listenForChecking();
            likePanelHolder.setData(mFromNearby);

        }

    }

    private void setupList() {
        mLoader = new AbsWorker.AbsLoader<ViewAnswerItem, BeanAnswerItem>() {
            @Override
            public String parseNextUrl(JSONObject response) {
                return Constant.API;
            }

            @Override
            public JSONObject parseNextJSON(JSONObject response) {
                ResponseQuestion responseQuestion = JacksonWrapper.json2Bean(
                        response, ResponseQuestion.class);
                ArrayList<BeanAnswerItem> data = responseQuestion
                        .getReturnData().getContData();
                if (data.size() > 0) {
                    int lastId = data.get(data.size() - 1).getAnsId();
                    BeanRequestAnswerList bean = getData();
                    bean.setIdType(1);
                    bean.setLastId(lastId);
                    return JacksonWrapper.bean2Json(bean);
                }
                return null;
            }

            @Override
            public ArrayList<BeanAnswerItem> parseJSON2ArrayList(
                    JSONObject response) {
                ResponseQuestion responseQuestion = JacksonWrapper.json2Bean(
                        response, ResponseQuestion.class);
                L.dbg("FragmentAnswerList response :" + response);
                ArrayList<BeanAnswerItem> contData = new ArrayList<>();
                if (responseQuestion != null) {
                    contData = responseQuestion.getReturnData().getContData();
                }

                if(mListView.isLoadMore()) {
                    mDataSource.addAll(contData);
                }else{
                    mDataSource.clear();
                    mDataSource.addAll(contData);
                }

                return contData;
            }

            @Override
            public void updateItemUI(final int position,final BeanAnswerItem data,
                                     ViewAnswerItem itemView) {

                mBeanAnswerItem = data;
                itemView.setData(data, position);
                itemView.setFloor(position);

                onLikePanelEvent(position, data, itemView);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mFloorNum = data.getFloorId();
                        setReplyCommentParams(mDataSource.get(position).getQuestId(), mDataSource.get(position).getAnsId());
                        popup();
                    }

                });
            }

            private void popup() {
                final View view = getActivity().getLayoutInflater().inflate(
                        R.layout.popup_answer_item, null);
                mPopupDialog = PopupUtil.makePopup(getActivity(), view);

                view.findViewById(R.id.btnReply).setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mPopupDialog.dismiss();
                                view.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        prepareReply();
                                    }
                                }, 400);
                            }
                        });

                view.findViewById(R.id.btnReportAbuse).setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mPopupDialog.dismiss();
                                reportAbuse(mBeanAnswerItem);
                            }
                        });

                view.findViewById(R.id.btnCancel).setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mPopupDialog.dismiss();
                            }
                        });

                mPopupDialog.show();
            }

            private void prepareReply() {
//                L.dbg("do prepareReply set prepareReply data aid %d", mBeanAnswerItem.getAnsId());
                ActivityDetail activityDetail = (ActivityDetail) getActivity();
                Device.showSoftKeyboard(activityDetail.mEditText, activityDetail);
                activityDetail.mEditText.setHint(getString(R.string.replyFloor, mFloorNum));
                activityDetail.isReplyComment = true;
//                setReplyCommentParams(mBeanAnswerItem.getQuestId(), mBeanAnswerItem.getAnsId());
//                activityDetail.ansId = mBeanAnswerItem.getAnsId();
            }

            private void reportAbuse(BeanAnswerItem item) {
                if (item == null)
                    return;
                Http http = new Http();
                // type 1 举报问题 2 举报评论
                http.url(Constant.API)
                        .JSON(U.buildReportAbuse(item.getAnsId(), 2, ""))
                        .post(new HttpCallBack() {
                            @Override
                            public void onResponse(JSONObject response) {
                                super.onResponse(response);
                                Toast.makeText(getActivity(),
                                        R.string.reportSuccess,
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }

            private void onLikePanelEvent(final int position,final BeanAnswerItem data,
                                          final ViewAnswerItem itemView) {
                LikePanelHolder.Extra extra = new LikePanelHolder.Extra();
                extra.qid = data.getQuestId();
                extra.aid = data.getAnsId();
                LikePanelHolder likePanelHolder = new LikePanelHolder(extra,
                        itemView) {

                    @Override
                    public void onPostSuccess(boolean isLike) {
//                        mListView.notifyDataSetChanged();
                    }

                    @Override
                    public void onPrePost(boolean isLike) {
                        int count = isLike ? Integer.valueOf(like.getText()
                                .toString()) : Integer.valueOf(unLike.getText()
                                .toString());
                        count++;
                        BeanAnswerItem item = null;
                        try {
                            item = mDataSource.get(position);
                        }catch (Exception e) {
                            Toast.makeText(getActivity(), R.string.data_error, Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (isLike) {
                            item.setLikeFlag(1);
                            item.setLikeNum(count);
                        } else {
                            item.setLikeFlag(2);
                            item.setUnlikeNum(count);
                        }
                        mListView.notifyDataSetChanged();
                    }
                };
                likePanelHolder.listenForChecking();
            }

            @Override
            public ViewAnswerItem makeItem(LayoutInflater inflater,
                                           int position, View convertView, ViewGroup parent) {
                return new ViewAnswerItem(getActivity());
            }
        };
        requestListView();
    }

    private void requestListView() {
        mData.setLastId(0);
        JSONObject jo = JacksonWrapper.bean2Json(mData);
        L.dbg("refresh detail list : " + mData);
        mListView.request(Constant.API, mLoader, jo);
    }

    //index 0 - qid 1- ansId
    public int[] getRelyCommentParams() {
        return mCommentParams;
    }

    public void setReplyCommentParams(int... params) {
        if(params.length == mCommentParams.length) {
            L.dbg(String.format("set replay params qid: %d, aid: %d", params[0], params[1]));
            mCommentParams[0] = params[0];
            mCommentParams[1] = params[1];
        }
    }


    public BeanRequestAnswerList getData() {
        return (BeanRequestAnswerList) getArguments().getSerializable(KEY_ARGS);
    }

    public void refreshAnswer() {
        requestListView();
    }

    static class HeaderHolder {
        TextView title;
        TextView author;
        TextView locDesc;
    }
}
