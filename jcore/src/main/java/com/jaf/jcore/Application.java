package com.jaf.jcore;


import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.androidquery.AQuery;

import cn.jpush.android.api.JPushInterface;

public class Application extends android.app.Application  {

	private static Application INSTANCE;
	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;
    public AppExtraInfo mAppExtraInfo;

	@Override
	public void onCreate() {
		super.onCreate();
		synchronized (Application.class) {
			INSTANCE = this;
		}
        mAppExtraInfo = new AppExtraInfo();
		mRequestQueue = Volley.newRequestQueue(this);
        JPushInterface.setDebugMode(Constant.Debug.DEBUG); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);
    }

	public static final Application getInstance() {
		Application ret;
		synchronized (Application.class) {
			ret = INSTANCE;
		}
		return ret;
	}

	public RequestQueue getRequestQueue() {
		return mRequestQueue;
	}

	public void cancelRequest(String tag) {
		mRequestQueue.cancelAll(tag);
	}
	
	public ImageLoader getImageLoader() {
		getRequestQueue();
		if (mImageLoader == null) {
			mImageLoader = new ImageLoader(this.mRequestQueue,
					Cache.getInstance());
		}
		return this.mImageLoader;
	}


    public static class AppExtraInfo {
        public double lat;
        public double lon;
        public String dvcId;
        public String city;
        public String addrStr;
        public String school;
    }

    public void setAppExtraInfo(String dvcId, double lat, double lon) {
        mAppExtraInfo = getAppExtraInfo();
        mAppExtraInfo.dvcId = dvcId;
        mAppExtraInfo.lat = lat;
        mAppExtraInfo.lon = lon;
    }


    private AQuery aQuery;
    public AQuery getAQuery() {
        if(aQuery == null) {
            aQuery = new AQuery(this);
        }
        return aQuery;
    }

    public AppExtraInfo getAppExtraInfo() {
        return mAppExtraInfo;
    }
}
