package com.jaf.biubiu;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jaf.bean.BeanNearbyItem;
import com.jaf.bean.BeanRequestAnswerList;
import com.jaf.jcore.Application;
import com.jaf.jcore.BaseActionBarActivity;
import com.jaf.jcore.BindView;
import com.jaf.jcore.Http;
import com.jaf.jcore.HttpCallBack;
import com.jaf.jcore.JacksonWrapper;
import com.jarrah.photo.PopupUtil;

import org.json.JSONObject;

import java.io.Serializable;

public class ActivityDetail extends BaseActionBarActivity {

	private static final String KEY_ARGS = "question_data";
	private Fragment mDisplayFragment;
	private Extra extra;

	@BindView(id = R.id.editPanel)
	private View mEditPanel;

	@BindView(id = R.id.msgEdit)
	public EditText mEditText;

	@BindView(id = R.id.send, onClick = "onSendClick")
	private View mSend;
	private Dialog mDialog;

    //for reply comment
    boolean isReplyComment = false;

	@Override
	protected View getActionBarView() {
		mActionBarView = getLayoutInflater().inflate(
				R.layout.view_action_bar_detail, null);
		mActionBarView.findViewById(R.id.barOption).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						popupReportAbuse();
					}
				});
		return mActionBarView;
	}

	private void popupReportAbuse() {
		View v = getLayoutInflater().inflate(R.layout.popup_report_question,
				null);
		v.findViewById(R.id.btnOK).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Http http = new Http();
						// type 1 举报问题 2 举报评论
						http.url(Constant.API)
								.JSON(U.buildReportAbuse(
										extra.fromNearby.getQuestId(), 1, ""))
								.post(new HttpCallBack() {
									@Override
									public void onResponse(JSONObject response) {
										super.onResponse(response);
										Toast.makeText(ActivityDetail.this,
												R.string.reportSuccess,
												Toast.LENGTH_SHORT).show();
									}
								});
						mDialog.dismiss();
					}
				});

		v.findViewById(R.id.btnCancel).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						mDialog.dismiss();
					}
				});

		mDialog = PopupUtil.makePopup(this, v);
		mDialog.show();
	}

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    private boolean fromPushMessage() {
        boolean ret = getIntent().getBooleanExtra(Constant.KEY_PUSH_DETAIL, false);
        return ret;
    }

    @Override
	protected int onLoadViewResource() {
		return R.layout.activity_detail;
	}

	@Override
	protected void onViewDidLoad(Bundle savedInstanceState) {
		extra = getData();
		if (extra == null) {
			L.dbg("activity question getData is null!");
			return;
		} else {
			if (extra.fromNearby != null) {
				BeanRequestAnswerList arg = U.buildAnswerArgs(true, 0,
						extra.questId);
				mDisplayFragment = FragmentQA.newInstance(arg);
			}
			if (mDisplayFragment != null) {
				FragmentTransaction trans = getSupportFragmentManager()
						.beginTransaction();
				trans.replace(R.id.container, mDisplayFragment);
				trans.commit();
			} else {
				L.dbg("display fragment is null");
			}
		}

        mEditText.requestFocus();
	}

	public Extra getData() {
        if(fromPushMessage()) {
            Extra extra = new Extra();
            L.dbg("from push");
            String jsonStr = getIntent().getStringExtra(Constant.KEY_PUSH_EXTRA);
            if(jsonStr != null) {
                try {
                    JSONObject jo = new JSONObject(jsonStr);
                    extra.fromNearby = JacksonWrapper.json2Bean(jo, BeanNearbyItem.class);
                    extra.questId = extra.fromNearby.getQuestId();
                } catch (Exception e) {
                    L.dbg("push start detail error" + e);
                    return null;
                }
            }
            return extra;
        }
		return (Extra) getIntent().getSerializableExtra(KEY_ARGS);
	}

	public static void start(Activity activity, final Extra extra) {
		Intent intent = new Intent();
		intent.putExtra(KEY_ARGS, extra);
		intent.setClass(activity, ActivityDetail.class);
		activity.startActivity(intent);
	}

	public static class Extra implements Serializable {
		// nearby pair
		int questId;
		BeanNearbyItem fromNearby;
	}

	public void onSendClick(View v) {
		final CharSequence text = mEditText.getText();
		if (TextUtils.isEmpty(text)) {
			Toast.makeText(this, R.string.saySomething, Toast.LENGTH_SHORT)
					.show();
		} else {
            String location = Application.getInstance().getAppExtraInfo().addrStr;
            if(!isReplyComment)
                replyQuestion(text.toString(), location);
            else
                replyComment(text.toString(), location);
		}
	}

    private void replyComment(String s, String location) {
        if(extra.fromNearby != null) {
//            FragmentQA qa = (FragmentQA) mDisplayFragment;
//            qa.mListView.getWorker().isLoadMore = false;
//
//            int qid = extra.fromNearby.getQuestId();
            FragmentQA qa = (FragmentQA) mDisplayFragment;
            qa.mListView.getWorker().isLoadMore = false;
            FragmentQA f = (FragmentQA) mDisplayFragment;
            int qid = f.getRelyCommentParams()[0];
            int aid = f.getRelyCommentParams()[1];
            JSONObject jo = U.postAnswerComment(s, aid, qid, location);
            request(jo);
        }else {
            L.dbg("no extra for reply comment");
        }
    }

    public void replyQuestion(String text, String location) {
		if (extra.fromNearby == null)
			return;

        FragmentQA qa = (FragmentQA) mDisplayFragment;
        qa.mListView.getWorker().isLoadMore = false;
		int qid = extra.fromNearby.getQuestId();
		JSONObject jo = U.buildPostAnswerQuestion(text, location,
				qid);
		request(jo);


	}

	private void request(JSONObject jo) {
		L.dbg("do reply :" + jo);
		Http http = new Http();
		http.url(Constant.API).JSON(jo).post(new HttpCallBack() {
			@Override
			public void onResponse(JSONObject response) {
				super.onResponse(response);
				L.dbg("reply success");
				FragmentQA fa = (FragmentQA) mDisplayFragment;
				fa.refreshAnswer();
				mEditText.setText("");
				U.hideSoftKeyboard(ActivityDetail.this);
                isReplyComment = false;
                mEditText.setHint(R.string.bestAnswerHere);
			}
		});
	}

}
