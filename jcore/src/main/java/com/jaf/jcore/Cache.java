package com.jaf.jcore;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;

/**
 * 最近最少算法的Cache类。提供图片的缓存能力。
 * 
 * @author henry
 * 
 */
public class Cache extends LruCache<String, Bitmap> implements ImageCache {
	private static final Cache INSTANCE = new Cache();

	public static Cache getInstance() {
		return INSTANCE;
	}

	/**
	 * 
	 * @return 根据应用堆大小算出的最大可用缓存KB字节数
	 */
	private static int getDefaultLruCacheSize() {
		final int maxMemory = (int) (Runtime.getRuntime().maxMemory());
		final int cacheSize = maxMemory / 2;
		return cacheSize;
	}

	private Cache() {
		this(getDefaultLruCacheSize());
	}

	private Cache(int sizeInKiloBytes) {
		super(sizeInKiloBytes);
	}

	@SuppressLint("NewApi")
	@Override
	protected int sizeOf(String key, Bitmap bitmap) {
		int ret;
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR1) {
			ret = (bitmap.getRowBytes() * bitmap.getHeight() + 1023) / 1024;
		} else {
			ret = bitmap.getByteCount();
		}
		return ret;
	}

	@Override
	public Bitmap getBitmap(String key) {
		return get(key);
	}

	@Override
	public void putBitmap(String key, Bitmap bmp) {
		put(key, bmp);
	}

}
