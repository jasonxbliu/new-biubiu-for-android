package com.jaf.jcore;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.jaf.jcore.AbsWorker.AbsLoader;
import java.util.ArrayList;

/**
 * @author jarrah
 *
 * @param <V> itemView 类型
 * @param <DT> 数据类型
 */
public class NetworkGridView<V extends View, DT> extends PullToRefreshGridView {

	private AbsWorker<V, GridView, DT> mWorker;

	public NetworkGridView(Context context) {
		super(context);
		init();
	}

	public NetworkGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		mWorker = new AbsWorker<V, GridView, DT>(getContext(), this);
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		mWorker.cancelRequest();
	}

//	public void request(String url, AbsLoader<V, DT> loader) {
//		mWorker.request(url, loader);
//	}
	
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

}
