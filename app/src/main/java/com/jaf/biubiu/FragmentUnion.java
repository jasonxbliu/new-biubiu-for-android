package com.jaf.biubiu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.location.BDLocation;
import com.jaf.bean.BeanUnionItem;
import com.jaf.bean.ResponseTopic;
import com.jaf.jcore.AbsWorker;
import com.jaf.jcore.Application;
import com.jaf.jcore.BindView;
import com.jaf.jcore.BindableFragment;
import com.jaf.jcore.JacksonWrapper;
import com.jaf.jcore.NetworkListView;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by jarrah on 2015/4/14.
 */
public class FragmentUnion extends BindableFragment implements Constant.CMD{

    @BindView(id = R.id.topicList)
    private NetworkListView<ViewUnionItemView, BeanUnionItem> mNetworkListView;
    private com.jaf.jcore.AbsWorker.AbsLoader<ViewUnionItemView, BeanUnionItem> loader;

    public FragmentUnion() {}

    @Override
    protected int onLoadViewResource() {
        return R.layout.fragment_union;
    }

    @Override
    protected void onViewDidLoad(Bundle savedInstanceState) {
        super.onViewDidLoad(savedInstanceState);
        loader = new AbsWorker.AbsLoader<ViewUnionItemView, BeanUnionItem>() {
            @Override
            public String parseNextUrl(JSONObject response) {
                return Constant.API;
            }

            @Override
            public JSONObject parseNextJSON(JSONObject response) {
                ResponseTopic responseTopic = JacksonWrapper.json2Bean(response, ResponseTopic.class);
                ArrayList<BeanUnionItem> data = responseTopic.getReturnData().getContData();
                if(data.size() > 0 ) {
                    int lastId = data.get(data.size() - 1).getSortId();
                    return U.buildTopic(false, lastId, 1);
                }
                return null;
            }

            @Override
            public ArrayList<BeanUnionItem> parseJSON2ArrayList(JSONObject response) {
                ResponseTopic responseTopic = JacksonWrapper.json2Bean(response, ResponseTopic.class);
                L.dbg("TOPIC : " + response);
                if (responseTopic != null && responseTopic.getReturnData() != null) {
                    return responseTopic.getReturnData().getContData();
                }else {
                    return null;
                }
            }

            @Override
            public void updateItemUI(int position, final BeanUnionItem data, ViewUnionItemView itemView) {
                itemView.setData(data);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityUnionTopic.Extra extra = new ActivityUnionTopic.Extra();
                        extra.fromTopic = U.buildTopicQuestionListArg(data.getUnionId());
                        extra.topicTitle = data.getUnionName();
                        ActivityUnionTopic.start(getActivity(), extra);
                    }
                });
            }

            @Override
            public ViewUnionItemView makeItem(LayoutInflater inflater, int position, View convertView, ViewGroup parent) {
                return new ViewUnionItemView(getActivity());
            }
        };

        LocationManager.getInstance().requestLocation(new LocationManager.JLsn() {
            @Override
            public void onResult(double latitude, double longitude, BDLocation location) {
                super.onResult(latitude, longitude, location);
                Application.getInstance().getAppExtraInfo().lat = latitude;
                Application.getInstance().getAppExtraInfo().lon = longitude;
                mNetworkListView.request(Constant.API, loader, U.buildTopic(true, 0, 1));
            }
        });

        mNetworkListView.setEmptyView(EmptyHelper.getEmptyView(getActivity(), R.drawable.bg_nearby_empty, R.string.unionEmpty));
    }

    public static Fragment newInstance(Bundle arg) {
        return new FragmentUnion();
    }
}
