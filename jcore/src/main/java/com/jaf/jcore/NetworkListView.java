package com.jaf.jcore;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jaf.jcore.Constant.Debug;
import com.jaf.jcore.AbsWorker.AbsLoader;

import org.json.JSONObject;

import java.util.ArrayList;

public class NetworkListView<V extends View, DT> extends PullToRefreshListView
		implements Debug {

	private AbsWorker<V, ListView, DT> mWorker;

	public NetworkListView(Context context) {
		super(context);
		init();
	}

	public NetworkListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		mWorker = new AbsWorker<V, ListView, DT>(getContext(), this);
		setShowIndicator(false);
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		mWorker.cancelRequest();
	}
	
	public void setExpire(long expire) {
		mWorker.expire(expire);
	}

//	public void request(String url, AbsLoader<V, DT> loader) {
//		mWorker.request(url, loader);
//	}
//
	public void setLoader(AbsLoader<V, DT> loader) {
		mWorker.setLoader(loader);
	}
	
	public void setUrl(String url) {
		mWorker.setRequestUrl(url);
	} 
	
//	public void request() {
//		mWorker.request();
//	}
	
	public void setAdapterData(ArrayList<DT> data, boolean isLoadMore) {
		mWorker.setAdapterData(data, isLoadMore);
	}
	
	public void notifyDataSetChanged() {
		mWorker.notifyDataSetChanged();
	}

	public void request(String url, AbsLoader<V, DT> loader, JSONObject jo) {
        if(jo == null) {
            Toast.makeText(getContext(), R.string.network_err, Toast.LENGTH_SHORT).show();
            return;
        }
		mWorker.request(url, loader, jo);
	}

    public void notifyDataSetInvalidated() {
        mWorker.notifyDataSetInvalidated();
    }

    public boolean isLoadMore() {
        return mWorker.isLoadMore;
    }

    public AbsWorker<V, ListView, DT> getWorker() {
        return mWorker;
    }

}
