package com.jaf.jcore;

import java.util.ArrayList;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;


public class Util {

	public static void toast(Object text) {
		Toast.makeText(Application.getInstance().getApplicationContext(),
				text.toString(), Toast.LENGTH_SHORT).show();
	}
	
	public static void toast(int res) {
		Context context = Application.getInstance().getApplicationContext();
		String text = context.getString(res);
		toast(text);
	}
	
	

	public static boolean isInputEmpty(String input, String toast) {
		if (TextUtils.isEmpty(input)) {
			Util.toast(toast);
			return true;
		}else {
			return false;
		}
	}
	
	public static void hideSoftKeyboard(Context context, EditText editText) {
		InputMethodManager imm = (InputMethodManager)context.getSystemService(
			      Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
	}
	
	public static void e(Object o) {
		Log.e("util", o.toString());
	}
	
}
