package com.jaf.biubiu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jaf.bean.BeanNearbyItem;
import com.jaf.bean.ResponseMyQA;
import com.jaf.jcore.AbsWorker;
import com.jaf.jcore.BindView;
import com.jaf.jcore.BindableFragment;
import com.jaf.jcore.JacksonWrapper;
import com.jaf.jcore.NetworkListView;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by jarrah on 2015/4/21.
 */
public class FragmentMyA extends BindableFragment{

    public static final String KEY_MY_A = "my_a";

    @BindView(id = R.id.myAList)
    private NetworkListView<ViewMyQAItem, BeanNearbyItem> mListView;

    private com.jaf.jcore.AbsWorker.AbsLoader<com.jaf.biubiu.ViewMyQAItem,com.jaf.bean.BeanNearbyItem> loader;

    public FragmentMyA() {}

    public static  FragmentMyA newInstance(Bundle arg) {
        FragmentMyA fragmentMyQ = new FragmentMyA();
        fragmentMyQ.setArguments(arg);
        return fragmentMyQ;
    }

    public static Fragment newInstance(ActivityMyQA.Extra extra) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_MY_A, extra);
        return newInstance(bundle);
    }

    @Override
    protected int onLoadViewResource() {
        return R.layout.fragment_my_a;
    }

    @Override
    protected void onViewDidLoad(Bundle savedInstanceState) {
        super.onViewDidLoad(savedInstanceState);
        getActivity().setTitle(R.string.myAnswer);
        loader = new AbsWorker.AbsLoader<ViewMyQAItem, BeanNearbyItem>() {
            @Override
            public String parseNextUrl(JSONObject response) {
                return Constant.API;
            }

            @Override
            public JSONObject parseNextJSON(JSONObject response) {
                ResponseMyQA responseMyQA = JacksonWrapper.json2Bean(response, ResponseMyQA.class);
                ArrayList<BeanNearbyItem> data = responseMyQA.getReturnData().getContData();
                if(data.size() > 0 ) {
                    int lastId = data.get(data.size() - 1).getSortId();
                    return U.buildMyAList(false, lastId);
                }
                return null;
            }

            @Override
            public ArrayList<BeanNearbyItem> parseJSON2ArrayList(JSONObject response) {
                ResponseMyQA responseMyQA = JacksonWrapper.json2Bean(response, ResponseMyQA.class);
                if(responseMyQA != null) {
                    return responseMyQA.getReturnData().getContData();
                }
                return null;
            }

            @Override
            public void updateItemUI(int position,final BeanNearbyItem data, ViewMyQAItem itemView) {
                itemView.setData(data);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityDetail.Extra extra = new ActivityDetail.Extra();
                        extra.questId = data.getQuestId();
                        extra.fromNearby = data;
                        ActivityDetail.start(getActivity(), extra);
                    }
                });
            }

            @Override
            public ViewMyQAItem makeItem(LayoutInflater inflater, int position, View convertView, ViewGroup parent) {
                return new ViewMyQAItem(getActivity());
            }
        };
        mListView.request(Constant.API, loader, U.buildMyAList(true, 0));
        mListView.setEmptyView(EmptyHelper.getEmptyView(getActivity(), R.drawable.bg_qa_empty, R.string.emptyMyA));
    }
}
