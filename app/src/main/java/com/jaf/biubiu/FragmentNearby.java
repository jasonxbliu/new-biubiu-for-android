package com.jaf.biubiu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.location.BDLocation;
import com.jaf.bean.BeanNearbyItem;
import com.jaf.bean.ResponseNearby;
import com.jaf.jcore.AbsWorker;
import com.jaf.jcore.Application;
import com.jaf.jcore.BindView;
import com.jaf.jcore.BindableFragment;
import com.jaf.jcore.Http;
import com.jaf.jcore.HttpCallBack;
import com.jaf.jcore.JacksonWrapper;
import com.jaf.jcore.NetworkListView;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by jarrah on 2015/4/14.
 */
public class FragmentNearby extends BindableFragment implements Constant {

    private static final String TAG = "FragmentNearby";

    public FragmentNearby() {
    }

    private ArrayList<BeanNearbyItem> mDataSource;

    @BindView(id = R.id.networkListView)
    private NetworkListView<ViewNearbyItem, BeanNearbyItem> mNetworkListView;

    private com.jaf.jcore.AbsWorker.AbsLoader<ViewNearbyItem, BeanNearbyItem> loader;

    public static Fragment newInstance(Bundle arg) {
        return new FragmentNearby();
    }

    @Override
    protected int onLoadViewResource() {
        return R.layout.fragment_nearby;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onViewDidLoad(Bundle savedInstanceState) {
        super.onViewDidLoad(savedInstanceState);
        loader = new AbsWorker.AbsLoader<ViewNearbyItem, BeanNearbyItem>() {
            @Override
            public String parseNextUrl(JSONObject response) {
                return Constant.API;
            }

            @Override
            public JSONObject parseNextJSON(JSONObject response) {
                ResponseNearby responseNearby = JacksonWrapper.json2Bean(
                        response, ResponseNearby.class);
                ArrayList<BeanNearbyItem> data = responseNearby.getReturnData()
                        .getContData();
                if (data.size() > 0) {
                    final Application.AppExtraInfo info = Application
                            .getInstance().getAppExtraInfo();
                    int lastId = data.get(data.size() - 1).getSortId();
                    return U.buildNearby(info.lat, info.lon, false, lastId);
                }
                return null;
            }

            @Override
            public ArrayList<BeanNearbyItem> parseJSON2ArrayList(
                    JSONObject response) {
                L.dbg(TAG + response.toString());
                ResponseNearby responseNearby = JacksonWrapper.json2Bean(
                        response, ResponseNearby.class);
                // return responseNearby.getReturnData().getContData();

                L.dbg("FragmentNearby response :" + response);
                if (responseNearby != null) {
                    mDataSource = responseNearby.getReturnData().getContData();
                    return mDataSource;
                } else {
                    L.dbg("FragmentNearby response is null !");
                    return null;
                }
            }

            @Override
            public void updateItemUI(int position, final BeanNearbyItem data,
                                     ViewNearbyItem itemView) {
                ViewNearbyItem view = (ViewNearbyItem) itemView;
                view.setData(data, position);

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

            @Override
            public ViewNearbyItem makeItem(LayoutInflater inflater,
                                           int position, View convertView, ViewGroup parent) {
                return new ViewNearbyItem(getActivity());
            }
        };

        requestListWhenLocated();

        mNetworkListView.setEmptyView(EmptyHelper.getEmptyView(getActivity(), R.drawable.bg_nearby_empty, R.string.nearby_empty));
    }

    private void requestListWhenLocated() {
        LocationManager.getInstance().requestLocation(
                new LocationManager.JLsn() {
                    @Override
                    public void onResult(double latitude, double longitude,
                                         BDLocation location) {
                        Application.getInstance().setAppExtraInfo(
                                Device.getId(Application.getInstance()
                                        .getApplicationContext()), latitude,
                                longitude);
                        Application.getInstance().mAppExtraInfo.city = location
                                .getCity();
                        String city = getString(R.string.app_name);
                        if (!TextUtils.isEmpty(Application.getInstance().mAppExtraInfo.city)) {
                            city = location.getCity();
                        }
                        getActivity().setTitle(city);
                        registerDevice();
                        refreshActivityTitle();
                    }
                });
    }

    private void registerDevice() {
        Http http = new Http(TAG);
        final Application.AppExtraInfo info = Application.getInstance()
                .getAppExtraInfo();
        JSONObject jsonObject = U.buildRegister();
        L.dbg("register post :" + jsonObject);
        http.url(Constant.API).JSON(jsonObject).post(new HttpCallBack() {
            @Override
            public void onResponse(JSONObject response) {
                super.onResponse(response);
                L.dbg(TAG + " register : " + response);
                // request url when located and registered
                JSONObject jo = U.buildNearby(info.lat, info.lon, true, 0);
                mNetworkListView.request(API, loader, jo);
            }
        });
    }

    private void refreshActivityTitle() {
        Http http = new Http();
        JSONObject jo = U.buildGetSchoolName();
        http.url(Constant.API).JSON(jo).post(new HttpCallBack() {
            @Override
            public void onResponse(JSONObject response) {
                super.onResponse(response);
                if (response == null) {
                    L.dbg("server error");
                    return;
                }
                L.dbg("refresh title" + response);
                JSONObject returnData = response.optJSONObject("returnData");
                String locDesc = returnData.optString("locDesc", null);
                if (returnData != null && !TextUtils.isEmpty(locDesc)) {
                    ActivityTab activityTab = (ActivityTab) getActivity();
                    L.dbg("locDesc tile : " + locDesc);
                    Application.getInstance().getAppExtraInfo().school = locDesc;
                    activityTab.setLocTitle(locDesc);
                } else {
                    L.dbg("refresh title error");
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        ViewNearbyItem.expandPosition = ViewNearbyItem.NO_EXPAND;
    }
}
