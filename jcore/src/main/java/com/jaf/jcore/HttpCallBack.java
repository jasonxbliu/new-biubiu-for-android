package com.jaf.jcore;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;

import org.json.JSONObject;

public class HttpCallBack implements Listener<JSONObject>, ErrorListener {

	private static final String TAG = "HttpCallBack";

	@Override
	public void onResponse(JSONObject response) {

	}

	@Override
	public void onErrorResponse(VolleyError error) {
		Log.e(TAG, error.toString());
        Toast.makeText(Application.getInstance().getApplicationContext(), R.string.network_err, Toast.LENGTH_SHORT).show();
	}

}
