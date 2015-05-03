package com.jaf.biubiu;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jaf.bean.BeanUser;
import com.jaf.bean.ResponseUser;
import com.jaf.jcore.AdapterWrapper;
import com.jaf.jcore.BindView;
import com.jaf.jcore.BindableFragment;
import com.jaf.jcore.Http;
import com.jaf.jcore.HttpCallBack;
import com.jaf.jcore.JacksonWrapper;

import org.json.JSONObject;

/**
 * Created by jarrah on 2015/4/14.
 */
public class FragmentMe extends BindableFragment{

    private static final String TAG = "Fragment Me";
    BeanUser mBeanUser;

    @BindView(id = R.id.meList)
    private ListView mListView;

    @BindView(id = R.id.percent)
    private TextView mPercent;

    @BindView(id = R.id.percentText)
    private TextView mPercentText;

    @BindView(id = R.id.progressBar)
    private ProgressBar mProgressBar;

    @BindView(id = R.id.getLikeCount)
    private TextView mLikeCount;

    private Adapter mAdapter;

    public static Fragment newInstance(Bundle arg) {
        return new FragmentMe();
    }

    @Override
    protected int onLoadViewResource() {
        return R.layout.fragment_me;
    }

    @Override
    protected void onViewDidLoad(Bundle savedInstanceState) {
        super.onViewDidLoad(savedInstanceState);
        initUserInfo();
        setupTop();
        setupMeList();
        requestUserInfo();
    }

    private void initUserInfo() {
        mBeanUser = new BeanUser();
        mBeanUser.setRank(0);
        mBeanUser.setAnswerNum(0);
        mBeanUser.setQuestionNum(0);
    }

    private void setupTop() {
        if(mBeanUser == null) return;
        mProgressBar.setProgress(mBeanUser.getRankRate());
        mPercent.setText(mBeanUser.getRankRate() + "%");
        mPercentText.setText(getString(R.string.userBeyondPercent, mBeanUser.getRankRate()));
        mLikeCount.setText(String.valueOf(mBeanUser.getOtherLikeNum()));
    }

    private void setupMeList() {
        mAdapter = new Adapter(getActivity());
        mListView.setAdapter(mAdapter);
        String qNum = mBeanUser == null ? "0" : String.valueOf(mBeanUser.getQuestionNum());
        String aNum = mBeanUser == null ? "0" : String.valueOf(mBeanUser.getAnswerNum());
        String unionNum = mBeanUser == null ? "0" : String.valueOf(mBeanUser.getUnionNum());
        mAdapter.add(MeItem.newItem(getString(R.string.myQusestion), qNum));
        mAdapter.add(MeItem.newItem(getString(R.string.myAnswer), aNum));
        mAdapter.add(MeItem.newItem(getString(R.string.myUnion), unionNum));
    }

    @Override
    public void onResume() {
        super.onResume();
        requestUserInfo();
    }

    private void requestUserInfo() {
        Http http = new Http(TAG);
        http.url(Constant.API).JSON(U.buildUser()).post(new HttpCallBack() {
            @Override
            public void onResponse(JSONObject response) {
                super.onResponse(response);
                ResponseUser responseUser = JacksonWrapper.json2Bean(response, ResponseUser.class);
                if (responseUser != null) {
                    mBeanUser = responseUser.getReturnData();

                    if(mBeanUser == null) {
                        initUserInfo();
                    }

                    L.dbg(TAG + " " + response.toString());
                    setupTop();
                    setupMeList();
                }
            }
        });
    }

    public static class Adapter extends AdapterWrapper<MeItem, View> {

        public Adapter(Context context) {
            super(context);
        }

        @Override
        protected void onBindView(final int position, MeItem item, View view) {
            TextView desc = (TextView) view.findViewById(R.id.meDesc);
            TextView count = (TextView) view.findViewById(R.id.meCount);
            desc.setText(item.text);
            count.setText(item.count);
            final boolean isQuestion = position == 0 ? true : false;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(position != 2) {
                        ActivityMyQA.start((Activity) getContext(), ActivityMyQA.Extra.newExtra(isQuestion));
                    }
                    else {
                        ActivityMyUnion.start((Activity) getContext());
                    }

                }
            });
        }

        @Override
        public View newView(int position, LayoutInflater lf, View convertView, ViewGroup parent) {
            return lf.inflate(R.layout.item_me_desc, null);
        }
    }

    public static class MeItem {
        String text;
        String count;

        public static MeItem newItem(String text, String count) {
            MeItem i = new MeItem();
            i.text = text;
            i.count = count;
            return i;
        }
    }

}
