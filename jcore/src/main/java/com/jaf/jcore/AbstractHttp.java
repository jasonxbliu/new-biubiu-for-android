package com.jaf.jcore;

import org.json.JSONObject;

import android.text.TextUtils;
import android.util.Log;

import com.android.volley.RequestQueue;

/**
 * 基于volley的http wrapper
 * 
 * @param <T>
 */
public abstract class AbstractHttp<T extends AbstractHttp<T>> {

	private static final int NO_EXPIRE = -1;
    private static final boolean DBG = true;

    public T mSelf;

	private HttpCallBack mCb;

	// cookie pair
	private String mCookieKey;
	private String mCookieValue;

	private String mUrl;

	private JSONObject mPostObject;

	private long mExpire = NO_EXPIRE;

	private String mRequstTag;

	@SuppressWarnings("unchecked")
	public T self() {
		return (T) this;
	}

	public T get(HttpCallBack callback) {
		callback(callback);

		BaseHttpRequst request;
		if (mExpire != NO_EXPIRE) {
			CacheHttpRequest cacheHttpRequest = new CacheHttpRequest(mUrl,
					mPostObject, mCb, mCb);
			cacheHttpRequest.setExpire(mExpire);
			request = cacheHttpRequest;
		} else {
			request = new BaseHttpRequst(mUrl, mPostObject, mCb, mCb);
		}

		if (mRequstTag != null) // tag for cancel cancelRequest
			request.setTag(mRequstTag);

		assertCookie(request);
		// 执行
		getQueue().add(request);
		return self();
	}

	public T post(HttpCallBack callback) {
		if (mPostObject == null) {
			throw new IllegalArgumentException("post json is null!");
		}
		return get(callback);
	}

	// public T expire(long expire) {
	// CacheHttpRequest cacheHttpRequest = new CacheHttpRequest(mUrl,
	// mPostObject, mCb, mCb);
	// cacheHttpRequest.setExpire(expire);
	// }

	public T exipre(long expire) {
		mExpire = expire;
		return self();
	}

	public void assertCookie(BaseHttpRequst request) {
		// set cookie
		if (!TextUtils.isEmpty(getCookieKey())
				&& !TextUtils.isEmpty(getCookieValue()))
			request.addRequstHeader(getCookieKey(), getCookieValue());
	}

	public T url(String url) {
		mUrl = url;
		return self();
	}

	public String getUrl() {
		return mUrl;
	}

	public T callback(HttpCallBack cb) {
		mCb = cb;
		return self();
	}

	public T cookie(String key, String value) {
		mCookieKey = key;
		mCookieValue = value;
		return self();
	}

	public String getCookieValue() {
		return mCookieValue;
	}

	public String getCookieKey() {
		return mCookieKey;
	}

	public T JSON(JSONObject jsonObject) {
        if(DBG) {
            Log.d("HTTP", "JSON POST :" + jsonObject);
        }
		mPostObject = jsonObject;
		return self();
	}

	public T requestTag(String cancelTag) {
		mRequstTag = cancelTag;
		return self();
	}

	/**
	 * 重写这个方法,用来获取Volley的队列
	 * 
	 * @return
	 */
	public abstract RequestQueue getQueue();

	// ------------Application sample ----------------//

	// public class TheApplication extends Application{
	//
	// public static TheApplication mInstance;
	//
	// private RequestQueue mQueue;
	//
	//
	// @Override
	// public void onCreate() {
	// super.onCreate();
	// mInstance = this;
	// mQueue = Volley.newRequestQueue(this);
	// }
	//
	//
	// public static synchronized TheApplication getInstance() {
	// return mInstance;
	// }
	//
	// public RequestQueue getQueue() {
	// return mQueue;
	// }
	// }
}
