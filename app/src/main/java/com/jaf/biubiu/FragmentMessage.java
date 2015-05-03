package com.jaf.biubiu;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jaf.bean.BeanMsgItem;
import com.jaf.bean.ResponseMsgList;
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
 * Created by jarrah on 2015/4/14.
 */
public class FragmentMessage extends BindableFragment {

    @BindView(id = R.id.messageList)
    private NetworkListView<ViewMsgItem, BeanMsgItem> mListView;
    private com.jaf.jcore.AbsWorker.AbsLoader<com.jaf.biubiu.ViewMsgItem, com.jaf.bean.BeanMsgItem> loader;
    private Dialog mDialog;
    private static ArrayList<BeanMsgItem> mDataSource;

    public static Fragment newInstance(Bundle arg) {
        return new FragmentMessage();
    }

    @Override
    protected int onLoadViewResource() {
        return R.layout.fragment_message;
    }

    @Override
    protected void onViewDidLoad(Bundle savedInstanceState) {
        super.onViewDidLoad(savedInstanceState);
        mDataSource = new ArrayList<>();
        loader = new AbsWorker.AbsLoader<ViewMsgItem, BeanMsgItem>() {
            @Override
            public String parseNextUrl(JSONObject response) {
                return Constant.API;
            }

            @Override
            public JSONObject parseNextJSON(JSONObject response) {
                ResponseMsgList responseMsgList = JacksonWrapper.json2Bean(
                        response, ResponseMsgList.class);
                if(responseMsgList.getReturnData() != null) {
                    ArrayList<BeanMsgItem> data = responseMsgList.getReturnData()
                            .getContData();
                    if (data.size() > 0) {
                        int lastId = data.get(data.size() - 1).getUllId();
                        return U.buildMsgList(false, lastId);
                    }
                }
                return null;
            }

            @Override
            public ArrayList<BeanMsgItem> parseJSON2ArrayList(
                    JSONObject response) {
                ResponseMsgList responseMsgList = JacksonWrapper.json2Bean(
                        response, ResponseMsgList.class);
                ArrayList<BeanMsgItem> contData = new ArrayList<>();
                if (responseMsgList != null && responseMsgList.getReturnData() != null) {
                    contData = responseMsgList.getReturnData().getContData();
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
            public void updateItemUI(final int position,
                                     final BeanMsgItem data, ViewMsgItem itemView) {
                itemView.setData(data);
                itemView.setOnLongClickListener(new DeleteClick(position, data));
            }
            @Override
            public ViewMsgItem makeItem(LayoutInflater inflater, int position,
                                        View convertView, ViewGroup parent) {
                return new ViewMsgItem(getActivity());
            }
        };
        request();
        mListView.setEmptyView(EmptyHelper.getEmptyView(getActivity(), R.drawable.bg_msg_empty, R.string.emptyMsg));
    }



    private void request() {
        mListView.request(Constant.API, loader,
                U.buildMsgList(true, Integer.MAX_VALUE));
    }

    public void refresh() {
        request();
    }


    public class DeleteClick implements View.OnLongClickListener {
        private int position;

        private BeanMsgItem data;

        public DeleteClick(int position, BeanMsgItem data) {
            this.position = position;
            this.data = data;
        }

        @Override
        public boolean onLongClick(View v) {
            popup(data, position);
            return true;
        }


        public void popup(final BeanMsgItem data, final int position) {
            LayoutInflater lf = (LayoutInflater) getActivity()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = lf.inflate(R.layout.popup_report_question, null);

            Button btnOK = (Button) v.findViewById(R.id.btnOK);
            btnOK.setText(R.string.delete);
            btnOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDataSource.remove(position);
                    L.dbg("on delete click : remove");
                    mListView.setAdapterData(mDataSource, false);
                    mDialog.dismiss();
                    Http http = new Http();
                    http.url(Constant.API)
                            .JSON(U.buildDelete(data.getUllId()))
                            .post(new HttpCallBack() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    super.onResponse(response);
                                    L.dbg("delete success :" + response);
//                                    request();
                                }
                            });
                }
            });

                    v.findViewById(R.id.btnCancel).setOnClickListener(
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mDialog.dismiss();
                                }
                            });



            mDialog = PopupUtil.makePopup(getActivity(), v);
            mDialog.show();
        }
    }
}
