package com.jaf.jcore;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.PullToRefreshAdapterViewBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author jarrah
 * 
 * @param <T>
 *            itemView 类型
 * @param <ABS>
 *            AbsListView 类型
 * @param <DT>
 *            数据类型
 */
public class AbsWorker<T extends View, ABS extends AbsListView, DT> {

	private static final boolean DBG = true;
	private Http mHttp;

	private String mRequestTag;

	private PullToRefreshAdapterViewBase<ABS> mPullToRefreshAdapterViewBase;

	private AbsLoader<T, DT> mLoader;

	private WorkerCallBack mCallBack;

	private WorkerAdapter mAdapter;

	public boolean isLoadMore;
	private boolean isLoading;

	private String mUrl;
	private String mNextUrl;
	private JSONObject mPostJo;
	private JSONObject mNextPostJo;

	public AbsWorker(final Context context,
			PullToRefreshAdapterViewBase<ABS> pullToRefreshAdapterViewBase) {

		mRequestTag = "abs_worker" + System.currentTimeMillis();// cancel tag
		mHttp = new Http();
		mCallBack = new WorkerCallBack();
		mPullToRefreshAdapterViewBase = pullToRefreshAdapterViewBase;
		mAdapter = new WorkerAdapter(context);
		pullToRefreshAdapterViewBase.setAdapter(mAdapter);

		mPullToRefreshAdapterViewBase
				.setOnRefreshListener(new OnRefreshListener<ABS>() {
					@Override
					public void onRefresh(PullToRefreshBase<ABS> refreshView) {
						isLoadMore = false;
						if (mPostJo == null) {
							Toast.makeText(context, R.string.network_err,
									Toast.LENGTH_SHORT).show();
                            mPullToRefreshAdapterViewBase.onRefreshComplete();
							return;
						}
                        Util.e("Abs worker : do refresh");
						request(mUrl, mLoader, mPostJo);
					}
				});

		mPullToRefreshAdapterViewBase
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
					@Override
					public void onLastItemVisible() {
						isLoadMore = true;
						if (mNextPostJo == null) {
							Toast.makeText(
									Application.getInstance()
											.getApplicationContext(),
									R.string.no_more, Toast.LENGTH_SHORT)
									.show();
						} else {
							Util.e("Abs worker : do load more");
							if (mNextPostJo == null) {
								Toast.makeText(context, R.string.network_err,
										Toast.LENGTH_SHORT).show();
								return;
							}
							request(mNextUrl, mLoader, mNextPostJo);
						}
					}
				});
	}
	// public void request(String url, AbsLoader<T, DT> loader, String token) {
	// if (token != null) {
	// mHttp.cookie("token", token);
	// }
	// request(url, loader, jo);
	// }

	// public void request(String url, AbsLoader<T, DT> loader) {
	// if (isLoading)
	// return;
	//
	// isLoading = true;
	// mLoader = loader;
	// mHttp.url(url).get(mCallBack);
	// setRequestUrl(url);
	// }

	public void request(String url, AbsLoader<T, DT> loader, JSONObject jo) {
		if (isLoading)
			return;
		isLoading = true;
		mLoader = loader;

		if (!isLoadMore)
			mPostJo = jo;
		else
			mNextPostJo = jo;

        JSONObject postJo = isLoadMore ? mNextPostJo : mPostJo;
        Util.e("Net work ListView request JSON : " + postJo);
		mHttp.url(url).JSON(postJo).post(mCallBack);
		setRequestUrl(url);
	}

	public void setRequestUrl(String url) {
		if (mUrl == null)
			mUrl = url;
	}

	public void setLoader(AbsLoader<T, DT> loader) {
		mLoader = loader;
	}

	public void expire(long expire) {
		mHttp.exipre(expire);
	}

	// public void request() {
	// if (isLoading)
	// return;
	// isLoading = true;
	// mHttp.url(mUrl).get(mCallBack);
	// }

	public void cancelRequest() {
		Application.getInstance().cancelRequest(mRequestTag);
	}

	final class WorkerCallBack extends HttpCallBack {
		@Override
		public void onResponse(JSONObject response) {
			super.onResponse(response);

			if (DBG) {
				Util.e("[worker : response] :" + response);
			}

			mPullToRefreshAdapterViewBase.onRefreshComplete();
			isLoading = false;
			if (mLoader != null) {
				ArrayList<DT> data = mLoader.parseJSON2ArrayList(response);
				setAdapterData(data, isLoadMore);
				mNextUrl = mLoader.parseNextUrl(response);
				mNextPostJo = mLoader.parseNextJSON(response);
			}
		}

		@Override
		public void onErrorResponse(VolleyError error) {
			super.onErrorResponse(error);
			mPullToRefreshAdapterViewBase.onRefreshComplete();
			isLoading = false;
		}
	}

	final class WorkerAdapter extends AdapterWrapper<DT, T> {

		public WorkerAdapter(Context context) {
			super(context);
		}

		@Override
		protected void onBindView(int position, DT item, T view) {
			mLoader.updateItemUI(position, item, view);
		}

		@Override
		public T newView(int position, LayoutInflater lf, View convertView,
				ViewGroup parent) {
			return mLoader.makeItem(getLayoutInflater(), position, convertView,
					parent);
		}

	}

    public WorkerAdapter getAdapter() {
        return mAdapter;
    }

	public interface AbsLoader<I extends View, DT> {

		/**
		 * 重写获取加载更多的URL
		 * 
		 * @param response
		 * @return
		 */
		String parseNextUrl(JSONObject response);

		JSONObject parseNextJSON(JSONObject response);

		/**
		 * 解析获取到response的JSON 然后每个对象放到arraylist里面
		 * 
		 * @param response
		 * @return
		 */
		ArrayList<DT> parseJSON2ArrayList(JSONObject response);

		/**
		 * 每个item 如何处理对应的JSON对象
		 * 
		 * @param position
		 * @param data
		 * @param itemView
		 */
		void updateItemUI(int position, DT data, I itemView);

		/**
		 * 生成itemView
		 * 
		 * @param inflater
		 * @param position
		 * @param convertView
		 * @param parent
		 * @return
		 */
		I makeItem(LayoutInflater inflater, int position, View convertView,
				ViewGroup parent);
	}

	public void setAdapterData(ArrayList<DT> data) {
        if(DBG) {
            String append = data == null ? "data is null" : String.format("data size >> %d", data.size());
            Log.e("AbsWorker", append);
        }
		if (isLoadMore) {
			if (data == null || data.size() == 0) {
				Toast.makeText(Application.getInstance(), "没有更多数据",
						Toast.LENGTH_SHORT).show();
                isLoadMore = false;
			} else {
				mAdapter.addAll(data);
			}
		} else {
			mAdapter.clear();;
			mAdapter.addAll(data);
		}
	}

	public void setAdapterData(ArrayList<DT> data, boolean isLoadMore) {
		this.isLoadMore = isLoadMore;
		setAdapterData(data);
        if (DBG) {
            String cur = String.format("current size is : " + mAdapter.getCount());
            String more = String.format("is load more ? : " + isLoadMore);
            Log.e("AbsWorker", cur + " | " + more);
        }
	}

    public void notifyDataSetInvalidated() {
        mAdapter.notifyDataSetInvalidated();
    }

	public void notifyDataSetChanged() {
		mAdapter.notifyDataSetChanged();
	}
}
