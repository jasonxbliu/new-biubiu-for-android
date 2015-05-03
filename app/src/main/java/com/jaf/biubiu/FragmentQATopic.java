package com.jaf.biubiu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jaf.bean.BeanNearbyItem;
import com.jaf.bean.BeanRequestTopicQuestionList;
import com.jaf.bean.ResponseTopicQuestions;
import com.jaf.jcore.AbsWorker;
import com.jaf.jcore.BindView;
import com.jaf.jcore.BindableFragment;
import com.jaf.jcore.JacksonWrapper;
import com.jaf.jcore.NetworkListView;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by jarrah on 2015/4/14.
 * 一个完整问答 来自 板块
 */
public class FragmentQATopic extends BindableFragment implements Constant{

    private static final String TAG = "FragmentQuestionList";
    public static final String KEY_REQUEST = "request_json";
    private int mUnionId;

    private ArrayList<BeanNearbyItem> mDataSource;

    public FragmentQATopic() {}

    @BindView(id = R.id.networkListView)
    private NetworkListView<ViewNearbyItem, BeanNearbyItem> mNetworkListView;

    private AbsWorker.AbsLoader<ViewNearbyItem, BeanNearbyItem> loader;

    public static Fragment newInstance(BeanRequestTopicQuestionList arg) {
        FragmentQATopic f = new FragmentQATopic();
        Bundle b = new Bundle();
        b.putSerializable(KEY_REQUEST, arg);
        f.setArguments(b);
        return f;
    }

    @Override
    protected int onLoadViewResource() {
        return R.layout.fragment_question_list;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onViewDidLoad(Bundle savedInstanceState) {
        super.onViewDidLoad(savedInstanceState);
        ViewNearbyItem.resetExpandPosition();
        mDataSource = new ArrayList<BeanNearbyItem>();
        loader = new AbsWorker.AbsLoader<ViewNearbyItem, BeanNearbyItem>() {
            @Override
            public String parseNextUrl(JSONObject response) {
                return Constant.API;
            }

            @Override
            public JSONObject parseNextJSON(JSONObject response) {
                ResponseTopicQuestions r = JacksonWrapper.json2Bean(response, ResponseTopicQuestions.class);
                ArrayList<BeanNearbyItem> data = r.getReturnData().getContData();
                if(data.size() > 0 ) {
                    int lastId = data.get(data.size() - 1).getSortId();
                    return U.buildTopicQuestion(false, lastId, mUnionId);
                }
                return null;
            }

            @Override
            public ArrayList<BeanNearbyItem> parseJSON2ArrayList(JSONObject response) {
                L.dbg("fragment question  response " + response.toString());
                ResponseTopicQuestions r = JacksonWrapper.json2Bean(response, ResponseTopicQuestions.class);
                ArrayList<BeanNearbyItem> array = new ArrayList<BeanNearbyItem>();
                if(r.getReturnData() != null && r.getReturnData().getContData() != null) {
                    array = r.getReturnData().getContData();
                    if(mNetworkListView.isLoadMore()) {
                        mDataSource.addAll(array);
                    }else{
                        mDataSource.clear();
                        mDataSource.addAll(array);
                    }
                }
                return array;
            }

            @Override
            public void updateItemUI(int position, final BeanNearbyItem data, ViewNearbyItem itemView) {
                itemView.setData(data, position);
                // like unlike
                LikePanelHolder.Extra extra = new LikePanelHolder.Extra();
                extra.aid = data.getAnsId();
                extra.qid = data.getQuestId();
                LikePanelHolder holder = new LikePanelHolder(extra, itemView) {
                    @Override
                    public void onPostSuccess(boolean isLike) {
                        super.onPostSuccess(isLike);
                    }
                };
                holder.setData(data);
                setupLikePanel(position, data, itemView);
            }



            @Override
            public ViewNearbyItem makeItem(LayoutInflater inflater, int position, View convertView, ViewGroup parent) {
                return new ViewNearbyItem(getActivity());
            }
        };
        mNetworkListView.setEmptyView(EmptyHelper.getEmptyView(getActivity(), R.drawable.bg_nearby_empty, R.string.no_topic));
        doRequest();
    }

    private void setupLikePanel(final int position,
                                BeanNearbyItem data, final View itemView) {
        LikePanelHolder.Extra extra = new LikePanelHolder.Extra();
        extra.aid = data.getAnsId();
        extra.qid = data.getQuestId();
        LikePanelHolder likePanelHolder = new LikePanelHolder(extra,
                itemView) {

            @Override
            public void onPostSuccess(boolean isLike) {
            }

            @Override
            public void onPrePost(boolean isLike) {
                super.onPrePost(isLike);
                int count = isLike ? Integer.valueOf(like.getText()
                        .toString()) : Integer.valueOf(unLike.getText()
                        .toString());
                count++;

                if (isLike) {
                    mDataSource.get(position).setLikeFlag(1);
                    mDataSource.get(position).setLikeNum(count);
                } else {
                    mDataSource.get(position).setLikeFlag(2);
                    mDataSource.get(position).setUnlikeNum(count);
                }
                ViewNearbyItem.expandPosition = position;
                mNetworkListView.notifyDataSetChanged();
            }
        };
        likePanelHolder.listenForChecking();
    }

    private void doRequest() {
        Serializable serializable = getArguments().getSerializable(KEY_REQUEST);
        if (getArguments() != null && serializable != null) {
            BeanRequestTopicQuestionList request = (BeanRequestTopicQuestionList) serializable;
            mUnionId = request.getUnionId();
            mNetworkListView.request(Constant.API, loader, U.buildTopicQuestion(true, 0, request.getUnionId()));
        }else {
            L.dbg(TAG + " do request but data is null!");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        ViewNearbyItem.resetExpandPosition();
    }
}
