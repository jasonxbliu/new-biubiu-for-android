package com.jaf.biubiu;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.jaf.bean.BeanUnionItem;
import com.jaf.bean.ResponseTopic;
import com.jaf.jcore.*;

import org.json.JSONObject;

import java.util.ArrayList;


public class ActivityMyUnion extends BaseActionBarActivity {

    @BindView(id = R.id.myUnionList)
    private NetworkListView<ViewUnionItemView, BeanUnionItem> mNetworkListView;
    private AbsWorker.AbsLoader<ViewUnionItemView,BeanUnionItem> loader;


    public static void start(Activity activity) {
        activity.startActivity(new Intent(activity, ActivityMyUnion.class));
    }

    @Override
    protected int onLoadViewResource() {
        return R.layout.activity_my_union;
    }

    @Override
    protected void onViewDidLoad(Bundle savedInstanceState) {
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
                    return U.buildTopic(false, lastId, 2);
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
                        ActivityUnionTopic.start(ActivityMyUnion.this, extra);
                    }
                });
            }

            @Override
            public ViewUnionItemView makeItem(LayoutInflater inflater, int position, View convertView, ViewGroup parent) {
                return new ViewUnionItemView(ActivityMyUnion.this);
            }
        };
        mNetworkListView.request(Constant.API, loader, U.buildTopic(true, 0, 2));

        mNetworkListView.setEmptyView(EmptyHelper.getEmptyView(this, R.drawable.bg_nearby_empty, R.string.youHasNoUnion));
    }


}
