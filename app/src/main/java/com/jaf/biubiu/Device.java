package com.jaf.biubiu;

import android.content.Context;
import android.content.res.Resources;
import android.provider.Settings;
import android.util.TypedValue;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.jaf.jcore.Application;

/**
 * Created by jarrah on 2015/4/14.
 */
public class Device {
    public static String getId(Context context) {
        String aid = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return aid;
    }

    public static float dp2px(float dp) {
        Resources r = Application.getInstance().getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
        return px;
    }

    public static float sp2px(float sp) {
        Resources r = Application.getInstance().getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, r.getDisplayMetrics());
        return px;
    }

    public static void showSoftKeyboard(EditText editText, Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }
}
