package com.jaf.jcore;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

public class CacheHttpRequest extends BaseHttpRequst {

	private static final int DEFALUT_CACHE_EXPIRE = 24 * 60 * 60 * 1000;
	private static long expire = DEFALUT_CACHE_EXPIRE;

	public CacheHttpRequest(String url, JSONObject jsonRequest,
			Listener<JSONObject> listener, ErrorListener errorListener) {
		super(url, jsonRequest, listener, errorListener);
	}

	@Override
	protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
		try {
			String jsonString = new String(response.data,
					HttpHeaderParser.parseCharset(response.headers));
			return Response.success(new JSONObject(jsonString),
					parseIgnoreCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JSONException je) {
			return Response.error(new ParseError(je));
		}
	}

	public static Cache.Entry parseIgnoreCacheHeaders(NetworkResponse response) {
		long now = System.currentTimeMillis();

		Map<String, String> headers = response.headers;
		long serverDate = 0;
		String serverEtag = null;
		String headerValue;

		headerValue = headers.get("Date");
		if (headerValue != null) {
			serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
		}

		serverEtag = headers.get("ETag");

		final long cacheHitButRefreshed = 3 * 60 * 1000; // in 3 minutes cache
															// will be hit, but
															// also refreshed on
															// background
		final long cacheExpired = expire; // in 24 hours this cache entry
											// expires completely
		final long softExpire = now + cacheHitButRefreshed;
		final long ttl = now + cacheExpired;

		Cache.Entry entry = new Cache.Entry();
		entry.data = response.data;
		entry.etag = serverEtag;
		entry.softTtl = softExpire;
		entry.ttl = ttl;
		entry.serverDate = serverDate;
		entry.responseHeaders = headers;

		return entry;
	}

	public void setExpire(long expire) {
		CacheHttpRequest.expire = expire;
	}

}
